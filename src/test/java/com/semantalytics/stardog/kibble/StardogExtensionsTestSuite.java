package com.semantalytics.stardog.kibble;

import com.complexible.common.protocols.server.Server;
import com.complexible.common.protocols.server.ServerException;
import com.complexible.stardog.Stardog;
import com.complexible.stardog.StardogConfiguration;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;
import com.google.common.io.Files;
import com.semantalytics.stardog.kibble.array.ArrayTestSuite;
import com.semantalytics.stardog.kibble.console.ConsoleTestSuite;
import com.semantalytics.stardog.kibble.geo.geohash.GeohashTestSuite;
import com.semantalytics.stardog.kibble.string.*;
import com.semantalytics.stardog.kibble.string.comparison.StringMetricTestSuite;
import com.semantalytics.stardog.kibble.string.emoji.EmojiTestSuite;
import com.semantalytics.stardog.kibble.string.escape.EscapeTestSuite;
import com.semantalytics.stardog.kibble.string.phonetic.PhoneticTestSuite;
import com.semantalytics.stardog.kibble.string.unescape.UnescapeTestSuite;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

@RunWith(Suite.class)
@SuiteClasses({
        ArrayTestSuite.class,
        ConsoleTestSuite.class,
        GeohashTestSuite.class,
        StringMetricTestSuite.class,
        EmojiTestSuite.class,
        EscapeTestSuite.class,
        PhoneticTestSuite.class,
        UnescapeTestSuite.class,
        StringTestSuite.class,
        })

public class StardogExtensionsTestSuite extends TestCase {

    private static Stardog STARDOG;
    private static Server SERVER;
    public static final String DB = "test";
	public static final int TEST_PORT = 5888;
	protected Connection connection;
    private static final String STARDOG_LICENSE_PATH = System.getenv("STARDOG_LICENSE_PATH");

    @BeforeClass
    public static void beforeClass() throws IOException, ServerException {

        final File TEST_HOME;

        TEST_HOME = Files.createTempDir();
        TEST_HOME.deleteOnExit();

        STARDOG = Stardog.builder()
                .set(StardogConfiguration.LICENSE_LOCATION, STARDOG_LICENSE_PATH)
                .home(TEST_HOME).create();

        SERVER = STARDOG.newServer()
                .bind(new InetSocketAddress("localhost", TEST_PORT))
                .start();

        final AdminConnection adminConnection = AdminConnectionConfiguration.toEmbeddedServer()
                .credentials("admin", "admin")
                .connect();

        if (adminConnection.list().contains(DB)) {
            adminConnection.drop(DB);
        }

        adminConnection.newDatabase(DB).create();
    }

    @AfterClass
    public static void afterClass() {
        if (SERVER != null) {
            SERVER.stop();
        }
        STARDOG.shutdown();
    }
}
