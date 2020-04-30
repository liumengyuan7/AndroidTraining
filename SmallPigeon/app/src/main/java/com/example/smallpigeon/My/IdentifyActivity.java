package com.example.smallpigeon.My;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.smallpigeon.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class IdentifyActivity extends AppCompatActivity {
    private EditText et_name;
    private EditText et_school;
    private ImageView iv_front;
    private ImageView iv_back;
    private Button btn_submit;

    private MyClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);

        getViews();
        registerListeners();
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
                    String name = et_name.getText().toString().trim();
                    String school = et_school.getText().toString().trim();
                    //TODO:将相应信息传到后台
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            iv_front.setImageBitmap(photo);
            String name = avatarStore(iv_front);
            sendImg(name);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            iv_back.setImageBitmap(photo);
            String name = avatarStore(iv_front);
            sendImg(name);
        }
    }

    //TODO:向后台发送照片数据,path为图片的存储路径,参数为随机生成的图片名称
    private void sendImg(String name) {
        String path = getFilesDir().getAbsolutePath()+"/"+name;
        File file = new File(path);

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
}
