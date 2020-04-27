package com.semantalytics.stardog.kibble;

import com.complexible.common.protocols.server.Server;
import com.complexible.stardog.Stardog;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.stardog.http.server.ServerOptions;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


public abstract class AbstractStardogTest {

    public static Stardog STARDOG;
    private static Server SERVER;
    private static final String DB = "test";
    public static int TEST_PORT = 5888;
    private static boolean shutdown = false;
    private static final String STARDOG_HOME = System.getenv("STARDOG_HOME");
    private static final File TEST_HOME = Files.createTempDir();
    protected Connection connection;
    private static final String STARDOG_LICENCE_KEY_FILE_NAME = "stardog-license-key.bin";

    @BeforeClass
    public static void beforeStardogTestClass() throws IOException {

        if(STARDOG == null) {
            shutdown = true;
            TEST_HOME.deleteOnExit();

            copyLicenseFile();
            copyLoggingConfig();

            STARDOG = Stardog.builder()
                    .set(ServerOptions.SECURITY_DISABLED, true)
                    .home(TEST_HOME)
                    .create();

            try (AdminConnection adminConnection = AdminConnectionConfiguration.toEmbeddedServer().connect()) {

                if (adminConnection.list().contains(DB)) {
                    adminConnection.drop(DB);
                }

                adminConnection.newDatabase(DB).create();
            }
        }
    }

    @AfterClass
    public static void afterClass() {
        if (STARDOG != null && shutdown) {
            STARDOG.shutdown();
        }
    }

    private static void copyLicenseFile() throws IOException {
        Files.copy(new File(STARDOG_HOME + "/" + STARDOG_LICENCE_KEY_FILE_NAME),
                new File(TEST_HOME, STARDOG_LICENCE_KEY_FILE_NAME));

    }

    private static void copyLoggingConfig() throws IOException {
        try {
            Files.copy(new File(Resources.getResource("log4j2-test.xml").toURI()), new File(TEST_HOME, "log4j2.xml"));
        } catch (URISyntaxException e1) {

        }
    }

}
