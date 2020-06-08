package com.semantalytics.stardog.kibble.multimediafragments;

import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.fragments.spatial.SpatialFragment;
import com.github.tkurz.media.fragments.temporal.NPTFragment;
import com.google.common.io.Resources;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.io.RDFFormats;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BoundingBoxTest extends AbstractStardogTest {

    @Test
    public void testBoundingBoxFunction() throws IOException, MediaFragmentURISyntaxException {

        final InputStream in = Resources.getResource("test6.ttl").openStream();
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT (mm:boundingBox(?l1,?l2) AS ?box) WHERE {" +
                        "   <http://test.org/resource/fragment1> ma:locator ?l1." +
                        "   <http://test.org/resource/fragment2> ma:locator ?l2." +
                        "}";
        try (final SelectQueryResult aResult = connection.select(query).execute()) {

        String box = aResult.next().literal("box").get().label();

        MediaFragmentURI uri = new MediaFragmentURI(box);

        SpatialFragment sfragment = uri.getMediaFragment().getSpatialFragment();

        Assert.assertEquals(0.0,sfragment.getX(),0);
        Assert.assertEquals(0.0,sfragment.getY(),0);
        Assert.assertEquals(75.0,sfragment.getWidth(),0);
        Assert.assertEquals(75.0,sfragment.getHeight(),0);

        NPTFragment tfragment = (NPTFragment) uri.getMediaFragment().getTemporalFragment();

        Assert.assertEquals(0.0,tfragment.getStart().getValue(),0);
        Assert.assertEquals(15.0,tfragment.getEnd().getValue(),0);

        assertThat(aResult).isExhausted();
        }
    }

}
