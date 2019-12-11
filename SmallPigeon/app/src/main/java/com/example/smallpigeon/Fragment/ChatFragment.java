package com.example.smallpigeon.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.example.smallpigeon.Chat.activity.AddContactActivity;
import com.example.smallpigeon.Chat.fragment.ContactListFragment;
import com.example.smallpigeon.Chat.fragment.ConversationListFragment;
import com.example.smallpigeon.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;


public class ChatFragment extends Fragment {
    private ConversationListFragment conversationListFragment;
    private ContactListFragment listFragment;
    private TextView tvHuihua;
    private TextView tvFriends;
    private LinearLayout tabHuihua;
    private LinearLayout tabFriends;
    private Fragment currentFragment = new Fragment();
    private FragmentManager fragmentManager;
    private View view;
    private ImageView addFriends;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat,container,false);
        addFriends = view.findViewById(R.id.addFriends);
        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddContactActivity.class);
                startActivity(intent);
            }
        });
//        SharedPreferences pre = getContext().getSharedPreferences("userInfo",Context.MODE_PRIVATE);
//        String nickname = pre.getString("user_nickname","");
//        String useId = pre.getString("user_id","");
//        if(!nickname.equals("") && nickname != null){
//            signIn(useId);
//        }
//        else{
//            signOut();
//        }
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
    }
    private void setListeners() {
        MyClickListener myClickListener = new MyClickListener();
        tabHuihua.setOnClickListener(myClickListener);
        tabFriends.setOnClickListener(myClickListener);
    }
    private class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tabHuihua:
                    showFragment(conversationListFragment);
                    tvHuihua.setTextColor(Color.parseColor("#259B24"));
                    tvFriends.setTextColor(Color.parseColor("#737373"));
                    break;
                case R.id.tabFriends:
                    showFragment(listFragment);
                    currentFragment = listFragment;
                    tvHuihua.setTextColor(Color.parseColor("#737373"));
                    tvFriends.setTextColor(Color.parseColor("#259B24"));
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
            public void onProgress(int i, String s) {

            }
        });
    }
}
