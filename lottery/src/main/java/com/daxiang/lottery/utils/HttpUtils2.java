package com.daxiang.lottery.utils;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.TreeMap;

public class HttpUtils2 implements HttpInterface2 {
    private Context mContext;

    public HttpUtils2(Context mContext) {
        this.mContext = mContext;
    }

    public static void display(ImageView imageView, String iconUrl) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(true)
                .setCrop(true)
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setFailureDrawableId(R.mipmap.default_header)
                .setLoadingDrawableId(R.mipmap.default_header)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

    public static void display(ImageView imageView, String iconUrl, int resId) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(true)
                .setCrop(true)
                .setIgnoreGif(false)
                .setFailureDrawableId(resId)
                .setLoadingDrawableId(resId)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

    @Override
    public void jsonByUrl(final String url, final JsonInterface mJsonInterface) {
        RequestParams entity = new RequestParams(url);
        entity.setConnectTimeout(10000);
        Logger.e("访问的接口 ", url);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logger.e("返回结果 ", result);
                mJsonInterface.callback(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                // Logger.e("错误网址的url",url);
                //Logger.e("onError", ex.getMessage());
                // HintDialogUtils.setHintDialog(mContext);
                // HintDialogUtils.setMessage("网络请求错误");
                mJsonInterface.onError();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    //林
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void post(String url, Bundle params, final JsonInterface mJsonInterface) {
        RequestParams entity = new RequestParams(url);
        //entity.setBodyContent();
        entity.setAsJsonContent(true);
        JSONObject json = new JSONObject();
        JSONObject data = new JSONObject();
        Set<String> strings = params.keySet();
        TreeMap<String, Object> map = new TreeMap<>();
        try {
            for (String key : strings) {
                data.put(key, params.get(key));
                map.put(key, params.get(key));
                //Logger.e("key&&&", key + "==" + params.get(key));
            }
            json.put("sign", HttpMd5.buildSign(map));
            json.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        entity.setBodyContent(json.toString());
        entity.setConnectTimeout(20000);
        Logger.e("网络请求参数：", json.toString());
        Logger.e("访问的接口 ", url);
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logger.e("返回结果 ", result);
                mJsonInterface.callback(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Logger.e("错误错误 ", TextUtils.isEmpty(ex.getMessage())?"":ex.getMessage());
               /* HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("网络请求错误");*/
                mJsonInterface.onError();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    //启动页两秒钟请求不下来数据就认为网络超时。
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void postLaunch(String url, Bundle params, final JsonInterface mJsonInterface) {
        RequestParams entity = new RequestParams(url);
        //entity.setBodyContent();
        entity.setAsJsonContent(true);
        JSONObject json = new JSONObject();
        JSONObject data = new JSONObject();
        Set<String> strings = params.keySet();
        TreeMap<String, Object> map = new TreeMap<>();
        try {
            for (String key : strings) {
                data.put(key, params.get(key));
                map.put(key, params.get(key));
                //Logger.e("key&&&", key + "==" + params.get(key));
            }
            json.put("sign", HttpMd5.buildSign(map));
            json.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        entity.setBodyContent(json.toString());
        entity.setConnectTimeout(2000);
        Logger.e("网络请求参数：", json.toString());
        Logger.e("访问的接口 ", url);
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logger.e("返回结果 ", result);
                mJsonInterface.callback(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Logger.e("错误错误 ", ex.getMessage());
               /* HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("网络请求错误");*/
                mJsonInterface.onError();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    //童
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void postH(String url, Bundle params, final JsonInterface mJsonInterface) {
        params.putString("timeStamp", System.currentTimeMillis() + "");
        RequestParams entity = new RequestParams(Url.ALLOCATION);
        //entity.setBodyContent();
        entity.setAsJsonContent(true);
        JSONObject json = new JSONObject();
        JSONObject data = new JSONObject();
        Set<String> strings = params.keySet();
        TreeMap<String, Object> map = new TreeMap<>();
        try {
            for (String key : strings) {
                data.put(key, params.get(key));
                map.put(key, params.get(key));
                // Logger.e("key&&&", key + "==" + params.get(key));
            }
            json.put("sign", HttpMd5.buildSign(map));
            json.put("method", url);
            json.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        entity.setBodyContent(json.toString());
        entity.setConnectTimeout(10000);
        Logger.e("网络请求参数：", json.toString());
        Logger.e("访问的接口 ", url);
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logger.e("返回结果 ", result);
                mJsonInterface.callback(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Logger.e("错误结果", ex.getMessage());
                /*;
                HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("网络请求错误");*/
                mJsonInterface.onError();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void get(String url, Bundle params, final JsonInterface mJsonInterface) {
        RequestParams entity = new RequestParams(url);
        Set<String> strings = params.keySet();
        for (String key : strings) {
            entity.addQueryStringParameter(key, String.valueOf(params.get(key)));
        }
        entity.setConnectTimeout(10000);
        Logger.e("访问的接口", entity.toString());
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logger.e("onSuccess", result);
                if (!TextUtils.isEmpty(result)) {
                    mJsonInterface.callback(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
               /* Logger.e("onError", ex.getMessage());
                HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("网络请求错误");*/
                mJsonInterface.onError();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public static byte[] byteByurl(String urlString) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                baos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    private ProgressDialog progressDialog;

    @Override
    public void updateFile(String url, final Context mContext, final JsonInterface mJsonInterface) {
        String[] split = url.split("\\/");
        //  final String path = Environment.getExternalStorageDirectory().getPath() + File.separator + split[split.length - 1];
        final String path = mContext.getExternalCacheDir() + File.separator + split[split.length - 1];
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.setIcon(R.drawable.icon_1);
        RequestParams entity = new RequestParams(url);
        entity.setSaveFilePath(path);
        entity.setConnectTimeout(10000);
        Logger.e("请求接口", url);
        x.http().get(entity, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("努力下载中......");
                progressDialog.show();
                progressDialog.setMax(100);
                int cur = (int) (current * 100 / total);
                progressDialog.setProgress(cur);
            }

            @Override
            public void onSuccess(File result) {
                Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //安装apk
                Intent intent = new Intent();
                //执行动作
                intent.setAction(Intent.ACTION_VIEW);
                //执行的数据类型
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                mContext.startActivity(intent);
                System.exit(0);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(mContext, "下载失败，请检查网络和SD卡", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
