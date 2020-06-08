package com.semantalytics.stardog.kibble.multimediafragments;

import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.fragments.spatial.SpatialFragment;
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

public class SpatialIntersectionTest extends AbstractStardogTest {


    @Test
    public void testIntersectionFunctionPercent() throws IOException, MediaFragmentURISyntaxException {

        final InputStream in = Resources.getResource("test6.ttl").openStream();
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT (mm:spatialIntersection(?l1,?l2) AS ?box) WHERE {" +
                        "   <http://test.org/resource/fragment1> ma:locator ?l1." +
                        "   <http://test.org/resource/fragment2> ma:locator ?l2." +
                        "}";
        List<BindingSet> list;
        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            assertThat(aResult).hasNext();



            String box = aResult.next().literal("box").get().label();

            SpatialFragment fragment = (new MediaFragmentURI(box)).getMediaFragment().getSpatialFragment();

            Assert.assertEquals(25.0, fragment.getX(), 0);
            Assert.assertEquals(25.0, fragment.getY(), 0);
            Assert.assertEquals(25.0, fragment.getWidth(), 0);
            Assert.assertEquals(25.0, fragment.getHeight(), 0);
            assertThat(aResult).isExhausted();
        }
    }
}
