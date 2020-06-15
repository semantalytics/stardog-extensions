package com.semantalytics.stardog.kibble.service.jdbc;

import com.complexible.stardog.plan.PlanNode;
import com.complexible.stardog.plan.PlanNodes;
import com.complexible.stardog.plan.PlanVarInfo;
import com.complexible.stardog.plan.QueryTerm;
import com.complexible.stardog.plan.eval.ExecutionContext;
import com.complexible.stardog.plan.eval.operator.Operator;
import com.complexible.stardog.plan.eval.operator.OperatorException;
import com.complexible.stardog.plan.eval.operator.SolutionIterator;
import com.complexible.stardog.plan.eval.service.*;
import com.complexible.stardog.plan.util.ExplainRenderer;
import com.complexible.stardog.plan.util.QueryTermRenderer;
import com.complexible.stardog.plan.util.SPARQLRenderer;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.semantalytics.stardog.kibble.jdbc.JdbcVocabulary;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.query.SelectQueryResult;

import java.io.StringReader;
import java.sql.*;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;

public class JdbcService extends SingleQueryService {

    @Override
    public boolean canEvaluate(final IRI iri) {
        return Collections.list(DriverManager.getDrivers()).stream().anyMatch(driver -> {
            try {
                driver.acceptsURL(iri.toString());
                return true;
            } catch (SQLException e) {
                return false;
            }
        });
    }

    @Override
    public PlanNode translate(IRI iri, PlanNode body, IntFunction<String> varNames, final Function<String, Integer> varAllocator, boolean silent) {
        return PlanNodes.service()
                .query(createQuery(iri, body))
                .silent(silent)
                .build();
    }

    @Override
    public ServiceQuery createQuery(final IRI theIRI, final PlanNode thePlanNode) {
        return new ServiceQuery(theIRI) {

            @Override
            public SolutionIterator evaluate(final ExecutionContext theContext, final Operator theOperator, final PlanVarInfo theVarInfo) throws OperatorException {

                final Connection sqlConnection;
                try {
                    sqlConnection = DriverManager.getConnection(serviceTerm().getValue().stringValue());

                    Map<QueryTerm, ServiceParameters> subjToParams = ServiceParameterUtils.build(thePlanNode);
                    Preconditions.checkArgument(subjToParams.size() == 1, "Parameters must correspond to a single subject");

                    ServiceParameters params = Iterables.getFirst(subjToParams.values(), null);

                    QueryTerm queryText = params.first(JdbcVocabulary.query.iri).get();


                    ResultSet rs = sqlConnection.createStatement().executeQuery(((Literal)queryText.getValue().value()).label());

                    return new SqlResultSetToSolutionIterator(theContext, rs, theVarInfo, thePlanNode.getAllVars());
                } catch (SQLException e) {
                    throw new OperatorException(e);
                }
            }

                @Override
            public ImmutableSet<Integer> getAssuredVars() {
                return thePlanNode.getAssuredVars();
            }

            @Override
            public ImmutableSet<Integer> getAllVars() {
                return thePlanNode.getAllVars();
            }

            @Override
            public String explain(PlanVarInfo theVarInfo) {
                return String.format("Service %s %s {\n%s\n}", (new QueryTermRenderer(theVarInfo)).render(this.serviceTerm()), ExplainRenderer.toCardinalityString(PlanNodes.serviceOf(this)), SPARQLRenderer.renderSelect(thePlanNode, theVarInfo, false));
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(this.getRequiredInputBindings(), this.getRequiredUnboundOutputs(), thePlanNode, this.serviceTerm());
            }

            @Override
            public boolean equals(Object obj) {
                if (!this.getClass().equals(obj.getClass())) {
                    return false;
                } else {
                    ServiceQuery q = (ServiceQuery) obj;
                    return this.getRequiredInputBindings().equals(q.getRequiredInputBindings()) && this.getRequiredUnboundOutputs().equals(q.getRequiredUnboundOutputs()) && Objects.equal(this.serviceTerm(), q.serviceTerm());
                }
            }
        };
    }
}

