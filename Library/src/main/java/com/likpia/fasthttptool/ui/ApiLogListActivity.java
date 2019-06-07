package com.likpia.fasthttptool.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import com.likpia.fasthttptool.R;
import com.likpia.fasthttptool.log.Log;
import com.likpia.fasthttptool.log.LogManager;

import java.text.SimpleDateFormat;
import java.util.List;

public class ApiLogListActivity extends Activity {
    private LogAdapter adapter = null;
    private SwipeRefreshLayout layout;
    private TextView hint;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void load() {
        List<Log> logs = LogManager.getLogManager().getLogs();
        adapter.update(logs);
        if (logs.isEmpty())
            hint.setText("无运行信息");
        layout.setRefreshing(false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_debug_log);
        hint = findViewById(R.id.hint);
        adapter = new LogAdapter(this);
        ListView lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);
        layout = findViewById(R.id.refresh);
        load();
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
    }

}
