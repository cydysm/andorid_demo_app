package www.csdn.net.lesson03;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class LoginActivity extends Activity {
    // 声明控件对象
    private EditText et_name;

    // 声明控件对象
    private EditText et_pass;

    // 声明显示返回数据库的控件对象
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置显示的视图
        setContentView(R.layout.activity_login);
        // 通过 findViewById(id)方法获取用户名的控件对象
        et_name = (EditText) findViewById(R.id.et_name);
        // 通过 findViewById(id)方法获取用户密码的控件对象
        et_pass = (EditText) findViewById(R.id.et_pass);

        // 通过 findViewById(id)方法获取显示返回数据的控件对象
        tv_result = (TextView) findViewById(R.id.tv_result);
    }

    /**
     * 通过android:onClick="login"指定的方法 ， 要求这个方法中接受你点击控件对象的参数v
     *
     * @param v
     */
    public void login(View v) {
        // 获取点击控件的id
        int id = v.getId();

        // 根据id进行判断进行怎么样的处理
        switch (id) {
            // 登陆事件的处理
            case R.id.btn_login:

                // 获取用户名
                final String userName = et_name.getText().toString();

                // 获取用户密码
                final String userPass = et_pass.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPass)) {
                    Toast.makeText(this, "用户名或者密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    // 开启子线程
                    new Thread() {
                        public void run() {
                            loginByPost(userName, userPass); // 调用loginByPost方法
                        }

                        ;
                    }.start();
                }

                break;
        }
    }

    /**
     * POST请求操作
     *
     * @param userName
     * @param userPass
     */
    public void loginByPost(String userName, String userPass) {
        try {
            // 请求的地址
            String spec = "http://172.16.237.200:8080/video/login.do";

            // 根据地址创建URL对象
            URL url = new URL(spec);

            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);

            // 传递的数据
            String data = "username=" + URLEncoder.encode(userName, "UTF-8") +
                    "&userpass=" + URLEncoder.encode(userPass, "UTF-8");
            // 设置请求的头
            urlConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(data.getBytes().length));
            // 设置请求的头
            urlConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

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
                byte[] buffer = new byte[1024];

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
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 在这里把返回的数据写在控件上 会出现什么情况尼
                        tv_result.setText(result);
                    }
                });
            } else {
                System.out.println("链接失败.........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
