package com.semantalytics.stardog.kibble.visualization.ascii;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum AsciiVisualizationVocabulary {

    widthBucket,
    spark,
    sparkline,
    qrcode,
    sparklineAgg,
    boxplot;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/visualization/ascii/";
    public final IRI iri;

    AsciiVisualizationVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
