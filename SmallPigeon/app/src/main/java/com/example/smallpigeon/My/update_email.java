package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class update_email extends AppCompatActivity {
    private TextView userEmail;
    private TextView getCode;
    private TextView code_error;
    private EditText checkCode;
    private ImageView back;
    private  String code1;
    private Handler sendEmail = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result.equals("true")){
                Toast.makeText(getApplicationContext(),"验证码发送成功！",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"验证码发送失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Handler secondHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String second = msg.obj + "";
            if(!second.equals("0")){
                getCode.setText(msg.obj + "秒后重新发送");
                getCode.setOnClickListener(null);
            }else{
                code1 = "";
                getCode.setText("获取验证码");
                getCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendEmailToServer();
                    }
                });
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);
        getViews();
        //获取用户已绑定的邮箱
        getUserEmail();
        //返回绑定邮箱界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(update_email.this,anquan.class);
                startActivity(intent4);
                finish();
            }
        });
        //获取验证码
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailToServer();
            }
        });

        checkCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(checkCode.getText().toString().equals(code1)){
                    Intent intent = new Intent(getApplicationContext(),update_emai_final.class);
                    startActivity(intent);
                    finish();
                }else{
                    code_error.setText("验证码输入错误！");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }



    //发送邮件的方法
    public void sendEmailToServer(){
        code1 = "";
        for(int i = 0;i<4;i++){
            code1 += (int)(Math.random()*10) + "";
        }
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/user/verifyCode?userEmail="+userEmail.getText().toString()+"&&code="+code1);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    sendEmail.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        secondDown();
    }

    //邮件的倒计时
    public void secondDown(){
        new Thread(){
            @Override
            public void run() {
                for (int i=59;i>=0;i--){
                    try {
                        Message message = new Message();
                        message.obj = i;
                        message.what = 1;
                        secondHandler.sendMessage(message);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    public void getViews(){
        userEmail=findViewById(R.id.user_email);
        getCode=findViewById(R.id.updemail_getcode);
        code_error=findViewById(R.id.code_error);
        checkCode=findViewById(R.id.updemail_edt_code);
        back=findViewById(R.id.updpwd_back);
    }
    public void getUserEmail(){
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        String s = pre.getString("user_email","");
        userEmail.setText(s);
    }


}
