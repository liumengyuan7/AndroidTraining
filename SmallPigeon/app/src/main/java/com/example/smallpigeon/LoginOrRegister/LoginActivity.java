package com.example.smallpigeon.LoginOrRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class LoginActivity extends AppCompatActivity {

    private ImageView loginReturn;
    private TextView accountRegister;
    private TextView forgetPassword;
    private EditText username;
    private EditText password;
    private ImageView userLogin;
    private String md5Pass;

    private static final String APPID = "1110462995";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener  mListener;
    private UserInfo mInfo;
    private String name, figureurl,gender;
    private LinearLayout ll_qqLogin;

    private Handler handlerLogin = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String re = msg.obj+"";
            Log.e("sssssssss",re);
            if(re.equals("false")){
                Toast toast=Toast.makeText(getApplicationContext(),"您的账号或者密码错误，登录失败！",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }else{
                try {
                    String result = re.split(";;")[0];
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject json = jsonArray.getJSONObject(0);
                    SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pre.edit();
                    editor.putString("user_id",json.getString("id"));
                    editor.putString("user_nickname",json.getString("user_nickname"));
                    editor.putString("user_sex",json.getString("user_sex"));
                    editor.putString("user_email",json.getString("user_email"));
                    editor.putString("user_register_time",json.getString("user_register_time"));
                    editor.putString("user_points",json.getString("user_points"));
                    editor.putString("user_interest",re.split(";;")[1]);
                    signIn(json.getString("id"));
                    editor.commit();
                } catch (JSONException e){
                    e.printStackTrace();
                }
                userLogin.setImageDrawable(getResources().getDrawable(R.drawable.wancheng_green ));
                Toast toast=Toast.makeText(getApplicationContext(),"登录成功！",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout toastView = (LinearLayout) toast.getView();
                ImageView imgBack = new ImageView(getApplicationContext());
                imgBack.setImageResource(R.drawable.r);
                toastView.addView(imgBack, 0);
                toast.show();
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBar();
        //获取控件
        getViews();
        //按钮的点击事件
        btnEvents();

        // 实例化Tencent
        mTencent = Tencent.createInstance(APPID, this.getApplicationContext());
    }

    //获取视图的控件
    private void getViews() {
        accountRegister = findViewById(R.id.account_register);
        forgetPassword = findViewById(R.id.forget_password);
        userLogin = findViewById(R.id.user_login);
        username = findViewById(R.id.username);
        loginReturn = findViewById(R.id.loginReturn);
        password = findViewById(R.id.password);
        ll_qqLogin = findViewById(R.id.ll_qqLogin);
        mListener = new BaseUiListener ();
    }

    //按钮的点击事件
    private void btnEvents() {

        ll_qqLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QQLogin();//获取所有权限
                mInfo = new UserInfo(LoginActivity.this,mTencent.getQQToken());
                mInfo.getUserInfo(mListener);
            }
        });

        accountRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(),"账号或者密码不能为空哦，请重新输入！",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else {
                    sendMessageToServer();
                }
            }
        });

        loginReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //忘记密码
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //向服务器发送数据
    private void sendMessageToServer(){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getText().toString().getBytes());
            md5Pass = new BigInteger(1, md.digest()).toString(16);
            Log.e("md5",md5Pass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/user/userLogin?useremail="+username.getText().toString()
                            +"&&password="+md5Pass);
                    Log.e("url",url.toString());
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    message.what = 2;
                    handlerLogin.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //隐藏状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }
    /*
     * 登录  异步
     * */
    private void signIn(String userId) {
        EMClient.getInstance().login(userId, userId, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("环信登录","登录成功");
            }
            @Override
            public void onError(int i, String s) {
                Log.e("环信登录","登录失败"+i+","+s);
            }
            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 登录方法
     */
    private void QQLogin() {
        //如果session不可用，则登录，否则说明已经登录
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", mListener);
        }
    }

    // 实现登录成功与否的接口
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object object) { //登录成功
            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            //获取openid和token
            JSONObject jb = (JSONObject) object;
            try {
                String openID = jb.getString("openid");  //openid用户唯一标识
                String access_token = jb.getString("access_token");
                String expires = jb.getString("expires_in");

                mTencent.setOpenId(openID);
                mTencent.setAccessToken(access_token, expires);

                QQToken token = mTencent.getQQToken();
                mInfo = new UserInfo(LoginActivity.this, token);
                mInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object object) {
                        Toast toast=Toast.makeText(getApplicationContext(),"登录成功！",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        LinearLayout toastView = (LinearLayout) toast.getView();
                        ImageView imgBack = new ImageView(getApplicationContext());
                        imgBack.setImageResource(R.drawable.r);
                        toastView.addView(imgBack, 0);
                        toast.show();
                        JSONObject jb = (JSONObject) object;
                        Log.e("json",jb+"");
                        try {
                            name = jb.getString("nickname");//昵称
                            figureurl = jb.getString("figureurl_qq_2");  //头像图片的url
                            gender = jb.getString("gender");//性别
                            Log.e("json",figureurl+"");
//                            Glide.with(LoginActivity.this).load(figureurl).into(figure);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Toast toast=Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        LinearLayout toastView = (LinearLayout) toast.getView();
                        ImageView imgBack = new ImageView(getApplicationContext());
                        imgBack.setImageResource(R.drawable.w);
                        toastView.addView(imgBack, 0);
                        toast.show();
                    }

                    @Override
                    public void onCancel() {
                        Toast toast=Toast.makeText(LoginActivity.this,"登录取消",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast toast=Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imgBack = new ImageView(getApplicationContext());
            imgBack.setImageResource(R.drawable.w);
            toastView.addView(imgBack, 0);
            toast.show();

        }

        @Override
        public void onCancel() {
            Toast toast=Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imgBack = new ImageView(getApplicationContext());
            imgBack.setImageResource(R.drawable.w);
            toastView.addView(imgBack, 0);
            toast.show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResultData(requestCode, resultCode, data, mListener);
    }
}
