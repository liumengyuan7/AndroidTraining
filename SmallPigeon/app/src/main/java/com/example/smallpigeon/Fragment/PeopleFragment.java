package com.example.smallpigeon.Fragment;

import android.annotation.SuppressLint;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidubce.model.User;
import com.example.smallpigeon.Adapter.PeopleAdapter;
import com.example.smallpigeon.Community.Comment.DynamicDetailActivity;
import com.example.smallpigeon.Community.ReleaseDynamic.ReleaseDynamic;
import com.example.smallpigeon.Entity.CommentDetailBean;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.ForwardContent;
import com.example.smallpigeon.Entity.ReplyDetailBean;
import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.MainActivity;
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

    private TextView tv_forwardNum;
    private TextView tv_commentNum;

    private PopupWindow popupWindow;
    private View popupView = null;
    private EditText et_discuss;
    private String nInputContentText;
    private TextView btn_submit;
    private RelativeLayout rl_input_container;
    private InputMethodManager mInputManager;
    private boolean isPause = false;

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
                        Log.e("第"+i+"条动态",json.toString());
                        DynamicContent content = new DynamicContent();
                        content.setDynamicId(json.getInt("id"));
                        UserContent userContent = new UserContent();
                        userContent.setUserNickname(json.get("nickName").toString());
                        userContent.setUserImage(json.getString("userEmail"));
                        String time = json.get("pushTime").toString();
                        Date d = new Date(time);
                        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
                        content.setDate(sdf.format(d));//时间转换
                        content.setUserContent(userContent);
                        content.setDevice(Build.MODEL);
                        content.setContent(json.get("pushContent").toString());
                        String [] imgs = json.getString("pushImage").split(";");
                        content.setImg(imgs[0]);
                        if(imgs.length==2) {
                            content.setImg2(imgs[1]);
                        }
                        content.setDevice(Build.MODEL);
                        content.setZan_num(json.getInt("zanNum"));
                        JSONArray jsonArrayComment = json.getJSONArray("comments");
                        Log.e("comments",jsonArrayComment.toString());
                        List<CommentDetailBean> commentDetailBeans = new ArrayList<>();
                        for (int j=0;j<jsonArrayComment.length();j++){
                            JSONObject jsonComment = jsonArrayComment.getJSONObject(j);
                            Log.e("该动态下第"+j+"条评论",jsonComment.toString());
                            String nickName = jsonComment.getString("commentFromNickname");
                            String userLogo = jsonComment.getString("commentFromEmail");
                            String cContent = jsonComment.getString("commentFromContent");
                            String createTime = jsonComment.getString("commentFromTime");
                            int commentId = jsonComment.getInt("id");
                            int commentFromId = jsonComment.getInt("commenmtFromId");
                            int dynamicId = jsonComment.getInt("dynamicId");
                            int commentZanNum = jsonComment.getInt("commentZanNum");
                            String createT = createTime.substring(0,19);
                            CommentDetailBean commentDetailBean = new CommentDetailBean(nickName,cContent,createTime);
                            commentDetailBean.setId(commentId);
                            commentDetailBean.setCommentFromId(commentFromId);
                            commentDetailBean.setDynamicId(dynamicId);
                            commentDetailBean.setCreateDate(createT);
                            commentDetailBean.setComomentZanNum(commentZanNum);
                            commentDetailBean.setUserLogo(userLogo);
                            JSONArray jsonArrayCommentReply = jsonComment.getJSONArray("replies");
                            List<ReplyDetailBean> replyDetailBeans = new ArrayList<>();
                            for (int k=0;k<jsonArrayCommentReply.length();k++) {
                                JSONObject jsonCommentReply = jsonArrayCommentReply.getJSONObject(k);
                                Log.e("该评论下的回复",jsonArrayCommentReply.toString());
                                ReplyDetailBean replyDetailBean = new ReplyDetailBean(jsonCommentReply.getString("fNickname"),jsonCommentReply.getString("replyContent"));
                                replyDetailBean.setCreateDate(jsonCommentReply.getString("replyTime").substring(0,19));
                                replyDetailBean.setCommentId(jsonCommentReply.getString("commentId"));
                                replyDetailBeans.add(replyDetailBean);
                            }
                            commentDetailBean.setReplyList(replyDetailBeans);
                            commentDetailBeans.add(commentDetailBean);
                        }
                        content.setComment_Num(jsonArrayComment.length());
                        content.setCommentDetailBeans(commentDetailBeans);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people,container,false);
        getViews(view);
        listener = new MyClickListener();
        registerListener();

        //显示后台服务器存储的所有发布的动态
        selectAllDynamic();

        //前端测试用
