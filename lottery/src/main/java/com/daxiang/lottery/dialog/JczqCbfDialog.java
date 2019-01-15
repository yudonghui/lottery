package com.daxiang.lottery.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.JczqData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Administrator on 2016/8/30 0030.
 */

public class JczqCbfDialog extends AlertDialog {
    protected Activity mContext;

    protected JczqData.DataBean dataBean;

    protected View confirmBtn;

    protected View cancelBtn;

    protected TextView popHostView;

    protected TextView popGuestView;
    AllPlayMethodDialog.OnOddsChooseFinishListener listener;
    protected HashMap<String, String> hasChoosedMap;
    private ArrayList<String> bfDatas;
    private List<TextView> popBfViews;

    private List<LinearLayout> popBfLayoutViews;

    private Map<View, Integer> popBfCheckStatusMap;
    public static int[] popBfViewIds = new int[]{R.id.tv_pop_bf_0, R.id.tv_pop_bf_1, R.id.tv_pop_bf_2,
            R.id.tv_pop_bf_3, R.id.tv_pop_bf_4, R.id.tv_pop_bf_5, R.id.tv_pop_bf_6, R.id.tv_pop_bf_7, R.id.tv_pop_bf_8,
            R.id.tv_pop_bf_9, R.id.tv_pop_bf_10, R.id.tv_pop_bf_11, R.id.tv_pop_bf_12, R.id.tv_pop_bf_13,
            R.id.tv_pop_bf_14, R.id.tv_pop_bf_15, R.id.tv_pop_bf_16, R.id.tv_pop_bf_17, R.id.tv_pop_bf_18,
            R.id.tv_pop_bf_19, R.id.tv_pop_bf_20, R.id.tv_pop_bf_21, R.id.tv_pop_bf_22, R.id.tv_pop_bf_23,
            R.id.tv_pop_bf_24, R.id.tv_pop_bf_25, R.id.tv_pop_bf_26, R.id.tv_pop_bf_27, R.id.tv_pop_bf_28,
            R.id.tv_pop_bf_29, R.id.tv_pop_bf_30};

    public static int[] popBfLayoutViewIds = new int[]{R.id.ll_pop_bf_0, R.id.ll_pop_bf_1, R.id.ll_pop_bf_2,
            R.id.ll_pop_bf_3, R.id.ll_pop_bf_4, R.id.ll_pop_bf_5, R.id.ll_pop_bf_6, R.id.ll_pop_bf_7, R.id.ll_pop_bf_8,
            R.id.ll_pop_bf_9, R.id.ll_pop_bf_10, R.id.ll_pop_bf_11, R.id.ll_pop_bf_12, R.id.ll_pop_bf_13,
            R.id.ll_pop_bf_14, R.id.ll_pop_bf_15, R.id.ll_pop_bf_16, R.id.ll_pop_bf_17, R.id.ll_pop_bf_18,
            R.id.ll_pop_bf_19, R.id.ll_pop_bf_20, R.id.ll_pop_bf_21, R.id.ll_pop_bf_22, R.id.ll_pop_bf_23,
            R.id.ll_pop_bf_24, R.id.ll_pop_bf_25, R.id.ll_pop_bf_26, R.id.ll_pop_bf_27, R.id.ll_pop_bf_28,
            R.id.ll_pop_bf_29, R.id.ll_pop_bf_30};
    protected HashMap<String, String> choosedOddMap = new HashMap<>();
    public JczqCbfDialog(Activity context, JczqData.DataBean dataBean, HashMap<String, String> map, AllPlayMethodDialog.OnOddsChooseFinishListener onOddsChooseFinishListener) {
        super(context);
        this.mContext = context;
        this.listener=onOddsChooseFinishListener;
        this.hasChoosedMap=map;
        this.dataBean=dataBean;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.jczq_pop_view_cbf);
        //mInflate = View.inflate(mContext, R.layout.jczq_pop_view_cbf,null);
        initBfDatas();
        initView();
    }
    protected void initBfDatas() {
        bfDatas = new ArrayList<String>();
        bfDatas.add(dataBean.getScore10());
        bfDatas.add(dataBean.getScore20());
        bfDatas.add(dataBean.getScore21());
        bfDatas.add(dataBean.getScore30());
        bfDatas.add(dataBean.getScore31());
        bfDatas.add(dataBean.getScore32());
        bfDatas.add(dataBean.getScore40());
        bfDatas.add(dataBean.getScore41());
        bfDatas.add(dataBean.getScore42());
        bfDatas.add(dataBean.getScore50());
        bfDatas.add(dataBean.getScore51());
        bfDatas.add(dataBean.getScore52());
        bfDatas.add(dataBean.getScore3x());
        bfDatas.add(dataBean.getScore00());
        bfDatas.add(dataBean.getScore11());
        bfDatas.add(dataBean.getScore22());
        bfDatas.add(dataBean.getScore33());
        bfDatas.add(dataBean.getScore1x());
        bfDatas.add(dataBean.getScore01());
        bfDatas.add(dataBean.getScore02());
        bfDatas.add(dataBean.getScore12());
        bfDatas.add(dataBean.getScore03());

        bfDatas.add(dataBean.getScore13());
        bfDatas.add(dataBean.getScore23());
        bfDatas.add(dataBean.getScore04());
        bfDatas.add(dataBean.getScore14());
        bfDatas.add(dataBean.getScore24());
        bfDatas.add(dataBean.getScore05());

        bfDatas.add(dataBean.getScore15());
        bfDatas.add(dataBean.getScore25());
        bfDatas.add(dataBean.getScore0x());

    }

    protected void initView()
    {
        // 获取当前屏幕的横竖屏
       /* LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.jczq_pop_view_cbf, null);*/

        initHeadview();
        initPopBfViews();
        initPopBfLayoutViews();

        confirmBtn = findViewById(R.id.bf_pop_confirm);
        cancelBtn = findViewById(R.id.bf_pop_cancel);
        confirmBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                onConfirmChoose();
                dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                dismiss();
            }
        });
    }
    protected void initPopBfLayoutViews() {
        popBfLayoutViews = new ArrayList<LinearLayout>();
        popBfCheckStatusMap = new HashMap<View, Integer>();
        // popBfChoosedOddMaps = new HashMap<View, String>();
        initLayoutViews(popBfLayoutViews, popBfCheckStatusMap, choosedOddMap, popBfLayoutViewIds, bfDatas);

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
    protected void initPopBfViews() {
        popBfViews = new ArrayList<TextView>();
        for (int i = 0; i < popBfViewIds.length; i++) {
            TextView tv = (TextView) findViewById(popBfViewIds[i]);
            tv.setText(bfDatas.get(i));
            popBfViews.add(tv);
        }
    }
    protected void initHeadview()
    {
        popHostView = (TextView) findViewById(R.id.tv_pop_bf_host);
        popGuestView = (TextView) findViewById(R.id.tv_pop_bf_guest);
        popHostView.setText(dataBean.getHomeShortCn());
        popGuestView.setText(dataBean.getGuestShortCn());

    }
    protected void onConfirmChoose() {
        if (listener != null) {
            dismiss();
            listener.onChooseFinish(choosedOddMap);
        }
    }
}
