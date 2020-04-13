package com.example.smallpigeon.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Adapter.PeopleAdapter;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.Fragment.Dynamic.ReleaseDynamic;
import com.example.smallpigeon.MainActivity;
import com.example.smallpigeon.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PeopleFragment extends Fragment {
    private ListView dynamic_list;
    private ImageView iv_add_Message;//发表动态，右上角加号

    private LinearLayout ll_toComment;

    private PopupWindow mPopWindow;

    private MyClickListener listener;
    private PeopleAdapter adapter;
    private List<DynamicContent> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_people, container, false );
        getViews( view );
        listener = new MyClickListener();
        registerListener();

        iv_add_Message.setOnClickListener( listener );

        DynamicContent content = new DynamicContent();
        UserContent userContent = new UserContent();
        userContent.setUserNickname( "啦啦啦" );
        content.setDate( new SimpleDateFormat( "yyyy年-MM月-dd日" ).format( new Date() ) );
        content.setUserContent( userContent );
        content.setContent( "今日跑步分享" );
        content.setDevice( Build.MODEL );
        list.add( content );
        adapter = new PeopleAdapter( getContext(), R.layout.people_dynamic_listitem, list );
        dynamic_list.setAdapter( adapter );

        return view;
    }

    private void registerListener() {
        iv_add_Message.setOnClickListener( listener );
        ll_toComment.setOnClickListener( listener );
    }

    private void getViews(View view) {
        dynamic_list = view.findViewById( R.id.dynamic_list );
        iv_add_Message = view.findViewById( R.id.iv_add_Message );
        ll_toComment = view.findViewById( R.id.ll_toComment );

    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_add_Message:
                    //TODO:添加新的动态
                    //TODO:如果已登录，跳转发表动态
//                    if (loginOrNot()){
                    Intent intent = new Intent( getContext(), ReleaseDynamic.class );
                    startActivity( intent );
//                    } else {
//                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
//                    }
                    break;
                case R.id.iv_comment:
                    //TODO:点击转发时可以转发
                    break;
                case R.id.ll_toComment:
                    //TODO：点击评论可以进行评论
                    showPopupWindow();
                    break;
                case R.id.iv_praise:
                    //TODO:赞

                    break;

            }
        }
    }

    @SuppressLint("WrongConstant")
    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.comment_popuwindow, null);
        mPopWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //防止PopupWindow被软件盘挡住（可能只要下面一句，可能需要这两句）
        mPopWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置软键盘弹出
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);//这里给它设置了弹出的时间
        //设置各个控件的点击响应
//        final EditText editText = contentView.findViewById(R.id.pop_editText);
//        Button btn = contentView.findViewById(R.id.pop_btn);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String inputString = editText.getText().toString();
//                Toast.makeText( getContext(), inputString, Toast.LENGTH_SHORT).show();
//                mPopWindow.dismiss();//让PopupWindow消失
//            }
//        });
        //是否具有获取焦点的能力
        mPopWindow.setFocusable(true);
        //显示PopupWindow
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
}