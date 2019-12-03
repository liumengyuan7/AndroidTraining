package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smallpigeon.Fragment.MyFragment;
import com.example.smallpigeon.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class Personal_center_updateUserNickname extends AppCompatActivity {
    private EditText edtNickname;
    private ImageView personal_center_updateNickname_back;
    private ImageView Personal_center_btnSaveNickname;
    private CustomeClickListener listener;
    private Handler handleNickname = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result.equals("true")){
                Personal_center_btnSaveNickname.setImageDrawable(getResources().getDrawable(R.drawable.wancheng));
                Toast.makeText(getApplicationContext(),"修改成功！",Toast.LENGTH_SHORT).show();
                SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = pre.edit();
                editor.putString("user_nickname",edtNickname.getText().toString());
                editor.commit();
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"修改失败,请重新输入！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center_update_user_nickname);
        personal_center_updateNickname_back=findViewById(R.id.personal_center_updateNickname_back);
        edtNickname=findViewById(R.id.edit_Nickname);
        Personal_center_btnSaveNickname=findViewById(R.id.personal_center_btnSaveNickname);

        listener=new CustomeClickListener();
        personal_center_updateNickname_back.setOnClickListener(listener);
        Personal_center_btnSaveNickname.setOnClickListener(listener);



    }


    class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.personal_center_updateNickname_back:
                    finish();
                    //返回到个人中心界面
                    break;
                case R.id.personal_center_btnSaveNickname:
                    if(edtNickname.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),
                            "昵称不能为空哦！",
                            Toast.LENGTH_SHORT).show();
                    }else{
                        updateUserNickname();
                    }
                    break;

            }


        }
    }


    public void updateUserNickname(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/user/updateNickname?nickname="+edtNickname.getText().toString()
                            +"&&userId="+getSharedPreferences("userInfo",MODE_PRIVATE).getString("user_id",""));
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    handleNickname.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
