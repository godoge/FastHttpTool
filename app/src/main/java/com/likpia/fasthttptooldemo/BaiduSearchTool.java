package com.likpia.fasthttptooldemo;

import com.likpia.fasthttptool.http.MyRequestTool;
import com.likpia.fasthttptool.http.OkHttpUtils;

import okhttp3.Headers;

public class BaiduSearchTool extends MyRequestTool {
    @Override
    protected void onRequestSuccess(Result result) {

    }

    @Override
    protected Headers getApiHeaders() {
        return new Headers.Builder()
                .add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36")
                .build();
    }

    @Override
    public void onFinished() {

    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public String getUrl() {
        return "https://www.baidu.com/s";
    }

    @Override
    public int getMethod() {
        return METHOD_GET;
    }

    private String wd;

    public void setWd(String wd) {
        this.wd = wd;
    }

    @Override
    protected void pullParams(OkHttpUtils.Parameter parameter) {

        parameter.add("ie", "UTF-8");
        parameter.add("wd", wd);
    }
}
