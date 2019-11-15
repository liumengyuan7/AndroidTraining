package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.smallpigeon.Fragment.MyFragment;
import com.example.smallpigeon.R;

public class Personal_center_updateUserNickname extends AppCompatActivity {
    private EditText edtNickname;
    private ImageView personal_center_updateNickname_back;
    private Button Personal_center_btnSaveNickname;
    private CustomeClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center_update_user_nickname);
        personal_center_updateNickname_back=findViewById(R.id.personal_center_updateNickname_back);
        edtNickname=findViewById(R.id.edit_Nickname);
        Personal_center_btnSaveNickname=findViewById(R.id.personal_center_btnSaveNickname);

        listener=new CustomeClickListener();
        personal_center_updateNickname_back.setOnClickListener(listener);
        Personal_center_btnSaveNickname.setOnClickListener(listener);
    }


    class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.personal_center_updateNickname_back:
                    Intent intent = new Intent(Personal_center_updateUserNickname.this, PersonalCenter.class);
                    startActivity(intent);
                    //返回到个人中心界面
                    break;
                case R.id.personal_center_btnSaveNickname:
                    String user_new_Nickname=edtNickname.toString();
                    //Send-to-mysql
                    //Send();
                    break;

            }


        }
    }
}
