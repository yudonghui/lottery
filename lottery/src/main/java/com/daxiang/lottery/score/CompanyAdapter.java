package com.daxiang.lottery.score;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.OddsData;

import java.util.List;

/**
 * Created by Android on 2018/3/29.
 */

public class CompanyAdapter extends BaseAdapter {
    Context mContext;
    List<OddsData.DataBean.ItemsBean> mCompanyList;
    String cId;

    public CompanyAdapter(Context mContext, List<OddsData.DataBean.ItemsBean> mCompanyList) {
        this.mContext = mContext;
        this.mCompanyList = mCompanyList;
    }

    public void setSelect(String cId) {
        this.cId = cId;
    }

    @Override
    public int getCount() {
        if (mCompanyList == null) return 0;
        return mCompanyList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCompanyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.item_company, null);
        TextView mCompanyName = (TextView) convertView.findViewById(R.id.company_name);
        OddsData.DataBean.ItemsBean itemsBean = mCompanyList.get(position);
        String name = itemsBean.getName();
        String id = itemsBean.getId();
        mCompanyName.setText(TextUtils.isEmpty(name) ? "--" : name);
        if (!TextUtils.isEmpty(id) && id.equals(cId)) {
            mCompanyName.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            mCompanyName.setBackgroundColor(mContext.getResources().getColor(R.color.blue_tablayout_bg));
        }
        return convertView;
    }
}
