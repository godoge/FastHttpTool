package com.likpia.fasthttptool.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogManager {
    private static LogManager logManager = new LogManager();
    private List<Log> logs = new ArrayList<>();

    public static LogManager getLogManager() {
        return logManager;
    }

    public void addLog(String method, Date date, String title, String content, int requestType) {
        logs.add(0, new Log(date, title, content, method, requestType));
    }

    public List<Log> getLogs() {
        return logs;
    }
}
