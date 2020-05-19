package com.semantalytics.stardog.kibble.console;

import com.complexible.common.protocols.server.Server;
import com.complexible.common.protocols.server.ServerException;
import com.complexible.stardog.Stardog;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;
import com.google.common.io.Files;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestBackgroundBlack.class,
        TestBackgroundBlue.class,
        TestBackgroundBrightBlack.class,
        TestBackgroundBrightBlue.class,
        TestBackgroundBrightCyan.class,
        TestBackgroundBrightGreen.class,
        TestBackgroundBrightMagenta.class,
        TestBackgroundBrightRed.class,
        TestBackgroundBrightWhite.class,
        TestBackgroundBrightYellow.class,
        TestBackgroundCyan.class,
        TestBackgroundGreen.class,
        TestBackgroundMagenta.class,
        TestBackgroundRed.class,
        TestBackgroundWhite.class,
        TestBackgroundYellow.class,
        TestBlinkFast.class,
        TestBlinkOff.class,
        TestBlinkSlow.class,
        TestBold.class,
        TestBoldOff.class,
        TestConceal.class,
        TestConcealOff.class,
        TestConsole.class,
        TestEraseLine.class,
        TestEraseScreen.class,
        TestForegroundBlack.class,
        TestForegroundBlue.class,
        TestForegroundCyan.class,
        TestForegroundDefault.class,
        TestForegroundGreen.class,
        TestForegroundMagenta.class,
        TestForegroundRed.class,
        TestForegroundWhite.class,
        TestForegroundYellow.class,
        TestItalic.class,
        TestItalicOff.class,
        TestNegative.class,
        TestNegativeOff.class,
        TestRender.class,
        TestReset.class,
        TestStrikethrough.class,
        TestStrikethroughOff.class,
        TestUnderline.class,
        TestUnderlineOff.class
})

public class ConsoleTestSuite extends TestCase {

    private static Stardog STARDOG;
    private static Server SERVER;
    public static final String DB = "test";
    public static final int TEST_PORT = 5888;
    private static final String STARDOG_HOME = System.getenv("STARDOG_HOME");
    protected Connection connection;
    private static final String STARDOG_LICENCE_KEY_FILE_NAME = "stardog-license-key.bin";

    @BeforeClass
    public static void beforeClass() throws IOException, ServerException {

        final File TEST_HOME;

        TEST_HOME = Files.createTempDir();
        TEST_HOME.deleteOnExit();

        Files.copy(new File(STARDOG_HOME + "/" + STARDOG_LICENCE_KEY_FILE_NAME),
                new File(TEST_HOME, STARDOG_LICENCE_KEY_FILE_NAME));

        STARDOG = Stardog.builder().home(TEST_HOME).create();

        SERVER = STARDOG.newServer()
                //.set(ServerOptions.SECURITY_DISABLED, true)
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
