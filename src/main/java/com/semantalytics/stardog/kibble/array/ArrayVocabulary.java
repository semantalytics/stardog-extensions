package com.semantalytics.stardog.kibble.array;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class ArrayVocabulary {

	public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/array/";

	public static final IRI append = iri(NS + "append");
	public static final IRI cartesianProduct = iri(NS + "cartesianProduct");
	public static final IRI contains = iri(NS + "contains");
	public static final IRI distinct = iri(NS + "distinct");
	public static final IRI uniformDatatype = iri(NS + "uniformDatatype");
	public static final IRI equals = iri(NS + "equals");
	public static final IRI fill = iri(NS + "fill");
	public static final IRI flatten = iri(NS + "flatten");
	public static final IRI index = iri(NS + "index");
	public static final IRI indexOf = iri(NS + "indexOf");
	public static final IRI indexOfFirst = iri(NS + "indexOfFirst");
	public static final IRI indexOfLast = iri(NS + "indexOfLast");
	public static final IRI isUniformType = iri(NS + "isUniformType");
	public static final IRI isUniformLiteral = iri(NS + "isUniformLiteral");
	public static final IRI isEmpty = iri(NS + "isEmpty");
	public static final IRI nDims = iri(NS + "nDims");
	public static final IRI partition = iri(NS + "partition");
	public static final IRI size = iri(NS + "size");
	public static final IRI shuffle = iri(NS + "shuffle");
	public static final IRI swap = iri(NS + "swap");
	public static final IRI shift = iri(NS + "shift");
	public static final IRI removeFirst = iri(NS + "removeFirst");
	public static final IRI removeLast = iri(NS + "removeLast");
	public static final IRI removeAll = iri(NS + "removeAll");
	public static final IRI sub = iri(NS + "sub");
	public static final IRI of = iri(NS + "of");
	public static final IRI offset = iri(NS + "offset");
	public static final IRI offsetDatatype = iri(NS + "offsetDatatype");
	public static final IRI ordinal = iri(NS + "ordinal");
	public static final IRI ordinalDatatype = iri(NS + "ordinalDatatype");
	public static final IRI reverse = iri(NS + "reverse");
	public static final IRI toString = iri(NS + "toString");
	public static final IRI zip = iri(NS + "zip");
	public static final IRI zipWithIndex = iri(NS + "zipWithIndex");

    public static String sparqlPrefix(final String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
