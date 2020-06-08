package com.semantalytics.stardog.kibble.multimediafragments.utils;

import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.type.SpatialTemporalEntity;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.Optional;

public class SpatialTemporalEntities {

    public static Optional<SpatialTemporalEntity> of(final Value v) throws MediaFragmentURISyntaxException, ParseException {
        if(v instanceof IRI) {
            return of((IRI)v);
        } else if(v instanceof Literal) {
            return of((Literal)v);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<SpatialTemporalEntity> of(final IRI v) throws MediaFragmentURISyntaxException {
        MediaFragmentURI uri = new MediaFragmentURI(v.toString());
        return Optional.ofNullable(uri.getMediaFragment());
    }

    public static Optional<SpatialTemporalEntity> of(final Literal v) throws ParseException  {
        //try to parse simple fragment TODO add object parsing
        return Optional.ofNullable(MediaFragment.create(v.toString()));
    }
}
