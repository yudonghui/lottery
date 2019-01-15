package com.daxiang.lottery.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.entity.PictureData;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class NewsAdapter extends PagerAdapter {
    List<ImageView> listImage;
    List<PictureData.DataBean.ItemsBean> results;
    Context mContext;

    public NewsAdapter(List<ImageView> listImage, List<PictureData.DataBean.ItemsBean> results, Context mContext) {
        this.listImage = listImage;
        this.results = results;
        this.mContext = mContext;
    }

    public void setData(List<PictureData.DataBean.ItemsBean> results) {
        this.results = results;
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView;
        if (position <= 0) {
            imageView = listImage.get(0);
        } else
            imageView = listImage.get(position);
        container.addView(imageView);
        // Log.e("position))))))))))))))", "  " + position);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (results.size() <= 0 || position > results.size()||position<=0) {
                    return;
                }
                PictureData.DataBean.ItemsBean resultsBean = results.get(position - 1);
                String remarks = resultsBean.getRemarks();
                new NetWorkData(mContext).setSkip(remarks);
               /* String[] link = remarks.split("\\|");
                switch (link[0]) {
                    case "-1":
                        //浏览器自己打开
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(link[1]);
                        intent.setData(content_url);
                        mContext.startActivity(intent);
                        break;
                    case "1":
                        //跳转某一个购彩界面界面
                        String lotcode = link[1];
                        if ("1000".equals(lotcode)) {
                            Intent intent2 = new Intent(mContext, JczqActivity.class);
                            intent2.putExtra("lotcode", "42");
                            intent2.putExtra("bunch", false);
                            mContext.startActivity(intent2);
                        } else if ("42".equals(lotcode)) {
                            Intent intent2 = new Intent(mContext, JczqActivity.class);
                            intent2.putExtra("lotcode", lotcode);
                            intent2.putExtra("bunch", true);
                            mContext.startActivity(intent2);
                        } else if ("11".equals(lotcode) || "19".equals(lotcode)) {
                            Intent intent2 = new Intent(mContext, SfcAndRjcActivity.class);
                            intent2.putExtra("lotcode", lotcode);
                            mContext.startActivity(intent2);
                        } else if ("1001".equals(lotcode)) {
                            Intent intent2 = new Intent(mContext, JclqActivity.class);
                            intent2.putExtra("lotcode", "43");
                            intent2.putExtra("bunch", false);
                            mContext.startActivity(intent2);
                        } else if ("43".equals(lotcode)) {
                            Intent intent2 = new Intent(mContext, JclqActivity.class);
                            intent2.putExtra("lotcode", lotcode);
                            intent2.putExtra("bunch", true);
                            mContext.startActivity(intent2);
                        } else {
                            Intent intent2 = new Intent(mContext, NumberActivity.class);
                            intent2.putExtra("lotcode", lotcode);
                            intent2.putExtra("whereFlag", true);
                            mContext.startActivity(intent2);
                        }
                        break;
                    case "0":
                        //本app内打开页面
                        Intent intent1 = new Intent(mContext, PictureActivity.class);
                        intent1.putExtra("url", link[1]);
                        mContext.startActivity(intent1);
                        break;
                    case "2":
                        //跳转界面
                        IntentSkip.skip(mContext, link[1]);

                }*/
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(listImage.get(position));
    }
}
