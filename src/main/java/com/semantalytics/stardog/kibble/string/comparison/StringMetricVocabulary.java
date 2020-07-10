package com.semantalytics.stardog.kibble.string.comparison;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class StringMetricVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/string/metric/";

	public static final IRI cosineDistance = iri(NS + "cosineDistance");
	public static final IRI cosineSimilarity = iri(NS + "cosineSimilarity");
	public static final IRI damerauDistance = iri(NS + "damerauDistance");
	public static final IRI hammingDistance = iri(NS + "hammingDistance");
	public static final IRI isub = iri(NS + "isub");
	public static final IRI jaroWinklerSimilarity = iri(NS + "jaroWinklerSimilarity");
	public static final IRI jaroWinklerDistance = iri(NS + "jaroWinklerDistance");
	public static final IRI levenshteinDistance = iri(NS + "levenshteinDistance");
	public static final IRI longestCommonSubsequence = iri(NS + "longestCommonSubsequence");
	public static final IRI longestCommonSubstring = iri(NS + "longestCommonSubstring");
	public static final IRI metricLongestCommonSubsequence = iri(NS + "metricLongestCommonSubsequence");
	public static final IRI mongeElkan = iri(NS + "mongeElkan");
	public static final IRI needlemanWunch = iri(NS + "needlemanWunch");
	public static final IRI ngram = iri(NS + "ngram");
	public static final IRI normalizedLevenshteinDistance = iri(NS + "normalizedLevenshteinDistance");
	public static final IRI normalizedLevenshteinSimarity = iri(NS + "normalizedLevenshteinSimarity");
	public static final IRI overlapCoefficient = iri(NS + "overlapCoefficient");
	public static final IRI qgram = iri(NS + "qgram");
	public static final IRI sift4 = iri(NS + "sift4");
	public static final IRI smithWaterman = iri(NS + "smithWaterman");
	public static final IRI smithWatermanGotoh = iri(NS + "smithWatermanGotoh");
	public static final IRI sorensenDiceSimilarity = iri(NS + "sorensenDiceSimilarity");
	public static final IRI sorensenDiceDistance = iri(NS + "sorensenDiceDistance");
	public static final IRI weightedLevenshteinDistance = iri(NS + "weightedLevenshteinDistance");

    public static String sparqlPrefix(final String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
