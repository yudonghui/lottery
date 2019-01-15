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
public class JczqJqsDialog extends AlertDialog {
    protected View confirmBtn;

    protected View cancelBtn;

    protected TextView popHostView;

    protected TextView popGuestView;
    protected HashMap<String, String> hasChoosedMap;
    AllPlayMethodDialog.OnOddsChooseFinishListener listener;
    protected HashMap<String, String> choosedOddMap = new HashMap<>();
    private Activity mContext;
    JczqData.DataBean dataBean;
    public static int[] popZjqViewIds = new int[]{R.id.tv_pop_zjq_0, R.id.tv_pop_zjq_1, R.id.tv_pop_zjq_2,
            R.id.tv_pop_zjq_3, R.id.tv_pop_zjq_4, R.id.tv_pop_zjq_5, R.id.tv_pop_zjq_6, R.id.tv_pop_zjq_7};

    public static int[] popZjqLayoutViewIds = new int[]{R.id.ll_pop_zjq_0, R.id.ll_pop_zjq_1, R.id.ll_pop_zjq_2,
            R.id.ll_pop_zjq_3, R.id.ll_pop_zjq_4, R.id.ll_pop_zjq_5, R.id.ll_pop_zjq_6, R.id.ll_pop_zjq_7};
    private List<TextView> popZjqViews;

    private List<LinearLayout> popZjqLayoutViews;

    private Map<View, Integer> popZjqCheckStatusMap;

    private ArrayList<String> zjqDatas;
    public JczqJqsDialog(Activity context, JczqData.DataBean dataBean, HashMap<String, String> map, AllPlayMethodDialog.OnOddsChooseFinishListener onOddsChooseFinishListener) {
        super(context);
        this.mContext = context;
        this.listener=onOddsChooseFinishListener;
        this.hasChoosedMap=map;
        this.dataBean=dataBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jczq_pop_view_jqs);
        //mInflate = View.inflate(mContext, R.layout.jczq_pop_view_cbf,null);
        initZjqDatas();
        initView();
    }
    protected void initView()
    {
        // 获取当前屏幕的横竖屏
       /* LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.jczq_pop_view_cbf, null);*/

        initHeadview();
        initPopZjqViews();
        initPopZjqLayoutViews();

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
    protected void initPopZjqViews() {
        popZjqViews = new ArrayList<TextView>();
        for (int i = 0; i < popZjqViewIds.length; i++) {
            TextView tv = (TextView) findViewById(popZjqViewIds[i]);
            tv.setText(zjqDatas.get(i));
            popZjqViews.add(tv);
        }
    }

    protected void initPopZjqLayoutViews() {
        popZjqLayoutViews = new ArrayList<LinearLayout>();
        popZjqCheckStatusMap = new HashMap<View, Integer>();
        // popZjqChoosedOddMaps = new HashMap<View, String>();
        initLayoutViews(popZjqLayoutViews, popZjqCheckStatusMap, choosedOddMap, popZjqLayoutViewIds, zjqDatas);
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
    protected void initZjqDatas() {
        zjqDatas = new ArrayList<String>();
        zjqDatas.add(dataBean.getGoal0());
        zjqDatas.add(dataBean.getGoal1());
        zjqDatas.add(dataBean.getGoal2());
        zjqDatas.add(dataBean.getGoal3());
        zjqDatas.add(dataBean.getGoal4());
        zjqDatas.add(dataBean.getGoal5());
        zjqDatas.add(dataBean.getGoal6());
        zjqDatas.add(dataBean.getGoal7());
    }
}
