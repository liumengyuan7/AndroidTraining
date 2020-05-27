package com.example.smallpigeon.LoginOrRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
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
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//忘记密码-》重置密码
public class ResetPwd extends AppCompatActivity {
    private EditText pwd;
    private EditText checkPwd;
    private TextView lengthError;
    private TextView sameError;
    private ImageView ok;
    private ImageView back;
    private String pwd2;
    private String userId;
    private String userEmail;
    private Handler updatePwd = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result.equals("true")){
                ok.setImageDrawable(getResources().getDrawable(R.drawable.wancheng_green ));
                Toast toast=Toast.makeText(getApplicationContext(),"修改成功，请重新登录！",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
                pre.edit().clear().commit();
                Intent intent3 = new Intent(ResetPwd.this, LoginActivity.class);
                startActivity(intent3);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"修改失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        getViews();
        length();
        same();
        //返回到登录界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //将新密码发送方法
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = getIntent();
                    userEmail = intent.getStringExtra("user_email");
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(pwd2.getBytes());
                    pwd2 = new BigInteger(1, md.digest()).toString(16);
                    Log.e("md5",pwd2);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                updatePwd();
            }
        });

    }
    public void updatePwd(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/user/forgetPassword?password="+pwd2+"&&userEmail="+userEmail);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    updatePwd.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void getViews() {
    pwd=findViewById(R.id.forget_newPwd);
    checkPwd=findViewById(R.id.forget_checkPwd);
    lengthError=findViewById(R.id.length_error);
    sameError=findViewById(R.id.same_error);
    ok=findViewById(R.id.btn_FinishForget);
    back=findViewById(R.id.forget_back);

    }
    public void length(){
        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str=pwd.getText().toString();
                Log.e("密码"," "+str);
                if(str.length()!=0&&str.length()<6){
                    lengthError.setText("密码长度不可小于6位");
                }
                else {
                    lengthError.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void  same(){
        checkPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str=checkPwd.getText().toString();
                String str1=pwd.getText().toString();
                Log.e("密码"," "+str);
                if(!str.equals(str1)){
                    sameError.setText("两次输入的密码不一致");
                }
                else {
                    sameError.setText("");
                    pwd2=str;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
