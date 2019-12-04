package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.smallpigeon.R;
import com.example.smallpigeon.RoundImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Personal_centet_updateUserImg extends AppCompatActivity {

    private ImageView personal_center_updateImg_back;
    private RoundImageView userImg;
    private ImageView camera;
    private PopupWindow pop;
    private MyCustomListener listener;
    private TextView avatarCamera;
    private TextView avatarGallery;
    private TextView popDismiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_centet_update_user_img);
        setStatusBar();
        getViews();
        registerListener();
        String path = getFilesDir().getAbsolutePath()+"/avatar/"
                +getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("user_email","")+".png";
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        userImg.setImageBitmap(bitmap);
        //打开相机获取图片并上传新头像
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popView = getLayoutInflater().inflate(R.layout.camera_pop,null);
                pop = new PopupWindow(popView,ViewGroup.LayoutParams.MATCH_PARENT, 450,true);
                pop.setAnimationStyle(R.style.popwin_anim_style);
                pop.showAsDropDown(getWindow().getDecorView());
                getPopAndRegister(popView);
                final WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.4f;
                getWindow().setAttributes(lp);
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                }});
            }
        });

    }

    //注册监听器
    private void registerListener() {
        listener = new MyCustomListener();
        personal_center_updateImg_back.setOnClickListener(listener);
    }

    //监听器
    class MyCustomListener implements View.OnClickListener, View.OnTouchListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.personal_center_updateImg_back:
                    finish();
                    break;
                case R.id.avatarCamera:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,3);
                    pop.dismiss();
                    break;
                case R.id.avatarGallery:
                    Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                    intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent1, 2);
                    pop.dismiss();
                    break;
                case R.id.popDismiss:
                    pop.dismiss();
                    break;
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()){
                case R.id.avatarCamera:
                    motionEvent(avatarCamera,event);
                    break;
                case R.id.avatarGallery:
                    motionEvent(avatarGallery,event);
                    break;
                case R.id.popDismiss:
                    motionEvent(popDismiss,event);
                    break;
            }
            return false;
        }

    }

    //获取pop的控件并注册监听器
    private void getPopAndRegister(View view){
        avatarCamera = view.findViewById(R.id.avatarCamera);
        avatarGallery = view.findViewById(R.id.avatarGallery);
        popDismiss = view.findViewById(R.id.popDismiss);
        avatarCamera.setOnClickListener(listener);
        avatarCamera.setOnTouchListener(listener);
        avatarGallery.setOnClickListener(listener);
        avatarGallery.setOnTouchListener(listener);
        popDismiss.setOnClickListener(listener);
        popDismiss.setOnTouchListener(listener);
    }

    //动态事件
    private void motionEvent(View view,MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundColor(Color.parseColor("#C0C0C0"));
                break;
            case MotionEvent.ACTION_UP:
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
    }

    //activity的返回方法
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                userImg.setImageURI(uri);
                avatarStore();
            }
        }else if(requestCode == 3){
            if (data != null){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                userImg.setImageBitmap(bitmap);
                avatarStore();
            }
        }
    }

    //头像的存储
    private void avatarStore(){
        String path = getFilesDir().getAbsolutePath()+"/avatar/"
                +getSharedPreferences("userInfo",MODE_PRIVATE).getString("user_email","")+".png";
        File file = new File(path);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        Bitmap bitmap = ((BitmapDrawable)userImg.getDrawable()).getBitmap();
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
    }

    //隐藏状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    //获取视图的控件
    public void getViews(){
        personal_center_updateImg_back=findViewById(R.id.personal_center_updateImg_back);
        userImg=findViewById(R.id.userImg_update);
        camera=findViewById(R.id.xiangji);
    }


}
