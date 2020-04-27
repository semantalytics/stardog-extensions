package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum JdbcVocabulary {

    exec,
    isDriverRegistered,
    isDriverJdbcCompliant,
    driverPropertyInfo,
    driverMinorVersion,
    driverMajorVersion,
    driverAccepts,
    databaseProductVersion,
    databaseProductName,
    databaseMinorVersion,
    databaseMajorVersion;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/jdbc/";
    public final IRI iri;

    JdbcVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
