package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class update_emai_final extends AppCompatActivity {
    private EditText userMail;
    private ImageView back;
    private ImageView finishUpd;
    private TextView email_error;
    private String code;
    private Handler userUpdate = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result.equals("true")){
                finishUpd.setImageDrawable(getResources().getDrawable(R.drawable.wancheng));
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }else if(result.equals("repeat")){
                Toast.makeText(getApplicationContext(),"该邮箱已经被注册了，换一个吧~",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"修改失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_emai_final);
        getViews();
        //返回到安全中心
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),anquan.class);
                startActivity(intent);
                finish();
            }
        });

        finishUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmail(userMail.getText().toString())){
                    sendEmail();
                }
                else{ Toast.makeText(getApplicationContext(),"请输入正确的邮箱格式！",Toast.LENGTH_SHORT).show(); }
            }
        });





    }
    //将新邮箱传到mysql
    public void sendEmail(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/user/updateEmail?userEmail="+userMail.getText().toString());
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    userUpdate.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }





    //检验邮箱格式
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public void getViews(){
        userMail=findViewById(R.id.newEmail);
        back=findViewById(R.id.back);
        finishUpd=findViewById(R.id.btn_FinishUpd);
        email_error=findViewById(R.id.email_error);

    }
}
