package com.daxiang.lottery.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.fragment.jcfragment.ForecastFragment;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.view.TitleBar;

public class ForecastActivity extends BaseNoTitleActivity {
    private TitleBar mTitleBar;
    private LinearLayout mLl_one;
    private TextView mDay_one;
    private TextView mWeek_one;
    private View mLine_one;
    private LinearLayout mLl_two;
    private TextView mDay_two;
    private TextView mWeek_two;
    private View mLine_two;
    private LinearLayout mLl_three;
    private TextView mDay_three;
    private TextView mWeek_three;
    private View mLine_three;
    private LinearLayout mLl_four;
    private TextView mDay_four;
    private TextView mWeek_four;
    private View mLine_four;
    private LinearLayout mLl_five;
    private TextView mDay_five;
    private TextView mWeek_five;
    private View mLine_five;
    private LinearLayout mLl_six;
    private TextView mDay_six;
    private TextView mWeek_six;
    private View mLine_six;
    private LinearLayout mLl_seven;
    private TextView mDay_seven;
    private TextView mWeek_seven;
    private View mLine_seven;
    private FrameLayout mFl;
    private ForecastFragment forecastFragment1;
    private ForecastFragment forecastFragment2;
    private ForecastFragment forecastFragment3;
    private ForecastFragment forecastFragment4;
    private ForecastFragment forecastFragment5;
    private ForecastFragment forecastFragment6;
    private ForecastFragment forecastFragment7;
    private FragmentManager mFragmentManager;
    private int colorRed;
    private int colorGray;
    private int colorBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        titleData();
        if (weekS == null || weekS.length != 7 || issueS == null || issueS.length != 7 || dayS == null || dayS.length != 7)
            return;
        initView();
        colorRed = getResources().getColor(R.color.red_txt);
        colorGray = getResources().getColor(R.color.gray_txt);
        colorBlack = getResources().getColor(R.color.deep_txt);
        initFragment();
        addListener();
    }

    private String[] issueS = new String[7];
    private String[] weekS = new String[7];
    private String[] dayS = new String[7];

    private void titleData() {
        long time = System.currentTimeMillis();
        long stepTime = 86400000;
        for (int j = 6; j >= 0; j--) {
            issueS[j] = DateFormtUtils.dateByLong(time - stepTime * (6 - j));
            weekS[j] = DateFormtUtils.forecastDate(time - stepTime * (6 - j));
            dayS[j] = dayByIssue(issueS[j]);
        }
    }

    private String dayByIssue(String issue) {
        if (TextUtils.isEmpty(issue)) return "--";
        String[] split = issue.split("\\-");
        if (split == null || split.length < 3) return "--";
        return split[1] + "-" + split[2];
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlebar_forecast);
        mTitleBar.setTitle("精选预测");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        mLl_one = (LinearLayout) findViewById(R.id.ll_one);
        mDay_one = (TextView) findViewById(R.id.day_one);
        mWeek_one = (TextView) findViewById(R.id.week_one);
        mLine_one = (View) findViewById(R.id.line_one);
        mLl_two = (LinearLayout) findViewById(R.id.ll_two);
        mDay_two = (TextView) findViewById(R.id.day_two);
        mWeek_two = (TextView) findViewById(R.id.week_two);
        mLine_two = (View) findViewById(R.id.line_two);
        mLl_three = (LinearLayout) findViewById(R.id.ll_three);
        mDay_three = (TextView) findViewById(R.id.day_three);
        mWeek_three = (TextView) findViewById(R.id.week_three);
        mLine_three = (View) findViewById(R.id.line_three);
        mLl_four = (LinearLayout) findViewById(R.id.ll_four);
        mDay_four = (TextView) findViewById(R.id.day_four);
        mWeek_four = (TextView) findViewById(R.id.week_four);
        mLine_four = (View) findViewById(R.id.line_four);
        mLl_five = (LinearLayout) findViewById(R.id.ll_five);
        mDay_five = (TextView) findViewById(R.id.day_five);
        mWeek_five = (TextView) findViewById(R.id.week_five);
        mLine_five = (View) findViewById(R.id.line_five);
        mLl_six = (LinearLayout) findViewById(R.id.ll_six);
        mDay_six = (TextView) findViewById(R.id.day_six);
        mWeek_six = (TextView) findViewById(R.id.week_six);
        mLine_six = (View) findViewById(R.id.line_six);
        mLl_seven = (LinearLayout) findViewById(R.id.ll_seven);
        mDay_seven = (TextView) findViewById(R.id.day_seven);
        mWeek_seven = (TextView) findViewById(R.id.week_seven);
        mLine_seven = (View) findViewById(R.id.line_seven);
        mFl = (FrameLayout) findViewById(R.id.fl);

        mDay_one.setText(dayS[0]);
        mWeek_one.setText(weekS[0]);
        mDay_two.setText(dayS[1]);
        mWeek_two.setText(weekS[1]);
        mDay_three.setText(dayS[2]);
        mWeek_three.setText(weekS[2]);
        mDay_four.setText(dayS[3]);
        mWeek_four.setText(weekS[3]);
        mDay_five.setText(dayS[4]);
        mWeek_five.setText(weekS[4]);
        mDay_six.setText(dayS[5]);
        mWeek_six.setText(weekS[5]);
        mDay_seven.setText(dayS[6]);
        mWeek_seven.setText(weekS[6]);
    }

    private void initFragment() {
        forecastFragment7 = new ForecastFragment();
        forecastFragment7.setData(issueS[6]);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl, forecastFragment7);
        fragmentTransaction.commit();
    }


    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(BackListener);
        mLl_one.setOnClickListener(ClickListener);
        mLl_two.setOnClickListener(ClickListener);
        mLl_three.setOnClickListener(ClickListener);
        mLl_four.setOnClickListener(ClickListener);
        mLl_five.setOnClickListener(ClickListener);
        mLl_six.setOnClickListener(ClickListener);
        mLl_seven.setOnClickListener(ClickListener);

    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            switch (v.getId()) {
                case R.id.ll_one:
                    setOne(fragmentTransaction);
                    break;
                case R.id.ll_two:
                    setTwo(fragmentTransaction);
                    break;
                case R.id.ll_three:
                    setThree(fragmentTransaction);
                    break;
                case R.id.ll_four:
                    setFour(fragmentTransaction);
                    break;
                case R.id.ll_five:
                    setFive(fragmentTransaction);
                    break;
                case R.id.ll_six:
                    setSix(fragmentTransaction);
                    break;
                case R.id.ll_seven:
                    setSeven(fragmentTransaction);
                    break;

            }
            fragmentTransaction.commit();
        }
    };
    View.OnClickListener BackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private void setOne(FragmentTransaction fragmentTransaction) {
        mDay_one.setTextColor(colorRed);
        mWeek_one.setTextColor(colorRed);
        mLine_one.setVisibility(View.VISIBLE);

        mDay_two.setTextColor(colorBlack);
        mWeek_two.setTextColor(colorGray);
        mLine_two.setVisibility(View.INVISIBLE);

        mDay_three.setTextColor(colorBlack);
        mWeek_three.setTextColor(colorGray);
        mLine_three.setVisibility(View.INVISIBLE);

        mDay_four.setTextColor(colorBlack);
        mWeek_four.setTextColor(colorGray);
        mLine_four.setVisibility(View.INVISIBLE);

        mDay_five.setTextColor(colorBlack);
        mWeek_five.setTextColor(colorGray);
        mLine_five.setVisibility(View.INVISIBLE);

        mDay_six.setTextColor(colorBlack);
        mWeek_six.setTextColor(colorGray);
        mLine_six.setVisibility(View.INVISIBLE);

        mDay_seven.setTextColor(colorBlack);
        mWeek_seven.setTextColor(colorGray);
        mLine_seven.setVisibility(View.INVISIBLE);

        if (forecastFragment1 == null) {
            forecastFragment1 = new ForecastFragment();
            forecastFragment1.setData(issueS[0]);
            fragmentTransaction.add(R.id.fl, forecastFragment1);
        }
        if (forecastFragment2 != null) {
            fragmentTransaction.hide(forecastFragment2);
        }
        if (forecastFragment3 != null) {
            fragmentTransaction.hide(forecastFragment3);
        }
        if (forecastFragment4 != null) {
            fragmentTransaction.hide(forecastFragment4);
        }
        if (forecastFragment5 != null) {
            fragmentTransaction.hide(forecastFragment5);
        }
        if (forecastFragment6 != null) {
            fragmentTransaction.hide(forecastFragment6);
        }
        if (forecastFragment7 != null) {
            fragmentTransaction.hide(forecastFragment7);
        }
        fragmentTransaction.show(forecastFragment1);
    }

    private void setTwo(FragmentTransaction fragmentTransaction) {
        mDay_one.setTextColor(colorBlack);
        mWeek_one.setTextColor(colorGray);
        mLine_one.setVisibility(View.INVISIBLE);

        mDay_two.setTextColor(colorRed);
        mWeek_two.setTextColor(colorRed);
        mLine_two.setVisibility(View.VISIBLE);

        mDay_three.setTextColor(colorBlack);
        mWeek_three.setTextColor(colorGray);
        mLine_three.setVisibility(View.INVISIBLE);

        mDay_four.setTextColor(colorBlack);
        mWeek_four.setTextColor(colorGray);
        mLine_four.setVisibility(View.INVISIBLE);

        mDay_five.setTextColor(colorBlack);
        mWeek_five.setTextColor(colorGray);
        mLine_five.setVisibility(View.INVISIBLE);

        mDay_six.setTextColor(colorBlack);
        mWeek_six.setTextColor(colorGray);
        mLine_six.setVisibility(View.INVISIBLE);

        mDay_seven.setTextColor(colorBlack);
        mWeek_seven.setTextColor(colorGray);
        mLine_seven.setVisibility(View.INVISIBLE);

        if (forecastFragment2 == null) {
            forecastFragment2 = new ForecastFragment();
            forecastFragment2.setData(issueS[1]);
            fragmentTransaction.add(R.id.fl, forecastFragment2);
        }
        if (forecastFragment1 != null) {
            fragmentTransaction.hide(forecastFragment1);
        }
        if (forecastFragment3 != null) {
            fragmentTransaction.hide(forecastFragment3);
        }
        if (forecastFragment4 != null) {
            fragmentTransaction.hide(forecastFragment4);
        }
        if (forecastFragment5 != null) {
            fragmentTransaction.hide(forecastFragment5);
        }
        if (forecastFragment6 != null) {
            fragmentTransaction.hide(forecastFragment6);
        }
        if (forecastFragment7 != null) {
            fragmentTransaction.hide(forecastFragment7);
        }
        fragmentTransaction.show(forecastFragment2);
    }

    private void setThree(FragmentTransaction fragmentTransaction) {
        mDay_one.setTextColor(colorBlack);
        mWeek_one.setTextColor(colorGray);
        mLine_one.setVisibility(View.INVISIBLE);

        mDay_two.setTextColor(colorBlack);
        mWeek_two.setTextColor(colorGray);
        mLine_two.setVisibility(View.INVISIBLE);

        mDay_three.setTextColor(colorRed);
        mWeek_three.setTextColor(colorRed);
        mLine_three.setVisibility(View.VISIBLE);

        mDay_four.setTextColor(colorBlack);
        mWeek_four.setTextColor(colorGray);
        mLine_four.setVisibility(View.INVISIBLE);

        mDay_five.setTextColor(colorBlack);
        mWeek_five.setTextColor(colorGray);
        mLine_five.setVisibility(View.INVISIBLE);

        mDay_six.setTextColor(colorBlack);
        mWeek_six.setTextColor(colorGray);
        mLine_six.setVisibility(View.INVISIBLE);

        mDay_seven.setTextColor(colorBlack);
        mWeek_seven.setTextColor(colorGray);
        mLine_seven.setVisibility(View.INVISIBLE);

        if (forecastFragment3 == null) {
            forecastFragment3 = new ForecastFragment();
            forecastFragment3.setData(issueS[2]);
            fragmentTransaction.add(R.id.fl, forecastFragment3);
        }

        if (forecastFragment1 != null) {
            fragmentTransaction.hide(forecastFragment1);
        }
        if (forecastFragment2 != null) {
            fragmentTransaction.hide(forecastFragment2);
        }
        if (forecastFragment4 != null) {
            fragmentTransaction.hide(forecastFragment4);
        }
        if (forecastFragment5 != null) {
            fragmentTransaction.hide(forecastFragment5);
        }
        if (forecastFragment6 != null) {
            fragmentTransaction.hide(forecastFragment6);
        }
        if (forecastFragment7 != null) {
            fragmentTransaction.hide(forecastFragment7);
        }
        fragmentTransaction.show(forecastFragment3);
    }

    private void setFour(FragmentTransaction fragmentTransaction) {
        mDay_one.setTextColor(colorBlack);
        mWeek_one.setTextColor(colorGray);
        mLine_one.setVisibility(View.INVISIBLE);

        mDay_two.setTextColor(colorBlack);
        mWeek_two.setTextColor(colorGray);
        mLine_two.setVisibility(View.INVISIBLE);

        mDay_three.setTextColor(colorBlack);
        mWeek_three.setTextColor(colorGray);
        mLine_three.setVisibility(View.INVISIBLE);

        mDay_four.setTextColor(colorRed);
        mWeek_four.setTextColor(colorRed);
        mLine_four.setVisibility(View.VISIBLE);

        mDay_five.setTextColor(colorBlack);
        mWeek_five.setTextColor(colorGray);
        mLine_five.setVisibility(View.INVISIBLE);

        mDay_six.setTextColor(colorBlack);
        mWeek_six.setTextColor(colorGray);
        mLine_six.setVisibility(View.INVISIBLE);

        mDay_seven.setTextColor(colorBlack);
        mWeek_seven.setTextColor(colorGray);
        mLine_seven.setVisibility(View.INVISIBLE);

        if (forecastFragment4 == null) {
            forecastFragment4 = new ForecastFragment();
            forecastFragment4.setData(issueS[3]);
            fragmentTransaction.add(R.id.fl, forecastFragment4);
        }

        if (forecastFragment1 != null) {
            fragmentTransaction.hide(forecastFragment1);
        }
        if (forecastFragment2 != null) {
            fragmentTransaction.hide(forecastFragment2);
        }
        if (forecastFragment3 != null) {
            fragmentTransaction.hide(forecastFragment3);
        }
        if (forecastFragment5 != null) {
            fragmentTransaction.hide(forecastFragment5);
        }
        if (forecastFragment6 != null) {
            fragmentTransaction.hide(forecastFragment6);
        }
        if (forecastFragment7 != null) {
            fragmentTransaction.hide(forecastFragment7);
        }
        fragmentTransaction.show(forecastFragment4);
    }

    private void setFive(FragmentTransaction fragmentTransaction) {
        mDay_one.setTextColor(colorBlack);
        mWeek_one.setTextColor(colorGray);
        mLine_one.setVisibility(View.INVISIBLE);

        mDay_two.setTextColor(colorBlack);
        mWeek_two.setTextColor(colorGray);
        mLine_two.setVisibility(View.INVISIBLE);

        mDay_three.setTextColor(colorBlack);
        mWeek_three.setTextColor(colorGray);
        mLine_three.setVisibility(View.INVISIBLE);

        mDay_four.setTextColor(colorBlack);
        mWeek_four.setTextColor(colorGray);
        mLine_four.setVisibility(View.INVISIBLE);

        mDay_five.setTextColor(colorRed);
        mWeek_five.setTextColor(colorRed);
        mLine_five.setVisibility(View.VISIBLE);

        mDay_six.setTextColor(colorBlack);
        mWeek_six.setTextColor(colorGray);
        mLine_six.setVisibility(View.INVISIBLE);

        mDay_seven.setTextColor(colorBlack);
        mWeek_seven.setTextColor(colorGray);
        mLine_seven.setVisibility(View.INVISIBLE);

        if (forecastFragment5 == null) {
            forecastFragment5 = new ForecastFragment();
            forecastFragment5.setData(issueS[4]);
            fragmentTransaction.add(R.id.fl, forecastFragment5);
        }
        if (forecastFragment1 != null) {
            fragmentTransaction.hide(forecastFragment1);
        }
        if (forecastFragment2 != null) {
            fragmentTransaction.hide(forecastFragment2);
        }
        if (forecastFragment3 != null) {
            fragmentTransaction.hide(forecastFragment3);
        }
        if (forecastFragment4 != null) {
            fragmentTransaction.hide(forecastFragment4);
        }
        if (forecastFragment6 != null) {
            fragmentTransaction.hide(forecastFragment6);
        }
        if (forecastFragment7 != null) {
            fragmentTransaction.hide(forecastFragment7);
        }
        fragmentTransaction.show(forecastFragment5);
    }

    private void setSix(FragmentTransaction fragmentTransaction) {
        mDay_one.setTextColor(colorBlack);
        mWeek_one.setTextColor(colorGray);
        mLine_one.setVisibility(View.INVISIBLE);

        mDay_two.setTextColor(colorBlack);
        mWeek_two.setTextColor(colorGray);
        mLine_two.setVisibility(View.INVISIBLE);

        mDay_three.setTextColor(colorBlack);
        mWeek_three.setTextColor(colorGray);
        mLine_three.setVisibility(View.INVISIBLE);

        mDay_four.setTextColor(colorBlack);
        mWeek_four.setTextColor(colorGray);
        mLine_four.setVisibility(View.INVISIBLE);

        mDay_five.setTextColor(colorBlack);
        mWeek_five.setTextColor(colorGray);
        mLine_five.setVisibility(View.INVISIBLE);

        mDay_six.setTextColor(colorRed);
        mWeek_six.setTextColor(colorRed);
        mLine_six.setVisibility(View.VISIBLE);

        mDay_seven.setTextColor(colorBlack);
        mWeek_seven.setTextColor(colorGray);
        mLine_seven.setVisibility(View.INVISIBLE);

        if (forecastFragment6 == null) {
            forecastFragment6 = new ForecastFragment();
            forecastFragment6.setData(issueS[5]);
            fragmentTransaction.add(R.id.fl, forecastFragment6);
        }
        if (forecastFragment1 != null) {
            fragmentTransaction.hide(forecastFragment1);
        }
        if (forecastFragment2 != null) {
            fragmentTransaction.hide(forecastFragment2);
        }
        if (forecastFragment3 != null) {
            fragmentTransaction.hide(forecastFragment3);
        }
        if (forecastFragment4 != null) {
            fragmentTransaction.hide(forecastFragment4);
        }
        if (forecastFragment5 != null) {
            fragmentTransaction.hide(forecastFragment5);
        }
        if (forecastFragment7 != null) {
            fragmentTransaction.hide(forecastFragment7);
        }

        fragmentTransaction.show(forecastFragment6);
    }

    private void setSeven(FragmentTransaction fragmentTransaction) {
        mDay_one.setTextColor(colorBlack);
        mWeek_one.setTextColor(colorGray);
        mLine_one.setVisibility(View.INVISIBLE);

        mDay_two.setTextColor(colorBlack);
        mWeek_two.setTextColor(colorGray);
        mLine_two.setVisibility(View.INVISIBLE);

        mDay_three.setTextColor(colorBlack);
        mWeek_three.setTextColor(colorGray);
        mLine_three.setVisibility(View.INVISIBLE);

        mDay_four.setTextColor(colorBlack);
        mWeek_four.setTextColor(colorGray);
        mLine_four.setVisibility(View.INVISIBLE);

        mDay_five.setTextColor(colorBlack);
        mWeek_five.setTextColor(colorGray);
        mLine_five.setVisibility(View.INVISIBLE);

        mDay_six.setTextColor(colorBlack);
        mWeek_six.setTextColor(colorGray);
        mLine_six.setVisibility(View.INVISIBLE);

        mDay_seven.setTextColor(colorRed);
        mWeek_seven.setTextColor(colorRed);
        mLine_seven.setVisibility(View.VISIBLE);

        if (forecastFragment7 == null) {
            forecastFragment7 = new ForecastFragment();
            forecastFragment7.setData(issueS[6]);
            fragmentTransaction.add(R.id.fl, forecastFragment7);
        }
        if (forecastFragment1 != null) {
            fragmentTransaction.hide(forecastFragment1);
        }
        if (forecastFragment2 != null) {
            fragmentTransaction.hide(forecastFragment2);
        }
        if (forecastFragment3 != null) {
            fragmentTransaction.hide(forecastFragment3);
        }
        if (forecastFragment4 != null) {
            fragmentTransaction.hide(forecastFragment4);
        }
        if (forecastFragment5 != null) {
            fragmentTransaction.hide(forecastFragment5);
        }
        if (forecastFragment6 != null) {
            fragmentTransaction.hide(forecastFragment6);
        }
       fragmentTransaction.show(forecastFragment7);
    }
}
