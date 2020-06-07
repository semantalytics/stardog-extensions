package com.github.tkurz.sparqlmm;

import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.google.common.io.Resources;
import com.semantalytics.stardog.function.multimediafragments.AbstractStardogTest;
import com.semantalytics.stardog.function.multimediafragments.Constants;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class TemporalContainsTest extends AbstractStardogTest {

    @Test
    public void testContainsFunction() throws IOException {

        final InputStream in = Resources.getResource("test1.ttl").openStream();
        connection.add().io().stream(in);

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?t1 ?t2 WHERE {" +
                        "   ?f1 rdfs:label ?t1." +
                        "   ?f2 rdfs:label ?t2." +
                        "   FILTER mm:temporalContains(?f1,?f2)" +
                        "} ORDER BY ?t1 ?t2";
        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            assertThat(assertStringLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            assertThat(aLiteral.label()).isEqualTo("Stard...");
            assertThat(aResult).isExhausted();
        }

        BindingSet set = r.next();
        Assert.assertEquals("2_4",set.getBinding("t1").getValue().stringValue());
        Assert.assertEquals("2_5",set.getBinding("t2").getValue().stringValue());

        Assert.assertFalse(r.hasNext());

        /*
        String query1 =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?t1 ?t2 WHERE {" +
                        "   ?f1 rdfs:label ?t1." +
                        "   ?f2 rdfs:label ?t2." +
                        "   FILTER mm:temporalContains(?f1,?f2,true)" +
                        "} ORDER BY ?t1 ?t2";
        TupleQuery q1 = connection.prepareTupleQuery(QueryLanguage.SPARQL,query1);
        TupleQueryResult r1 = q1.evaluate();

        Assert.assertTrue(r1.hasNext());

        int size = 0;
        while(r1.hasNext()) {
            size++;
            r1.next();
        }

        Assert.assertEquals(10, size);
        */
    }
}
