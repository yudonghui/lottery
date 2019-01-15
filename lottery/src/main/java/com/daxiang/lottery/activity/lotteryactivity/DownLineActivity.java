package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.DownLineAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.DownLineData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.BillListView;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class DownLineActivity extends AppCompatActivity {
    private EditText mEditSearch;
    private BillListView mListView;
    private NoDataView mNoData;
    private TextView mTextearch;
    // private TextView mDownline;
    // private TextView mSales;
    private Context mContext;
    private TitleBar mTitleBar;
    private SmartRefreshLayout mRefresh;

    private int pageStart = 1;
    private int pageSize = 20;
    private int totalPage;
    private DownLineAdapter mAdapter;
    private TextView mTotalNum;
    private ArrayList<DownLineData.DataBeanX.ItemsBean.DataBean> itemsBeen = new ArrayList<>();
    private String findUserId;
    private int isFind = 0;//是否允许继续查下线 0不允许, 1允许
    private boolean isChangFanDian = true;//是否可以修改返点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_line);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        mContext = this;
        findUserId = getIntent().getStringExtra("findUserId");
        if (TextUtils.isEmpty(findUserId))
            findUserId = LotteryApp.uid;
        initView();
        mAdapter = new DownLineAdapter(itemsBeen);
        mListView.setAdapter(mAdapter);
        addData();
        addListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.downline_titlebar);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle("我的下线");

        //mTitleBar.mTextRegister.setVisibility(View.VISIBLE);
        mTitleBar.mTextRegister.setText("添加下线");

        mTitleBar.setFocusable(true);
        mTitleBar.setFocusableInTouchMode(true);//这三个设置是为了刚进入这个界面的时候不是显示最下面。
        mTitleBar.requestFocus();//参考网址http://blog.csdn.net/jifashihan/article/details/51918345
        mEditSearch = (EditText) findViewById(R.id.et_search);
        mTextearch = (TextView) findViewById(R.id.search);
        mListView = (BillListView) findViewById(R.id.lv_downline);
        mTotalNum = (TextView) findViewById(R.id.totalnum);
        // mDownline = (TextView) findViewById(R.id.downline);
        //mSales = (TextView) findViewById(R.id.sales);
        mNoData = (NoDataView) findViewById(R.id.no_data);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
    }

    private String searchContent;//搜索内容


    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //添加下线
        mTitleBar.mTextRegister.setOnClickListener(AddDownLineListener);
        //搜索
        mTextearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContent = mEditSearch.getText().toString();
                itemsBeen.clear();
                pageStart = 1;
                addData();
            }
        });
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                itemsBeen.clear();
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*int userType = itemsBeen.get(position).getUserType();//1 大户，0 散户，2 代理
                //  if (userType == 1) {//是大户
                if (isDownLine && isFind == 1&&userType==1) {//查看下线，并且支持查看下线，
                    userIdMap.put(itemsBeen.get(position).getUserId() + "", findUserId);
                    findUserId = itemsBeen.get(position).getUserId() + "";
                    String userName = itemsBeen.get(position).getUserName();
                    mTitleBar.setTitle(userName + "的下线");
                    itemsBeen.clear();
                    pageStart = 1;
                    flag = false;//每次点击条目都要设置成返回不finish
                    addData();
                } else if (isDownLine && isFind == 0&&userType==1) {//查看下线，并且不支持查看下线
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
                }else if (isDownLine&&userType==0){
                    Toast.makeText(mContext, "已经到最底部！", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(mContext, RebateDetailActivity.class);
                    intent.putExtra("userId", itemsBeen.get(position).getUserId());
                    intent.putExtra("userName", itemsBeen.get(position).getUserName());
                    intent.putExtra("creatTime", itemsBeen.get(position).getCreateTime());
                    intent.putExtra("phone", itemsBeen.get(position).getMobile());
                    //标志位
                    intent.putExtra("flag", false);
                    intent.putExtra("isChangFanDian", isChangFanDian);
                    startActivity(intent);
                }*/
                Intent intent = new Intent(mContext, RebateDetailActivity.class);
                intent.putExtra("userId", itemsBeen.get(position).getUserId());
                intent.putExtra("userName", itemsBeen.get(position).getUserName());
                intent.putExtra("creatTime", itemsBeen.get(position).getCreateTime());
                intent.putExtra("phone", itemsBeen.get(position).getMobile());
                intent.putExtra("userType", itemsBeen.get(position).getUserType());
                intent.putExtra("parentUserId", "" + itemsBeen.get(position).getParentUserId());

                intent.putExtra("isFind", isFind);
                //标志位
                intent.putExtra("flag", false);
                intent.putExtra("isChangFanDian", isChangFanDian);
                startActivity(intent);
             /*   } else {//散户，没有销量统计，同时没有下线。
                    Toast.makeText(mContext, "已经到最底部！", Toast.LENGTH_SHORT).show();
                }*/

            }
        });
    }

    View.OnClickListener AddDownLineListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, AddDownLineActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, 22);
        }
    };

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
        params.putString("findUserId", findUserId);
        params.putString("pageStart", pageStart + "");
        params.putString("pageSize", pageSize + "");
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("userType", "1");
        params.putString("queryChildLevelKey", "query_child_level");
        if (!TextUtils.isEmpty(searchContent))
            params.putString("keyWord", searchContent);
        mHttp.post(Url.DOWNLINE_USERS_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                searchContent = "";
                Gson gson = new Gson();
                DownLineData downLineData = gson.fromJson(result, DownLineData.class);
                if (downLineData.getCode() == 0) {
                    mNoData.setVisibility(View.GONE);
                    //itemsBeen.clear();
                    DownLineData.DataBeanX.ItemsBean items = downLineData.getData().getItems();
                    totalPage = items.getPages();
                    isFind = items.getIsFind();
                    List<DownLineData.DataBeanX.ItemsBean.DataBean> data = items.getData();
                    if (data != null && data.size() > 0) {
                        String title = data.get(0).getParentUserName();
                        long parentUserId = items.getData().get(0).getParentUserId();
                        if (LotteryApp.uid.equals(parentUserId + "")) {
                            mTitleBar.setTitle("我的下线");
                            isChangFanDian = true;//可以修改返点
                        } else {
                            mTitleBar.setTitle(title + "的下线");
                            isChangFanDian = false;//不可以修改返点
                        }
                        mTotalNum.setText(items.getTotals() + "人");
                        itemsBeen.addAll(items.getData());
                        findUserId = itemsBeen.get(0).getParentUserId() + "";
                    }
                    if (itemsBeen.size() == 0) mNoData.setVisibility(View.VISIBLE);
                    else mNoData.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();

                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(mContext, downLineData.getMsg(), Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 33) {
            itemsBeen.clear();
            pageStart = 1;
            addData();
        }

    }
}
