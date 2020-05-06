package com.semantalytics.stardog.lab.doc.extraction;

import com.complexible.common.rdf.StatementSource;
import com.complexible.stardog.db.DatabaseConnection;
import com.complexible.stardog.docs.extraction.RDFExtractor;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import org.openrdf.model.IRI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class VCardExtractor implements RDFExtractor {

    Logger log = LoggerFactory.getLogger(VCardExtractor.class);

    @Override
    public StatementSource extract(DatabaseConnection databaseConnection, IRI iri, Path path) throws StardocsException {
        try {
            for(final VCard vCard : Ezvcard.parse(path.toFile()).all()) {

            }
        } catch (IOException e) {
            log.error("Unable to parse vcard file " + path);
        }
        return null;
    }
}
