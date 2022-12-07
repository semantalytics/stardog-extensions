package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.common.base.Pair;
import com.complexible.common.base.Streams;
import com.complexible.stardog.AbstractStardogModule;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.index.statistics.Accuracy;
import com.complexible.stardog.index.statistics.Cardinality;
import com.complexible.stardog.plan.Costs;
import com.complexible.stardog.plan.PlanNode;
import com.complexible.stardog.plan.PlanVarInfo;
import com.complexible.stardog.plan.QueryTerm;
import com.complexible.stardog.plan.eval.ExecutionContext;
import com.complexible.stardog.plan.eval.operator.Operator;
import com.complexible.stardog.plan.eval.operator.Solution;
import com.complexible.stardog.plan.eval.operator.SolutionExtender;
import com.complexible.stardog.plan.eval.operator.SolutionIterator;
import com.complexible.stardog.plan.eval.service.*;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.github.davidmoten.geo.GeoHash;
import com.github.davidmoten.geo.LatLong;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.stardog.stark.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Stardog {@link Service} implementation of Geohash decoding. Stardog's query processor
 * searches for a service implementation which indicates it can evaluate a given {@code SERVICE}
 * block using the {@link #canEvaluate(IRI)} method. The matched IRI pattern should be unique.
 */
public class NeighborsService extends SingleQueryService {

	@Override
	public boolean canEvaluate(IRI serviceIri) {
		return GeoHashVocabulary.neighbors.equals(serviceIri);
	}

	@Override
	protected ServiceQuery createQuery(IRI serviceIri, PlanNode body) {
		return new NeighborsServiceQuery(body);
	}

	public static class NeighborsServiceQuery extends PlanNodeBodyServiceQuery {

		private final Optional<QueryTerm> mTopVar;
		private final Optional<QueryTerm> mBottomVar;
		private final Optional<QueryTerm> mLeftVar;
		private final Optional<QueryTerm> mRightVar;

		/**
		 * The hash that we will decode. If it's a variable, we'll perform the operation
		 * for each solution produced by the input argument.
		 */
		private final QueryTerm mHash;

		public NeighborsServiceQuery(PlanNode body) {
			super(GeoHashVocabulary.decode, body);

			Map<QueryTerm, ServiceParameters> parsedParams = ServiceParameterUtils.build(body);
			Preconditions.checkArgument(parsedParams.size() == 1, "Geohash decoding service requires a single parameter set");
			ServiceParameters params = parsedParams.values().iterator().next();
			new NeighborsParameterValidator(params).validate();
			mTopVar = params.first(GeoHashVocabulary.top);
			mBottomVar = params.first(GeoHashVocabulary.bottom);
			mLeftVar = params.first(GeoHashVocabulary.left);
			mRightVar = params.first(GeoHashVocabulary.right);
			mHash = params.first(GeoHashVocabulary.hash).get();
		}

		/**
		 * These variables a required to be unbound at the time of evaluation. That means if the vars
		 * are used in a later join, we will not push the join into the argument of the service query,
		 *
		 * eg Given the following query, the optimizer knows it can't push long/lat scans into the
		 *    service as they would be bound by the service. This is similar to how we treat BIND
		 *    which cannot reference a variable which has been created before it in the query.
		 *    {@code ?city :hasLat ?lat ; :hasLong ?long SERVICE <decode> { [] :lat ?lat ; :long ?long ; :hash ?hash } ?xxx :hasHash ?hash}
		 */
		@Override
		public Set<Integer> getRequiredUnboundOutputs() {
			//return Sets.newHashSet(mTopVar, mBottomVar, mLeftVar, mRightVar);
			return Stream.of(mTopVar, mBottomVar, mLeftVar, mRightVar).filter(Optional::isPresent).map(Optional::get).map(QueryTerm::getName).collect(toSet());
		}

		/**
		 * The inverse of {@link #getRequiredInputBindings()}. The set of variables returned by this method
		 * must be bound by the argument. If the input/output constraints cannot be satisfied by the planner,
		 * an error is thrown. This can occur if the input bindings are never bound, or there is a circular
		 * reference, eg {@code ?input :p ?output}
		 */
		@Override
		public Set<Integer> getRequiredInputBindings() {
			if (mHash.isVariable()) {
				return Collections.singleton(mHash.getName());
			}
			return Collections.emptySet();
		}
/*
		@Override
		public Cardinality estimateCardinality(Costs.CostingContext theCostingContext) {
			if (mHash.isConstant()) {
				return Cardinality.of(1, Accuracy.ACCURATE);
			}
			else {
				return super.estimateCardinality(theCostingContext);
			}
		}

		@Override
		public Cardinality estimateJoinCardinality(Costs.CostingContext theCostingContext, PlanNode joined, int joinKey) {
			return joined.getCardinality();
		}

 */

		@Override
		public String explain(PlanVarInfo theVarInfo) {
			//TODO probably shouldn't put a prefix in there
			return String.format("geohash:neighbors(%s -> (%s, %s, %s, %s))",
			                     mHash.isConstant() ? Value.lex(mHash.getValue().value()) : theVarInfo.getName(mHash.getName()),
			                     mTopVar, mBottomVar, mLeftVar, mRightVar);
		}

		@Override
		public SolutionIterator evaluate(final ExecutionContext theContext,
		                                 final Operator theArg,
		                                 final PlanVarInfo theVarInfo) {
			if (theArg == null) {
				Preconditions.checkArgument(mHash.isConstant());

				Solution soln = theContext.getSolutionFactory().variables(getRequiredUnboundOutputs()).newSolution();
				decodeAndBind(mHash.getValue().value(), soln, theContext.getMappings());
				return SolutionIterator.of(soln);
			}
			else {
				SolutionExtender extender = theContext.getSolutionFactory()
				                                      .createSolutionExtender(Sets.union(theArg.getVars(), getRequiredUnboundOutputs()));
				return SolutionIterator.of(Streams.stream(theArg)
				                                  .map(argSoln -> {
					                                  ValueOrError hashValue = argSoln.getValue(mHash.getName(), theContext.getMappings());
					                                  if (hashValue.isError()) {
						                                  // don't try if input var isn't bound
						                                  return argSoln;
					                                  }
					                                  Solution soln = extender.extend(argSoln);
					                                  decodeAndBind(hashValue.value(), soln, theContext.getMappings());
					                                  return soln;
				                                  }));
			}
		}

		private void decodeAndBind(Value hash, Solution soln, MappingDictionary mappings) {
			Preconditions.checkArgument(hash instanceof Literal);
			if (((Literal) hash).datatype() != Datatype.STRING) {
				throw new IllegalArgumentException("Hash value must be a string");
			}
			// Do we need to worry about failures here? eg, non-strings, bad strings, etc. Ideally bind ValueOrError.Error
            if(mTopVar.isPresent()) {
				soln.set(mTopVar.get().getName(), mappings.add(Values.literal(GeoHash.top(Value.lex(hash)))));
			}
            if(mBottomVar.isPresent()) {
				soln.set(mBottomVar.get().getName(), mappings.add(Values.literal(GeoHash.bottom(Value.lex(hash)))));
			}
            if(mLeftVar.isPresent()) {
				soln.set(mLeftVar.get().getName(), mappings.add(Values.literal(GeoHash.left(Value.lex(hash)))));
			}
            if(mRightVar.isPresent()) {
				soln.set(mRightVar.get().getName(), mappings.add(Values.literal(GeoHash.right(Value.lex(hash)))));
			}
		}

		@Override
		public PlanNodeBodyServiceQueryBuilder toBuilder() {
			return new DecodeServiceQueryBuilder().body(body());
		}

		/**
		 * Plan caching requires inserting placeholder constants and this class facilitates that. Placeholder constants
		 * are substituted with actual constants when a plan is reused from the plan cache.
		 */
		private static class DecodeServiceQueryBuilder extends PlanNodeBodyServiceQueryBuilder {
			@Override
			public PlanNodeBodyServiceQueryBuilder replaceConstants(MappingDictionary dictionary, UnaryOperator<Constant> mapping, boolean performValidation) {
				Preconditions.checkArgument(!performValidation);

				return new CanonicalizedPlanNodeBodyServiceQueryBuilder(this).body(replaceBodyConstants(dictionary, mapping, false));
			}

			@Override
			public NeighborsServiceQuery build() {
				return new NeighborsServiceQuery(mBody);
			}
		}
	}

	/**
	 * Validator for service query parameters
	 */
	static class NeighborsParameterValidator extends Validator {

		NeighborsParameterValidator(final ServiceParameters theParameters) {
			super(theParameters);
		}

		@Override
		public void validate() {
			optional(GeoHashVocabulary.top, this::singleVariable);
			optional(GeoHashVocabulary.bottom, this::singleVariable);
			optional(GeoHashVocabulary.left, this::singleVariable);
			optional(GeoHashVocabulary.right, this::singleVariable);
			mandatory(GeoHashVocabulary.hash, oneOf(this::singleLiteral, this::singleVariable));

			invalidPredicates();
		}
	}

	/**
	 * Guice module to register the Stardog query service via Java service loader
	 */
	public static class NeighborsModule extends AbstractStardogModule {
		@Override
		protected void configure() {
			Multibinder.newSetBinder(binder(), Service.class).addBinding().to(NeighborsService.class).in(Singleton.class);
		}
	}
}
