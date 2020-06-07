package com.github.tkurz.sparqlmm;

import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.fragments.temporal.NPTFragment;
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

public class TemporalIntersectionTest extends AbstractStardogTest {

    @Test
    public void testIntersection() throws IOException {
        final InputStream in = Resources.getResource("test1.ttl").openStream();
        connection.add().io().stream(in);

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?f1 ?f2 (mm:temporalIntersection(?f1,?f2) AS ?box) WHERE {" +
                        "   ?f1 rdfs:label \"1_4\"." +
                        "   ?f2 rdfs:label \"1_3\"." +
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

        for(BindingSet set : list) {
            String box = set.getBinding("box").getValue().stringValue();

            NPTFragment fragment = (NPTFragment)(new MediaFragmentURI(box)).getMediaFragment().getTemporalFragment();

            Assert.assertEquals(20.0,fragment.getStart().getValue(),0);
            Assert.assertEquals(25.0,fragment.getEnd().getValue(),0);
        }
    }
}
