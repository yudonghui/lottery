package com.daxiang.lottery.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.utils.BitmapUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.SDCardUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class EditeIntroduceActivity extends BaseNoTitleActivity {
    private Context mContext;
    private TitleBar mTitleBar;
    private LinearLayout mLl_avatar;
    private ImageView mAvatar;
    private EditText mEditText;
    private TextView mConfirm;
    private TextView mCamera;
    private TextView mPhoto;
    private Button mCancel;
    // private PopupWindow popupWindow;
    private View inflate;
    //private String headerUrl;
    private Dialog mDialog;
    private Window window;
    //调用相机和相册
    private static final String IMAGE_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/donghui/photo";
    private static final String IMAGE_SAVE_PATH = IMAGE_SAVE_DIR + "/demo.jpg";
    private static final int PERMISSIONS_REQUEST_PHOTO = 0x01;
    private static final int PERMISSIONS_REQUEST_FILE = 0x02;
    private static final int REQUEST_CODE_TAKING_PHOTO = 0x03;
    private static final int REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL = 0x04;
    private static final int REQUEST_CODE_CUT_PHOTO = 0x05;
    private static final int TARGET_HEAD_SIZE = 480;
    private boolean isTakePhoto = false;
    private boolean isGetPic = false;
    private String mPath;
    private Uri mUri;
    private String introduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_introduce);
        mContext = this;
        if (savedInstanceState != null) mPath = savedInstanceState.getString("Path");
        introduce = getIntent().getStringExtra("introduce");
        initView();
        HttpUtils2.display(mAvatar, Url.HEADER_ROOT_URL + LotteryApp.uid);
        addListener();
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlBar);
        mTitleBar.setTitle("个人信息");
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mLl_avatar = (LinearLayout) findViewById(R.id.ll_avatar);
        mAvatar = (ImageView) findViewById(R.id.avatar);
        mEditText = (EditText) findViewById(R.id.editText);
        mConfirm = (TextView) findViewById(R.id.confirm);
        inflate = View.inflate(mContext, R.layout.item_popup, null);
        mCamera = (TextView) inflate.findViewById(R.id.buttonCamera);
        mPhoto = (TextView) inflate.findViewById(R.id.buttonPhoto_selector);
        mCancel = (Button) inflate.findViewById(R.id.buttoncancle);
        mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        window = mDialog.getWindow();
        mDialog.setContentView(inflate);

        File file = new File(IMAGE_SAVE_DIR);
        if (!file.exists()) file.mkdirs();
        if (TextUtils.isEmpty(introduce)) {
            mEditText.setText("");
        } else {
            mEditText.setText(introduce);
            mEditText.setSelection(introduce.length());
        }

    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(BackListener);
        mLl_avatar.setOnClickListener(AvatarListener);
        //拍照
        mCamera.setOnClickListener(CameraListener);
        //从相册中选取
        mPhoto.setOnClickListener(PhotoListener);
        //取消
        mCancel.setOnClickListener(CancelListener);
        //确认修改
        mConfirm.setOnClickListener(ConfirmListener);
    }

    View.OnClickListener BackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    View.OnClickListener AvatarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!LotteryApp.phoneFlag && LotteryApp.isThird) {
                HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                    @Override
                    public void callBack(View view) {
                        Intent intent = new Intent(mContext, PhoneIsExistActivity.class);
                        startActivityForResult(intent, 100);
                    }
                });
            } else {
                mDialog.show();
                window.setGravity(Gravity.BOTTOM);
            }
        }
    };
    View.OnClickListener ConfirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String userDesc = mEditText.getText().toString();
            if (TextUtils.isEmpty(userDesc)) {
                Toast.makeText(mContext, "请输入简介内容", Toast.LENGTH_SHORT).show();
                return;
            }
            HttpInterface2 mHttp = new HttpUtils2(mContext);
            Bundle params = new Bundle();
            params.putString("token", LotteryApp.token);
            params.putString("userId", LotteryApp.uid);
            params.putString("timeStamp", System.currentTimeMillis() + "");
            params.putString("channelId", Number.CHANNELID);
            params.putString("userDesc", userDesc);
            mHttp.post(Url.NICKNAME_URL, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        if (code == 0) {
                            Intent intent = new Intent();
                            setResult(888, intent);
                            finish();
                        } else {
                            Toast.makeText(mContext, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError() {

                }
            });
        }
    };
    //取消
    View.OnClickListener CancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };
    //拍照
    View.OnClickListener CameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            operTakePhoto();
        }
    };
    //从相册中选取
    View.OnClickListener PhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            operChoosePic();
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Path", mPath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //调用相机和相册以及裁剪后的回调
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TAKING_PHOTO: // 拍照的结果
                    dealTakePhotoThenZoom();

                    break;
                case REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL://选择图片的结果
                    dealChoosePhotoThenZoom(data);
                    break;
                case REQUEST_CODE_CUT_PHOTO: // 剪裁图片的结果
                    dealZoomPhoto();
                    break;
            }
        }

    }

    /**
     * 处理拍照并剪裁
     */
    private void dealTakePhotoThenZoom() {
        startPhotoZoom(Uri.fromFile(new File(mPath + ".jpg")), TARGET_HEAD_SIZE);
    }

    /**
     * 处理选择图片并剪裁
     */
    private void dealChoosePhotoThenZoom(Intent data) {
        Uri uri = data.getData();
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
            }
            startPhotoZoom(Uri.fromFile(new File(IMAGE_SAVE_PATH)), TARGET_HEAD_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 剪裁图片
     */

    private void startPhotoZoom(Uri uri, int size) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");

            intent.setDataAndType(uri, "image/*");
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);

            // outputX,outputY 是剪裁图片的宽高
            intent.putExtra("outputX", size);
            intent.putExtra("outputY", size);
            //   intent.putExtra("return-data", true);
            mUri = Uri.parse("file:///" + IMAGE_SAVE_PATH);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent, REQUEST_CODE_CUT_PHOTO);
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Your device doesn't support the crop action!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将头像数据上传到服务器
     */
    private void dealZoomPhoto() {
        try {
            final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
            if (mUri != null) {
                final Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mUri));
                if (bitmap != null) {
                    boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
                    if (b) {
                        File file = new File(IMAGE_SAVE_PATH);
                        /*FormFile formFile=new FormFile(file.getName(),file,"document","image*//*");
                        boolean isSuccess= HttpRequestUtil.uploadFile(Url.CHANGE_HEADER_URL,null,formFile);
                        if(isSuccess){
                            Toast.makeText(mContext, "文件上传成功", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(mContext, "文件上传失败", Toast.LENGTH_SHORT).show();
                        }*/
                        RequestParams entity = new RequestParams(Url.UPDATE_IMAGE);
                        entity.addBodyParameter("file", file);
                        entity.addBodyParameter("userId", LotteryApp.uid);
                        entity.addBodyParameter("path", "avatar");
                        entity.addBodyParameter("token", LotteryApp.token);
                        x.http().post(entity, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                loadingDialogUtils.setDimiss();
                                if (result == null || result.length() == 0) {
                                    Toast.makeText(mContext, "修改头像失败！", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    String code = jsonObject.getString("code");
                                    String msg = jsonObject.getString("msg");
                                    if ("0".equals(code)) {
                                        mAvatar.setImageBitmap(bitmap);
                                    }
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                loadingDialogUtils.setDimiss();
                                //LogUtils.e("错误结果： ", ex.getMessage());
                                Toast.makeText(mContext, "修改头像失败！", Toast.LENGTH_SHORT).show();
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照操作
     */
    private void operTakePhoto() {
        isTakePhoto = true;
        isGetPic = false;
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //如果sdk大于等于23那么提示是否获取调取相机的授权。否则直接请求授权
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.CAMERA))
                showPhotoPerDialog();
            else
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_PHOTO);
        } else takePhoto();
    }

    /**
     * 选择图片操作
     */
    private void operChoosePic() {
        isTakePhoto = false;
        isGetPic = true;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                showFilePerDialog();
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_FILE);
        } else getPictureFromLocal();
    }

    /**
     * 拍照权限提示
     */
    private void showPhotoPerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("需要获取访问您的相机权限，以确保您可以正常拍照。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_PHOTO);
                    }
                }).create().show();
    }

    /**
     * 文件权限提示
     */
    private void showFilePerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("PhotoDemo需要获取存储文件权限，以确保可以正常保存拍摄或选取的图片。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, PERMISSIONS_REQUEST_FILE);
                    }
                }).create().show();
    }

    /**
     * 权限申请回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_PHOTO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isTakePhoto) takePhoto();
                    if (isGetPic) getPictureFromLocal();
                }
            }
            case PERMISSIONS_REQUEST_FILE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    dealZoomPhoto();
                break;
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        String mUUID = UUID.randomUUID().toString();
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        mPath = SDCardUtils.getStorageDirectory() + mUUID;
        File file = new File(mPath + ".jpg");

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQUEST_CODE_TAKING_PHOTO);
    }

    /**
     * 从本地选择图片
     */
    private void getPictureFromLocal() {
        Intent innerIntent =
                new Intent(Intent.ACTION_GET_CONTENT);
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL);
    }

}
