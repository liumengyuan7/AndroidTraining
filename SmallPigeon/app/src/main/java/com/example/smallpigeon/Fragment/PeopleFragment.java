package com.example.smallpigeon.Fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Adapter.PeopleAdapter;
import com.example.smallpigeon.Community.ReleaseDynamic.ReleaseDynamic;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class PeopleFragment extends Fragment {
    private ListView dynamic_list;//动态列表
    private ImageView iv_add_Message;//发表动态，右上角加号
    private LinearLayout ll_toComment;//评论

    private MyClickListener listener;
    private PeopleAdapter peopleAdapter;
    private List<DynamicContent> dynamicContents = new ArrayList<>();

    private PopupWindow popupWindow;
    private View popupView = null;
    private EditText inputComment;
    private String nInputContentText;
    private TextView btn_submit;
    private RelativeLayout rl_input_container;
    private InputMethodManager mInputManager;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj+"";
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people,container,false);
        getViews(view);
        listener = new MyClickListener();
        registerListener();

        DynamicContent content = new DynamicContent();
        UserContent userContent = new UserContent();
        userContent.setUserNickname("啦啦啦");
        content.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
        content.setUserContent(userContent);
        content.setContent("今日跑步分享");
        content.setDevice(Build.MODEL);
        dynamicContents.add(content);

        //selectDynamic();

        //单条动态的点击事件
        peopleAdapter = new PeopleAdapter(getContext(),R.layout.people_dynamic_listitem, dynamicContents);
        dynamic_list.setAdapter(peopleAdapter);
        peopleAdapter.setBtnOnclick(new PeopleAdapter.btnOnclick() {
            @Override
            public void click(View view, int index) {
                switch (view.getId()){
                    case R.id.ll_toComment:
                        showPopupWindow();
                        break;
                    case R.id.ll_forward:
                        //todo:转发
                        break;
                    case R.id.ll_like:
                        //todo:点赞
                        break;
                }
            }
        });

        return view;
    }

    @SuppressLint("WrongConstant")
    private void showPopupWindow() {
        if (popupView == null){
            //加载评论框的资源文件
            popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup, null);
        }
        inputComment = (EditText) popupView.findViewById(R.id.et_discuss);
        btn_submit = (Button) popupView.findViewById(R.id.btn_confirm);
        rl_input_container = (RelativeLayout)popupView.findViewById(R.id.rl_input_container);
        //利用Timer这个Api设置延迟显示软键盘，这里时间为200毫秒
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run()
            {
                mInputManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputManager.showSoftInput(inputComment, 0);
                mInputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 200);
        if (popupWindow == null){
            popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT, false);

        }
        //popupWindow的常规设置，设置点击外部事件，背景色
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                    popupWindow.dismiss();
                return false;

            }
        });

        // 设置弹出窗体需要软键盘，放在setSoftInputMode之前
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        // 再设置模式，和Activity的一样，覆盖，调整大小。
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置popupwindow的显示位置，这里应该是显示在底部，即Bottom
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);

        popupWindow.update();

        //设置监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            public void onDismiss() {
                mInputManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        //外部点击事件
        rl_input_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0); //强制隐藏键盘
                popupWindow.dismiss();
            }
        });
        //评论框内的发送按钮设置点击事件
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nInputContentText = inputComment.getText().toString().trim();
                Toast.makeText(getContext(),nInputContentText,Toast.LENGTH_SHORT).show();
                if (nInputContentText == null || "".equals(nInputContentText)) {
                    //showToastMsgShort("请输入评论内容");
                    return;
                }
                mInputManager.hideSoftInputFromWindow(inputComment.getWindowToken(),0);
                popupWindow.dismiss();
            }
        });
    }

    //TODO:查出所有动态
    private void selectDynamic() {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("","");
                Message message = new Message();
                message.obj = result;
                handler.sendMessage(message);
            }
        }.start();
    }

    //TODO:得到从后台传入的图片并设置到对应的动态上


    private void registerListener() {
        iv_add_Message.setOnClickListener(listener);
    }

    private void getViews(View view) {
        dynamic_list = view.findViewById(R.id.dynamic_list);
        iv_add_Message = view.findViewById(R.id.iv_add_Message);
    }

    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_add_Message:
                    //TODO:添加新的动态
                    //TODO:如果已登录，跳转发表动态
//                    if (loginOrNot()){
                        Intent intent = new Intent(getContext(), ReleaseDynamic.class);
                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
//                    }
                    break;
            }
        }
    }
}