package com.example.smallpigeon.Run;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.MainActivity;
import com.example.smallpigeon.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Time: 2019/11/27
 * @Author: 程璐
 * @Descripe: 点击匹配模式——重新匹配好友后跳转的页面，根据匹配机制重新匹配，显示并聊天
 */
public class RemachingActivity extends AppCompatActivity {

    //未完成plan列表
    private List<UserContent> userContents = new ArrayList<>(  );;
    //视图控件
    private ListView lvRemachingUsers;
    private ImageView remachingBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_remaching );

        //获取视图控件
        getViews();

        //返回按钮
        remachingBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( RemachingActivity.this, MachingActivity.class );
                startActivity( intent );
            }
        } );

        //获取数据源
        //todo 此处应该从数据库查询所有和当前用户匹配的用户信息
        UserContent userContent = new UserContent( 1, "用户姓名", "用户昵称",
                "用户密码", "用户性别", "用户邮箱", "用户头像",
                new Date(System.currentTimeMillis()), "用户编号", 25 );
        userContents.add( userContent );

        //加载未完成任务列表Adapter
        RemachingUserAdapter remachingUserAdapter = new RemachingUserAdapter( userContents, R.layout.run_maching_remaching_listitem, getApplicationContext());
        lvRemachingUsers.setAdapter(remachingUserAdapter);
    }

    /**
     * @Descripe: 获取视图控件id
     */
    private void getViews() {
        lvRemachingUsers = findViewById( R.id.lv_remachingUsers );
        remachingBack = findViewById( R.id.remaching_back );
    }

}
