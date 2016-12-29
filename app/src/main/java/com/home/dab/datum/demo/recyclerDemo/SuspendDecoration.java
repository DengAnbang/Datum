package com.home.dab.datum.demo.recyclerDemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

/**
 * Created by DAB on 2016/12/29 13:25.
 */

public abstract class SuspendDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "SuspendDecoration";
    private int topGap = 100;
    private Paint paint;
    private TextPaint textPaint;
    private int alignBottom = 100;
//    public View getView() {
//        return mView.clone();
//    }


    public SuspendDecoration() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
//        mView = view;
        //设置悬浮栏中文本的画笔
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextAlign(Paint.Align.LEFT);
//        fontMetrics = new Paint.FontMetrics();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int childPosition = parent.getChildAdapterPosition(child);
            if (shouldShowTitle(childPosition)) {
                c.drawRect(child.getLeft(), child.getTop() - topGap, child.getRight(), child.getBottom() - child.getHeight(), paint);
                c.drawText("dasd", child.getLeft(), child.getBottom() - child.getHeight(), textPaint);
            }
        }
    }

    private boolean shouldShowTitle(int position) {
        return position == 0 || isFirstInGroup(position - 1, position);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        Log.i(TAG, "getItemOffsets：" + pos);
//        String groupId = callback.getGroupId(pos);
//        if (groupId.equals("-1")) return;
        //只有是同一组的第一个才显示悬浮栏
        if (shouldShowTitle(pos)) {
            outRect.top = topGap;
//            if (dataList.get(pos).getName() == "") {
//                outRect.top = 0;
//            }
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
//
//        int childCount = parent.getChildCount();
//
//
////        if (childCount<1)return;
////        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
////        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
////        View child = parent.getChildAt(firstVisibleItemPosition);
////        if (shouldShowTitle(firstVisibleItemPosition)) {
////
////        }
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            int childPosition = parent.getChildAdapterPosition(child);
//            if (shouldShowTitle(childPosition)) continue;
//            if (child.getTop()-topGap <= 0) continue;
//            if (child.getTop()>topGap*2) continue;
//            float textY=child.getTop() - topGap;
////            float textY = Math.max(topGap, child.getTop());
////            int viewBottom = child.getBottom();
            c.drawRect(0, 0, parent.getRight(), topGap, paint);
////            c.drawText("dasd111", 0, textY, textPaint);
//        }


//        int itemCount = state.getItemCount();
//        int childCount = parent.getChildCount();
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
//
//
//        for (int i = 0; i < childCount; i++) {
//            View view = parent.getChildAt(i);
//            int position = parent.getChildAdapterPosition(view);
//
//            if (!shouldShowTitle(position)) continue;
//
//            String textLine = "sad";
//            if (TextUtils.isEmpty(textLine)) continue;
//
//            int viewBottom = view.getBottom();
//            float textY = Math.max(topGap, view.getTop());
//            //下一个和当前不一样移动当前
//            if (position + 1 < itemCount) {
//                if (!shouldShowTitle(position))
//                    //组内最后一个view进入了header
//                    if (viewBottom < textY) {
//                        textY = viewBottom;
//                    }
//            }
//            //textY - topGap决定了悬浮栏绘制的高度和位置
////            c.drawRect(left, textY - topGap, right, textY, paint);
////            //left+2*alignBottom 决定了文本往左偏移的多少（加-->向左移）
////            //textY-alignBottom  决定了文本往右偏移的多少  (减-->向上移)
////            c.drawText(textLine, left + 2 * alignBottom, textY - alignBottom, textPaint);
//            c.drawRect(0, textY - topGap, parent.getRight(), textY, paint);
////            c.drawText("dasd111", 0, textY, textPaint);
//        }
    }

    abstract boolean isFirstInGroup(int priorGroupId, int nowGroupId);

}
