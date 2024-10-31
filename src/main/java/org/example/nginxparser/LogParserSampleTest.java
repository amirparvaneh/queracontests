import org.junit.*;

import java.io.*;

import static org.junit.Assert.*;

public class LogParserSampleTest {
    @Test
    public void testParseLog() {
        String schema = "$remote_addr - $remote_user [$time_local] \"$request\" $status $bytes_sent \"$http_referer\" \"$http_user_agent\"";
        LogParser parser = new LogParser(schema);

        String expectedLog = "Log[remote_addr=127.0.0.1, remote_user=-, time_local=07/Jul/2018:17:37:28 +0200, request=GET /7d32ce87648a4050faca.hot-update.json HTTP/1.1, status=200, bytes_sent=43, http_referer=http://test.local/, http_user_agent=Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0]";
        Log actualLog = parser.parseLog(
                "127.0.0.1 - - [07/Jul/2018:17:37:28 +0200] \"GET /7d32ce87648a4050faca.hot-update.json HTTP/1.1\" 200 43 \"http://test.local/\" \"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0\"");
        assertEquals(expectedLog, actualLog.toString());

        schema = "x $remote_addr";
        parser = new LogParser(schema);

        expectedLog = "Log[remote_addr=127.0.0.1]";
        actualLog = parser.parseLog(
                "x 127.0.0.1");
        assertEquals(expectedLog, actualLog.toString());
    }

    @Test
    public void testParseLogsString() {
        String schema = "$remote_addr - $remote_user [$time_local] \"$request\" $status $bytes_sent \"$http_referer\" \"$http_user_agent\"";
        LogParser parser = new LogParser(schema);

        String expectedLog = "[Log[remote_addr=127.0.0.1, remote_user=-, time_local=07/Jul/2018:17:37:28 +0200, request=GET /7d32ce87648a4050faca.hot-update.json HTTP/1.1, status=200, bytes_sent=43, http_referer=http://test.local/, http_user_agent=Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0], Log[remote_addr=185.232.135.11, remote_user=-, time_local=07/Jul/2022:17:37:28 +0200, request=POST /7d32ce87648a4050faca.hot-update.json HTTP/2, status=200, bytes_sent=43, http_referer=http://tests.local/, http_user_agent=Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/61.0]]";
        LogList actualLog = parser.parseLogs(
                "127.0.0.1 - - [07/Jul/2018:17:37:28 +0200] \"GET /7d32ce87648a4050faca.hot-update.json HTTP/1.1\" 200 43 \"http://test.local/\" \"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0\"\n185.232.135.11 - - [07/Jul/2022:17:37:28 +0200] \"POST /7d32ce87648a4050faca.hot-update.json HTTP/2\" 200 43 \"http://tests.local/\" \"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/61.0\"");
        assertEquals(expectedLog, actualLog.toString());

        schema = "a $a b";
        parser = new LogParser(schema);

        expectedLog = "[Log[a=b], Log[a=c]]";
        actualLog = parser.parseLogs(
                "a b b\na c b");
        assertEquals(expectedLog, actualLog.toString());
    }

    @Test
    public void testParseLogsFile() {
        String schema = "$remote_addr - $remote_user [$time_local] \"$request\" $status $bytes_sent \"$http_referer\" \"$http_user_agent\"";
        LogParser parser = new LogParser(schema);

        String expectedLog = "[Log[remote_addr=127.0.0.1, remote_user=-, time_local=07/Jul/2018:17:37:28 +0200, request=GET /7d32ce87648a4050faca.hot-update.json HTTP/1.1, status=200, bytes_sent=43, http_referer=http://test.local/, http_user_agent=Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0], Log[remote_addr=185.232.135.11, remote_user=-, time_local=07/Jul/2022:17:37:28 +0200, request=POST /7d32ce87648a4050faca.hot-update.json HTTP/2, status=200, bytes_sent=43, http_referer=http://tests.local/, http_user_agent=Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/61.0]]";
        String filePath = "access_log";
        writeStringToFile(filePath,
                "127.0.0.1 - - [07/Jul/2018:17:37:28 +0200] \"GET /7d32ce87648a4050faca.hot-update.json HTTP/1.1\" 200 43 \"http://test.local/\" \"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0\"\n185.232.135.11 - - [07/Jul/2022:17:37:28 +0200] \"POST /7d32ce87648a4050faca.hot-update.json HTTP/2\" 200 43 \"http://tests.local/\" \"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/61.0\"");
        LogList actualLog = parser.parseLogs(new File(filePath));
        new File(filePath).delete();
        assertEquals(expectedLog, actualLog.toString());

        schema = "a $a b";
        parser = new LogParser(schema);

        expectedLog = "[Log[a=b], Log[a=c]]";
        filePath = "access_log.log";
        writeStringToFile(filePath, "a b b\na c b");
        actualLog = parser.parseLogs(new File(filePath));
        new File(filePath).delete();
        assertEquals(expectedLog, actualLog.toString());
    }

    private static void writeStringToFile(String filePath, String str) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath))) {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}