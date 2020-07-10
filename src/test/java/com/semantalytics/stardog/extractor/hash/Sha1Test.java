package com.semantalytics.stardog.extractor.hash;

import com.complexible.stardog.docs.BitesConnection;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.query.SelectQueryResult;
import org.apache.commons.collections4.IterableUtils;
import org.junit.*;

import java.io.InputStream;

import static org.junit.Assert.*;

public class Sha1Test extends AbstractStardogTest {

    @Test
    public void testAbbreviate() throws Exception {

        BitesConnection aDocsConn = connection.as(BitesConnection.class);

        InputStream docInputStream = Resources.getResource("input.pdf").toURI().toURL().openStream();

        IRI aDocIri = aDocsConn.putDocument("input.pdf", docInputStream, ImmutableList.of("Sha1"), IterableUtils.emptyIterable());

        String aQuery = "select ?sha1 { graph ?doc { ?doc <http://stardog.semantalytics.com/2019/11/ns/hash/sha1> ?sha1 } }";
        try(SelectQueryResult aRes = connection.select(aQuery).parameter("sha1", aDocIri).execute()) {
            int aWordCount = Literal.intValue(aRes.next().literal("sha1").orElseThrow(Exception::new));
            assertEquals(313, aWordCount);
        }
    }

}
