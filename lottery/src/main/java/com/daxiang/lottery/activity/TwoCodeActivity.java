package com.daxiang.lottery.activity;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.utils.BitmapUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.Share;
import com.daxiang.lottery.view.TitleBar;
import com.uuzuche.lib_zxing.activity.CodeUtils;


public class TwoCodeActivity extends BaseNoTitleActivity {
    private String cxWebUrl;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_code);
        SharedPreferences userinfo = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        cxWebUrl = userinfo.getString("html_header", "") + "/mobile/download";
        TextView mSave = (TextView) findViewById(R.id.save_two_code);
        ImageView mTwoCode = (ImageView) findViewById(R.id.two_code);
        TitleBar mTitleBar = (TitleBar) findViewById(R.id.titlBar);
        mTitleBar.mShare.setVisibility(View.VISIBLE);
        mTitleBar.setTitle("二维码");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1112);
                }else {
                    Share share = new Share(TwoCodeActivity.this, getString(R.string.share_text), "", "");
                    share.showShare(cxWebUrl);
                }

            }
        });
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int resize = DisplayUtil.dip2px(180);
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.circle_logo);
        image = CodeUtils.createImage(cxWebUrl, resize, resize, mBitmap);
        mTwoCode.setImageBitmap(image);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapUtils.saveImageToGallery(TwoCodeActivity.this, image);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1111) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                BitmapUtils.saveImage(mContext, image);
            }
        }else if (requestCode==1112){//分享的时候需要用到读写权限
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Share share = new Share(TwoCodeActivity.this, getString(R.string.share_text), "", "");
                share.showShare(cxWebUrl);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  Log.e("生命周期", "onresume");
    }

    @Override
    protected void onStop() {
        super.onStop();
       // Log.e("生命周期", "onstop");
        // Toast.makeText(TwoCodeActivity.this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // Log.e("生命周期", "onRestart");
        //Toast.makeText(TwoCodeActivity.this,"onRestart",Toast.LENGTH_SHORT).show();
    }
}
