package com.haibin.calendarviewproject.simple;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarviewproject.Article;
import com.haibin.calendarviewproject.ArticleAdapter;
import com.haibin.calendarviewproject.R;
import com.haibin.calendarviewproject.base.activity.BaseActivity;
import com.haibin.calendarviewproject.colorful.ColorfulActivity;
import com.haibin.calendarviewproject.group.GroupItemDecoration;
import com.haibin.calendarviewproject.group.GroupRecyclerView;
import com.haibin.calendarviewproject.index.IndexActivity;
import com.haibin.calendarviewproject.meizu.MeiZuActivity;

import java.util.HashMap;
import java.util.Map;

public class SimpleActivity extends BaseActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener {

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;

    GroupRecyclerView mRecyclerView;
    private int mYear;
    CalendarLayout mCalendarLayout;

    public static void show(Context context) {
        context.startActivity(new Intent(context, SimpleActivity.class));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setStatusBarDarkMode();
        mTextMonthDay = findViewById(R.id.tv_month_day);
        mTextYear = findViewById(R.id.tv_year);
        mTextLunar = findViewById(R.id.tv_lunar);
        mRelativeTool = findViewById(R.id.rl_tool);
        mCalendarView = findViewById(R.id.calendarView);
        mTextCurrentDay = findViewById(R.id.tv_current_day);
        mCalendarView.scrollToCurrent();
        mCalendarView.setSelectRangeMode();
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.setSelectCalendarRange(2021, 5, 1, 2021, 5, 2);
            }
        });


        mCalendarLayout = findViewById(R.id.calendarLayout);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
        mCalendarView.setSelectSingleMode();


        mCalendarView.setOnCalendarInterceptListener(new CalendarView.OnCalendarInterceptListener() {
            @Override
            public boolean onCalendarIntercept(Calendar calendar) {
                return false;
            }

            @Override
            public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
                Toast.makeText(SimpleActivity.this, "test:" + calendar.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new GroupItemDecoration<String, Article>());
        mRecyclerView.setAdapter(new ArticleAdapter(this));
        mRecyclerView.notifyDataSetChanged();


    }

    @Override
    protected void initData() {

        int year  = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, R.color.solar_background, "有任务", Calendar.STATUS_TEST,true, false).toString(),
                getSchemeCalendar(year, month, 3, R.color.solar_background, "假", Calendar.STATUS_TEST,true, false));
        map.put(getSchemeCalendar(year, month, 4, R.color.solar_background, "有任务",Calendar.STATUS_TEST, false, false).toString(),
                getSchemeCalendar(year, month, 4, R.color.solar_background, "假", Calendar.STATUS_TEST,false, false));
        map.put(getSchemeCalendar(year, month, 5, R.color.solar_background, "有任务",Calendar.STATUS_TEST, false, false).toString(),
                getSchemeCalendar(year, month, 5, R.color.solar_background, "假", Calendar.STATUS_TEST,false, false));
        map.put(getSchemeCalendar(year, month, 6, R.color.solar_background, "有任务", Calendar.STATUS_TEST_2,false, false).toString(),
                getSchemeCalendar(year, month, 6, R.color.solar_background, "假",Calendar.STATUS_TEST_2, false, false));
        map.put(getSchemeCalendar(year, month, 7, R.color.solar_background, "有任务", Calendar.STATUS_TEST_2,false, false).toString(),
                getSchemeCalendar(year, month, 7, R.color.solar_background, "假", Calendar.STATUS_TEST_2,false, false));
        map.put(getSchemeCalendar(year, month, 8, R.color.solar_background, "有任务", Calendar.STATUS_TEST_2,false, false).toString(),
                getSchemeCalendar(year, month, 8, R.color.solar_background, "假", Calendar.STATUS_TEST_2,false, false));
        map.put(getSchemeCalendar(year, month, 9, R.color.solar_background, "有任务", Calendar.STATUS_TEST_2,false, false).toString(),
                getSchemeCalendar(year, month, 9, R.color.solar_background, "假", Calendar.STATUS_TEST_2,false, false));
        map.put(getSchemeCalendar(year, month, 10, R.color.solar_background, "有任务", false, false).toString(),
                getSchemeCalendar(year, month, 10, R.color.solar_background, "假", false, false));
        map.put(getSchemeCalendar(year, month, 11, R.color.solar_background, "有任务", false, false).toString(),
                getSchemeCalendar(year, month, 11, R.color.solar_background, "假", false, false));
        map.put(getSchemeCalendar(year, month, 12, R.color.solar_background, "有任务", false, true).toString(),
                getSchemeCalendar(year, month, 12, R.color.solar_background, "假", false, true));

        map.put(getSchemeCalendar(year, month, 13, R.color.colorAccent, "有任务", true, false).toString(),
                getSchemeCalendar(year, month, 13, R.color.colorAccent, "假", true, false));
        map.put(getSchemeCalendar(year, month, 14, R.color.colorAccent, "有任务", false, false).toString(),
                getSchemeCalendar(year, month, 14, R.color.colorAccent, "假", false, false));
        map.put(getSchemeCalendar(year, month, 15, R.color.colorAccent, "有任务", false, true).toString(),
                getSchemeCalendar(year, month, 15, R.color.colorAccent, "假", false, true));


        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_flyme:
                MeiZuActivity.show(this);
                break;
            case R.id.ll_simple:
                SimpleActivity.show(this);
                break;
            case R.id.ll_colorful:
                ColorfulActivity.show(this);
                break;
            case R.id.ll_index:
                IndexActivity.show(this);
                break;
        }
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text, boolean isStart, boolean isEnd) {
        return getSchemeCalendar(year,month,day,color,text,0,isStart,isEnd);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text,int status, boolean isStart, boolean isEnd) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setStatus(status);
        calendar.setDay(day);
        calendar.setSchemeColor(getResources().getColor(color));//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.setStartCalenday(isStart);
        calendar.setEndCalenday(isEnd);
        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }


}
