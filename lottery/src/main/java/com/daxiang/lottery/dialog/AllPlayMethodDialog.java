package com.daxiang.lottery.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
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
 * Created by Administrator on 2016/8/26 0026.
 */
public class AllPlayMethodDialog extends AlertDialog {

    protected Activity mContext;

    protected JczqData.DataBean dataBean;

    protected View confirmBtn;

    protected View cancelBtn;

    protected TextView popHostView;

    protected TextView popGuestView;

    protected TextView letNumberView;
    protected HashMap<String, String> choosedOddMap = new HashMap<>();
    /**
     * 胜平负
     */

    private List<TextView> popSpfTextViews;

    private List<LinearLayout> popSpfLayoutViews;

    private Map<View, Integer> popSpfCheckStatusMap;
    /**
     * 让球胜平负
     */
    private List<TextView> popRqspfViews;

    private List<LinearLayout> popRqspfLayoutViews;

    private Map<View, Integer> popRqSpfCheckStatusMap;

    /**
     * 半全场
     */
    private List<TextView> popBqspfViews;

    private List<LinearLayout> popBqspfLayoutViews;

    private Map<View, Integer> popBqSpfCheckStatusMap;

    /**
     * 总进球
     */
    private List<TextView> popZjqViews;

    private List<LinearLayout> popZjqLayoutViews;

    private Map<View, Integer> popZjqCheckStatusMap;

    /**
     * 比分
     */
    private List<TextView> popBfViews;

    private List<LinearLayout> popBfLayoutViews;

    private Map<View, Integer> popBfCheckStatusMap;
    /**
     * 胜平负ids
     */
    public static int[] popSpfTextViewIds = new int[]{R.id.tv_pop_spf_0, R.id.tv_pop_spf_1, R.id.tv_pop_spf_2};

    public static int[] popSpfLayoutViewIds = new int[]{R.id.ll_pop_spf_0, R.id.ll_pop_spf_1, R.id.ll_pop_spf_2};

    private ArrayList<String> spfDatas;

    /**
     * 让球胜平负ids
     */

    public static int[] popRqspfViewIds = new int[]{R.id.tv_pop_rqspf_0, R.id.tv_pop_rqspf_1, R.id.tv_pop_rqspf_2};

    public static int[] popRqspfLayoutViewIds = new int[]{R.id.ll_pop_rqspf_0, R.id.ll_pop_rqspf_1,
            R.id.ll_pop_rqspf_2};

    private ArrayList<String> rqSpfDatas;

    /**
     * 半全场ids
     */
    public static int[] popBqspfViewIds = new int[]{R.id.tv_pop_bqspf_0, R.id.tv_pop_bqspf_1, R.id.tv_pop_bqspf_2,
            R.id.tv_pop_bqspf_3, R.id.tv_pop_bqspf_4, R.id.tv_pop_bqspf_5, R.id.tv_pop_bqspf_6, R.id.tv_pop_bqspf_7,
            R.id.tv_pop_bqspf_8};

    public static int[] popBqspfLayoutViewIds = new int[]{R.id.ll_pop_bqspf_0, R.id.ll_pop_bqspf_1,
            R.id.ll_pop_bqspf_2, R.id.ll_pop_bqspf_3, R.id.ll_pop_bqspf_4, R.id.ll_pop_bqspf_5, R.id.ll_pop_bqspf_6,
            R.id.ll_pop_bqspf_7, R.id.ll_pop_bqspf_8};

    private ArrayList<String> bqcSpfDatas;

    /**
     * 总进球 ids
     */

    public static int[] popZjqViewIds = new int[]{R.id.tv_pop_zjq_0, R.id.tv_pop_zjq_1, R.id.tv_pop_zjq_2,
            R.id.tv_pop_zjq_3, R.id.tv_pop_zjq_4, R.id.tv_pop_zjq_5, R.id.tv_pop_zjq_6, R.id.tv_pop_zjq_7};

    public static int[] popZjqLayoutViewIds = new int[]{R.id.ll_pop_zjq_0, R.id.ll_pop_zjq_1, R.id.ll_pop_zjq_2,
            R.id.ll_pop_zjq_3, R.id.ll_pop_zjq_4, R.id.ll_pop_zjq_5, R.id.ll_pop_zjq_6, R.id.ll_pop_zjq_7};

    private ArrayList<String> zjqDatas;

    /**
     * 比分ids
     */
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

    private ArrayList<String> bfDatas;

    protected HashMap<String, String> hasChoosedMap;

    public AllPlayMethodDialog(Activity activity) {
        super(activity);
        this.mContext = activity;
    }

    public AllPlayMethodDialog(Activity activity, JczqData.DataBean dataBean, HashMap<String, String> hasChoosedMap, OnOddsChooseFinishListener onOddsChooseFinishListener) {
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
        setContentView(R.layout.jczq_pop_view_hh);
        initViewDatas();
        initView();
    }

