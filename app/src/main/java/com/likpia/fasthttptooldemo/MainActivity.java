package com.likpia.fasthttptooldemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    private EditText edt;
    private ProgressDialog progressDialog;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt = findViewById(R.id.edt_search);
        tv_result = findViewById(R.id.tv_result);

    }

    public void query(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("查询中..");

        BaiduSearchTool baiduSearchTool = new BaiduSearchTool() {
            @Override
            protected void onRequestSuccess(Result result) {
                super.onRequestSuccess(result);
                tv_result.setText(result.getResult());
            }

            @Override
            public void onFail(Exception e) {
                super.onFail(e);
                tv_result.setText("失败");
            }

            @Override
            public void onFinished() {
                super.onFinished();
                progressDialog.dismiss();
            }
        };

        progressDialog.show();
        baiduSearchTool.setWd(edt.getText().toString());
        baiduSearchTool.start();
    }
}
