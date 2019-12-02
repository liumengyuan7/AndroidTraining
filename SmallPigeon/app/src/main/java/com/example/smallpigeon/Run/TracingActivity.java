package com.example.smallpigeon.Run;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        tracingBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( TracingActivity.this, MainActivity.class );
                startActivity( intent );
            }
        } );
    }
}
