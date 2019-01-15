package com.daxiang.lottery.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.cxinterface.SiftInterface;
import com.daxiang.lottery.entity.JczqData;
import com.daxiang.lottery.view.autotextview.AutofitTextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class SiftDialog extends AlertDialog {
    private TextView mAllCheck;
    private LinearLayout mAllClear;
    private TextView mFive;
    private GridView mGridView;
    private Context mContext;
    private TextView mCancel;
    private TextView mConfirm;
    private TextView mTotaleNum;
    private boolean siftFlag;
    private SiftInterface mSiftInterface;
    ArrayList<ArrayList<JczqData.DataBean>> mDataList;
    ArrayList<ArrayList<JczqData.DataBean>> mListData = new ArrayList<>();
    ArrayList<String> adapterList = new ArrayList<>();
    ArrayList<String> clickList = new ArrayList<>();

    ArrayList<String> fiveList = new ArrayList<>();
    SiftDialogAdapter mAdapter;

    public SiftDialog(Context context, ArrayList<ArrayList<JczqData.DataBean>> mDataList, ArrayList<String> siftList, boolean siftFlag, SiftInterface mSiftInterface) {
        super(context);
        this.mDataList = mDataList;
        this.mContext = context;
        this.mSiftInterface = mSiftInterface;
        this.siftFlag = siftFlag;
        clickList.addAll(siftList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sift_dialog);
        initView();
        initData();
        mAdapter = new SiftDialogAdapter();
        mGridView.setAdapter(mAdapter);
        addListener();
    }

    private int totalNum;

    private void initData() {
        ArrayList<String> liansai = new ArrayList<>();
        liansai.add("西甲");
        liansai.add("德甲");
        liansai.add("英超");
        liansai.add("法甲");
        liansai.add("意甲");
        for (int i = 0; i < mDataList.size(); i++) {
            for (int j = 0; j < mDataList.get(i).size(); j++) {
                totalNum++;
                if (!adapterList.contains(mDataList.get(i).get(j).getLeagueShortCn())) {
                    adapterList.add(mDataList.get(i).get(j).getLeagueShortCn());
                }
            }
        }
        //将 出现 的五大联赛放入集合中
        for (int i = 0; i < liansai.size(); i++) {
            if (adapterList.contains(liansai.get(i))) {
                fiveList.add(liansai.get(i));
            }
        }
        String allMatch = "全部联赛(共" + totalNum + "场)";
        int indexOf = allMatch.indexOf("" + totalNum);
        SpannableString ssb = new SpannableString(allMatch);
        ssb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), indexOf, indexOf + ("" + totalNum).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTotaleNum.setText(ssb);
    }

    private void initView() {
        mAllCheck = (TextView) findViewById(R.id.btn_allcheck);
        mAllClear = (LinearLayout) findViewById(R.id.ll_allclear);
        mFive = (TextView) findViewById(R.id.btn_five);
        mGridView = (GridView) findViewById(R.id.gv_sift);
        mCancel = (TextView) findViewById(R.id.cancel);
        mConfirm = (TextView) findViewById(R.id.confirm);
        mTotaleNum = (TextView) findViewById(R.id.totaleNum);
    }

    private void addListener() {
        mAllCheck.setOnClickListener(AllCheckListener);
        mAllClear.setOnClickListener(AllClearListener);
        mFive.setOnClickListener(FiveListener);
        mConfirm.setOnClickListener(ConfirmListener);
        mCancel.setOnClickListener(CancelListener);
    }

    View.OnClickListener CancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickList.clear();
            dismiss();
        }
    };
    View.OnClickListener ConfirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (clickList != null && clickList.size() != 0) {
                for (int j = 0; j < mDataList.size(); j++) {
                    ArrayList<JczqData.DataBean> dataBeen = new ArrayList<>();
                    for (int m = 0; m < mDataList.get(j).size(); m++) {
                        if (clickList.contains(mDataList.get(j).get(m).getLeagueShortCn())) {
                            dataBeen.add(mDataList.get(j).get(m));
                        }
                    }
                    /*
                    * 如果不判断，会出现bug。例如全部选择的时候是三天的比赛，但是某一一项可能就两天的比赛。
                    * 但是mlistdata的size还是3。最后会导致mListData.get(2)再.get(0)的时候会出现错误
                    * */
                    if (dataBeen.size() != 0) {
                        mListData.add(dataBeen);
                    }
                }
                mSiftInterface.siftCallBack(mListData, clickList, siftFlag);
                dismiss();
            } else {
                AlertDialog mDialog = new Builder(mContext)
                        .setTitle("提示")
                        .setMessage("筛选结果为空，请重新选择筛选条件")
                        .setPositiveButton("确定", null)
                        .show();
            }
        }
    };
    View.OnClickListener AllCheckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickList.clear();
            clickList.addAll(adapterList);
            mAdapter = new SiftDialogAdapter();
            mGridView.setAdapter(mAdapter);
        }
    };
    View.OnClickListener AllClearListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickList.clear();
            mAdapter = new SiftDialogAdapter();
            mGridView.setAdapter(mAdapter);
        }
    };
    View.OnClickListener FiveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickList.clear();
            clickList.addAll(fiveList);
            mAdapter = new SiftDialogAdapter();
            mGridView.setAdapter(mAdapter);
        }
    };

    class SiftDialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return adapterList.size();
        }

        @Override
        public Object getItem(int position) {
            return adapterList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.item_sift_dialog, null);
            final AutofitTextView mTextView = (AutofitTextView) convertView.findViewById(R.id.tv_item);
            mTextView.setText(adapterList.get(position));
            if (siftFlag) {
                clickList.addAll(adapterList);
                siftFlag = false;
            }
            if (clickList.contains(adapterList.get(position))) {
                mTextView.setBackgroundResource(R.drawable.shape_btn);
            } else {
                mTextView.setBackgroundResource(R.drawable.shape_whitebg_gray);
            }
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = mTextView.getText().toString();
                    if (clickList.contains(text)) {
                        mTextView.setBackgroundResource(R.drawable.shape_whitebg_gray);
                        clickList.remove(text);
                    } else {
                        mTextView.setBackgroundResource(R.drawable.shape_btn);
                        clickList.add(text);
                    }
                }
            });
            return convertView;
        }
    }
}
