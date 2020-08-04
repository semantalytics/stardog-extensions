package com.semantalytics.stardog.kibble;

import com.complexible.common.protocols.server.Server;
import com.complexible.common.protocols.server.ServerException;
import com.complexible.stardog.Stardog;
import com.complexible.stardog.StardogConfiguration;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.StardogLicense;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;


public abstract class AbstractStardogTest {

    private static Stardog STARDOG;
    private static Server SERVER;
    private static final String DB = "test";
    private static int TEST_PORT = 5888;
    private static final String STARDOG_LICENSE_PATH = System.getenv("STARDOG_LICENSE_PATH");
    protected Connection connection;

    @BeforeClass
    public static void beforeClass() throws IOException, ServerException {
       AdminConnection adminConnection;
       try{
           adminConnection = AdminConnectionConfiguration.toEmbeddedServer()
                .credentials("admin", "admin")
                .connect();
       } catch(StardogException e) {


        final File TEST_HOME = Files.createTempDir();
        TEST_HOME.deleteOnExit();

        try {
            Files.copy(new File(Resources.getResource("log4j2-test.xml").toURI()), new File(TEST_HOME, "log4j2.xml"));
        } catch(URISyntaxException e1) {

        }

        STARDOG = Stardog.builder()
                         .set(StardogConfiguration.LICENSE_LOCATION, STARDOG_LICENSE_PATH)
                         .home(TEST_HOME).create();

        SERVER = STARDOG.newServer()
                .bind(new InetSocketAddress("localhost", TEST_PORT)).start();

        adminConnection = AdminConnectionConfiguration.toEmbeddedServer()
                .credentials("admin", "admin")
                .connect();

        if (adminConnection.list().contains(DB)) {
            adminConnection.drop(DB);
        } else {
            adminConnection.newDatabase(DB).create();
        }
       }
    }

    @AfterClass
    public static void afterClass() {
        if (SERVER != null) {
            SERVER.stop();
            STARDOG.shutdown();
        }
    }

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
}
