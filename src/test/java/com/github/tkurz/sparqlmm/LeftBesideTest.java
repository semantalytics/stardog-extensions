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
import java.util.ArrayList;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class LeftBesideTest extends AbstractStardogTest {

    @Test
    public void testLeftBesideFunction() throws IOException {

        final InputStream in = Resources.getResource("test4.ttl").openStream();
        connection.add().io().stream(in);

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?t1 ?t2 (mm:leftBeside(?f1,?f2) AS ?test) WHERE {" +
                        "   ?f1 rdfs:label \"1_1\";" +
                        "       rdfs:label ?t1." +
                        "   ?f2 a ma:MediaFragment;" +
                        "       rdfs:label ?t2." +
                        "} ORDER BY ?t2";
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

        Assert.assertEquals("false",list.get(0).getBinding("test").getValue().stringValue());
        Assert.assertEquals("true",list.get(1).getBinding("test").getValue().stringValue());
        Assert.assertEquals("false",list.get(2).getBinding("test").getValue().stringValue());
        Assert.assertEquals("true",list.get(3).getBinding("test").getValue().stringValue());
    }

}
