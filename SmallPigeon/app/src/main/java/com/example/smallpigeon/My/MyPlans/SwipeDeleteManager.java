package com.example.smallpigeon.My.MyPlans;

import android.util.Log;

public class SwipeDeleteManager {
    private SwipeDeleteManager() {
    }

    private static SwipeDeleteManager swipeDeleteManager = new SwipeDeleteManager();

    public static SwipeDeleteManager getInstance() {
        return swipeDeleteManager;
    }

    private SwipeDeleteItem swipeDeleteItem;

    public void setSwipeDeleteItem(SwipeDeleteItem s) {
        swipeDeleteItem = s;
    }

    public void clear() {
        swipeDeleteItem = null;
    }

    public void close() {
        Log.e("Manager","关闭请求");
        if (swipeDeleteItem != null){
            swipeDeleteItem.close();
            Log.e("Manager","关闭");
        }
    }


    /**
     * 是否有item已经打开
     */
    public boolean haveOpened() {
        return swipeDeleteItem != null;
    }

    /**
     * 是否有item已经打开
     */
    public boolean haveOpened(SwipeDeleteItem s) {
        //如果为空 表示没有打开的item
        return swipeDeleteItem != null && swipeDeleteItem == s;
        // true 表示 两个item不是同一个 有一个已经打开的item
    }
}
