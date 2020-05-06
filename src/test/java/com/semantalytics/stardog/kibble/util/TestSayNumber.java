package com.semantalytics.stardog.kibble.util;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSayNumber extends AbstractStardogTest {

    @Test
    public void testFromSpokenTime() {

            final String aQuery = "prefix util: <" + UtilVocabulary.NAMESPACE + ">" +
                    "select ?result where { bind(util:sayNumber(532) as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final Optional<Value> aPossibleValue = aResult.next().value("result");

                assertThat(aPossibleValue).isPresent();

                final Value aValue = aPossibleValue.get();

                assertThat(aValue).isInstanceOf(Literal.class);
                assertThat(((Literal)aValue).label()).isEqualTo("five hundred and thirty-two");
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
