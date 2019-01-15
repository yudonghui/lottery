package com.daxiang.lottery.forum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.jcadapter.JclqHhMatchAdapter;
import com.daxiang.lottery.adapter.jcadapter.JclqMatchAdapter;
import com.daxiang.lottery.adapter.jcadapter.JclqOtherAdapter;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.OnClickJclqListener;
import com.daxiang.lottery.cxinterface.PlayMethodSelector;
import com.daxiang.lottery.dialog.PlayMethodDialog;
import com.daxiang.lottery.entity.JclqData;
import com.daxiang.lottery.forum.base.BaseMvpActivity;
import com.daxiang.lottery.utils.BunchMethod;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.NetworkUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.view.JcTitleBar;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.autotextview.AutofitTextView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daxiang.lottery.constant.LotteryTypes.lotcode;

public class JclqRecommendActivity extends BaseMvpActivity {
    private LinearLayout mClear;
    private AutofitTextView mRemark;
    private TextView mMinGames;
    private TextView mEnsure;
    private JcTitleBar mTitleBar;
    private NoDataView mNoData;
    //日期
    private SmartRefreshLayout mRefresh;
    private ExpandableListView mListView;
    private PlayMethodDialog mPlayMethodDialog;

    //玩法
    private int playMethod;
    BaseExpandableListAdapter mJczq1Adapter;
    private boolean mPlayMethodBunchs = true;
    private boolean siftFlag = true;
    HttpInterface2 mHttpInterface;
    private int displayWidth;

    @Override
    public int getInflateId() {
        return R.layout.activity_jclq_recommend;
    }

    public HashMap<String, ArrayList<JclqData.DataBean>> map = new HashMap<>();
    public ArrayList<ArrayList<JclqData.DataBean>> mListData = new ArrayList<>();
    //用于筛选
    public ArrayList<ArrayList<JclqData.DataBean>> mLists = new ArrayList<>();
    //选择的场次
    HashMap<JclqData.DataBean, HashMap<String, String>> mChoosedContentMap = new HashMap<>();
    HashMap<JclqData.DataBean, HashMap<String, String>> mChoosedMap = new HashMap<>();

    public void setData(int playMethod, boolean mPlayMethodBunchs) {
        this.playMethod = playMethod;
        this.mPlayMethodBunchs = mPlayMethodBunchs;
        map.clear();
        mListData.clear();
        mChoosedContentMap.clear();
        selectAdapter();
        addData();
    }

    @Override
    public void init() {
        mTitleBar = (JcTitleBar) findViewById(R.id.jcTitleBar);
        mTitleBar.mTitlebarSift.setVisibility(View.GONE);
        mListView = (ExpandableListView) findViewById(R.id.lv_jczq);
        mNoData = (NoDataView) findViewById(R.id.no_data);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        mListView.setGroupIndicator(null);
        mClear = (LinearLayout) findViewById(R.id.clear);
        mRemark = (AutofitTextView) findViewById(R.id.remarkjc);
        mMinGames = (TextView) findViewById(R.id.min_games);
        mEnsure = (TextView) findViewById(R.id.ensure);

        if (DateFormtUtils.isTimeRange(false)) {//售票期间
            String string = getString(R.string.buy_jl_time_remark);
            mRemark.setText(TextUtils.isEmpty(string) ? "" : string);
        } else {//竞彩官网没有开售
            String string = getString(R.string.nobuy_time_remark);
            mRemark.setText(TextUtils.isEmpty(string) ? "" : string);
        }
        mChoosedMap = (HashMap<JclqData.DataBean, HashMap<String, String>>) getIntent().getSerializableExtra("mChoosedContentMap");
        selectAdapter();
    }

