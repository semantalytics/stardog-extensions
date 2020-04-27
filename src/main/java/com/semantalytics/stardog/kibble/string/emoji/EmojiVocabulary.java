package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

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
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
