package com.semantalytics.stardog.kibble.multimediafragments;

import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.io.RDFFormats;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StringFragmentTest extends AbstractStardogTest {

    private final String TEST9 = "/test9.ttl";
    private final String BASE_URI = "http://test.org/resource/";

    @Test
    public void testBoundingBoxFunction() throws IOException {

        final InputStream in = Resources.getResource("test9.ttl").openStream();
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();

        String query =
                "PREFIX oa: <http://www.w3.org/ns/oa#>" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                "SELECT ?v1 ?v2 (mm:boundingBox(?v1,?v2) AS ?box) WHERE {" +
                "   ?s1 oa:hasSource ?s;" +
                "       rdf:value ?v1;" +
                "       rdfs:label \"1_1\"." +
                "   ?s2 oa:hasSource ?s;" +
                "       rdf:value ?v2." +
                "   FILTER (?s1 != ?s2)" +
                "   FILTER mm:hasSpatialFragment(?v1)" +
                "   FILTER mm:hasSpatialFragment(?v2)" +
                "}";
        List<BindingSet> list;
        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            list = ImmutableList.copyOf(aResult);

            assertThat(list).hasSize(1);
            assertThat(list.get(0).literal("box").get().label()).isEqualTo("xywh=0,0,4,2");
        }
    }
}
