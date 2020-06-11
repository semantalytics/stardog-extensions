package com.semantalytics.stardog.kibble.jdbc;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum JdbcVocabulary {

    exec,
    isDriverRegistered,
    drivers,
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
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.toString();
    }
}
