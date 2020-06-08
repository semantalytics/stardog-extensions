package com.semantalytics.stardog.kibble.multimediafragments;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.io.RDFFormats;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DurationTest extends AbstractStardogTest {


    @Test
    public void testGetDuration() throws IOException {

        final InputStream in = Resources.getResource("test8.ttl").openStream();
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?duration WHERE {" +
                        "   ?f a ma:MediaFragment." +
                        "   BIND (mm:duration(?f) AS ?duration)" +
                        "} ORDER BY DESC(?duration)";

        final List<BindingSet> list;

        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            list = ImmutableList.copyOf(aResult);

            Assert.assertTrue(list.size() == 3);
            assertThat(Literal.floatValue(list.get(0).literal("duration").get())).isEqualTo(3.0f);
            assertThat(Literal.floatValue(list.get(1).literal("duration").get())).isEqualTo(1.0f);
            Assert.assertNull(list.get(2).literal("duration").get());
        }
    }
}
