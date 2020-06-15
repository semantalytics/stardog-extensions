package com.semantalytics.stardog.kibble.service.jdbc;

import com.complexible.stardog.AbstractStardogModule;
import com.complexible.stardog.plan.eval.service.Service;
import com.google.inject.multibindings.Multibinder;

import javax.inject.Singleton;

public class JdbcServiceModule extends AbstractStardogModule {

    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), Service.class)
            .addBinding()
            .to(JdbcService.class)
            .in(Singleton.class);
    }
}
