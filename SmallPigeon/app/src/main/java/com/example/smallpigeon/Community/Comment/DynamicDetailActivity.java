package com.example.smallpigeon.Community.Comment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smallpigeon.Entity.CommentBean;
import com.example.smallpigeon.Entity.CommentDetailBean;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.ReplyDetailBean;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DynamicDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_icon;
    private TextView tv_nickName;
    private TextView tv_date;
    private TextView device;
    private TextView dynamic_item_txt;
    private ImageView dynamic_item_img;
    private ImageView dynamic_item_img2;
    private ImageView user_dynamic_item_img;
    private ImageView user_dynamic_item_img2;
    private TextView tv_user;
    private TextView tv_user_txt;
    private LinearLayout ll_forwardtype;
    private LinearLayout ll_type;
    private LinearLayout ll_ftype;
    private ImageView iv_back;
    private ExpandableListView expandableListView;
    private TextView detail_page_do_comment;
    private ImageView iv_collect;
    private boolean isCollect = false;
    private DynamicContent dynamicContent;
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private List<CommentDetailBean> commentsList;
    private BottomSheetDialog dialog;
    private String userNickname;
    private String userLogo;
    private int userId;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj+"";
            switch (msg.what){
                case 0:
                    if(result.equals("true")){
                        Toast toast=Toast.makeText(getApplicationContext(),"收藏成功",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else{
                        Toast toast=Toast.makeText(getApplicationContext(),"收藏失败",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    break;
                case 1:
                    if(result.equals("true")){
                        Toast toast=Toast.makeText(getApplicationContext(),"取消收藏成功",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else{
                        Toast toast=Toast.makeText(getApplicationContext(),"取消收藏失败",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_detail);

        getViews();
//        commentsList = generateTestData();
        SharedPreferences pre = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userNickname = pre.getString("user_nickname","");
        userLogo = pre.getString("user_email","");
        userId = Integer.parseInt(pre.getString("user_id",""));
        Intent intent = getIntent();
        dynamicContent = (DynamicContent) intent.getSerializableExtra("dynamic");
        System.out.println(dynamicContent.toString());
        int type = dynamicContent.getType();
        if (type==0){
            ll_type.setVisibility(View.VISIBLE);
            ll_forwardtype.setVisibility(View.GONE);
            showUserImage(dynamicContent.getUserContent().getUserImage(),iv_icon);
            tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
            tv_date.setText(dynamicContent.getDate());
            device.setText(dynamicContent.getDevice());
            dynamic_item_txt.setText(dynamicContent.getContent());
            //缓存图片
            if(!"".equals(dynamicContent.getImg())) {
                showDynamicImage(dynamicContent.getImg(), dynamic_item_img);
            }
            if(!"".equals(dynamicContent.getImg2())) {
                showDynamicImage(dynamicContent.getImg2(), dynamic_item_img2);
            }
        }else if (type==1){
            ll_type.setVisibility(View.GONE);
            ll_forwardtype.setVisibility(View.VISIBLE);
            showUserImage(dynamicContent.getUserContent().getUserImage(),iv_icon);
            tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
            tv_date.setText(dynamicContent.getDate());
            device.setText(dynamicContent.getDevice());
            dynamic_item_txt.setText(dynamicContent.getContent());
            tv_user.setText(dynamicContent.getUserContent().getUserNickname()+"：");
            tv_user_txt.setText(dynamicContent.getForwardContent().getDpushContent());
            if (!"".equals(dynamicContent.getForwardContent().getDpushImage1())){
                showDynamicImage(dynamicContent.getForwardContent().getDpushImage1(),user_dynamic_item_img);
            }
            if (!"".equals(dynamicContent.getForwardContent().getDpushImage2())){
                showDynamicImage(dynamicContent.getForwardContent().getDpushImage2(),user_dynamic_item_img2);
            }
        }else if (type==2){
            ll_type.setVisibility(View.GONE);
            ll_forwardtype.setVisibility(View.GONE);
            showUserImage(dynamicContent.getUserContent().getUserImage(),iv_icon);
            tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
            tv_date.setText(dynamicContent.getDate());
            device.setText(dynamicContent.getDevice());
            dynamic_item_txt.setText(dynamicContent.getContent());
        }else if (type==3){
            ll_type.setVisibility(View.GONE);
            ll_ftype.setVisibility(View.GONE);
            showUserImage(dynamicContent.getUserContent().getUserImage(),iv_icon);
            tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
            tv_date.setText(dynamicContent.getDate());
            device.setText(dynamicContent.getDevice());
            dynamic_item_txt.setText(dynamicContent.getContent());
            tv_user.setText(dynamicContent.getUserContent().getUserNickname()+"：");
            tv_user_txt.setText(dynamicContent.getForwardContent().getDpushContent());
        }

        commentsList = dynamicContent.getCommentDetailBeans();
        dynamicContent.setCommentDetailBeans(commentsList);
        initExpandableListView(dynamicContent);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getViews() {
        expandableListView = findViewById(R.id.detail_page_lv_comment);
        iv_icon = findViewById(R.id.iv_icon);
        tv_nickName = findViewById(R.id.tv_nickName);
        tv_date = findViewById(R.id.tv_date);
        device = findViewById(R.id.device);
        dynamic_item_txt = findViewById(R.id.dynamic_item_txt);
        dynamic_item_img = findViewById(R.id.dynamic_item_img);
        dynamic_item_img2 = findViewById(R.id.dynamic_item_img2);
        detail_page_do_comment = (TextView) findViewById(R.id.detail_page_do_comment);
        detail_page_do_comment.setOnClickListener(this);
        iv_collect = findViewById(R.id.iv_collect);
        iv_collect.setOnClickListener(this);
        user_dynamic_item_img = findViewById(R.id.user_dynamic_item_img);
        user_dynamic_item_img2 = findViewById(R.id.user_dynamic_item_img2);
        tv_user = findViewById(R.id.tv_user);
        tv_user_txt = findViewById(R.id.tv_user_txt);
        ll_forwardtype = findViewById(R.id.ll_forwardtype);
        ll_type = findViewById(R.id.ll_type);
        iv_back = findViewById(R.id.iv_back);
        ll_ftype = findViewById(R.id.ll_ftype);
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final DynamicContent dynamicContent){
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(DynamicDetailActivity.this, dynamicContent);
        Log.e("评论列表",dynamicContent.getCommentDetailBeans().toString());
        expandableListView.setAdapter(adapter);
        for(int i = 0; i<dynamicContent.getCommentDetailBeans().size(); i++){
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e(TAG, "onGroupClick: 当前的评论id>>>"+dynamicContent.getCommentDetailBeans().get(groupPosition).getCommentFromId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast toast=Toast.makeText(DynamicDetailActivity.this,"点击了回复",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });

    }

    private void showCommentDialog(){
        dialog = new BottomSheetDialog(this,R.style.BottomSheetStyle);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    CommentDetailBean detailBean = new CommentDetailBean();
                    detailBean.setNickName(userNickname);
                    detailBean.setContent(commentContent);
                    detailBean.setCreateDate(new Timestamp(new Date().getTime()).toString());
                    detailBean.setCommentFromId(userId);
                    detailBean.setUserLogo(userLogo);
                    detailBean.setDynamicId(dynamicContent.getDynamicId());
                    adapter.addTheCommentData(detailBean);
//                    Toast.makeText(DynamicDetailActivity.this,"评论成功",Toast.LENGTH_SHORT).show();

                }else {
                    Toast toast=Toast.makeText(DynamicDetailActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    private void showReplyDialog(final int position){
        dialog = new BottomSheetDialog(this,R.style.BottomSheetStyle);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){
                    dialog.dismiss();
                    ReplyDetailBean detailBean = new ReplyDetailBean();
                    detailBean.setNickName(userNickname);
                    detailBean.setContent(replyContent);
                    detailBean.setCreateDate(new Timestamp(new Date().getTime()).toString());
                    detailBean.setCommentId(String.valueOf(commentsList.get(position).getId()));
                    detailBean.setToId(commentsList.get(position).getCommentFromId());
                    detailBean.setFromId(userId);
                    adapter.addTheReplyData(detailBean, position);
                    expandableListView.expandGroup(position);
//                    Toast.makeText(DynamicDetailActivity.this,"回复成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast toast=Toast.makeText(DynamicDetailActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_page_do_comment:
//                Toast.makeText(getApplicationContext(),"aaa",Toast.LENGTH_LONG).show();
                showCommentDialog();
                break;
            case R.id.iv_collect:
//                Toast.makeText(getApplicationContext(),"bbb",Toast.LENGTH_LONG).show();
                if (isCollect){
                    decCollect(dynamicContent.getDynamicId(),userId);
                    isCollect = false;
                    iv_collect.setImageResource(R.drawable.collect_gray);
                }else {
                    addCollect(dynamicContent.getDynamicId(),userId,dynamicContent.getType());
                    isCollect = true;
                    iv_collect.setImageResource(R.drawable.collect_yellow);
                }
                break;
        }
    }
    /*
    * 详情页显示动态图片
    * */
    //缓存动态图片
    private void showDynamicImage(String imgName,ImageView imageView) {
        String url = "http://"+this.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/dynamic/"+imgName;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(url).into(imageView);
    }
    /*
    * 详情页显示动态用户头像
    * */
    //缓存头像图片
    private void showUserImage(String imgName,ImageView imageView) {
        String url = "http://"+this.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/avatar/"+imgName+".jpg";
        Glide.with(this).load(url).into(imageView);
    }

    //点击进行动态收藏
    private void addCollect(int dynamicId, int userId,int type) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","addCollect","dynamicId="+dynamicId
                        +"&&userId="+userId+"&&type="+type);
                Message message = new Message();
                message.obj = result;
                message.what=0;
                handler.sendMessage(message);
            }
        }.start();
    }
    //点击取消动态收藏
    private void decCollect(int dynamicId, int userId) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","decCollect","dynamicId="+dynamicId
                        +"&&userId="+userId);
                Message message = new Message();
                message.obj = result;
                message.what=1;
                handler.sendMessage(message);
            }
        }.start();
    }
}
