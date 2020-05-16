package com.example.smallpigeon.My.MyCommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smallpigeon.Adapter.MyDynamicAdapter;
import com.example.smallpigeon.Entity.CommentDetailBean;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.ForwardContent;
import com.example.smallpigeon.Entity.ReplyDetailBean;
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

public class MyCommunity extends AppCompatActivity {

    private ImageView iv_back;
    private ListView my_dynamic_list;

    private CustomClickListener listener;

    private MyDynamicAdapter myDynamicAdapter;
    private List<DynamicContent> list = new ArrayList<>();
    private boolean isPause = false;
    private String userId;

    private Handler handler = new Handler() {
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
                        if(json.has("pushImage") && json.getString("pushImage")!=null && !json.getString("pushImage").equals("")){
                            String [] imgs = json.getString("pushImage").split(";");
                            content.setImg(imgs[0]);
                            if (imgs.length == 2) {
                                content.setImg2(imgs[1]);
                            }
                        }
                        content.setDevice(Build.MODEL);
                        content.setForward_Num(json.getInt("forwardNum"));
                        content.setZan_num(json.getInt("zanNum"));
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
                        Log.e("list",list.toString());
                        list.add(content);
                        myDynamicAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast toastTip = Toast.makeText(getApplicationContext(),"获取动态失败！请检查网络！",Toast.LENGTH_LONG);
                toastTip.setGravity(Gravity.CENTER, 0, 0);
                toastTip.show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_my_community );

        getViews();
        listener = new CustomClickListener();
        registerListener();
        setStatusBar();

        SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = pre.getString("user_id","");
        Log.e("userId",userId);
        //todo:显示后台服务器存储的当前用户所有发布的动态
        selectAllDynamic(userId);
        //前端测试用
//        DynamicContent content = new DynamicContent();
//        UserContent userContent = new UserContent();
//        userContent.setUserNickname("啦啦啦");
//        content.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
//        content.setUserContent(userContent);
//        content.setContent("今日跑步分享");
//        content.setDevice(Build.MODEL);
//        list.add(content);

        myDynamicAdapter = new MyDynamicAdapter( MyCommunity.this, R.layout.people_dynamic_listitem, list );
        my_dynamic_list.setAdapter(myDynamicAdapter);

        my_dynamic_list.setAdapter(myDynamicAdapter);
        //item点击事件：查看详情
        my_dynamic_list.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MyCommunityDetails.class);
                //TODO：传入被点击动态的id
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("dynamic", position);
//                intent.putExtras(bundle);
//                bundle.putSerializable( "dynamic", content );
//                intent.putExtras( bundle );
                startActivity(intent);
            }
        } );
    }

    class CustomClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_back:
                    finish();
                    break;

            }
        }
    }

    private void registerListener() {
        iv_back.setOnClickListener( listener );
    }

    private void getViews() {
        iv_back = findViewById( R.id.iv_back );
        my_dynamic_list =findViewById( R.id.my_dynamic_list );
    }

    //设置手机的状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    //查出所有动态
    private void selectAllDynamic(String userId) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","getAllDynamicAndCommentByUerId","userId="+userId);
                Message message = new Message();
                message.obj = result;
                handler.sendMessage(message);
            }
        }.start();
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
            Log.e("userId",userId);
            selectAllDynamic(userId);
            myDynamicAdapter.notifyDataSetChanged();
        }
    }
}