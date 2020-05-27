package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smallpigeon.Adapter.CollectAdapter;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.ForwardContent;
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
import java.util.Set;

public class MyCollectActivity extends AppCompatActivity {
    private ImageView mycollect_back;
    private ListView lv_collect;
    private LinearLayout mLlEditBar;
    private LinearLayout ll_cancel;
    private LinearLayout ll_delete;
    private LinearLayout ll_inverse;
    private LinearLayout ll_selectall;
    private List<DynamicContent> list = new ArrayList<>();
    private List<DynamicContent> checkList = new ArrayList<>();
    private SparseBooleanArray stateCheckedMap = new SparseBooleanArray();//用来存放CheckBox的选中状态，true为选中,false为没有选中
    private boolean isSelectedAll = true;//用来控制点击全选，全选和全不选相互切换
    private CollectAdapter adapter;
    private MyClickListener listener;
    private String userId;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String result = msg.obj + "";
                    if(!result.equals("empty")){
                        Log.e("收藏信息",result);
                        list.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0;i<jsonArray.length();i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                Log.e("第"+i+"条动态",json.toString());
                                DynamicContent content = new DynamicContent();
                                //获得动态的id
                                content.setDynamicId(json.getInt("id"));
                                //获得动态发布的用户昵称 头像
                                UserContent userContent = new UserContent();
                                userContent.setUserNickname(json.get("nickName").toString());
                                userContent.setUserImage(json.getString("userEmail"));
                                //获得动态发布时间
                                String time = json.get("pushTime").toString();
                                Date d = new Date(time);
                                SimpleDateFormat sdf  = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
                                content.setDate(sdf.format(d));//时间转换
                                content.setUserContent(userContent);
                                content.setDevice(Build.MODEL);
                                //获得动态的发布内容
                                content.setContent(json.get("pushContent").toString());
                                //获得动态发布的图片信息
                                if(json.has("pushImage") && json.getString("pushImage")!=null && !json.getString("pushImage").equals("")){
                                    String [] imgs = json.getString("pushImage").split(";");
                                    content.setImg(imgs[0]);
                                    if (imgs.length == 2) {
                                        content.setImg2(imgs[1]);
                                    }
                                }
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
                                //获得收藏的该条动态的评论数量
                                int commentNum = json.getInt("commentNum");
                                content.setComment_Num(commentNum);
                                //获得收藏的该条动态的收藏数量
                                int collectNum = json.getInt("collectNum");
                                content.setCollect_Num(collectNum);
                                content.setDevice(Build.MODEL);
                                list.add(content);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast toastTip = Toast.makeText(getApplicationContext(),"你的收藏为空哦！",Toast.LENGTH_LONG);
                        toastTip.setGravity(Gravity.CENTER, 0, 0);
                        toastTip.show();
                    }
                    break;
                case 1:
                    String r = msg.obj + "";
                    Log.e("删除收藏返回的数据",r);
                    if(r.equals("true")){
                        Toast toast=Toast.makeText(getApplicationContext(),"删除收藏成功",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }else{
                        Toast toast=Toast.makeText(getApplicationContext(),"删除收藏失败",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = pre.getString("user_id","");
        Log.e("userId",userId);
        getViews();
        listener = new MyClickListener();
        registerListener();
        setStateCheckedMap(false);

        mycollect_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getAllCollect(userId);
        setStatusBar();//状态栏隐藏

        adapter = new CollectAdapter(list,getApplicationContext(),stateCheckedMap);
        lv_collect.setAdapter(adapter);
        setOnListViewItemClickListener();
        setOnListViewItemLongClickListener();
    }
    //根据用户id得到收藏数据
    private void getAllCollect(String userId) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","getAllCollectByUserId","userId="+userId);
                Message message = new Message();
                message.what = 0;
                message.obj = result;
                handler.sendMessage(message);
            }
        }.start();
    }
    //根据用户id删除收藏数据
    private void deleteCollect(String userId, String dynamicIdList) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","decCollectList","userId="+userId
                        +"&&dynamicIdList="+dynamicIdList);
                Message message = new Message();
                message.what = 1;
                message.obj = result;
                handler.sendMessage(message);
            }
        }.start();
    }
    private void setOnListViewItemClickListener() {
        lv_collect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateCheckBoxStatus(view, position);
            }
        });
    }
    /**
     * 如果返回false那么click仍然会被调用,,先调用Long click，然后调用click。
     * 如果返回true那么click就会被吃掉，click就不会再被调用了
     * 在这里click即setOnItemClickListener
     */
    private void setOnListViewItemLongClickListener() {
        lv_collect.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mLlEditBar.setVisibility(View.VISIBLE);//显示下方布局
                adapter.setShowCheckBox(true);//CheckBox的那个方框显示
                updateCheckBoxStatus(view, position);
                return true;
            }
        });
    }


    private void updateCheckBoxStatus(View view, int position) {
        CollectAdapter.ViewHolder holder = (CollectAdapter.ViewHolder) view.getTag();
        holder.checkBox.toggle();//反转CheckBox的选中状态
        lv_collect.setItemChecked(position, holder.checkBox.isChecked());//长按ListView时选中按的那一项
        stateCheckedMap.put(position, holder.checkBox.isChecked());//存放CheckBox的选中状态
        if (holder.checkBox.isChecked()) {
            checkList.add(list.get(position));//CheckBox选中时，把这一项的数据加到选中数据列表
        } else {
            checkList.remove(list.get(position));//CheckBox未选中时，把这一项的数据从选中数据列表移除
        }
        adapter.notifyDataSetChanged();

    }
    /**
     * 设置所有CheckBox的选中状态
     * */
    private void setStateCheckedMap(boolean isSelectedAll) {
        for (int i = 0; i < list.size(); i++) {
            stateCheckedMap.put(i, isSelectedAll);
            lv_collect.setItemChecked(i, isSelectedAll);
        }
    }

    private void registerListener() {
        ll_selectall.setOnClickListener(listener);
        ll_inverse.setOnClickListener(listener);
        ll_cancel.setOnClickListener(listener);
        ll_delete.setOnClickListener(listener);
    }

    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_cancle:
                    cancel();
                    break;
                case R.id.ll_delete:
                    delete();
                    break;
                case R.id.ll_inverse:
                    inverse();
                    break;
                case R.id.ll_select_all:
                    selectAll();
                    break;
            }
        }
    }

    private void selectAll() {
        checkList.clear();
        if (isSelectedAll){
            setStateCheckedMap(true);
            isSelectedAll = false;
            checkList.addAll(list);
        }else {
            setStateCheckedMap(false);
            isSelectedAll = true;
        }
        adapter.notifyDataSetChanged();
    }

    private void inverse() {
        checkList.clear();
        for (int i=0;i<list.size();i++){
            if (stateCheckedMap.get(i)){
                stateCheckedMap.put(i,false);
            }else {
                stateCheckedMap.put(i,true);
                checkList.add(list.get(i));
            }
            lv_collect.setItemChecked(i,stateCheckedMap.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    private void delete() {
        if (checkList.size() == 0){
            Toast toast=Toast.makeText(MyCollectActivity.this,"您还没有选中任何数据",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }else {
            list.removeAll(checkList);//删除选中数据
            setStateCheckedMap(false);
            //后台删除选中数据  多选时传入多个动态id
            String dynamicIdList="";
            for (int i =0;i<checkList.size();i++){
                dynamicIdList = dynamicIdList+checkList.get(i).getDynamicId()+",";
            }
            deleteCollect(userId,dynamicIdList);
            checkList.clear();
            adapter.notifyDataSetChanged();
//            Toast.makeText(MyCollectActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

        }

    }

    private void cancel() {
        setStateCheckedMap(false);
        mLlEditBar.setVisibility(View.GONE);
        adapter.setShowCheckBox(false);
        adapter.notifyDataSetChanged();
    }

    //设置手机的状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    private void getViews() {
        mycollect_back = findViewById(R.id.mycollect_back);
        lv_collect = findViewById(R.id.lv_collect);
        mLlEditBar = findViewById(R.id.ll_edit_bar);
        ll_cancel=findViewById(R.id.ll_cancle);
        ll_delete=findViewById(R.id.ll_delete);
        ll_inverse=findViewById(R.id.ll_inverse);
        ll_selectall=findViewById(R.id.ll_select_all);
        lv_collect.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onBackPressed() {
        if (mLlEditBar.getVisibility() == View.VISIBLE){
            cancel();
        }
        super.onBackPressed();
    }
}
