package com.semantalytics.stardog.extractor.ipfs;

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

public class IpfsTest extends AbstractStardogTest {

    @Test
    public void testAbbreviate() throws Exception {

        BitesConnection aDocsConn = connection.as(BitesConnection.class);

        InputStream docInputStream = Resources.getResource("input.pdf").toURI().toURL().openStream();

        IRI aDocIri = aDocsConn.putDocument("input.pdf", docInputStream, ImmutableList.of("Ipfs"), IterableUtils.emptyIterable());

        String aQuery = "select ?sha1 { graph ?doc { ?doc " + IpfsVocabulary.ipfs + " ?cid } }";
        try(SelectQueryResult aRes = connection.select(aQuery).parameter("cid", aDocIri).execute()) {
            int aWordCount = Literal.intValue(aRes.next().literal("cid").orElseThrow(Exception::new));
            assertEquals(313, aWordCount);
        }
    }

}
