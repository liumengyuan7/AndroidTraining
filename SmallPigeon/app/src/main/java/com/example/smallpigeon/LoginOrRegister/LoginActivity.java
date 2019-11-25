package com.example.smallpigeon.LoginOrRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {
    private TextView accountRegister;
    private TextView forgetPassword;
    private EditText username;
    private EditText password;
    private Button userLogin;
    private Handler handlerLogin = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj+"";
            if(result.equals("true")){
                Toast.makeText(getApplicationContext(),"登录成功！",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"登录失败！",Toast.LENGTH_SHORT).show();
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

    private void getViews() {
        accountRegister = findViewById(R.id.account_register);
        forgetPassword = findViewById(R.id.forget_password);
        userLogin = findViewById(R.id.user_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

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
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                                    +":8080/SmallPigeon/user/userLogin?username="+username.getText().toString()
                                    +"&&password="+password.getText().toString());
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
    }

}
