package com.haibin.calendarviewproject.range;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.RangeMonthView;

/**
 * 范围选择月视图
 * Created by huanghaibin on 2018/9/13.
 */

public class CustomRangeMonthView extends RangeMonthView {

    private int mRadius;

    public CustomRangeMonthView(Context context) {
        super(context);
    }


    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme,
                                     boolean isSelectedPre, boolean isSelectedNext) {
        // 选中事件
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        return false;
    }


    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {
        mSchemePaint.setStyle(Paint.Style.FILL);

        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;

        if (calendar.isStartCalenday()) {
            canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
            canvas.drawRect(cx, cy - mRadius, x + mItemWidth, cy + mRadius, mSchemePaint);
        } else if (calendar.isEndCalenday()) {
            canvas.drawRect(x, cy - mRadius, cx, cy + mRadius, mSchemePaint);
            canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
        } else {
            canvas.drawRect(cx, cy - mRadius, x + mItemWidth, cy + mRadius, mSchemePaint);
            canvas.drawRect(x, cy - mRadius, cx, cy + mRadius, mSchemePaint);
        }
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int   cx        = x + mItemWidth / 2;

        boolean isInRange = isInRange(calendar);
        boolean isEnable  = !onCalendarIntercept(calendar);

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
                            calendar.isCurrentMonth() && isInRange && isEnable ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && isInRange && isEnable ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }
}
