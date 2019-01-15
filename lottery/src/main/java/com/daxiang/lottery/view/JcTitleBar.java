package com.daxiang.lottery.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.JczqData;

import java.util.ArrayList;
/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class JcTitleBar extends LinearLayout {
    private View mInflate;
    public ImageView mTitlebar_back;
    public TextView mTextTitlebar;
    public ImageView mImageTitlebar;
    public TextView mTitlebarSift;
    public LinearLayout mPlayMethodTitleBar;
    public TextView mMiddleText;
    public ImageView mImgeOrder;
    Context mContext;
    ArrayList<ArrayList<JczqData.DataBean>> mListData;

    public JcTitleBar(Context context) {
        super(context);
        mContext = context;
        mInflate = View.inflate(context, R.layout.titlebar_jc, this);
    }

    public JcTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflate = View.inflate(context, R.layout.titlebar_jc, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitlebar_back = (ImageView) mInflate.findViewById(R.id.titlebar_back);
        mTextTitlebar = (TextView) mInflate.findViewById(R.id.text_jc_titlebar);
        mImageTitlebar = (ImageView) mInflate.findViewById(R.id.image_jc_titlebar);
        mTitlebarSift = (TextView) mInflate.findViewById(R.id.titlebar_sift);
        mPlayMethodTitleBar = (LinearLayout) mInflate.findViewById(R.id.ll_jc_titlebar);
        mImgeOrder= (ImageView) mInflate.findViewById(R.id.title_order);
        mMiddleText = (TextView) mInflate.findViewById(R.id.titlebar_middle);
        addListener();
    }

    private void addListener() {
        //mTitlebarSift.setOnClickListener(SiftClickListener);
        mTitlebar_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });
    }

    /*OnClickListener SiftClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mListData!=null&&mListData.size()!=0){
                SiftDialog mDialog=new SiftDialog(mContext,mListData);
                mDialog.show();
            }
        }
    };*/

    public void setText(String title) {
        mTextTitlebar.setText(title);
    }

    public void setSelect(ArrayList<ArrayList<JczqData.DataBean>> mListData) {
        this.mListData = mListData;
    }
}
