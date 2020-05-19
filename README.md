[![Build Status](https://travis-ci.org/semantalytics/stardog-kibbles.svg?branch=master)](https://travis-ci.org/semantalytics/stardog-kibbles)

# Stardog Functions

A collection of [Stardog](http://stardog.com) functions

## Stardog Kibbles Console

A collection of [Stardog](http://stardog.com) plugins for writing ANSI console escape codes

Namespace: http://semantalytics.com/2017/09/ns/stardog/kibble/console/

Suggested prefixes: c: , cons:, console:

Functions:


    backgroundBlack
    backgroundBlue
    backgroundBrightBlack
    backgroundBrightBlue
    backgroundBrightCyan
    backgroundBrightGreen
    backgroundBrightMagenta
    backgroundBrightRed
    backgroundBrightWhite
    backgroundBrightYellow
    backgroundCyan
    backgroundDefault
    backgroundGreen
    backgroundMagenta
    backgroundRed
    backgroundWhite
    backgroundYellow
    blinkFast
    blinkOff
    blinkSlow
    bold
    boldOff
    eraseScreen
    eraseLine
    conceal
    concealOff
    console
    foregroundBlack
    foregroundBlue
    foregroundCyan
    foregroundDefault
    foregroundGreen
    foregroundMagenta
    foregroundRed
    foregroundWhite
    foregroundYellow
    italic
    italicOff
    negative
    negativeOff
    render
    reset
    strikeThrough
    strikeThroughOff
    underline
    underlineDouble
    underlineOff


## String

Namespace: http://semantalytics.com/2017/09/ns/stardog/kibble/string/

Suggested prefixes: s: , str: , string

Functions: 

    abbreviate
    abbreviateWithMarker
    abbreviateMiddle
    appendIfMissing
    appendIfMissingIgnoreCase
    capitalize
    caseFormat
    center
    chomp
    commonSuffix
    chop
    compare
    compareIgnoreCase
    commonPrefix
    containsAny
    contains
    containsIgnoreCase
    containsNone
    containsWhitespace
    containsOnly
    countMatches
    deleteWhitespace
    difference
    defaultIfBlank
    defaultIfEmpty
    endsWith
    endsWithIgnoreCase
    equals
    equalsAny
    equalsIgnoreCase
    digits
    initials
    indexOf
    indexOfAny
    indexOfAnyBut
    indexOfDifference
    indexOfIgnoreCase
    isAlpha
    isAsciiPrintable
    isAlphaSpace
    isAlphanumeric
    isAnyEmpty
    isAnyBlank
    isAlphanumericSpace
    isAllLowerCase
    isAllUpperCase
    isBlank
    isEmpty
    isMixedCase
    isNoneBlank
    isNoneEmpty
    isNotEmpty
    isNumericSpace
    isAllBlank
    isAllEmpty
    isNotBlank
    isNumeric
    isWhitespace
    join
    joinWith
    left
    length
    leftPad
    lastIndexOfAny
    lastOrdinalIndexOf
    lastIndexOfIgnoreCase
    lastIndexOf
    lowerCaseFully
    lowerCase
    ordinalIndexOf
    overlay
    padEnd
    pipe
    padStart
    prependIfMissing
    prependIfMissingIgnoreCase
    reverseDelimited
    removeEndIgnoreCase
    repeat
    remove
    removeIgnoreCase
    rotate
    reverse
    removeEnd
    removeAll
    removePattern
    removeFirst
    removeStart
    replace
    replaceAll
    replaceChars
    replaceEach
    replaceEachRepeatedly
    replaceFirst
    replaceOnce
    replaceOnceIgnoreCase
    replaceIgnoreCase
    replacePattern
    removeStartIgnoreCase
    right
    rightPad
    split
    splitByCharacterType
    splitByCharacterTypeCamelCase
    splitByWholeSeparator
    splitByWholeSeparatorPreserverAllTokens
    splitPreserverAllTokens 
    startsWith
    startsWithAny
    startsWithIgnoreCase
    strip
    stripAll
    stripAccents
    stripAllChar
    stripAllString
    stripEnd
    stripStart
    substring
    substringAfter
    substringAfterLast
    substringBefore
    substringBeforeLast
    substringBetween
    substringsBetween
    swapCase
    trim
    truncate
    toEncodedString
    toCodePoints
    uncapitalize
    unwrap
    wrap
    wrapIfMissing
    mid
    normalizeSpace
    upperCase
    
## Emoji

Namespace: http://semantalytics.com/2017/09/ns/stardog/kibble/string/emoji/

Suggested prefixes: e:, emj:, emoji:

    aliases
    count
    decimalHtml
    decimalHtmlShort
    decimalSurrogateHtml
    emojify
    emoticon
    hexHtmlify
    htmlify
    isEmoji
    remove
    shortCodify
    unicode
    
## String metrics

Namespace: http://semantalytics.com/2017/09/ns/stardog/kibble/string/metric/

Suggested prefixes: sm: , strm:, stringmetric: 

    cosineDistance
    cosineSimilarity
    damerauDistance
    hammingDistance
    isub
    jaroWinklerSimilarity
    jaroWinklerDistance
    levenshteinDistance
    longestCommonSubsequence
    longestCommonSubstring
    metricLongestCommonSubsequence
    needlemanWunch
    ngram
    normalizedLevenshteinDistance
    normalizedLevenshteinSimarity
    overlapCoefficient
    qgram
    sift4
    smithWaterman
    smithWatermanGotoh
    sorensenDiceSimilarity
    sorensenDiceDistance
    weightedLevenshteinDistance
    
## phonetic

Namespace: http://semantalytics.com/2017/09/ns/stardog/kibble/string/phonetic/

Suggested prefixes: p, pho, phonetic

Functions:

    carverphone2
    colognePhonetic
    daitchMokotoffSoundex
    doubleMetaphone
    metaphone
    nysiis
    refinedSoundex
    soundex
    
## String Escape

A collection of [Stardog](http://stardog.com) plugins for generating escaped strings in various formats

Namespace: http://semantalytics.com/2017/09/ns/stardog/kibble/string/escape/
Suggested prefix: escape:
Functions:

    csv
    ecmaScript
    html3
    html4
    java
    json
    xml
    xml10
    xml11
    xsi
    
## String Unescape

Namespace: http://semantalytics.com/2017/09/ns/stardog/kibble/string/unescape/
Suggested prefix: unescape:
Functions:

    csv
    ecmaScript
    html3
    html4
    java
    json
    xml
    xml10
    xml11
    xsi
