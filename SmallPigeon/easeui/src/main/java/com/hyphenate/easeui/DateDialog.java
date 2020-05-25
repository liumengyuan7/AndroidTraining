package com.hyphenate.easeui;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import androidx.annotation.NonNull;

public class DateDialog extends Dialog {
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hour;
    private WheelView wv_minute;
    private TextView tv_dialog_cancel;
    private TextView tv_dialog_ok;

    private List<String> listYear = new ArrayList<>();
    private List<String> listMonth = new ArrayList<>();
    private List<String> listDay = new ArrayList<>();
    private List<String> listHour = new ArrayList<>();
    private List<String> listMinute = new ArrayList<>();
    private Context mContext;

    private int currentYear; //系统当前时间
    private int currentMonth;
    private int currentDay;
    private int currentHour;
    private int currentMinute;

    private int selectYear = 0;
    private int selectMonth = 0;
    public DateDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_date_layout, null);
        setContentView(view);
        initData();
        initView(view);
    }

    private void initData() {
        initCurrentDate();
        initYear();
        initMonth();
        initDay();
        initHour();
        initMinute();
    }

    /**
     * 初始化系统当前时间
     */
    private void initCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);
    }

    /**
     * 集合添加年
     */
    private void initYear() {
//        for (int i = currentYear; i <= currentYear + 1; i++) {
        for (int i = currentYear; i <= currentYear ; i++) {
            listYear.add(i + "年");
        }
    }

    /**
     * 集合添加月
     */
    private void initMonth() {
        for (int i = 1; i <= 12; i++) {
            listMonth.add(String.format("%02d", i) + "月");
        }
    }

    /**
     * 集合添加天数
     */
    private void initDay() {

        //判断一个月有多少天
        int hasDay = getDay(currentYear, (currentMonth+1));
        for (int i = 1; i <= hasDay; i++) {
            listDay.add(String.format("%02d", i) + "日");
        }
    }

    /**
     * 集合添加小时
     */
    private void initHour() {
        for (int i = 0; i <= 24; i++) {
            listHour.add(String.format("%02d", i) + "点");
        }
    }

    /**
     * 集合添加分钟
     */
    private void initMinute() {
        for (int i = 0; i <= 60; i++) {
            listMinute.add(String.format("%02d", i) + "分");
        }
    }

    private void initView(View view) {
        tv_dialog_cancel = view.findViewById(R.id.tv_dialog_cancel);
        tv_dialog_ok = view.findViewById(R.id.tv_dialog_ok);

        wv_year = view.findViewById(R.id.wv_year);
        wv_month = view.findViewById(R.id.wv_month);
        wv_day = view.findViewById(R.id.wv_day);
        wv_hour = view.findViewById(R.id.wv_hour);
        wv_minute = view.findViewById(R.id.wv_minute);
        //取消弹窗
        tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        tv_dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("时间",getDate());
//                dismiss();
//            }
//        });

        //设置在屏幕中的位置
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); //居中
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        window.setLayout((int) (displayMetrics.widthPixels * 0.9), WindowManager.LayoutParams.WRAP_CONTENT);

        //设置年数据
        wv_year.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
        wv_year.setSkin(WheelView.Skin.Holo); // common皮肤
        wv_year.setWheelData(listYear);
        wv_year.setSelection(0);
        wv_year.setVisibility(View.VISIBLE); //解决数据加载延迟
        wv_year.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                updateDay();
            }
        });
        //月
        wv_month.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
        wv_month.setSkin(WheelView.Skin.Holo); // common皮肤
        wv_month.setWheelData(listMonth);
        wv_month.setSelection(currentMonth);
        wv_month.setVisibility(View.VISIBLE);
        wv_month.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                updateDay();
            }
        });
        //日
        wv_day.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
        wv_day.setSkin(WheelView.Skin.Holo); // common皮肤
        wv_day.setWheelData(listDay);
        wv_day.setSelection(currentDay - 1);
        wv_day.setVisibility(View.VISIBLE);
        //时
        wv_hour.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
        wv_hour.setSkin(WheelView.Skin.Holo); // common皮肤
        wv_hour.setLoop(true);
        wv_hour.setWheelData(listHour);
        wv_hour.setSelection(currentHour);
        wv_hour.setVisibility(View.VISIBLE);
        //分
        wv_minute.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
        wv_minute.setSkin(WheelView.Skin.Holo); // common皮肤
        wv_minute.setLoop(true);
        wv_minute.setWheelData(listMinute);
        wv_minute.setSelection(currentMinute);
        wv_minute.setVisibility(View.VISIBLE);
    }

    /**
     * 用户滑动时更新每个月的天数
     */
    private void updateDay() {
        //获取当前滑动的位置
        selectYear = Integer.parseInt(wv_year.getSelectionItem().toString().replace("年", ""));
        selectMonth = Integer.parseInt(wv_month.getSelectionItem().toString().replace("月", ""));
        //判断一个月有多少天
        int hasDay = getDay(selectYear, selectMonth);
        listDay.clear();
        for (int i = 1; i <= hasDay; i++) {
            listDay.add(String.format("%02d", i) + "日");
        }
        wv_day.setWheelData(listDay);
    }

    /**
     * 根据是否闰年和月份判断本月的天数
     *
     * @param year
     * @param month
     * @return
     */
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;

        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    /**
     * 确定按钮监听
     *
     * @param listener
     */
    public void onClickOkBtnLisitener(View.OnClickListener listener) {
        tv_dialog_ok.setOnClickListener(listener);
    }

    /**
     * 获取时间
     * @return
     */
    public String getDate(){
        String year = wv_year.getSelectionItem().toString().replace("年","");
        String month = wv_month.getSelectionItem().toString().replace("月","");
        String day = wv_day.getSelectionItem().toString().replace("日","");
        String hour = wv_hour.getSelectionItem().toString().replace("点","");
        String minute = wv_minute.getSelectionItem().toString().replace("分","");
//        return year+"-"+month+"-"+day+" "+hour+":"+minute;
        return year+"年"+month+"月"+day+"日"+hour+"点"+minute+"分";
    }

    /**
     * 根据时间戳验证用户选择时间是否合理
     * @return
     */
//    public boolean isValid(){
//
//        long time = Long.parseLong(TimeUtil.createTimestamp(getDate()));
//
//        if (TimeUtil.getUnixStamp()>time){
//            return false;
//        }
//        return true;
//    }

}
