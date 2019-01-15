package com.daxiang.lottery.score;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.dialog.MatchsWheelDialog;
import com.daxiang.lottery.entity.MatchScheduleData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2018/3/28.
 */

public class MatchScheduleFragment extends Fragment {
    private View mInflate;
    private Context mContext;
    private NoDataView mNoData;
    private ListView mListView;
    private ImageView mLeft;
    private ImageView mRight;
    private TextView mLeftRound;
    private TextView mCenterRound;
    private LinearLayout mLl_center;
    private SmartRefreshLayout mRefresh;
    private TextView mRightRound;
    private int rounds = 0;
    List<MatchScheduleData.DataBean.ItemsBean.ListBean> mDataList = new ArrayList<>();
    List<String> round_list = new ArrayList<>();
    private MatchScheduleAdapter mScheduleAdapter;
    private String leagueId;
    private int roundsTotal;

    public void setData(Context mContext, String leagueId) {
        this.mContext = mContext;
        this.leagueId = leagueId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_matchs_schedule, null);
            initView();
        }
        mContext = getContext();
        mScheduleAdapter = new MatchScheduleAdapter(mContext, mDataList);
        mListView.setAdapter(mScheduleAdapter);
        addListener();
        return mInflate;
    }

    private boolean isFirst = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            addData();
        }
    }

    private void initView() {
        mNoData = (NoDataView) mInflate.findViewById(R.id.no_data);
        mLeftRound = (TextView) mInflate.findViewById(R.id.leftRound);
        mLl_center = (LinearLayout) mInflate.findViewById(R.id.ll_center);
        mCenterRound = (TextView) mInflate.findViewById(R.id.centerRound);
        mRightRound = (TextView) mInflate.findViewById(R.id.rightRound);
        mListView = (ListView) mInflate.findViewById(R.id.listView);
        mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
        mLeft = (ImageView) mInflate.findViewById(R.id.left);
        mRight = (ImageView) mInflate.findViewById(R.id.right);

    }

    private void addListener() {
        mLeft.setOnClickListener(LeftRoundListener);
        mLeftRound.setOnClickListener(LeftRoundListener);
        mLl_center.setOnClickListener(CenterRoundListener);
        mRight.setOnClickListener(RightRoundListener);
        mRightRound.setOnClickListener(RightRoundListener);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MatchScheduleData.DataBean.ItemsBean.ListBean listBean = mDataList.get(position);
                String message = listBean.getMessage();
                if ("0".equals(message)) {
                    Toast.makeText(mContext, "抱歉,该场次暂不支持查看比赛详情", Toast.LENGTH_SHORT).show();
                    return;
                }
                String match_id = listBean.getMatch_id();
                if (TextUtils.isEmpty(match_id)) {
                    return;
                }
                Intent intent = new Intent(mContext, ScoreDetailActivity.class);
                intent.putExtra("mId", match_id);
                startActivity(intent);
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
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("leagueId", leagueId);
        if (rounds != 0)
            params.putString("rounds", rounds + "");
        mHttp.get(Url.LEAGUE_SCHEDULE, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                MatchScheduleData matchScheduleData = gson.fromJson(result, MatchScheduleData.class);
                int code = matchScheduleData.getCode();
                String msg = matchScheduleData.getMsg();
                if (code == 0) {
                    mDataList.clear();
                    round_list.clear();
                    MatchScheduleData.DataBean data = matchScheduleData.getData();
                    rounds = data.getItems().getRounds();
                    round_list.addAll(data.getItems().getRound_list());
                    roundsTotal = round_list.size();
                    mDataList.addAll(data.getItems().getList());
                    setHeaderView();
                    if (mScheduleAdapter != null)
                        mScheduleAdapter.notifyDataSetChanged();
                }
                if (mDataList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else {
                    mNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                if (mNoData == null) return;
                mRefresh.finishRefresh();
                if (mDataList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else {
                    mNoData.setVisibility(View.GONE);
                }
            }
        });
    }

    View.OnClickListener LeftRoundListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            rounds = rounds - 1;
            setHeaderView();
            addData();
        }
    };
    //private MatchsWheelDialogUtils mDialogUtils;
    private MatchsWheelDialog mDialog;
    View.OnClickListener CenterRoundListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
          /*  if (mDialogUtils == null)
                mDialogUtils = new MatchsWheelDialogUtils(mContext, rounds, roundsTotal, CenterListener);
            else mDialogUtils.show(rounds);*/
            if (mDialog == null) {
                mDialog = new MatchsWheelDialog(mContext, rounds, round_list, CenterListener);
                mDialog.show(mLl_center, rounds);
            } else {
                mDialog.show(mLl_center, rounds);
            }
        }
    };
    MatchsWheelDialog.CenterClickListener CenterListener = new MatchsWheelDialog.CenterClickListener() {
        @Override
        public void onClick(int bounds) {
            rounds = bounds;
            setHeaderView();
            addData();
        }
    };
    View.OnClickListener RightRoundListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            rounds = rounds + 1;
            setHeaderView();
            addData();
        }
    };

    private void setHeaderView() {
        if ((rounds == 1 && roundsTotal == 1) || (rounds == 0 && roundsTotal == 0)) {
            mLeft.setVisibility(View.GONE);
            mLeftRound.setVisibility(View.GONE);
            mRight.setVisibility(View.GONE);
            mRightRound.setVisibility(View.GONE);
            mCenterRound.setText(round_list.get(rounds - 1));
        } else if (rounds == 1) {
            mLeft.setVisibility(View.GONE);
            mLeftRound.setVisibility(View.GONE);
            mRight.setVisibility(View.VISIBLE);
            mRightRound.setVisibility(View.VISIBLE);
            mCenterRound.setText(round_list.get(rounds - 1));
            mRightRound.setText(round_list.get(rounds));
        } else if (rounds == roundsTotal) {
            mLeft.setVisibility(View.VISIBLE);
            mLeftRound.setVisibility(View.VISIBLE);
            mRight.setVisibility(View.GONE);
            mRightRound.setVisibility(View.GONE);
            mCenterRound.setText(round_list.get(rounds - 1));
            mLeftRound.setText(round_list.get(rounds - 2));
        } else {
            mLeft.setVisibility(View.VISIBLE);
            mLeftRound.setVisibility(View.VISIBLE);
            mRight.setVisibility(View.VISIBLE);
            mRightRound.setVisibility(View.VISIBLE);
            mCenterRound.setText(round_list.get(rounds - 1));
            mLeftRound.setText(round_list.get(rounds - 2));
            mRightRound.setText(round_list.get(rounds));
        }
    }
}
