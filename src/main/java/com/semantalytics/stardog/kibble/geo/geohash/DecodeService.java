package com.semantalytics.stardog.kibble.geo.geohash;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

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
import com.complexible.stardog.plan.eval.service.PlanNodeBodyServiceQuery;
import com.complexible.stardog.plan.eval.service.Service;
import com.complexible.stardog.plan.eval.service.ServiceParameterUtils;
import com.complexible.stardog.plan.eval.service.ServiceParameters;
import com.complexible.stardog.plan.eval.service.ServiceQuery;
import com.complexible.stardog.plan.eval.service.SingleQueryService;
import com.complexible.stardog.plan.eval.service.Validator;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.stardog.stark.Datatype;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.Values;
import com.stardog.stark.vocabs.XSD;

import com.github.davidmoten.geo.GeoHash;
import com.github.davidmoten.geo.LatLong;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

/**
 * Stardog {@link Service} implementation of Geohash decoding. Stardog's query processor
 * searches for a service implementation which indicates it can evaluate a given {@code SERVICE}
 * block using the {@link #canEvaluate(IRI)} method. The matched IRI pattern should be unique.
 */
public class DecodeService extends SingleQueryService {

	@Override
	public boolean canEvaluate(IRI serviceIri) {
		return GeoHashVocabulary.decode.equals(serviceIri);
	}

	@Override
	protected ServiceQuery createQuery(IRI serviceIri, PlanNode body) {
		return new DecodeServiceQuery(body);
	}

	public static class DecodeServiceQuery extends PlanNodeBodyServiceQuery {

		private final int mLatitudeVar;

		private final int mLongitudeVar;

		/**
		 * The hash that we will decode. If it's a variable, we'll perform the operation
		 * for each solution produced by the input argument.
		 */
		private final QueryTerm mHash;

		public DecodeServiceQuery(PlanNode body) {
			super(GeoHashVocabulary.decode, body);

			Map<QueryTerm, ServiceParameters> parsedParams = ServiceParameterUtils.build(body);
			Preconditions.checkArgument(parsedParams.size() == 1, "Geohash decoding service requires a single parameter set");
			ServiceParameters params = parsedParams.values().iterator().next();
			new DecodeParameterValidator(params).validate();
			mLatitudeVar = params.first(GeoHashVocabulary.latitude).get().getName();
			mLongitudeVar = params.first(GeoHashVocabulary.longitude).get().getName();
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
			return Sets.newHashSet(mLatitudeVar, mLongitudeVar);
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

		@Override
		public String explain(PlanVarInfo theVarInfo) {
			return String.format("geohash:decode(%s -> (%s, %s))",
			                     mHash.isConstant() ? Value.lex(mHash.getValue().value()) : theVarInfo.getName(mHash.getName()),
			                     mLatitudeVar, mLongitudeVar);
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
			Pair<Value, Value> latLong = decode(Value.lex(hash));
			soln.set(mLatitudeVar, mappings.add(latLong.first));
			soln.set(mLongitudeVar, mappings.add(latLong.second));
		}

		/**
		 * Given the hash string, decode it into lat/long components and return the RDF literals to be bound in the solution
		 */
		private Pair<Value, Value> decode(String hash) {
			LatLong coordinate = GeoHash.decodeHash(hash);
			return Pair.create(Values.literal(coordinate.getLat()), Values.literal(coordinate.getLon()));
		}

		@Override
		public PlanNodeBodyServiceQueryBuilder toBuilder() {
			return new DecodeServiceQueryBuilder().body(body());
		}

		/**
		 * Plan caching requires inserting placeholder constants and this class facilitates that. Placeholder constants
		 * are substituted with actual constants when a plan is reused from the plan cache.
		 */
		private static class DecodeServiceQueryBuilder extends PlanNodeBodyServiceQuery.PlanNodeBodyServiceQueryBuilder {
			@Override
			public PlanNodeBodyServiceQuery.PlanNodeBodyServiceQueryBuilder replaceConstants(MappingDictionary dictionary, UnaryOperator<Constant> mapping, boolean performValidation) {
				Preconditions.checkArgument(!performValidation);

				return new CanonicalizedPlanNodeBodyServiceQueryBuilder(this).body(replaceBodyConstants(dictionary, mapping, false));
			}

			@Override
			public DecodeServiceQuery build() {
				return new DecodeServiceQuery(mBody);
			}
		}
	}

	/**
	 * Validator for service query parameters
	 */
	static class DecodeParameterValidator extends Validator {

		DecodeParameterValidator(final ServiceParameters theParameters) {
			super(theParameters);
		}

		@Override
		public void validate() {
			singleVariable(GeoHashVocabulary.latitude);
			singleVariable(GeoHashVocabulary.longitude);
			mandatory(GeoHashVocabulary.hash, oneOf(this::singleLiteral, this::singleVariable));

			invalidPredicates();
		}
	}

	/**
	 * Guice module to register the Stardog query service via Java service loader
	 */
	public static class DecodeModule extends AbstractStardogModule {
		@Override
		protected void configure() {
			Multibinder.newSetBinder(binder(), Service.class).addBinding().to(DecodeService.class).in(Singleton.class);
		}
	}
}
