package com.daxiang.lottery.forum.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.ForecastActivity;
import com.daxiang.lottery.adapter.jcadapter.JczqHhMatchAdapter;
import com.daxiang.lottery.adapter.jcadapter.JczqMatchAdapter;
import com.daxiang.lottery.adapter.jcadapter.JczqOtherAdapter;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.OnClickJczqListener;
import com.daxiang.lottery.cxinterface.PlayMethodSelector;
import com.daxiang.lottery.cxinterface.SiftInterface;
import com.daxiang.lottery.dialog.PlayMethodDialog;
import com.daxiang.lottery.dialog.SiftDialog;
import com.daxiang.lottery.entity.JczqData;
import com.daxiang.lottery.forum.base.BaseMvpActivity;
import com.daxiang.lottery.utils.BunchMethod;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.NetworkUtils;
import com.daxiang.lottery.utils.dialogutils.DialogAnimotion;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.view.FloatViewLayout;
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

public class JczqRecommendActivity extends BaseMvpActivity {
    private LinearLayout mClear;
    private AutofitTextView mRemark;
    private TextView mMinGames;
    private TextView mEnsure;
    private ImageView mForecast;
    private FloatViewLayout mFvl;
    private JcTitleBar mTitleBar;
    private NoDataView mNoData;
    //日期
    private SmartRefreshLayout mRefresh;
    private ExpandableListView mListView;
    private PlayMethodDialog mPlayMethodDialog;
    //玩法
    private int playMethod = 6;
    BaseExpandableListAdapter mJczq1Adapter;
    private boolean mPlayMethodBunchs = true;
    private boolean siftFlag = true;
    HttpInterface2 mHttpInterface;
    private int displayWidth;

    @Override
    public int getInflateId() {
        return R.layout.activity_jczq_recommend;
    }

    public void setData(int playMethod, boolean mPlayMethodBunchs) {
        this.playMethod = playMethod;
        this.mPlayMethodBunchs = mPlayMethodBunchs;
        map.clear();
        mListData.clear();
        mChoosedContentMap.clear();
        selectAdapter();
        addData();
    }

    public HashMap<String, ArrayList<JczqData.DataBean>> map = new HashMap<>();
    public ArrayList<ArrayList<JczqData.DataBean>> mListData = new ArrayList<>();
    //用于筛选
    public ArrayList<ArrayList<JczqData.DataBean>> mLists = new ArrayList<>();
    ArrayList<String> siftList = new ArrayList<>();
    //选择的场次
    HashMap<JczqData.DataBean, HashMap<String, String>> mChoosedContentMap = new HashMap<>();
    HashMap<JczqData.DataBean, HashMap<String, String>> mChoosedMap = new HashMap<>();

