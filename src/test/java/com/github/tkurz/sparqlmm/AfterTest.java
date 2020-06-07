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

public class AfterTest extends AbstractStardogTest {

    @Test
    public void testAfterFunction() throws IOException {

        final InputStream in = Resources.getResource("test1.ttl").openStream();
        connection.add().io().stream(in);

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?t1 ?t2 WHERE {" +
                        "   ?f1 rdfs:label ?t1." +
                        "   ?f2 rdfs:label ?t2." +
                        "   FILTER mm:after(?f1,?f2)" +
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

        Assert.assertTrue(list.size() == 11);

        for(BindingSet set : list) {
            String t1 = set.getBinding("t1").getValue().stringValue();
            String t2 = set.getBinding("t2").getValue().stringValue();

            Assert.assertEquals(t1.charAt(0),t2.charAt(0));
            Assert.assertTrue( Integer.valueOf(t1.charAt(2)) > Integer.valueOf(t2.charAt(2)) );
        }
    }
}
