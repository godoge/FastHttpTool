package com.likpia.fasthttptool.http;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.likpia.fasthttptool.ApiResult;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Request;

public abstract class MyRequestTool extends NewBaseTool {

    public static final int METHOD_POST = 1;
    public static final int METHOD_GET = 0;
    private static Context context;
    protected OkHttpUtils.Parameter parameter = new OkHttpUtils.Parameter();

    public void pullParameter(OkHttpUtils.Parameter parameter) {
        MyRequestTool.this.parameter.addAll(parameter);
    }

    @Override
    public String getTitle() {
        return getClass().getSuperclass().getSimpleName();
    }


    public boolean isJsonParams() {
        return false;
    }


    protected abstract void onRequestSuccess(Result result);

    public void toRawContent(String rawContent) {
    }


    abstract public void onFinished();

    abstract public void onFail(Exception e);

    abstract public String getUrl();

    abstract public int getMethod();

    protected Headers getApiHeaders() {

        return null;

    }


    protected Result netPost(Request request) throws IOException, JSONException {

        Result result = post(request);
        toRawContent(result.getResult());
        return result;


    }

    protected Result netGet(Request request) throws IOException, JSONException {
        Result result = get(request);
        toRawContent(result.getResult());
        return result;

    }

    public int netHandle(SimpleAsyncHandle.DataBundle bundle) throws Exception {

        pullParams(parameter);
        if (getMethod() == METHOD_GET) {


            bundle.putObj(netGet(OkHttpUtils.getGetRequest(getUrl(), parameter, getApiHeaders())));

            return 1;

        } else if (getMethod() == METHOD_POST) {
            if (isJsonParams()) {
                bundle.putObj(netPost(OkHttpUtils.getPostRequestByJson(getUrl(), parameter, getApiHeaders())));
            } else
                bundle.putObj(netPost(OkHttpUtils.getPostRequest(getUrl(), parameter, getApiHeaders())));
            return 1;
        } else
            throw new RuntimeException("method incorrect!!!");
    }


    public ApiResult startSync() throws Exception {
        SimpleAsyncHandle.DataBundle bundle = new SimpleAsyncHandle.DataBundle();
        netHandle(bundle);
        return (ApiResult) bundle.getObj();


    }

    public static void init(Context context) {
        MyRequestTool.context = context;
    }

    public void start() {

        SimpleAsyncHandle.newBuilder(context).start(new SimpleAsyncHandle.OnGetDataListener() {
            @Override
            public int onNetworkHandle(SimpleAsyncHandle.DataBundle bundle) {
                try {
                    return netHandle(bundle);
                } catch (Exception e) {
                    bundle.putObj(e);
                    return -1;
                }

            }

            @Override
            public void onFinished(int actionCode, SimpleAsyncHandle.DataBundle bundle, Context context) {
                super.onFinished(actionCode, bundle, context);
                if (actionCode == 1) {
                    onRequestSuccess((Result) bundle.getObj());
                } else {
                    onFail((Exception) bundle.getObj());
                }
                MyRequestTool.this.onFinished();
            }
        });

    }

    protected abstract void pullParams(OkHttpUtils.Parameter parameter);


}
