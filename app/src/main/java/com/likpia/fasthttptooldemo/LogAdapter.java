package com.likpia.fasthttptooldemo;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;
import android.widget.Toast;


import com.likpia.fasthttptool.log.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class LogAdapter extends BaseAdapter {
    private Activity activity;

    public void update(List<Log> logs) {
        this.list.clear();
        if (logs != null)
            this.list.addAll(logs);
        notifyDataSetChanged();
    }

    private class Holder {
        public TextView timeTv;
        public TextView titleTv;
        public TextView contentTv;
        public TextView methodTv;
        public TextView tv_request_type;
        public View itemView;

        public Holder(View itemView) {
            this.itemView = itemView;
            timeTv = itemView.findViewById(R.id.tv_time);
            contentTv = itemView.findViewById(R.id.tv_content);
            titleTv = itemView.findViewById(R.id.action_bar_title);
            methodTv = itemView.findViewById(R.id.tv_manage);
            tv_request_type = itemView.findViewById(R.id.tv_request_type);
        }
    }

    private List<Log> list = new ArrayList<>();

    public LogAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Log getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_debug_log, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Log log = getItem(position);
        holder.titleTv.setText(log.getTitle());
        if (log.getRequestType() == 0) {
            holder.tv_request_type.setText("↑");
            holder.tv_request_type.setTextColor(Color.BLUE);
        } else if (log.getRequestType() == 1) {
            holder.tv_request_type.setText("↓");
            holder.tv_request_type.setTextColor(Color.GREEN);
        }
        holder.methodTv.setText(log.getMethod());
        if (log.getContent().length() > 100)
            holder.contentTv.setText(log.getContent().substring(0, 100).replace("\n", ""));
        else {
            holder.contentTv.setText(log.getContent().replace("\n", ""));
        }
        holder.timeTv.setText(SimpleDateFormat.getDateTimeInstance().format(log.getOutTime()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity).setTitle(getItem(position).getTitle()).setMessage(getItem(position).getContent()).setPositiveButton("复制内容", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ClipboardManager manager = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                            manager.setPrimaryClip(ClipData.newPlainText("text", getItem(position).getContent()));
                            Toast.makeText(activity, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(activity, "复制失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("返回", null).show();
            }
        });
        return convertView;
    }
}