//        DynamicContent content = new DynamicContent();
//        UserContent userContent = new UserContent();
//        userContent.setUserNickname("啦啦啦");
//        content.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
//        content.setUserContent(userContent);
//        content.setContent("今日跑步分享");
//        content.setDevice(Build.MODEL);
//        list.add(content);

        peopleAdapter = new PeopleAdapter(getContext(),list);
        dynamic_list.setAdapter(peopleAdapter);
        peopleAdapter.setBtnOnclick(new PeopleAdapter.btnOnclick() {
            @Override
            public void click(View view, int index) {
                switch (view.getId()){
                    case R.id.ll_toComment:
                        tv_commentNum = view.findViewById( R.id.tv_commentNum );
                        if (loginOrNot()){
                            Intent intent = new Intent(getContext(), DynamicDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("dynamic",list.get(index));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                        }
                        break;
//                    case R.id.ll_forward:
//                        tv_forwardNum = view.findViewById( R.id.tv_forwardNum );
//                        if (loginOrNot()){
//                            showPopupWindow(index,"forward");
//                        } else {
//                            Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
//                        }
//                        break;
                }
            }
        });
        return view;
    }

//    @SuppressLint("WrongConstant")
//    private void showPopupWindow(int index, String type) {
//        if (popupView == null){
//            //加载评论框的资源文件
//            popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup, null);
//        }
//
//        et_discuss = (EditText) popupView.findViewById(R.id.et_discuss);
//        btn_submit = (Button) popupView.findViewById(R.id.btn_confirm);
//        rl_input_container = (RelativeLayout)popupView.findViewById(R.id.rl_input_container);
//        et_discuss.setHint( "  转发理由……" );
//
//        //利用Timer这个Api设置延迟显示软键盘，这里时间为200毫秒
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                mInputManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                mInputManager.showSoftInput(et_discuss, 0);
//                mInputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }, 200);
//
//        if (popupWindow == null){
//            popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT, false);
//        }
//        //popupWindow的常规设置，设置点击外部事件，背景色
//        popupWindow.setTouchable(true);
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
//        et_discuss.setFocusable(true);
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
//                    popupWindow.dismiss();
//                return false;
//            }
//        });
//        // 设置弹出窗体需要软键盘，放在setSoftInputMode之前
//        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//        // 再设置模式，和Activity的一样，覆盖，调整大小。
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        //设置popupwindow的显示位置，这里应该是显示在底部，即Bottom
//        popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
//        popupWindow.update();
//
//        //设置监听
////        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
////            // 在dismiss中恢复透明度
////            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
////            public void onDismiss() {
////                mInputManager.hideSoftInputFromWindow(et_discuss.getWindowToken(), 0); //强制隐藏键盘
////            }
////        });
//        //外部点击事件
//        rl_input_container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mInputManager.hideSoftInputFromWindow(et_discuss.getWindowToken(), 0); //强制隐藏键盘
//                popupWindow.dismiss();
//            }
//        });
//        //评论框内的发送按钮设置点击事件
//        btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //转发理由
//                nInputContentText = et_discuss.getText().toString().trim();
//                if (nInputContentText == null || "".equals(nInputContentText)) {
//                    Toast.makeText(getContext(),"内容不能为空！",Toast.LENGTH_SHORT).show();
//                }else {
//                    //TODO：插入转发信息
//                    ForwardContent forwardContent = new ForwardContent();
//                    //TODO:得到当前转发动态的用户信息
//                    UserContent userContent = new UserContent();
//                    DynamicContent dynamicContent = new DynamicContent();
//                    dynamicContent.setContent(nInputContentText);
//                    dynamicContent.setType(1);//type为1代表转发内容，type为0表示不是转发内容
//                    forwardContent.setDynamicContent(dynamicContent);
//                    forwardContent.setUserContent(userContent);
//
//                    mInputManager.hideSoftInputFromWindow(et_discuss.getWindowToken(),0);
//                    popupWindow.dismiss();
//                    Toast.makeText(getContext(),"发送成功",Toast.LENGTH_SHORT).show();
//                    et_discuss.setText( null );
//                    //TODO：从数据库获取数据并更改转发数
//                    tv_forwardNum.setText( "9" );
//                    //TODO：发送成功，与后台交互，保存到数据库
//
//                }
//            }
//        });
//    }

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
    public void onPause() {
        super.onPause();
        isPause = true;//记录页面已经被暂停
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isPause){//判断是否暂停
            isPause = false;
            selectAllDynamic();
            peopleAdapter.notifyDataSetChanged();

        }
    }
}