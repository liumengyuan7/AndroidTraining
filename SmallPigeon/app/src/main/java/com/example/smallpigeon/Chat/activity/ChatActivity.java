package com.example.smallpigeon.Chat.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.smallpigeon.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {
    private String userId;
    private String userNickname;
    private String myId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //use EaseChatFratFragment
        EaseChatFragment chatFragment = new EaseChatFragment();
        //pass parameters to chat fragment
//        Intent intent = getIntent();
//        userId = intent.getStringExtra("userId");
//        userNickname = intent.getStringExtra("userNickname");
//        myId = intent.getStringExtra("myId");
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, chatFragment).commit();
    }
}
