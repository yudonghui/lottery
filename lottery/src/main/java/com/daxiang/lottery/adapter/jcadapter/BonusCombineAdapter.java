package com.daxiang.lottery.adapter.jcadapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.ChoosedContentFormBean;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class BonusCombineAdapter extends BaseAdapter {
    private Context mContext;
    // int sumShakes;
    //int shakes;
    int mMutil;
    // averagePrize;
    // int mMultis = 0;
    // ArrayList<List<ChoosedContentFormBean>> formList;
    ArrayList<List<ChoosedContentFormBean>> orderFormList;
    //每注奖金
    // ArrayList<Float> oddsList = new ArrayList<>();
    // ArrayList<Float> orderOddsList;
    float[] orderOddsArray;
    //倍数
    // ArrayList<Integer> mMultiList = new ArrayList<>();
    //int[] mMultiArray;
    // ArrayList<Integer> orderMultiList;
    //存放每一个条目的倍数
    int[] orderMultiArray;
    // float[] oddsArray;
    //int[] sort;
    //float sum = 0;
    //HashMap<Float, Integer> map = new HashMap<>();
    //float[] moneyArray;

 /*   public BonusCombineAdapter(ArrayList<List<ChoosedContentFormBean>> formList, int shakes, int mMutil) {
        this.formList = formList;
        this.shakes = shakes;
        this.mMutil = mMutil;
        sumShakes = mMutil * shakes;
        oddsArray = new float[formList.size()];
        mMultiArray = new int[formList.size()];
        //每注奖金
        bonusData();
        //倍数
        mutilData();
        //将数据进行排序,得到对应的索引
        sort = new int[formList.size()];
        for (int index = 0; index < oddsArray.length - 1; index++) {     // 每一趟选择的最小数字依次放在左边
            int lowIndex = index;   // 每一趟的最小数据所对应的索引
            for (int j = index + 1; j < oddsArray.length; j++) {     // 得到每一趟比较的最小值的索引
                if (oddsArray[j] < oddsArray[lowIndex]) {
                    lowIndex = j;
                }
            }
            float temp = oddsArray[lowIndex];     // 将本趟的最小值放在左边
            oddsArray[lowIndex] = oddsArray[index];
            oddsArray[index] = temp;
        }
        //通过排序后的数组取出对应的索引，放入另外一个数组中
        for (int i = 0; i < oddsArray.length; i++) {
            float v = oddsArray[i];
            sort[i] = map.get(v);
        }
        //对所有的数据进行排序
        orderList();
    }


    public void mutilData() {
        //平稳盈利时候平均的奖金，准备倍数的数据
        averagePrize = sumShakes / sum;
        for (int i = 0; i < oddsList.size(); i++) {
            int rint = (int) Math.rint(averagePrize / oddsList.get(i));
            //不能出现倍数为0的现象
            if (rint <= 0) {
                rint = 1;
            }
            mMultis += rint;
            mMultiArray[i] = rint;
        }
    }

    public void bonusData() {
        //准备每注奖金的数据
        for (int i = 0; i < formList.size(); i++) {
            float sumOdds = 1;
            for (int j = 0; j < formList.get(i).size(); j++) {
                sumOdds *= Float.parseFloat(formList.get(i).get(j).getOdds());
            }
            //每注奖金
            oddsList.add(sumOdds * 2);
            map.put(sumOdds * 2, i);
            oddsArray[i] = sumOdds * 2;
            sum += 1 / (sumOdds * 2);
        }
    }*/
    /**
     * 监听倍数数值变化
     */
    public OnTimesChangeListner onTimesChangeListener;

    public void setOnTimesChangeListener(OnTimesChangeListner onTimesChangeListener) {
        this.onTimesChangeListener = onTimesChangeListener;
    }

    public BonusCombineAdapter(ArrayList<List<ChoosedContentFormBean>> orderFormList, float[] orderOddsArray, int[] orderMultiArray, int mMutil) {
        this.orderFormList = orderFormList;
        this.orderOddsArray = orderOddsArray;
        this.orderMultiArray = orderMultiArray;
        this.mMutil = mMutil;
    }

   /* public void orderList() {
        moneyArray = new float[formList.size()];
        for (int i = 0; i < sort.length; i++) {
            orderFormList.add(formList.get(sort[i]));
            if (i != 0) {
                orderMultiList.add(mMultiArray[sort[i]]);
            } else {
                orderMultiList.add(sumShakes - (mMultis - mMultiArray[sort[i]]));
            }
            orderOddsList.add(oddsList.get(sort[i]));
            moneyArray[i] = orderMultiList.get(i) * orderOddsList.get(i);
        }
    }*/

    @Override
    public int getCount() {
        //  if(orderFormList.size()==0||orderMultiArray.length==0||orderOddsArray.length==0){
        if (orderFormList==null || orderMultiArray==null || orderOddsArray==null) {
            return 0;
        } else {
            return orderFormList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return orderFormList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        mContext = parent.getContext();
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_bonus_optimize, null);
        mViewHolder = new ViewHolder();
        mViewHolder.btn_bonus_plus = (Button) convertView.findViewById(R.id.btn_bonus_plus);
        mViewHolder.btn_bonus_reduction = (Button) convertView.findViewById(R.id.btn_bonus_reduction);
        mViewHolder.edit_buy_times = (EditText) convertView.findViewById(R.id.edit_buy_times);
        mViewHolder.lv_bet_combine = (LinearLayout) convertView.findViewById(R.id.lv_bet_combine);
        mViewHolder.tv_per_bonus = (TextView) convertView.findViewById(R.id.tv_per_bonus);
        mViewHolder.tv_bonus_money = (TextView) convertView.findViewById(R.id.tv_bonus_money);
        addView(position, mViewHolder);
        addListener(mViewHolder, position);
        mViewHolder.edit_buy_times.addTextChangedListener(new MyTextWatcher(convertView, position));
        return convertView;
    }

    private void addListener(final ViewHolder mViewHolder, final int position) {
        //加
        mViewHolder.btn_bonus_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curValue = Integer.valueOf(mViewHolder.edit_buy_times.getText().toString());
                curValue++;
                mViewHolder.edit_buy_times.setText(String.valueOf(curValue));
                mViewHolder.tv_bonus_money.setText(String.format("%.2f", curValue * orderOddsArray[position]));
                orderMultiArray[position] = curValue;
                if (onTimesChangeListener != null) {
                    onTimesChangeListener.onTimesChangeListener(orderMultiArray, position);
                }
            }
        });
        //减
        mViewHolder.btn_bonus_reduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                int curValue = Integer.valueOf(mViewHolder.edit_buy_times.getText().toString());
                if (curValue == 0)
                    return;
                curValue--;
                mViewHolder.edit_buy_times.setText(String.valueOf(curValue));
                mViewHolder.tv_bonus_money.setText(String.format("%.2f", curValue * orderOddsArray[position]));
                orderMultiArray[position] = curValue;
                if (onTimesChangeListener != null) {
                    onTimesChangeListener.onTimesChangeListener(orderMultiArray, position);
                }
            }
        });
    }

    private void addView(int position, ViewHolder mViewHolder) {
        List<ChoosedContentFormBean> beanList = orderFormList.get(position);
        // float odds = 1;
        for (int i = 0; i < beanList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_bonus_optimize_ll, null);
            TextView mName = (TextView) view.findViewById(R.id.name);
            TextView mTitleOdds = (TextView) view.findViewById(R.id.title_odds);
            mName.setText(beanList.get(i).getHome());
            mTitleOdds.setText(beanList.get(i).getContent() + "\n" + beanList.get(i).getOdds());
            mViewHolder.lv_bet_combine.addView(view);
            //odds *= Float.parseFloat(beanList.get(i).getOdds());
        }
        mViewHolder.tv_per_bonus.setText(String.format("%.2f", orderOddsArray[position]));
        if (mMutil != 1) {
            mViewHolder.edit_buy_times.setText(orderMultiArray[position] + "");
            mViewHolder.tv_bonus_money.setText(String.format("%.2f", orderOddsArray[position] * orderMultiArray[position]));
        } else {
            mViewHolder.edit_buy_times.setText("1");
            mViewHolder.tv_bonus_money.setText(String.format("%.2f", orderOddsArray[position]));
        }

    }

    private class MyTextWatcher implements TextWatcher {
        private View view;
        private int position;

        private MyTextWatcher(View view, int position) {
            this.view = view;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
            // do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            // do nothing
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            String timesString = s.toString().trim();
           // if(timesString.charAt(0)=='0'){}
            int times = timesString.equals("") ? 0 : Integer.valueOf(timesString);
            EditText timesEditText = (EditText) view.findViewById(R.id.edit_buy_times);
            if (times < 0 || times > 9999) {
                timesEditText.setText("0");
                times = 0;
                Toast.makeText(mContext, "倍数范围在0-9999之间哦", Toast.LENGTH_SHORT).show();
                // ToastUtil.showShortMsg(mContext, "倍数范围在1-9999之间哦");
            }
          //  if(times.)
            orderMultiArray[position] = times;
            TextView tv_per_bonus = (TextView) view.findViewById(R.id.tv_bonus_money);
            tv_per_bonus.setText(String.format("%.2f", times * orderOddsArray[position]));
            if (onTimesChangeListener != null) {
                onTimesChangeListener.onTimesChangeListener(orderMultiArray, position);
            }
        }

    }

    public interface OnTimesChangeListner {
        public void onTimesChangeListener(int[] orderMultiArray, int position);
    }

    class ViewHolder {
        private LinearLayout lv_bet_combine;
        private TextView tv_per_bonus;
        private Button btn_bonus_reduction;
        private EditText edit_buy_times;
        private Button btn_bonus_plus;
        private TextView tv_bonus_money;
    }
}
