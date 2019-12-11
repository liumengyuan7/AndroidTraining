package com.example.smallpigeon.Chat.activity;

import android.os.Bundle;

import com.example.smallpigeon.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //use EaseChatFratFragment
        EaseChatFragment chatFragment = new EaseChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, chatFragment).commit();
    }
}
