package com.daxiang.lottery.adapter.balladapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
 * Created by Administrator on 2016/8/15 0015.
 */
public class BallLvAdapter extends BaseAdapter {
    int lvNum;
    int gvNum;
    String lotcode;
    boolean isOmit = true;
    Map<Integer, List<Integer>> randomMapBall;
    private Context mContext;
    OnClickBallListener listener;
    String[] str1 = {"万位", "千位", "百位", "十位", "个位"};
    String[] str2 = {"百位", "十位", "个位"};
    String[] str4 = {"十位", "个位"};
    String[] str3 = {"一", "二", "三", "四", "五", "六", "七"};
    Map<Integer, View> map = new HashMap<>();
    //遗漏模型
    String omitResult;
    String playMethod;
    private int blueTxt;
    private int redTxt;

    public BallLvAdapter(int lvNum, int gvNum, String lotcode,
                         OnClickBallListener listener,
                         Map<Integer, List<Integer>> randomMapBall,
                         String omitResult, String playMethod, Context mContext) {
        this.lvNum = lvNum;
        this.gvNum = gvNum;
        this.lotcode = lotcode;
        this.listener = listener;
        this.randomMapBall = randomMapBall;
        this.omitResult = omitResult;
        this.playMethod = playMethod;
        this.mContext = mContext;
        blueTxt = mContext.getResources().getColor(R.color.blue_txt);
        redTxt = mContext.getResources().getColor(R.color.red_txt);
        setMap();
    }

    public void setData(int lvNum, int gvNum, String lotcode,
                        OnClickBallListener listener,
                        Map<Integer, List<Integer>> randomMapBall,
                        String omitResult, String playMethod) {
        this.lvNum = lvNum;
        this.gvNum = gvNum;
        this.lotcode = lotcode;
        this.listener = listener;
        this.randomMapBall = randomMapBall;
        this.omitResult = omitResult;
        this.playMethod = playMethod;
        blueTxt = mContext.getResources().getColor(R.color.blue_txt);
        redTxt = mContext.getResources().getColor(R.color.red_txt);
        setMap();
    }

    public void setData(boolean isOmit) {
        this.isOmit = isOmit;
    }

