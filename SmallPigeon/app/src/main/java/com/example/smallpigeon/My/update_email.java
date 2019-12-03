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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.LoginOrRegister.LoginActivity;
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

public class update_email extends AppCompatActivity {
    private EditText userEmail;
    private TextView getCode;
    private TextView code_error;
    private EditText checkCode;
    private ImageView btnFinish;
    private ImageView back;
    private String code1;
    private Handler sendEmail = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result.equals("true")){
                secondDown();
                Toast.makeText(getApplicationContext(),"验证码发送成功！",Toast.LENGTH_SHORT).show();
            }else if(result.equals("repeat")){
                getEmailCode();
                Toast.makeText(getApplicationContext(),"该邮箱已经被注册了哦，请换一个吧～",Toast.LENGTH_SHORT).show();
            }else{
                getEmailCode();
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

    private Handler updateEmail = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result.equals("true")){
                btnFinish.setImageDrawable(getResources().getDrawable(R.drawable.wancheng));
                Toast.makeText(getApplicationContext(),"邮箱更改成功！",Toast.LENGTH_SHORT).show();
                SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
                pre.edit().clear().commit();
                Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent3);
                finish();
            } else{
                Toast.makeText(getApplicationContext(),"邮箱更改失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);
        getViews();
        //控件的点击事件
        btnEvent();

    }

    //控件的点击事件
    private void btnEvent() {
        //返回绑定邮箱界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(update_email.this,anquan.class);
                startActivity(intent4);
                finish();
            }
        });
        getEmailCode();
        //确认图片的点击事件
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCode.getText().toString().equals("") || !isEmail(userEmail.getText().toString())
                    || !checkCode.getText().toString().equals(code1)){
                    Toast.makeText(getApplicationContext(),"请输入正确的信息哦！",Toast.LENGTH_SHORT).show();
                }else{
                    updateEmail();
                }
            }
        });
    }

    //获取邮件
    public void getEmailCode(){
        getCode.setText("获取验证码");
        //获取验证码
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmail(userEmail.getText().toString())){
                    Toast.makeText(getApplicationContext(),"请按正确的邮箱格式填写哦！",Toast.LENGTH_SHORT).show();
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
                            +":8080/smallpigeon/user/verifyCodeAndEmail?userEmail="+userEmail.getText().toString()+"&&code="+code1);
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

    //更改邮箱的方法
    public void updateEmail(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/user/updateEmail?userEmail="+userEmail.getText().toString()
                            +"&&userId="+getSharedPreferences("userInfo",MODE_PRIVATE).getString("user_id",""));
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    updateEmail.sendMessage(message);
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

    //判断邮箱的格式正确与否
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //获取视图的控件
    public void getViews(){
        userEmail=findViewById(R.id.user_email);
        getCode=findViewById(R.id.updemail_getcode);
        code_error=findViewById(R.id.code_error);
        checkCode=findViewById(R.id.updemail_edt_code);
        back=findViewById(R.id.updpwd_back);
        btnFinish = findViewById(R.id.btn_Finish);
    }


}
