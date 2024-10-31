package org.example.nginxparser;

import java.io.*;

public class LogParser {

    public LogParser(String template) {
        // TODO: Implement
    }

    public Log parseLog(String line) {
        // TODO: Implement
        return null;
    }

    public LogList parseLogs(String logs) {
        // TODO: Implement
        return null;
    }

    public LogList parseLogs(File file) {
        // TODO: Implement
        return null;
    }

    public static void main(String[] args) {
        LogPropertyTransformerRepository.addTransformer("status",
                Long::parseLong);
        String schema = "$remote_addr - $remote_user [$time_local] \"$request\" $status $bytes_sent \"$http_referer\" \"$http_user_agent\"";
        LogParser parser = new LogParser(schema);
        LogList logs = parser.parseLogs(
                "127.0.0.1 - - [07/Jul/2018:17:37:28 +0200] \"GET /7d32ce87648a4050faca.hot-update.json HTTP/1.1\" 200 43 \"http://test.local/\" \"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0\"");
        System.out.println(
                logs.where(Condition.greaterOrEqual("status", 200L)));
    }
}
