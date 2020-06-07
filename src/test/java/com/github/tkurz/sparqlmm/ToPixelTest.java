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

public class ToPixelTest extends AbstractStardogTest {

    @Test
    public void testTransformers() throws IOException {

        final InputStream in = Resources.getResource("test10.ttl").openStream();
        connection.add().io().stream(in);

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT DISTINCT ?x WHERE {" +
                        "   ?x ma:hasFragment ?f1, ?f2." +
                        "   ?x ma:width ?w." +
                        "   ?x ma:height ?h." +
                        "   BIND (mm:toPixel(?f1,?w,?h) AS ?pf1)" +
                        "   BIND (mm:toPixel(?f2,?w,?h) AS ?pf2)" +
                        "   FILTER mm:spatialEquals(?pf1,?pf2)" +
                        "   FILTER(?f1 != ?f2)" +
                        "}";
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
        Assert.assertTrue(list.size() == 1);
    }
}
