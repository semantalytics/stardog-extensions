package com.semantalytics.stardog.kibble.util;

import com.complexible.common.protocols.server.ServerException;
import com.complexible.common.protocols.server.ServerOptions;
import com.complexible.stardog.Stardog;
import com.google.common.io.Files;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.io.File;
import java.io.IOException;

@RunWith(Suite.class)
@SuiteClasses({
        TestBindPrevious.class,
        TestFromSpokenTime.class,
        TestIndex.class,
        TestSpokenTime.class,
        TestStardogVersion.class
})

public class UtilTestSuite extends TestCase {

    private static final String STARDOG_HOME = System.getenv("STARDOG_HOME");
    private static final String STARDOG_LICENCE_KEY_FILE_NAME = "stardog-license-key.bin";

    @BeforeClass
    public static void beforeClass() throws IOException, ServerException {

        if(AbstractStardogTest.STARDOG == null) {
            final File TEST_HOME;

            TEST_HOME = Files.createTempDir();
            TEST_HOME.deleteOnExit();

            Files.copy(new File(STARDOG_HOME + "/" + STARDOG_LICENCE_KEY_FILE_NAME),
                    new File(TEST_HOME, STARDOG_LICENCE_KEY_FILE_NAME));

            AbstractStardogTest.STARDOG = Stardog.builder()
                    .set(ServerOptions.SECURITY_DISABLED, true)
                    .home(TEST_HOME)
                    .create();
        }
    }

    @AfterClass
    public static void afterClass() {
        if (AbstractStardogTest.STARDOG != null) {
            AbstractStardogTest.STARDOG.shutdown();
        }
    }
}
