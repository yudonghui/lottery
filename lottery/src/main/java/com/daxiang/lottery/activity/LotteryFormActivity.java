package com.daxiang.lottery.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.DragGridAdapter;
import com.daxiang.lottery.adapter.LotteryFormAdapter;
import com.daxiang.lottery.common.IntentSkip;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.HomeLotteryData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.VersionInfo;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.DragGridView;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.daxiang.lottery.fragment.HomeFragment.Lottery_DLT;
import static com.daxiang.lottery.fragment.HomeFragment.Lottery_QLC;
import static com.daxiang.lottery.fragment.HomeFragment.Lottery_QXC;
import static com.daxiang.lottery.fragment.HomeFragment.Lottery_SSQ;
import static com.umeng.socialize.utils.ContextUtil.getContext;

public class LotteryFormActivity extends BaseNoTitleActivity {
    private DragGridView mGridView;
    private TitleBar mTitleBar;
    List<HomeLotteryData.DataBean.Item1Bean> mHomeLotteryList;
    List<HomeLotteryData.DataBean.Item2Bean> awardPoolList;
    private LotteryFormAdapter mAdapter;
    private SharedPreferences.Editor spEditor;
    private StringBuilder lotCodeStr;
    private String originLotCode;//原来的彩种的顺序

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_form);
        lotCodeStr = new StringBuilder();
        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        spEditor = sp.edit();
        originLotCode = sp.getString("lotCodeStr", "");
        lotCodeStr.append(originLotCode);

        mGridView = (DragGridView) findViewById(R.id.gridView);
        mTitleBar = (TitleBar) findViewById(R.id.titlbar);
        mTitleBar.setTitle("更多彩种");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);

        mHomeLotteryList = new ArrayList<>();
        awardPoolList = new ArrayList<>();
        mAdapter = new LotteryFormAdapter(mHomeLotteryList);
        mGridView.setAdapter(mAdapter);
        mAdapter.setOnChangeListener(GridViewChangListener);


        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBack();
            }
        });

       /* mTitleBar.mTextRegister.setVisibility(View.VISIBLE);
        mTitleBar.mTextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spEditor.putString("lotCodeStr", "");
                spEditor.commit();
            }
        });*/
        addData();

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentSkip intentSkip = new IntentSkip();
                intentSkip.skipLotcode(mContext, mHomeLotteryList.get(position).getLotCode());
            }
        });
    }

    private void setBack() {
        lotCodeStr.delete(0, lotCodeStr.length());
        for (int i = 0; i < mHomeLotteryList.size(); i++) {
            HomeLotteryData.DataBean.Item1Bean item1Bean = mHomeLotteryList.get(i);
            lotCodeStr.append(item1Bean.getLotCode() + ":");
            Log.e(i + "位置：", item1Bean.getLotCode());
        }
        Log.e("位置顺序：", String.valueOf(lotCodeStr));
        //spEditor.putString("lotCodeStr","");
        spEditor.putString("lotCodeStr", String.valueOf(lotCodeStr));
        spEditor.commit();
        if (!(originLotCode != null && originLotCode.equals(String.valueOf(lotCodeStr)))) {
            Intent intent = new Intent();
            setResult(2222, intent);
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    private List<HomeLotteryData.DataBean.Item1Bean> mShengyu = new ArrayList<>();

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(this);
        HttpInterface2 mHttp = new HttpUtils2(this);
        //gridview加载数据
        Bundle params = new Bundle();
        params.putString("type", "android");
        params.putString("version", VersionInfo.getAppVersionName(getContext()));
        mHttp.get(Url.HOME_LOTTERY_URL, params, new JsonInterface() {


            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                if (result != null && result.length() != 0) {
                    Gson gson = new Gson();
                    final HomeLotteryData homeLotteryData = gson.fromJson(result, HomeLotteryData.class);
                    if (homeLotteryData.getCode() == 0) {
                        //将请求的数据进行保存，在没有网络的时候用
                        SharedPreferences sp = getSharedPreferences("flag", Context.MODE_PRIVATE);
                        SharedPreferences.Editor mEdit = sp.edit();
                        mEdit.putString("json", result);
                        mEdit.commit();
                        mShengyu.clear();
                        mHomeLotteryList.clear();
                        awardPoolList.clear();
                        List<HomeLotteryData.DataBean.Item1Bean> item1 = homeLotteryData.getData().getItem1();
                        SharedPreferences userinfoSp = mContext.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                        String lotCodeStr = userinfoSp.getString("lotCodeStr", "");
                        if (!TextUtils.isEmpty(lotCodeStr)) {
                            String[] split = lotCodeStr.split("\\:");
                            if (split.length == item1.size()) {
                                for (int j = 0; j < split.length; j++) {
                                    for (int k = 0; k < item1.size(); k++) {
                                        HomeLotteryData.DataBean.Item1Bean item1Bean = item1.get(k);
                                        String lotCode = item1Bean.getLotCode();
                                        if (lotCode.equals(split[j])) {
                                            mHomeLotteryList.add(item1Bean);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                mHomeLotteryList.addAll(item1);
                            }
                        } else {
                            mHomeLotteryList.addAll(item1);
                        }
                        awardPoolList = homeLotteryData.getData().getItem2();
                        for (int i = 0; i < awardPoolList.size(); i++) {
                            String seLotid = awardPoolList.get(i).getLotCode();
                            String awardPool = awardPoolList.get(i).getAwardPool();
                            switch (seLotid) {
                                case "51":
                                    Lottery_SSQ = awardPool;
                                    break;
                                case "23529":
                                    Lottery_DLT = awardPool;
                                    break;
                                case "10022":
                                    Lottery_QXC = awardPool;
                                    break;
                                case "23528":
                                    Lottery_QLC = awardPool;
                                    break;
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {

                    }
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    public boolean useLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    DragGridAdapter.OnChanageListener GridViewChangListener = new DragGridAdapter.OnChanageListener() {

        @Override
        public void onChange(int from, int to) {
          /*  lotCodeStr.delete(0, lotCodeStr.length());
            for (int i = 0; i < mHomeLotteryList.size(); i++) {
                HomeLotteryData.DataBean.Item1Bean item1Bean = mHomeLotteryList.get(i);
                lotCodeStr.append(item1Bean.getLotCode() + ":");
                Log.e(i + "位置：", item1Bean.getLotCode());
            }
            Log.e("位置顺序：", String.valueOf(lotCodeStr));
            spEditor.putString("lotCodeStr", String.valueOf(lotCodeStr));
            spEditor.commit();*/
        }
    };
}
