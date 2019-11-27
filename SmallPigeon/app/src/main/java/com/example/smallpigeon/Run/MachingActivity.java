package com.example.smallpigeon.Run;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.smallpigeon.Entity.PlanContent;
import com.example.smallpigeon.MainActivity;
import com.example.smallpigeon.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Time: 2019/11/27
 * @Author: 程璐
 * @Descripe: 点击匹配模式后跳转的activity，显示未完成plan
 */
public class MachingActivity extends AppCompatActivity {

    //未完成plan列表
    private List<PlanContent> planContents = new ArrayList<>(  );;
    //视图控件
    private ImageView machingBack;
    private ListView lvMatchingTask;
    private Button btnRematch;

    private MyClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maching );

        //获取视图控件
        getViews();
        //注册监听器
        listener = new MyClickListener();
        registerListener();

        //获取数据源
        //todo 此处应该从数据库查询当前用户的所有未完成计划
        PlanContent planContent1 = new PlanContent( 1, 1, 1, new Date(System.currentTimeMillis()), "河北师范大学", "未完成" );
        PlanContent planContent2 = new PlanContent( 2, 2, 2, new Date(System.currentTimeMillis()), "河北师范大学", "未完成" );
        planContents.add( planContent1 );
        planContents.add( planContent2 );

        //加载未完成任务列表Adapter
        PlanAdapter planAdapter = new PlanAdapter( planContents, R.layout.run_maching_listitem, this);
        lvMatchingTask.setAdapter(planAdapter);
    }

    private void registerListener() {
        btnRematch.setOnClickListener( listener );
        machingBack.setOnClickListener( listener );
    }

    /**
     * @Descripe: 获取视图控件id
     */
    private void getViews() {
        lvMatchingTask = findViewById( R.id.lv_machingTask );
        btnRematch = findViewById( R.id.btn_rematch );
        machingBack = findViewById( R.id.maching_back );
    }

    private class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_rematch:
                    //重新匹配按钮
                    Intent intent1 = new Intent( MachingActivity.this, RemachingActivity.class );
                    startActivity( intent1 );
                    break;
                case R.id.maching_back:
                    //返回按钮
                    Intent intent2 = new Intent( MachingActivity.this, MainActivity.class );
                    startActivity( intent2 );
                    break;
            }
        }
    }
}