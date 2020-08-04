package com.semantalytics.stardog.kibble.string.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.*;

public class TestOverlapsCoefficient extends AbstractStardogTest {

    @Test
    public void testOverlapCoefficient() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?overlapCoefficient where { bind(stringmetric:overlapCoefficient(\"Stardog\", \"Starman\") as ?overlapCoefficient) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final Value aValue = aResult.next().value("overlapCoefficient").get();

        assertThat(Literal.floatValue((Literal)aValue)).isEqualTo(0.0f);
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }

    @Test
    public void testCosineTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?overlapCoefficient where { bind(stringmetric:overlapCoefficient(\"one\", \"two\", \"three\", \"four\") as ?overlapCoefficient) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }

    @Test
    public void testCosineWrongType() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?overlapCoefficient where { bind(stringmetric:overlapCoefficient(7) as ?overlapCoefficient) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }
}
