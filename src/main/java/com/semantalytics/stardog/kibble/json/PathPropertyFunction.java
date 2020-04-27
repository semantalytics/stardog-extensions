package com.semantalytics.stardog.kibble.json;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.jayway.jsonpath.JsonPath;
import org.openrdf.model.Value;

import java.util.stream.Collectors;

public final class PathPropertyFunction implements PropertyFunction {

    private static final IRI FUNCTION_IRI = JsonVocabulary.path.iri;

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
    public PathPlanNodeBuilder newBuilder() {
        return new PathPlanNodeBuilder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Operator translate(final ExecutionContext theExecutionContext,
                              final PropertyFunctionPlanNode thePropertyFunctionPlanNode,
                              final Operator theOperator) throws
            TranslateException {

        if (thePropertyFunctionPlanNode instanceof PathPlanNode) {
            return new PathnOperator(theExecutionContext, (PathPlanNode) thePropertyFunctionPlanNode, theOperator);
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
        Preconditions.checkArgument(theNode instanceof PathPlanNode);

        if (((PathPlanNode) theNode).getInput().isConstant()) {
            final long aLimit = ((PathPlanNode) theNode).getInput().getValue().stringValue().split("\u001f").length;

            // we know this is an exact cardinality for the repeat node, but also get the value of the child
            final double aCount = aLimit * Math.max(1, theNode.getArg().getCardinality().value());

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

        Preconditions.checkArgument(theNode instanceof PathPlanNode);
        Preconditions.checkNotNull(theTermRenderer);

        final PathPlanNode aNode = (PathPlanNode) theNode;

        return String.format("StringArray(%s)", theTermRenderer.render(aNode.getInput()));
    }

    /**
     * Representation of the property function as a `PlanNode`. This is used to represent the function within a query plan.
     *
     * @author Michael Grove
     */
    public static final class PathPlanNode extends AbstractPropertyFunctionPlanNode {

        private PathPlanNode(final PlanNode theArg,
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

        public QueryTerm getResultVar() {
            return getSubjects().get(0);
        }

        public Optional<QueryTerm> getArrayIndexVar() {
            return getSubjects().size() == 2
                    ? Optional.of(getSubjects().get(1))
                    : Optional.empty();
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
        public PathPlanNode copy() {
            return new PathPlanNode(getArg().copy(),
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
            return new PathPlanNodeBuilder();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected boolean canEquals(final Object theObj) {
            return theObj instanceof PathPlanNode;
        }
    }

    /**
     * Basic builder for creating a {@link PathPlanNode}
     *
     * @author  Michael Grove
     */
    public static final class PathPlanNodeBuilder extends AbstractPropertyFunctionNodeBuilder<PathPlanNode> {

        public PathPlanNodeBuilder() {
            arg(PlanNodes.empty());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void validate() {
            super.validate();

            Preconditions.checkState(mSubjects.size() <= 2);
            Preconditions.checkState(mSubjects.get(0).isVariable());
            Preconditions.checkState(mSubjects.size() == 1 || mSubjects.get(1).isVariable());

            Preconditions.checkState(mObjects.size() == 1);
            if(mObjects.get(0).isConstant()) {
                Preconditions.checkState(mObjects.get(0).getValue() instanceof Literal && EvalUtil.isStringLiteral((Literal)mObjects.get(0).getValue()));
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected PathPlanNode createNode(final ImmutableSet<Integer> theSubjVars,
                                          final ImmutableSet<Integer> theObjVars,
                                          final ImmutableSet<Integer> theContextVars,
                                          final ImmutableSet<Integer> theAllVars) {

            return new PathPlanNode(mArg,
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
    public static final class PathOperator extends AbstractOperator implements PropertyFunctionOperator {

        /**
         * The number of times we should repeat the value
         */
        private long mLimit;

        /**
         * The current iteration
         */
        private int mCount;

        /**
         * The current solution
         */
        private Solution mValue;

        /**
         * The child argument
         */
        private final Optional<Operator> mArg;

        /**
         * The original node
         */
        private final PathPlanNode mNode;

        /**
         * An iterator over the child solutions of this operator
         */
        private Iterator<Solution> mInputs = null;

        public PathOperator(final ExecutionContext theExecutionContext, final PathPlanNode theNode, final Operator theOperator) {
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
                    aVars.add(mNode.getResultVar().getName());

                    mNode.getArrayIndexVar().map(QueryTerm::getName).ifPresent(aVars::add);

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

                    aVars.add(mNode.getResultVar().getName());

                    mNode.getArrayIndexVar().map(QueryTerm::getName).ifPresent(aVars::add);

                    // we only want to create solutions with the minimum number of variables
                    mInputs = Iterators.singletonIterator(mExecutionContext.getSolutionFactory()
                            .variables(aVars)
                            .newSolution());
                }
            }


            while (mInputs.hasNext() || mCount < mLimit) {
                if (mValue == null) {
                    // get the current solution, set the value, and begin iteration
                    mValue = mInputs.next();
                    //mValue.set(mNode.getResultVar().getName(), getValue());
                    mCount = 0;
                    mLimit = getMappings().getValue(getValue()).stringValue().split("\u001f").length;
                }

                if (mCount < mLimit) {
                    Value string = literal(getMappings().getValue(getValue()).stringValue().split("\u001f")[mCount]);
                    long value = getMappings().add(string);
                    mValue.set(mNode.getResultVar().getName(), value);
                    if (mNode.getArrayIndexVar().isPresent()) {
                        // update the counter when the var is present
                        mValue.set(mNode.getArrayIndexVar().get().getName(), getMappings().getID(literal(mCount)));
                    }

                    mCount++;

                    return mValue;
                }
                else {
                    mValue = null;
                }
            }

            return endOfData();
        }

        private long getValue() {
            if(mNode.getInput().isConstant()) {
                return mNode.getInput().getIndex();
            } else  {
                return mValue.get(mNode.getInput().getName());
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void performReset() {
            mArg.ifPresent(Operator::reset);
            mCount = 0;

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
