package com.semantalytics.stardog.kibble.multimediafragments;

import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.fragments.spatial.SpatialFragment;
import com.github.tkurz.media.fragments.temporal.NPTFragment;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.multimediafragments.Constants;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.io.RDFFormats;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class IntersectionTest extends AbstractStardogTest {

    @Test
    public void testIntersectionFunction() throws IOException, MediaFragmentURISyntaxException {

        final InputStream in = Resources.getResource("test6.ttl").openStream();
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT (mm:intersection(?l1,?l2) AS ?box) WHERE {" +
                        "   <http://test.org/resource/fragment1> ma:locator ?l1." +
                        "   <http://test.org/resource/fragment2> ma:locator ?l2." +
                        "}";
        List<BindingSet> list;
        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            assertThat(aResult).hasNext();


            BindingSet set = aResult.next();

            Assert.assertFalse(aResult.hasNext());

            String box = set.literal("box").get().label();

            MediaFragmentURI uri = new MediaFragmentURI(box);

            SpatialFragment sfragment = uri.getMediaFragment().getSpatialFragment();

            Assert.assertEquals(25.0, sfragment.getX(), 0);
            Assert.assertEquals(25.0, sfragment.getY(), 0);
            Assert.assertEquals(25.0, sfragment.getWidth(), 0);
            Assert.assertEquals(25.0, sfragment.getHeight(), 0);

            NPTFragment tfragment = (NPTFragment) uri.getMediaFragment().getTemporalFragment();

            Assert.assertEquals(5.0, tfragment.getStart().getValue(), 0);
            Assert.assertEquals(10.0, tfragment.getEnd().getValue(), 0);
        }

    }

}
