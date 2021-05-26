package com.haibin.calendarviewproject.single;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;
import com.haibin.calendarviewproject.R;

/**
 * 高仿魅族日历布局
 * Created by huanghaibin on 2017/11/15.
 */

public class SingleMonthView extends MonthView {

    private int   mRadius;
    private Paint mRingPaint = new Paint();
    private int   mRingRadius;

    private Bitmap mTestBitmap;
    private Bitmap mDoneBitmap;
    private Bitmap mSelectedBitmap;


    /**
     * 不可用画笔
     */
    private Paint mDisablePaint = new Paint();

    private int mH;

    public SingleMonthView(Context context) {
        super(context);

        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mSchemePaint.getColor());
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(dipToPx(context, 1));
        setLayerType(View.LAYER_TYPE_SOFTWARE, mRingPaint);
        mRingPaint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID));

        mDisablePaint.setColor(0xFF9f9f9f);
        mDisablePaint.setAntiAlias(true);
        mDisablePaint.setStrokeWidth(dipToPx(context, 2));
        mDisablePaint.setFakeBoldText(true);

        mH = dipToPx(context, 18);

    }

    @Override
    protected void onPreviewHook() {
        mTestBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_clear);
        mDoneBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_custom);
        mSelectedBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_calendar);

        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mRingRadius = Math.min(mItemWidth, mItemHeight) / 7 * 2;
        mSelectTextPaint.setTextSize(dipToPx(getContext(), 17));
    }

    /**
     * 如果需要点击Scheme没有效果，则return true
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return false 则不绘制onDrawScheme，因为这里背景色是互斥的
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        if (hasScheme) {
            return true;
        }

        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        Log.i("test_end_canvas", "cx :" + cx);
        Log.i("test_end_canvas", "cy :" + cy);

        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        canvas.drawBitmap(mTestBitmap, cx - mTestBitmap.getWidth() / 2, cy - mTestBitmap.getHeight() / 2, mSelectedPaint);


        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
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

        ////////// test code

        if (calendar.getStatus() == 0) {
            return;
        }

        if (calendar.getStatus() == Calendar.STATUS_TEST) {
            canvas.drawBitmap(mDoneBitmap, cx - mDoneBitmap.getWidth() / 2, cy - mDoneBitmap.getHeight() / 2, mSelectedPaint);
        } else if (calendar.getStatus() == Calendar.STATUS_TEST_2) {
            canvas.drawBitmap(mSelectedBitmap, cx - mSelectedBitmap.getWidth() / 2, cy - mSelectedBitmap.getHeight() / 2, mSelectedPaint);
        } else if (calendar.getStatus() == Calendar.STATUS_TEST_3) {
        }
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y - dipToPx(getContext(), 1);
        int   cx        = x + mItemWidth / 2;
        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }

        //日期是否可用？拦截
        if (onCalendarIntercept(calendar)) {
            canvas.drawLine(x + mH, y + mH, x + mItemWidth - mH, y + mItemHeight - mH, mDisablePaint);
        }

    }


    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
