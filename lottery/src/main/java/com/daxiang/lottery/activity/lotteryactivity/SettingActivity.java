package com.daxiang.lottery.activity.lotteryactivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.utils.AppUtils;
import com.daxiang.lottery.utils.DataCleanManager;
import com.daxiang.lottery.utils.SDCardUtils;
import com.daxiang.lottery.utils.Share;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.daxiang.lottery.constant.Number.CHANNELID;


public class SettingActivity extends BaseActivity implements View.OnClickListener {
    // Content View Elements

    private LinearLayout mLl_suggestion;
    private LinearLayout mLl_share_recommended;
    private LinearLayout mLl_version;
    private TextView mVersion_num;
    private LinearLayout mLl_tel;
    private TextView mTel_num;
    private LinearLayout mLl_service_agreement;
    private LinearLayout mLl_about_us;
    private LinearLayout mLl_app_score;
    private LinearLayout mLl_clear;
    private TextView mText_cache;
    // End Of Content View Elements

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_setting);
        bindViews();
        addListener();
        String versionName = AppUtils.getVersionName(this);
        int versionCode = AppUtils.getVersionCode(this);
        String channel = "";
        if (!TextUtils.isEmpty(CHANNELID) && CHANNELID.length() > 5) {
            channel = Number.CHANNELID.substring(Number.CHANNELID.length() - 2, Number.CHANNELID.length());
        }
        mVersion_num.setText("V" + versionName + "-" + versionCode + "-" + channel);
    }

    private void bindViews() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("设置");
        mTitleBar.setTitleVisibility(true);
        mLl_suggestion = (LinearLayout) findViewById(R.id.ll_suggestion);
        mLl_share_recommended = (LinearLayout) findViewById(R.id.ll_share_recommended);
        mLl_version = (LinearLayout) findViewById(R.id.ll_version);
        mVersion_num = (TextView) findViewById(R.id.version_num);
        mLl_tel = (LinearLayout) findViewById(R.id.ll_tel);
        mTel_num = (TextView) findViewById(R.id.tel_num);
        mLl_service_agreement = (LinearLayout) findViewById(R.id.ll_service_agreement);
        mLl_about_us = (LinearLayout) findViewById(R.id.ll_about_us);
        mLl_clear = (LinearLayout) findViewById(R.id.ll_clear);
        mText_cache = (TextView) findViewById(R.id.text_cache);
        try {
            mText_cache.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addListener() {
        //意见建议
        mLl_suggestion.setOnClickListener(this);
        mLl_about_us.setOnClickListener(this);
        mLl_service_agreement.setOnClickListener(this);
        mLl_tel.setOnClickListener(this);
        mLl_version.setOnClickListener(this);
        mLl_share_recommended.setOnClickListener(this);
        mLl_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_suggestion:
                //意见建议
                startActivity(new Intent(this, SuggestionActivity.class));
                break;
            case R.id.ll_share_recommended:
                //分享推荐
                SharedPreferences userinfo = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                String cxWebUrl = userinfo.getString("html_header", "") + "/mobile/download";
                String shareText = getString(R.string.share_text);
                Share share = new Share(SettingActivity.this, shareText, "", "");
                share.showShare(cxWebUrl);
                break;
            case R.id.ll_version:
                break;
            case R.id.ll_tel:
                tel();
                break;
            case R.id.ll_service_agreement:
                //服务协议
                startActivity(new Intent(this, ServiceAgreementActivity.class));
                break;
            case R.id.ll_about_us:
                //关于我们
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.ll_clear:
                DataCleanManager.clearAllCache(SettingActivity.this);
                try {
                    mText_cache.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void tel() {
        HintDialogUtils.setHintDialog(this);
        HintDialogUtils.setMessage("您将拨打客服电话021-61600381");
        HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
            @Override
            public void callBack(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:021-61600381"));
                if (ActivityCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });
    }


    private String getImage(View v) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        String imageName = sdf.format(new Date());
        //sd卡存在的时候才能存
        if (SDCardUtils.isSDCardMounted()) {
            String fname = SDCardUtils.getSDCardPath() + imageName + ".png";

            View view = v.getRootView();

            view.setDrawingCacheEnabled(true);

            view.buildDrawingCache();

            Bitmap bitmap = view.getDrawingCache();

            if (bitmap != null) {
                // System.out.println("bitmap got!");
                try {
                    FileOutputStream out = new FileOutputStream(fname);

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

                    // System.out.println("file" + fname + "output done.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("bitmap is NULL !");

            }
        }
        return imageName;
    }

}
