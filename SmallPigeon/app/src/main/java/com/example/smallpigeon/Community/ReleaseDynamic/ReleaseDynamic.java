package com.example.smallpigeon.Community.ReleaseDynamic;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.Fragment.PeopleFragment;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.BitmapCompat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReleaseDynamic extends AppCompatActivity {
    private static final int IMG_COUNT = 3;
    private static final String IMG_ADD_TAG = "a";
    private GridView gridView;
    private GVAdapter adapter;
    private TextView textView;
    private TextView cancle;
    private ImageView img;
    private List<String> list;
//    private List<String> bitmaps = new ArrayList<>();
    private EditText dynamic_content;
    //imgInfo为传入所有图片的名称，各个图片名中间用空格隔开
    private  String imgInfo="";
    private Utils utils;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj+"";
            if(result.equals("true")){
                Toast.makeText(getApplicationContext(),"动态发布成功，快去看看叭",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"动态发布失败，请重新发布",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_dynamic);
        gridView = (GridView) findViewById(R.id.gridview);
        dynamic_content = findViewById(R.id.dynamic_content);
        textView = (TextView) findViewById(R.id.send);
        cancle = findViewById(R.id.tv_cancel);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("发送：" + Integer.toString(list.size() - 1));
                System.out.println("发送"+dynamic_content.getText().toString());
                upLoad();

                String id = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString ("user_id","");
                UserContent userContent = new UserContent();
                DynamicContent dynamicContent = new DynamicContent();
                dynamicContent.setUserContent(userContent);
                dynamicContent.setContent(dynamic_content.getText().toString());
                dynamicContent.setDate(new Date().toLocaleString());
                dynamicContent.setZan_num(0);
                dynamicContent.setDevice(Build.MODEL);
                dynamicContent.setImg(imgInfo);
                if (imgInfo.equals("")||imgInfo==null){
                    dynamicContent.setType(3);
                }else {
                    dynamicContent.setType(1);
                }

                //将动态信息插入数据库
                Timestamp pushTime = new Timestamp(new Date().getTime());
                sendMessageToServer(id,dynamic_content.getText().toString(),pushTime);
                finish();
            }
        });
        initData();
    }
    //上传图片到后台
    private void upLoad() {
        Bitmap bitmap;
        Bitmap bmpCompressed;
        utils = new Utils();
        if (list != null) {
            Log.e("图片数组",list.size()+"");
            for (int i = 0; i < list.size() - 1; i++) {
               bitmap = BitmapFactory.decodeFile(list.get(i));
            //将缩略图进行扩大
//              bmpCompressed = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
//            utils.postPictureServer("dynamic","addDynamic", bmpCompressed);
            /*ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmpCompressed.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();
            System.out.println("upload:"+data);*/

            OkHttpClient okHttpClient = new OkHttpClient();
            Log.e("图片"+i,list.get(i));
            File file = new File(list.get(i));
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url("http://"+getResources().getString(R.string.ip_address)+":8080/smallpigeon/dynamic/getPicture")
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
        }
    }

    private void initData() {
        if (list == null) {
            list = new ArrayList<>();
            list.add(IMG_ADD_TAG);
        }
        adapter = new GVAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).equals(IMG_ADD_TAG)) {
                    if (list.size() < IMG_COUNT) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 0);
                    } else
                        Toast.makeText(ReleaseDynamic.this, "最多只能选择2张照片！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshAdapter();
    }

    private void refreshAdapter() {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (adapter == null) {
            adapter = new GVAdapter();
        }
        adapter.notifyDataSetChanged();
    }

    private class GVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplication()).inflate(R.layout.release_dynamic_pic_items, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.main_gridView_item_photo);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.main_gridView_item_cb);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String s = list.get(position);
            if (!s.equals(IMG_ADD_TAG)) {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.imageView.setImageBitmap(ImageTool.createImageThumbnail(s));
            } else {
                holder.checkBox.setVisibility(View.GONE);
                holder.imageView.setImageResource(R.drawable.addpic);
            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    refreshAdapter();
                }
            });
            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            System.out.println("data null");
            return;
        }
        if (requestCode == 0) {
            final Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri,null,null,null);
            cursor.moveToFirst();
            String imgPath = cursor.getString(1); //图片文件路径
            String imgName = cursor.getString(3); //图片文件名
            Log.e("imgName:",imgName);
            if (imgInfo==null || imgInfo.equals("")){
                imgInfo = imgName;
            }else {
                imgInfo = imgInfo+";"+imgName;
            }
            Log.e("图片信息imgInfo:",imgInfo);
            String path = ImageTool.getImageAbsolutePath(this, uri);
            Log.e("图片的路径:",path);
            Log.e("uri",uri.getPath());
            if (list.size() == IMG_COUNT) {
                removeItem();
                refreshAdapter();
                return;
            }
            removeItem();
            list.add(path);
            list.add(IMG_ADD_TAG);
            refreshAdapter();
        }
    }

    private void removeItem() {
        if (list.size() != IMG_COUNT) {
            if (list.size() != 0) {
                list.remove(list.size() - 1);
            }
        }
    }

    //将动态信息插入数据库
    private void sendMessageToServer(String id, String pushContent, Timestamp pushTime){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/dynamic/addDynamic?userId="+id
                            +"&&pushContent="+pushContent+"&&pushTime="+pushTime+"&&pushImg="+imgInfo);
                    Log.e("url",url.toString());
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    message.what = 2;
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
