package com.github.tkurz.sparqlmm;

import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.fragments.spatial.SpatialFragment;
import com.github.tkurz.media.fragments.temporal.NPTFragment;
import com.google.common.io.Resources;
import com.semantalytics.stardog.function.multimediafragments.AbstractStardogTest;
import com.semantalytics.stardog.function.multimediafragments.Constants;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.SelectQueryResult;
import org.assertj.core.api.AbstractStringAssert;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class BoundingBoxTest extends AbstractStardogTest {

    @Test
    public void testBoundingBoxFunction() throws IOException {

        final InputStream in = Resources.getResource("test6.ttl").openStream();
        connection.add().io().stream(in);

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT (mm:boundingBox(?l1,?l2) AS ?box) WHERE {" +
                        "   <http://test.org/resource/fragment1> ma:locator ?l1." +
                        "   <http://test.org/resource/fragment2> ma:locator ?l2." +
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


        Assert.assertFalse(r.hasNext());

        String box = set.getBinding("box").getValue().stringValue();

        MediaFragmentURI uri = new MediaFragmentURI(box);

        SpatialFragment sfragment = uri.getMediaFragment().getSpatialFragment();

        Assert.assertEquals(0.0,sfragment.getX(),0);
        Assert.assertEquals(0.0,sfragment.getY(),0);
        Assert.assertEquals(75.0,sfragment.getWidth(),0);
        Assert.assertEquals(75.0,sfragment.getHeight(),0);

        NPTFragment tfragment = (NPTFragment) uri.getMediaFragment().getTemporalFragment();

        Assert.assertEquals(0.0,tfragment.getStart().getValue(),0);
        Assert.assertEquals(15.0,tfragment.getEnd().getValue(),0);

    }

}
