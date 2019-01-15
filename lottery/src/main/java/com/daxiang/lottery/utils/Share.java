package com.daxiang.lottery.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class Share {


    public final static int QRCODE_TYPE = 0;

    public final static int NONE_QRCODE_TYPE = 1;


    private Context mContext;

    private String shareText;

    private String titleUrl;

    private String imagePath;

    private String comment;


    public Share(Context context, String shareText, String imagePath,
                 String comment) {
        this.mContext = context;
        this.shareText = shareText;
        this.imagePath = imagePath;
        this.comment = comment;
    }

    public Share(Context mContext) {
        this.mContext = mContext;
    }

    public void showShare(final String url) {
        /*OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (WechatMoments.NAME.equals(platform.getName())
                        || Wechat.NAME.equals(platform.getName())) {

                    paramsToShare.setTitle("彩象彩票");
                    paramsToShare.setText(shareText);
                    paramsToShare.setUrl(url);
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                } else if (QQ.NAME.equals(platform.getName()) || QZone.NAME.equals(platform.getName())) {
                    paramsToShare.setTitle("彩象彩票");
                    paramsToShare.setText(shareText);
                    paramsToShare.setTitleUrl(url);
                    paramsToShare.setSite(mContext.getString(R.string.app_name));
                    paramsToShare.setSiteUrl(url);
                } else {
                    // paramsToShare.setTitle(title + "\n" + text);
                    paramsToShare.setText(shareText + "\n" + url);
                    // paramsToShare.setUrl(url);
                }
            }
        });
        oks.show(mContext);*/
        UMImage thumb = new UMImage(mContext, R.mipmap.icon_logo);
        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle("彩象彩票");
        umWeb.setThumb(thumb);//缩略图
        umWeb.setDescription(shareText);
        new ShareAction((Activity) mContext)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .withMedia(umWeb)
                .setCallback(shareListener)//回调监听器
                .open();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(mContext, "分享成功！", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(mContext, "分享失败！" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mContext, "取消分享！", Toast.LENGTH_LONG).show();

        }
    };

    public void showShare(final String title, final String text, final String url) {
        UMImage thumb = new UMImage(mContext, R.mipmap.icon_logo);
        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(TextUtils.isEmpty(title) ? "彩象彩票" : title);
        umWeb.setThumb(thumb);//缩略图
        umWeb.setDescription(TextUtils.isEmpty(text) ? "精彩赛事" : text);
        new ShareAction((Activity) mContext)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .withMedia(umWeb)
                .setCallback(shareListener)//回调监听器
                .open();
    }
   /* public void showShare(final String title, final String text, final String url) {
        ShareSDK.initSDK(mContext);
        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (WechatMoments.NAME.equals(platform.getName())
                        || Wechat.NAME.equals(platform.getName())) {

                    paramsToShare.setTitle(title);
                    paramsToShare.setText(text);
                    paramsToShare.setUrl(url);
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                } else if (QQ.NAME.equals(platform.getName()) || QZone.NAME.equals(platform.getName())) {
                    paramsToShare.setTitle(title);
                    paramsToShare.setText(text);
                    paramsToShare.setTitleUrl(url);
                    paramsToShare.setSite(mContext.getString(R.string.app_name));
                    paramsToShare.setSiteUrl("http://www.51caixiang.com/");
                } else {
                    paramsToShare.setTitle(title + "\n" + text);
                    paramsToShare.setText(title + "\n" + text + "\n" + url);
                }
            }
        });
        // 启动分享GUI
        oks.show(mContext);
    }*/
    public void showShare(Bitmap bitmap) {
        UMImage thumb = new UMImage(mContext, bitmap);
        thumb.compressStyle = UMImage.CompressStyle.QUALITY;
        new ShareAction((Activity) mContext)
                .withText("彩象彩票")
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .withMedia(thumb)
                .setCallback(shareListener)//回调监听器
                .open();
    }

    private Bitmap scaleSmall(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.15f, 0.15f); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
}
