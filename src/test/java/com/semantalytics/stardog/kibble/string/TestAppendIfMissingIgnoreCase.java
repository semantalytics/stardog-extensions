package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Datatype;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class TestAppendIfMissingIgnoreCase extends AbstractStardogTest {
  
    @Test
    public void testNotMissing() {
   
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:appendIfMissingIgnoreCase(\"stardog.txt\", \".txt\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();
                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue)).isTrue();
                assertThat(((Literal)aValue).label()).isEqualTo("stardog.txt");
                assertThat(((Literal)aValue).datatype()).isEqualTo(Datatype.STRING);
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testMissing() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:appendIfMissingIgnoreCase(\"stardog\", \".txt\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult.hasNext()).isTrue();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            assertThat(assertStringLiteral(aValue)).isTrue();
            assertThat(((Literal)aValue).label()).isEqualTo("stardog.txt");
            assertThat(((Literal)aValue).datatype()).isEqualTo(Datatype.STRING);
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:appendIfMissingIgnoreCase(\"\", \".txt\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue)).isTrue();
                final Literal aLiteral = ((Literal)aValue);
                assertThat(((Literal) aLiteral).label()).isEqualTo(".txt");
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?abbreviation where { bind(string:appendIfMissingIgnoreCase(\"one\") as ?abbreviation) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.variables()).isEmpty();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?abbreviation where { bind(string:appendIfMissingIgnoreCase(\"one\", 2, \"three\") as ?abbreviation) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.variables()).isEmpty();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?abbreviation where { bind(string:appendIfMissingIgnoreCase(4, 5) as ?abbreviation) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.variables()).isEmpty();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeSecondArg() {
    
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?abbreviation where { bind(string:appendIfMissingIgnoreCase(\"one\", 2) as ?abbreviation) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.variables()).isEmpty();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testLengthTooShort() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?abbreviation where { bind(string:appendIfMissingIgnoreCase(\"Stardog\", 3) as ?abbreviation) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.variables()).isEmpty();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
