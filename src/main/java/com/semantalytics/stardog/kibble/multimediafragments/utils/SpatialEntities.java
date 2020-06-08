package com.semantalytics.stardog.kibble.multimediafragments.utils;

import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpatialEntities {

    public static List<Optional<SpatialEntity>> of(final Value... values) throws MediaFragmentURISyntaxException, ParseException {

        final List<Optional<SpatialEntity>> entities = new ArrayList<>(values.length);

        for(final Value value : values) {
            entities.add(of(value));
        }

        return  entities;
    }

    public static Optional<SpatialEntity> of(final Value v) throws MediaFragmentURISyntaxException, ParseException {
        if(v instanceof IRI) {
            return SpatialEntities.of((IRI)v);
        } else if(v instanceof Literal) {
            return SpatialEntities.of((Literal)v);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<SpatialEntity> of(final IRI iri) throws MediaFragmentURISyntaxException {
        final MediaFragmentURI mediaFragmentURI = new MediaFragmentURI(iri.toString());
        return Optional.ofNullable(mediaFragmentURI.getMediaFragment().getSpatialFragment());
    }

    public static Optional<SpatialEntity> of(final Literal literal) throws ParseException {
        //try to parse simple fragment TODO add object parsing
        final MediaFragment fragment = MediaFragment.create(literal.label());
        return Optional.ofNullable(fragment.getSpatialFragment());
    }
}
