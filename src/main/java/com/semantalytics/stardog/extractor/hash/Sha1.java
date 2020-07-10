package com.semantalytics.stardog.extractor.hash;

import com.complexible.common.rdf.StatementSource;
import com.complexible.common.rdf.StatementSources;
import com.complexible.common.rdf.impl.MemoryStatementSource;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.docs.BitesException;
import com.complexible.stardog.docs.extraction.RDFExtractor;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.stardog.stark.IRI;
import com.stardog.stark.Statement;
import com.stardog.stark.Values;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Sha1 implements RDFExtractor {

    @Override
    public StatementSource extract(Connection theConnection, IRI theDocIri, Path theDocContents) throws BitesException {
        try {
            if (theDocContents.toFile().length() > 0L) {
                HashCode hashCode = Hashing.sha1().newHasher().putBytes(Files.readAllBytes(theDocContents)).hash();
                Set<Statement> aModel = new HashSet();
                aModel.add(Values.statement(theDocIri, Values.iri("http://stardog.semantalytics.com/2019/11/ns/hash/sha1"), Values.literal(hashCode.toString())));
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
