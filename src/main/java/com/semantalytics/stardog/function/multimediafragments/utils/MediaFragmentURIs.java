package com.semantalytics.stardog.function.multimediafragments.utils;

import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public class MediaFragmentURIs {

    public static MediaFragmentURI of(final IRI iri) throws MediaFragmentURISyntaxException {
        return new MediaFragmentURI(iri.toString());
    }

    public static MediaFragmentURI of(final Literal literal) throws MediaFragmentURISyntaxException {
        return new MediaFragmentURI(literal.label());
    }

    public static boolean isMediaFragmentURI(final Value value) {
        if(value instanceof IRI) {
            try {
                new MediaFragmentURI(value.toString());
                return true;
            } catch (MediaFragmentURISyntaxException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
