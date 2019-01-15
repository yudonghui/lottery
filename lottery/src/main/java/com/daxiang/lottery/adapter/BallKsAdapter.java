package com.daxiang.lottery.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.cxinterface.OnClickBallListener;
import com.daxiang.lottery.view.BallGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author yudonghui
 * @date 2017/12/12
 * @describe May the Buddha bless bug-free!!!
 */
public class BallKsAdapter extends BaseAdapter {
    String lotcode;
    boolean isOmit = true;
    Map<Integer, List<Integer>> randomMapBall;
    private Context mContext;
    OnClickBallListener listener;
    Map<Integer, View> map = new HashMap<>();
    //遗漏模型
    String omitResult;
    String playMethod;
    private String[] strings1;
    private String[] strings2;
    HashMap<Integer, HashMap<Integer, TextView>> mViewMap;

    public BallKsAdapter(HashMap<Integer, HashMap<Integer, TextView>> mViewMap, Context mContext, String lotcode,
                         OnClickBallListener listener,
                         Map<Integer, List<Integer>> randomMapBall,
                         String omitResult, String playMethod) {
        mViewMap.clear();
        this.mViewMap = mViewMap;
        this.mContext = mContext;
        this.lotcode = lotcode;
        this.listener = listener;
        this.randomMapBall = randomMapBall;
        this.omitResult = omitResult;
        this.playMethod = playMethod;
        data();
        setMap();
    }

    private int numColumns1;
    private int numColumns2;

    private void data() {
        switch (playMethod) {
            case "1"://和值
                strings1 = new String[]{"3", "4", "5", "6", "7", "8", "9",
                        "10", "11", "12", "13", "14", "15", "16", "17", "18"};
                strings2 = new String[]{"中240元", "中80元", "中40元", "中25元",
                        "中16元", "中12元", "中10元", "中9元", "中9元", "中10元", "中12元",
                        "中16元", "中25元", "中40元", "中80元", "中240元"};
                numColumns1 = 4;
                numColumns2 = 0;
                break;
            case "2"://三同号
                strings1 = new String[]{"111", "222", "333", "444", "555", "666"};
                strings2 = new String[]{};
                numColumns1 = 3;
                numColumns2 = 0;
                break;
            case "3"://三不同号
                strings1 = new String[]{"1", "2", "3", "4", "5", "6"};
                strings2 = new String[]{};
                numColumns1 = 3;
                numColumns2 = 0;
                break;
            case "4"://三连号通选
                strings1 = new String[]{"123   234   345   456"};
                strings2 = new String[]{};
                numColumns1 = 1;
                numColumns2 = 0;
                break;
            case "5"://二同号单选
                strings1 = new String[]{"11", "22", "33", "44", "55", "66"};
                strings2 = new String[]{"1", "2", "3", "4", "5", "6"};
                numColumns1 = 3;
                numColumns2 = 3;
                break;
            case "6"://二同号复选
                strings1 = new String[]{"11", "22", "33", "44", "55", "66"};
                strings2 = new String[]{};
                numColumns1 = 3;
                numColumns2 = 0;
                break;
            case "7"://二不同号
                strings1 = new String[]{"1", "2", "3", "4", "5", "6"};
                strings2 = new String[]{};
                numColumns1 = 3;
                numColumns2 = 0;
                break;
            case "8"://三同号通选
                strings1 = new String[]{"111   222   333   444   555   666"};
                strings2 = new String[]{};
                numColumns1 = 1;
                numColumns2 = 0;
                break;
        }
    }

    public void setData(boolean isOmit) {
        this.isOmit = isOmit;
    }

