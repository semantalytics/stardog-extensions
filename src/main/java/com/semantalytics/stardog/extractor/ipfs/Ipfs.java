package com.semantalytics.stardog.extractor.ipfs;

import com.complexible.common.rdf.StatementSource;
import com.complexible.common.rdf.StatementSources;
import com.complexible.common.rdf.impl.MemoryStatementSource;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.docs.BitesException;
import com.complexible.stardog.docs.extraction.RDFExtractor;
import com.stardog.stark.IRI;
import com.stardog.stark.Statement;
import com.stardog.stark.Values;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Ipfs implements RDFExtractor {

    @Override
    public StatementSource extract(Connection theConnection, IRI theDocIri, Path theDocContents) throws BitesException {
        try {
            if (theDocContents.toFile().length() > 0L) {
                io.ipfs.api.IPFS ipfs = new IPFS("/ipv4/127.0.0.1/tcp/5001");

                NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(theDocContents.toFile());
                MerkleNode addResult = ipfs.add(file).get(0);
                Set<Statement> aModel = new HashSet();
                aModel.add(Values.statement(theDocIri, Values.iri(IpfsVocabulary.ipfs.toString()), Values.iri("ipfs://" + addResult.hash.toString())));
                return MemoryStatementSource.of(aModel);
            } else {
                return StatementSources.empty();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (Exception e) {
            throw new StardogException(e);
        }
    }

}
