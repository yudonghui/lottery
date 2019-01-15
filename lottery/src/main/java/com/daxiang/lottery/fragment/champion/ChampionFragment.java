package com.daxiang.lottery.fragment.champion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.common.LotCode;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.common.SpUtils;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.ChampionBean;
import com.daxiang.lottery.fragment.userfragment.BaseYuFragment;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.ToastUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Android on 2018/5/2.
 */

public class ChampionFragment extends BaseYuFragment implements View.OnLayoutChangeListener {
    private View mInflate;
    private LinearLayout mLl_root;
    private SmartRefreshLayout mRefresh;
    private LinearLayout mBottom;
    private GridView mGridView;
    private View mView;
    private LinearLayout mLl_mutile;
    private TextView mOne;
    private TextView mTwo;
    private TextView mThree;
    private TextView mFour;
    private TextView mMoney;
    private EditText mMutile;
    private TextView mForecast;
    private Button mConfirm;
    private int colorBlack;
    private int colorGray;
    private int colorWhite;
    private int colorRed;
    private Map<Integer, ChampionBean.DataBean.ItemsBean> map = new HashMap<>();
    private List<ChampionBean.DataBean.ItemsBean> mDataList = new ArrayList<>();
    private ChampionAdapter mAdapter;
    private int keyHeight;
    private InputMethodManager imm;
    private int buyPrivilege;
    private int gjState;

