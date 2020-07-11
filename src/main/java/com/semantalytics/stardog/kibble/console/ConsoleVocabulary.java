package com.semantalytics.stardog.kibble.console;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class ConsoleVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/console/";

	public static final IRI backgroundBlack = iri(NS + "backgroundBlack");
	public static final IRI backgroundBlue = iri(NS + "backgroundBlue");
	public static final IRI backgroundBrightBlack = iri(NS + "backgroundBrightBlack");
	public static final IRI backgroundBrightBlue = iri(NS + "backgroundBrightBlue");
	public static final IRI backgroundBrightCyan = iri(NS + "backgroundBrightCyan");
	public static final IRI backgroundBrightGreen = iri(NS + "backgroundBrightGreen");
	public static final IRI backgroundBrightMagenta = iri(NS + "backgroundBrightMagenta");
	public static final IRI backgroundBrightRed = iri(NS + "backgroundBrightRed");
	public static final IRI backgroundBrightWhite = iri(NS + "backgroundBrightWhite");
	public static final IRI backgroundBrightYellow = iri(NS + "backgroundBrightYellow");
	public static final IRI backgroundCyan = iri(NS + "backgroundCyan");
	public static final IRI backgroundDefault = iri(NS + "backgroundDefault");
	public static final IRI backgroundGreen = iri(NS + "backgroundGreen");
	public static final IRI backgroundMagenta = iri(NS + "backgroundMagenta");
	public static final IRI backgroundRed = iri(NS + "backgroundRed");
	public static final IRI backgroundWhite = iri(NS + "backgroundWhite");
	public static final IRI backgroundYellow = iri(NS + "backgroundYellow");
	public static final IRI blinkFast = iri(NS + "blinkFast");
	public static final IRI blinkOff = iri(NS + "blinkOff");
	public static final IRI blinkSlow = iri(NS + "blinkSlow");
	public static final IRI bold = iri(NS + "bold");
	public static final IRI boldOff = iri(NS + "boldOff");
	public static final IRI eraseScreen = iri(NS + "eraseScreen");
	public static final IRI eraseLine = iri(NS + "eraseLine");
	public static final IRI conceal = iri(NS + "conceal");
	public static final IRI concealOff = iri(NS + "concealOff");
	public static final IRI console = iri(NS + "console");
	public static final IRI foregroundBlack = iri(NS + "foregroundBlack");
	public static final IRI foregroundBlue = iri(NS + "foregroundBlue");
	public static final IRI foregroundCyan = iri(NS + "foregroundCyan");
	public static final IRI foregroundDefault = iri(NS + "foregroundDefault");
	public static final IRI foregroundGreen = iri(NS + "foregroundGreen");
	public static final IRI foregroundMagenta = iri(NS + "foregroundMagenta");
	public static final IRI foregroundRed = iri(NS + "foregroundRed");
	public static final IRI foregroundWhite = iri(NS + "foregroundWhite");
	public static final IRI foregroundYellow = iri(NS + "foregroundYellow");
	public static final IRI italic = iri(NS + "italic");
	public static final IRI italicOff = iri(NS + "italicOff");
	public static final IRI negative = iri(NS + "negative");
	public static final IRI negativeOff = iri(NS + "negativeOff");
	public static final IRI render = iri(NS + "render");
	public static final IRI reset = iri(NS + "reset");
	public static final IRI strikeThrough = iri(NS + "strikeThrough");
	public static final IRI strikeThroughOff = iri(NS + "strikeThroughOff");
	public static final IRI underline = iri(NS + "underline");
	public static final IRI underlineDouble = iri(NS + "underlineDouble");
	public static final IRI underlineOff = iri(NS + "underlineOff");

    public static String sparqlPrefix(final String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
