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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class IntersectsTest extends AbstractStardogTest {

    @Test
    public void testIntersectsFunction() throws IOException {

        final InputStream in = Resources.getResource("test3.ttl").openStream();
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?t1 ?t2 WHERE {" +
                        "   ?f1 rdfs:label ?t1." +
                        "   ?f2 rdfs:label ?t2." +
                        "   FILTER mm:intersects(?f1,?f2)" +
                        "   FILTER (?f1 != ?f2)" +
                        "} ORDER BY ?t1 ?t2";
        List<BindingSet> list;
        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            list = ImmutableList.copyOf(aResult);

        Assert.assertTrue(list.size() == 4);

        Assert.assertEquals("1_1",list.get(0).literal("t1").get().label());
        Assert.assertEquals("1_2",list.get(0).literal("t2").get().label());
        Assert.assertEquals("1_2",list.get(1).literal("t1").get().label());
        Assert.assertEquals("1_1",list.get(1).literal("t2").get().label());

        Assert.assertEquals("2_1",list.get(2).literal("t1").get().label());
        Assert.assertEquals("2_2",list.get(2).literal("t2").get().label());
        Assert.assertEquals("2_2",list.get(3).literal("t1").get().label());
        Assert.assertEquals("2_1",list.get(3).literal("t2").get().label());

        }

        String query2 =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?t1 ?t2 WHERE {" +
                        "   ?f1 rdfs:label ?t1." +
                        "   ?f2 rdfs:label ?t2." +
                        "   FILTER mm:intersects(?f1,?f2)" +
                        "} ORDER BY ?t1 ?t2";
        SelectQueryResult r2 = connection.select(query2).execute();

        List<BindingSet> list2 = ImmutableList.copyOf(r2);
        while(r2.hasNext()) {
            list2.add(r2.next());
        }

        Assert.assertTrue(list2.size() == 11);
    }

}
