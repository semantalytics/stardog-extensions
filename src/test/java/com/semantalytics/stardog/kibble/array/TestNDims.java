package com.semantalytics.stardog.kibble.array;

import com.complexible.stardog.plan.filter.EvalUtil;
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

public class TestNDims extends AbstractStardogTest {

    final String sparqlPrefix = ArrayVocabulary.sparqlPrefix("array");

    @Test
    public void happyPathSimpleArray() {

        final String aQuery = sparqlPrefix
                + "select ?result where { { select (array:nDims(set(?value)) as ?array) where { values ?value { 1 2 } } } unnest(?array as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertLiteral(aValue));
            Assertions.assertThat(EvalUtil.isNumericDatatype(((Literal)aValue).datatype()));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(Literal.intValue(aLiteral)).isEqualTo(1);
            Assertions.assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void happyPathComplexArray() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(array:nDims(array:of(array:of(1, 2), array:of(3, 4))) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertLiteral(aValue));
            Assertions.assertThat(EvalUtil.isNumericDatatype(((Literal)aValue).datatype()));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(Literal.intValue(aLiteral)).isEqualTo(2);
            Assertions.assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void happyPathMoreComplexArray() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(array:nDims(array:of(array:of(1, 2), array:of(3, array:of(4, 5)))) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertLiteral(aValue));
            Assertions.assertThat(EvalUtil.isNumericDatatype(((Literal)aValue).datatype()));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(Literal.intValue(aLiteral)).isEqualTo(3);
            Assertions.assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void happyPathMoreComplexArrayTwoBranches() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(array:nDims(array:of(array:of(array:of(1, 2), 2), array:of(3, array:of(4, array:of(7, 8))))) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertLiteral(aValue));
            Assertions.assertThat(EvalUtil.isNumericDatatype(((Literal)aValue).datatype()));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(Literal.intValue(aLiteral)).isEqualTo(4);
            Assertions.assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void tooManyArgs() {

        final String aQuery = sparqlPrefix +
                "select ?result where { { select (array:toString(set(?value), 5) as ?array) where { values ?value { 1 2 } } } unnest(?array as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
            "select ?result where { { select (array:toString(5) as ?array) where { values ?value { 1 } } } unnest(?array as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
