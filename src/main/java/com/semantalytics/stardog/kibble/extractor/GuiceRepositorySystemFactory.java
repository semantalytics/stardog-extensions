package com.semantalytics.stardog.lab.doc.extraction;

import com.google.inject.Guice;
import org.eclipse.aether.RepositorySystem;

public class GuiceRepositorySystemFactory {

    public static RepositorySystem newRepositorySystem() {
        return Guice.createInjector(new AetherModule()).getInstance(RepositorySystem.class);
    }
}
