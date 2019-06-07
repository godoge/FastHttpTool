package com.likpia.fasthttptool.http;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;


public class SimpleAsyncHandle {
    public static final int ACTION_CODE_ERROR = -1;
    public static final int ACTION_CODE_FAILED = 0;
    public static final int ACTION_CODE_SUCCESS = 1;
    private static final int MSG_WHAT_ON_FINISH = 1;
    private static final int MSG_WHAT_ON_PUSH = 2;
    private DataBundle bundle = new DataBundle();
    private Context context;
    private Handler handler;
    private OnGetDataListener mOnGetDataListener;
    private Object[] sendValue;

    public Context getContext() {
        return context;
    }

    public void start(OnGetDataListener mOnGetDataListener) {
        mOnGetDataListener.setValue(sendValue);
        this.mOnGetDataListener = mOnGetDataListener;
        Thread thread = new Thread() {
            @Override
            public void run() {
                int actionCode = SimpleAsyncHandle.this.mOnGetDataListener.onNetworkHandle(bundle);
                Message message = new Message();
                message.what = MSG_WHAT_ON_FINISH;
                message.arg1 = actionCode;
                handler.sendMessage(message);

            }
        };
        mOnGetDataListener.setHandle(handler);
        mOnGetDataListener.onPrepare(context);

        thread.start();
    }

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    public static class Builder {
        SimpleAsyncHandle getDataHandle;

        public Builder(Context context) {
            getDataHandle = new SimpleAsyncHandle(context);
        }

        public void start(OnGetDataListener mOnGetDataListener) {
            getDataHandle.start(mOnGetDataListener);

        }

        public SimpleAsyncHandle sendValue(Object... value) {
            getDataHandle.sendValue(value);
            return getDataHandle;
        }

    }


    public static class DataBundle {
        private Map<String, Object> dataMap;
        private Object data;
        private String stringData;
        private Integer intData;
        private Long longData;

        public void put(String key, Object value) {
            if (dataMap == null)
                dataMap = new HashMap<>();
            dataMap.put(key, value);
        }

        public Object get(String key, Object defVal) {
            if (dataMap == null)
                return defVal;
            Object obj = dataMap.get(key);
            if (obj == null)
                return defVal;
            return obj;
        }

        public void putString(String stringData) {
            this.stringData = stringData;
        }

        public int getInteger(int defVal) {
            if (intData == null)
                return defVal;
            return intData;
        }


        public void putLong(long longData) {
            this.longData = longData;
        }

        public long getLong(long val) {
            if (longData == null)
                return val;
            return longData;
        }

        public void putInteger(int intData) {

            this.intData = intData;
        }

        public String getString() {
            return stringData;
        }

        public String getString(String defVal) {
            if (stringData == null)
                return defVal;
            return stringData;
        }

        public void putObj(Object data) {
            this.data = data;
        }

        public Object getObj() {
            return data;
        }

        public Object get(String key) {
            return dataMap.get(key);
        }

    }

    public void sendValue(Object... backValue) {
        this.sendValue = backValue;
    }


    public SimpleAsyncHandle(final Context context) {
        this.context = context;
        handler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_WHAT_ON_PUSH:
                        mOnGetDataListener.onPushed(msg.obj, context);
                        break;
                    case MSG_WHAT_ON_FINISH:
                        mOnGetDataListener.onFinished(msg.arg1, bundle, SimpleAsyncHandle.this.context);
                        break;
                }


            }
        };

    }


    public static abstract class OnGetDataListener {
        private Handler handler;
        private Object[] sendValue;

        private void setValue(Object... value) {
            sendValue = value;
        }

        public Object receiveValue(int index) {
            return sendValue[index];
        }


        public abstract int onNetworkHandle(DataBundle bundle);

        public void onPrepare(Context context) {
        }

        private void setHandle(Handler handler) {

            this.handler = handler;
        }

        protected final void push(Object obj) {
            Message msg = new Message();
            msg.obj = obj;
            msg.what = MSG_WHAT_ON_PUSH;
            handler.sendMessage(msg);
        }

        public void onPushed(Object object, Context context) {
        }

        public void onFinished(int actionCode, DataBundle bundle, Context context) {
        }

    }
}
