package com.likpia.fasthttptool.log;

import java.util.Date;

public class Log {
    private Date outTime;
    private String title;
    private String content;
    private String method;
    private int requestType;

    public Log(Date outTime, String title, String content, String method, int requestType) {
        this.outTime = outTime;
        this.title = title;
        this.content = content;
        this.method = method;
        this.requestType = requestType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
