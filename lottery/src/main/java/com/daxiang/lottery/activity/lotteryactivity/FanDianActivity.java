package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.RebateGroupData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.JsonToMapUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FanDianActivity extends AppCompatActivity {
    private TitleBar mTitlerBar;
    private ListView mListView;
    private SmartRefreshLayout mRefresh;

    private Context mContext;
    private RebateAdapter mAdapter;
    // private ArrayList<JsonData> data = new ArrayList<>();
    private List<RebateGroupData.DataBean.ItemsBean> items = new ArrayList<>();
    private HashMap<String, String> map = new HashMap<>();
    private HttpInterface2 mHttp;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_dian);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        mContext = this;
        userId = getIntent().getStringExtra("userId");
        initView();
        addData();
        addListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initView() {
        mTitlerBar = (TitleBar) findViewById(R.id.fanddian_titlebar);
        mTitlerBar.setTitle("返点");
        mTitlerBar.setTitleVisibility(true);
        mTitlerBar.setImageTitleVisibility(false);
        mTitlerBar.mTextRegister.setText("保存");
        mTitlerBar.mTextRegister.setVisibility(View.VISIBLE);
        mListView = (ListView) findViewById(R.id.listview);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        mHttp = new HttpUtils2(mContext);
    }

    private void addListener() {
        mTitlerBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlerBar.mTextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.size() == 0) {
                    return;
                }
                final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
                StringBuilder stringBuilder = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String value = entry.getValue();
                    stringBuilder.append(entry.getKey() + ":0:" + value + ",");
                }
                Bundle params = new Bundle();
                params.putString("token", LotteryApp.token);
                params.putString("userId", userId);
                params.putString("rebateRatePairs", stringBuilder.toString());
                params.putString("remarks", "所有采种返点比率");
                params.putString("timeStamp", System.currentTimeMillis() + "");
                mHttp.post(Url.UPDATE_REBATE_URL, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        loadingDialogUtils.setDimiss();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError() {
                        loadingDialogUtils.setDimiss();
                    }
                });
            }
        });
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addData();
            }
        });
    }

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
//首先先获取返点组的信息
        Bundle params1 = new Bundle();
        params1.putString("token", LotteryApp.token);
        params1.putString("timeStamp", System.currentTimeMillis() + "");
        mHttp.post(Url.FANDIANS_URL, params1, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                RebateGroupData rebateGroupData = gson.fromJson(result, RebateGroupData.class);
                int code = rebateGroupData.getCode();
                String msg = rebateGroupData.getMsg();
                if (code == 0) {
                    items = rebateGroupData.getData().getItems();
                    //获取下线的返点信息
                    userRebateData(loadingDialogUtils);
                } else {
                    loadingDialogUtils.setDimiss();
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
                loadingDialogUtils.setDimiss();
            }
        });
    }

    HashMap<String, String> maps = new HashMap<>();

    private void userRebateData(final LoadingDialogUtils loadingDialogUtils) {
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("userId", userId);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttp.post(Url.MY_REBATE_URL, params, new JsonInterface() {

            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (code == 0) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        // data = JsonToMapUtils.jsonToList(jsonObject1);
                        maps = JsonToMapUtils.jsonToMap(jsonObject1);
                        mAdapter = new RebateAdapter();
                        mListView.setAdapter(mAdapter);
                        //mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    class RebateAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.item_rebate_edit, null);
            final TextView mLotcode = (TextView) convertView.findViewById(R.id.rebate_lotcode);
            EditText mRatio = (EditText) convertView.findViewById(R.id.rebate_ratio);
            RebateGroupData.DataBean.ItemsBean itemsBean = items.get(position);
            final String tradeTypeId = itemsBean.getTradeTypeId();
            mLotcode.setText(LotteryTypes.getTypes(tradeTypeId));
            /*
            * 判断用户的返点信息中是否有这个彩种的返点。
            * 如果有就显示，没有的话就显示0.0，这样方便修改各个彩种的返点
            * */
            if (maps.containsKey(tradeTypeId)) {
                DecimalFormat df = new DecimalFormat("0.0");
                String ratio = df.format(Double.parseDouble(maps.get(tradeTypeId)) * 100);
                mRatio.setText(ratio);
            } else {
                mRatio.setText("0");
            }

            mRatio.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s)) {
                        String temp = s.toString();
                        int posDot = temp.indexOf(".");
                        if (posDot > 0) {
                            if (temp.length() - posDot - 1 > 1) {
                                s.delete(posDot + 2, posDot + 3);
                            }
                        }
                        double val = Double.parseDouble(s.toString());
                        String format = new DecimalFormat("0.0").format(val);
                        BigDecimal bigDecimal = new BigDecimal(format);
                        BigDecimal bigDecimal1 = new BigDecimal(100);
                        String v = bigDecimal.divide(bigDecimal1, 3, BigDecimal.ROUND_HALF_UP).toString();
                        map.put(tradeTypeId, v);
                    }

                }
            });
            return convertView;
        }
    }
}