    private void selectAdapter() {
        if (playMethod == LotteryTypes.HH) {
            setHhMatchAdapter();
        } else if (playMethod == LotteryTypes.SPF || playMethod == LotteryTypes.RQSPF) {
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
        mJczq1Adapter = new JczqHhMatchAdapter();
        mListView.setAdapter(mJczq1Adapter);
        ((JczqHhMatchAdapter) mJczq1Adapter).setData((Activity) mContext, mChoosedContentMap, mListData, "42", new OnClickJczqListener() {
            @Override
            public void onClickJcListener(HashMap<JczqData.DataBean, HashMap<String, String>> clickMap) {
                //每次点击都会回调过来点击的条目信息
                mChoosedContentMap = clickMap;
                setStake();
            }
        });
    }

    //让球胜平负和胜平负过关适配器
    private void setMatchAdapter() {
        mJczq1Adapter = new JczqMatchAdapter();
        mListView.setAdapter(mJczq1Adapter);

        ((JczqMatchAdapter) mJczq1Adapter).setData((Activity) mContext, mListData, mPlayMethodBunchs, playMethod, new OnClickJczqListener() {
            @Override
            public void onClickJcListener(HashMap<JczqData.DataBean, HashMap<String, String>> clickMap) {
                mChoosedContentMap = clickMap;
                setStake();
            }
        });
    }

    //半全场，进球数，比分过关适配器
    private void setOtherAdapter() {
        mJczq1Adapter = new JczqOtherAdapter();
        mListView.setAdapter(mJczq1Adapter);
        ((JczqOtherAdapter) mJczq1Adapter).setData((Activity) mContext, mListData, mPlayMethodBunchs, playMethod, new OnClickJczqListener() {
            @Override
            public void onClickJcListener(HashMap<JczqData.DataBean, HashMap<String, String>> clickMap) {
                mChoosedContentMap = clickMap;
                setStake();
            }
        });
    }

    @Override
    public void init() {
        mTitleBar = (JcTitleBar) findViewById(R.id.jcTitleBar);
        mListView = (ExpandableListView) findViewById(R.id.lv_jczq);
        mNoData = (NoDataView) findViewById(R.id.no_data);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        mListView.setGroupIndicator(null);
        mClear = (LinearLayout) findViewById(R.id.clear);
        mRemark = (AutofitTextView) findViewById(R.id.remarkjc);
        mMinGames = (TextView) findViewById(R.id.min_games);
        mEnsure = (TextView) findViewById(R.id.ensure);
        mForecast = (ImageView) findViewById(R.id.forecast);
        mFvl = (FloatViewLayout) findViewById(R.id.floatViewLayout);


        mHttpInterface = new HttpUtils2(mContext);
        if (DateFormtUtils.isTimeRange(true)) {//售票期间
            String string = mContext.getResources().getString(R.string.buy_jz_time_remark);
            String introduce = mContext.getResources().getString(R.string.jz_dian_remark);
            String remark = "1." + string + "\n2." + introduce;
            mRemark.setText(remark);
        } else {//竞彩官网没有开售
            String string = getString(R.string.nobuy_time_remark);
            String introduce = mContext.getResources().getString(R.string.jz_dian_remark);
            String remark = "1." + string + "\n2." + introduce;
            mRemark.setText(remark);
        }
        displayWidth = DisplayUtil.getDisplayWidth();
        // clearColletion();
        // mListData.clear();
        mChoosedMap = (HashMap<JczqData.DataBean, HashMap<String, String>>) getIntent().getSerializableExtra("mChoosedContentMap");
        selectAdapter();
    }

    @Override
    public void addListener() {
        mPlayMethodDialog = new PlayMethodDialog(this, "42", mPlayMethodBunchs, PlayMethodSelectorListener);
        mTitleBar.mPlayMethodTitleBar.setOnClickListener(playMethodClick);
        //点击确定
        mEnsure.setOnClickListener(EnsureBtnClick);
        //清空
        mClear.setOnClickListener(ClearClick);
        //精彩预测
        mFvl.setMove(ForecastMoveListener);
        mForecast.setOnClickListener(ForecastListener);
        //筛选
        if (mTitleBar != null && mTitleBar.mTitlebarSift != null)
            mTitleBar.mTitlebarSift.setOnClickListener(SiftClickListener);
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
        final Bundle params = new Bundle();
        params.putString("lotCode", "42");
        params.putString("status", "100");
        mHttpInterface.get(Url.JCZQ_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                //取消加载条
                //   loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                JczqData jczqData = gson.fromJson(result, JczqData.class);
                if (jczqData.getCode() == 0) {
                    List<JczqData.DataBean> data = jczqData.getData();
                    //用于排序
                    ArrayList<String> orderList = new ArrayList<String>();
                    for (int i = 0; i < data.size(); i++) {
                        ArrayList<JczqData.DataBean> list;
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
                    //看是否有数据
                    if (mListData != null && mListData.size() != 0) {
                        mListView.setVisibility(View.VISIBLE);
                        mNoData.setVisibility(View.GONE);
                    } else {
                        mListView.setVisibility(View.GONE);
                        mNoData.setVisibility(View.VISIBLE);
                    }
                    if (mChoosedMap != null) {
                        for (Map.Entry<JczqData.DataBean, HashMap<String, String>> entry : mChoosedMap.entrySet()) {
                            JczqData.DataBean key = entry.getKey();
                            HashMap<String, String> value = entry.getValue();
                            for (int i = 0; i < data.size(); i++) {
                                JczqData.DataBean dataBean = data.get(i);
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


    PlayMethodSelector PlayMethodSelectorListener = new PlayMethodSelector() {
        @Override
        public void playMethod(int playMethod, boolean mPlayMethodBunch) {
            //mPlayMethodBunchs为true是串关，否则为单关
            mPlayMethodBunchs = mPlayMethodBunch;
            switch (playMethod) {
                case 1:
                    //胜平负
                    if (mPlayMethodBunchs) {
                        mTitleBar.mTextTitlebar.setText("胜平负");
                    } else {
                        mTitleBar.mTextTitlebar.setText("胜平负(单关)");
                    }
                    setData(1, mPlayMethodBunchs);
                    break;
                case 2:
                    if (mPlayMethodBunchs) {
                        mTitleBar.mTextTitlebar.setText("让球胜平负");
                    } else {
                        mTitleBar.mTextTitlebar.setText("让球胜平负(单关)");
                    }
                    setData(2, mPlayMethodBunchs);
                    break;
                case 3:
                    if (mPlayMethodBunchs) {
                        mTitleBar.mTextTitlebar.setText("比分");
                    } else {
                        mTitleBar.mTextTitlebar.setText("比分(单关)");
                    }
                    setData(3, mPlayMethodBunchs);
                    break;
                case 4:
                    if (mPlayMethodBunchs) {
                        mTitleBar.mTextTitlebar.setText("总进球");
                    } else {
                        mTitleBar.mTextTitlebar.setText("总进球(单关)");
                    }
                    setData(4, mPlayMethodBunchs);
                    break;
                case 5:
                    if (mPlayMethodBunchs) {
                        mTitleBar.mTextTitlebar.setText("半全场");
                    } else {
                        mTitleBar.mTextTitlebar.setText("半全场(单关)");
                    }
                    setData(5, mPlayMethodBunchs);
                    break;
                case 6:
                    mTitleBar.mTextTitlebar.setText("混合过关");
                    setData(6, mPlayMethodBunchs);
                    break;
            }
        }
    };
    View.OnClickListener playMethodClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPlayMethodDialog.show(mTitleBar.mPlayMethodTitleBar);
        }
    };
    View.OnClickListener SiftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListData != null && mListData.size() != 0) {
                SiftDialog mDialog = new SiftDialog(mContext, mLists, siftList, siftFlag, new SiftInterface() {

                    @Override
                    public void siftCallBack(ArrayList<ArrayList<JczqData.DataBean>> mListData1, ArrayList<String> clickList, boolean siftFlag1) {
                        mListData.clear();
                        siftList.clear();
                        siftFlag = siftFlag1;
                        mListData.addAll(mListData1);
                        siftList.addAll(clickList);
                        mJczq1Adapter.notifyDataSetChanged();
                    }
                });
                mDialog.show();
                DialogAnimotion.setAnimotion(mDialog);
            }
        }
    };
    FloatViewLayout.MoveListener ForecastMoveListener = new FloatViewLayout.MoveListener() {
        @Override
        public void moveLeft(int left) {

            if (left < ((displayWidth / 2) - DisplayUtil.dip2px(25))) {
                mForecast.setBackgroundResource(R.mipmap.forecast_left);
            } else {
                mForecast.setBackgroundResource(R.mipmap.forecast_right);
            }
        }
    };
    View.OnClickListener ForecastListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ForecastActivity.class);
            startActivity(intent);
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

    View.OnClickListener EnsureBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mChoosedContentMap == null || mChoosedContentMap.size() == 0) {
                Toast.makeText(mContext, "请选择比赛", Toast.LENGTH_SHORT).show();
                return;
            }
            for (Map.Entry<JczqData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
                JczqData.DataBean key = entry.getKey();
                HashMap<String, String> value = entry.getValue();
                for (Map.Entry<String, String> entry2 : value.entrySet()) {
                    String key1 = entry2.getKey();
                    String value1 = entry2.getValue();
                    Log.e("选中的内容：", "key: " + key1 + "value: " + value1);
                }
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectMap", mChoosedContentMap);
            intent.putExtras(bundle);
            setResult(33333, intent);
            finish();
        }
    };

