package com.example.smallpigeon.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.smallpigeon.Adapter.PeopleAdapter;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.Fragment.Dynamic.ReleaseDynamic;
import com.example.smallpigeon.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PeopleFragment extends Fragment {
    private ListView dynamic_list;
    private ImageView iv_add_Message;//发表动态，右上角加号

    private MyClickListener listener;
    private PeopleAdapter adapter;
    private List<DynamicContent> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people,container,false);
        getViews(view);
        listener = new MyClickListener();
        registerListener();

        iv_add_Message.setOnClickListener( listener );

        DynamicContent content = new DynamicContent();
        UserContent userContent = new UserContent();
        userContent.setUserNickname("啦啦啦");
        content.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
        content.setUserContent(userContent);
        content.setContent("今日跑步分享");
        content.setDevice(Build.MODEL);
        list.add(content);
        adapter = new PeopleAdapter(getContext(),R.layout.people_dynamic_listitem,list);
        dynamic_list.setAdapter(adapter);

        return view;
    }

    private void registerListener() {
        iv_add_Message.setOnClickListener(listener);
    }

    private void getViews(View view) {
        dynamic_list = view.findViewById(R.id.dynamic_list);
        iv_add_Message = view.findViewById(R.id.iv_add_Message);
    }

    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_add_Message:
                    //TODO:添加新的动态
                    //TODO:如果已登录，跳转发表动态
//                    if (loginOrNot()){
                        Intent intent = new Intent(getContext(),ReleaseDynamic.class);
                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
//                    }
                    break;
                case R.id.iv_unfold:
                    //TODO:点击转发时可以转发
                    break;
                case R.id.iv_comment:
                    //TODO：点击评论可以进行评论
                    break;
                case R.id.iv_praise:
                    //TODO:赞

                    break;

            }
        }
    }
}
