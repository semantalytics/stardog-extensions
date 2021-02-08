package com.semantalytics.stardog.kibble.geo.geohash;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class GeoHashVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/geo/hash/";

	public static final IRI right = iri(NS + "right");
	public static final IRI left = iri(NS + "left");
	public static final IRI top = iri(NS + "top");
	public static final IRI bottom = iri(NS + "bottom");
	public static final IRI decode = iri(NS + "decode");
	public static final IRI encode = iri(NS + "encode");
	public static final IRI latitude = iri(NS + "latitude");
	public static final IRI hashLengthToCoverBoundingBox = iri(NS + "hashLengthToCoverBoundingBox");
	public static final IRI hashContains = iri(NS + "hashContains");
	public static final IRI heightDegrees = iri(NS + "heightDegrees");
	public static final IRI longitude = iri(NS + "longitude");
	public static final IRI neighbors = iri(NS + "neighbors");
	public static final IRI widthDegrees = iri(NS + "widthDegrees");
	public static final IRI addLongitude = iri(NS + "addLongitude");

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }

}
