package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.stardog.plan.eval.ExecutionException;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static com.stardog.stark.Values.literal;
import static org.junit.Assert.*;

public class TestNeighbors extends AbstractStardogTest {

    private final String sparqlPrefix = GeoHashVocabulary.sparqlPrefix("geohash");

    @Test(expected = ExecutionException.class)
    public void tooManyResultsThrowsError() {

        final String aQueryStr = sparqlPrefix +
                " select * where { (?one ?two ?three ?four ?five) geohash:neighbors (\"gbsuv7ztqzpt\") }";

        final SelectQueryResult aResult = connection.select(aQueryStr).execute();
        try {
            fail("Should not have successfully executed");
        } finally {
            aResult.close();
        }
    }


    @Test(expected = ExecutionException.class)
    public void resultTermsWhichAreNotVariablesShouldBeAnError() {

        final String aQueryStr = sparqlPrefix +
                " select * where { (\"one\" \"two\" \"three\" \"four\") geohash:neighbors (\"gbsuv7ztqzpt\") }";

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
                " select * where { ?result geohash:neighbors (\"gbsuv7ztqzpt\" 5) }";


        final SelectQueryResult aResult = connection.select(aQueryStr).execute();

        try {
            fail("Should not have successfully executed");
        } finally {
            aResult.close();
        }
    }

    @Test(expected = ExecutionException.class)
    public void argCannotBeANonnumericLiteral() {
        final String aQueryStr = sparqlPrefix +
                " select * where { ?result geohash:neighbors (5) }";


        final SelectQueryResult aResult = connection.select(aQueryStr).execute();
        try {
            fail("Should not have successfully executed");
        } finally {
            aResult.close();
        }
    }

    @Test(expected = ExecutionException.class)
    public void argCannotBeAnIRI() {
        final String aQueryStr = sparqlPrefix +
                " select * where { ?result geohash:neighbors (<http://example.com>) }";


        final SelectQueryResult aResult = connection.select(aQueryStr).execute();
        try {
            fail("Should not have successfully executed");
        } finally {
            aResult.close();
        }
    }

    @Test
    public void argCannotBeABNode() {
        final String aQueryStr = sparqlPrefix +
                " select * where { (?top ?bottom ?left ?right) geohash:neighbors (_:bnode) }";

        try(final SelectQueryResult aResult = connection.select(aQueryStr).execute()) {
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void varInputWithNoResultsShouldProduceZeroResults() {
        final String aQueryStr = sparqlPrefix +
                " select * where { (?top ?bottom ?left ?right) geohash:neighbors (?input) }";


        final SelectQueryResult aResult = connection.select(aQueryStr).execute();
        try {
            assertFalse(aResult.hasNext());
        } finally {
            aResult.close();
        }
    }

    @Test
    public void decodeWithVarInput() {
        final String aQueryStr = sparqlPrefix +
                " select * where { (?top ?bottom ?left ?right) geohash:neighbors (?in) . values ?in { \"gbsuv7ztqzpt\"} }";

        BindingSet aBindingSet;

        try(final SelectQueryResult aResult = connection.select(aQueryStr).execute()) {

            aBindingSet = aResult.next();

            assertEquals(literal("gbsuv7ztqzpw"), aBindingSet.value("top").get());
            assertEquals(literal("gbsuv7ztqzps"), aBindingSet.value("bottom").get());
            assertEquals(literal("gbsuv7ztqzpm"), aBindingSet.value("left").get());
            assertEquals(literal("gbsuv7ztqzpv"), aBindingSet.value("right").get());
            //assertEquals(literal(0, StardogValueFactory.Datatype.INTEGER), aBindingSet.value("idx"));

            //aBindingSet = aResult.next();

            //assertEquals(literal("dog"), aBindingSet.value("result"));
            //assertEquals(literal(1, StardogValueFactory.Datatype.INTEGER), aBindingSet.value("idx"));

            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void neighborsWithConstInput() {
        final String aQueryStr = sparqlPrefix +
                " select * where { (?top ?bottom ?left ?right) geohash:neighbors (\"gbsuv7ztqzpt\") }";

        try(final SelectQueryResult aResult = connection.select(aQueryStr).execute()) {

            final BindingSet aBindingSet = aResult.next();

            assertEquals("gbsuv7ztqzpw", ((Literal)aBindingSet.value("top").get()).label());
            assertEquals("gbsuv7ztqzps", ((Literal)aBindingSet.value("bottom").get()).label());
            assertEquals("gbsuv7ztqzpm", ((Literal)aBindingSet.value("left").get()).label());
            assertEquals("gbsuv7ztqzpv", ((Literal)aBindingSet.value("right").get()).label());

            //aBindingSet = aResult.next();

            //assertEquals(literal("dog"), aBindingSet.value("result"));
            //assertEquals(literal(1, StardogValueFactory.Datatype.INTEGER), aBindingSet.value("idx"));

            assertFalse("Should have no more results", aResult.hasNext());
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
                "select * where { (?top ?bottom ?right ?left) geohash:neighbors (\"gbsuv7ztqzpt\") }";

        assertTrue(connection.select(aQueryStr).explain().contains("geohashneighbors("));
    }
}
