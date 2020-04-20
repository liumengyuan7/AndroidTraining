package com.example.smallpigeon.Fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
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


public class PeopleFragment extends Fragment {
    private ListView dynamic_list;
    private ImageView iv_add_Message;//发表动态，右上角加号
    private ImageView iv_unfold;
    private ImageView iv_comment;
    private ImageView iv_praise;
    private MyClickListener listener;
    private PeopleAdapter adapter;
    private List<DynamicContent> list = new ArrayList<>();

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
                        adapter.notifyDataSetChanged();
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
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_people,container,false);

//        listener = new MyClickListener();
//        registerListener();
//        //显示后台服务器存储的所有发布的动态
//        selectAllDynamic();
////        DynamicContent content = new DynamicContent();
////        UserContent userContent = new UserContent();
////        userContent.setUserNickname("啦啦啦");
////        content.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
////        content.setUserContent(userContent);
////        content.setContent("今日跑步分享");
////        content.setDevice(Build.MODEL);
////        list.add(content);
//        //selectDynamic();
////        adapter = new PeopleAdapter(getContext(),R.layout.people_dynamic_listitem,list);
////        dynamic_list.setAdapter(adapter);
//        dynamic_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                iv_comment = view.findViewById(R.id.iv_comment);
//                Toast.makeText( getContext(), "xxx", Toast.LENGTH_SHORT ).show();
//                iv_comment.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText( getContext(), "评论", Toast.LENGTH_SHORT ).show();
//                        showPopupWindow();
//                    }
//                });
//            }
//        });
        return view;
    }

    @SuppressLint("WrongConstant")
    private void showPopupWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup, null);
        mPopWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //防止PopupWindow被软件盘挡住（可能只要下面一句，可能需要这两句）
        mPopWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //设置软键盘弹出
        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//这里给它设置了弹出的时间

        //设置各个控件的点击响应
        final EditText editText = contentView.findViewById(R.id.pop_editText);
        Button btn = contentView.findViewById(R.id.pop_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputString = editText.getText().toString();
                Toast.makeText(getActivity(), inputString, Toast.LENGTH_SHORT).show();
                /*TextView textView = new TextView(CommentActivity.this);
                textView.setText(inputString);
                ll.addView(textView);*/
                mPopWindow.dismiss();//让PopupWindow消失
            }
        });
        //是否具有获取焦点的能力
        mPopWindow.setFocusable(true);
        //显示PopupWindow
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_people, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getViews(view);
        adapter = new PeopleAdapter(getContext(),R.layout.people_dynamic_listitem,list);
        dynamic_list.setAdapter(adapter);
        listener = new MyClickListener();
        registerListener();


        dynamic_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                iv_comment = view.findViewById(R.id.iv_comment);
                Toast.makeText( getContext(), "xxx", Toast.LENGTH_SHORT ).show();
                iv_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText( getContext(), "评论", Toast.LENGTH_SHORT ).show();
                        showPopupWindow();
                    }
                });
            }
        });
        //显示后台服务器存储的所有发布的动态
        selectAllDynamic();
    }
}
