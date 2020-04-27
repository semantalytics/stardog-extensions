package com.semantalytics.stardog.kibble.jdbc;

import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.Optional;

public class JdbcUtils {

    public static final Optional<String> fromLiteralOrIRI(final Value value) {

        if(value instanceof IRI) {
            return Optional.of(value.toString());
        } else if(value instanceof Literal) {
            return Optional.of(((Literal)value).label());
        } else {
            return Optional.empty();
        }

    }
}
