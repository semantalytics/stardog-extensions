package com.semantalytics.stardog.kibble.string.emoji;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class EmojiVocabulary {

	public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/string/emoji/";

	public static final IRI aliases = iri(NS + "aliases");
	public static final IRI count = iri(NS + "count");
	public static final IRI decimalHtml = iri(NS + "decimalHtml");
	public static final IRI decimalHtmlShort = iri(NS + "decimalHtmlShort");
	public static final IRI decimalSurrogateHtml = iri(NS + "decimalSurrogateHtml");
	public static final IRI unicode = iri(NS + "unicode");
	public static final IRI emojify = iri(NS + "emojify");
	public static final IRI emoticon = iri(NS + "emoticon");
	public static final IRI hexHtmlify = iri(NS + "hexHtmlify");
	public static final IRI htmlify = iri(NS + "htmlify");
	public static final IRI isEmoji = iri(NS + "isEmoji");
	public static final IRI remove = iri(NS + "remove");
	public static final IRI shortCodify = iri(NS + "shortCodify");

    public static String sparqlPrefix(final String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
