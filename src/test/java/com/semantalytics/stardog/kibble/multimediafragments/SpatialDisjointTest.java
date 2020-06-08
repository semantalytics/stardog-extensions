package com.semantalytics.stardog.kibble.multimediafragments;

import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
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

public class SpatialDisjointTest extends AbstractStardogTest {


    @Test
    public void testDisjointFunction() throws IOException {

        final InputStream in = Resources.getResource("test3.ttl").openStream();
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?t1 ?t2 WHERE {" +
                        "   <http://test.org/resource/video1> ma:hasFragment ?f1, ?f2." +
                        "   ?f1 rdfs:label ?t1." +
                        "   ?f2 rdfs:label ?t2." +
                        "   FILTER mm:spatialDisjoint(?f1,?f2)" +
                        "} ORDER BY ?t1 ?t2";

        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            List<BindingSet> list = ImmutableList.copyOf(aResult);

            assertThat(list).hasSize(10);

            for (BindingSet set : list) {
                Assert.assertFalse(
                        set.literal("t1").get().label().equals("1_1")
                                && set.literal("t2").get().label().equals("1_2")
                );

                Assert.assertFalse(
                        set.literal("t1").get().label().equals("1_2")
                                && set.literal("t2").get().label().equals("1_1")
                );
            }
        }
    }
}
