package com.zeonic.myapplication;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import java.util.logging.Handler;

/**
 * Created by IrrElephant on 17/6/7.
 */

public class PaymentActivity extends AppCompatActivity {
    private ImageView paymentBack;
    private Handler handler;
    Bitmap qrcodeBmp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        final String text = "Y60vM9Zz5cnOKc6YydvtmMd8m/jMCaa2q2aQObhw5Y4r/jsyXSXQaCVFmuDMluSuSdxAgcFeip6dGjsFNJ+yoKGq/Own6beGVbMH1/t9y5jXkvfkY8hH678dpAmMitHySoC6dbZhp1g3PzTjZ8ULIqxnhMw4LlX6x8IP12hncTM=";
        final String text1 = "1";
        final ImageView imageView = (ImageView) findViewById(R.id.payment_qrcode);
        Bitmap logo = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.ic_logo);
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        ImageView imageView1 = (ImageView) findViewById(R.id.img2);

        try {
            qrcodeBmp = QRCode.createCode(getApplicationContext(), text,logo);
            imageView.setImageBitmap(qrcodeBmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog mDialog = new QRCodeDialog(PaymentActivity.this, qrcodeBmp);

//                ImageView mImageView = (ImageView) mDialog.findViewById(R.id.dialog_qrcode);
//                try {
//                    qrcode = QRCode.createCode(getApplicationContext(), text1);
//                    mImageView.setImageBitmap(qrcode);
//                } catch (WriterException e) {
//                    e.printStackTrace();
//                }
                mDialog.show();
//                mImageView.setImageBitmap(qrcodeBmp);
            }
        });
    }




}
