package com.example.smallpigeon.Run;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.smallpigeon.MainActivity;
import com.example.smallpigeon.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 *@time: 2019/11/27
 *@author: 程璐
 */
public class TracingActivity extends AppCompatActivity {

    private ImageView tracingBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tracing );

        tracingBack = findViewById( R.id.tracing_back );
        setStatusBar();//状态栏隐藏

        tracingBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( TracingActivity.this, MainActivity.class );
                startActivity( intent );
            }
        } );
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }
}
