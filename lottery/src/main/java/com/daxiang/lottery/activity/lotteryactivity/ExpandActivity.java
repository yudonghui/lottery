package com.daxiang.lottery.activity.lotteryactivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.utils.BitmapUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.Share;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class ExpandActivity extends BaseActivity {
    private TextView mExpand_url;
    private TextView mText_copy;
    private TextView mTwoCode;
    private LinearLayout mLl_down_line;
    private LinearLayout mLl_money_find;
    private LinearLayout mLl_rebate;
    private FrameLayout mTwo_code_bg;
    private ImageView mTwo_code;
    private TextView mSave_two_code;
    private Context mContext;
    private View mInflate;
    private Dialog mDialog;
    private Bitmap mBitmap;
    private String cxWebUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_expand);
        mContext = this;
        initView();
        addListener();
    }

    private void initView() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle("推广服务");
        mTitleBar.mShare.setVisibility(View.VISIBLE);
        mExpand_url = (TextView) findViewById(R.id.expand_url);
        mTwoCode = (TextView) findViewById(R.id.two_code);
        mText_copy = (TextView) findViewById(R.id.text_copy);
        mLl_down_line = (LinearLayout) findViewById(R.id.ll_down_line);
        mLl_money_find = (LinearLayout) findViewById(R.id.ll_money_find);
        mLl_rebate = (LinearLayout) findViewById(R.id.ll_rebate);

        mInflate = View.inflate(mContext, R.layout.dialog_twocode, null);
        mTwo_code_bg = (FrameLayout) mInflate.findViewById(R.id.two_code_bg);
        mTwo_code = (ImageView) mInflate.findViewById(R.id.two_code);
        mSave_two_code = (TextView) mInflate.findViewById(R.id.save_two_code);
        int displayWidth = DisplayUtil.getDisplayWidth();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(displayWidth - 20, (displayWidth - 20) * 85 / 59);
        //mTwo_code_bg.setLayoutParams(layoutParams);
        mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mInflate, layoutParams);
        mDialog.setCanceledOnTouchOutside(true);
        SharedPreferences userinfo = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        cxWebUrl = userinfo.getString("html_header", "") + "/register?parentUserId=" + LotteryApp.uid;
        mExpand_url.setText(cxWebUrl);
    }

    private void addListener() {
        //点击复制推广链接
        mText_copy.setOnClickListener(CopyListener);
        //二维码推广
        mTwoCode.setOnClickListener(TwoCodeListener);
        mTwo_code_bg.setOnClickListener(DissmissDialog);
        mTwo_code.setOnClickListener(DissmissDialog);
        mSave_two_code.setOnClickListener(SaveTwoCodeListener);//保存二维码至相册
        //我的下线
        mLl_down_line.setOnClickListener(DownLineListener);
        //佣金查询
        mLl_money_find.setOnClickListener(MoneyFindListener);
        //我的返点
        mLl_rebate.setOnClickListener(RebateListener);
        //点击分享
        mTitleBar.mShare.setOnClickListener(ShareListener);
    }

    View.OnClickListener ShareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Share share = new Share(mContext, getString(R.string.share_text), "", "");
            share.showShare(cxWebUrl);
        }
    };
    View.OnClickListener CopyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (TextUtils.isEmpty(mExpand_url.getText())) {
                Toast.makeText(mContext, "推广链接为空！", Toast.LENGTH_SHORT).show();
            } else {
                String trim = mExpand_url.getText().toString().trim();
                ClipboardManager systemService = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                systemService.setText(trim);
                Toast.makeText(mContext, "复制成功！", Toast.LENGTH_SHORT).show();
                //  share("推广链接", "注册彩象彩票，赢取百万大奖！", Url.H5EXPAND_URL + LotteryApp.uid);
            }

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1111) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                BitmapUtils.saveImage(mContext, mBitmap);
            }
        }
    }

    View.OnClickListener DissmissDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };
    View.OnClickListener SaveTwoCodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mBitmap == null) {
                Toast.makeText(mContext, "未获取到二维码", Toast.LENGTH_SHORT).show();
            } else
                BitmapUtils.saveImageToGallery(mContext, mBitmap);
        }
    };
    View.OnClickListener TwoCodeListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(mExpand_url.getText())) {
                Toast.makeText(mContext, "推广链接为空！", Toast.LENGTH_SHORT).show();
            } else {

                final int resize = DisplayUtil.dip2px(175);
                String trim = mExpand_url.getText().toString().trim();
                Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.circle_logo);
                mBitmap = CodeUtils.createImage(trim, resize, resize, image);
                mTwo_code.setImageBitmap(mBitmap);
                mDialog.show();
                //  share("推广链接", "注册彩象彩票，赢取百万大奖！", Url.H5EXPAND_URL + LotteryApp.uid);
            }
        }
    };
    View.OnClickListener DownLineListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, DownLineActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener MoneyFindListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, RebateDetailActivity.class);
            //标志位
            intent.putExtra("flag", true);
            intent.putExtra("userId", LotteryApp.uid);
            intent.putExtra("parentUserId", LotteryApp.uid);
            startActivity(intent);
        }
    };
    View.OnClickListener RebateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, MyRebateActivity.class);
            startActivity(intent);
        }
    };

}
