package com.zeonic.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText tel;
    EditText pwd;
    EditText code;
    Button get;
    Button login;
    Button reg;
    TextView tv;

    public static String userLogin(String username, String password) {
        HttpURLConnection conn = null;
        try {
            URL mURL = new URL("http://115.159.117.234/user/login");
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            String data = "phone_num=" + username + "&passwd=" + password;
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.http);
        tel = (EditText) findViewById(R.id.tel);
        pwd = (EditText) findViewById(R.id.pwd);
        code = (EditText) findViewById(R.id.code);
        get = (Button) findViewById(R.id.get);
        login = (Button) findViewById(R.id.login);
        reg = (Button) findViewById(R.id.reg);
        tv = (TextView) findViewById(R.id.tv_result);
        get.setOnClickListener(this);
        login.setOnClickListener(this);
        reg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                final String phone_num = tel.getText().toString();
                final String passwd = pwd.getText().toString();
                if (TextUtils.isEmpty(phone_num) || TextUtils.isEmpty(passwd)) {
                    Toast.makeText(this, "手机号或者密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    // 开启子线程
                    new Thread() {
                        public void run() {
                            loginByPost(phone_num, passwd); // 调用loginByPost方法
                        }
                    }.start();
                }
                break;
            case R.id.reg:
                loginOfGet();
                break;
        }
    }

    public void loginByPost(String userName, String userPass) {

        try {

            // 请求的地址
            String spec = "http://115.159.117.234/user/login";
            // 根据地址创建URL对象
            URL url = new URL(spec);
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            // 传递的数据
            String data = "phone_num=" + URLEncoder.encode(userName, "UTF-8")
                    + "&passwd=" + URLEncoder.encode(userPass, "UTF-8");
            // 设置请求的头
            urlConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(data.getBytes().length));
            // 设置请求的头
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
            //setDoInput的默认值就是true
            //获取输出流
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                // 返回字符串
                final String result = new String(baos.toByteArray());

                // 通过runOnUiThread方法进行修改主线程的控件内容
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 在这里把返回的数据写在控件上 会出现什么情况尼
                        tv.setText(result);
                    }
                });

            } else {
                System.out.println("链接失败.........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String loginOfGet() {
        HttpURLConnection conn = null;

        String url = "http://115.159.117.234/payment/ticket";
        try {
            // 利用string url构建URL对象
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }
//    Toolbar toolbar;
//    ImageView paymentImageView;
//    //    PhotoViewAttacher mAttacher;
//    private Fragment fragment;
//    private boolean show = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.wallet_activity);
//
//
////        toolbar =  (Toolbar)findViewById(R.id.line_detail_toolbar);
////        setSupportActionBar(toolbar);
////        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        paymentImageView = (ImageView) findViewById(R.id.paymentImageView);
//        paymentImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
//                startActivity(intent);
//
//            }
//        });
//    }
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(show) {
//
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(DensityUtils.dp2px(getApplication(), 100),DensityUtils.dp2px(getApplicationContext(), 100)));
//                    imageView.setAdjustViewBounds(true);
//                    show = false;
//                }else{
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(400,400));
//
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                    show = true;
//                }
//            }
//        });

    //    }

}
