package com.daxiang.lottery.activity.wonderfulactivity.LotteryForum;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.wonderfuladapter.ForumLvAdapter;
import com.daxiang.lottery.entity.ForumData;
import com.daxiang.lottery.utils.dialogutils.IosDialogUtils;
import com.daxiang.lottery.view.BillListView;
import com.daxiang.lottery.view.ForumScrollView;

import java.lang.reflect.Field;
import java.util.ArrayList;
public class ForumUserActivity extends AppCompatActivity {
    private ForumScrollView mScrollView;
    private ImageView mImage_header;
    private TextView mText_nickname;
    private TextView mText_introduce;
    private LinearLayout mLl_focus;
    private LinearLayout mLl_fans;
    private LinearLayout mLl_message;
    private LinearLayout mLl_collection;
    private BillListView mLv_user;
    private ImageView mBack;
    private TextView mForum_user_title;
    private LinearLayout mFatie;
    private ImageView mSetting;
    private RelativeLayout linearLayout;
    private LinearLayout mBackGround;
    private ForumLvAdapter mLvAdapter;
    private ArrayList<ForumData> mList;
    private String[] content = {"还在俗尘看世人来来往往匆忙流转，眼中不见了最净的风骨，我混迹于人群中，寻寻觅觅等下一刻的彩霞。黄昏来的太过突兀，许久不曾静待世事的妖媚与纯粹了，眼前的风物凄凄宿雨收是回忆里的絮语。逝过的花入睡了，不忍欺它的一片寂静，可予它的也只是悄悄掠过生命的轴承。",
            "还在俗尘看世人来来往往匆忙流转，眼中不见了最净的风骨，我混迹于人群中，寻寻觅觅等下一刻的彩霞。黄昏来的太过突兀，许久不曾静待世事的妖媚与纯粹了，眼前的风物凄凄宿雨收是回忆里的絮语。逝过的花入睡了，不忍欺它的一片寂静，可予它的也只是悄悄掠过生命的轴承。",
            "林木还是那样的深沉，风雨疏骤扰过的也只是片片枫叶的余情，拟订谁若不语的沉默。",
            "如歌亦如墨，清扬浓厚是凤楼传来的千古流芳，我怎悟得透汗青的文戏。簌簌是独立的挽留，是声的呼唤，亦是情的感怀。不遇是归期无期消歇后的数落，希冀总是难料事实的自由流浪。",
            "踏进你的领地，偶闻脉搏的跳动，呼吸变得薄弱清凉，素浅的纱裙封禁了不该的风尘。云霞纵横四海之内，却是少了你的这一处，或是绿意早已足够，奢求终不是你的做派。萤火虫之舞恍恍惚惚成了惊艳的流星，听叶与叶的呢喃密语，轻许一个誓言。",
            "树苍茫了面颜，有着一些悠老的韵味，真情还在憧憬，岁月终会馈赠痕迹斑斑的伤悲。",
            "离枝叶子洒落了最后一次的珠泪，旋律奏起生命的哀歌，顷刻间缠绵悱恻的星光斑斓。",
            "我路过你的领地，惊扰了尘世之外的宁静，贪恋片刻的温暖与少顷的深情。",
            "林木深深，缠绵入骨的顷刻深情，",
            "终会长久不衰，你的世界从此盛世长宁。还是那么喧闹的繁杂，那么刺眼的云霞，我站立苍苍的长空下，我是属于这世间的。等秋风远去，我还能在哪一个梦里寻到你的飞花一叶，不期而遇总会碰触一缕纯粹的曙光。"};
    private int[] headerId = {R.drawable.jclq, R.drawable.jczq, R.drawable.jczqd, R.drawable.jclqd,
            R.drawable.dlt, R.drawable.sfc, R.drawable.rj, R.drawable.pls, R.drawable.plw, R.drawable.qxc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_user);
        initView();
        addData();
        mLvAdapter = new ForumLvAdapter(mList, this);
        mLv_user.setAdapter(mLvAdapter);
        addListener();
    }

    private void initView() {
        mScrollView = (ForumScrollView) findViewById(R.id.scrollView);
        mImage_header = (ImageView) findViewById(R.id.image_header);
        mText_nickname = (TextView) findViewById(R.id.text_nickname);
        mText_introduce = (TextView) findViewById(R.id.text_introduce);
        mLl_focus = (LinearLayout) findViewById(R.id.ll_focus);
        mLl_fans = (LinearLayout) findViewById(R.id.ll_fans);
        mLl_message = (LinearLayout) findViewById(R.id.ll_message);
        mLl_collection = (LinearLayout) findViewById(R.id.ll_collection);
        mLv_user = (BillListView) findViewById(R.id.lv_user);
        mBack = (ImageView) findViewById(R.id.back);
        mForum_user_title = (TextView) findViewById(R.id.forum_user_title);
        mFatie = (LinearLayout) findViewById(R.id.fatie);
        linearLayout = (RelativeLayout) findViewById(R.id.layout);
        mBackGround = (LinearLayout) findViewById(R.id.ll_background);
        mList = new ArrayList<>();
    }

    private void addListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mScrollView.setOnScrollistener(new ForumScrollView.OnScrollistener() {
            @Override
            public void onScroll(int startY, int endY) {
                //根据scrollview滑动更改标题栏透明度
                dynamicChangeAphla(startY, endY);
            }
        });
        //关注
        mLl_focus.setOnClickListener(FocusListener);
        //收藏
        mLl_collection.setOnClickListener(CollectionListener);
        //发帖
        mFatie.setOnClickListener(FatieListener);
    }

    View.OnClickListener FatieListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IosDialogUtils.IosStyleDialog(ForumUserActivity.this);
            IosDialogUtils.setReplayVisibility(true);
            IosDialogUtils.setFocusListener("文字", FatieDialogListener);
            IosDialogUtils.setReplayListener("图片",FatieDialogListener);
            IosDialogUtils.setSendtoListener("视频",FatieDialogListener);
        }
    };
    IosDialogUtils.DialogListener FatieDialogListener = new IosDialogUtils.DialogListener() {
        @Override
        public void callBack(View view) {
            switch (view.getId()) {
                case R.id.buttonCamera:
                    //发帖（文字）
                    Intent intent=new Intent(ForumUserActivity.this,WenziTieActivity.class);
                    startActivity(intent);
                    break;
                case R.id.dialog_reply:
                    //发帖（图片）
                    operChoosePic();
                    break;
                case R.id.buttonPhoto_selector:
                    //发帖（视频）

                    break;
            }
        }
    };

    View.OnClickListener FocusListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ForumUserActivity.this, FocusActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener CollectionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ForumUserActivity.this, CollectionActivity.class);
            startActivity(intent);
        }
    };
    /**
     * 选择图片操作
     */
    private static final int PERMISSIONS_REQUEST_FILE = 0x02;
    private static final int REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL = 0x04;
    private void operChoosePic() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                showFilePerDialog();
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_FILE);
        } else getPictureFromLocal();
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
                        ActivityCompat.requestPermissions(ForumUserActivity.this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, PERMISSIONS_REQUEST_FILE);
                    }
                }).create().show();
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

    private void addData() {
        for (int i = 0; i < 10; i++) {
            ForumData forumData = new ForumData();
            forumData.setContent(content[i]);
            forumData.setReaderId(headerId[i]);
            forumData.setImageNum(i);
            mList.add(forumData);
        }
    }

    /**
     * 根据内容窗体的移动改变标题栏背景透明度
     *
     * @param startY scrollview开始滑动的y坐标（相对值）
     * @param endY   scrollview结束滑动的y坐标（相对值）
     */
    private void dynamicChangeAphla(int startY, int endY) {
        //获取到状态栏的高度
        int statusBarHeight = getStatusBarHeight(this);
        //获取标题高度
        int titleHeight = linearLayout.getLayoutParams().height;
        //获取背景高度
        int backHeight = mBackGround.getLayoutParams().height;

        //获取控件的绝对位置坐标
        int[] location = new int[2];
        mBackGround.getLocationInWindow(location);
        //从屏幕顶部到控件顶部的坐标位置Y
        int currentY = location[1];
        //表示回到原位（滑动到顶部）
        if (currentY >= statusBarHeight) {
            linearLayout.setBackgroundColor(Color.argb(0, 254, 37, 37));
        }
        //红色背景移除或移入屏幕的过程中；颜色透明度改变
        if (currentY < statusBarHeight && currentY >= -backHeight) {
            //计算出滚动过程中改变的透明值
            double changeValue = ((double) Math.abs(currentY) / (statusBarHeight + backHeight)) * 255;
            //判断是向上还是向下
            if (endY > startY) {//向上;透明度值增加，实际透明度减小
                linearLayout.setBackgroundColor(Color.argb((int) changeValue, 254, 37, 37));
            } else if (endY < startY) {//向下；透明度值减小，实际透明度增加
                linearLayout.setBackgroundColor(Color.argb((int) changeValue, 254, 37, 37));
            }
        }
        //红色背景移除屏幕
        if (currentY < -backHeight / 2.5) {
            linearLayout.setBackgroundColor(Color.argb(255, 254, 37, 37));
            mForum_user_title.setVisibility(View.VISIBLE);
            mForum_user_title.setText("彩象彩票");
        } else {
            mForum_user_title.setVisibility(View.GONE);
        }
    }

    /**
     * 获取通知栏高度
     *
     * @param context 上下文
     * @return 通知栏高度
     */
    private int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //调用相机和相册以及裁剪后的回调
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL://选择图片的结果
                    //dealChoosePhotoThenZoom(data);
                    break;
            }
        }
    }
}
