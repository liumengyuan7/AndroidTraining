package com.example.smallpigeon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Fragment.ChatFragment;
import com.example.smallpigeon.Fragment.MyFragment;
import com.example.smallpigeon.Fragment.PeopleFragment;
import com.example.smallpigeon.Fragment.RunFragment;

public class MainActivity extends AppCompatActivity {

    private LinearLayout PeopleTab;
    private LinearLayout RunTab;
    private LinearLayout ChatTab;
    private LinearLayout MyTab;

    private ImageView peopleIv;
    private ImageView runIv;
    private ImageView chatIv;
    private ImageView myIv;

    private TextView PeopleTv;
    private TextView RunTv;
    private TextView ChatTv;
    private TextView MyTv;

    private FragmentManager fragmentManager;
    private PeopleFragment peopleFragment;
    private RunFragment runFragment;
    private ChatFragment chatFragment;
    private MyFragment myFragment;

    //当前正在显示的Fragment
    private Fragment currentFragment = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar();
        findViews();
        setListeners();
        //创建FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        //初始化内容页对应的Fragment
        peopleFragment = new PeopleFragment();
        runFragment = new RunFragment();
        chatFragment = new ChatFragment();
        myFragment=new MyFragment();
        showFragment(runFragment);
    }

    //隐藏状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    //获取所有视图控件的引用
    public void findViews(){
        PeopleTab = findViewById(R.id.tab1);
        RunTab = findViewById(R.id.tab2);
        ChatTab = findViewById(R.id.tab3);
        MyTab = findViewById(R.id.tab4);

        peopleIv = findViewById(R.id.img1);
        runIv = findViewById(R.id.img2);
        chatIv = findViewById(R.id.img3);
        myIv=findViewById(R.id.img4);

        PeopleTv = findViewById(R.id.tv_title1);
        RunTv = findViewById(R.id.tv_title2);
        ChatTv = findViewById(R.id.tv_title3);
        MyTv = findViewById(R.id.tv_title4);
    }

    public void setListeners(){
        MyClickListener myClickListener = new MyClickListener();
        PeopleTab.setOnClickListener(myClickListener);
        RunTab.setOnClickListener(myClickListener);
        ChatTab.setOnClickListener(myClickListener);
        MyTab.setOnClickListener(myClickListener);
    }

    private class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tab1:
                    //显示Fragment
                    //showFragment(peopleFragment);
                    Toast toastTip = Toast.makeText(MainActivity.this,"程序员们正在努力开发，敬请期待！",Toast.LENGTH_LONG);
                    toastTip.setGravity(Gravity.CENTER, 0, 0);
                    toastTip.show();
                    A();//变色
                    break;
                case R.id.tab2:
                    showFragment(runFragment);
                    B();
                    break;
                case R.id.tab3:
                    showFragment(chatFragment);
                    C();
                    break;
                case R.id.tab4:
                    showFragment(myFragment);
                    D();
                    break;
            }
        }
    }

    public void showFragment(Fragment fragment){
        //事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment != currentFragment){
            //隐藏正在显示的Fragment
            transaction.hide(currentFragment);
            //添加将要显示的Fragment
            if (!fragment.isAdded()) {
                transaction.add(R.id.tabContent, fragment);
            }
            //显示
            transaction.show(fragment);
            //提交事务
            transaction.commit();
            //切换当前选中的Fragment
            currentFragment = fragment;
        }
    }

    public void A(){
        peopleIv.setImageDrawable(getResources().getDrawable(R.drawable.shequgreen));
        runIv.setImageDrawable(getResources().getDrawable(R.drawable.runblack));
        chatIv.setImageDrawable(getResources().getDrawable(R.drawable.liaotianblack));
        myIv.setImageDrawable(getResources().getDrawable(R.drawable.myblack));
        PeopleTv.setTextColor(Color.parseColor("#259B24"));
        RunTv.setTextColor(Color.parseColor("#737373"));
        ChatTv.setTextColor(Color.parseColor("#737373"));
        MyTv.setTextColor(Color.parseColor("#737373"));
    }

    public void B(){
        peopleIv.setImageDrawable(getResources().getDrawable(R.drawable.shequblack));
        runIv.setImageDrawable(getResources().getDrawable(R.drawable.rungreen));
        chatIv.setImageDrawable(getResources().getDrawable(R.drawable.liaotianblack));
        myIv.setImageDrawable(getResources().getDrawable(R.drawable.myblack));

        PeopleTv.setTextColor(Color.parseColor("#737373"));
        RunTv.setTextColor(Color.parseColor("#259B24"));
        ChatTv.setTextColor(Color.parseColor("#737373"));
        MyTv.setTextColor(Color.parseColor("#737373"));
    }

    public void C(){
        peopleIv.setImageDrawable(getResources().getDrawable(R.drawable.shequblack));
        runIv.setImageDrawable(getResources().getDrawable(R.drawable.runblack));
        chatIv.setImageDrawable(getResources().getDrawable(R.drawable.liaotiangreen));
        myIv.setImageDrawable(getResources().getDrawable(R.drawable.myblack));

        PeopleTv.setTextColor(Color.parseColor("#737373"));
        RunTv.setTextColor(Color.parseColor("#737373"));
        ChatTv.setTextColor(Color.parseColor("#259B24"));
        MyTv.setTextColor(Color.parseColor("#737373"));
    }

    public void D(){
        peopleIv.setImageDrawable(getResources().getDrawable(R.drawable.shequblack));
        runIv.setImageDrawable(getResources().getDrawable(R.drawable.runblack));
        chatIv.setImageDrawable(getResources().getDrawable(R.drawable.liaotianblack));
        myIv.setImageDrawable(getResources().getDrawable(R.drawable.mygreen));

        PeopleTv.setTextColor(Color.parseColor("#737373"));
        RunTv.setTextColor(Color.parseColor("#737373"));
        ChatTv.setTextColor(Color.parseColor("#737373"));
        MyTv.setTextColor(Color.parseColor("#259B24"));
    }
}
