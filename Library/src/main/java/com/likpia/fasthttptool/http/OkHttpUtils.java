package com.likpia.fasthttptool.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class OkHttpUtils {
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    public static String getGetString(String url, Parameter parameter) throws IOException {
        Request request = getGetRequest(url, parameter);
        return OK_HTTP_CLIENT.newCall(request).execute().body().string();
    }

    public static FormBody getFormBody(Parameter parameter) {
        FormBody.Builder builder = new FormBody.Builder();
        if (parameter == null)
            return builder.build();
        Iterator<Map.Entry<String, Object>> iterator = parameter.map.entrySet().iterator();
        for (; iterator.hasNext(); ) {
            Map.Entry<String, Object> entry = iterator.next();
            if (entry.getKey() == null || entry.getValue() == null)
                continue;
            builder.add(entry.getKey(), entry.getValue().toString());
        }
        return builder.build();
    }

    public static JSONObject getJsonParams(Parameter parameter) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (parameter == null)
            return jsonObject;
        Iterator<Map.Entry<String, Object>> iterator = parameter.map.entrySet().iterator();
        for (; iterator.hasNext(); ) {
            Map.Entry<String, Object> entry = iterator.next();
            if (entry.getKey() == null)
                continue;
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

    public static String getPostString(String url, FormBody.Builder body) throws IOException {

        return OK_HTTP_CLIENT.newCall(getPostRequest(url, body.build())).execute().body().string();
    }

    public static Request getGetRequest(String url, Parameter parameter, Headers headers) {
        if (parameter != null) {
            url = url.concat("?");
            Iterator<Map.Entry<String, Object>> iterator = parameter.map.entrySet().iterator();
            for (; iterator.hasNext(); ) {
                Map.Entry<String, Object> entry = iterator.next();
                if (entry.getValue() != null)
                    url = url.concat(entry.getKey()).concat("=").concat(entry.getValue().toString()) + "&";
            }
            url = url.substring(0, url.length() - 1);
        }

        Request.Builder builder = new Request.Builder().url(url).get();
        if (headers != null)
            builder.headers(headers);

        return builder.build();

    }

    public static Request getGetRequest(String url, Parameter parameter) {


        return getGetRequest(url, parameter, null);

    }

    public static Request getPostRequest(String url, Parameter parameter) {
        return getPostRequest(url, parameter, null);
    }

    public static Request getPostRequest(String url, Parameter parameter, Headers headers) {
        Request.Builder builder = new Request.Builder().url(url).post(getFormBody(parameter));
        if (headers != null) {
            builder.headers(headers);
        }
        return builder.build();
    }

    public static Request getPostRequestByJson(String url, Parameter parameter, Headers headers) throws JSONException {

        String bodyString = JSON.toJSONString(getJsonParams(parameter), SerializerFeature.WriteMapNullValue);
        Request.Builder builder = new Request.Builder().url(url).post(FormBody.create(MediaType.parse("application/json;charset=utf-8"), bodyString));
        if (headers != null) {
            builder.headers(headers);
        }
        return builder.build();
    }

    public static Request getPostRequest(String url, RequestBody builder) {

        return new Request.Builder().url(url).post(builder).build();
    }

    public static Request getPostRequest(String url, MultipartBody multipartBody) {
        return new Request.Builder().url(url).post(multipartBody).build();
    }


    public static class Parameter {
        Map<String, Object> map = new HashMap<>();


        public static Parameter create() {
            return new Parameter();
        }

        public Parameter add(String key, Object val) {
            map.put(key, val);
            return this;
        }

        public Parameter add(String key, JSONArray val) {
            map.put(key, val);
            return this;
        }

        public Parameter addAll(Parameter parameter) {
            Iterator<Map.Entry<String, Object>> iterator = parameter.map.entrySet().iterator();
            while (iterator.hasNext()) {

                Map.Entry<String, Object> entry = iterator.next();
                map.put(entry.getKey(), entry.getValue());
            }

            return this;
        }

    }

}
