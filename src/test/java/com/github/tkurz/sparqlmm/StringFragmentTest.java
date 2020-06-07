package com.github.tkurz.sparqlmm;

import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.google.common.io.Resources;
import com.semantalytics.stardog.function.multimediafragments.AbstractStardogTest;
import com.semantalytics.stardog.function.multimediafragments.Constants;
import com.semantalytics.stardog.function.multimediafragments.HasSpatialFragment;
import com.semantalytics.stardog.function.multimediafragments.BoundingBox;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;
import org.openrdf.repository.RepositoryConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class StringFragmentTest extends AbstractStardogTest {

    private final String TEST9 = "/test9.ttl";
    private final String BASE_URI = "http://test.org/resource/";

    @Test
    public void testBoundingBoxFunction() throws IOException {

        final InputStream in = Resources.getResource("test9.ttl").openStream();
        connection.add().io().stream(in);

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
        Assert.assertEquals("xywh=0,0,4,2",list.get(0).getBinding("box").getValue().stringValue());
    }
}
