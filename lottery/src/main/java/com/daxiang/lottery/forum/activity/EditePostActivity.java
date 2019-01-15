package com.daxiang.lottery.forum.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.common.SpliceCtr;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.entity.ChoosedContentFormBean;
import com.daxiang.lottery.entity.JclqData;
import com.daxiang.lottery.entity.JczqData;
import com.daxiang.lottery.forum.album.ContentData;
import com.daxiang.lottery.forum.album.RichTextEditor;
import com.daxiang.lottery.forum.base.BaseMvpActivity;
import com.daxiang.lottery.forum.contract.EditePostContract;
import com.daxiang.lottery.forum.presenter.EditePostPresenter;
import com.daxiang.lottery.utils.LogUtils;
import com.daxiang.lottery.utils.SDCardUtils;
import com.daxiang.lottery.view.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class EditePostActivity extends BaseMvpActivity<EditePostPresenter> implements EditePostContract.View, View.OnLayoutChangeListener {
    private LinearLayout mLlRoot;
    private TitleBar mTitlbar;
    private EditText mTitle;
    private TextView mAll;
    private TextView mNumber;
    private TextView mBasketball;
    private TextView mFootball;
    private ImageView mIsRecommend;
    private LinearLayout mAddImage;
    private RichTextEditor mRichText;
    private LinearLayout mLlMatch;

    private TextView mCamera;
    private TextView mPhoto;
    private Button mCancel;
    private View inflate;
    private Dialog mDialog;
    private Window window;

    @Override
    public int getInflateId() {
        return R.layout.activity_edite_post;
    }

    @Override
    public void init() {
        mPresenter = new EditePostPresenter(this);
        mLlRoot = (LinearLayout) findViewById(R.id.ll_root);
        mTitlbar = (TitleBar) findViewById(R.id.titlbar);
        mTitlbar.setImageTitleVisibility(false);
        mTitlbar.mTextRegister.setVisibility(View.VISIBLE);
        mTitlbar.mTextRegister.setText("发布");
        mTitle = (EditText) findViewById(R.id.title);
        mAll = (TextView) findViewById(R.id.all);
        mNumber = (TextView) findViewById(R.id.number);
        mBasketball = (TextView) findViewById(R.id.basketball);
        mFootball = (TextView) findViewById(R.id.football);
        mIsRecommend = (ImageView) findViewById(R.id.isRecommend);
        mAddImage = (LinearLayout) findViewById(R.id.addImage);
        mRichText = (RichTextEditor) findViewById(R.id.richText);
        mLlMatch = (LinearLayout) findViewById(R.id.ll_match);

        inflate = View.inflate(this, R.layout.item_popup, null);
        mCamera = (TextView) inflate.findViewById(R.id.buttonCamera);
        mPhoto = (TextView) inflate.findViewById(R.id.buttonPhoto_selector);
        mCancel = (Button) inflate.findViewById(R.id.buttoncancle);
        mDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        window = mDialog.getWindow();
        mDialog.setContentView(inflate);

        //获取屏幕高度
        int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    @Override
    public void addListener() {
        mTitlbar.mImageBack.setOnClickListener(BackListener);
        //发布
        mTitlbar.mTextRegister.setOnClickListener(ConfirmListener);
        mLlRoot.addOnLayoutChangeListener(this);
        //添加图片
        mAddImage.setOnClickListener(AddImageListener);
        //拍照
        mCamera.setOnClickListener(CameraListener);
        //从相册中选取
        mPhoto.setOnClickListener(PhotoListener);
        //取消
        mCancel.setOnClickListener(CancelListener);
        //帖子类型
        mAll.setOnClickListener(PostFlagListener);
        mNumber.setOnClickListener(PostFlagListener);
        mBasketball.setOnClickListener(PostFlagListener);
        mFootball.setOnClickListener(PostFlagListener);
        //选择比赛
        mLlMatch.setOnClickListener(MatchListener);

    }

    View.OnClickListener MatchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ("竞彩篮球".equals(postFlag)) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("mChoosedContentMap", mChoosedContentJlMap);
                startActivityForResult(JclqRecommendActivity.class, 22222, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("mChoosedContentMap", mChoosedContentJzMap);
                startActivityForResult(JczqRecommendActivity.class, 22222, bundle);
            }
        }
    };
    private HashMap<Integer, ContentData> dataMap;
    View.OnClickListener ConfirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title = mTitle.getText().toString();
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(mContext, "帖子标题不能为空", Toast.LENGTH_SHORT).show();
            }
            dataMap = mRichText.getRichEditData();
            uploadPhotoAction();
        }
    };
    private String postFlag = "竞彩足球";
    View.OnClickListener PostFlagListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAll.setBackgroundResource(R.mipmap.unselected);
            mNumber.setBackgroundResource(R.mipmap.unselected);
            mBasketball.setBackgroundResource(R.mipmap.unselected);
            mFootball.setBackgroundResource(R.mipmap.unselected);

            mAll.setTextColor(getResources().getColor(R.color.gray_txt));
            mNumber.setTextColor(getResources().getColor(R.color.gray_txt));
            mBasketball.setTextColor(getResources().getColor(R.color.gray_txt));
            mFootball.setTextColor(getResources().getColor(R.color.gray_txt));

            switch (v.getId()) {
                case R.id.all:
                    postFlag = mAll.getText().toString();
                    mLlMatch.setVisibility(View.GONE);
                    mAll.setBackgroundResource(R.mipmap.selected);
                    mAll.setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.number:
                    postFlag = mNumber.getText().toString();
                    mLlMatch.setVisibility(View.GONE);
                    mNumber.setBackgroundResource(R.mipmap.selected);
                    mNumber.setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.basketball:
                    postFlag = mBasketball.getText().toString();
                    mLlMatch.setVisibility(View.VISIBLE);
                    mBasketball.setBackgroundResource(R.mipmap.selected);
                    mBasketball.setTextColor(getResources().getColor(R.color.white));
                    break;
                case R.id.football:
                    postFlag = mFootball.getText().toString();
                    mLlMatch.setVisibility(View.VISIBLE);
                    mFootball.setBackgroundResource(R.mipmap.selected);
                    mFootball.setTextColor(getResources().getColor(R.color.white));
                    break;

            }
        }
    };
    View.OnClickListener BackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    View.OnClickListener AddImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.show();
            window.setGravity(Gravity.BOTTOM);
        }
    };
    private int permissionType;//1，调取相机需要的权限；2，调取相册需要的权限
    //拍照
    View.OnClickListener CameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            permissionType = 1;
            requestPermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    };
    //从相册中选取
    View.OnClickListener PhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            permissionType = 2;
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    };

    //取消
    View.OnClickListener CancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };
    String mPath;

    @Override
    public void permissonExcute() {//权限申请成功
        if (permissionType == 1) {
            String mUUID = UUID.randomUUID().toString();
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            mPath = SDCardUtils.getStorageDirectory() + mUUID + ".jpg";
            File file = new File(mPath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(intent, 1111);
        } else if (permissionType == 2) {
            Intent intent = new Intent(mContext, PickOrTakeImageActivity.class);
            ((Activity) mContext).startActivityForResult(intent, 2222);
        }
    }

    private HashMap<JczqData.DataBean, HashMap<String, String>> mChoosedContentJzMap;
    private HashMap<JclqData.DataBean, HashMap<String, String>> mChoosedContentJlMap;
    private String mSendContent;
    private String issueNo = "999999999999";//最早截期的期号

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 33333 && requestCode == 22222) {
            if ("竞彩篮球".equals(postFlag)) {
                mChoosedContentJlMap = (HashMap<JclqData.DataBean, HashMap<String, String>>) data.getSerializableExtra("selectMap");
                if (mChoosedContentJlMap == null) return;
                ArrayList<ArrayList<ChoosedContentFormBean>> choosedContentFormList = new ArrayList<>();
                for (Map.Entry<JclqData.DataBean, HashMap<String, String>> entry : mChoosedContentJlMap.entrySet()) {
                    HashMap<String, String> value = entry.getValue();
                    //遍历内层的hashmap
                    JclqData.DataBean dataBean = entry.getKey();
                    String awary = dataBean.getGuestShortCn();
                    String home = dataBean.getHomeShortCn();
                    String let1 = dataBean.getLet();
                    float let = Float.parseFloat(TextUtils.isEmpty(let1) ? "0" : let1);
                    String mid = dataBean.getSession();
                    ArrayList<ChoosedContentFormBean> list = new ArrayList<>();
                    for (Map.Entry<String, String> entry1 : value.entrySet()) {
                        ChoosedContentFormBean mContentBean = new ChoosedContentFormBean();
                        mContentBean.setAwary(awary);
                        mContentBean.setHome(home);
                        mContentBean.setContent(entry1.getKey());
                        mContentBean.setLet(let);
                        mContentBean.setMid(mid);
                        mContentBean.setOdds(entry1.getValue());
                        list.add(mContentBean);
                    }
                    choosedContentFormList.add(list);
                    mSendContent = SpliceCtr.spliteStr1(choosedContentFormList, new ArrayList<String>(), false);
                }
            } else {
                mChoosedContentJzMap = (HashMap<JczqData.DataBean, HashMap<String, String>>) data.getSerializableExtra("selectMap");
                if (mChoosedContentJzMap == null) return;
                ArrayList<ArrayList<ChoosedContentFormBean>> choosedContentFormList = new ArrayList<>();
                for (Map.Entry<JczqData.DataBean, HashMap<String, String>> entry : mChoosedContentJzMap.entrySet()) {
                    HashMap<String, String> value = entry.getValue();
                    //遍历内层的hashmap
                    JczqData.DataBean dataBean = entry.getKey();
                    String awary = dataBean.getGuestShortCn();
                    String home = dataBean.getHomeShortCn();
                    int let = dataBean.getLet();
                    String mid = dataBean.getSession();
                    if (Long.parseLong(mid) < Long.parseLong(issueNo)) {
                        issueNo = mid;
                    }
                    ArrayList<ChoosedContentFormBean> list = new ArrayList<>();
                    for (Map.Entry<String, String> entry1 : value.entrySet()) {
                        ChoosedContentFormBean mContentBean = new ChoosedContentFormBean();
                        mContentBean.setAwary(awary);
                        mContentBean.setHome(home);
                        mContentBean.setContent(entry1.getKey());
                        mContentBean.setLet(let);
                        mContentBean.setMid(mid);
                        mContentBean.setOdds(entry1.getValue());
                        list.add(mContentBean);
                        //  isBunch.put(BunchMethod.getPlayMethod(entry1.getKey(), dataBean), 0);
                    }
                    choosedContentFormList.add(list);
                    mSendContent = SpliceCtr.spliteStr1(choosedContentFormList, new ArrayList<String>(), true);
                }
            }
        } else if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1111) {
                //拍照的结果
                List<String> pathList = new ArrayList<>();
                pathList.add(mPath);
                for (int i = 0; i < pathList.size(); i++) {
                    mRichText.insertImage(pathList.get(i));
                }
            } else if (requestCode == 2222) {
                List<String> pathList = (List<String>) data.getSerializableExtra("data");
                for (int i = 0; i < pathList.size(); i++) {
                    mRichText.insertImage(pathList.get(i));
                }
            }

        }
    }


    private String pictureUrl;
    private int n;

    public void uploadPhotoAction() {
        if (dataMap == null || dataMap.size() == 0) {
            Toast.makeText(mContext, "帖子内容不能为空", Toast.LENGTH_SHORT).show();
        }
        showLoading();
        if (dataMap.size() > 1) {
            final RequestParams params = new RequestParams(Url.UPDATE_IMAGE);
            n = 0;
            for (Map.Entry<Integer, ContentData> entry : dataMap.entrySet()) {
                if (entry.getKey() < dataMap.size() - 1) {
                    ContentData value = entry.getValue();
                    File file = new File(value.imagePath);
                    Log.e("选中的图片路径：", value.imagePath);
                    compress(file, dataMap.size(), params);
                }
            }
        } else {
            imageSuccess();
        }

    }

    public void compress(File file, final int imageNum, final RequestParams params) {
        Luban.get(mContext)
                .load(file)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        params.addBodyParameter("img" + n, file);
                        n++;
                        // params.addBodyParameter("file", file);
                        //当最后一张压缩完成之后再上传。
                        if (n >= imageNum - 1) {
                            params.addBodyParameter("token", LotteryApp.token);
                            x.http().post(params, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    LogUtils.e("onSuccess", result);
                                    if (result == null || result.length() == 0) {
                                        Toast.makeText(mContext, "上传图片失败！", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        JSONObject data = jsonObject.getJSONObject("data");
                                        for (int i = 0; i < n; i++) {
                                            String string = data.getString("img" + i);
                                            dataMap.get(i).imgUrl = string;
                                        }
                                        pictureUrl = data.getString("img0");
                                        //图片上传成功
                                        imageSuccess();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        dismissLoading();
                                    }

                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    LogUtils.e("错误结果： ", ex.getMessage());
                                    dismissLoading();
                                    Toast.makeText(mContext, "上传图片失败！", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(CancelledException cex) {
                                    LogUtils.e("onCancelled", "取消");
                                }

                                @Override
                                public void onFinished() {
                                    LogUtils.e("onFinished", "完成");
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError", "压缩失败");
                        dismissLoading();
                    }
                }).launch();
    }


    public void imageSuccess() {
        StringBuilder stringBuilder = new StringBuilder();
        // stringBuilder.append("<h2  style=\"margin:10px 0px 5px\">" + skillName + "</h2><p style=\"margin:5px 0px 10px; color:#999999; font-size:12px\">发表于" + time + "</p>");
        for (int i = 0; i < dataMap.size(); i++) {
            ContentData contentData = dataMap.get(i);
            if (i == dataMap.size() - 1) {
                stringBuilder.append("<p style=\"margin:0px 0px 0px;text-align: justify;color:#333333;\">&nbsp;&nbsp;&nbsp;&nbsp;" + contentData.title + "</p>");
            } else
                stringBuilder.append("<p style=\"margin:0px 0px 0px;text-align: justify;color:#333333;\">&nbsp;&nbsp;&nbsp;&nbsp;" + contentData.title + "</p><img src=\"" + contentData.imgUrl + "\"/>");
        }
        Bundle bundle = new Bundle();
        bundle.putString("token", LotteryApp.token);
        bundle.putString("userName", LotteryApp.nikeName);
        bundle.putString("title", mTitle.getText().toString());
        bundle.putString("content", String.valueOf(stringBuilder));
        bundle.putString("infoSource", LotteryApp.nikeName);
        bundle.putString("postFlag", postFlag);
        bundle.putString("timeStamp", System.currentTimeMillis() + "");
        if (!TextUtils.isEmpty(pictureUrl))
            bundle.putString("pictureUrl", pictureUrl);
        // bundle.putString("qualityFlag","0");
        if (!TextUtils.isEmpty(mSendContent))
            bundle.putString("betContent", mSendContent + mChoosedContentJzMap.size() + "*1");
        if (!TextUtils.isEmpty(issueNo)&&!"999999999999".equals(issueNo))
            bundle.putString("issueNo",issueNo);
        bundle.putString("postType", "0"); //帖子类型 0普通 1新闻 2公告 默认0
        //bundle.putString("issueNo", );
        //bundle.putString("money", );
        // bundle.putString("postStatus", ); //帖子状态 是否收费 1收费 0不收费 默认0
        // bundle.putString("auditFlag", );// 审核标志 0未审核，1通过，2未通过 3违规删除4.用户删除 默认1
        mPresenter.getCommitData(bundle, mContext);
    }

    @Override
    public void getCommitSuccess(String result) {
        dismissLoading();
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == 0) {
                Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCommitError() {
        dismissLoading();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case View.VISIBLE:
                    //mAddImage.setVisibility(View.VISIBLE);
                    break;
                case View.GONE:
                    // mAddImage.setVisibility(View.GONE);
                    break;
            }
        }
    };
    private int keyHeight;

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            handler.sendEmptyMessage(View.VISIBLE);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            handler.sendEmptyMessage(View.GONE);
        }
    }
}
