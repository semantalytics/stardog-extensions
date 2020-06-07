package com.github.tkurz.sparqlmm;

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
import java.util.ArrayList;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class DurationTest extends AbstractStardogTest {


    @Test
    public void testGetDuration() throws IOException {

        final InputStream in = Resources.getResource("test8.ttl").openStream();
        connection.add().io().stream(in);

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?duration WHERE {" +
                        "   ?f a ma:MediaFragment." +
                        "   BIND (mm:duration(?f) AS ?duration)" +
                        "} ORDER BY DESC(?duration)";

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

        Assert.assertTrue(list.size() == 3);
        Assert.assertEquals("3.0",list.get(0).getBinding("duration").getValue().stringValue());
        Assert.assertEquals("1.0",list.get(1).getBinding("duration").getValue().stringValue());
        Assert.assertNull(list.get(2).getBinding("duration"));
    }
}
