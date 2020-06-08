package com.semantalytics.stardog.kibble.multimediafragments;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.multimediafragments.Constants;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.io.RDFFormats;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.io.InputStream;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class HasSpatialFragmentTest extends AbstractStardogTest {

    private final String TEST8 = "/test8.ttl";
    private final String TEST10 = "/test10.ttl";
    private final String BASE_URI = "http://test.org/resource/";

    private void importFile(String filename) {
        InputStream in = this.getClass().getResourceAsStream(filename);
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();
    }

    @Test
    public void testIsSpatialFragmentFunction() {

        importFile(TEST8);

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?result WHERE {" +
                        "   ?f a ma:MediaFragment; rdfs:label ?l. " +
                        "   FILTER mm:hasSpatialFragment(?f)" +
                        "} ORDER BY ?l";

        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            assertThat(assertStringLiteral(aValue));
            final Literal aLiteral = ((Literal) aValue);
            assertThat(aLiteral.label()).isEqualTo("1_1");

            assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue2 = aResult.next().value("result");
            assertThat(aPossibleValue2).isPresent();
            final Value aValue2 = aPossibleValue2.get();
            assertThat(assertStringLiteral(aValue2));
            final Literal aLiteral2 = ((Literal) aValue2);
            assertThat(aLiteral2.label()).isEqualTo("1_3");

            assertThat(aResult).isExhausted();
        }
    }
}
