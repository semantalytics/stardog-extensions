package com.semantalytics.stardog.kibble.lambda;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.array.ArrayVocabulary;
import com.semantalytics.stardog.kibble.string.StringVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestExec extends AbstractStardogTest {

    @Test
    public void testTwoArg() {

        final String aQuery = "prefix lambda: <http://semantalytics.com/2020/07/ns/lambda/> " +
                "select ?result where { bind(lambda:exec(" + StringVocabulary.upperCase + ", \"hello world\" ) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            List<Integer> results = IteratorUtils.toList(aResult).stream().map(b -> b.get("result")).filter(Literal.class::isInstance).map(Literal.class::cast).map(Literal::intValue).collect(toList());
            assertThat(results).containsExactly(1, 2, 5);
        }
    }
}
