package com.daxiang.lottery.activity.lotteryactivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.RebateDeatailAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.RebateDetailData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.BillListView;
import com.daxiang.lottery.view.TitleBar;
import com.daxiang.lottery.wheelview.ChangeBirthDialog;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RebateDetailActivity extends AppCompatActivity {
    private LinearLayout mActivity_rebate_detail;
    private TitleBar mTitleBar;
    private SmartRefreshLayout mRefresh;

    private TextView mTotale_money;
    private TextView mTotale_buy;
    private TextView mUser_name;
    private TextView mCreat_time;
    private TextView mStart_time;
    private TextView mEnd_time;
    private TextView mTv_search;
    private TextView mNoData;
    private TextView mText_nodata;
    private LinearLayout mLl_Red;
    private LinearLayout mLl_yongjin;
    private TextView mText_yongjin;
    private BillListView mListView;
    private TextView mFandian;
    private Context mContext;
    private String userId;
    private String userName;
    private long creatTime;
    private long startTime = System.currentTimeMillis() / 1000 - (long) 604800;
    private long endTime = System.currentTimeMillis() / 1000;
    private int pageStart = 1;
    private int pageSize = 15;
    private int totalPage;
    ArrayList<RebateDetailData.DataBean.ItemsBean.DetailBean> detailList = new ArrayList<>();
    private RebateDeatailAdapter mAdapter;
    //flag为false时候是下线的返利详情。true时候是佣金详情
    private boolean flag;
    private String phone;
    private boolean isChangFanDian;
    private int userType;//1 大户，0 散户，2 代理
    private int isFind;//是否允许继续查下线 0不允许, 1允许
    private String findUserId;
    private String parentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate_detail);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        mContext = this;
        userId = getIntent().getStringExtra("userId");
        userName = getIntent().getStringExtra("userName");
        phone = getIntent().getStringExtra("phone");
        creatTime = getIntent().getLongExtra("creatTime", 0);
        flag = getIntent().getBooleanExtra("flag", false);
        isChangFanDian = getIntent().getBooleanExtra("isChangFanDian", true);
        userType = getIntent().getIntExtra("userType", 0);
        isFind = getIntent().getIntExtra("isFind", 0);
        parentUserId = getIntent().getStringExtra("parentUserId");
        findUserId = getIntent().getStringExtra("findUserId");
        initView();
        mAdapter = new RebateDeatailAdapter(detailList);
        mListView.setAdapter(mAdapter);
        addData();
        addListener();
    }

    private void initView() {
        mActivity_rebate_detail = (LinearLayout) findViewById(R.id.activity_rebate_detail);
        mTitleBar = (com.daxiang.lottery.view.TitleBar) findViewById(R.id.rebate_detail_titlebar);

        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        mTotale_money = (TextView) findViewById(R.id.totale_money);
        mTotale_buy = (TextView) findViewById(R.id.totale_buy);
        mUser_name = (TextView) findViewById(R.id.user_name);
        mFandian = (TextView) findViewById(R.id.fandian);
        mCreat_time = (TextView) findViewById(R.id.creat_time);
        mStart_time = (TextView) findViewById(R.id.start_time);
        mEnd_time = (TextView) findViewById(R.id.end_time);
        mTv_search = (TextView) findViewById(R.id.tv_search);
        mText_nodata = (TextView) findViewById(R.id.text_nodata);
        mLl_Red = (LinearLayout) findViewById(R.id.ll_red);
        mLl_yongjin = (LinearLayout) findViewById(R.id.yongjin);
        mText_yongjin = (TextView) findViewById(R.id.yongjin_money);
        mListView = (BillListView) findViewById(R.id.lv_rebate_detail);
        mNoData = (TextView) findViewById(R.id.text_nodata);
        mStart_time.setText(timeFormat(startTime));
        mEnd_time.setText(timeFormat(endTime));
        if (flag) {
            mTitleBar.setTitle("佣金查询");
            mLl_Red.setVisibility(View.GONE);
            mLl_yongjin.setVisibility(View.VISIBLE);
        } else {
            mTitleBar.setTitle(TextUtils.isEmpty(userName) ? "返利明细" : userName);
            mLl_Red.setVisibility(View.VISIBLE);
            mLl_yongjin.setVisibility(View.GONE);
            if (isChangFanDian)//只有对直接才能设置返点
                mFandian.setVisibility(View.VISIBLE);
            else mFandian.setVisibility(View.GONE);
            mTitleBar.mTextRegister.setText("下线");
            if (userType == 1) {//大户才显示下线
                mTitleBar.mTextRegister.setVisibility(View.VISIBLE);
            } else {
                mTitleBar.mTextRegister.setVisibility(View.GONE);
            }
        }

    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //点击返点
        mTitleBar.mTextRegister.setOnClickListener(DownLineListener);
        mFandian.setOnClickListener(FandianListener);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                detailList.clear();
                pageStart = 1;
                addData();
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageStart < totalPage) {
                    pageStart++;
                    addData();
                } else {
                    mRefresh.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(mContext, "已经是最后一页", Toast.LENGTH_SHORT).show();
                            mRefresh.finishLoadMore();
                        }
                    }, 1000);
                }
            }
        });
        //开始时间
        mStart_time.setOnClickListener(StartTimeListener);
        //结束时间
        mEnd_time.setOnClickListener(EndTimeListener);
        //点击搜索
        mTv_search.setOnClickListener(SearchListener);
    }

    View.OnClickListener StartTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
