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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    private SmartRefreshLayout smartRefreshLayout;
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
                        content.setDevice(json.get("device").toString());
                        content.setContent(json.get("pushContent").toString());
                        if(json.has("pushImage") && json.getString("pushImage")!=null && !json.getString("pushImage").equals("")){
                            String [] imgs = json.getString("pushImage").split(";");
                            content.setImages(json.getString("pushImage"));
                            content.setImg(imgs[0]);
                            if (imgs.length == 2) {
                                content.setImg2(imgs[1]);
                            }
                        }
//                        content.setDevice(Build.MODEL);
                        content.setForward_Num(json.getInt("forwardNum"));
                        content.setZan_num(json.getInt("zanNum"));
                        content.setZanFocus(false);
                        int forwardId = json.getInt("forwardId");
                        content.setForwardId(forwardId);
                        content.setType(json.getInt("dtype"));
                        Log.e("第"+i+"动态的forwardId和dtype",forwardId+":"+json.getString("dtype"));
                        if (forwardId>0){
                            JSONObject jsonForwardContent = json.getJSONObject("forwardContent");
                            ForwardContent forwardContent = new ForwardContent();
                            forwardContent.setDid(jsonForwardContent.getInt("did"));
                            forwardContent.setDuserNickname(jsonForwardContent.getString("duserNickname"));
                            forwardContent.setDuserEmail(jsonForwardContent.getString("duserEmail"));
                            String dpushTime = jsonForwardContent.get("dpushTime").toString();
                            Date d1 = new Date(dpushTime);
                            SimpleDateFormat sdf1  = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
                            forwardContent.setDpushTime(sdf1.format(d1));
                            forwardContent.setDpushContent(jsonForwardContent.getString("dpushContent"));
                            if(jsonForwardContent.getString("dpushImage")!=null && !jsonForwardContent.getString("dpushImage").equals("")){
                                String [] images = jsonForwardContent.getString("dpushImage").split(";");
                                forwardContent.setDpushImage1(images[0]);
                                if (images.length == 2) {
                                    forwardContent.setDpushImage2(images[1]);
                                }
                            }
                            Log.e("forward",forwardContent.toString());
                            content.setForwardContent(forwardContent);
                            Log.e("第"+i+"条动态下的转发",content.getForwardContent().toString());
                        }
                        JSONArray jsonArrayComment = json.getJSONArray("comments");
//                        Log.e("comments",jsonArrayComment.toString());
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
                            commentDetailBean.setZanFocus(false);
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
                    }
                    Collections.reverse(list);
                    Log.e("反转后的list",list.toString());
                    peopleAdapter.notifyDataSetChanged();
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
//        content.setType(0);
//        list.add(content);
//        Log.e("content:",content.toString());
//        DynamicContent content1 = new DynamicContent();
//        UserContent userContent1 = new UserContent();
//        userContent1.setUserNickname("啦啦啦");
//        content1.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
//        content1.setUserContent(userContent);
//        content1.setContent("今日跑步分享");
//        content1.setDevice(Build.MODEL);
//        ForwardContent forwardContent = new ForwardContent();
//        forwardContent.setDpushContent("dmskc");
//        forwardContent.setDuserNickname("aaaa");
//        content1.setForwardContent(forwardContent);
//        content1.setType(1);
//        list.add(content1);
//
//        DynamicContent content2 = new DynamicContent();
//        UserContent userContent2 = new UserContent();
//        userContent2.setUserNickname("啦啦啦");
//        content2.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
//        content2.setUserContent(userContent);
//        content2.setContent("今日跑步分享");
//        content2.setDevice(Build.MODEL);
//        content2.setType(2);
//        list.add(content2);DynamicContent content3 = new DynamicContent();
//        UserContent userContent3 = new UserContent();
//        userContent3.setUserNickname("啦啦啦");
//        content3.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
//        content3.setUserContent(userContent3);
//        content3.setContent("今日跑步分享");
//        content3.setDevice(Build.MODEL);
//        content3.setType(3);
//        ForwardContent forwardContent1 = new ForwardContent();
//        forwardContent1.setDpushContent("dmskc");
//        forwardContent1.setDuserNickname("aaaa");
//        content3.setForwardContent(forwardContent1);
//        list.add(content3);

        peopleAdapter = new PeopleAdapter(getContext(),list);
        dynamic_list.setAdapter(peopleAdapter);
        peopleAdapter.setBtnOnclick(new PeopleAdapter.btnOnclick() {
            @Override
            public void click(View view, int index) {
                switch (view.getId()){
                    case R.id.ll_toComment:
                        tv_commentNum = view.findViewById( R.id.tv_commentNum );
//                        if (loginOrNot()){
                            Intent intent = new Intent(getContext(), DynamicDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("dynamic",list.get(index));
                            intent.putExtras(bundle);
                            startActivity(intent);
//                        } else {
//                            Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
//                        }

                        break;
                }
            }
        });
        //注册刷新监听器
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                selectAllDynamic();
                smartRefreshLayout.finishRefresh();
            }
        });
        //注册加载更多监听器
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                selectAllDynamic();
                smartRefreshLayout.finishLoadMore();
            }
        });
        return view;
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
        BezierRadarHeader header = new BezierRadarHeader(getContext());
        smartRefreshLayout.setRefreshHeader(header);
        FalsifyFooter footer = new FalsifyFooter(getContext());
        smartRefreshLayout.setRefreshFooter(footer);
        iv_add_Message.setOnClickListener(listener);
    }

    private void getViews(View view) {
        smartRefreshLayout = view.findViewById(R.id.srl);
        dynamic_list = view.findViewById(R.id.dynamic_list);
        iv_add_Message = view.findViewById(R.id.iv_add_Message);
    }

    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_add_Message:
                    /*if (loginOrNot()){
                        Intent intent = new Intent(getContext(), ReleaseDynamic.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                    }*/
                    Intent intent = new Intent(getContext(), ReleaseDynamic.class);
                    startActivity(intent);

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

//    @Override
//    public void onPause() {
//        super.onPause();
//        isPause = true;//记录页面已经被暂停
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if(isPause){//判断是否暂停
//            isPause = false;
//            selectAllDynamic();
//            peopleAdapter.notifyDataSetChanged();
//
//        }
//    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        selectAllDynamic();
//    }

  /*  @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        selectAllDynamic();
    }*/
}