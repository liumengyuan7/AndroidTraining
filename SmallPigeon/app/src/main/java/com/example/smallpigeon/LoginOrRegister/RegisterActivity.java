package com.example.smallpigeon.LoginOrRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private ImageView btn_FinishReg;
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
    private TextView getCode;

    private TextView pwd_length;
    private TextView pwd_same;

    private String code;
    private RadioButton RB;

    private String userEmail,userNickname,checkpwd,userPassword,checkcode;

    //用户注册是否成功的handler
    private Handler userRegister = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result.equals("true")){
                btn_FinishReg.setImageDrawable(getResources().getDrawable(R.drawable.wancheng));
                Toast.makeText(getApplicationContext(),"恭喜你加入小鸽快跑~ 要好好锻炼哦~",Toast.LENGTH_SHORT).show();
                finish();
            }else if(result.equals("repeat")){
                Toast.makeText(getApplicationContext(),"该邮箱已经被注册了，换一个吧~",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"注册失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    //发送邮件是否成功的handler
    private Handler emailHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result.equals("true")){
                secondDown();
                Toast.makeText(getApplicationContext(),"验证码发送成功！",Toast.LENGTH_SHORT).show();
            }else if(result.equals("repeat")){
                Toast.makeText(getApplicationContext(),"该邮箱已经被注册了，换一个吧~！",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"验证码发送失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    //邮件的倒计时处理
    private Handler secondHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String second = msg.obj + "";
            if(!second.equals("0")){
                getCode.setText(msg.obj + "秒后重新发送");
                getCode.setOnClickListener(null);
            }else{
                code = "";
                getCode.setText("获取验证码");
                getCode.setOnClickListener(listener);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getViews();
        btn_FinishReg.setImageDrawable(getResources().getDrawable(R.drawable.wancheng1));
        setViews();//屏幕适配
        registListeners();
        length();
        same();

    }

    //动态判断密码的长度是否符合规范
    public void length(){
        register_userPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str=register_userPassword.getText().toString();
                Log.e("密码"," "+str);
                if(str.length()!=0&&str.length()<6){
                    pwd_length.setText("密码长度不可小于6位");
                }
                else {
                    pwd_length.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //动态判断两次输入的密码是否一致
    public void same(){
       register_checkPwd.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               String str=register_userPassword.getText().toString();
               String str1=register_checkPwd.getText().toString();
               if(str1.length()!=0){
                   if(str1.equals(str)){
                       pwd_same.setText("");
                   }
                   else{
                       pwd_same.setText("两次密码输入不一致");
                   }
               }
               else{
                   pwd_same.setText("");
               }

           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
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

        pwd_length=findViewById(R.id.pwd_length);
        pwd_same=findViewById(R.id.pwd_same);



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
                case R.id.register_getCode:
                    if(isEmail(register_userEmail.getText().toString())){
                        sendEmail();
                    }
                    else{ Toast.makeText(getApplicationContext(),"请输入正确的邮箱格式！",Toast.LENGTH_SHORT).show(); }
                    break;
                case R.id.btn_FinishReg:
                    radioAndCheckbox();
                    if(!confirmUserInfo()) {Toast.makeText(getApplicationContext(),"请正确填写注册信息哦~",Toast.LENGTH_SHORT).show();}
                    else{
                        if(checkcode.equals(code)){
                            Log.e("str"," "+interesStr);
                            str1 = interesStr.substring(0,interesStr.length()-1);//兴趣爱好
                            Log.e("str1"," "+str1);
                            if(!confirmUserInfo()){ Toast.makeText(getApplicationContext(),"请正确填写注册信息哦~",Toast.LENGTH_SHORT).show(); }
                            else{
                                if(!userPassword.equals(checkpwd)){
                                    Toast.makeText(getApplicationContext(),"两次密码输入不一致",Toast.LENGTH_SHORT).show(); }
                                else{
                                    try {
                                        MessageDigest md = MessageDigest.getInstance("MD5");
                                        md.update(userPassword.getBytes());
                                        userPassword = new BigInteger(1, md.digest()).toString(16);
                                        Log.e("md5",userPassword);
                                        userRegister(userEmail,userPassword,userNickname,str1);
                                    } catch (NoSuchAlgorithmException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"验证码有误，请重新填写",Toast.LENGTH_SHORT).show();
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

    //邮件通过后台发送
    public void sendEmail(){
        code = "";
        for(int i = 0;i<4;i++){
            code += (int)(Math.random()*10) + "";
        }
        Log.e("dsadsadsa",code);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/user/verifyCodeAndEmail?userEmail="+register_userEmail.getText().toString()
                            +"&&code="+ code);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    emailHandle.sendMessage(message);
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

    //单选框和复选框的点击事件
    public void radioAndCheckbox(){
        for(int i=0;i<radioGroup_userSex.getChildCount();i++){
            RB=(RadioButton) radioGroup_userSex.getChildAt(i);
            if(RB.isChecked()) {
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
    }

    //用户登录信息输入是否为空的验证
    public boolean confirmUserInfo(){
        userEmail = register_userEmail.getText().toString();
        userNickname=register_userNickname.getText().toString();
        checkpwd=register_checkPwd.getText().toString();
        userPassword = register_userPassword.getText().toString();
        checkcode=register_checkCode.getText().toString();
        if( userEmail.length()==0 || userPassword.length()==0
                || userNickname.length()==0 || register_checkPwd.length()==0
                || checkcode.length()==0 || sexstr.equals("")||interesStr.equals(""))
            return false;
        return true;
    }

    //判断邮箱的格式正确与否
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
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
