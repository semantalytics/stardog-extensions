package com.semantalytics.stardog.kibble.geo.geohash;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.complexible.stardog.index.statistics.Accuracy;
import com.complexible.stardog.index.statistics.Cardinality;
import com.complexible.stardog.plan.*;
import com.complexible.stardog.plan.eval.ExecutionContext;
import com.complexible.stardog.plan.eval.TranslateException;
import com.complexible.stardog.plan.eval.operator.EmptyOperator;
import com.complexible.stardog.plan.eval.operator.Operator;
import com.complexible.stardog.plan.eval.operator.OperatorVisitor;
import com.complexible.stardog.plan.eval.operator.PropertyFunctionOperator;
import com.complexible.stardog.plan.eval.operator.Solution;
import com.complexible.stardog.plan.eval.operator.impl.AbstractOperator;
import com.complexible.stardog.plan.eval.operator.impl.Solutions;
import com.complexible.stardog.plan.filter.EvalUtil;
import com.complexible.stardog.plan.util.QueryTermRenderer;
import com.github.davidmoten.geo.GeoHash;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;

import static com.complexible.stardog.plan.Costs.*;
import static com.stardog.stark.Values.literal;

public final class Neighbors implements PropertyFunction {

    private static final IRI FUNCTION_IRI = GeoHashVocabulary.neighbors.iri;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IRI> getURIs() {
        return ImmutableList.of(FUNCTION_IRI);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NeighborsPlanNodeBuilder newBuilder() {
        return new NeighborsPlanNodeBuilder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Operator translate(final ExecutionContext theExecutionContext,
                              final PropertyFunctionPlanNode thePropertyFunctionPlanNode,
                              final Operator theOperator) throws
            TranslateException {

        if (thePropertyFunctionPlanNode instanceof NeighborsPlanNode) {
            return new NeighborsOperator(theExecutionContext, (NeighborsPlanNode) thePropertyFunctionPlanNode, theOperator);
        }
        else {
            throw new TranslateException("Invalid node type, cannot translate");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void estimate(final PropertyFunctionPlanNode theNode, CostingContext theContext) throws PlanException {
        Preconditions.checkArgument(theNode instanceof NeighborsPlanNode);

        if (((NeighborsPlanNode) theNode).getInput().isConstant()) {

            // we know this is an exact cardinality for the repeat node, but also get the value of the child
            final double aCount = Math.max(1, theNode.getArg().getCardinality().value());

            // the accuracy of the estimation is whatever is the lesser
            theNode.setCardinality(Cardinality.of(aCount, Accuracy.takeLessAccurate(Accuracy.ACCURATE, theNode.getArg().getCardinality().accuracy())));

            // assume a flat cost of 1 per iteration + the cost of our child
            theNode.setCost(theNode.getCardinality().value() + theNode.getArg().getCost());
        } else {
            theNode.setCardinality(Cardinality.UNKNOWN);

            // assume a flat cost of 1 per iteration + the cost of our child
            theNode.setCost(theNode.getCardinality().value() + theNode.getArg().getCost());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String explain(final PropertyFunctionPlanNode theNode, final QueryTermRenderer theTermRenderer) {

        Preconditions.checkArgument(theNode instanceof NeighborsPlanNode);
        Preconditions.checkNotNull(theTermRenderer);

        final NeighborsPlanNode aNode = (NeighborsPlanNode) theNode;

        return String.format("geohashneighbors(%s)", theTermRenderer.render(aNode.getInput()));
    }

    /**
     * Representation of the property function as a `PlanNode`. This is used to represent the function within a query plan.
     *
     * @author Michael Grove
     */
    public static final class NeighborsPlanNode extends AbstractPropertyFunctionPlanNode {

        private NeighborsPlanNode(final PlanNode theArg,
                                  final List<QueryTerm> theSubjects,
                                  final List<QueryTerm> theObjects,
                                  final QueryTerm theContext,
                                  final QueryDataset.Scope theScope,
                                  final double theCost,
                                  final Cardinality theCardinality,
                                  final ImmutableSet<Integer> theSubjVars,
                                  final ImmutableSet<Integer> thePredVars,
                                  final ImmutableSet<Integer> theObjVars,
                                  final ImmutableSet<Integer> theContextVars,
                                  final ImmutableSet<Integer> theAssuredVars,
                                  final ImmutableSet<Integer> theAllVars) {
            super(theArg, theSubjects, theObjects, theContext, theScope, theCost, theCardinality, theSubjVars,
                    thePredVars, theObjVars, theContextVars, theAssuredVars, theAllVars);
        }

        public QueryTerm getInput() {
            return getObjects().get(0);
        }

        //public QueryTerm getResultVar() {
        //   return getSubjects().get(0);
        //}

        public QueryTerm getTop() {
            return getSubjects().get(0);
        }

        public QueryTerm getBottom() {
            return getSubjects().get(1);
        }

        public QueryTerm getLeft() {
            return getSubjects().get(2);
        }

        public QueryTerm getRight() {
            return getSubjects().get(3);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ImmutableList<QueryTerm> getInputs() {
            return getObjects().get(0).isVariable() ? ImmutableList.of(getObjects().get(0))
                    : ImmutableList.<QueryTerm>of();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public IRI getURI() {
            return FUNCTION_IRI;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public NeighborsPlanNode copy() {
            return new NeighborsPlanNode(getArg().copy(),
                    getSubjects(),
                    getObjects(),
                    getContext(),
                    getScope(),
                    getCost(),
                    getCardinality(),
                    getSubjectVars(),
                    getPredicateVars(),
                    getObjectVars(),
                    getContextVars(),
                    getAssuredVars(),
                    getAllVars());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected PropertyFunctionNodeBuilder createBuilder() {
            return new NeighborsPlanNodeBuilder();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected boolean canEquals(final Object theObj) {
            return theObj instanceof NeighborsPlanNode;
        }
    }

    /**
     * Basic builder for creating a {@link NeighborsPlanNode}
     *
     * @author  Michael Grove
     */
    public static final class NeighborsPlanNodeBuilder extends AbstractPropertyFunctionNodeBuilder<NeighborsPlanNode> {

        public NeighborsPlanNodeBuilder() {
            arg(PlanNodes.empty());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void validate() {
            super.validate();

            Preconditions.checkState(mSubjects.size() == 4);
            Preconditions.checkState(mSubjects.get(0).isVariable());
            Preconditions.checkState(mSubjects.get(1).isVariable());
            Preconditions.checkState(mSubjects.get(2).isVariable());
            Preconditions.checkState(mSubjects.get(3).isVariable());

            Preconditions.checkState(mObjects.size() == 1);
            if(mObjects.get(0).isConstant()) {
                Preconditions.checkState(!mObjects.get(0).getValue().isError());
                Preconditions.checkState(mObjects.get(0).getValue().value() instanceof Literal);
                Preconditions.checkState(EvalUtil.isStringLiteral((Literal)mObjects.get(0).getValue().value()));
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected NeighborsPlanNode createNode(final ImmutableSet<Integer> theSubjVars,
                                               final ImmutableSet<Integer> theObjVars,
                                               final ImmutableSet<Integer> theContextVars,
                                               final ImmutableSet<Integer> theAllVars) {

            return new NeighborsPlanNode(mArg,
                    mSubjects,
                    mObjects,
                    mContext,
                    mScope,
                    mCost,
                    mCardinality,
                    theSubjVars,
                    ImmutableSet.<Integer> of(),
                    theObjVars,
                    theContextVars,
                    Sets.union(theSubjVars, theObjVars).immutableCopy(),
                    theAllVars);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasInputs() {
            return mObjects.get(0).isVariable();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<QueryTerm> getInputs() {
            return  mObjects.get(0).isVariable()
                    ? ImmutableList.of(mObjects.get(0))
                    : ImmutableList.<QueryTerm>of();
        }
    }

    /**
     * Executable operator for the repeat function
     *
     * @author Michael Grove
     */
    public static final class NeighborsOperator extends AbstractOperator implements PropertyFunctionOperator {


        /**
         * The current solution
         */
        private Solution solution;

        /**
         * The child argument
         */
        private final Optional<Operator> mArg;

        /**
         * The original node
         */
        private final NeighborsPlanNode mNode;

        /**
         * An iterator over the child solutions of this operator
         */
        private Iterator<Solution> mInputs = null;

        public NeighborsOperator(final ExecutionContext theExecutionContext, final NeighborsPlanNode theNode, final Operator theOperator) {
            super(theExecutionContext, SortType.UNSORTED);

            mNode = Preconditions.checkNotNull(theNode);
            mArg = Optional.of(theOperator);

        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Solution computeNext() {
            if (mInputs == null) {
                // first call to compute results, perform some init
                // either use our child's solutions, or if we don't have a child, create a single solution to use
                if (mArg.filter(theOp -> !(theOp instanceof EmptyOperator)).isPresent()) {
                    // these are the variables the child arg will bind
                    Set<Integer> aVars = Sets.newHashSet(mArg.get().getVars());

                    // and these are the ones that the pf will bind
                    aVars.add(mNode.getTop().getName());
                    aVars.add(mNode.getBottom().getName());
                    aVars.add(mNode.getRight().getName());
                    aVars.add(mNode.getLeft().getName());

                    //mNode.getArrayIndexVar().map(QueryTerm::getName).ifPresent(aVars::add);

                    // now we create a solution that contains room for bindings for these variables
                    final Solution aSoln = mExecutionContext.getSolutionFactory()
                            .variables(aVars)
                            .newSolution();

                    // and transform the child solutions to this one large enough to accomodate our vars
                    mInputs = Iterators.transform(mArg.get(), theSoln -> {
                        Solutions.copy(aSoln, theSoln);
                        return aSoln;
                    });
                }
                else if (mNode.getInput().isVariable()) {
                    // no arg or empty operator and the input is a variable, there's nothing to repeat
                    return endOfData();
                }
                else {
                    final List<Integer> aVars = Lists.newArrayListWithCapacity(2);

                    aVars.add(mNode.getTop().getName());
                    aVars.add(mNode.getBottom().getName());
                    aVars.add(mNode.getRight().getName());
                    aVars.add(mNode.getLeft().getName());

                    // we only want to create solutions with the minimum number of variables
                    mInputs = Iterators.singletonIterator(mExecutionContext.getSolutionFactory()
                            .variables(aVars)
                            .newSolution());
                }
            }


            while (mInputs.hasNext()) {
                solution = mInputs.next();

                //TODO should test for Literal
                final String top = GeoHash.top(((Literal)getMappings().getValue(getValue())).label());
                final String bottom = GeoHash.bottom(((Literal)getMappings().getValue(getValue())).label());
                final String right = GeoHash.right(((Literal)getMappings().getValue(getValue())).label());
                final String left = GeoHash.left(((Literal)getMappings().getValue(getValue())).label());

                solution.set(mNode.getTop().getName(), getMappings().add(literal(top)));
                solution.set(mNode.getBottom().getName(), getMappings().add(literal(bottom)));
                solution.set(mNode.getRight().getName(), getMappings().add(literal(right)));
                solution.set(mNode.getLeft().getName(), getMappings().add(literal(left)));

                return solution;
            }

            return endOfData();
        }

        private long getValue() {
            if(mNode.getInput().isConstant()) {
                return mNode.getInput().getIndex();
            } else  {
                return solution.get(mNode.getInput().getName());
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void performReset() {
            mArg.ifPresent(Operator::reset);

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<Integer> getVars() {
            return mNode.getSubjects().stream()
                    .filter(QueryTerm::isVariable)
                    .map(QueryTerm::getName)
                    .collect(Collectors.toSet());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void accept(final OperatorVisitor theOperatorVisitor) {
            theOperatorVisitor.visit(this);
        }
    }
}
