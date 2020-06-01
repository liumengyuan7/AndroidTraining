package com.example.smallpigeon.My;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smallpigeon.R;
import com.example.smallpigeon.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class IdentifyActivity extends AppCompatActivity {
    private EditText et_name;
    private EditText et_school;
    private EditText et_sno;
    private ImageView iv_front;
    private ImageView iv_back;
    private Button btn_submit;
    private String userId;
    private MyClickListener listener;
    private String identifyImages;
    private int is_accreditation;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String result = msg.obj + "";
                    if (result.equals("true")) {
                        Toast toast=Toast.makeText(getApplicationContext(), "提交成功，请等待管理员验证", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    } else {
                        Toast toast=Toast.makeText(getApplicationContext(), "提交失败，请重新提交", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    break;
                case 1:
                    String status = msg.obj + "";
                    is_accreditation = Integer.parseInt(status);
                    Log.e("IdentifyActivity当前认证状态",is_accreditation+"");
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);
        setStatusBar();
        getViews();
        registerListeners();
        SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = pre.getString("user_id","");
        selectUserInfo(userId);
    }

    //隐藏状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    private void registerListeners() {
        listener = new MyClickListener();
        iv_front.setOnClickListener(listener);
        iv_back.setOnClickListener(listener);
        btn_submit.setOnClickListener(listener);
    }

    private void getViews() {
        et_name = findViewById(R.id.et_name);
        et_school = findViewById(R.id.et_school);
        et_sno = findViewById(R.id.et_sno);
        iv_back = findViewById(R.id.iv_back);
        iv_front = findViewById(R.id.iv_front);
        btn_submit = findViewById(R.id.btn_submit);
    }

    public class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_back://背面照片
                    Intent back = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(back,2);
                    break;
                case R.id.iv_front://正面照片
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,1);
                    break;
                case R.id.btn_submit:
                    String userName = et_name.getText().toString().trim();
                    String userSchool = et_school.getText().toString().trim();
                    String userSno = et_sno.getText().toString().trim();
                    //更新后台用户数据  增加姓名 学校 学号 以及认证图片  认证状态
                    updateUserMsg(userId,userName,userSchool,userSno,identifyImages,2);
                    Intent intent1 = new Intent(IdentifyActivity.this,IsIdentifyActivity.class);
                    intent1.putExtra("identify",is_accreditation);
                    startActivity(intent1);
                    finish();
                    break;
            }
        }
    }

    //根据用户id得到 用户是否认证成功
    private void selectUserInfo(String userId) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("user","getStatusByUserId", "userId="+ userId);
                Message message = new Message();
                message.obj = result;
                message.what=1;
                handler.sendMessage(message);
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            iv_front.setImageBitmap(photo);
            String name = avatarStore(iv_front);
            sendImg(name);
            if (identifyImages==null || identifyImages.equals("")){
                identifyImages = name;
            }else {
                identifyImages = identifyImages+";"+name;
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            iv_back.setImageBitmap(photo);
            String name = avatarStore(iv_back);
            sendImg(name);
            if (identifyImages==null || identifyImages.equals("")){
                identifyImages = name;
            }else {
                identifyImages = identifyImages+";"+name;
            }
        }
    }

    private void sendImg(String name) {
        String path = getFilesDir().getAbsolutePath()+"/"+name;
        File file = new File(path);
        OkHttpClient okHttpClient = new OkHttpClient();
        Log.e("图片",path);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("images", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url("http://"+getResources().getString(R.string.ip_address)+":8080/smallpigeon/user/postIdentifyImages")
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "失败信息: " + e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                            Toast.makeText(getApplicationContext(),"失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("TAG", "成功返回" + response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                            Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private String avatarStore(ImageView imageView){
        //图片名称为随机生成
        String imgName = getRandomPicString()+".jpg";
        String path = getFilesDir().getAbsolutePath()+"/"+imgName;
        File file = new File(path);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(out.toByteArray());
            out.flush();
            out.close();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgName;
    }

    private String getRandomPicString() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();

    }
    //更新用户信息
    private void updateUserMsg(String userId, String userName, String userSchool, String userSno, String identifyImages, int status) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("user","updateUserByMsg",
                        "userId="+userId+"&&userName="+userName+"&&userSno="+
                                userSno+"&&userSchool="+userSchool+"&&identifyImages="+identifyImages+"&&status="+status);
                Message message = new Message();
                message.obj = result;
                message.what=0;
                handler.sendMessage(message);
            }
        }.start();
    }
}
