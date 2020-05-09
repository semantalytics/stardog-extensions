package com.semantalytics.stardog.kibble.string.emoji;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum EmojiVocabulary {

    aliases,
    count,
    decimalHtml,
    decimalHtmlShort,
    decimalSurrogateHtml,
    unicode,
    emojify,
    emoticon,
    hexHtmlify,
    htmlify,
    isEmoji,
    remove,
    shortCodify;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/string/emoji/";
    public final IRI iri;

    EmojiVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.toString();
    }
}
