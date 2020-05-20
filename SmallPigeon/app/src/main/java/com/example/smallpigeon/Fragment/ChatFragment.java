package com.example.smallpigeon.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Chat.ChatHelper;
import com.example.smallpigeon.Chat.activity.AddContactActivity;
import com.example.smallpigeon.Chat.fragment.ContactListFragment;
import com.example.smallpigeon.Chat.fragment.ConversationListFragment;
import com.example.smallpigeon.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class ChatFragment extends Fragment {

    private ImageView addFriends;
    private View view;
    private FragmentManager fragmentManager;
    private ConversationListFragment conversationListFragment;
    private ContactListFragment listFragment;
    private Fragment currentFragment = new Fragment();
    private TextView tvHuihua;
    private TextView tvFriends;
    private LinearLayout tabHuihua;
    private LinearLayout tabFriends;
    private String useId="";
    private Resources resources;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat,container,false);
        return view;
    }

    private void signOut() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().logout(true);
            }
        }).start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences pre = getContext().getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        String nickname = pre.getString("user_nickname","");
        useId = pre.getString("user_id","");
        if(!nickname.equals("") && nickname != null){
            signIn(useId);
            ChatHelper.getInstance().sendMessageToGetContactList(getContext(), Integer.parseInt(useId));
        }
        findById();
        setListeners();
        fragmentManager = getChildFragmentManager();
        conversationListFragment = new ConversationListFragment();
        listFragment = new ContactListFragment();
        showFragment(conversationListFragment);
        currentFragment = conversationListFragment;
    }

    private void findById() {
        tabHuihua = view.findViewById(R.id.tabHuihua);
        tabFriends = view.findViewById(R.id.tabFriends);
        tvHuihua = view.findViewById(R.id.tvHuihua);
        tvFriends = view.findViewById(R.id.tvFriends);
        addFriends = view.findViewById(R.id.addFriends);
    }

    private void setListeners() {
        MyClickListener myClickListener = new MyClickListener();
        tabHuihua.setOnClickListener(myClickListener);
        tabFriends.setOnClickListener(myClickListener);
        addFriends.setOnClickListener(myClickListener);
    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            resources = getContext().getResources();
            switch (v.getId()){
                case R.id.tabHuihua:
                    showFragment(conversationListFragment);
                    tvHuihua.setTextColor(resources.getColor( R.color.black ));
                    tabHuihua.setBackgroundResource( R.drawable.bg_chat_title );
                    tvFriends.setTextColor(resources.getColor( R.color.btn_gray_pressed ));
                    tabFriends.setBackgroundResource( 0 );
                    break;
                case R.id.tabFriends:
                    showFragment(listFragment);
                    currentFragment = listFragment;
                    tvFriends.setTextColor(resources.getColor( R.color.black ));
                    tabFriends.setBackgroundResource( R.drawable.bg_chat_title );
                    tvHuihua.setTextColor(resources.getColor( R.color.btn_gray_pressed ));
                    tabHuihua.setBackgroundResource( 0 );
                    break;
                case R.id.addFriends:
                    //TODO:先判断用户是否登陆，若没有登陆则提示用户先登录，用户登陆后才能进行跳转
                    if(loginOrNot()){
//                        ChatHelper.getInstance().sendMessageToSearchAllUser(getContext());
                        Intent intent = new Intent(getContext(), AddContactActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    public void showFragment(Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment != currentFragment){
            transaction.hide(currentFragment);
            if (!fragment.isAdded()) {
                transaction.add(R.id.tabContent, fragment);
            }
            transaction.show(fragment);
            transaction.commit();
            currentFragment = fragment;
        }
    }

    private void signIn(String userId) {
        EMClient.getInstance().login(userId, userId, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("ChatFragment环信登录","登录成功");
            }
            @Override
            public void onError(int i, String s) {
                Log.e("ChatFragment环信登录","登录失败"+i+","+s);
            }
            @Override
            public void onProgress(int i, String s) { }
        });
    }

    //判断是否登录的方法
    private boolean loginOrNot(){
        SharedPreferences pre = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userEmail = pre.getString("user_email","");
        if(userEmail.equals("")||userEmail==null){
            return false;
        } else{
            return true;
        }
    }
}