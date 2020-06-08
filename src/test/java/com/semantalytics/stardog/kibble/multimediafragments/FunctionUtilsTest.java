package com.semantalytics.stardog.kibble.multimediafragments;

import com.semantalytics.stardog.kibble.multimediafragments.utils.Entities;
import com.stardog.stark.Value;
import com.stardog.stark.Values;
import org.junit.Assert;
import org.junit.Test;

public class FunctionUtilsTest {

    @Test
    public void testUriComparator() {

        Value v0 = Values.literal("456");
        Value v1 = Values.literal("123");
        Value v2 = Values.literal("312");
        Value v3 = Values.iri("http://example.org/123#t=1,2");
        Value v4 = Values.iri("http://example.org/321#t=1,2");
        Value v5 = Values.iri("http://example.org/321#t=3,4");
        Value v6 = Values.iri("http://example.org/321#t=4,5");

        Assert.assertTrue(Entities.haveComparable(v1,v2));
        Assert.assertTrue(Entities.haveComparable(v0,v1,v2));
        Assert.assertTrue(Entities.haveComparable(v4,v5));
        Assert.assertTrue(Entities.haveComparable(v4,v5,v6));

        Assert.assertFalse(Entities.haveComparable(v3, v4));
        Assert.assertFalse(Entities.haveComparable(v1, v5));

    }

}
