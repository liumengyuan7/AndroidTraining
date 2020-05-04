package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.smallpigeon.Adapter.MyDynamicAdapter;
import com.example.smallpigeon.Community.ReleaseDynamic.ReleaseDynamic;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyCommunity extends AppCompatActivity {

    private ImageView iv_back;
    private ImageView iv_add_Message;
    private ListView my_dynamic_list;

    private CustomClickListener listener;

    private MyDynamicAdapter myDynamicAdapter;
    private List<DynamicContent> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_my_community );

        getViews();
        listener = new CustomClickListener();
        registerListener();
        setStatusBar();

        //前端测试用
        DynamicContent content = new DynamicContent();
        UserContent userContent = new UserContent();
        userContent.setUserNickname("啦啦啦");
        content.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
        content.setUserContent(userContent);
        content.setContent("今日跑步分享");
        content.setDevice(Build.MODEL);
        list.add(content);

        myDynamicAdapter = new MyDynamicAdapter( MyCommunity.this, R.layout.people_dynamic_listitem, list );
//        my_dynamic_list.setAdapter(myDynamicAdapter);
//        //item点击事件
//        my_dynamic_list.setOnItemClickListener( new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText( getApplicationContext(), "hh", Toast.LENGTH_SHORT ).show();
//            }
//        } );
    }

    class CustomClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.iv_add_Message:
                    //发表动态
                    Intent intent = new Intent(MyCommunity.this, ReleaseDynamic.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void registerListener() {
        iv_back.setOnClickListener( listener );
        iv_add_Message.setOnClickListener( listener );
    }

    private void getViews() {
        iv_back = findViewById( R.id.iv_back );
        iv_add_Message = findViewById( R.id.iv_add_Message );
        my_dynamic_list =findViewById( R.id.my_dynamic_list );
    }

    //设置手机的状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }
}