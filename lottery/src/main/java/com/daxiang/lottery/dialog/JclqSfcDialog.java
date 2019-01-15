package com.daxiang.lottery.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
 * Created by Administrator on 2016/10/19 0019.
 */
public class JclqSfcDialog extends AlertDialog {


    protected JclqData.DataBean dataBean;
    Context mContext;
    protected HashMap<String, String> choosedOddMap = new HashMap<>();
    protected HashMap<String, String> hasChoosedMap;

    protected View confirmBtn;

    protected View cancelBtn;
    protected TextView popHostView;

    protected TextView popGuestView;

    /**
     * 胜分差
     */
    protected List<TextView> popSfcViews;

    protected List<LinearLayout> popSfcLayoutViews;

    protected Map<View, Integer> popSfcCheckStatusMap;

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
    protected LinearLayout hh_sfc_ll;

    public JclqSfcDialog(Activity activity) {
        super(activity);
        this.mContext = activity;
    }

    public JclqSfcDialog(Activity activity, JclqData.DataBean dataBean, HashMap<String, String> hasChoosedMap, OnOddsChooseFinishListener onOddsChooseFinishListener) {
        super(activity);
        this.mContext = activity;
        this.listener = onOddsChooseFinishListener;
        this.dataBean = dataBean;
        this.hasChoosedMap = hasChoosedMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jclq_pop_view_sfc);
        initViewDatas();
        initView();
    }

    protected void initViewDatas() {
        initSfcDatas();
    }

    protected void initSfcDatas() {
        sfcDatas = new ArrayList<>();
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

    protected void initLayoutViews(List<LinearLayout> layoutViews, final Map<View, Integer> checkedStatusMap,
                                   final HashMap<String, String> choosedOddMap, int[] ids, final List<String> datas) {

        for (int i = 0; i < ids.length; i++) {
            final LinearLayout ll = (LinearLayout) findViewById(ids[i]);
            final TextView titleTextView = (TextView) ll.getChildAt(0);
            final TextView oddTextView = (TextView) ll.getChildAt(1);

            checkedStatusMap.put(ll, 0);
            if (hasChoosedMap != null) {
                for (String layoutText : hasChoosedMap.keySet()) {

                    if (layoutText.equals(titleTextView.getText().toString())) {
                        int status = checkedStatusMap.get(ll);
                        status = (status + 1) % 2;
                        checkedStatusMap.put(ll, status);
                        ll.setBackgroundResource(R.color.red_txt);
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
                        ll.setBackgroundResource(R.color.red_txt);
                        titleTextView.setTextColor(Color.WHITE);
                        oddTextView.setTextColor(Color.WHITE);
                        String title = titleTextView.getText().toString();
                        String data = datas.get(index);
                        choosedOddMap.put(title, data);

                    } else if (status == 0) {
                        ll.setBackgroundResource(R.drawable.table_bg);
                        titleTextView.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
                        oddTextView.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                        choosedOddMap.remove(titleTextView.getText().toString());
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

        popHostView.setText(dataBean.getHomeShortCn());
        popGuestView.setText(dataBean.getGuestShortCn());

    }

    protected void initGroupView() {
        hh_sfc_ll = (LinearLayout) findViewById(R.id.hh_sfc_ll);
        if (dataBean.getSfcDg() == 0) {
            hh_sfc_ll.setVisibility(View.GONE);
        }
    }

    protected void initView() {
        initHeadview();
        initGroupView();
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
