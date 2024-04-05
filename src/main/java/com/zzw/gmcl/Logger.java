package com.zzw.gmcl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static Logger instance;
    private StringBuilder logBuilder;

    private Logger() {
        logBuilder = new StringBuilder();
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public synchronized void log(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        logBuilder.append(timestamp).append(" - ").append(message).append("\n");
    }

    public synchronized String getLog() {
        return logBuilder.toString();
    }
}
