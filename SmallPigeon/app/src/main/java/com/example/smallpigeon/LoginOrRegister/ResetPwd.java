package com.example.smallpigeon.LoginOrRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smallpigeon.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
//忘记密码-》重置密码
public class ResetPwd extends AppCompatActivity {
    private Button btn_resetBtn;
    private EditText resetPwd;
    private EditText checkPwd;
    private ImageView resetImg;
    private Handler handlePassword = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg + "";
            if(result.equals("true")){
                Toast.makeText(getApplicationContext(),"修改成功，请重新登录！",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"修改失败,请重新输入！",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        getViews();

        resetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(ResetPwd.this, ForgetPassword.class);
                startActivity(intent3);
                finish();
            }
        });

        btn_resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPwd= resetPwd.getText().toString();
                String checkNewPwd= checkPwd.getText().toString();
                if(newPwd==checkNewPwd){
                    ResetPassword(newPwd);
                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    Intent intent3 = new Intent(ResetPwd.this, LoginActivity.class);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),"两次输入的密码不一致哦~请重新输入",Toast.LENGTH_SHORT).show();
                    resetPwd.setText("");
                    checkPwd.setText("");
            }
        });
    }

    private void getViews() {
        resetImg=findViewById(R.id.resetImg);
        resetPwd=findViewById(R.id.resetPwd);
        checkPwd=findViewById(R.id.checkPwd);
        btn_resetBtn=findViewById(R.id.btn_resetBtn);

    }
    //新密上传 未md5加密
    public void ResetPassword(final String pwd){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/SmallPigeon/user/Register?newPwd="+pwd);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    handlePassword.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
