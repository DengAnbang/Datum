package com.home.dab.datum.demo.recyclerDemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

/**
 * Created by DAB on 2016/12/29 13:25.
 * 自定义的RecyclerView的ItemDecoration,实现挤动悬浮的效果
 */

public abstract class SuspendDecoration extends RecyclerView.ItemDecoration {

    private int mTitleHeight;//悬浮窗的高度
    private Paint mBackgroundPaint;//悬浮窗的画笔
    private TextPaint mTextPaint;//悬浮窗内容的画笔
    private int mTitleGravity;//悬浮窗的内容显示位置(左,中,右)
    private int mTextOffsetX, mTextOffsetY;
    public static final int TITLE_GRAVITY_CENTER = 0;
    public static final int TITLE_GRAVITY_LEFT = 1;
    public static final int TITLE_GRAVITY_RIGHT = 2;
    private Rect mRect;//悬浮窗的的矩形(动态改变的)

    /**
     * @param titleHeight  悬浮窗的的高度
     * @param titleSize    悬浮窗的字体大小
     * @param titleGravity 悬浮窗的显示位置
     */
    public SuspendDecoration(int titleHeight, int titleSize, int titleGravity) {
        mTitleHeight = titleHeight;
        mTitleGravity = titleGravity;
        mBackgroundPaint = new Paint();
//        mBackgroundPaint.setColor(Color.GREEN);
        //设置悬浮栏中文本的画笔
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(titleSize);
        mTextPaint.setColor(Color.DKGRAY);
        mRect = new Rect();
    }

    public SuspendDecoration() {
        mTitleHeight = 100;
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(Color.GREEN);
        //设置悬浮栏中文本的画笔
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(30);
        mTextPaint.setColor(Color.DKGRAY);
        mRect = new Rect();
    }

    /**
     * 设置字体的颜色
     *
     * @param color
     */
    public void setTextColor(int color) {
        mTextPaint.setColor(color);
    }

    /**
     * 设置文字在X轴的偏移
     *
     * @param textOffsetX
     */
    public void setTextOffsetX(int textOffsetX) {
        mTextOffsetX = textOffsetX;
    }

    /**
     * 设置文字在Y轴的偏移
     *
     * @param textOffsetY
     */
    public void setTextOffsetY(int textOffsetY) {
        mTextOffsetY = textOffsetY;
    }

    /**
     * 设置背景的颜色
     *
     * @param color
     */
    public void setBackgroundColor(int color) {
        mBackgroundPaint.setColor(color);
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //获取当前显示的所有item的大小
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //通过遍历来拿到 position
            View child = parent.getChildAt(i);
            int childPosition = parent.getChildAdapterPosition(child);

            //判断是否需要显示悬浮窗
            if (shouldShowTitle(childPosition)) {
                //悬浮窗的矩形边框
                mRect.set(child.getLeft(), child.getTop() - mTitleHeight, child.getRight(), child.getBottom() - child.getHeight());
                c.drawRect(mRect, mBackgroundPaint);
                //悬浮窗的内容
                c.drawText(showTitle(childPosition), getBaseLineX(mRect), getBaseLineY(mRect), mTextPaint);
            }
        }
    }

    //是否需要显示悬浮窗
    private boolean shouldShowTitle(int position) {
        return position == 0 || isNewGroup(position - 1, position);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //拿到当前的position
        int pos = parent.getChildAdapterPosition(view);
        //判断是否需要给这个position预留显示悬浮窗的位置
        if (shouldShowTitle(pos)) {
            outRect.top = mTitleHeight;
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //初始化悬浮窗的高度
        float textY = mTitleHeight;
        int childCount = parent.getChildCount();
        if (childCount < 1) return;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int childPosition = parent.getChildAdapterPosition(child);
            if (shouldShowTitle(childPosition)) {
                //通过获取布局管理器来找到当前显示的第一个position
                LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                //判断当前悬浮窗是否需要被挤压,如果需要,就改变矩形的bottom

                if (mTitleHeight < child.getTop() && child.getTop() < mTitleHeight * 2) {
                    textY = child.getTop() - mTitleHeight;
                }
                //不能依据当前的第一个来画悬浮
                if (i == 0) continue;

                mRect.set(0, (int) textY-mTitleHeight, child.getRight(), (int) textY);
                c.drawRect(mRect, mBackgroundPaint);
                c.drawText(showTitle(firstVisibleItemPosition), getBaseLineX(mRect), getBaseLineY(mRect), mTextPaint);
                //画一个悬浮就结束循环
                return;
            }
        }
    }

    /**
     * 决定文字绘制在X轴左,中,右
     *
     * @param rect
     * @return
     */
    private int getBaseLineX(Rect rect) {
        switch (mTitleGravity) {
            case TITLE_GRAVITY_LEFT:
                mTextPaint.setTextAlign(Paint.Align.LEFT);
                return rect.left+mTextOffsetX;
            case TITLE_GRAVITY_RIGHT:
                mTextPaint.setTextAlign(Paint.Align.RIGHT);
                return rect.right+mTextOffsetX;
            default:
                mTextPaint.setTextAlign(Paint.Align.CENTER);
                return rect.centerX()+mTextOffsetX;
        }

    }

    /**
     * 文字显示在Y轴居中
     *
     * @param rect
     * @return
     */
    private int getBaseLineY(Rect rect) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        return (int) (rect.centerY() - top / 2 - bottom / 2) + mTextOffsetY;
    }

    /**
     * 是否需要显示悬浮窗
     * @param priorGroupId
     * @param nowGroupId
     * @return
     */
    public abstract boolean isNewGroup(int priorGroupId, int nowGroupId);

    /**
     * 悬浮窗的内容
     *
     * @param position
     * @return
     */
    public abstract String showTitle(int position);
}
