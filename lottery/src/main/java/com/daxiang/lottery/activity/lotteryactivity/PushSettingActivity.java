package com.daxiang.lottery.activity.lotteryactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseNoTitleActivity;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.PushSettingBean;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;

import java.util.List;

public class PushSettingActivity extends BaseNoTitleActivity {
    private TitleBar mTitleBar;
    private ImageView mGod_notice;
    private ImageView mWin_notice;
    private ImageView mTikuan_notice;
    private ImageView mResult_notice;
    private ImageView mLottery_result_notice;
    private LinearLayout mLl_number;
    private ImageView mDlt_notice;
    private ImageView mSsq_notice;
    private ImageView mQxc_notice;
    private ImageView mQlc_notice;
    private ImageView mFc3d_notice;
    private ImageView mPl3_notice;
    private ImageView mPl5_notice;
    private ImageView mLzc_notice;
    private ImageView mHot_notice;
    private ImageView mAd_notice;
    private ImageView mCancel_notice;
    private ImageView mDisturb_notice;
    private NetWorkData mNetWorkData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_setting);
        mNetWorkData = new NetWorkData(this);
        initView();
        addListener();
        addData();
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlBar);
        mTitleBar.setTitle("推送设置");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        mGod_notice = (ImageView) findViewById(R.id.god_notice);
        mWin_notice = (ImageView) findViewById(R.id.win_notice);
        mTikuan_notice = (ImageView) findViewById(R.id.tikuan_notice);
        mResult_notice = (ImageView) findViewById(R.id.result_notice);
        mLottery_result_notice = (ImageView) findViewById(R.id.lottery_result_notice);
        mLl_number = (LinearLayout) findViewById(R.id.ll_number);
        mDlt_notice = (ImageView) findViewById(R.id.dlt_notice);
        mSsq_notice = (ImageView) findViewById(R.id.ssq_notice);
        mQxc_notice = (ImageView) findViewById(R.id.qxc_notice);
        mQlc_notice = (ImageView) findViewById(R.id.qlc_notice);
        mFc3d_notice = (ImageView) findViewById(R.id.fc3d_notice);
        mPl3_notice = (ImageView) findViewById(R.id.pl3_notice);
        mPl5_notice = (ImageView) findViewById(R.id.pl5_notice);
        mLzc_notice = (ImageView) findViewById(R.id.lzc_notice);
        mHot_notice = (ImageView) findViewById(R.id.hot_notice);
        mAd_notice = (ImageView) findViewById(R.id.ad_notice);
        mCancel_notice = (ImageView) findViewById(R.id.cancel_notice);
        mDisturb_notice = (ImageView) findViewById(R.id.disturb_notice);

        mGod_notice.setTag(0);
        mWin_notice.setTag(0);
        mTikuan_notice.setTag(0);
        mResult_notice.setTag(0);
        mLottery_result_notice.setTag(0);
        mDlt_notice.setTag(0);
        mSsq_notice.setTag(0);
        mQxc_notice.setTag(0);
        mQlc_notice.setTag(0);
        mFc3d_notice.setTag(0);
        mPl3_notice.setTag(0);
        mPl5_notice.setTag(0);
        mLzc_notice.setTag(0);
        mHot_notice.setTag(0);
        mAd_notice.setTag(0);
        mCancel_notice.setTag(0);
        mDisturb_notice.setTag(0);
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLottery_result_notice.setOnClickListener(NumberListener);
        mGod_notice.setOnClickListener(NoticeListener);
        mWin_notice.setOnClickListener(NoticeListener);
        mTikuan_notice.setOnClickListener(NoticeListener);
        mResult_notice.setOnClickListener(NoticeListener);
        mDisturb_notice.setOnClickListener(NoticeListener);

        mDlt_notice.setOnClickListener(NoticeListener);
        mSsq_notice.setOnClickListener(NoticeListener);
        mQxc_notice.setOnClickListener(NoticeListener);
        mQlc_notice.setOnClickListener(NoticeListener);
        mFc3d_notice.setOnClickListener(NoticeListener);
        mPl3_notice.setOnClickListener(NoticeListener);
        mPl5_notice.setOnClickListener(NoticeListener);
        mLzc_notice.setOnClickListener(NoticeListener);

        mHot_notice.setOnClickListener(NoticeListener);
        mAd_notice.setOnClickListener(NoticeListener);
        mCancel_notice.setOnClickListener(NoticeListener);

    }

    View.OnClickListener NoticeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.god_notice://大神通知
                    int tagGod = (int) mGod_notice.getTag();
                    if (tagGod == 1) {//说明之前是选中的
                        mGod_notice.setImageResource(R.drawable.btn_off);
                        mGod_notice.setTag(0);
                        setPush("recommend", "recommend", 0);
                    } else {
                        mGod_notice.setImageResource(R.drawable.btn_on);
                        mGod_notice.setTag(1);
                        setPush("recommend", "recommend", 1);
                    }

                    break;
                case R.id.win_notice://中奖通知
                    int tagWin = (int) mWin_notice.getTag();
                    if (tagWin == 1) {//说明之前是选中的
                        mWin_notice.setImageResource(R.drawable.btn_off);
                        mWin_notice.setTag(0);
                        setPush("win", "win", 0);
                    } else {
                        mWin_notice.setImageResource(R.drawable.btn_on);
                        mWin_notice.setTag(1);
                        setPush("win", "win", 1);
                    }
                    break;
                case R.id.tikuan_notice://提款通知
                    int tagTikuan = (int) mTikuan_notice.getTag();
                    if (tagTikuan == 1) {//说明之前是选中的
                        mTikuan_notice.setImageResource(R.drawable.btn_off);
                        mTikuan_notice.setTag(0);
                        setPush("withdraw", "withdraw", 0);
                    } else {
                        mTikuan_notice.setImageResource(R.drawable.btn_on);
                        mTikuan_notice.setTag(1);
                        setPush("withdraw", "withdraw", 1);
                    }
                    break;
                case R.id.result_notice://赛果通知
                    int tagResult = (int) mResult_notice.getTag();
                    if (tagResult == 1) {//说明之前是选中的
                        mResult_notice.setImageResource(R.drawable.btn_off);
                        mResult_notice.setTag(0);
                        setPush("result", "result", 0);
                    } else {
                        mResult_notice.setImageResource(R.drawable.btn_on);
                        mResult_notice.setTag(1);
                        setPush("result", "result", 1);
                    }
                    break;
                case R.id.disturb_notice://勿扰模式
                    int tagDis = (int) mDisturb_notice.getTag();
                    if (tagDis == 1) {//说明之前是选中的
                        mDisturb_notice.setImageResource(R.drawable.btn_off);
                        mDisturb_notice.setTag(0);
                        setPush("silence", "silence", 0);
                    } else {
                        mDisturb_notice.setImageResource(R.drawable.btn_on);
                        mDisturb_notice.setTag(1);
                        setPush("silence", "silence", 1);
                    }
                    break;
                case R.id.dlt_notice:
                    int tagDlt = (int) mDlt_notice.getTag();
                    if (tagDlt == 1) {//说明之前是选中的
                        mDlt_notice.setImageResource(R.drawable.btn_off);
                        mDlt_notice.setTag(0);
                        setPush("prize", "dlt", 0);
                    } else {
                        mDlt_notice.setImageResource(R.drawable.btn_on);
                        mDlt_notice.setTag(1);
                        setPush("prize", "dlt", 1);
                    }
                    break;
                case R.id.ssq_notice:
                    int tagSsq = (int) mSsq_notice.getTag();
                    if (tagSsq == 1) {//说明之前是选中的
                        mSsq_notice.setImageResource(R.drawable.btn_off);
                        mSsq_notice.setTag(0);
                        setPush("prize", "ssq", 0);
                    } else {
                        mSsq_notice.setImageResource(R.drawable.btn_on);
                        mSsq_notice.setTag(1);
                        setPush("prize", "ssq", 1);
                    }
                    break;
                case R.id.qxc_notice:
                    int tagQxc = (int) mQxc_notice.getTag();
                    if (tagQxc == 1) {//说明之前是选中的
                        mQxc_notice.setImageResource(R.drawable.btn_off);
                        mQxc_notice.setTag(0);
                        setPush("prize", "qxc", 0);
                    } else {
                        mQxc_notice.setImageResource(R.drawable.btn_on);
                        mQxc_notice.setTag(1);
                        setPush("prize", "qxc", 1);
                    }
                    break;
                case R.id.qlc_notice:
                    int tagQlc = (int) mQlc_notice.getTag();
                    if (tagQlc == 1) {//说明之前是选中的
                        mQlc_notice.setImageResource(R.drawable.btn_off);
                        mQlc_notice.setTag(0);
                        setPush("prize", "qlc", 0);
                    } else {
                        mQlc_notice.setImageResource(R.drawable.btn_on);
                        mQlc_notice.setTag(1);
                        setPush("prize", "qlc", 1);
                    }
                    break;
                case R.id.fc3d_notice:
                    int tagFc3d = (int) mFc3d_notice.getTag();
                    if (tagFc3d == 1) {//说明之前是选中的
                        mFc3d_notice.setImageResource(R.drawable.btn_off);
                        mFc3d_notice.setTag(0);
                        setPush("prize", "fc3d", 0);
                    } else {
                        mFc3d_notice.setImageResource(R.drawable.btn_on);
                        mFc3d_notice.setTag(1);
                        setPush("prize", "fc3d", 1);
                    }
                    break;
                case R.id.pl3_notice:
                    int tagPl3 = (int) mPl3_notice.getTag();
                    if (tagPl3 == 1) {//说明之前是选中的
                        mPl3_notice.setImageResource(R.drawable.btn_off);
                        mPl3_notice.setTag(0);
                        setPush("prize", "pl3", 0);
                    } else {
                        mPl3_notice.setImageResource(R.drawable.btn_on);
                        mPl3_notice.setTag(1);
                        setPush("prize", "pl3", 1);
                    }
                    break;
                case R.id.pl5_notice:
                    int tagPl5 = (int) mPl5_notice.getTag();
                    if (tagPl5 == 1) {//说明之前是选中的
                        mPl5_notice.setImageResource(R.drawable.btn_off);
                        mPl5_notice.setTag(0);
                        setPush("prize", "pl5", 0);
                    } else {
                        mPl5_notice.setImageResource(R.drawable.btn_on);
                        mPl5_notice.setTag(1);
                        setPush("prize", "pl5", 1);
                    }
                    break;
                case R.id.lzc_notice:
                    int tagLzc = (int) mLzc_notice.getTag();
                    if (tagLzc == 1) {//说明之前是选中的
                        mLzc_notice.setImageResource(R.drawable.btn_off);
                        mLzc_notice.setTag(0);
                        setPush("prize", "lzc", 0);
                    } else {
                        mLzc_notice.setImageResource(R.drawable.btn_on);
                        mLzc_notice.setTag(1);
                        setPush("prize", "lzc", 1);
                    }
                    break;
                case R.id.hot_notice:
                    int tagHot = (int) mHot_notice.getTag();
                    if (tagHot == 1) {//说明之前是选中的
                        mHot_notice.setImageResource(R.drawable.btn_off);
                        mHot_notice.setTag(0);
                        setPush("hot", "hot", 0);
                    } else {
                        mHot_notice.setImageResource(R.drawable.btn_on);
                        mHot_notice.setTag(1);
                        setPush("hot", "hot", 1);
                    }
                    break;
                case R.id.ad_notice:
                    int tagAd = (int) mAd_notice.getTag();
                    if (tagAd == 1) {//说明之前是选中的
                        mAd_notice.setImageResource(R.drawable.btn_off);
                        mAd_notice.setTag(0);
                        setPush("notice", "notice", 0);
                    } else {
                        mAd_notice.setImageResource(R.drawable.btn_on);
                        mAd_notice.setTag(1);
                        setPush("notice", "notice", 1);
                    }
                    break;
                case R.id.cancel_notice:
                    int tagCancel = (int) mCancel_notice.getTag();
                    if (tagCancel == 1) {//说明之前是选中的
                        mCancel_notice.setImageResource(R.drawable.btn_off);
                        mCancel_notice.setTag(0);
                        setPush("cancel", "cancel", 0);
                    } else {
                        mCancel_notice.setImageResource(R.drawable.btn_on);
                        mCancel_notice.setTag(1);
                        setPush("cancel", "cancel", 1);
                    }
                    break;
            }
            setVisible();
        }
    };

    private void setPush(String pushType, String pushItem, int status) {//配置推送
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("userId", LotteryApp.uid);
        params.putString("pushType", pushType);//大分类
        params.putString("pushItem", pushItem);//小分类
        params.putString("status", status + "");
        mHttp.postH(Url.PUSH_SETTING, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mNetWorkData.addDeleteTag();
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    private void addData() {
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("userId", LotteryApp.uid);
        mHttp.get(Url.PUSH_LIST, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                PushSettingBean pushSettingBean = new Gson().fromJson(result, PushSettingBean.class);
                int code = pushSettingBean.getCode();
                if (code == 0) {
                    List<PushSettingBean.DataBean> data = pushSettingBean.getData();
                    if (data == null) return;
                    for (int i = 0; i < data.size(); i++) {
                        PushSettingBean.DataBean dataBean = data.get(i);
                        String pushType = dataBean.getPushType();
                        String pushItem = dataBean.getPushItem();
                        int status = dataBean.getStatus();
                        switch (pushType) {
                            case "recommend":
                                mGod_notice.setTag(status);
                                if (status == 1) {
                                    mGod_notice.setImageResource(R.drawable.btn_on);
                                } else {
                                    mGod_notice.setImageResource(R.drawable.btn_off);
                                }
                                break;
                            case "win":
                                mWin_notice.setTag(status);
                                if (status == 1) {
                                    mWin_notice.setImageResource(R.drawable.btn_on);
                                } else {
                                    mWin_notice.setImageResource(R.drawable.btn_off);
                                }
                                break;
                            case "withdraw"://提款通知
                                mTikuan_notice.setTag(status);
                                if (status == 1) {
                                    mTikuan_notice.setImageResource(R.drawable.btn_on);
                                } else {
                                    mTikuan_notice.setImageResource(R.drawable.btn_off);
                                }
                                break;
                            case "result"://赛果通知
                                mResult_notice.setTag(status);
                                if (status == 1) {
                                    mResult_notice.setImageResource(R.drawable.btn_on);
                                } else {
                                    mResult_notice.setImageResource(R.drawable.btn_off);
                                }
                                break;
                            case "hot"://热门比赛
                                mHot_notice.setTag(status);
                                if (status == 1) {
                                    mHot_notice.setImageResource(R.drawable.btn_on);
                                } else {
                                    mHot_notice.setImageResource(R.drawable.btn_off);
                                }
                                break;
                            case "notice"://网站通知
                                mAd_notice.setTag(status);
                                if (status == 1) {
                                    mAd_notice.setImageResource(R.drawable.btn_on);
                                } else {
                                    mAd_notice.setImageResource(R.drawable.btn_off);
                                }
                                break;
                            case "cancel"://比赛取消
                                mCancel_notice.setTag(status);
                                if (status == 1) {
                                    mCancel_notice.setImageResource(R.drawable.btn_on);
                                } else {
                                    mCancel_notice.setImageResource(R.drawable.btn_off);
                                }
                                break;
                            case "silence"://勿扰模式
                                mDisturb_notice.setTag(status);
                                if (status == 1) {
                                    mDisturb_notice.setImageResource(R.drawable.btn_on);
                                } else {
                                    mDisturb_notice.setImageResource(R.drawable.btn_off);
                                }
                                break;
                            case "prize"://开奖通知
                                switch (pushItem) {
                                    case "dlt":
                                        mDlt_notice.setTag(status);
                                        if (status == 1) {
                                            mDlt_notice.setImageResource(R.drawable.btn_on);
                                        } else {
                                            mDlt_notice.setImageResource(R.drawable.btn_off);
                                        }
                                        break;
                                    case "ssq":
                                        mSsq_notice.setTag(status);
                                        if (status == 1) {
                                            mSsq_notice.setImageResource(R.drawable.btn_on);
                                        } else {
                                            mSsq_notice.setImageResource(R.drawable.btn_off);
                                        }
                                        break;
                                    case "qxc":
                                        mQxc_notice.setTag(status);
                                        if (status == 1) {
                                            mQxc_notice.setImageResource(R.drawable.btn_on);
                                        } else {
                                            mQxc_notice.setImageResource(R.drawable.btn_off);
                                        }
                                        break;
                                    case "qlc":
                                        mQlc_notice.setTag(status);
                                        if (status == 1) {
                                            mQlc_notice.setImageResource(R.drawable.btn_on);
                                        } else {
                                            mQlc_notice.setImageResource(R.drawable.btn_off);
                                        }
                                        break;
                                    case "fc3d":
                                        mFc3d_notice.setTag(status);
                                        if (status == 1) {
                                            mFc3d_notice.setImageResource(R.drawable.btn_on);
                                        } else {
                                            mFc3d_notice.setImageResource(R.drawable.btn_off);
                                        }
                                        break;
                                    case "pl3":
                                        mPl3_notice.setTag(status);
                                        if (status == 1) {
                                            mPl3_notice.setImageResource(R.drawable.btn_on);
                                        } else {
                                            mPl3_notice.setImageResource(R.drawable.btn_off);
                                        }
                                        break;
                                    case "pl5":
                                        mPl5_notice.setTag(status);
                                        if (status == 1) {
                                            mPl5_notice.setImageResource(R.drawable.btn_on);
                                        } else {
                                            mPl5_notice.setImageResource(R.drawable.btn_off);
                                        }
                                        break;
                                    case "lzc":
                                        mLzc_notice.setTag(status);
                                        if (status == 1) {
                                            mLzc_notice.setImageResource(R.drawable.btn_on);
                                        } else {
                                            mLzc_notice.setImageResource(R.drawable.btn_off);
                                        }
                                        break;
                                }
                                break;
                        }
                    }
                    setVisible();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void setVisible() {
        int tag1 = (int) mDlt_notice.getTag();
        int tag2 = (int) mSsq_notice.getTag();
        int tag3 = (int) mQxc_notice.getTag();
        int tag4 = (int) mQlc_notice.getTag();
        int tag5 = (int) mPl3_notice.getTag();
        int tag6 = (int) mPl5_notice.getTag();
        int tag7 = (int) mLzc_notice.getTag();

        int total1 = tag1 + tag2 + tag3 + tag4 + tag5 + tag6 + tag7;
        if (total1 > 0) {
            mLottery_result_notice.setImageResource(R.drawable.btn_on);
            mLottery_result_notice.setTag(1);
            mLl_number.setVisibility(View.VISIBLE);
        } else {
            mLottery_result_notice.setImageResource(R.drawable.btn_off);
            mLottery_result_notice.setTag(0);
            mLl_number.setVisibility(View.GONE);
        }
    }

    View.OnClickListener NumberListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) mLottery_result_notice.getTag();
            if (tag == 1) {
                mLl_number.setVisibility(View.GONE);
                mLottery_result_notice.setImageResource(R.drawable.btn_off);
                mDlt_notice.setImageResource(R.drawable.btn_off);
                mSsq_notice.setImageResource(R.drawable.btn_off);
                mQxc_notice.setImageResource(R.drawable.btn_off);
                mQlc_notice.setImageResource(R.drawable.btn_off);
                mFc3d_notice.setImageResource(R.drawable.btn_off);
                mPl3_notice.setImageResource(R.drawable.btn_off);
                mPl5_notice.setImageResource(R.drawable.btn_off);
                mLzc_notice.setImageResource(R.drawable.btn_off);

                mLottery_result_notice.setTag(0);
                mDlt_notice.setTag(0);
                mSsq_notice.setTag(0);
                mQxc_notice.setTag(0);
                mQlc_notice.setTag(0);
                mFc3d_notice.setTag(0);
                mPl3_notice.setTag(0);
                mPl5_notice.setTag(0);
                mLzc_notice.setTag(0);
                setPush("prize", "all", 0);
            } else {
                mLl_number.setVisibility(View.VISIBLE);
                mLottery_result_notice.setImageResource(R.drawable.btn_on);
                mDlt_notice.setImageResource(R.drawable.btn_on);
                mSsq_notice.setImageResource(R.drawable.btn_on);
                mQxc_notice.setImageResource(R.drawable.btn_on);
                mQlc_notice.setImageResource(R.drawable.btn_on);
                mFc3d_notice.setImageResource(R.drawable.btn_on);
                mPl3_notice.setImageResource(R.drawable.btn_on);
                mPl5_notice.setImageResource(R.drawable.btn_on);
                mLzc_notice.setImageResource(R.drawable.btn_on);

                mLottery_result_notice.setTag(1);
                mDlt_notice.setTag(1);
                mSsq_notice.setTag(1);
                mQxc_notice.setTag(1);
                mQlc_notice.setTag(1);
                mFc3d_notice.setTag(1);
                mPl3_notice.setTag(1);
                mPl5_notice.setTag(1);
                mLzc_notice.setTag(1);
                setPush("prize", "all", 1);
            }
        }
    };
}
