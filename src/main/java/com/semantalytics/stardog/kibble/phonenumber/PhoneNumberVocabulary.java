package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum PhoneNumberVocabulary {

    formatNationalNumberWithPreferredCarrierCode,
    formatNumberWithPreferredCarrierCode,
    formatNumberForMobileDialing,
    formatOutOfCountryCallingNumber,
    formatOutOfCountryKeepingAlphaChars,
    formatRfc3966,
    invalidExampleNumber,
    isMobileNumberPortableRegion,
    isNANPACountry,
    isNumberMatch,
    isPossibleNumberForType,
    isPossibleNumberForTypeWithReason,
    isPossibleNumberWithReason,
    isValidNumberForRegion,
    lengthOfGeographicalAreaCode,
    lengthOfNationalDestinationCode,
    isAlphaNumber,
    leniencyExactGrouping,
    leniencyPossible,
    leniencyStrictGrouping,
    leniencyValid,
    matchTypeExact,
    matchTypeNone,
    matchTypeNotANumber,
    matchTypeNSN,
    matchTypeShortNSN,
    nationalSignificantNumber,
    format,
    alternateFormatsCountryCode,
    canBeInternationallyDialed,
    convertAlphaCharactersInNumber,
    countryCodeForRegion,
    countryCodeToRegionCode,
    countryMobileToken,
    exampleNumber,
    exampleNumberForNonGeoEntity,
    exampleNumberForType,
    findNumbers,
    formatByPattern,
    formatE164,
    formatInOriginalFormat,
    formatInternational,
    formatNational,
    formatNationalNumberWithCarierCode,
    formatNationalNumberWithCarrierCode,
    isPossibleNumber,
    isValidNumber,
    typeUnknown,
    typeVoiceMail,
    typeVoip,
    typeUAN,
    typeTollFree,
    typeSharedCost,
    typePremiumRate,
    typePersonal,
    typeMobile,
    typeFixedLineOrMobile,
    typeFixedLine,
    truncateTooLongNumber,
    regionCodesForCountryCode,
    regionCodeForNumber,
    numberType,
    normalizeDigitsOnly,
    normalizeDiallableCharsOnly,
    nddPrefixForRegion;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/phonenumber/";
    public final IRI iri;

    PhoneNumberVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
