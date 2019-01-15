package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.JsonData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.JsonToMapUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyRebateActivity extends AppCompatActivity {
    private ListView mListView;
    private TitleBar mTitleBar;
    private Context mContext;
    private NoDataView mNoData;
    private RebateAdapter mAdapter;
    private ArrayList<JsonData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rebate);
        mContext = this;
        initView();
        addData();

        addListener();
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.rebate_titlebar);
        mTitleBar.setTitle("我的返点");
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mListView = (ListView) findViewById(R.id.lv_rebate);
        mNoData = (NoDataView) findViewById(R.id.no_data);
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
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
                        data = JsonToMapUtils.jsonToList(jsonObject1);
                        if (data == null || data.size() == 0) mNoData.setVisibility(View.VISIBLE);
                        else mNoData.setVisibility(View.GONE);
                        mAdapter = new RebateAdapter();
                        mListView.setAdapter(mAdapter);
                        //mAdapter.notifyDataSetChanged();
                    } else {
                        mNoData.setVisibility(View.VISIBLE);
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mNoData.setVisibility(View.VISIBLE);
            }
        });
    }

    class RebateAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.item_rebate, null);
            TextView mLotcode = (TextView) convertView.findViewById(R.id.rebate_lotcode);
            TextView mRatio = (TextView) convertView.findViewById(R.id.rebate_ratio);
            JsonData jsonData = data.get(position);
            mLotcode.setText(LotteryTypes.getTypes(jsonData.getKey()));
            DecimalFormat df = new DecimalFormat(".0");
            String ratio = df.format(Double.parseDouble(jsonData.getValue()) * 100) + "%";
            mRatio.setText(ratio);
            return convertView;
        }
    }
}
