package com.example.smallpigeon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smallpigeon.Adapter.FragmentAdapter;
import com.example.smallpigeon.Fragment.ChatFragment;
import com.example.smallpigeon.Fragment.MyFragment;
import com.example.smallpigeon.Fragment.PeopleFragment;
import com.example.smallpigeon.Fragment.RunFragment;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private QMUIViewPager viewPager;

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

    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar();
        findViews();
        //创建avatar文件夹
        createAvatar();
        //创建FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        //初始化内容页对应的Fragment
        peopleFragment = new PeopleFragment();
        runFragment = new RunFragment();
        chatFragment = new ChatFragment();
        myFragment=new MyFragment();
        initViewPager();
        setListeners();
    }

    private void initViewPager(){
        fragmentList = new ArrayList<>();
        fragmentList.add( 0, peopleFragment );
        fragmentList.add( 1, runFragment );
        fragmentList.add( 2, chatFragment );
        fragmentList.add( 3, myFragment );

        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragmentManager, fragmentList);
        //ViewPager设置适配器
        viewPager.setAdapter(fragmentAdapter);
        //ViewPager显示第一个Fragment
        viewPager.setCurrentItem(1);
        //ViewPager页面切换监听
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private class MyOnPageChangeListener implements QMUIViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    A();
                    break;
                case 1:
                    B();
                    break;
                case 2:
                    C();
                    break;
                case 3:
                    D();
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) { }
    }

    //创建avatar文件夹
    private void createAvatar() {
        String path = getFilesDir().getAbsolutePath()+"/avatar";
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
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
        viewPager = findViewById( R.id.viewPager );

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
                    viewPager.setCurrentItem( 0 );
                    A();//变色
                    break;
                case R.id.tab2:
                    viewPager.setCurrentItem( 1 );
                    B();
                    break;
                case R.id.tab3:
                    viewPager.setCurrentItem( 2 );
                    C();
                    break;
                case R.id.tab4:
                    viewPager.setCurrentItem( 3 );
                    D();
                    break;
            }
        }
    }

    public void A(){
        peopleIv.setImageDrawable(getResources().getDrawable(R.drawable.community_green ));
        runIv.setImageDrawable(getResources().getDrawable(R.drawable.run_black ));
        chatIv.setImageDrawable(getResources().getDrawable(R.drawable.chat_black ));
        myIv.setImageDrawable(getResources().getDrawable(R.drawable.my_black ));
        PeopleTv.setTextColor(getResources().getColor( R.color.colorPrimary ));
        RunTv.setTextColor(Color.parseColor("#737373"));
        ChatTv.setTextColor(Color.parseColor("#737373"));
        MyTv.setTextColor(Color.parseColor("#737373"));
    }

    public void B(){
        peopleIv.setImageDrawable(getResources().getDrawable(R.drawable.community_black ));
        runIv.setImageDrawable(getResources().getDrawable(R.drawable.run_green ));
        chatIv.setImageDrawable(getResources().getDrawable(R.drawable.chat_black ));
        myIv.setImageDrawable(getResources().getDrawable(R.drawable.my_black ));
        PeopleTv.setTextColor(Color.parseColor("#737373"));
        RunTv.setTextColor(getResources().getColor( R.color.colorPrimary ));
        ChatTv.setTextColor(Color.parseColor("#737373"));
        MyTv.setTextColor(Color.parseColor("#737373"));
    }

    public void C(){
        peopleIv.setImageDrawable(getResources().getDrawable(R.drawable.community_black ));
        runIv.setImageDrawable(getResources().getDrawable(R.drawable.run_black ));
        chatIv.setImageDrawable(getResources().getDrawable(R.drawable.chat_green ));
        myIv.setImageDrawable(getResources().getDrawable(R.drawable.my_black ));
        PeopleTv.setTextColor(Color.parseColor("#737373"));
        RunTv.setTextColor(Color.parseColor("#737373"));
        ChatTv.setTextColor(getResources().getColor( R.color.colorPrimary ));
        MyTv.setTextColor(Color.parseColor("#737373"));
    }

    public void D(){
        peopleIv.setImageDrawable(getResources().getDrawable(R.drawable.community_black ));
        runIv.setImageDrawable(getResources().getDrawable(R.drawable.run_black ));
        chatIv.setImageDrawable(getResources().getDrawable(R.drawable.chat_black ));
        myIv.setImageDrawable(getResources().getDrawable(R.drawable.my_green ));
        PeopleTv.setTextColor(Color.parseColor("#737373"));
        RunTv.setTextColor(Color.parseColor("#737373"));
        ChatTv.setTextColor(Color.parseColor("#737373"));
        MyTv.setTextColor(getResources().getColor( R.color.colorPrimary ));
    }
}