    protected void initView() {
        // 获取当前屏幕的横竖屏
        // LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflater.inflate(R.layout.jczq_pop_view_hh, this);
        initHeadview();

        LinearLayout spfLinearLayout = (LinearLayout) findViewById(R.id.hh_spf_ll);
        spfLinearLayout.setVisibility(dataBean.getSpfFs() == 1 ? View.VISIBLE : View.GONE);
        initPopSpfTextViews();
        initPopSpfLayoutViews();

        LinearLayout hhRqspfLinearLayout = (LinearLayout) findViewById(R.id.hh_rqspf_ll);
        hhRqspfLinearLayout.setVisibility(dataBean.getRqspfFs() == 1 ? View.VISIBLE : View.GONE);
        try {
            TextView textView = (TextView) findViewById(R.id.rqs);
            int let = dataBean.getLet();
            textView.setText("让球胜平负(主队" + let + ")");
        } catch (Exception e) {

        }

        initPopRqspfViews();
        initPopRqspfLayoutViews();

        LinearLayout bqspfLinearLayout = (LinearLayout) findViewById(R.id.hh_bqspf_ll);
        bqspfLinearLayout.setVisibility(dataBean.getBqcFs() == 1 ? View.VISIBLE : View.GONE);
        initBqspfViews();
        initPopBqspfLayoutviews();

        LinearLayout bfLinearLayout = (LinearLayout) findViewById(R.id.hh_bf_ll);
        bfLinearLayout.setVisibility(dataBean.getBfFs() == 1 ? View.VISIBLE : View.GONE);
        initPopBfViews();
        initPopBfLayoutViews();

        LinearLayout zjqLinearLayout = (LinearLayout) findViewById(R.id.hh_zjq_ll);
        zjqLinearLayout.setVisibility(dataBean.getJqsFs() == 1 ? View.VISIBLE : View.GONE);
        initPopZjqViews();
        initPopZjqLayoutViews();

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

    protected void initHeadview() {
        popHostView = (TextView) findViewById(R.id.tv_pop_hh_host);
        popGuestView = (TextView) findViewById(R.id.tv_pop_hh_guest);
        letNumberView = (TextView) findViewById(R.id.tv_rangqiu);

        popHostView.setText(dataBean.getHomeShortCn());
        popGuestView.setText(dataBean.getGuestShortCn());

    }

    protected void initPopSpfTextViews() {
        popSpfTextViews = new ArrayList<TextView>();
        for (int i = 0; i < popSpfTextViewIds.length; i++) {
            TextView tv = (TextView) findViewById(popSpfTextViewIds[i]);
            tv.setText(spfDatas.get(i));
            popSpfTextViews.add(tv);

        }
    }

    protected void initPopSpfLayoutViews() {
        popSpfLayoutViews = new ArrayList<LinearLayout>();
        popSpfCheckStatusMap = new HashMap<View, Integer>();
        // popSpfChoosedOddMaps = new HashMap<View, String>();
        initLayoutViews(popSpfLayoutViews, popSpfCheckStatusMap, choosedOddMap, popSpfLayoutViewIds, spfDatas);

    }

    protected void initPopRqspfViews() {
        popRqspfViews = new ArrayList<TextView>();
        for (int i = 0; i < popRqspfViewIds.length; i++) {
            TextView tv = (TextView) findViewById(popRqspfViewIds[i]);
            tv.setText(rqSpfDatas.get(i));
            popRqspfViews.add(tv);
        }
    }

    protected void initPopRqspfLayoutViews() {
        popRqspfLayoutViews = new ArrayList<LinearLayout>();

        popRqSpfCheckStatusMap = new HashMap<View, Integer>();
        // popRqSpfChoosedOddMaps = new HashMap<View, String>();

        initLayoutViews(popRqspfLayoutViews, popRqSpfCheckStatusMap, choosedOddMap, popRqspfLayoutViewIds, rqSpfDatas);

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

    protected void initPopBfViews() {
        popBfViews = new ArrayList<TextView>();
        for (int i = 0; i < popBfViewIds.length; i++) {
            TextView tv = (TextView) findViewById(popBfViewIds[i]);
            tv.setText(bfDatas.get(i));
            popBfViews.add(tv);
        }
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

    protected void initViewDatas() {

        initSpfDatas();
        initRqSpfDatas();
        //初始化半全场控件
        initBqcSpfDatas();
        initZjqDatas();
        initBfDatas();
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

    protected void initBfDatas() {
        bfDatas = new ArrayList<>();
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

    protected void initSpfDatas() {
        spfDatas = new ArrayList<>();
        spfDatas.add(dataBean.getOdds3());
        spfDatas.add(dataBean.getOdds1());
        spfDatas.add(dataBean.getOdds0());

    }

    protected void initRqSpfDatas() {
        rqSpfDatas = new ArrayList<String>();
        rqSpfDatas.add(dataBean.getLetOdds3());
        rqSpfDatas.add(dataBean.getLetOdds1());
        rqSpfDatas.add(dataBean.getLetOdds0());

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

  /*  public void onShow() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        //HunhePlayView1.this.setLayoutParams(params);
        //  RelativeLayout viewById = (RelativeLayout) mContext.findViewById(R.id.container);
        // viewById.addView(HunhePlayView1.this);
        Activity activity = (Activity) mContext;
        ((RelativeLayout) activity.findViewById(R.id.container)).addView(HunhePlayView.this, params);
        Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, -1, Animation.RELATIVE_TO_PARENT, 0);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(600);
        anim.setFillAfter(true);
        startAnimation(anim);
    }*/

   /* public void onDismiss() {
        Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, -1);
        RelativeLayout mRl = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.recode_fragment_activity, this);
        ((ViewGroup) mRl.findViewById(R.id.container)).removeView(HunhePlayView.this);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(600);
        anim.setFillAfter(true);
        startAnimation(anim);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return true;
    }

  /*  public void dismissAdView() {
        this.setVisibility(View.GONE);
    }*/

    protected OnOddsChooseFinishListener listener;

    public static interface OnOddsChooseFinishListener {
        public void onChooseFinish(HashMap<String, String> choosedOddMap);
    }
}
