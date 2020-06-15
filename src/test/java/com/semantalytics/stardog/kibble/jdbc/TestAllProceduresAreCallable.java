package com.semantalytics.stardog.kibble.jdbc;

import com.google.common.collect.ImmutableList;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.string.emoji.EmojiVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestAllProceduresAreCallable extends AbstractStardogTest {

    @Test
    public void testListDrivers() {

            final String aQuery = JdbcVocabulary.sparqlPrefix("jdbc")
                                  + "select ?result where { bind(jdbc:allProceduresAreCallable(\"jdbc:derby:derbyDB\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {


                List<BindingSet> results = ImmutableList.copyOf(aResult);
                String[] drivers = new String[] {"org.apache.calcite.jdbc.Driver", "org.apache.calcite.avatica.remote.Driver", "org.apache.derby.jdbc.AutoloadedDriver40"};
                assertThat(results.stream().map(b -> b.literal("result")).map(Optional::get).map(Literal::label).collect(toList())).containsExactlyInAnyOrder(drivers);

            }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:aliases() as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

//            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
