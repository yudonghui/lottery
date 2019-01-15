package com.daxiang.lottery.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.JclqData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/18 0018.
 */
public class AllPlayMethodDialogJL extends AlertDialog {


    protected JclqData.DataBean dataBean;
    Context mContext;
    protected HashMap<String, String> choosedOddMap = new HashMap<>();
    protected HashMap<String, String> hasChoosedMap;

    protected View confirmBtn;

    protected View cancelBtn;
    protected TextView popHostView;

    protected TextView popGuestView;

    protected TextView letNumberView;
    /**
     * 胜负
     */
    protected List<TextView> popSfTextViews;

    protected List<LinearLayout> popSfLayoutViews;

    protected Map<View, Integer> popSfCheckStatusMap;

    /**
     * 让分胜负
     */
    protected List<TextView> popRfsfViews;

    protected List<LinearLayout> popRfsfLayoutViews;

    protected Map<View, Integer> popRfSfCheckStatusMap;

    /**
     * 大小分
     */
    protected List<TextView> popDxfViews;

    protected List<LinearLayout> popDxfLayoutViews;

    protected Map<View, Integer> popDxfCheckStatusMap;

    /**
     * 胜分差
     */
    protected List<TextView> popSfcViews;

    protected List<LinearLayout> popSfcLayoutViews;

    protected Map<View, Integer> popSfcCheckStatusMap;

    /**
     * 胜负ids
     */
    public static int[] popSfTextViewIds = new int[]{R.id.tv_pop_sf_0, R.id.tv_pop_sf_1};

    public static int[] popSfLayoutViewIds = new int[]{R.id.ll_pop_sf_0, R.id.ll_pop_sf_1};

    protected ArrayList<String> sfDatas;

    /**
     * 让分胜负ids
     */
    public static int[] popRfsfViewIds = new int[]{R.id.tv_pop_rfsf_0, R.id.tv_pop_rfsf_1, R.id.tv_pop_rfsf_2};

    public static int[] popRfsfLayoutViewIds = new int[]{R.id.ll_pop_rfsf_0, R.id.ll_pop_rfsf_1/*, R.id.tv_rangqiu, R.id.rangQLeft, R.id.rangQRight*/};

    protected ArrayList<String> rfSfDatas;

    /**
     * 大小分ids
     */
    public static int[] popDxfViewIds = new int[]{R.id.tv_pop_dxf_0, R.id.tv_pop_dxf_1, R.id.tv_pop_dxf_2};

    public static int[] popDxfLayoutViewIds = new int[]{R.id.ll_pop_dxf_0, R.id.ll_pop_dxf_1};

    protected ArrayList<String> dxfDatas;

    /**
     * 胜分差ids
     */
    public static int[] popSfcViewIds = new int[]{R.id.tv_pop_sfc_0, R.id.tv_pop_sfc_1, R.id.tv_pop_sfc_2,
            R.id.tv_pop_sfc_3, R.id.tv_pop_sfc_4, R.id.tv_pop_sfc_5, R.id.tv_pop_sfc_6, R.id.tv_pop_sfc_7,
            R.id.tv_pop_sfc_8, R.id.tv_pop_sfc_9, R.id.tv_pop_sfc_10, R.id.tv_pop_sfc_11};

    public static int[] popSfcLayoutViewIds = new int[]{R.id.ll_pop_sfc_0, R.id.ll_pop_sfc_1, R.id.ll_pop_sfc_2,
            R.id.ll_pop_sfc_3, R.id.ll_pop_sfc_4, R.id.ll_pop_sfc_5, R.id.ll_pop_sfc_6, R.id.ll_pop_sfc_7,
            R.id.ll_pop_sfc_8, R.id.ll_pop_sfc_9, R.id.ll_pop_sfc_10, R.id.ll_pop_sfc_11};

    protected ArrayList<String> sfcDatas;

    protected LinearLayout hh_sf_ll;

    protected LinearLayout hh_rfsf_ll;

    protected LinearLayout hh_dxf_ll;

