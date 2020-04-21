package com.example.smallpigeon.Fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class PeopleFragment extends Fragment {
    private ListView dynamic_list;
    private ImageView iv_add_Message;//发表动态，右上角加号
    private MyClickListener listener;
    private PeopleAdapter peopleAdapter;
    private List<DynamicContent> list = new ArrayList<>();

    private PopupWindow popupWindow;
    private View popupView = null;
    private EditText et_discuss;
    private String nInputContentText;
    private TextView btn_submit;
    private RelativeLayout rl_input_container;
    private InputMethodManager mInputManager;

    private  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj + "";
            if(!result.equals("false")){
                list.clear();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        DynamicContent content = new DynamicContent();
                        UserContent userContent = new UserContent();
                        userContent.setUserNickname(json.get("user_nickname").toString());
                        String time = json.get("push_time").toString();
                        Date d = new Date(time);
                        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
                        content.setDate(sdf.format(d).toString());//时间转换
                        content.setUserContent(userContent);
                        content.setContent(json.get("push_content").toString());
                        String [] imgs = json.getString("push_image").split(";");
                        content.setImg(imgs[0]);
                        if(imgs.length==2) {
                            content.setImg2(imgs[1]);
                        }
                        content.setDevice(Build.MODEL);
                        list.add(content);
                        peopleAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast toastTip = Toast.makeText(getContext(),"获取动态失败！请检查网络！",Toast.LENGTH_LONG);
                toastTip.setGravity(Gravity.CENTER, 0, 0);
                toastTip.show();
            }
        }
    };
    private PopupWindow mPopWindow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people,container,false);
        getViews(view);
        listener = new MyClickListener();
        registerListener();

        //显示后台服务器存储的所有发布的动态
//        selectAllDynamic();

        //前端测试用
        DynamicContent content = new DynamicContent();
        UserContent userContent = new UserContent();
        userContent.setUserNickname("啦啦啦");
        content.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
        content.setUserContent(userContent);
        content.setContent("今日跑步分享");
        content.setDevice(Build.MODEL);
        list.add(content);

        peopleAdapter = new PeopleAdapter(getContext(),R.layout.people_dynamic_listitem,list);
        dynamic_list.setAdapter(peopleAdapter);
        peopleAdapter.setBtnOnclick(new PeopleAdapter.btnOnclick() {
            @Override
            public void click(View view, int index) {
                switch (view.getId()){
                    case R.id.ll_toComment:
                        showPopupWindow("comment");
                        break;
                    case R.id.ll_forward:
                        //todo:转发
                        showPopupWindow("forward");
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
    private void showPopupWindow(String type) {
//        if (loginOrNot()){
            if (popupView == null){
                //加载评论框的资源文件
                popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup, null);
            }
            et_discuss = (EditText) popupView.findViewById(R.id.et_discuss);
            btn_submit = (Button) popupView.findViewById(R.id.btn_confirm);
            rl_input_container = (RelativeLayout)popupView.findViewById(R.id.rl_input_container);
            if (type == "comment"){
                et_discuss.setHint( "说点什么吧……" );
            } else if (type == "forward"){
                et_discuss.setHint( "转发理由……" );
            }
            //利用Timer这个Api设置延迟显示软键盘，这里时间为200毫秒
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run()
                {
                    mInputManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mInputManager.showSoftInput(et_discuss, 0);
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
            et_discuss.setFocusable(true);
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
                    mInputManager.hideSoftInputFromWindow(et_discuss.getWindowToken(), 0); //强制隐藏键盘
                }
            });
            //外部点击事件
            rl_input_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInputManager.hideSoftInputFromWindow(et_discuss.getWindowToken(), 0); //强制隐藏键盘
                    popupWindow.dismiss();
                }
            });
            //评论框内的发送按钮设置点击事件
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nInputContentText = et_discuss.getText().toString().trim();
                    Toast.makeText(getContext(),nInputContentText,Toast.LENGTH_SHORT).show();
                    if (nInputContentText == null || "".equals(nInputContentText)) {
                        Toast.makeText(getContext(),"内容不能为空！",Toast.LENGTH_LONG).show();
                    }else {
                        mInputManager.hideSoftInputFromWindow(et_discuss.getWindowToken(),0);
                        popupWindow.dismiss();
                        //TODO：发送成功，与后台交互
                    }
                }
            });
//        }else {
//            Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
//        }
    }

    //查出所有动态
    private void selectAllDynamic() {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","getAllDynamic");
                Message message = new Message();
                message.obj = result;
                handler.sendMessage(message);
            }
        }.start();
    }

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
                    if (loginOrNot()){
                        Intent intent = new Intent(getContext(), ReleaseDynamic.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private boolean loginOrNot(){
        SharedPreferences pre = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userEmail = pre.getString("user_email","");
        if(userEmail.equals("")||userEmail==null){
            return false;
        }else{
            return true;
        }
    }
}