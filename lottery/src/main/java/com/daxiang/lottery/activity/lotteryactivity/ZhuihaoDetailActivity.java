package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.ZhuihaoDetailAdapter;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.ZhuiHaoBean;
import com.daxiang.lottery.entity.ZhuihaoDetailBean;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.view.LotteryView;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ZhuihaoDetailActivity extends AppCompatActivity {
    private Context mContext;
    private TitleBar mTitleBar;
    private ImageView mHeader;
    private TextView mLotcode;
    private TextView mTime;
    private TextView mTotaleIssue;
    private TextView mOpenIssue;
    private TextView mTotalMoney;
    private TextView mWinMoney;
    private TextView mDate;
    private TextView mTotaleIssue2;
    private TextView mOpenIssue2;
    private LotteryView mWinNum;
    private LinearLayout mLlStop;
    private TextView mRemark;
    private com.daxiang.lottery.view.BillListView mListView;
    private TextView mLookMore;
    private TextView mOrderCode;
    private TextView mCreatTime;
    private TextView mStopBtn;


    private ZhuiHaoBean.DataBean.ListBean listBean;
    private ZhuihaoDetailAdapter mAdapter;
    private int pn = 1;
    private int ps = 5;
    List<ZhuihaoDetailBean.DataBean.ListBean> mList = new ArrayList<>();
    private String totalPrice;//每一期投注的金额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuihao_detail);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        mContext = this;
        Intent intent = getIntent();
        listBean = (ZhuiHaoBean.DataBean.ListBean) intent.getSerializableExtra("listBean");
        initView();
        setView();
        mAdapter = new ZhuihaoDetailAdapter(mList);
        mListView.setAdapter(mAdapter);
        addData();
        addListener();
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlebar_detail);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("追号详情");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setFocusable(true);
        mTitleBar.setFocusableInTouchMode(true);//这三个设置是为了刚进入这个界面的时候不是显示最下面。
        mTitleBar.requestFocus();//参考网址http://blog.csdn.net/jifashihan/article/details/51918345

        mHeader = (ImageView) findViewById(R.id.header);
        mLotcode = (TextView) findViewById(R.id.lotcode);
        mTime = (TextView) findViewById(R.id.time);
        mTotaleIssue = (TextView) findViewById(R.id.totaleIssue);
        mOpenIssue = (TextView) findViewById(R.id.openIssue);
        mTotalMoney = (TextView) findViewById(R.id.totalMoney);
        mWinMoney = (TextView) findViewById(R.id.winMoney);
        mDate = (TextView) findViewById(R.id.date);
        mTotaleIssue2 = (TextView) findViewById(R.id.totaleIssue2);
        mOpenIssue2 = (TextView) findViewById(R.id.openIssue2);
        mWinNum = (LotteryView) findViewById(R.id.winNum);
        mRemark = (TextView) findViewById(R.id.remark);
        mListView = (com.daxiang.lottery.view.BillListView) findViewById(R.id.listView);
        mLookMore = (TextView) findViewById(R.id.lookMore);
        mOrderCode = (TextView) findViewById(R.id.orderCode);
        mCreatTime = (TextView) findViewById(R.id.creatTime);
        mStopBtn = (TextView) findViewById(R.id.stopBtn);
        mLlStop = (LinearLayout) findViewById(R.id.ll_stop);
        if ("200".equals(listBean.getZhuihaoStatus())) mLlStop.setVisibility(View.VISIBLE);
    }

    private void setView() {
        if (listBean == null) return;
        String lotCode = listBean.getLotCode();
        String createTime = listBean.getCreateTime();
        String totalNum = listBean.getTotalNum();//总期数
        String boughtNum = listBean.getBoughtNum();//购买的期数
        String totalMoney = listBean.getTotalMoney();//投注总金额
        String winMoney = listBean.getWinMoney();//中奖金额
        String betContent = listBean.getBetContent();//投注号码
        String remark = listBean.getRemark();//追加备注
        String orderCode = listBean.getOrderCode();//方案号
        mLotcode.setText(LotteryTypes.getTypes(lotCode));
        mHeader.setImageResource(LotteryTypes.getImgId(lotCode));
        if (!TextUtils.isEmpty(createTime)) {
            String[] split = createTime.split("\\ ");
            if (split.length > 1) {
                mTime.setText(split[0] + "  共追");
                mDate.setText(split[0]);
                mCreatTime.setText(createTime);
            }
        }
        mTotaleIssue.setText(TextUtils.isEmpty(totalNum) ? "--" : totalNum);
        mTotaleIssue2.setText(TextUtils.isEmpty(totalNum) ? "--" : totalNum);
        mOpenIssue.setText(TextUtils.isEmpty(boughtNum) ? "--" : boughtNum);
        mOpenIssue2.setText(TextUtils.isEmpty(boughtNum) ? "--" : boughtNum);
        mTotalMoney.setText(TextUtils.isEmpty(totalMoney) ? "--" : StringUtil.getString(totalMoney));
        mWinMoney.setText(TextUtils.isEmpty(winMoney) ? "--" : StringUtil.getString(winMoney));
        //mWinNum.setAwardNum("", betContent, Integer.parseInt(lotCode));
        mWinNum.setContent(lotCode, betContent);
        mRemark.setText(TextUtils.isEmpty(remark) ? "" : remark);
        mOrderCode.setText(TextUtils.isEmpty(orderCode) ? "" : orderCode);
    }

    private void addData() {
        String zhuihaoId = listBean.getZhuihaoId();
        if (TextUtils.isEmpty(zhuihaoId)) return;
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("zhuihaoId", zhuihaoId);
        params.putString("userId", LotteryApp.uid);
        params.putString("pn", pn + "");
        params.putString("ps", ps + "");
        mHttp.get(Url.ZHUIHAO_DETAIL_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                ZhuihaoDetailBean zhuihaoDetailBean = gson.fromJson(result, ZhuihaoDetailBean.class);
                int code = zhuihaoDetailBean.getCode();
                String msg = zhuihaoDetailBean.getMsg();
                if (code == 0) {
                    int totalRecords = zhuihaoDetailBean.getData().getTotalRecords();
                    if (totalRecords > 5 && !isLookMore) mLookMore.setVisibility(View.VISIBLE);
                    else mLookMore.setVisibility(View.GONE);
                    mList.addAll(zhuihaoDetailBean.getData().getList());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLookMore.setOnClickListener(LookMoreListener);
        mStopBtn.setOnClickListener(StopListener);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ZhuihaoDetailBean.DataBean.ListBean itemsBean = mList.get(position);
                totalPrice = itemsBean.getTotalPrice();
                try {
                    Intent intent = new Intent(mContext, OrderFormDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("orderType", 6);//6是追号子订单
                    bundle.putString("orderCode", itemsBean.getOrderCode());
                    bundle.putString("orderId", itemsBean.getOrderId());
                    bundle.putString("status", LotteryTypes.getStatus(itemsBean.getOrderStatus()));
                    bundle.putString("allmoney", totalPrice);
                    bundle.putString("buycontent", listBean.getBetContent());
                    bundle.putString("lotCode", listBean.getLotCode());
                    // String issue = itemsBean.getLotIssue().substring(0, 7);
                    bundle.putString("issue", itemsBean.getIssueNo());

                    String createTime = listBean.getCreateTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long time = sdf.parse(createTime).getTime();
                    bundle.putLong("creattime", time);
                    bundle.putString("margin", itemsBean.getAftertaxBonus());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isLookMore = false;
    View.OnClickListener LookMoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isLookMore = true;
            ps = 50;
            mList.clear();
            addData();
        }
    };
    View.OnClickListener StopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String zhuihaoId = listBean.getZhuihaoId();
            if (TextUtils.isEmpty(zhuihaoId)) return;
            HttpInterface2 mHttp = new HttpUtils2(mContext);
            Bundle params = new Bundle();
            params.putString("token", LotteryApp.token);
            params.putString("zhuihaoId", zhuihaoId);
            params.putString("timeStamp", System.currentTimeMillis() + "");
            mHttp.postH(Url.ZHUIHAO_STOP_URL, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        if (code == 0) mLlStop.setVisibility(View.GONE);
                        String msg = jsonObject.getString("msg");
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError() {

                }
            });
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
