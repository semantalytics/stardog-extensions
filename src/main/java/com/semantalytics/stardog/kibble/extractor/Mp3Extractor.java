package com.semantalytics.stardog.lab.doc.extraction;

import com.complexible.common.rdf.StatementSource;
import com.complexible.stardog.db.DatabaseConnection;
import com.complexible.stardog.docs.StardocsException;
import com.complexible.stardog.docs.extraction.RDFExtractor;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.openrdf.model.IRI;

import java.io.IOException;
import java.nio.file.Path;

public class Mp3Extractor implements RDFExtractor {
    @Override
    public StatementSource extract(DatabaseConnection databaseConnection, IRI iri, Path path) throws StardocsException {
        try {
            Mp3File mp3file = new Mp3File(path.toFile());
            mp3file.
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
