package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.stardog.plan.eval.ExecutionException;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Datatype;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.QueryResults;
import com.stardog.stark.query.SelectQueryResult;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestNeighbors extends AbstractStardogTest {

    private final String sparqlPrefix = GeoHashVocabulary.sparqlPrefix("geohash");

    @Test(expected = ExecutionException.class)
    public void resultTermsWhichAreNotVariablesShouldBeAnError() {

        final String aQueryStr = sparqlPrefix +
                " select * where { SERVICE geohash:neighbors { [] geohash:top 'no literals allowed' ; geohash:bottom ?x ; geohash:hash \"gbsuv7ztqzpt\" } }";

        final SelectQueryResult aResult = connection.select(aQueryStr).execute();
        try {
            fail("Should not have successfully executed");
        } finally {
            aResult.close();
        }
    }

    @Test(expected = ExecutionException.class)
    public void tooManyInputsThrowsError() {
        final String aQueryStr = sparqlPrefix +
                                 " select * where { SERVICE geohash:neighbors { [] geohash:top ?top ; geohash:bottom ?bottom ; geohash:left ?left ; geohash:right ?right ; " +
                                 "geohash:hash \"gbsuv7ztqzpt\" ; geohash:unrecognized 'xyz' } }";


        final SelectQueryResult aResult = connection.select(aQueryStr).execute();

        try {
            fail("Should not have successfully executed");
        } finally {
            aResult.close();
        }
    }

    @Test(expected = ExecutionException.class)
    public void argCannotBeANonStringLiteral() {
        final String aQueryStr = sparqlPrefix +
                " select * where { SERVICE geohash:neighbors { [] geohash:top 'no literals allowed' ; geohash:bottom ?x ; geohash:hash 5 } }";


        final SelectQueryResult aResult = connection.select(aQueryStr).execute();
        try {
            fail("Should not have successfully executed");
        } finally {
            aResult.close();
        }
    }

    @Test(expected = ExecutionException.class)
    public void argCannotBeAnIRI() {
        // I don't know if it would be more idiomatic to bind ValueOrError.Error to output variables. this is what would happen with a function
        final String aQueryStr = sparqlPrefix +
                " select * where { SERVICE geohash:neighbors { [] geohash:top ?top ; geohash:bottom ?bottom ; geohash:hash <http://example.com> } }";


        final SelectQueryResult aResult = connection.select(aQueryStr).execute();
        try {
            fail("Should not have successfully executed");
        } finally {
            aResult.close();
        }
    }

    @Test(expected = ExecutionException.class)
    public void argCannotBeABNode() {
        // Bnodes are anonymous vars, so we get the same constraint failure as not binding the input var
        final String aQueryStr = sparqlPrefix +
                " select * where { SERVICE geohash:neighbors { [] geohash:top ?top ; geohash:bottom ?bottom ; geohash:hash _:bnode }  }";

        try(final SelectQueryResult aResult = connection.select(aQueryStr).execute()) {
            QueryResults.consume(aResult);
        }
    }

    @Test
    public void varInputWithNoResultsShouldProduceZeroResults() {
        // this will throw failure from service constraint rewriter
        final String aQueryStr = sparqlPrefix +
                " select * where { SERVICE geohash:neighbors { [] geohash:top ?top ; geohash:bottom ?bottom ; geohash:hash ?input } }";


        try (SelectQueryResult aResult = connection.select(aQueryStr).execute()) {
            aResult.hasNext();
            fail("Expected exception");
        }
        catch (Exception ex) {
            Assert.assertThat(ex.getMessage(), Matchers.containsString("Unable to bind: input"));
        }
    }

    @Test
    public void decodeWithVarInput() {
        // this is not a great test because values will be inlined before execution and will not use the iterator branch of the service query eval method
        final String aQueryStr = sparqlPrefix +
                " select * where { SERVICE geohash:neighbors { [] geohash:top ?top ; geohash:bottom ?bottom ; geohash:left ?left ; geohash:right ?right ; geohash:hash \"gbsuv7ztqzpt\" } values ?in { \"gbsuv7ztqzpt\"} }";

        BindingSet aBindingSet;

        try(final SelectQueryResult theResult = connection.select(aQueryStr).execute()) {

            aBindingSet = theResult.next();

            assertEquals("gbsuv7ztqzpm", (((Literal)aBindingSet.value("left").get())).label());
            assertEquals("gbsuv7ztqzpv", (((Literal)aBindingSet.value("right").get())).label());
            assertEquals("gbsuv7ztqzpw", (((Literal)aBindingSet.value("top").get())).label());
            assertEquals("gbsuv7ztqzps", (((Literal)aBindingSet.value("bottom").get())).label());

            assertThat(theResult).isExhausted();
        }
    }

    @Test
    public void decodeWithConstInput() {
        final String aQueryStr = sparqlPrefix +
                " select * where { SERVICE geohash:neighbors { [] geohash:top ?top ; geohash:bottom ?bottom ; geohash:left ?left ; geohash:right ?right ; geohash:hash \"gbsuv7ztqzpt\" } }";

        BindingSet aBindingSet;

        try(final SelectQueryResult theResult = connection.select(aQueryStr).execute()) {

            aBindingSet = theResult.next();

            assertEquals("gbsuv7ztqzpm", ((Literal)aBindingSet.value("left").get()).label());
            assertEquals("gbsuv7ztqzpv", ((Literal)aBindingSet.value("right").get()).label());
            assertEquals("gbsuv7ztqzpw", ((Literal)aBindingSet.value("top").get()).label());
            assertEquals("gbsuv7ztqzps", ((Literal)aBindingSet.value("bottom").get()).label());

            //aBindingSet = aResult.next();

            //assertEquals(literal("dog"), aBindingSet.getValue("result"));
            //assertEquals(literal(1, StardogValueFactory.Datatype.INTEGER), aBindingSet.getValue("idx"));

            assertThat(theResult).isExhausted();
        }
    }

    /*
    @Test
    public void costAndCardinalityShouldBeCorrect() throws Exception {
        final String aQueryStr = GeoHashVocabulary.sparqlPrefix("geohash") +
            "select * where { (?result ?idx) geohash:neighbors (\"star\u001fdog\") }";

        Optional<PlanNode> aResult = PlanNodes.find(new QueryParserImpl().parseQuery(aQueryStr, Namespaces.STARDOG).getNode(),
                                                    PlanNodes.is(ArrayPropertyFunction.ArrayPlanNode.class));

        assertTrue(aResult.isPresent());

        ArrayPropertyFunction.ArrayPlanNode aNode = (ArrayPropertyFunction.ArrayPlanNode) aResult.get();

        new ArrayPropertyFunction().estimate(aNode);

        assertEquals(5d, aNode.getCost(), .00001);

        assertEquals(Accuracy.ACCURATE, aNode.getCardinality().accuracy());
        assertEquals(5d, aNode.getCardinality().value(), .00001);
    }

    @Test
    public void costAndCardinalityShouldBeCorrectWithArg() throws Exception {

        final String aQueryStr = GeoHashVocabulary.sparqlPrefix("geohash") +
            "select * where { (?result ?idx) geohash:neighbors (?in) . values ?in { \"star\u001fdog"} }";

        Optional<PlanNode> aResult = PlanNodes.find(new QueryParserImpl().parseQuery(aQueryStr, Namespaces.STARDOG).getNode(),
                                                  PlanNodes.is(ArrayPropertyFunction.ArrayPlanNode.class));

        assertTrue(aResult.isPresent());

        ArrayPropertyFunction.ArrayPlanNode aNode = (ArrayPropertyFunction.ArrayPlanNode) aResult.get();

        aNode.getArg().setCardinality(Cardinality.of(3, Accuracy.ACCURATE));
        aNode.getArg().setCost(3);

        new ArrayPropertyFunction().estimate(aNode);

        assertEquals(18d, aNode.getCost(), .00001);

        assertEquals(Accuracy.UNKNOWN, aNode.getCardinality().accuracy());
        assertEquals(15d, aNode.getCardinality().value(), .00001);
    }

*/
    @Test
    public void shouldRenderACustomExplanation() {

        final String aQueryStr = GeoHashVocabulary.sparqlPrefix("geohash") +
                "select * where { SERVICE geohash:neighbors { [] geohash:top ?top ; geohash:bottom ?bottom ; geohash:hash \"gbsuv7ztqzpt\" } }";

        assertTrue(connection.select(aQueryStr).explain().contains("geohash:neighbors("));
    }
}
