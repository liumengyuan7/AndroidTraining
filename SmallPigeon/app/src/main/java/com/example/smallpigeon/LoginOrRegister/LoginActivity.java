package com.example.smallpigeon.LoginOrRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    private ImageView loginReturn;
    private TextView accountRegister;
    private TextView forgetPassword;
    private EditText username;
    private EditText password;
    private Button userLogin;
    private String md5Pass;
    private Handler handlerLogin = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj+"";
            if(result.equals("false")){
                Toast.makeText(getApplicationContext(),"登录失败！",Toast.LENGTH_SHORT).show();
            }else{
//                Gson gson = new Gson();
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.get("attrs");
//                System.out.println(jsonObject.toString());
                Toast.makeText(getApplicationContext(),"登录成功！",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取控件
        getViews();
        //按钮的点击事件
        btnEvents();

    }

    //获取视图的控件
    private void getViews() {
        accountRegister = findViewById(R.id.account_register);
        forgetPassword = findViewById(R.id.forget_password);
        userLogin = findViewById(R.id.user_login);
        username = findViewById(R.id.username);
        loginReturn = findViewById(R.id.loginReturn);
        password = findViewById(R.id.password);
    }

    //按钮的点击事件
    private void btnEvents() {

        accountRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(password.getText().toString().getBytes());
                    md5Pass = new BigInteger(1, md.digest()).toString(16);
                    Log.e("md5",md5Pass);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                                    +":8080/smallpigeon/user/userLogin?username="+username.getText().toString()
                                    +"&&password="+md5Pass);
                            URLConnection conn = url.openConnection();
                            InputStream in = conn.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                            String result = reader.readLine();
                            Message message = new Message();
                            message.obj = result;
                            message.what = 2;
                            handlerLogin.sendMessage(message);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        loginReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
