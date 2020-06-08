package com.semantalytics.stardog.kibble.multimediafragments.utils;

import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TemporalEntities {

    public static List<Optional<TemporalEntity>> of(final  Value... values) throws ParseException, MediaFragmentURISyntaxException {
        final List<Optional<TemporalEntity>> entities = new ArrayList<>(values.length);

        for(final Value value : values) {
            entities.add(of(value));
        }

        return  entities;
    }

    public static Optional<TemporalEntity> of(final IRI v) throws MediaFragmentURISyntaxException {
        final MediaFragmentURI uri = new MediaFragmentURI(v.toString());
        return uri.getMediaFragment().hasTemporalFragment() ? Optional.of(uri.getMediaFragment().getTemporalFragment()) : Optional.empty();
    }

    public static Optional<TemporalEntity> of(final Literal l) throws ParseException {
        final MediaFragment fragment = MediaFragment.create(l.label());
        return fragment.hasTemporalFragment() ? Optional.of(fragment.getTemporalFragment()) : Optional.empty();
    }

    public static Optional<TemporalEntity> of(final Value v) throws ParseException, MediaFragmentURISyntaxException {
        if(v instanceof IRI) {
            return of((IRI)v);
        } else if(v instanceof Literal) {
            return of((Literal)v);
        } else {
            return Optional.empty();
        }
    }

}
