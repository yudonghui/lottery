package com.daxiang.lottery.score;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseNoTitleActivity;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.OddsData;
import com.daxiang.lottery.entity.OddsDetailData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OddsDetailActivity extends BaseNoTitleActivity {
    private TitleBar mTitlBar;
    private ListView mCompany_lv;
    private ListView mOdds_lv;
    private CompanyAdapter mCompanyAdapter;
    List<OddsData.DataBean.ItemsBean> mCompanyList;
    List<OddsDetailData.DataBean.ItemsBean> mOddsList = new ArrayList<>();
    private OddsDetailAdapter mOddsDetailAdapter;
    private String cId;
    private String mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odds_detail);
        cId = getIntent().getStringExtra("cId");
        mId = getIntent().getStringExtra("mId");
        mCompanyList= (List<OddsData.DataBean.ItemsBean>) getIntent().getSerializableExtra("mCompany");
        initView();
        addData();
        addListener();
        mCompanyAdapter = new CompanyAdapter(mContext,mCompanyList);
        mCompanyAdapter.setSelect(cId);
        mCompany_lv.setAdapter(mCompanyAdapter);

        mOddsDetailAdapter = new OddsDetailAdapter(mContext, mOddsList,1);
        mOdds_lv.setAdapter(mOddsDetailAdapter);
    }

    private void initView() {
        mTitlBar = (TitleBar) findViewById(R.id.titlBar);
        mTitlBar.setImageTitleVisibility(false);
        mTitlBar.setTitleVisibility(true);
        mTitlBar.setTitle("欧赔详情");
        mCompany_lv = (ListView) findViewById(R.id.company_lv);
        mOdds_lv = (ListView) findViewById(R.id.odds_lv);
    }

    private void addListener() {
        mTitlBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCompany_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OddsData.DataBean.ItemsBean r = mCompanyList.get(position);
                cId = r.getId();
                mCompanyAdapter.setSelect(cId);
                mCompanyAdapter.notifyDataSetChanged();
                addData();
            }
        });
    }

    private void addData() {
        HttpInterface2 mHttp=new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("mId",mId);
        params.putString("cId",cId);
        mHttp.get(Url.ODDS_DETAIL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                OddsDetailData oddsDetailData = gson.fromJson(result, OddsDetailData.class);
                int code = oddsDetailData.getCode();
                if (code==0){
                    mOddsList.clear();
                    OddsDetailData.DataBean data = oddsDetailData.getData();
                    List<OddsDetailData.DataBean.ItemsBean> items = data.getItems();
                    mOddsList.addAll(items);
                    mOddsDetailAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

}
