package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestIsUniformType extends AbstractStardogTest {

    final String sparqlPrefix = ArrayVocabulary.sparqlPrefix("array");

    @Test
    public void arrayOfAllIntsShouldBeTrue() {

        final String aQuery = sparqlPrefix
                + "select (array:isUniformType(?array) as ?result) where { bind(array:of(1, 2, 3) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(Literal.booleanValue(aLiteral)).isEqualTo(true);
            Assertions.assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void twoDimArrayOfAllIntsShouldBeTrue() {

        final String aQuery = sparqlPrefix
                + "select (array:isUniformType(?array) as ?result) where { bind(array:of(array:of(1, 2), 3, 4) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(Literal.booleanValue(aLiteral)).isEqualTo(true);
            Assertions.assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void arrayOfMixedLiteralsShouldBeFalse() {

        final String aQuery = sparqlPrefix
                + "select (array:isUniformType(?array) as ?result) where { bind(array:of(array:of(1.1, 2), 3, 4) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(Literal.booleanValue(aLiteral)).isEqualTo(false);
            Assertions.assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void arrayOfAllIrisShouldBeTrue() {

        final String aQuery = sparqlPrefix
                + "select (array:isUniformType(?array) as ?result) where { bind(array:of(<http://example/1>, <http://example/2>, <http://example/3>) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(Literal.booleanValue(aLiteral)).isEqualTo(true);
            Assertions.assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void twoDimArrayOfAllIrisShouldBeTrue() {

        final String aQuery = sparqlPrefix
                + "select (array:isUniformType(?array) as ?result) where { bind(array:of(<http://example/1>, <http://example/2>, array:of(<http://example/3>, <http://example/4>)) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(Literal.booleanValue(aLiteral)).isEqualTo(true);
            Assertions.assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void twoDimArrayOfAllIrisWithSingleIntShouldBeFalse() {

        final String aQuery = sparqlPrefix
                + "select (array:isUniformType(?array) as ?result) where { bind(array:of(<http://example/1>, <http://example/2>, array:of(<http://example/3>, 4)) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(Literal.booleanValue(aLiteral)).isEqualTo(false);
            Assertions.assertThat(aResult).isExhausted();
        }
    }


    @Test
    public void wrongTypeFirstArgShouldBeEmptyResult() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array")
            + "select (array:isUniformType(?array) as ?result) where { bind(1 as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
