package com.example.smallpigeon.Fragment.Dynamic;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class ReleaseDynamic extends AppCompatActivity {
    private static final int IMG_COUNT = 3;
    private static final String IMG_ADD_TAG = "a";
    private GridView gridView;
    private GVAdapter adapter;
    private TextView textView;
    private ImageView img;
    private List<String> list;
    private EditText dynamic_content;
    //imgInfo为传入所有图片的名称，各个图片名中间用空格隔开
    private  String imgInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_dynamic);
        gridView = (GridView) findViewById(R.id.gridview);
        dynamic_content = findViewById(R.id.dynamic_content);
        textView = (TextView) findViewById(R.id.send);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("发送：" + Integer.toString(list.size() - 1));
                System.out.println("发送"+dynamic_content.getText().toString());
                upLoad();
                //TODO:插入动态的信息以及用户信息
                String id = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString ("user_id","");
                UserContent userContent = new UserContent();//TODO：从数据库中查出用户信息
                DynamicContent dynamicContent = new DynamicContent();
                dynamicContent.setUserContent(userContent);
                dynamicContent.setContent(dynamic_content.getText().toString());
                dynamicContent.setDate(new Date().toLocaleString());
                dynamicContent.setZan_num(0);
                dynamicContent.setDevice(Build.MODEL);
                dynamicContent.setImg(imgInfo);
                //TODO:将动态信息插入数据库
                Intent intent = new Intent(getApplicationContext(), PeopleFragment.class);
                startActivity(intent);
            }
        });
        initData();
    }
    private void upLoad() {
        Bitmap bitmap;
        Bitmap bmpCompressed;
        for (int i = 0; i < list.size() - 1; i++) {
            bitmap = BitmapFactory.decodeFile(list.get(i));
            bmpCompressed = Bitmap.createScaledBitmap(bitmap, 640, 480, true);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmpCompressed.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();
            System.out.println("upload:"+data);
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
            imgInfo = imgInfo+imgName+" ";
            System.out.println("imgInfo:"+imgInfo);
            System.out.println("imgPath:"+imgPath+", imgName:"+imgName);
            String path = ImageTool.getImageAbsolutePath(this, uri);

            System.out.println(path);
            Log.e("path",path);
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
}
