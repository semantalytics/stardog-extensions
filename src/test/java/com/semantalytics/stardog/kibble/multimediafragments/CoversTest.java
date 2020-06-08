package com.semantalytics.stardog.kibble.multimediafragments;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.io.RDFFormats;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CoversTest extends AbstractStardogTest {

    @Test
    public void testCoversFunction() throws IOException {

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
                        "   FILTER mm:covers(?f1,?f2)" +
                        "   FILTER (?f1 != ?f2)" +
                        "} ORDER BY ?t1 ?t2";

        final List<BindingSet> list;

        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            list = ImmutableList.copyOf(aResult);

        Assert.assertTrue(list.size() == 1);

        String t1 = list.get(0).literal("t1").get().label();
        String t2 = list.get(0).literal("t2").get().label();

        Assert.assertEquals("2_1",t1);
        Assert.assertEquals("2_2",t2);

        }

        String query2 =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?t1 ?t2 WHERE {" +
                        "   ?f1 rdfs:label ?t1." +
                        "   ?f2 rdfs:label ?t2." +
                        "   FILTER mm:covers(?f1,?f2)" +
                        "} ORDER BY ?t1 ?t2";


        SelectQueryResult r2 = connection.select(query2).execute();

        ArrayList<BindingSet> list2 = new ArrayList<>();
        while(r2.hasNext()) {
            list2.add(r2.next());
        }

        Assert.assertTrue(list2.size() == 8);
    }
}
