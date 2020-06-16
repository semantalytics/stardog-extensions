package com.semantalytics.stardog.kibble.jdbc;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum JdbcVocabulary {

    exec,
    query,
    numericFunctions,
    extraNameCharacters,
    supportsMinimumSqlGrammar,
    supportsAnsi92EntryLevelSql,
    supportsExtendedSqlGrammar,
    supportsNonNullableColumns,
    supportsMultipleResultSets,
    supportsCoreSqlGrammar,
    supportsGroupByUnrelated,
    supportsOrderByUnrelated,
    supportsGroupBy,
    supportsMultipleTransactions,
    supportsExpressionsInOrderBy,
    supportsAlterTableWithAddColumn,
    supportsDifferentTableCorrelationNames,
    supportsTableCorrelationNames,
    supportsConvert,
    supportsAlterTableWithDropColumn,
    supportsColumnAliasing,
    supportsLikeEscapeClause,
    searchStringEscape,
    timeDateFunctions,
    systemFunctions,
    stringFunctions,
    identifierQuoteString,
    sqlKeywords,
    deletesAreDetected,
    storesUpperCaseQuotedIdentifiers,
    storesMixedCaseQuotedIdentifiers,
    storesLowerCaseQuotedIdentifiers,
    supportsBatchUpdates,
    supportsMixedCaseIdentifiers,
    nullPlusNonNullIsNull,
    usesLocalFilesPerTable,
    usesLocalFiles,
    isReadOnly,
    locatorsUpdateCopy,
    supportsSavePoints,
    supportsNamedParameters,
    insertsAreDetected,
    dataDefinitionCausesTransactionCommit,
    nullsAreSortedAtEnd,
    nullsAreSortedAtStart,
    nullsAreSortedHigh,
    nullsAreSortedLow,
    isCatalogAtStart,
    allTablesAreSelectable,
    generatedKeyAlwaysReturned,
    doesMaxRowSizeIncludeBlobs,
    allProceduresAreCallable,
    supportsGroupByBeyondSelect,
    autoCommitFailurewClosesAllResultSets,
    isDriverRegistered,
    drivers,
    isDriverJdbcCompliant,
    dataDefinitionIgnoredInTransaction,
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
