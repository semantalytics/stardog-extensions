package com.semantalytics.stardog.kibble.service.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.IntStream;

import com.complexible.common.base.CloseableIterator;
import com.complexible.common.base.Disposables;
import com.complexible.stardog.plan.PlanVarInfo;
import com.complexible.stardog.plan.SortType;
import com.complexible.stardog.plan.eval.ExecutionContext;
import com.complexible.stardog.plan.eval.operator.OperatorException;
import com.complexible.stardog.plan.eval.operator.Solution;
import com.complexible.stardog.plan.eval.operator.SolutionIterator;
import com.google.common.collect.AbstractIterator;
import com.stardog.stark.Value;
import com.stardog.stark.Values;
import com.stardog.stark.query.BindingSet;

import com.stardog.stark.query.QueryExecutionFailure;
import com.stardog.stark.query.SelectQueryResult;

public class SqlResultSetToSolutionIterator extends AbstractIterator<Solution> implements SolutionIterator<Solution>, CloseableIterator<Solution> {

    private final ExecutionContext mExecutionContext;

    private final ResultSet mResult;

    private final PlanVarInfo mVarInfo;

    // reusable solution
    private final Solution mSolution;

    public SqlResultSetToSolutionIterator(final ExecutionContext theExecutionContext,
                                     final ResultSet theResult,
                                     final PlanVarInfo theVarInfo,
                                     final Set<Integer> theVars) {

        mExecutionContext = theExecutionContext;
        mResult = theResult;
        mVarInfo = theVarInfo;
        mSolution = theExecutionContext.getSolutionFactory().variables(theVars).newSolution();
        Disposables.markCreated(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws OperatorException {
        Disposables.markReleased(this);
        try {
            mResult.close();
        } catch (SQLException e) {
            throw new OperatorException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Solution computeNext() throws OperatorException {
        try {
            if(mResult.next()) {
                // needed if not all variables are assured
                mSolution.clear();

                int columnCount = mResult.getMetaData().getColumnCount();
                IntStream.rangeClosed(1, mResult.getMetaData().getColumnCount()).forEach(i -> {
                    Value aValue = null;
                    try {
                        aValue = Values.literal(mResult.getString(i));
                    } catch (SQLException e) {

                    }

                    if (aValue != null) {
                        try {
                            mSolution.set(mVarInfo.getVarId(mResult.getMetaData().getColumnLabel(i).toLowerCase()), mExecutionContext.getMappings().add(aValue));
                        } catch (SQLException e) {
                            throw new OperatorException(e);
                        }
                    }
                });

                return mSolution;
            } else {
                return endOfData();
            }
        }
        catch (QueryExecutionFailure | SQLException e) {
            throw new OperatorException(e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortType getSortType() {
        return SortType.UNSORTED;
    }
}

