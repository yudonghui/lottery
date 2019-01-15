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
public class JczqBqcDialog extends AlertDialog {
    protected View confirmBtn;

    protected View cancelBtn;

    protected TextView popHostView;

    protected TextView popGuestView;
    private Activity mContext;
    protected HashMap<String, String> hasChoosedMap;
    AllPlayMethodDialog.OnOddsChooseFinishListener listener;
    JczqData.DataBean dataBean;
    protected HashMap<String, String> choosedOddMap = new HashMap<>();
    public static int[] popBqspfViewIds = new int[]{R.id.tv_pop_bqspf_0, R.id.tv_pop_bqspf_1, R.id.tv_pop_bqspf_2,
            R.id.tv_pop_bqspf_3, R.id.tv_pop_bqspf_4, R.id.tv_pop_bqspf_5, R.id.tv_pop_bqspf_6, R.id.tv_pop_bqspf_7,
            R.id.tv_pop_bqspf_8};

    public static int[] popBqspfLayoutViewIds = new int[]{R.id.ll_pop_bqspf_0, R.id.ll_pop_bqspf_1,
            R.id.ll_pop_bqspf_2, R.id.ll_pop_bqspf_3, R.id.ll_pop_bqspf_4, R.id.ll_pop_bqspf_5, R.id.ll_pop_bqspf_6,
            R.id.ll_pop_bqspf_7, R.id.ll_pop_bqspf_8};
    private List<TextView> popBqspfViews;

    private List<LinearLayout> popBqspfLayoutViews;

    private Map<View, Integer> popBqSpfCheckStatusMap;
    private ArrayList<String> bqcSpfDatas;

    public JczqBqcDialog(Activity context, JczqData.DataBean dataBean, HashMap<String, String> map, AllPlayMethodDialog.OnOddsChooseFinishListener onOddsChooseFinishListener) {
        super(context);
        this.mContext = context;
        this.listener = onOddsChooseFinishListener;
        this.hasChoosedMap = map;
        this.dataBean = dataBean;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jczq_pop_view_bqc);
        //mInflate = View.inflate(mContext, R.layout.jczq_pop_view_cbf,null);
        initBqcSpfDatas();
        initView();
    }
    protected void initBqcSpfDatas() {
        bqcSpfDatas = new ArrayList<>();
        bqcSpfDatas.add(dataBean.getHalfFull33());
        bqcSpfDatas.add(dataBean.getHalfFull31());
        bqcSpfDatas.add(dataBean.getHalfFull30());
        bqcSpfDatas.add(dataBean.getHalfFull13());
        bqcSpfDatas.add(dataBean.getHalfFull11());
        bqcSpfDatas.add(dataBean.getHalfFull10());
        bqcSpfDatas.add(dataBean.getHalfFull03());
        bqcSpfDatas.add(dataBean.getHalfFull01());
        bqcSpfDatas.add(dataBean.getHalfFull00());
    }
    protected void initView()
    {
        // 获取当前屏幕的横竖屏
       /* LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.jczq_pop_view_cbf, null);*/

        initHeadview();
        initBqspfViews();
        initPopBqspfLayoutviews();


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
    protected void initBqspfViews() {

        popBqspfViews = new ArrayList<TextView>();
        for (int i = 0; i < popBqspfViewIds.length; i++) {
            TextView tv = (TextView) findViewById(popBqspfViewIds[i]);
            tv.setText(bqcSpfDatas.get(i));
            popBqspfViews.add(tv);

        }
    }

    protected void initPopBqspfLayoutviews() {
        popBqspfLayoutViews = new ArrayList<LinearLayout>();
        popBqSpfCheckStatusMap = new HashMap<View, Integer>();
        // popBqSpfChoosedOddMaps = new HashMap<View, String>();
        initLayoutViews(popBqspfLayoutViews, popBqSpfCheckStatusMap, choosedOddMap, popBqspfLayoutViewIds, bqcSpfDatas);

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

    protected void initHeadview() {
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
