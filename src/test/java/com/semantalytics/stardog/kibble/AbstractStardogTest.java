package com.semantalytics.stardog.kibble;

import com.complexible.common.protocols.server.Server;
<<<<<<< HEAD
import com.complexible.stardog.Stardog;
import com.complexible.stardog.api.Connection;
=======
import com.complexible.common.protocols.server.ServerException;
import com.complexible.stardog.Stardog;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
>>>>>>> master
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;
import com.google.common.io.Files;
import com.google.common.io.Resources;
<<<<<<< HEAD
import com.stardog.http.server.ServerOptions;
import org.junit.*;

import java.io.File;
import java.io.IOException;
=======
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
>>>>>>> master
import java.net.URISyntaxException;


public abstract class AbstractStardogTest {

<<<<<<< HEAD
    public static Stardog STARDOG;
    private static Server SERVER;
    private static final String DB = "test";
    public static int TEST_PORT = 5888;
    private static boolean shutdown = false;
    private static final String STARDOG_HOME = System.getenv("STARDOG_HOME");
    private static final File TEST_HOME = Files.createTempDir();
=======
    private static Stardog STARDOG;
    private static Server SERVER;
    private static final String DB = "test";
    private static int TEST_PORT = 5888;
    private static final String STARDOG_HOME = System.getenv("STARDOG_HOME");
>>>>>>> master
    protected Connection connection;
    private static final String STARDOG_LICENCE_KEY_FILE_NAME = "stardog-license-key.bin";

    @BeforeClass
<<<<<<< HEAD
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
=======
    public static void beforeClass() throws IOException, ServerException {
       AdminConnection adminConnection;
       try{
           adminConnection = AdminConnectionConfiguration.toEmbeddedServer()
                .credentials("admin", "admin")
                .connect();
       } catch(StardogException e) {


        final File TEST_HOME = Files.createTempDir();
        TEST_HOME.deleteOnExit();

        Files.copy(new File(STARDOG_HOME + "/" + STARDOG_LICENCE_KEY_FILE_NAME),
                new File(TEST_HOME, STARDOG_LICENCE_KEY_FILE_NAME));

        try {
            Files.copy(new File(Resources.getResource("log4j2-test.xml").toURI()), new File(TEST_HOME, "log4j2.xml"));
        } catch(URISyntaxException e1) {

        }

        STARDOG = Stardog.builder().home(TEST_HOME).create();

        SERVER = STARDOG.newServer()
                //.set(ServerOptions.SECURITY_DISABLED, true)
                .bind(new InetSocketAddress("localhost", TEST_PORT)).start();
       }
     
        adminConnection = AdminConnectionConfiguration.toEmbeddedServer()
                .credentials("admin", "admin")
                .connect();

        if (adminConnection.list().contains(DB)) {
            adminConnection.drop(DB);
        }

        adminConnection.newDatabase(DB).create();
>>>>>>> master
    }

    @AfterClass
    public static void afterClass() {
<<<<<<< HEAD
        if (STARDOG != null && shutdown) {
=======
        if (SERVER != null) {
            SERVER.stop();
>>>>>>> master
            STARDOG.shutdown();
        }
    }

<<<<<<< HEAD
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

=======
    @Before
    public void setUp() {
        connection = ConnectionConfiguration.to(DB)
                .credentials("admin", "admin")
                .connect();
    }

    @After
    public void tearDown() {
        connection.close();
    }
>>>>>>> master
}