    private void setStake() {
        //串关 至少选择两场比赛，单关 至少选择一场比赛
        if (mPlayMethodBunchs) {
            if (mChoosedContentMap.size() == 1) {
                boolean playMethodBoolean = BunchMethod.getPlayMethodBoolean(mChoosedContentMap);
                if (playMethodBoolean) {
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


    private void deletenNoExist() {
        if (!mPlayMethodBunchs) {
            if (playMethod == LotteryTypes.SPF) {
                ArrayList<ArrayList<JczqData.DataBean>> bb = new ArrayList<>();
                for (int i = 0; i < mListData.size(); i++) {
                    ArrayList<JczqData.DataBean> aa = new ArrayList<>();
                    for (int j = 0; j < mListData.get(i).size(); j++) {
                        int spfFu = mListData.get(i).get(j).getSpfDg();
                        if (spfFu == 0) {
                            aa.add(mListData.get(i).get(j));
                            //mListData.get(i).remove(j);
                        }
                    }
                    for (int m = 0; m < aa.size(); m++) {
                        mListData.get(i).remove(aa.get(m));
                    }
                    // Log.e("mListData.get(i).size()", mListData.get(i).size() + "");
                    //如果这个条目里面已经没有数据，那么存起来，循环完成后再从mListData中循环移除。
                    //不能在mListData循环中直接add或者remove  mListData中的数据
                    if (mListData.get(i).size() == 0) {
                        bb.add(mListData.get(i));
                    }
                }
                for (int n = 0; n < bb.size(); n++) {
                    mListData.remove(bb.get(n));
                }
            } else if (playMethod == LotteryTypes.RQSPF) {
                ArrayList<ArrayList<JczqData.DataBean>> bb = new ArrayList<>();
                for (int i = 0; i < mListData.size(); i++) {
                    ArrayList<JczqData.DataBean> aa = new ArrayList<>();
                    for (int j = 0; j < mListData.get(i).size(); j++) {
                        int rqSpfFu = mListData.get(i).get(j).getRqspfDg();
                        if (rqSpfFu == 0) {
                            aa.add(mListData.get(i).get(j));
                            //mListData.get(i).remove(j);
                        }
                    }
                    for (int m = 0; m < aa.size(); m++) {
                        mListData.get(i).remove(aa.get(m));
                    }
                    //Log.e("mListData.get(i).size()", mListData.get(i).size() + "");
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

}
