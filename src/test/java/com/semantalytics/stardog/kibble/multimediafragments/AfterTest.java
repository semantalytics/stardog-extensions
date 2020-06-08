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
import java.util.List;

public class AfterTest extends AbstractStardogTest {

    @Test
    public void testAfterFunction() throws IOException {

        final InputStream in = Resources.getResource("test1.ttl").openStream();
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?t1 ?t2 WHERE {" +
                        "   ?f1 rdfs:label ?t1." +
                        "   ?f2 rdfs:label ?t2." +
                        "   FILTER mm:after(?f1,?f2)" +
                        "} ORDER BY ?t1 ?t2";

        final List<BindingSet> list;
        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            list =  ImmutableList.copyOf(aResult);

        Assert.assertTrue(list.size() == 11);

        for(BindingSet set : list) {
            String t1 = set.literal("t1").get().label();
            String t2 = set.literal("t2").get().label();

            Assert.assertEquals(t1.charAt(0),t2.charAt(0));
            Assert.assertTrue( Integer.valueOf(t1.charAt(2)) > Integer.valueOf(t2.charAt(2)) );
        }
        }
    }
}
