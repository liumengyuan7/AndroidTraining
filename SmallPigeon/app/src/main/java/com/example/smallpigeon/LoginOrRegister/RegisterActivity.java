package com.example.smallpigeon.LoginOrRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class RegisterActivity extends AppCompatActivity {
    private Button btn_FinishReg;
    private CustomeClickListener listener;
    private RadioGroup radioGroup_userSex;
    private CheckBox outdoor;
    private CheckBox music;
    private CheckBox film;
    private CheckBox society;
    private CheckBox delicacy;
    private CheckBox science;
    private CheckBox star;
    private CheckBox comic;
    private String sexstr;
    private String interesStr;
    private String str1;
    private  LinearLayout register_Linear;
    private ImageView Register_Return;

    private EditText register_userEmail;
    private EditText register_userPassword;
    private EditText register_userNickname;
    private EditText register_checkPwd;
    private EditText register_checkCode;
    private Button getCode;

    private Handler userRegister = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result.equals("true")){
                Toast.makeText(getApplicationContext(),"恭喜你加入小鸽快跑~ 要好好锻炼哦~",Toast.LENGTH_SHORT).show();
                finish();
            }else if(result.equals("repeat")){
                Toast.makeText(getApplicationContext(),"该邮箱已经被注册了，换个邮箱吧！",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"注册失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getViews();
        setViews();//屏幕适配
        registListeners();
    }

    //动态设置控件
    private void setViews() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                (int)(displayWidth * 1f + 0.1f),
                (int)(displayHeight * 0.5f + 0.5f));
        params1.setMargins((int)(displayWidth * 0.001f + 0.1f),(int)(displayHeight * 0.001f + 0.5f)
                ,(int)(displayWidth * 0.05f + 0.5f),0);
        register_Linear.setLayoutParams(params1);




    }

    //获取视图的控件
    private void getViews() {
        btn_FinishReg=findViewById(R.id.btn_FinishReg);
        radioGroup_userSex=findViewById(R.id.radioGroup_userSex);

        outdoor=findViewById(R.id.outdoor);
        music=findViewById(R.id.music);
        film=findViewById(R.id.film);
        society=findViewById(R.id.society);
        delicacy=findViewById(R.id.delicacy);
        science=findViewById(R.id.science);
        star=findViewById(R.id.star);
        comic=findViewById(R.id.comic);

        register_userEmail=findViewById(R.id.register_userEmail);
        register_userPassword=findViewById(R.id.register_userPassword);
        register_userNickname=findViewById(R.id.register_userNickname);

        register_Linear=findViewById(R.id.register_Linear);

        Register_Return=findViewById(R.id.Register_Return);

        register_checkPwd=findViewById(R.id.register_checkPwd);
        register_checkCode=findViewById(R.id.register_checkCode);
        getCode=findViewById(R.id.register_getCode);


    }

    //注册监听器
    private void registListeners(){
        listener = new CustomeClickListener();
        btn_FinishReg.setOnClickListener(listener);
        Register_Return.setOnClickListener(listener);
        getCode.setOnClickListener(listener);

    }

    //设置监听器
    class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            sexstr="";
            interesStr="";
            switch (v.getId()){

                case R.id.register_getCode://发送验证码

                    break;

                case R.id.btn_FinishReg:
//                    String checkcode=register_checkCode.getText().toString();//获取用户输入的验证码
//                    if(checkcode.equals("验证码")){
//                        //提示注册成功  148行之后的代码挪至此处
//                    }
//                    else{
//                        //提示错误
//
//                    }
                    for(int i=0;i<radioGroup_userSex.getChildCount();i++){
                        RadioButton RB=(RadioButton) radioGroup_userSex.getChildAt(i);
                        if(RB.isChecked())
                        {
                            Log.e("单选按钮","性别："+RB.getText());
                            if(RB.getText().equals("男")){
                                sexstr=sexstr+"man";
                            }
                            else{
                                sexstr=sexstr+"woman";
                            }

                        }
                    }
                    if(outdoor.isChecked())
                        interesStr=interesStr+"outdoor,";
                    if(music.isChecked())
                        interesStr+="music,";
                    if(film.isChecked())
                        interesStr+="film,";
                    if(society.isChecked())
                        interesStr+="society,";
                    if(delicacy.isChecked())
                        interesStr+="delicacy,";
                    if(science.isChecked())
                        interesStr+="science,";
                    if(star.isChecked())
                        interesStr+="star,";
                    if(comic.isChecked())
                        interesStr+="comic,";
                    if(sexstr.equals("")||interesStr.equals("")){
                        if(sexstr.length()==0&&interesStr.length()!=0)
                            Toast.makeText(getApplicationContext(),"请正确填写注册信息哦~",Toast.LENGTH_SHORT).show();
                        if(sexstr.length()!=0&&interesStr.length()==0)
                            Toast.makeText(getApplicationContext(),"请正确填写注册信息哦~",Toast.LENGTH_SHORT).show();
                        if(sexstr.length()==0&&interesStr.length()==0)
                            Toast.makeText(getApplicationContext(),"请正确填写注册信息哦~",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.e("str"," "+interesStr);
                        str1 = interesStr.substring(0,interesStr.length()-1);//兴趣爱好
                        Log.e("str1"," "+str1);
                        String userEmail = register_userEmail.getText().toString();
                        String userNickname=register_userNickname.getText().toString();
                        String checkpwd=register_checkPwd.getText().toString();
                        String userPassword = register_userPassword.getText().toString();
                        


                        if(userEmail.length()==0||userPassword.length()==0||userNickname.length()==0||checkpwd.length()==0){
                            if(userEmail.length()==0)
                                Toast.makeText(getApplicationContext(),"请正确填写注册信息哦~",Toast.LENGTH_SHORT).show();
                            if(userPassword.length()==0)
                                Toast.makeText(getApplicationContext(),"请正确填写注册信息哦~",Toast.LENGTH_SHORT).show();
                            if(userNickname.length()==0)
                                Toast.makeText(getApplicationContext(),"请正确填写注册信息哦~",Toast.LENGTH_SHORT).show();
                            if(register_checkPwd.length()==0)
                                Toast.makeText(getApplicationContext(),"请正确填写注册信息哦~",Toast.LENGTH_SHORT).show();
                            if(userEmail.length()==0&&userPassword.length()==0&&userNickname.length()==0)
                                Toast.makeText(getApplicationContext(),"注册信息不可为空~",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(!userPassword.equals(checkpwd)){
                                Toast.makeText(getApplicationContext(),"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                try {
                                    MessageDigest md = MessageDigest.getInstance("MD5");
                                    md.update(userPassword.getBytes());
                                    userPassword = new BigInteger(1, md.digest()).toString(16);
                                    Log.e("md5",userPassword);
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                }
                                userRegister(userEmail,userPassword,userNickname,str1);
                            }

                        }
                    }

                    break;

                case R.id.Register_Return:
                    Intent intent3 = new Intent(RegisterActivity.this, LoginActivity.class);
                    finish();
                    break;

            }




        }
    }

    //用户的注册
    public void userRegister(final String userEmail,final String userPassword,final String userNickname,final String str){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/user/userRegister?userEmail="+userEmail+"&&userPassword="+userPassword+"&&userNickname="+userNickname+"&&userInterest="+str+"&&userSex="+sexstr);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    userRegister.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
