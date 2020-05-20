package com.semantalytics.stardog.kibble.console;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum ConsoleVocabulary {

    backgroundBlack,
    backgroundBlue,
    backgroundBrightBlack,
    backgroundBrightBlue,
    backgroundBrightCyan,
    backgroundBrightGreen,
    backgroundBrightMagenta,
    backgroundBrightRed,
    backgroundBrightWhite,
    backgroundBrightYellow,
    backgroundCyan,
    backgroundDefault,
    backgroundGreen,
    backgroundMagenta,
    backgroundRed,
    backgroundWhite,
    backgroundYellow,
    blinkFast,
    blinkOff,
    blinkSlow,
    bold,
    boldOff,
    eraseScreen,
    eraseLine,
    conceal,
    concealOff,
    console,
    foregroundBlack,
    foregroundBlue,
    foregroundCyan,
    foregroundDefault,
    foregroundGreen,
    foregroundMagenta,
    foregroundRed,
    foregroundWhite,
    foregroundYellow,
    italic,
    italicOff,
    negative,
    negativeOff,
    render,
    reset,
    strikeThrough,
    strikeThroughOff,
    underline,
    underlineDouble,
    underlineOff;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/console/";
    public final IRI iri;

    ConsoleVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.toString();
    }
}
