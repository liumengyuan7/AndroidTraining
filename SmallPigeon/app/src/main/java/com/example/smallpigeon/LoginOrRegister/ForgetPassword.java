package com.example.smallpigeon.LoginOrRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPassword extends AppCompatActivity {

    private ImageView back;
    private EditText userEmail;
    private EditText checkCode;
    private TextView getCode;
    private TextView code_error;
    private String code1;

    private Handler sendEmail = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result.equals("notRepeat")){
                Toast toast=Toast.makeText(getApplicationContext(),"该邮箱没有注册信息哦！",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                getCodeEvent();
            }else if(result.equals("true")){
                secondDown();
                Toast toast=Toast.makeText(getApplicationContext(),"验证码发送成功！",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }else{
                getCodeEvent();
                Toast toast=Toast.makeText(getApplicationContext(),"验证码发送失败！",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
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
                getCodeEvent();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getViews();


        //返回到登录界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //获取验证码
        getCodeEvent();

        //检查验证码是否正确
        checkCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(checkCode.getText().toString().equals(code1)){
                    Intent intent = new Intent(getApplicationContext(), ResetPwd.class);
                    intent.putExtra("user_email",userEmail.getText().toString());
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

    //获取验证码的点击事件
    private void getCodeEvent(){
        getCode.setText("获取验证码");
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmail(userEmail.getText().toString())){
                    Toast toast=Toast.makeText(getApplicationContext(),"请输入正确的邮箱格式哦！",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    getCode.setText("验证码发送中...");
                    getCode.setOnClickListener(null);
                    sendEmailToServer();
                }
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
                            +":8080/smallpigeon/user/verifyCodeAndEmail?userEmail="+userEmail.getText().toString()
                            +"&&code="+code1+"&&tag=re");
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

    private void getViews() {
        back=findViewById(R.id.forget_back);
        userEmail=findViewById(R.id.user_email);
        checkCode=findViewById(R.id.resetPwd_edt_code);
        getCode=findViewById(R.id.resetPwd_getcode);
        code_error=findViewById(R.id.code_error);
    }

    //判断邮箱的格式正确与否
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
