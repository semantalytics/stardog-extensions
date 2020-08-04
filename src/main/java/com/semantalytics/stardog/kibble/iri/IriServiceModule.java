package com.semantalytics.stardog.kibble.iri;

import com.complexible.stardog.AbstractStardogModule;
import com.complexible.stardog.plan.eval.service.Service;
import com.google.inject.multibindings.Multibinder;

import javax.inject.Singleton;

public class IriServiceModule extends AbstractStardogModule {
    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), Service.class)
                .addBinding()
                .to(IriService.class)
                .in(Singleton.class);
    }
}
