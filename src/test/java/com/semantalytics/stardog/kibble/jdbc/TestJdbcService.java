package com.semantalytics.stardog.kibble.jdbc;

import com.google.common.collect.ImmutableList;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.string.emoji.EmojiVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestJdbcService extends AbstractStardogTest {


    @Before
    public void setUp() {
        super.setUp();
        final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        final String protocol = "jdbc:derby:";
        try {
            Connection conn = DriverManager.getConnection(protocol + "derbyDB;create=true");
            conn.createStatement().execute("drop table test");
            conn.createStatement().execute("create table test (result VARCHAR(255))");
            conn.createStatement().execute("insert into test values ('myResult')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @After
    public void tearDown() {
        super.tearDown();
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
    }

    @Test
    public void testListDrivers() {

            final String aQuery = JdbcVocabulary.sparqlPrefix("jdbc")
                                  + "select ?result where {" +
                    "service<jdbc:derby:derbyDB> {" +
                    " [] " +
                    "jdbc:solution ?result;" +
                    "jdbc:query \"select result from test\" ." +
                    "}}";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.next().literal("result").get().label()).isEqualTo("myResult");

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