    public void setData(Context context) {
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_champion, null);
            SharedPreferences userinfo = getContext().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
            gjState = userinfo.getInt(LotCode.GJ_CODE, 1);
            buyPrivilege = SpUtils.getBuyPrivilege();
            init();
        }
        addListener();
        mAdapter = new ChampionAdapter();
        mGridView.setAdapter(mAdapter);
        return mInflate;
    }


    private boolean isFirst = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            if (mRefresh != null)
                mRefresh.autoRefresh();
            addData();
        }
    }

    private void init() {
        mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
        mBottom = (LinearLayout) mInflate.findViewById(R.id.bottom);
        mGridView = (GridView) mInflate.findViewById(R.id.gridView);
        mLl_root = (LinearLayout) mInflate.findViewById(R.id.ll_root);
        mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
        mGridView = (GridView) mInflate.findViewById(R.id.gridView);
        mMoney = (TextView) mInflate.findViewById(R.id.money);
        mMutile = (EditText) mInflate.findViewById(R.id.mutile);
        mMutile.setSelection(1);
        mForecast = (TextView) mInflate.findViewById(R.id.forecast);
        mConfirm = (Button) mInflate.findViewById(R.id.confirm);
        mView = mInflate.findViewById(R.id.view);
        mLl_mutile = (LinearLayout) mInflate.findViewById(R.id.ll_mutile);
        mOne = (TextView) mInflate.findViewById(R.id.one);
        mTwo = (TextView) mInflate.findViewById(R.id.two);
        mThree = (TextView) mInflate.findViewById(R.id.three);
        mFour = (TextView) mInflate.findViewById(R.id.four);

        if (LotteryApp.buyPrivilege == 2 || buyPrivilege == 1) {
            mConfirm.setText("暂停销售");
            mConfirm.setEnabled(false);
        } else {
            mConfirm.setText("立即预约");
            mConfirm.setEnabled(true);
        }


        colorBlack = getResources().getColor(R.color.deep_txt);
        colorGray = getResources().getColor(R.color.gray_txt);
        colorWhite = getResources().getColor(R.color.white);
        colorRed = getResources().getColor(R.color.red_txt);
        //获取屏幕高度
        int screenHeight = DisplayUtil.getDisplayHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private int mutile = 5;

    private void addListener() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addData();
            }
        });
        mLl_root.addOnLayoutChangeListener(this);
        mOne.setOnClickListener(MultilFastListener);
        mTwo.setOnClickListener(MultilFastListener);
        mThree.setOnClickListener(MultilFastListener);
        mFour.setOnClickListener(MultilFastListener);
        if (gjState == 0) {
            mConfirm.setText("停售");
            mConfirm.setBackgroundColor(getContext().getResources().getColor(R.color.gray_deep));
        } else {
            mConfirm.setOnClickListener(ConfirmListener);
            mConfirm.setText("确定");
            mConfirm.setBackgroundColor(getContext().getResources().getColor(R.color.red_theme));
        }
        mMutile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = mMutile.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    mutile = 0;
                } else if (Integer.parseInt(trim) > 50000) {
                    mutile = 50000;
                    mMutile.setText("50000");
                    ToastUtils.showToast(mContext, "最大输入50000");
                } else mutile = Integer.parseInt(trim);
                setView();
            }
        });
    }

    private void addData() {
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("type", "0");
        mHttp.get(Url.CHAMPION, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                ChampionBean championBean = gson.fromJson(result, ChampionBean.class);
                int code = championBean.getCode();
                if (code == 0) {
                    SharedPreferences sp = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("gj", result);
                    edit.commit();
                    mDataList.clear();
                    ChampionBean.DataBean data = championBean.getData();
                    List<ChampionBean.DataBean.ItemsBean> items = data.getItems();
                    mDataList.addAll(items);
                    if (mAdapter != null)
                        mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
            }
        });
    }

    View.OnClickListener MultilFastListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.one:
                    mMutile.setText("20");
                    break;
                case R.id.two:
                    mMutile.setText("50");
                    break;
                case R.id.three:
                    mMutile.setText("100");
                    break;
                case R.id.four:
                    mMutile.setText("200");
                    break;

            }
        }
    };
    View.OnClickListener ConfirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (LotteryApp.isLogin) {
                if (!LotteryApp.phoneFlag) {
                    HintDialogUtils.setHintDialog(mContext);
                    HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                    HintDialogUtils.setTitleVisiable(true);
                    HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                        @Override
                        public void callBack(View view) {
                            //手机号未绑定
                            if (LotteryApp.isThird) {
                                Intent intent = new Intent(mContext, PhoneIsExistActivity.class);
                                mContext.startActivity(intent);
                            } else {
                                Intent intent = new Intent(mContext, BindPhoneActivity.class);
                                intent.putExtra("isBind", false);
                                mContext.startActivity(intent);
                            }

                        }
                    });
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("GJ|");
                int i = 0;
                for (Map.Entry<Integer, ChampionBean.DataBean.ItemsBean> entry : map.entrySet()) {

                    ChampionBean.DataBean.ItemsBean value = entry.getValue();
                    String serial = value.getSerial();
                    if (!TextUtils.isEmpty(serial)) {
                        sb.append(serial.length() == 1 ? ("0" + serial) : serial);
                        if (i < map.size() - 1) {
                            sb.append(",");
                        }
                    }
                    i++;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("requestCode", 200);
                bundle.putString("buyMethod", "normal");
                bundle.putString("content", String.valueOf(sb));
                bundle.putInt("shakes", map.size());
                bundle.putInt("mMulti", mutile);
                bundle.putString("money", map.size() * mutile * 2 + "");
                bundle.putString("issue", "20180715");
                bundle.putString("lotcode", "30");
                NetWorkData netWorkData = new NetWorkData(getContext(), bundle);
                netWorkData.orderForm();
            } else {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }

        }
    };

    private void setView() {
        if (buyPrivilege == 0) return;
        if (map.size() == 0) {
            mBottom.setVisibility(View.GONE);
            imm.hideSoftInputFromWindow(mInflate.getWindowToken(), 0);
        } else mBottom.setVisibility(View.VISIBLE);
        String trim = mMutile.getText().toString().trim();
        if (!TextUtils.isEmpty(trim))
            mMutile.setSelection(trim.length());
        mMoney.setText(mutile * 2 * (map.size()) + "元");
        setSpan();
    }

    private void setSpan() {
        double maxPrize = 0;//最大赔率
        double minPrize = 100000;//最小赔率
        for (Map.Entry<Integer, ChampionBean.DataBean.ItemsBean> entry : map.entrySet()) {
            ChampionBean.DataBean.ItemsBean value = entry.getValue();
            String prize = value.getPrize();
            if (!TextUtils.isEmpty(prize)) {
                double v = Double.parseDouble(prize);
                if (v > maxPrize) {
                    maxPrize = v;
                }
                if (v < minPrize) {
                    minPrize = v;
                }
            }
        }
        String forcastMoney;
        if (minPrize == 100000 || maxPrize == 0 || mutile == 0) {
            forcastMoney = "预测奖金: 0元";
        } else if (minPrize == maxPrize) {
            forcastMoney = "预测奖金: " + String.format("%.2f", minPrize * 2 * mutile) + "元";
        } else {
            forcastMoney = "预测奖金: " + String.format("%.2f", minPrize * 2 * mutile) + "~" + String.format("%.2f", maxPrize * 2 * mutile)+ "元";
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(forcastMoney);
        for (int i = 0; i < forcastMoney.length(); i++) {
            char c = forcastMoney.charAt(i);
            if ((c >= 48 && c <= 57) || c == 46) {
                ssb.setSpan(new ForegroundColorSpan(colorRed), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        mForecast.setText(ssb);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case View.VISIBLE:
                    mLl_mutile.setVisibility(View.VISIBLE);
                    mView.setVisibility(View.VISIBLE);
                    break;
                case View.GONE:
                    mLl_mutile.setVisibility(View.GONE);
                    mView.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            handler.sendEmptyMessage(View.VISIBLE);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            handler.sendEmptyMessage(View.GONE);
        }
    }

    class ChampionAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder mViewHolder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_champion, null);
                mViewHolder = new ViewHolder();
                mViewHolder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);
                mViewHolder.mAvatar = (ImageView) convertView.findViewById(R.id.avatar);
                mViewHolder.mName = (TextView) convertView.findViewById(R.id.name);
                mViewHolder.mOdds = (TextView) convertView.findViewById(R.id.odds);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            final ChampionBean.DataBean.ItemsBean itemsBean = mDataList.get(position);
            String team_logo = itemsBean.getTeam_logo();
            String team_name = itemsBean.getTeam_name();
            final int status = itemsBean.getStatus();
            String prize = itemsBean.getPrize();

            if (!TextUtils.isEmpty(team_logo))
                HttpUtils2.display(mViewHolder.mAvatar, team_logo, R.mipmap.matchs_default_img);
            mViewHolder.mName.setText(TextUtils.isEmpty(team_name) ? "--" : team_name);
            mViewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status != 1) return;
                    if (map.containsKey(position)) {
                        mViewHolder.mOdds.setTextColor(colorGray);
                        mViewHolder.mName.setTextColor(colorBlack);
                        mViewHolder.mLinearLayout.setBackgroundResource(R.drawable.shape_white_10);
                        map.remove(position);
                    } else {
                        mViewHolder.mOdds.setTextColor(colorWhite);
                        mViewHolder.mName.setTextColor(colorWhite);
                        mViewHolder.mLinearLayout.setBackgroundResource(R.drawable.shape_red_10);
                        map.put(position, itemsBean);
                    }
                    setView();
                }
            });
            if (map.containsKey(position)) {
                mViewHolder.mOdds.setTextColor(colorWhite);
                mViewHolder.mName.setTextColor(colorWhite);
                mViewHolder.mOdds.setText(TextUtils.isEmpty(prize) ? "--" : prize);
                mViewHolder.mLinearLayout.setBackgroundResource(R.drawable.shape_red_10);
            } else {
                mViewHolder.mLinearLayout.setBackgroundResource(R.drawable.shape_white_10);
                if (status == 0) {
                    mViewHolder.mOdds.setText("停售");
                    mViewHolder.mOdds.setTextColor(colorRed);
                    mViewHolder.mName.setTextColor(getResources().getColor(R.color.gray_txt));
                } else if (status == 1) {
                    mViewHolder.mOdds.setTextColor(colorGray);
                    mViewHolder.mOdds.setText(TextUtils.isEmpty(prize) ? "--" : prize);
                    mViewHolder.mName.setTextColor(getResources().getColor(R.color.deep_txt));
                } else if (status == 2) {
                    mViewHolder.mOdds.setText("出局");
                    mViewHolder.mOdds.setTextColor(colorRed);
                    mViewHolder.mName.setTextColor(getResources().getColor(R.color.gray_txt));
                } else if (status == 3) {
                    mViewHolder.mOdds.setText("退款");
                    mViewHolder.mOdds.setTextColor(colorRed);
                    mViewHolder.mName.setTextColor(getResources().getColor(R.color.gray_txt));
                } else {
                    mViewHolder.mOdds.setText("--");
                    mViewHolder.mOdds.setTextColor(colorGray);
                    mViewHolder.mName.setTextColor(getResources().getColor(R.color.gray_txt));
                }
            }
            return convertView;
        }
    }

    class ViewHolder {
        private LinearLayout mLinearLayout;
        private ImageView mAvatar;
        private TextView mName;
        private TextView mOdds;
    }
}
