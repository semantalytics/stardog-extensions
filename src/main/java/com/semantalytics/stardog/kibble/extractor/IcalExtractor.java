package com.semantalytics.stardog.lab.doc.extraction;

import com.complexible.common.rdf.StatementSource;
import com.complexible.stardog.db.DatabaseConnection;
import com.complexible.stardog.docs.StardocsException;
import com.complexible.stardog.docs.extraction.RDFExtractor;
import com.google.auto.service.AutoService;
import org.openrdf.model.IRI;

import java.nio.file.Path;

@AutoService(com.complexible.stardog.docs.extraction.RDFExtractor.class)
public class IcalExtractor implements RDFExtractor {
    @Override
    public StatementSource extract(DatabaseConnection databaseConnection, IRI iri, Path path) throws StardocsException {
        return null;
    }
}
