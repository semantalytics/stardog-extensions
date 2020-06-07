package com.github.tkurz.sparqlmm;

import com.google.common.io.Resources;
import com.semantalytics.stardog.function.multimediafragments.AbstractStardogTest;
import com.semantalytics.stardog.function.multimediafragments.Constants;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Assert;
import org.junit.Test;
import org.openrdf.query.TupleQuery;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class TouchesTest extends AbstractStardogTest {

    @Test
    public void testTouchesFunction() throws IOException {

        final InputStream in = Resources.getResource("test3.ttl").openStream();
        connection.add().io().stream(in);

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?t1 ?t2 WHERE {" +
                        "   ?f1 rdfs:label ?t1." +
                        "   ?f2 rdfs:label ?t2." +
                        "   FILTER mm:touches(?f1,?f2)" +
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

        Assert.assertTrue(list.size() == 4);

        Assert.assertEquals("1_2",list.get(0).getBinding("t1").getValue().stringValue());
        Assert.assertEquals("1_3",list.get(0).getBinding("t2").getValue().stringValue());
        Assert.assertEquals("1_3",list.get(1).getBinding("t1").getValue().stringValue());
        Assert.assertEquals("1_2",list.get(1).getBinding("t2").getValue().stringValue());

        Assert.assertEquals("2_1",list.get(2).getBinding("t1").getValue().stringValue());
        Assert.assertEquals("2_3",list.get(2).getBinding("t2").getValue().stringValue());
        Assert.assertEquals("2_3",list.get(3).getBinding("t1").getValue().stringValue());
        Assert.assertEquals("2_1",list.get(3).getBinding("t2").getValue().stringValue());
    }
}
