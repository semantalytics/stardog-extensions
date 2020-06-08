package com.semantalytics.stardog.kibble.multimediafragments;

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

public class ToPixelTest extends AbstractStardogTest {

    @Test
    public void testTransformers() throws IOException {

        final InputStream in = Resources.getResource("test10.ttl").openStream();
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT DISTINCT ?x WHERE {" +
                        "   ?x ma:hasFragment ?f1, ?f2." +
                        "   ?x ma:width ?w." +
                        "   ?x ma:height ?h." +
                        "   BIND (mm:toPixel(?f1,?w,?h) AS ?pf1)" +
                        "   BIND (mm:toPixel(?f2,?w,?h) AS ?pf2)" +
                        "   FILTER mm:spatialEquals(?pf1,?pf2)" +
                        "   FILTER(?f1 != ?f2)" +
                        "}";

        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            List<BindingSet> list = ImmutableList.copyOf(aResult);
            assertThat(list).hasSize(1);
        }
    }
}
