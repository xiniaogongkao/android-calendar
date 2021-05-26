package com.haibin.calendarviewproject.simple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.WeekView;

/**
 * 简单周视图
 * Created by huanghaibin on 2017/11/29.
 */

public class SimpleWeekView extends WeekView {
    private int mRadius;


    public SimpleWeekView(Context context) {
        super(context);
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);

        ////

//        int cx = x + mItemWidth / 2;
//        int cy = y + mItemHeight / 2;
//        if (isSelectedPre) {
//            if (isSelectedNext) {
//                canvas.drawRect(x, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
//            } else {//最后一个，the last
//                canvas.drawRect(x, cy - mRadius, cx, cy + mRadius, mSelectedPaint);
//                canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
//            }
//        } else {
//            if (isSelectedNext) {
//                canvas.drawRect(cx, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
//            }
//            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
//        }
//


        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x) {
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine;
        int cx = x + mItemWidth / 2;
        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mSchemeTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mCurMonthTextPaint);
        }
    }
}
