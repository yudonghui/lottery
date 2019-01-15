package com.daxiang.lottery.forum.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.daxiang.lottery.R;
import com.daxiang.lottery.dialog.LoadingDialog;
import com.daxiang.lottery.dialog.YuAlertDialog;
import com.daxiang.lottery.forum.cxinterface.PermissionListener;
import com.daxiang.lottery.forum.utils.ActivityCollector;
import com.daxiang.lottery.forum.utils.PermissionSetting;
import com.daxiang.lottery.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseMvpActivity<T extends BasePresenter> extends AppCompatActivity {
    public Context mContext;
    private static PermissionListener mlistener;
    public T mPresenter;
    public LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getInflateId());
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCollector.addActivity(this);
        mContext = this;
        init();
        addListener();
        addData();
    }

    public void addData() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //设置布局id
    public abstract int getInflateId();

    //初始化
    public abstract void init();

    //点击事件
    public abstract void addListener();

    //申请权限之后需要执行的方法。需要的时候再重写
    public void permissonExcute() {

    }

    protected void startActivity(Class<? extends AppCompatActivity> targetClass) {
        startActivity(new Intent(mContext, targetClass));
    }

    protected void startActivity(Class<? extends AppCompatActivity> targetClass, Bundle params) {
        Intent intent = new Intent(mContext, targetClass);
        intent.putExtras(params);
        startActivity(intent);
    }

    protected void startActivityForResult(Class<? extends AppCompatActivity> targetClass, int resultCode) {
        Intent intent = new Intent(mContext, targetClass);
        startActivityForResult(intent, resultCode);
    }

    protected void startActivityForResult(Class<? extends AppCompatActivity> targetClass, int resultCode, Bundle params) {
        Intent intent = new Intent(mContext, targetClass);
        intent.putExtras(params);
        startActivityForResult(intent, resultCode);
    }

    /**
     * 切换软键盘的状态
     * 如当前为收起变为弹出,若当前为弹出变为收起
     */
    public void toggleInput() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制隐藏输入法键盘
     */
    public void hideInput(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 权限申请
     *
     * @param permissions 待申请的权限集合
     * @param listener    申请结果监听事件
     */
    private void requestRunTimePermission(String[] permissions, PermissionListener listener) {
        mlistener = listener;

        Activity topActivity = ActivityCollector.getTopActivity();
        if (topActivity == null) {
            return;
        }
        //用于存放为授权的权限
        List<String> permissionList = new ArrayList<>();
        //遍历传递过来的权限集合
        for (String permission : permissions) {
            //判断是否已经授权
            if (ContextCompat.checkSelfPermission(topActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                //未授权，则加入待授权的权限集合中
                permissionList.add(permission);
            }
        }

        //判断集合
        if (!permissionList.isEmpty()) {  //如果集合不为空，则需要去授权
            ActivityCompat.requestPermissions(topActivity, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {  //为空，则已经全部授权
            listener.onGranted();
        }
    }

    /**
     * 权限申请结果
     *
     * @param requestCode  请求码
     * @param permissions  所有的权限集合
     * @param grantResults 授权结果集合
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    //被用户拒绝的权限集合
                    List<String> deniedPermissions = new ArrayList<>();
                    //用户通过的权限集合
                    List<String> grantedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        //获取授权结果，这是一个int类型的值
                        int grantResult = grantResults[i];

                        if (grantResult != PackageManager.PERMISSION_GRANTED) { //用户拒绝授权的权限
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                        } else {  //用户同意的权限
                            String permission = permissions[i];
                            grantedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()) {  //全部申请成功
                        mlistener.onGranted();
                    } else {  //部分权限申请成功
                        //回调授权成功的接口
                        mlistener.onDenied(deniedPermissions);
                        //回调授权失败的接口
                        mlistener.onGranted(grantedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void requestPermission(final String[] persssions) {
        final PermissionSetting ps = new PermissionSetting(this);
        requestRunTimePermission(persssions
                , new PermissionListener() {
                    @Override
                    public void onGranted() {//全部申请成功的回调
                        permissonExcute();
                    }

                    @Override
                    public void onGranted(List<String> grantedPermission) {//部分成功权限

                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {//部分未成功权限
                        String message = "我们需要以下权限，请在设置中为我们开启：" + "\n" + PermissionUtils.getName(Arrays.asList(persssions));
                        YuAlertDialog.newBuilder(BaseMvpActivity.this)
                                .setCancelable(false)
                                .setTitle("提示")
                                .setMessage(message)
                                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ps.execute();
                                    }
                                })
                                .setNegativeButton("不", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ps.cancel();
                                    }
                                })
                                .show();
                    }
                });
    }


    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(mContext);
        }
        mLoadingDialog.show();
        WindowManager.LayoutParams lp = mLoadingDialog.getWindow().getAttributes();
        lp.alpha = 0.8f;
        mLoadingDialog.getWindow().setAttributes(lp);
    }

    public void dismissLoading() {
        if (mLoadingDialog == null) return;
        if (mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }
}