    protected LinearLayout hh_sfc_ll;

    public AllPlayMethodDialogJL(Activity activity) {
        super(activity);
        this.mContext = activity;
    }

    public AllPlayMethodDialogJL(Activity activity, JclqData.DataBean dataBean, HashMap<String, String> hasChoosedMap, OnOddsChooseFinishListener onOddsChooseFinishListener) {
        super(activity);
        // super(activity);
        this.mContext = activity;
        //setMatchInfo(matchInfo);
        this.listener = onOddsChooseFinishListener;
        this.dataBean = dataBean;
        this.hasChoosedMap = hasChoosedMap;
      /*  initViewDatas();
        initView();*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jclq_pop_view_hh);
        initViewDatas();
        initView();
    }

    protected void initViewDatas() {
        initSfDatas();
        initRfSfDatas();
        initDxfDatas();
        initSfcDatas();
    }

    protected void initSfDatas() {
        sfDatas = new ArrayList<String>();
        sfDatas.add(dataBean.getOdds3());
        sfDatas.add(dataBean.getOdds0());

    }

    protected void initRfSfDatas() {
        rfSfDatas = new ArrayList<String>();
        rfSfDatas.add(dataBean.getLetOdds3());
        rfSfDatas.add(dataBean.getLetOdds0());
        rfSfDatas.add(dataBean.getLet());

    }

    protected void initDxfDatas() {
        dxfDatas = new ArrayList<String>();
        dxfDatas.add(dataBean.getLargeScore());
        dxfDatas.add(dataBean.getSmallScore());
        dxfDatas.add(dataBean.getPresetScore());

    }

    protected void initSfcDatas() {
        sfcDatas = new ArrayList<String>();
        sfcDatas.add(dataBean.getHomeWin15());
        sfcDatas.add(dataBean.getHomeWin610());
        sfcDatas.add(dataBean.getHomeWin1115());
        sfcDatas.add(dataBean.getHomeWin1620());
        sfcDatas.add(dataBean.getHomeWin2125());
        sfcDatas.add(dataBean.getHomeWin26());
        sfcDatas.add(dataBean.getGuestWin15());
        sfcDatas.add(dataBean.getGuestWin610());
        sfcDatas.add(dataBean.getGuestWin1115());
        sfcDatas.add(dataBean.getGuestWin1620());
        sfcDatas.add(dataBean.getGuestWin2125());
        sfcDatas.add(dataBean.getGuestWin26());

    }

    protected void initPopSfTextViews() {
        popSfTextViews = new ArrayList<TextView>();
        for (int i = 0; i < popSfTextViewIds.length; i++) {
            TextView tv = (TextView) findViewById(popSfTextViewIds[i]);
            tv.setText(sfDatas.get(i));
            popSfTextViews.add(tv);
        }
    }

    protected void initPopSfLayoutViews() {
        popSfLayoutViews = new ArrayList<LinearLayout>();
        popSfCheckStatusMap = new HashMap<View, Integer>();
        initLayoutViews(popSfLayoutViews, popSfCheckStatusMap, choosedOddMap, popSfLayoutViewIds, sfDatas);

    }

    protected void initPopRfsfViews() {
        popRfsfViews = new ArrayList<TextView>();
        for (int i = 0; i < popRfsfViewIds.length; i++) {
            TextView tv = (TextView) findViewById(popRfsfViewIds[i]);
            if (i == popRfsfViewIds.length - 1) {
                tv.setText("让分胜负(主队" + rfSfDatas.get(i) + ")");
            } else {
                tv.setText(rfSfDatas.get(i));
            }
            popRfsfViews.add(tv);
        }
//         tv_pop_rfsf_win = (TextView) findViewById(R.id.tv_pop_rfsf_win);

//        TextView rfsfWinTitle = (TextView)findViewById(R.id.tv_pop_rfsf_win);
//        rfsfWinTitle.setText("让胜(" + dataBean.getLet() + ")");


    }

    protected void initPopRfsfLayoutViews() {
        popRfsfLayoutViews = new ArrayList<LinearLayout>();
        popRfSfCheckStatusMap = new HashMap<View, Integer>();
        initLayoutViews(popRfsfLayoutViews, popRfSfCheckStatusMap, choosedOddMap, popRfsfLayoutViewIds, rfSfDatas);
        // String str = mContext.getString(R.string.jclq_rfsf_blancket);
        // SpannableString sp = new SpannableString(str +
        // dataBean.getLet() + ")");
        // sp.setSpan(new ForegroundColorSpan(Color.RED), str.length(),
        // sp.length() - 1,
        // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // tv_pop_rfsf_win.setText(sp);

    }

    protected void initPopDxfViews() {
        popDxfViews = new ArrayList<TextView>();
        for (int i = 0; i < popDxfViewIds.length; i++) {
            TextView tv = (TextView) findViewById(popDxfViewIds[i]);
            if (i == popDxfViewIds.length - 1) {
                tv.setText("预设总分(" + dxfDatas.get(i) + ")");
            } else {
                tv.setText(dxfDatas.get(i));
            }
            popDxfViews.add(tv);
        }
    }

    protected void initPopDxfLayoutViews() {
        popDxfLayoutViews = new ArrayList<LinearLayout>();
        popDxfCheckStatusMap = new HashMap<View, Integer>();
        initLayoutViews(popDxfLayoutViews, popDxfCheckStatusMap, choosedOddMap, popDxfLayoutViewIds, dxfDatas);

    }

    protected void initLayoutViews(List<LinearLayout> layoutViews, final Map<View, Integer> checkedStatusMap,
                                   final HashMap<String, String> choosedOddMap, int[] ids, final List<String> datas) {

        for (int i = 0; i < ids.length; i++) {
            final LinearLayout ll = (LinearLayout) findViewById(ids[i]);
            final TextView titleTextView = (TextView) ll.getChildAt(0);
            final TextView oddTextView = (TextView) ll.getChildAt(1);
//            if(ids.length > 3){
//                final TextView letTextView = (TextView)ll.getChildAt(3);
//                final TextView leftBrancketView = (TextView)ll.getChildAt(4);
//                final TextView rightBrancketView = (TextView)ll.getChildAt(5);
//            }

            checkedStatusMap.put(ll, 0);
            if (hasChoosedMap != null) {
                for (String layoutText : hasChoosedMap.keySet()) {

                    if (layoutText.equals(titleTextView.getText().toString())) {
                        int status = checkedStatusMap.get(ll);
                        status = (status + 1) % 2;
                        checkedStatusMap.put(ll, status);
                        ll.setBackgroundResource(R.color.red_theme);
                        titleTextView.setTextColor(Color.WHITE);
                        oddTextView.setTextColor(Color.WHITE);
                        choosedOddMap.put(layoutText, datas.get(i));
                    }
                }
            }
            final int index = i;
            ll.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    int status = checkedStatusMap.get(v);
                    status = (status + 1) % 2;
                    checkedStatusMap.put(v, status);
                    if (status == 1) {
                        ll.setBackgroundResource(R.color.red_theme);
                        titleTextView.setTextColor(Color.WHITE);
                        oddTextView.setTextColor(Color.WHITE);
                        String title = titleTextView.getText().toString();
                        String data = datas.get(index);
                        choosedOddMap.put(title, data);

//                        if(letTextView != null && leftBrancketView != null && rightBrancketView != null){
//                            letTextView.setTextColor(mContext.getResources().getColor(R.color.white));
//                            leftBrancketView.setTextColor(mContext.getResources().getColor(R.color.white));
//                            leftBrancketView.setTextColor(mContext.getResources().getColor(R.color.white));
//                        }

                    } else if (status == 0) {
                        ll.setBackgroundResource(R.drawable.table_bg);
                        titleTextView.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
                        oddTextView.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                        choosedOddMap.remove(titleTextView.getText().toString());

//                        if(letTextView != null && leftBrancketView != null && rightBrancketView != null){
//                            letTextView.setTextColor(mContext.getResources().getColor(R.color.txt_middle));
//                            leftBrancketView.setTextColor(mContext.getResources().getColor(R.color.txt_middle));
//                            leftBrancketView.setTextColor(mContext.getResources().getColor(R.color.txt_middle));
//                        }
                    }

                }
            });
            layoutViews.add(ll);
        }
    }

    protected void initPopSfcViews() {
        popSfcViews = new ArrayList<TextView>();
        for (int i = 0; i < popSfcViewIds.length; i++) {
            TextView tv = (TextView) findViewById(popSfcViewIds[i]);
            tv.setText(sfcDatas.get(i));
            popSfcViews.add(tv);
        }
    }

    protected void initPopSfcLayoutViews() {
        popSfcLayoutViews = new ArrayList<LinearLayout>();
        popSfcCheckStatusMap = new HashMap<View, Integer>();
        initLayoutViews(popSfcLayoutViews, popSfcCheckStatusMap, choosedOddMap, popSfcLayoutViewIds, sfcDatas);

    }

    protected void initHeadview() {
        popHostView = (TextView) findViewById(R.id.tv_pop_hh_hosts);
        popGuestView = (TextView) findViewById(R.id.tv_pop_hh_guests);
        letNumberView = (TextView) findViewById(R.id.tv_rangqius);

        popHostView.setText(dataBean.getHomeShortCn());
        popGuestView.setText(dataBean.getGuestShortCn());
        if (!TextUtils.isEmpty(dataBean.getLet()))
            setLet(letNumberView, dataBean.getLet());

    }

    private void setLet(TextView letTextView, String let) {
        if (Float.valueOf(let) > 0) {
            letTextView.setText(let);
            letTextView.setTextColor(mContext.getResources().getColor(R.color.red_theme));
        } else if (Float.valueOf(let) < 0) {
            letTextView.setText(let);// String.format("%+d", let)
            letTextView.setTextColor(mContext.getResources().getColor(R.color.green_let));
        } else {
            letTextView.setText("");
        }
    }

    protected void initGroupView() {
        hh_sf_ll = (LinearLayout) findViewById(R.id.hh_sf_ll);
        hh_rfsf_ll = (LinearLayout) findViewById(R.id.hh_rfsf_ll);
        hh_dxf_ll = (LinearLayout) findViewById(R.id.hh_dxf_ll);
        hh_sfc_ll = (LinearLayout) findViewById(R.id.hh_sfc_ll);
        if (dataBean.getSfFs() == 0) {
            hh_sf_ll.setVisibility(View.GONE);
        }
        if (dataBean.getRfsfFs() == 0) {
            hh_rfsf_ll.setVisibility(View.GONE);
        }
        if (dataBean.getDxfFs() == 0) {
            hh_dxf_ll.setVisibility(View.GONE);
        }
        if (dataBean.getSfcFs() == 0) {
            hh_sfc_ll.setVisibility(View.GONE);
        }
    }

    protected void initView() {
        initHeadview();
        initGroupView();
        initPopSfTextViews();
        initPopSfLayoutViews();
        initPopRfsfViews();
        initPopRfsfLayoutViews();
        initPopDxfViews();
        initPopDxfLayoutViews();
        initPopSfcViews();
        initPopSfcLayoutViews();

        initConfirmView();

    }

    protected void initConfirmView() {
        confirmBtn = findViewById(R.id.hh_pop_confirm);
        cancelBtn = findViewById(R.id.hh_pop_cancel);
        confirmBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onConfirmChoose();
                //onDismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // onDismiss();
                onConfirmChoose();
            }
        });
    }

    protected void onConfirmChoose() {
        if (listener != null) {
            dismiss();
            listener.onChooseFinish(choosedOddMap);
        }
    }

    protected OnOddsChooseFinishListener listener;

    public static interface OnOddsChooseFinishListener {
        public void onChooseFinish(HashMap<String, String> choosedOddMap);
    }
}
