package com.zeonic.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.transitionseverywhere.TransitionManager;

/**
 * Created by IrrElephant on 17/6/16.
 */

public class Test extends AppCompatActivity {

    public static final int UPDATE_VIEW = 1;
    ProgressDialog progressDialog;
    boolean visible;
    ViewGroup viewGroup;
    TextView text;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法
            progressDialog.dismiss();// 关闭ProgressDialog
            anim(viewGroup, visible, text);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        viewGroup = (ViewGroup) findViewById(R.id.viewGroup);
        text = (TextView) viewGroup.findViewById(R.id.text);
        final Button button = (Button) viewGroup.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(Test.this, "加载中", "Loading");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        spandTimeMethod();// 耗时的方法
                        Message message = new Message();
                        message.what = UPDATE_VIEW;
                        handler.sendMessage(message);// 执行耗时的方法之后发送消给handler
                    }
                }).start();
            }
        });

    }

    private void spandTimeMethod() {
        try {
            Thread.sleep(1000);
            return;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void anim(ViewGroup viewGroup, boolean mboolean, TextView mTextView) {
        TransitionManager.beginDelayedTransition(viewGroup);
        mboolean = !mboolean;
        mTextView.setVisibility(mboolean ? View.VISIBLE : View.GONE);

    }
}

