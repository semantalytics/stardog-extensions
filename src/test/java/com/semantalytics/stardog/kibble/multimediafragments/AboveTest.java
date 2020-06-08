package com.semantalytics.stardog.kibble.multimediafragments;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.io.RDFFormat;
import com.stardog.stark.io.RDFFormats;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class AboveTest extends AbstractStardogTest {

    @Test
    public void testIsAboveFunction() throws IOException {

        final InputStream in = Resources.getResource("test4.ttl").openStream();
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?t1 ?t2 (mm:above(?f1,?f2) AS ?test) WHERE {" +
                        "   ?f1 rdfs:label \"1_1\";" +
                        "       rdfs:label ?t1." +
                        "   ?f2 a ma:MediaFragment;" +
                        "       rdfs:label ?t2." +
                        "} ORDER BY ?t2";

        List<BindingSet> list;

        try (final SelectQueryResult aResult = connection.select(query).execute()) {


            list = ImmutableList.copyOf(aResult);

        Assert.assertTrue(list.size() == 4);

        Assert.assertEquals(false,Literal.booleanValue(list.get(0).literal("test").get()));
        Assert.assertEquals(false,Literal.booleanValue(list.get(1).literal("test").get()));
        Assert.assertEquals(true,Literal.booleanValue(list.get(2).literal("test").get()));
        Assert.assertEquals(true,Literal.booleanValue(list.get(3).literal("test").get()));

    }
    }
}
