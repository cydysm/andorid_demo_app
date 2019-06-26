package com.zeonic.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by IrrElephant on 17/6/9.
 */

public class QRCodeDialog extends Dialog{
    public ImageView qrcodeImage;
    public int theme;

    private Bitmap mBitmap;

    public QRCodeDialog(Activity activity, Bitmap qrcodeBmp,int theme) {
        super(activity,theme);
        this.theme = theme;
        this.mBitmap = qrcodeBmp;
    }
    public QRCodeDialog(Activity activity, Bitmap qrcodeBmp) {
        super(activity);
        this.mBitmap = qrcodeBmp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_dialog);
        qrcodeImage = (ImageView) findViewById(R.id.dialog_qrcode);
        qrcodeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        qrcodeImage.setImageBitmap(mBitmap);
    }
}
