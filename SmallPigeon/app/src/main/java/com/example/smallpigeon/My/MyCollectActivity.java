package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);

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
        getAllCollect();
        setStatusBar();//状态栏隐藏

        adapter = new CollectAdapter(list,getApplicationContext(),stateCheckedMap);
        lv_collect.setAdapter(adapter);
        setOnListViewItemClickListener();
        setOnListViewItemLongClickListener();


        DynamicContent content = new DynamicContent();
        UserContent userContent = new UserContent();
        userContent.setUserNickname("aaa");
        content.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
        content.setUserContent(userContent);
        content.setContent(".....");
        content.setDevice(Build.MODEL);
        content.setType(0);
        list.add(content);

        DynamicContent content1 = new DynamicContent();
        UserContent userContent1 = new UserContent();
        ForwardContent forwardContent = new ForwardContent();
        forwardContent.setUserContent(userContent);
        forwardContent.setDynamicContent(content);
        userContent1.setUserNickname("啦啦啦");
        content1.setDate(new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date()));
        content1.setForwardContent(forwardContent);
        content1.setUserContent(userContent1);
        content1.setContent("今日跑步分享");
        content1.setDevice(Build.MODEL);
        content1.setType(1);
        list.add(content1);
    }

    //todo:查询所有的收藏信息
    private void getAllCollect() {

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
            Toast.makeText(MyCollectActivity.this,"您还没有选中任何数据",Toast.LENGTH_LONG).show();
        }else {
            list.removeAll(checkList);//删除选中数据
            setStateCheckedMap(false);
            checkList.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(MyCollectActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

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
