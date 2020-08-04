package com.semantalytics.stardog.kibble.iri;

import com.complexible.common.base.Strings2;
import com.complexible.stardog.plan.PlanNode;
import com.complexible.stardog.plan.PlanNodes;
import com.complexible.stardog.plan.PlanVarInfo;
import com.complexible.stardog.plan.eval.ExecutionContext;
import com.complexible.stardog.plan.eval.operator.Operator;
import com.complexible.stardog.plan.eval.operator.OperatorException;
import com.complexible.stardog.plan.eval.operator.SolutionIterator;
import com.complexible.stardog.plan.eval.service.ResultsToSolutionIterator;
import com.complexible.stardog.plan.eval.service.ServiceQuery;
import com.complexible.stardog.plan.eval.service.SingleQueryService;
import com.complexible.stardog.plan.util.ExplainRenderer;
import com.complexible.stardog.plan.util.QueryTermRenderer;
import com.complexible.stardog.plan.util.SPARQLRenderer;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.stardog.stark.IRI;
import com.stardog.stark.query.SelectQueryResult;
import com.stardog.stark.query.io.QueryResultFormat;
import com.stardog.stark.query.io.QueryResultFormats;
import com.stardog.stark.query.io.QueryResultParsers;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.InputStream;
import java.util.function.Function;
import java.util.function.IntFunction;

public class IriService extends SingleQueryService {
    public static final String EXAMPLE_SCHEME = "example://";

    // we will use just the SPARQL/XML result format
    private static final QueryResultFormat FORMAT = QueryResultFormats.XML;

    /**
     * Returns whether or not this instance can a SERVICE query against the given IRI.
     *
     * @param theIRI    the IRI of the service
     * @return          {@code true} if this instance can {@code #evaluate} the service, {@code false}  otherwise
     */
    @Override
    public boolean canEvaluate(final IRI theIRI) {
        return theIRI.toString().startsWith(EXAMPLE_SCHEME);
    }

    @Override
    public PlanNode translate(IRI iri, PlanNode body, IntFunction<String> varNames, final Function<String, Integer> varAllocator, boolean silent) {
        return PlanNodes.service()
                .query(createQuery(iri, body))
                .silent(silent)
                .build();
    }

    /**
     * Create a query which evaluates the service over HTTP.
     *
     * @param theIRI the IRI of the service call
     * @param thePlanNode the SPARQL body of the service call
     * @return a {@link ServiceQuery} to evaluate the service call
     */
    @Override
    public ServiceQuery createQuery(final IRI theIRI, final PlanNode thePlanNode) {
        return new ServiceQuery(theIRI) {
            @Override
            public SolutionIterator evaluate(final ExecutionContext theContext, final Operator theOperator, final PlanVarInfo theVarInfo) throws OperatorException {
                // convert the service IRI to an http:// IRI
                String aServiceIRI = serviceTerm().getValue().stringValue().replace(EXAMPLE_SCHEME, "http://");

                // retrieve the SPARQL string for the service
                String aQueryStr = SPARQLRenderer.renderSelect(thePlanNode, theVarInfo, true);

                // create an HTTP request
                final HttpGet aGet = createRequest(aServiceIRI, aQueryStr);

                // execute the HTTP request
                try (CloseableHttpClient aClient = HttpClientBuilder.create().build();
                     CloseableHttpResponse aResponse = aClient.execute(aGet)) {

                    final int aResponseCode = aResponse.getStatusLine().getStatusCode();

                    // check if the request failed
                    if (aResponseCode != HttpStatus.SC_OK) {
                        throw new OperatorException("SERVICE evaluation returned HTTP response code " + aResponseCode);
                    }

                    try (InputStream aServiceResults = aResponse.getEntity().getContent()) {
                        // parse the query results
                        SelectQueryResult aSelectQueryResult = QueryResultParsers.readSelect(aServiceResults, FORMAT);

                        // convert the results to a solution iterator so it can be processed by the execution engine, e.g. joined with the rest of the query results
                        return new ResultsToSolutionIterator(theContext, aSelectQueryResult, theVarInfo, thePlanNode.getAllVars());
                    }
                }
                catch (Exception e) {
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
                    ServiceQuery q = (ServiceQuery)obj;
                    return this.getRequiredInputBindings().equals(q.getRequiredInputBindings()) && this.getRequiredUnboundOutputs().equals(q.getRequiredUnboundOutputs()) && Objects.equal(this.serviceTerm(), q.serviceTerm());
                }
            }
        };
    }

    private HttpGet createRequest(final String theService, final String theQueryStr) {
        final String aURLStr = theService + "?query=" + Strings2.urlEncode(theQueryStr);

        HttpGet aGet = new HttpGet(aURLStr);

        aGet.setHeader("Accept", FORMAT.defaultMimeType());

        return aGet;
    }
}
