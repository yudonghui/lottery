package com.daxiang.lottery.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.BankCardBean;

import java.util.List;

import static com.daxiang.lottery.R.id.bank_name;

/**
 * @author yudonghui
 * @date 2017/9/22
 * @describe May the Buddha bless bug-free!!!
 */
public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.ViewHolder> {
    List<BankCardBean.DataBean.CardInfoBean> mList;

    public BankCardAdapter(List<BankCardBean.DataBean.CardInfoBean> mList) {
        this.mList = mList;
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_card_form, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(mList.get(position), position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLl_background;
        private TextView mBank_name;
        private TextView mBank_type;
        private TextView mBank_card_num;

        public ViewHolder(View itemView) {
            super(itemView);
            mLl_background = (LinearLayout) itemView.findViewById(R.id.ll_background);
            mBank_name = (TextView) itemView.findViewById(bank_name);
            mBank_type = (TextView) itemView.findViewById(R.id.bank_type);
            mBank_card_num = (TextView) itemView.findViewById(R.id.bank_card_num);
        }

        public void setData(BankCardBean.DataBean.CardInfoBean cardInfoBean, int position) {
            int res = position % 3;
            if (res == 0) mLl_background.setBackgroundResource(R.mipmap.red_bg);
            else if (res == 1) mLl_background.setBackgroundResource(R.mipmap.yellow_bg);
            else mLl_background.setBackgroundResource(R.mipmap.blue_bg);

            String bank_name = cardInfoBean.getBank_name();
            String card_no = cardInfoBean.getCard_no();
            String card_type = cardInfoBean.getCard_type();
            mBank_name.setText(TextUtils.isEmpty(bank_name) ? "--" : bank_name);
            if (!TextUtils.isEmpty(card_no))
                mBank_card_num.setText("**** **** **** ***" + card_no);
            if (card_type != null) {
                switch (card_type) {
                    case "2"://借记卡
                        mBank_type.setText("借记卡");
                        break;
                    case "3"://信用卡
                        mBank_type.setText("信用卡");
                        break;
                }
            }
        }
    }

}