    @Override
    public void addListener() {
        mPlayMethodDialog = new PlayMethodDialog(this, "43", mPlayMethodBunchs, PlayMethodSelectorListener);
        mTitleBar.mPlayMethodTitleBar.setOnClickListener(playMethodClick);
        //点击确定
        mEnsure.setOnClickListener(EnsureBtnClick);
        //清空
        mClear.setOnClickListener(ClearClick);
        //刷新数据
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                map.clear();
                mListData.clear();
                mChoosedContentMap.clear();
                if (mChoosedMap != null)
                    mChoosedMap.clear();
                addData();
            }
        });
    }

    View.OnClickListener playMethodClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPlayMethodDialog.show(mTitleBar.mPlayMethodTitleBar);
        }
    };
    View.OnClickListener ClearClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mChoosedContentMap.clear();
            setStake();
            mJczq1Adapter.notifyDataSetChanged();

        }
    };
    PlayMethodSelector PlayMethodSelectorListener = new PlayMethodSelector() {
        @Override
        public void playMethod(int playMethod, boolean mPlayMethodBunch) {
            //mPlayMethodBunchs为true是串关，否则为单关
            mPlayMethodBunchs = mPlayMethodBunch;
            //竞彩篮球
            switch (playMethod) {
                case 1:
                    //胜平负
                    if (mPlayMethodBunchs) {
                        mTitleBar.mTextTitlebar.setText("胜负");
                    } else {
                        mTitleBar.mTextTitlebar.setText("胜负(单关)");
                    }
                    setData(1, mPlayMethodBunchs);
                    break;
                case 2:
                    if (mPlayMethodBunchs) {
                        mTitleBar.mTextTitlebar.setText("让分胜负");
                    } else {
                        mTitleBar.mTextTitlebar.setText("让分胜负(单关)");
                    }
                    setData(2, mPlayMethodBunchs);
                    break;
                case 3:
                    if (mPlayMethodBunchs) {
                        mTitleBar.mTextTitlebar.setText("大小分");
                    } else {
                        mTitleBar.mTextTitlebar.setText("大小分(单关)");
                    }
                    setData(3, mPlayMethodBunchs);
                    break;
                case 4:
                    if (mPlayMethodBunchs) {
                        mTitleBar.mTextTitlebar.setText("胜分差");
                    } else {
                        mTitleBar.mTextTitlebar.setText("胜分差(单关)");
                    }
                    setData(4, mPlayMethodBunchs);
                    break;
                case 6:
                    mTitleBar.mTextTitlebar.setText("混合过关");
                    setData(6, mPlayMethodBunchs);
                    break;
            }
        }
    };
    View.OnClickListener EnsureBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mChoosedContentMap == null || mChoosedContentMap.size() == 0) {
                Toast.makeText(mContext, "请选择比赛", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectMap", mChoosedContentMap);
            intent.putExtras(bundle);
            setResult(33333, intent);
            finish();
        }
    };

    @Override
    public void addData() {
        siftFlag = true;
        if (!NetworkUtils.isNetworkAvailable(mContext)) {
            HintDialogUtils.setHintDialog(mContext);
            HintDialogUtils.setMessage("您的网络未连接，请连接后重试！");
            mRefresh.finishRefresh();
            mNoData.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            //mLoadingDialog.dismiss();
            return;
        }
        mHttpInterface=new HttpUtils2(mContext);
        final Bundle params = new Bundle();
        params.putString("lotCode", "43");
        params.putString("status", "100");
        mHttpInterface.get(Url.JCZQ_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                JclqData jclqData = gson.fromJson(result, JclqData.class);
                if (jclqData.getCode() == 0) {
                    List<JclqData.DataBean> data = jclqData.getData();
                    //用于排序
                    ArrayList<String> orderList = new ArrayList<String>();
                    for (int i = 0; i < data.size(); i++) {
                        ArrayList<JclqData.DataBean> list;
                        if (map.containsKey(data.get(i).getIssue())) {
                            list = map.get(data.get(i).getIssue());
                        } else {
                            list = new ArrayList<>();
                        }
                        list.add(data.get(i));
                        map.put(data.get(i).getIssue(), list);
                        if (!orderList.contains(data.get(i).getIssue())) {
                            orderList.add(data.get(i).getIssue());
                        }
                    }
                    for (int i = 0; i < orderList.size(); i++) {
                        mListData.add(map.get(orderList.get(i)));
                    }
                    mLists.clear();
                    mLists.addAll(mListData);
                    //如果为单关需要循环看一下比赛是否支持单关。将不支持的移除
                    deletenNoExist();
                    if (mListData != null && mListData.size() != 0) {
                        mListView.setVisibility(View.VISIBLE);
                        mNoData.setVisibility(View.GONE);
                    } else {
                        mListView.setVisibility(View.GONE);
                        mNoData.setVisibility(View.VISIBLE);
                    }
                    if (mChoosedMap != null) {
                        for (Map.Entry<JclqData.DataBean, HashMap<String, String>> entry : mChoosedMap.entrySet()) {
                            JclqData.DataBean key = entry.getKey();
                            HashMap<String, String> value = entry.getValue();
                            for (int i = 0; i < data.size(); i++) {
                                JclqData.DataBean dataBean = data.get(i);
                                if (key.getSession().equals(dataBean.getSession())) {
                                    mChoosedContentMap.put(dataBean, value);
                                }
                            }
                        }
                        setStake();
                    }
                    mJczq1Adapter.notifyDataSetChanged();
                    for (int i = 0; i < mListData.size(); i++) {
                        mListView.expandGroup(i);
                    }
                } else {
                    mListView.setVisibility(View.GONE);
                    mNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
            }
        });
    }

    private void deletenNoExist() {
        if (!mPlayMethodBunchs) {
            if (playMethod == LotteryTypes.SPF) {
                ArrayList<ArrayList<JclqData.DataBean>> bb = new ArrayList<>();
                for (int i = 0; i < mListData.size(); i++) {
                    ArrayList<JclqData.DataBean> aa = new ArrayList<>();
                    for (int j = 0; j < mListData.get(i).size(); j++) {
                        int spfFu = mListData.get(i).get(j).getSfDg();
                        if (spfFu == 0) {
                            aa.add(mListData.get(i).get(j));
                            //mListData.get(i).remove(j);
                        }
                    }
                    for (int m = 0; m < aa.size(); m++) {
                        mListData.get(i).remove(aa.get(m));
                    }
                    // Log.e("mListData.get(i).size()", mListData.get(i).size() + "");
                    if (mListData.get(i).size() == 0) {
                        bb.add(mListData.get(i));
                    }
                }
                for (int n = 0; n < bb.size(); n++) {
                    mListData.remove(bb.get(n));
                }
            } else if (playMethod == LotteryTypes.RQSPF) {
                ArrayList<ArrayList<JclqData.DataBean>> bb = new ArrayList<>();
                for (int i = 0; i < mListData.size(); i++) {
                    ArrayList<JclqData.DataBean> aa = new ArrayList<>();
                    for (int j = 0; j < mListData.get(i).size(); j++) {
                        int rqSpfFu = mListData.get(i).get(j).getRfsfDg();
                        if (rqSpfFu == 0) {
                            aa.add(mListData.get(i).get(j));
                            //mListData.get(i).remove(j);
                        }
                    }
                    for (int m = 0; m < aa.size(); m++) {
                        mListData.get(i).remove(aa.get(m));
                    }
                    Log.e("mListData.get(i).size()", mListData.get(i).size() + "");
                    if (mListData.get(i).size() == 0) {
                        bb.add(mListData.get(i));
                    }
                }
                for (int n = 0; n < bb.size(); n++) {
                    mListData.remove(bb.get(n));
                }
            } else if (playMethod == LotteryTypes.CBF) {
                ArrayList<ArrayList<JclqData.DataBean>> bb = new ArrayList<>();
                for (int i = 0; i < mListData.size(); i++) {
                    ArrayList<JclqData.DataBean> aa = new ArrayList<>();
                    for (int j = 0; j < mListData.get(i).size(); j++) {
                        int spfFu = mListData.get(i).get(j).getDxfDg();
                        if (spfFu == 0) {
                            aa.add(mListData.get(i).get(j));
                            //mListData.get(i).remove(j);
                        }
                    }
                    for (int m = 0; m < aa.size(); m++) {
                        mListData.get(i).remove(aa.get(m));
                    }
                    // Log.e("mListData.get(i).size()", mListData.get(i).size() + "");
                    if (mListData.get(i).size() == 0) {
                        bb.add(mListData.get(i));
                    }
                }
                for (int n = 0; n < bb.size(); n++) {
                    mListData.remove(bb.get(n));
                }
            }
        }
    }

    private void selectAdapter() {

        if (playMethod == LotteryTypes.HH) {
            setHhMatchAdapter();
        } else if (playMethod == LotteryTypes.SPF || playMethod == LotteryTypes.RQSPF || playMethod == LotteryTypes.CBF) {

            setMatchAdapter();
        } else {
            setOtherAdapter();
        }
        for (int i = 0; i < mListData.size(); i++) {
            mListView.expandGroup(i);
        }
    }

    //混合过关适配器
    private void setHhMatchAdapter() {
        mJczq1Adapter = new JclqHhMatchAdapter();
        mListView.setAdapter(mJczq1Adapter);
        ((JclqHhMatchAdapter) mJczq1Adapter).setData(this, mListData, lotcode, new OnClickJclqListener() {
            @Override
            public void onClickJcListener(HashMap<JclqData.DataBean, HashMap<String, String>> clickMap) {
                //每次点击都会回调过来点击的条目信息
                mChoosedContentMap = clickMap;
                setStake();
            }
        });
    }

    //让分胜负和胜负 大小分 过关适配器
    private void setMatchAdapter() {
        mJczq1Adapter = new JclqMatchAdapter();
        mListView.setAdapter(mJczq1Adapter);

        ((JclqMatchAdapter) mJczq1Adapter).setData(this, mListData, mPlayMethodBunchs, playMethod, new OnClickJclqListener() {
            @Override
            public void onClickJcListener(HashMap<JclqData.DataBean, HashMap<String, String>> clickMap) {
                mChoosedContentMap = clickMap;
                setStake();
            }
        });
    }

    //半全场，进球数，比分过关适配器
    private void setOtherAdapter() {
        mJczq1Adapter = new JclqOtherAdapter();
        mListView.setAdapter(mJczq1Adapter);
        ((JclqOtherAdapter) mJczq1Adapter).setData(this, mListData, mPlayMethodBunchs, playMethod, new OnClickJclqListener() {
            @Override
            public void onClickJcListener(HashMap<JclqData.DataBean, HashMap<String, String>> clickMap) {
                mChoosedContentMap = clickMap;
                setStake();
            }
        });
    }

    private void setStake() {
        //串关 至少选择两场比赛，单关 至少选择一场比赛
        if (mPlayMethodBunchs) {
            if (mChoosedContentMap.size() == 1) {
                boolean playMethodLqBoolean = BunchMethod.getPlayMethodLqBoolean(mChoosedContentMap);
                if (playMethodLqBoolean) {
                    mMinGames.setText("已选" + mChoosedContentMap.size() + "场");
                } else {
                    mMinGames.setText("请至少选择两场比赛");
                }
            } else if (mChoosedContentMap.size() >= 2) {
                mMinGames.setText("已选" + mChoosedContentMap.size() + "场");
            } else {
                mMinGames.setText("请至少选择两场比赛");
            }
        } else {
            if (mChoosedContentMap.size() >= 1) {
                mMinGames.setText("已选" + mChoosedContentMap.size() + "场");
            } else {
                mMinGames.setText("请至少选择一场比赛");
            }
        }

    }

}
