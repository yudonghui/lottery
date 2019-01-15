package com.daxiang.lottery.activity.wonderfulactivity.LotteryForum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.wonderfuladapter.ForumContentAdapter;
import com.daxiang.lottery.adapter.wonderfuladapter.ForumGvAdapter;
import com.daxiang.lottery.entity.CommentContentData;
import com.daxiang.lottery.view.BallGridView;
import com.daxiang.lottery.view.TitleBar;

import java.util.ArrayList;

public class CommentContentActivity extends AppCompatActivity {
    private TitleBar mTitleBar;
    private ListView mListView;
    private ImageView mImage_deader;
    private LinearLayout mLl_author_date;
    private TextView mText_author;
    private TextView mText_date;
    private ImageView mXiangxia;
    private TextView mText_content;
    private BallGridView mGv_forum;
    private LinearLayout mLl_collection;
    private ImageView mImage_collection;
    private TextView mText_collection;
    private LinearLayout mLl_comment;
    private ImageView mImage_comment;
    private TextView mText_comment;
    private LinearLayout mLl_good;
    private ImageView mImage_good;
    private TextView mText_good;
    private EditText mEt_content;
    private TextView mSend_comment;
    private int numGv;
    private ForumContentAdapter commentContentAdapter;
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
    private String[] nickName = {"张无忌", "孙悟空", "猪八戒", "逍遥子", "沙悟净", "刘德华", "周润发", "成龙", "岳云鹏", "苍井空"};
    ArrayList<CommentContentData> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_content);
        Intent intent = getIntent();
        numGv = intent.getIntExtra("num", 0);
        initView();
        addData();
        commentContentAdapter = new ForumContentAdapter(this, mList);
        mListView.setAdapter(commentContentAdapter);
        addListener();
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlebar_content);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle("帖子正文");
        mEt_content = (EditText) findViewById(R.id.et_content);
        mSend_comment = (TextView) findViewById(R.id.send_comment);

        mListView = (ListView) findViewById(R.id.lv_comment);
        View view = View.inflate(this, R.layout.item_lv_forum, null);
        mImage_deader = (ImageView) view.findViewById(R.id.image_deader);
        mLl_author_date = (LinearLayout) view.findViewById(R.id.ll_author_date);
        mText_author = (TextView) view.findViewById(R.id.text_author);
        mText_date = (TextView) view.findViewById(R.id.text_date);
        mXiangxia = (ImageView) view.findViewById(R.id.xiangxia);
        mText_content = (TextView) view.findViewById(R.id.text_content);
        mLl_collection = (LinearLayout) view.findViewById(R.id.ll_collection);
        mImage_collection = (ImageView) view.findViewById(R.id.image_collection);
        mText_collection = (TextView) view.findViewById(R.id.text_collection);
        mLl_comment = (LinearLayout) view.findViewById(R.id.ll_comment);
        mImage_comment = (ImageView) view.findViewById(R.id.image_comment);
        mText_comment = (TextView) view.findViewById(R.id.text_comment);
        mLl_good = (LinearLayout) view.findViewById(R.id.ll_good);
        mImage_good = (ImageView) view.findViewById(R.id.image_good);
        mText_good = (TextView) view.findViewById(R.id.text_good);
        mGv_forum = (BallGridView) view.findViewById(R.id.gv_forum);
        ForumGvAdapter mGvAdapter = new ForumGvAdapter(this, numGv);
        mGv_forum.setAdapter(mGvAdapter);
        mListView.addHeaderView(view);
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //发送评论内容
        mSend_comment.setOnClickListener(SendCommentListener);
    }

    private void addData() {
        for (int i = 0; i < 10; i++) {
            CommentContentData commentContentData = new CommentContentData();
            commentContentData.setId(headerId[i]);
            commentContentData.setCommentContent(content[i]);
            commentContentData.setCommentNickname(nickName[i]);
            ArrayList<CommentContentData.ReplyBean> replyList = new ArrayList<>();
            //回复内容的集合
            for (int j = 0; j < 3; j++) {
                CommentContentData.ReplyBean replyBean = new CommentContentData.ReplyBean();
                replyBean.setCommentNickname(nickName[j + 3]);
                replyBean.setReplyNickname(nickName[j + 5]);
                replyBean.setReplyContent(content[j + 3]);
                replyList.add(replyBean);
            }
            commentContentData.setReplyList(replyList);
            mList.add(commentContentData);
        }
    }

    View.OnClickListener SendCommentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
