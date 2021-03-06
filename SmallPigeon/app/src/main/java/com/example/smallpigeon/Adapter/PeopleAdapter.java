package com.example.smallpigeon.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.ForwardContent;
import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PeopleAdapter extends BaseAdapter  implements View.OnClickListener{

    private Context context;
    private int itemLayoutID;
    private List<DynamicContent> list = new ArrayList<>();
//    private ImageView ivLike;
//    private TextView tvLikeNum;
    private boolean judgeZan = false;
    private String userId;
    private static final int VIEWTYPFIRST = 1;
    private static final int VIEWTYPSECOND = 2;
    private static final int VIEWTYPTHIRD = 3;
    private static final int VIEWTYPFOUR = 4;

    private PopupWindow popupWindow;
    private View popupView = null;
    private EditText et_discuss;
    private String nInputContentText;
    private TextView btn_submit;
    private RelativeLayout rl_input_container;
    private InputMethodManager mInputManager;
    private ViewHolder holder = null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj+"";
            switch (msg.what){
                case 0:
                    if(result.equals("true")){
                        Toast toast=Toast.makeText(context,"点赞成功",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }else{
                        Toast toast=Toast.makeText(context,"点赞失败",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    break;
                case 1:
                    if(result.equals("true")){
                        Toast toast=Toast.makeText(context,"取消点赞成功",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }else{
                        Toast toast=Toast.makeText(context,"取消点赞失败",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    break;
            }
        }
    };

    public PeopleAdapter(Context context,List<DynamicContent> list) {
        this.context = context;
        this.list = list;
    }

    private btnOnclick btnOnclick;

    public btnOnclick getBtnOnclick() {
        return btnOnclick;
    }

    public void setBtnOnclick(btnOnclick btnOnclick) {
        this.btnOnclick = btnOnclick;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == 0){
            return VIEWTYPFIRST;
        } else if(list.get(position).getType() == 1) {
            return VIEWTYPSECOND;
        }else if(list.get(position).getType() == 2) {
            return VIEWTYPTHIRD;
        }else if(list.get(position).getType() == 3) {
            return VIEWTYPFOUR;
        }else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        Log.e("type:",type+"");
        DynamicContent dynamicContent = list.get(position);
        if (convertView == null){
            holder = new ViewHolder();
            switch (type){
                case VIEWTYPFIRST:
                    LayoutInflater inflater = LayoutInflater.from(context);
                    convertView = inflater.inflate(R.layout.people_dynamic_listitem,null);
                    holder.iv_icon = convertView.findViewById(R.id.iv_icon);
                    holder.tv_nickName = convertView.findViewById(R.id.tv_nickName);
                    holder.tv_date = convertView.findViewById(R.id.tv_date);
                    holder.device = convertView.findViewById(R.id.device);
                    holder.dynamic_item_txt = convertView.findViewById(R.id.dynamic_item_txt);
                    holder.dynamic_item_img = convertView.findViewById( R.id.dynamic_item_img );
                    holder.dynamic_item_img2 = convertView.findViewById( R.id.dynamic_item_img2 );
                    holder.ll_forward = convertView.findViewById( R.id.ll_forward );
                    holder.tv_forwardNum = convertView.findViewById(R.id.tv_forwardNum);
                    holder.ll_toComment = convertView.findViewById( R.id.ll_toComment );
                    holder.tv_commentNum = convertView.findViewById(R.id.tv_commentNum);
                    holder.ll_like = convertView.findViewById( R.id.ll_like );
                    holder.iv_like = convertView.findViewById( R.id.iv_like );
                    holder.tv_likeNum = convertView.findViewById( R.id.tv_likeNum );
                    break;
                case VIEWTYPSECOND:
                    convertView = LayoutInflater.from(context).inflate(R.layout.people_dynamic_listitem2,null);
                    holder.iv_icon = convertView.findViewById(R.id.iv_icon);
                    holder.tv_nickName = convertView.findViewById(R.id.tv_nickName);
                    holder.tv_date = convertView.findViewById(R.id.tv_date);
                    holder.device = convertView.findViewById(R.id.device);
                    holder.dynamic_item_txt = convertView.findViewById(R.id.dynamic_item_txt);
                    holder.dynamic_item_img = convertView.findViewById( R.id.dynamic_item_img );
                    holder.dynamic_item_img2 = convertView.findViewById( R.id.dynamic_item_img2 );
                    holder.ll_forward = convertView.findViewById( R.id.ll_forward );
                    holder.tv_forwardNum = convertView.findViewById(R.id.tv_forwardNum);
                    holder.ll_toComment = convertView.findViewById( R.id.ll_toComment );
                    holder.tv_commentNum = convertView.findViewById(R.id.tv_commentNum);
                    holder.ll_like = convertView.findViewById( R.id.ll_like );
                    holder.iv_like = convertView.findViewById( R.id.iv_like );
                    holder.tv_likeNum = convertView.findViewById( R.id.tv_likeNum );
                    holder.tv_user_txt = convertView.findViewById(R.id.tv_user_txt);
                    holder.tv_user = convertView.findViewById(R.id.tv_user);
                    break;
                case VIEWTYPTHIRD:
                    convertView = LayoutInflater.from(context).inflate(R.layout.people_dynamic_listitem3,null);
                    holder.iv_icon = convertView.findViewById(R.id.iv_icon);
                    holder.tv_nickName = convertView.findViewById(R.id.tv_nickName);
                    holder.tv_date = convertView.findViewById(R.id.tv_date);
                    holder.device = convertView.findViewById(R.id.device);
                    holder.dynamic_item_txt = convertView.findViewById(R.id.dynamic_item_txt);
                    holder.ll_forward = convertView.findViewById( R.id.ll_forward );
                    holder.tv_forwardNum = convertView.findViewById(R.id.tv_forwardNum);
                    holder.ll_toComment = convertView.findViewById( R.id.ll_toComment );
                    holder.tv_commentNum = convertView.findViewById(R.id.tv_commentNum);
                    holder.ll_like = convertView.findViewById( R.id.ll_like );
                    holder.iv_like = convertView.findViewById( R.id.iv_like );
                    holder.tv_likeNum = convertView.findViewById( R.id.tv_likeNum );
                    break;
                case VIEWTYPFOUR:
                    convertView = LayoutInflater.from(context).inflate(R.layout.people_dynamic_listitem4,null);
                    holder.iv_icon = convertView.findViewById(R.id.iv_icon);
                    holder.tv_nickName = convertView.findViewById(R.id.tv_nickName);
                    holder.tv_date = convertView.findViewById(R.id.tv_date);
                    holder.device = convertView.findViewById(R.id.device);
                    holder.dynamic_item_txt = convertView.findViewById(R.id.dynamic_item_txt);
                    holder.ll_forward = convertView.findViewById( R.id.ll_forward );
                    holder.tv_forwardNum = convertView.findViewById(R.id.tv_forwardNum);
                    holder.ll_toComment = convertView.findViewById( R.id.ll_toComment );
                    holder.tv_commentNum = convertView.findViewById(R.id.tv_commentNum);
                    holder.ll_like = convertView.findViewById( R.id.ll_like );
                    holder.iv_like = convertView.findViewById( R.id.iv_like );
                    holder.tv_likeNum = convertView.findViewById( R.id.tv_likeNum );
                    holder.tv_user_txt = convertView.findViewById(R.id.tv_user_txt);
                    holder.tv_user = convertView.findViewById(R.id.tv_user);
                    break;
            }
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.e("content：",dynamicContent.toString());
        switch (type){
            case VIEWTYPFIRST:
                holder.tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
                holder.tv_date.setText(dynamicContent.getDate());
                holder.device.setText(dynamicContent.getDevice());
                holder.dynamic_item_txt.setText(dynamicContent.getContent());
                holder.tv_commentNum.setText(dynamicContent.getComment_Num()+"");
//                holder.tv_likeNum.setText(dynamicContent.getZan_num()+"");
                holder.tv_forwardNum.setText(dynamicContent.getForward_Num()+"");
                holder.ll_toComment.setOnClickListener(this);
                holder.ll_forward.setOnClickListener(this);
                holder.ll_toComment.setTag(position);
                holder.ll_forward.setTag(position);
                //缓存发布的动态图片
                if(!"".equals(dynamicContent.getImg())) {
                    showImges(dynamicContent.getImg(), holder.dynamic_item_img);
                }
                if(!"".equals(dynamicContent.getImg2())) {
                    showImges(dynamicContent.getImg2(), holder.dynamic_item_img2);
                }
                break;
            case VIEWTYPSECOND:
                holder.tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
                holder.tv_date.setText(dynamicContent.getDate());
                holder.device.setText(dynamicContent.getDevice());
                holder.dynamic_item_txt.setText(dynamicContent.getContent());
                holder.tv_commentNum.setText(dynamicContent.getComment_Num()+"");
                holder.tv_user.setText(dynamicContent.getForwardContent().getDuserNickname()+"：");
                holder.tv_user_txt.setText(dynamicContent.getForwardContent().getDpushContent());
                holder.ll_toComment.setOnClickListener(this);
                holder.ll_forward.setOnClickListener(this);
//                holder.ll_forward.setVisibility(View.GONE);//转发的状态隐藏转发按钮
                holder.ll_toComment.setTag(position);
                holder.ll_forward.setTag(position);
                //缓存发布的动态图片 来自被转发动态
                if(!"".equals(dynamicContent.getForwardContent().getDpushImage1())) {
                    showImges(dynamicContent.getForwardContent().getDpushImage1(), holder.dynamic_item_img);
                }
                if(!"".equals(dynamicContent.getForwardContent().getDpushImage2())) {
                    showImges(dynamicContent.getForwardContent().getDpushImage2(), holder.dynamic_item_img2);
                }
                break;
            case VIEWTYPTHIRD:
                holder.tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
                holder.tv_date.setText(dynamicContent.getDate());
                holder.device.setText(dynamicContent.getDevice());
                holder.dynamic_item_txt.setText(dynamicContent.getContent());
                holder.tv_commentNum.setText(dynamicContent.getComment_Num()+"");
                holder.tv_forwardNum.setText(dynamicContent.getForward_Num()+"");
                holder.ll_toComment.setOnClickListener(this);
                holder.ll_forward.setOnClickListener(this);
                holder.ll_toComment.setTag(position);
                holder.ll_forward.setTag(position);
                break;
            case VIEWTYPFOUR:
                holder.tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
                holder.tv_date.setText(dynamicContent.getDate());
                holder.device.setText(dynamicContent.getDevice());
                holder.dynamic_item_txt.setText(dynamicContent.getContent());
                holder.tv_commentNum.setText(dynamicContent.getComment_Num()+"");
                holder.tv_user.setText(dynamicContent.getForwardContent().getDuserNickname()+"：");
                holder.tv_user_txt.setText(dynamicContent.getForwardContent().getDpushContent());
                holder.ll_toComment.setOnClickListener(this);
                holder.ll_forward.setOnClickListener(this);
//                holder.ll_forward.setVisibility(View.GONE);//转发状态隐藏转发
                holder.ll_toComment.setTag(position);
                holder.ll_forward.setTag(position);
                break;
        }
        //缓存头像
        showImage(dynamicContent.getUserContent().getUserImage(),holder.iv_icon);
        //得到点赞用户id
        SharedPreferences pre = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = pre.getString("user_id","");

        // 取出bean中当记录状态是否为true，是的话则给img设置focus点赞图片
        if (dynamicContent.isZanFocus()) {
            holder.iv_like.setImageResource(R.drawable.heart);
        } else {
            holder.iv_like.setImageResource(R.drawable.good);
        }
        holder.tv_likeNum.setText(dynamicContent.getZan_num()+"");
        holder.ll_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginOrNot()) {
                    //获取上次是否已经被点击
                    Log.e("第"+position+"条记录上次点击状态",dynamicContent.isZanFocus()+"");
                    boolean flag = dynamicContent.isZanFocus();
                    //判断当前点赞状态
                    if (flag) {
                        int zanNumAfter = dynamicContent.getZan_num()-1;
                        dynamicContent.setZan_num(zanNumAfter);
                        decZanNum(dynamicContent.getDynamicId(), Integer.parseInt(userId),zanNumAfter);
                    }else {
                        int zanNumAfter = dynamicContent.getZan_num()+1;
                        dynamicContent.setZan_num(zanNumAfter);
                        addZanNum(dynamicContent.getDynamicId(), Integer.parseInt(userId),zanNumAfter);
                    }
                    //反向存储记录，实现取消点赞功能
                    dynamicContent.setZanFocus(!flag);
                    AnimationTools.scale(holder.iv_like);
                    notifyDataSetChanged();
                } else {
                    //没有登录注册 不能点赞  请先登录
                    Toast toast=Toast.makeText(context, "请先登录哦！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        //转发
        holder.ll_forward.setOnClickListener( new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if (loginOrNot()){
                    if (popupView == null){
                        //加载评论框的资源文件
                        popupView = LayoutInflater.from(context).inflate(R.layout.popup, null);
                    }

                    et_discuss = (EditText) popupView.findViewById(R.id.et_discuss);
                    btn_submit = (Button) popupView.findViewById(R.id.btn_confirm);
                    rl_input_container = (RelativeLayout)popupView.findViewById(R.id.rl_input_container);
                    et_discuss.setHint( "  转发理由……" );

                    //利用Timer这个Api设置延迟显示软键盘，这里时间为200毫秒
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        public void run() {
                            mInputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
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
                    popupWindow.setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    //设置popupwindow的显示位置，这里应该是显示在底部，即Bottom
                    popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
                    popupWindow.update();
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
                            //转发理由
                            nInputContentText = et_discuss.getText().toString().trim();
                            if (nInputContentText == null || "".equals(nInputContentText)) {
                                Toast toast= Toast.makeText(context,"内容不能为空！",Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();
                            }else {
                    Timestamp pushTime = new Timestamp(new Date().getTime());
                    int forwardId = list.get(position).getDynamicId();
                    if (list.get(position).getImages()==null || list.get(position).getImages().equals("") || list.get(position).getImages().equals("null")){
                        addForwardDynamic(pushTime, nInputContentText ,forwardId,3, Build.MODEL);
                    }else {
                        addForwardDynamic(pushTime, nInputContentText, forwardId,1,Build.MODEL);
                    }
                    mInputManager.hideSoftInputFromWindow(et_discuss.getWindowToken(),0);
                    popupWindow.dismiss();
                    et_discuss.setText( null );
                    int forwardNumBefore = list.get(position).getForward_Num();
                    int forwardNumAfter = forwardNumBefore+1;
                    Log.e("forwardNumAfter",forwardNumAfter+"");
                    list.get(position).setForward_Num(forwardNumAfter);
                    holder.tv_forwardNum.setText(list.get(position).getForward_Num()+"");
                    //转发成功后改变转发数量
                    changeForwardNum(list.get(position).getDynamicId(),forwardNumAfter); } }});
                    notifyDataSetChanged();
                } else {
                    Toast toast=Toast.makeText(context, "请先登录哦！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        } );

        holder.ll_toComment.setOnClickListener(this);
//        holder.ll_forward.setOnClickListener(this);
        holder.ll_toComment.setTag(position);
        holder.ll_forward.setTag(position);
        notifyDataSetChanged();
        return convertView;
    }

    private void changeForwardNum(int dynamicId,int forwardNum) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","changeForwardNum","dynamicId="+dynamicId+"&&forwardNum="+forwardNum);
                Message message = new Message();
                message.obj = result;
                message.what=2;
                handler.sendMessage(message);
            }
        }.start();
    }

    //点赞
    private void addZanNum(int dynamicId, int userId,int zanNumAfter) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","addZanNum","dynamicId="+dynamicId
                        +"&&userId="+userId+"&&zanNumAfter="+zanNumAfter);
                Message message = new Message();
                message.obj = result;
                message.what=0;
                handler.sendMessage(message);
            }
        }.start();
    }

    //取消点赞
    private void decZanNum(int dynamicId, int userId,int zanNumAfter) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","decZanNum","dynamicId="+dynamicId
                        +"&&userId="+userId+"&&zanNumAfter="+zanNumAfter);
                Message message = new Message();
                message.obj = result;
                message.what=1;
                handler.sendMessage(message);
            }
        }.start();
    }

    //缓存动态图片
    private void showImges(String imgName,ImageView imageView) {
        String url = "http://"+this.context.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/dynamic/"+imgName;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this.context).load(url).into(imageView);
    }

    @Override
    public void onClick(View view) {
        btnOnclick.click(view,(int)view.getTag());
    }

    static class ViewHolder{
        ImageView iv_icon;//用户头像
        TextView tv_nickName;//用户昵称
        TextView tv_date;//发表时间
        TextView device;//设备名称
        TextView dynamic_item_txt;//发表内容
        ImageView dynamic_item_img;//发表内容配图
        ImageView dynamic_item_img2;//发表内容配图2
        LinearLayout ll_forward;//转发
        TextView tv_forwardNum;//转发数
        LinearLayout ll_toComment;//评论
        TextView tv_commentNum;//评论数
        LinearLayout ll_like;//赞
        ImageView iv_like;//点赞图标
        TextView tv_likeNum;//点赞数
        TextView tv_user;
        TextView tv_user_txt;
    }


    public interface btnOnclick{
        public void click(View view,int index);
    }

    //缓存头像图片
    private void showImage(String imgName,ImageView imageView) {
        String url = "http://"+this.context.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/avatar/"+imgName+".jpg";
        Glide.with(this.context).load(url).into(imageView);
    }
    private boolean loginOrNot(){
        SharedPreferences pre = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userEmail = pre.getString("user_email","");
        if(userEmail.equals("")||userEmail==null){
            return false;
        }else{
            return true;
        }
    }

    //插入转发信息  当前转发者id  转发时间 转发内容  转发动态的id  转发状态 1 为转发 0为不转发
    private void addForwardDynamic(Timestamp pushTime, String pushContent,int forwardId,int type,String device){
        Handler handler1 = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String result = msg.obj+"";
                if(result.equals("true")){

                   Toast toast= Toast.makeText(context,"转发成功",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    Toast toast=Toast.makeText(context,"转发失败",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        };
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","addForwardDynamic",
                        "userId="+userId +"&&pushTime="+pushTime+"&&pushContent="+pushContent+"&&forwardId="+forwardId+"&&type="+type+"&&device="+device);
                Message message = new Message();
                message.obj = result;
                handler1.sendMessage(message);
            }
        }.start();
    }
    static class AnimationTools {
        public static void scale(View v) {
            ScaleAnimation anim = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
            anim.setDuration(100);
            v.startAnimation(anim);

        }
    }
}