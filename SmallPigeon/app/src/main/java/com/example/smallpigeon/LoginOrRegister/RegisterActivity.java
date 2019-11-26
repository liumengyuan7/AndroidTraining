package com.example.smallpigeon.LoginOrRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Fragment.MyFragment;
import com.example.smallpigeon.My.PersonalCenter;
import com.example.smallpigeon.My.Personal_center_More;
import com.example.smallpigeon.My.Personal_center_updateUserNickname;
import com.example.smallpigeon.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
    private String str;
    private  String str1;

    private  LinearLayout register_Linear;
    private ImageView Register_Return;

    private EditText register_userEmail;
    private EditText register_userPassword;
    private EditText register_userNickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getViews();
        setViews();//屏幕适配
        registListeners();
    }

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


    }
    private void registListeners(){
        listener = new CustomeClickListener();
        btn_FinishReg.setOnClickListener(listener);
        Register_Return.setOnClickListener(listener);
    }


    class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            str="";
            switch (v.getId()){
                case R.id.btn_FinishReg:
                    for(int i=0;i<radioGroup_userSex.getChildCount();i++){
                        RadioButton RB=(RadioButton) radioGroup_userSex.getChildAt(i);
                        if(RB.isChecked())
                        {
                            Log.e("单选按钮","性别："+RB.getText());
                            if(RB.getText()=="男"){
                                str=str+"Man;";
                            }
                            else{
                                str=str+"Woman;";
                            }
                            break;
                        }
                    }
                    if(outdoor.isChecked())
                        str=str+"outdoor,";
                    if(music.isChecked())
                        str+="music,";
                    if(film.isChecked())
                        str+="film,";
                    if(society.isChecked())
                        str+="society,";
                    if(delicacy.isChecked())
                        str+="delicacy,";
                    if(science.isChecked())
                        str+="science,";
                    if(star.isChecked())
                        str+="star,";
                    if(comic.isChecked())
                        str+="comic,";

                    Log.e("str"," "+str);
                    int b = str.length();
                    str1 = str.substring(0,b-1);//性别+兴趣爱好
                    Log.e("str1"," "+str1);

                    String userEmail = register_userEmail.getText().toString();
                    String userPassword = register_userPassword.getText().toString();
                    String userNickname=register_userNickname.getText().toString();
                    userRegister(userEmail,userPassword,userNickname,str1);

                    Toast.makeText(getApplicationContext(),"恭喜你加入小鸽快跑~ 要好好锻炼哦~",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    finish();
                    break;

                case R.id.Register_Return:
                    Intent intent3 = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent3);
                    break;

            }




        }
    }


    public void userRegister(final String userEmail,final String userPassword,final String userNickname,final String str){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/user/userRegister?userEmail="+userEmail+"&&userPassword="+userPassword+"&&userNickname="+userNickname+"&&sexAndInterest="+str);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
