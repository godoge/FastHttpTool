package com.likpia.fasthttptool.http;


import android.util.Log;

import com.likpia.fasthttptool.log.LogManager;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class NewBaseTool {

    protected OkHttpClient client;

    public abstract String getTitle();

    public NewBaseTool() {
        client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.MINUTES).readTimeout(10, TimeUnit.MINUTES).build();
    }

    protected Result get(Request request) throws IOException {
        String params = null;
        if (request.body() instanceof MultipartBody) {


        } else if (request.body() instanceof FormBody) {

            FormBody formBody = (FormBody) request.body();

            for (int i = 0; i < formBody.size(); i++) {
                params = params.concat(formBody.name(i)).concat(" -> ").concat(formBody.value(i) + "\n");
            }


        }
        if (params != null)
            LogManager.getLogManager().addLog("GET", new Date(), getTitle(), request.toString() + "\n\n请求参数：\n" + params, 0);
        else
            LogManager.getLogManager().addLog("GET", new Date(), getTitle(), request.toString(), 0);
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        LogManager.getLogManager().addLog("GET", new Date(), getTitle(), request.toString() + "\n\n响应内容：\n" + result, 1);

        return new Result(response.code(), result);
    }

    public static class Result {
        private int state;
        private String result;

        public int getState() {
            return state;
        }

        public String getResult() {
            return result;
        }

        public Result(int state, String result) {
            this.state = state;
            this.result = result;
        }
    }

    protected Result post(Request request) throws IOException {
        if (request.body() instanceof MultipartBody) {


        } else if (request.body() instanceof FormBody) {

            FormBody formBody = (FormBody) request.body();
            String params = "";
            for (int i = 0; i < formBody.size(); i++) {
                params = params.concat(formBody.name(i)).concat(" -> ").concat(formBody.value(i) + "\n");
            }
            LogManager.getLogManager().addLog("POST", new Date(), getTitle(), request.toString() + "\n\n请求参数：\n" + params, 0);

        }

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        Log.i("API_RESULT_POST -> " + getClass().getCanonicalName(), result);
        LogManager.getLogManager().addLog("POST", new Date(), getTitle(), request.toString() + "\n\n响应内容：\n" + result, 1);
        return new Result(response.code(), result);
    }


}
