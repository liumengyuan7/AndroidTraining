package com.example.smallpigeon.My.MyPlans;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class SwipeDeleteItem extends FrameLayout {
    private ViewDragHelper viewDragHelper;
    private View contentView;
    private View deleteView;
    private int contentHeight;
    private int contentWidth;
    private int deleteWidth;
    float downX, downY;//按下的x y
    private float upX;
    private float upY;
    private float moveX;
    private float moveY;
    private float downIX;
    private float downIY;

    enum State {
        close, open
    }

    //默认状态是关闭
    private State state = State.close;

    public SwipeDeleteItem(@NonNull Context context) {
        super(context);
    }
    public SwipeDeleteItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeDeleteItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }
    /**
     * 此控件的结束标签读取完毕
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        deleteView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        contentView.layout(0, 0, contentWidth, contentHeight);
        Log.e("", "content" + contentWidth);
        deleteView.layout(contentWidth, 0, contentWidth + deleteWidth, deleteWidth);
    }
    /**
     * onMeasure 已经执行完毕 可以直接获取孩子宽高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentHeight = contentView.getMeasuredHeight();
        contentWidth = contentView.getMeasuredWidth();
        deleteWidth = deleteView.getMeasuredWidth();
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.e("进入","onInterceptTouchEvent");
        boolean value = viewDragHelper.shouldInterceptTouchEvent(event);
        if (!SwipeDeleteManager.getInstance().haveOpened(this)) {
            //如果打开的不是当前的item 关闭
            SwipeDeleteManager.getInstance().close();
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downIX = event.getX();
                downIY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                moveY = event.getY();
                //如果 不是点击事件 拦截事件 这里判断dx dy >1就是判断是否是点击事件
                if (Math.abs(moveX-downIX)>1|| Math.abs(moveY-downIY)>1){
                    Log.e("onInterceptTouchEvent","不是点击拦截事件");
                    value = true;
                }
                break;
        }
        return value;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("进入","onTouchEvent");
        //如果已经有item打开 不允许父控件拦截事件
        if (SwipeDeleteManager.getInstance().haveOpened(this)) {
            //如果触摸的是打开的item 让viewDragHelper处理触摸事件
            //请求父控件不要拦截事件
            Log.e("onTouchEvent","不允许父控件拦截");
            requestDisallowInterceptTouchEvent(true);
        } else if (SwipeDeleteManager.getInstance().haveOpened()) {
            //如果触摸的不是当前打开的item 直接消耗事件 不让item滑动
            requestDisallowInterceptTouchEvent(true);
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
             case MotionEvent.ACTION_MOVE:
                 float moveX = event.getX();
                 float moveY = event.getY();
                 float dx = Math.abs(moveX - downX);
                 float dy = Math.abs(moveY - downY);
                 if (dx > dy) {
                     //如果 x距离大于y 则不允许父控件拦截事件
                     Log.e("Touch","dx>dy");
                     requestDisallowInterceptTouchEvent(true);
                 }
                 downX = moveX;
                 downY = moveY;
                 break;
        }
        viewDragHelper.processTouchEvent(event);
        return true;
    }

        private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == contentView || child == deleteView;
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                /**伴随移动**/
                if (changedView == contentView) {
                    deleteView.layout(deleteView.getLeft() + dx, deleteView.getTop() + dy,
                            deleteView.getRight() + dx, deleteView.getBottom() + dy);
                } else if (changedView == deleteView) {
                    contentView.layout(contentView.getLeft() + dx, contentView.getTop() + dy,
                            contentView.getRight() + dx, contentView.getBottom() + dy);
                }

                //如果 item getLeft不等于0 就认为item已经打开 不允许其它item滑动
                if (contentView.getLeft() != 0)
                    SwipeDeleteManager.getInstance().setSwipeDeleteItem(SwipeDeleteItem.this);
                else SwipeDeleteManager.getInstance().clear();

                if (contentView.getLeft() == 0 && state != State.close) state = State.close;
                else if (contentView.getLeft() == -deleteWidth && state != State.open)
                    state = State.open;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return deleteWidth;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //限定滑动的范围
                if (child == contentView) {
                    if (left > 0) left = 0;
                    if (left < -deleteWidth) left = -deleteWidth;
                } else if (child == deleteView) {
                    if (left > contentWidth) left = contentWidth;
                    if (left < contentWidth - deleteWidth) left = contentWidth - deleteWidth;
                }
                return left;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                //当速度达到一定的值的时候 直接打开或者关闭
                if (xvel < -2000) {
                    open();
                    return;
                }
                if (xvel > 2000) {
                    close();
                    return;
                }
                if (contentView.getLeft() > -deleteWidth / 2) {
                    close();
                } else {
                    open();
                }
            }
        };

        public void open() {
            Log.e("item","动画打开");
            viewDragHelper.smoothSlideViewTo(contentView, -deleteWidth, contentView.getTop());
            state = State.open;
            SwipeDeleteManager.getInstance().setSwipeDeleteItem(SwipeDeleteItem.this);
            ViewCompat.postInvalidateOnAnimation(SwipeDeleteItem.this);
        }

        public void close() {
            Log.e("item","动画关闭");
            viewDragHelper.smoothSlideViewTo(contentView, 0, contentView.getTop());
            ViewCompat.postInvalidateOnAnimation(SwipeDeleteItem.this);
        }

        /**
         * 重写此方法刷新动画
         */
        @Override
        public void computeScroll() {
            if (viewDragHelper.continueSettling(true)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

}
