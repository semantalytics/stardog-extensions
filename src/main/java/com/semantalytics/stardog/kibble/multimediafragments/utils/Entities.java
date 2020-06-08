package com.semantalytics.stardog.kibble.multimediafragments.utils;

import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.github.tkurz.media.ontology.type.SpatialTemporalEntity;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.stardog.stark.IRI;
import com.stardog.stark.Value;

public class Entities {


    public static boolean haveComparable(final Value... values) {
        //What the hell is this doing? Convoluted as shit.
        Class<? extends Value> clazz = null;
        String baseURI = null;
        for(final Value v : values) {
            if(clazz == null) {
                clazz = v.getClass();
                baseURI = getBaseURI(v);
            } else if(v.getClass().equals(clazz)) {
                if(v instanceof IRI) {
                    if(!baseURI.equals(getBaseURI(v))) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public static String getBaseURI(final Value v) {
        return v.toString().replaceAll("\\?.*","").replaceAll("#.*","");
    }
}
