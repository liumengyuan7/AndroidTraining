package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.LoginOrRegister.LoginActivity;
import com.example.smallpigeon.LoginOrRegister.RegisterActivity;
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

public class update_pwd_final extends AppCompatActivity {
    private EditText newPwd;
    private EditText check_newPwd;
    private ImageView btn;//完成图标
    private ImageView img;
    private TextView length_error;
    private  TextView same_error;
    private  String pwd;
    private String userId;
    private Handler updatePwd = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result.equals("true")){
                btn.setImageDrawable(getResources().getDrawable(R.drawable.wancheng));
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
                pre.edit().clear().commit();
                Intent intent3 = new Intent(update_pwd_final.this, LoginActivity.class);

                Toast.makeText(getApplicationContext(),"请重新登录！",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"修改失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd_final);
        findviews();
        getId();//获取用户id 用于修改密码
        length();//监测密码长度状态
        same();//检测两次密码输入是否一致
        // 左上角返回 点击进入登录页面
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(update_pwd_final.this, LoginActivity.class);
                startActivity(intent4);
            }
        });


        //完成修改 将密码发送到数据库    修改密码完成后返回为true  更换图标颜色  同注册界面
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(pwd.getBytes());
                    pwd = new BigInteger(1, md.digest()).toString(16);
                    Log.e("md5",pwd);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                updatePwd(pwd,userId);
            }
        });



    }

    private void getId() {
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        String s = pre.getString("user_id","");
        userId=s;
    }

    public void updatePwd(final String pwd, final String userId){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/user/updatePassword?password="+pwd+"&userId="+userId);
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

    public void findviews(){
        newPwd=findViewById(R.id.newPwd);
        check_newPwd=findViewById(R.id.edt_checkPwd);
        btn=findViewById(R.id.btn_FinishUpd);
        img=findViewById(R.id.shut);
        length_error=findViewById(R.id.length_error);
        same_error=findViewById(R.id.same_error);
    }
    public void length(){
        newPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str=newPwd.getText().toString();
                Log.e("密码"," "+str);
                if(str.length()!=0&&str.length()<6){
                    length_error.setText("密码长度不可小于6位");
                }
                else {
                    length_error.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void  same(){
        check_newPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str=check_newPwd.getText().toString();
                String str1=newPwd.getText().toString();
                Log.e("密码"," "+str);
                if(!str.equals(str1)){
                    same_error.setText("两次输入的密码不一致");
                }
                else {
                    same_error.setText("");
                    pwd=str;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