// TODO Auto-generated method stub
            ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(mContext);
            String string = mStart_time.getText().toString();
            String[] split = string.split("\\-");
            mChangeBirthDialog.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            mChangeBirthDialog.show();
            mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {

                @Override
                public void onClick(String year, String month, String day) {
                    String monthS;
                    String dayS;
                    if (month.length() < 2) monthS = "0" + month;
                    else monthS = month;
                    if (day.length() < 2) dayS = "0" + day;
                    else dayS = day;
                    String date = year + "-" + monthS + "-" + dayS;
                    mStart_time.setText(date);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        startTime = dateFormat.parse(date).getTime() / 1000;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    View.OnClickListener EndTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(mContext);
            String string = mEnd_time.getText().toString();
            String[] split = string.split("\\-");
            mChangeBirthDialog.setDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            mChangeBirthDialog.show();
            mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {

                @Override
                public void onClick(String year, String month, String day) {
                    String monthS;
                    String dayS;
                    if (month.length() < 2) monthS = "0" + month;
                    else monthS = month;
                    if (day.length() < 2) dayS = "0" + day;
                    else dayS = day;
                    String date = year + "-" + monthS + "-" + dayS;
                    mEnd_time.setText(date);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    long currentTime = System.currentTimeMillis();
                    String currentDate = dateFormat.format(currentTime);
                    try {
                        //如果选择的截止日期和当前的日期一样，那么就要已当前的时间点为准。
                        if (currentDate.equals(date)) {
                            endTime = currentTime / 1000;
                        } else
                            endTime = dateFormat.parse(date).getTime() / 1000;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    View.OnClickListener SearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            detailList.clear();
            pageStart = 1;
            addData();
        }
    };
    View.OnClickListener FandianListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, FanDianActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        }
    };
    View.OnClickListener DownLineListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isFind == 1) {
                Intent intent = new Intent(mContext, DownLineActivity.class);
                intent.putExtra("findUserId", userId);
                startActivity(intent);
            } else {
                HintDialogUtils2 hintDialogUtils2 = new HintDialogUtils2(mContext);
                hintDialogUtils2.setMessage("继续查看？");
                hintDialogUtils2.setCancel("立即取消", new DialogHintInterface() {
                    @Override
                    public void callBack(View view) {

                    }
                });
                hintDialogUtils2.setConfirm("联系客服", new DialogHintInterface() {
                    @Override
                    public void callBack(View view) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:021-61600381"));
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    }
                });
            }

        }
    };

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(this);
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("userId", userId);
        params.putString("parentUserId", parentUserId);
        params.putString("pageStart", pageStart + "");
        params.putString("pageSize", pageSize + "");
        if (startTime > endTime) {
            params.putString("startTime", endTime + "000");
            params.putString("endTime", startTime + "000");
        } else {
            params.putString("startTime", startTime + "000");
            params.putString("endTime", endTime + "000");
        }
        mHttp.post(Url.REBATE_DETAIL_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                RebateDetailData rebateDetailData = gson.fromJson(result, RebateDetailData.class);
                if (rebateDetailData.getCode() == 0) {
                    mNoData.setVisibility(View.GONE);

                    if (flag) {
                        String totalFanDian = rebateDetailData.getData().getItems().getTotalFanDian();
                        mText_yongjin.setText(TextUtils.isEmpty(totalFanDian) ? "" : totalFanDian);
                    } else setTopView(rebateDetailData);
                    List<RebateDetailData.DataBean.ItemsBean.DetailBean> data = rebateDetailData.getData().getItems().getDetail();
                    detailList.addAll(data);
                    if (detailList.size() == 0) mNoData.setVisibility(View.VISIBLE);
                    else mNoData.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext, rebateDetailData.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                mNoData.setVisibility(View.VISIBLE);
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
            }
        });
    }

    private void setTopView(RebateDetailData rebateDetailData) {
        RebateDetailData.DataBean.ItemsBean items = rebateDetailData.getData().getItems();
        totalPage = items.getPageNum();
        mTotale_money.setText(TextUtils.isEmpty(items.getTotalFanDian()) ? "--" : items.getTotalFanDian());
        mTotale_buy.setText(TextUtils.isEmpty(items.getTotalTrade()) ? "--" : items.getTotalTrade());
        mUser_name.setText(TextUtils.isEmpty(phone) ? "--" : phone);
        mCreat_time.setText(timeFormat(creatTime / 1000));
    }

    //时间格式转换
    private String timeFormat(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(time * 1000);
        return format;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
