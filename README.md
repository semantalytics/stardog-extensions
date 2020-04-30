[![Build Status](https://travis-ci.org/semantalytics/stardog-kibbles.svg?branch=master)](https://travis-ci.org/semantalytics/stardog-kibbles)

# Stardog Functions

A collection of [Stardog](http://stardog.com) functions

# String

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
    
# Emoji

Namespace: http://semantalytics.com/2017/09/ns/stardog/kibble/string/emoji/

Suggested prefixes: e:, emj:, emoji:

Functions:

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
    
# String metrics

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