    @Override
    public int getCount() {
        return lvNum;
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
        View mInflate;
        if (!map.containsKey(position)) {
            mInflate = View.inflate(mContext, R.layout.item_lv_ball, null);
            BallGridView mGridView = (BallGridView) mInflate.findViewById(R.id.gv_red_ball);
            TextView mTextView = (TextView) mInflate.findViewById(R.id.sign);
            TextView mDantuoRule = (TextView) mInflate.findViewById(R.id.dantuo_rule);
            View v = mInflate.findViewById(R.id.cutting_line);
            if (position == lvNum - 1) {
                v.setVisibility(View.GONE);
            }
            //listview每一个条目的前面有一个条，用于提示
            if ("52".equals(lotcode) || "33".equals(lotcode)) {
                mTextView.setText(str2[position]);
                mGridView.setNumColumns(6);
            } else if ("35".equals(lotcode)) {
                mTextView.setText(str1[position]);
                mGridView.setNumColumns(6);
            } else if ("10022".equals(lotcode)) {
                mTextView.setText(str3[position]);
                mGridView.setNumColumns(6);
            } else if ("50".equals(lotcode)) {
                //这里要改00000000000000000000000000000000000000000
                if ("2:1".equals(playMethod) || "10:1".equals(playMethod)) {//二星直选，大小单双
                    mTextView.setText(str4[position]);
                    mGridView.setNumColumns(6);
                } else if ("3:1".equals(playMethod)) {//三星直选
                    mTextView.setText(str2[position]);
                    mGridView.setNumColumns(6);
                } else if ("5:1".equals(playMethod) || "9:1".equals(playMethod)) {//五星直选/通选
                    mTextView.setText(str1[position]);
                    mGridView.setNumColumns(6);
                } else {
                    mTextView.setVisibility(View.GONE);
                    mGridView.setNumColumns(7);
                }
            } else {
                mTextView.setVisibility(View.GONE);
                mGridView.setNumColumns(7);
            }
                    /*大乐透/双色球胆拖玩法
                    * 第一行红球胆码
                    * 第二行红球拖码
                    * 第三行篮球胆码
                    * 第四行蓝球拖码
                    * */
            if ("23529".equals(lotcode)) {

                if ("1:1".equals(playMethod) || "2:1".equals(playMethod)) {
                    //大乐透普通玩法,和追加玩法
                    if (position == 0) {
                        mGridView.setAdapter(new BallGvAdapter(35, position,
                                randomMapBall.get(position), hashMap.get("RED")));
                    } else {
                        mGridView.setAdapter(new BallGvAdapter(12, position, randomMapBall.get(position),
                                hashMap.get("BLUE")));
                    }
                } else {
                    mDantuoRule.setVisibility(View.VISIBLE);
                    if (position == 0) {
                        mDantuoRule.setText("前区胆码区，请选择1-4个胆码");
                        mGridView.setAdapter(new BallGvAdapter(35, position,
                                randomMapBall.get(position), hashMap.get("RED")));
                    } else if (position == 1) {
                        mDantuoRule.setText("前区拖码区，请选择2-34个拖码");
                        mGridView.setAdapter(new BallGvAdapter(35, position,
                                randomMapBall.get(position), hashMap.get("RED")));
                    } else if (position == 2) {
                        mDantuoRule.setText("后区胆码区，请选择0-1个胆码");
                        mGridView.setAdapter(new BallGvAdapter(12, position, randomMapBall.get(position),
                                hashMap.get("BLUE")));
                    } else {
                        mDantuoRule.setText("后区拖码区，请选择2-11个拖码");
                        mGridView.setAdapter(new BallGvAdapter(12, position, randomMapBall.get(position), hashMap.get("BLUE")));
                    }
                }

            } else if ("51".equals(lotcode)) {
                if ("1:1".equals(playMethod)) {
                    if (position == 0) {
                        mGridView.setAdapter(new BallGvAdapter(33, position, randomMapBall.get(position), hashMap.get("RED")));
                    } else {
                        mGridView.setAdapter(new BallGvAdapter(16, position, randomMapBall.get(position), hashMap.get("BLUE")));
                    }
                } else {
                    mDantuoRule.setVisibility(View.VISIBLE);
                    if (position == 0) {
                        mDantuoRule.setText("前区胆码区，请选择1-5个胆码");
                        mGridView.setAdapter(new BallGvAdapter(33, position, randomMapBall.get(position), hashMap.get("RED")));
                    } else if (position == 1) {
                        mDantuoRule.setText("前区拖码区，请选择2-32个拖码");
                        mGridView.setAdapter(new BallGvAdapter(33, position, randomMapBall.get(position), hashMap.get("RED")));
                    } else {
                        mDantuoRule.setText("至少选择1个蓝球");
                        mGridView.setAdapter(new BallGvAdapter(16, position, randomMapBall.get(position), hashMap.get("BLUE")));
                    }
                }

            } else if ("23528".equals(lotcode)) {
                HashMap<String, String> hashMap1 = new HashMap<>();
                hashMap1 = hashMap.get("ZHU");
                //七乐彩，胆拖玩法，有两行
                if ("1:1".equals(playMethod)) {
                    mGridView.setAdapter(new BallGvAdapter(gvNum, position, randomMapBall.get(position), hashMap1));
                } else {
                    mDantuoRule.setVisibility(View.VISIBLE);
                    if (position == 0) {
                        mDantuoRule.setText("至少选择一个胆码");
                        mGridView.setAdapter(new BallGvAdapter(gvNum, position, randomMapBall.get(position), hashMap1));
                    } else {
                        mDantuoRule.setText("请选择2-29个拖码");
                        mGridView.setAdapter(new BallGvAdapter(gvNum, position, randomMapBall.get(position), hashMap1));
                    }
                }

            } else if ("50".equals(lotcode)) {//时时彩
                HashMap<String, String> hashMap1 = new HashMap<>();
                mGridView.setAdapter(new BallGvAdapter(gvNum, position, randomMapBall.get(position), hashMap1));
            } else {

                HashMap<String, String> hashMap1 = new HashMap<>();
                //如果不只有1行那么肯定是直选，否则为组选。其中十一运里面前一直选也是一行
                if (lvNum == 1) {
                    mTextView.setVisibility(View.GONE);
                    if ("33".equals(lotcode) || "52".equals(lotcode)) {
                        hashMap1 = hashMap.get("ZHU");
                    } else if ("21406".equals(lotcode)) {
                        if ("11:01".equals(playMethod)) {
                            hashMap1 = hashMap.get("ZHU2");
                        } else if ("12:01".equals(playMethod)) {
                            hashMap1 = hashMap.get("ZHU3");
                        } else if ("01:01".equals(playMethod)) {
                            hashMap1 = hashMap.get("ZHI1");
                        } else {
                            hashMap1 = hashMap.get("ZHU");
                        }
                    }
                } else {
                    switch (position) {
                        case 0:
                            hashMap1 = hashMap.get("ZHI1");
                            break;
                        case 1:
                            hashMap1 = hashMap.get("ZHI2");
                            break;
                        case 2:
                            hashMap1 = hashMap.get("ZHI3");
                            break;
                        case 3:
                            hashMap1 = hashMap.get("ZHI4");
                            break;
                        case 4:
                            hashMap1 = hashMap.get("ZHI5");
                            break;
                        case 5:
                            hashMap1 = hashMap.get("ZHI6");
                            break;
                        case 6:
                            hashMap1 = hashMap.get("ZHI7");
                            break;
                    }
                }
                mGridView.setAdapter(new BallGvAdapter(gvNum, position, randomMapBall.get(position), hashMap1));
            }
            map.put(position, mInflate);
        } else {
            mInflate = map.get(position);
        }

        return mInflate;

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

    class BallGvAdapter extends BaseAdapter {
        int gvNum;
        private Context mContext;
        int prePosition;
        List<Integer> list;
        //遗漏模型
        HashMap<String, String> hashMap;

        public BallGvAdapter(int gvNum, int prePosition, List<Integer> list,
                             HashMap<String, String> hashMap) {
            this.gvNum = gvNum;
            this.prePosition = prePosition;
            this.list = list;
            this.hashMap = hashMap;
        }

        @Override
        public int getCount() {
            return gvNum;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            mContext = parent.getContext();
            final View inflate = View.inflate(mContext, R.layout.item_gv_ball, null);
            final TextView mBall = (TextView) inflate.findViewById(R.id.text_ball);
            TextView mTextView = (TextView) inflate.findViewById(R.id.omit_text);
            if (isOmit) mTextView.setVisibility(View.VISIBLE);
            else mTextView.setVisibility(View.GONE);
            if (gvNum == 12 || gvNum == 16) {
                mBall.setBackgroundResource(R.drawable.widget_ball_blue_frame);
                mBall.setTextColor(blueTxt);
                mBall.setTag(prePosition + "-blue");
            } else {
                mBall.setBackgroundResource(R.drawable.widget_ball_red_frame);
                mBall.setTextColor(redTxt);
                mBall.setTag(prePosition + "-red");
            }
            if ("35".equals(lotcode) || "33".equals(lotcode) || "10022".equals(lotcode) || "52".equals(lotcode)) {
                mBall.setText(position + "");
                //随机选择的情况
                if (list != null && list.contains(position)) {
                    setSelect(mBall);
                } else {
                    setUnSelect(mBall);
                }
            } else if ("50".equals(lotcode)) {
                if (gvNum == 4) {//时时彩的大小单双
                    mBall.setText(getDxds(position));
                    //随机选择的情况
                    if (list != null && list.contains(getDxds(position))) {
                        setSelect(mBall);
                    } else {
                        setUnSelect(mBall);
                    }
                } else {
                    mBall.setText(position + "");
                    //随机选择的情况
                    if (list != null && list.contains(position)) {
                        setSelect(mBall);
                    } else {
                        setUnSelect(mBall);
                    }
                }
            } else {
                if (position + 1 < 10) {
                    mBall.setText("0" + (position + 1));
                } else {
                    mBall.setText("" + (position + 1));
                }
                //随机选择的情况
                if (list != null && list.contains(position + 1)) {
                    setSelect(mBall);
                } else {
                    setUnSelect(mBall);
                }
            }

            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickListener(mBall);
                }
            });
//遗漏
            String text = mBall.getText().toString();
            if (text.length() < 2) {
                text = "0" + text;
            }
            if (hashMap != null) {
                mTextView.setText(TextUtils.isEmpty(hashMap.get(text)) ? "" : hashMap.get(text));
            }
            return inflate;
        }

        private String getDxds(int position) {
            switch (position) {
                case 0:
                    return "大";
                case 1:
                    return "小";
                case 2:
                    return "单";
                case 3:
                    return "双";
            }
            return "";
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.unregisterDataSetObserver(observer);
            }
        }
    }

    public void setSelect(TextView mBall) {
        String tag = (String) mBall.getTag();
        if (tag == null) return;
        String[] split = tag.split("\\-");
        if (tag.length() < 2) return;
        String color = split[1];
        if ("red".equals(color)) {
            mBall.setBackgroundResource(R.drawable.widget_ball_red);
            mBall.setTextColor(Color.WHITE);
        } else {
            mBall.setBackgroundResource(R.drawable.widget_ball_blue);
            mBall.setTextColor(Color.WHITE);
        }
    }

    public void setUnSelect(TextView mBall) {
        String tag = (String) mBall.getTag();
        if (tag == null) return;
        String[] split = tag.split("\\-");
        if (tag.length() < 2) return;
        String color = split[1];
        if ("red".equals(color)) {
            mBall.setBackgroundResource(R.drawable.widget_ball_red_frame);
            mBall.setTextColor(redTxt);
        } else {
            mBall.setBackgroundResource(R.drawable.widget_ball_blue_frame);
            mBall.setTextColor(blueTxt);
        }
    }
}
