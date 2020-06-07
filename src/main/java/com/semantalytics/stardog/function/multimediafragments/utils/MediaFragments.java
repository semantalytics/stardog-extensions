package com.semantalytics.stardog.function.multimediafragments.utils;

import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;

public class MediaFragments {

    public static boolean isMediaFragment(final Literal literal) {
        try {
            MediaFragment.create(literal.label());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static MediaFragment of(final IRI v) throws MediaFragmentURISyntaxException {
        return new MediaFragmentURI(v.toString()).getMediaFragment();
    }

    public static MediaFragment of(final Literal literal) throws MediaFragmentURISyntaxException {
        return new MediaFragmentURI(literal.label()).getMediaFragment();
    }
}