    @Override
    public int getCount() {
        switch (playMethod) {
            case "5":
                return 2;
            case "1":
            case "2":
            case "3":
            case "4":
            case "6":
            case "7":
            case "8":
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return "";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.item_ks_ball, null);
        BallGridView mGridView = (BallGridView) convertView.findViewById(R.id.gv_red_ball);
        TextView mTextView = (TextView) convertView.findViewById(R.id.sign);

        if (position == 0) {
            mTextView.setVisibility(View.GONE);
            mGridView.setNumColumns(numColumns1);
            BallKsGvAdaper ballKsGvAdaper = new BallKsGvAdaper(strings1, position);
            mGridView.setAdapter(ballKsGvAdaper);
        } else if (position == 1) {
            mTextView.setVisibility(View.VISIBLE);
            mGridView.setNumColumns(numColumns2);
            String text = "不同号\n猜同号不同号的组合 奖金80元";
            SpannableString ss = new SpannableString(text);
            ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.deep_txt)), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ss.setSpan(new RelativeSizeSpan(1.2f), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTextView.setText(ss);
            BallKsGvAdaper ballKsGvAdaper = new BallKsGvAdaper(strings2, position);
            mGridView.setAdapter(ballKsGvAdaper);
        }

        return convertView;

    }

    private HashMap<Integer, TextView> mTextViewMap;

    class BallKsGvAdaper extends BaseAdapter {
        private String[] dataArray;
        private List<Integer> list;
        private int prePosition;

        public BallKsGvAdaper(String[] dataArray, int prePosition) {
            this.dataArray = dataArray;
            this.prePosition = prePosition;
            this.list = randomMapBall.get(prePosition);
            mTextViewMap = new HashMap<>();
        }

        @Override
        public int getCount() {
            return dataArray.length;
        }

        @Override
        public Object getItem(int position) {
            return dataArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.item_gv_ks_ball, null);
            LinearLayout mLlBall = (LinearLayout) convertView.findViewById(R.id.ll_ball);
            TextView mTextNum = (TextView) convertView.findViewById(R.id.text_ball);
            TextView mRemark = (TextView) convertView.findViewById(R.id.remark);
            TextView mOmit = (TextView) convertView.findViewById(R.id.omit_text);

            if (isOmit) {
                mOmit.setVisibility(View.VISIBLE);
            } else mOmit.setVisibility(View.GONE);
            if (list != null && list.contains(position)) {
                select(mLlBall, mTextNum, mRemark);
            } else {
                unSelect(mLlBall, mTextNum, mRemark);
            }
            mTextViewMap.put(position, mTextNum);
            mViewMap.put(prePosition, mTextViewMap);//将每个子空控件的位置放进去。
            mTextNum.setText(dataArray[position]);
            if (playMethod.equals("1")) {
                mRemark.setVisibility(View.VISIBLE);
                mRemark.setText(strings2[position]);
            } else mRemark.setVisibility(View.GONE);
            convertView.setTag(prePosition);
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickListener(finalConvertView);
                }
            });
            return convertView;
        }
    }

    public void select(LinearLayout mLlBall, TextView mTextNum, TextView mRemark) {
        mLlBall.setBackgroundResource(R.drawable.shape_btn);
        mTextNum.setTextColor(mContext.getResources().getColor(R.color.white));
        mRemark.setTextColor(mContext.getResources().getColor(R.color.white));
    }

    public void unSelect(LinearLayout mLlBall, TextView mTextNum, TextView mRemark) {
        mLlBall.setBackgroundResource(R.drawable.shape_whitebg_gray);
        mTextNum.setTextColor(mContext.getResources().getColor(R.color.red_txt));
        mRemark.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
    }

    HashMap<String, HashMap<String, String>> hashMap = new HashMap<>();

    private void setMap() {
        try {
            if (TextUtils.isEmpty(omitResult)) {
                return;
            }
            JSONObject omitObject = new JSONObject(omitResult);
            int code = omitObject.getInt("code");
            if (code != 0) {
                return;
            }
            JSONObject jsonObject = omitObject.getJSONObject("data");
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next().toString();
                JSONObject jsonObject1 = jsonObject.getJSONObject(key);
                Iterator<String> keys1 = jsonObject1.keys();
                HashMap<String, String> hashMap1 = new HashMap<>();
                while (keys1.hasNext()) {
                    String key1 = keys1.next().toString();
                    String value1 = jsonObject1.getString(key1);
                    hashMap1.put(key1, value1);
                }
                hashMap.put(key, hashMap1);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
