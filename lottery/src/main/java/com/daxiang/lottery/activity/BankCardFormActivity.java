package com.daxiang.lottery.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.BankCardAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.StringInterface;
import com.daxiang.lottery.entity.BankCardBean;
import com.daxiang.lottery.entity.PayBean;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.PaymentUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.ListItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BankCardFormActivity extends BaseNoTitleActivity {
    private TitleBar mTitlBar;
    private ImageView mAdd_bankcard;
    private LinearLayout mLl_list;
    private SwipeMenuRecyclerView mRecyclerView;
    private BankCardAdapter mAdapter;
    private String money;
    private PaymentUtils paymentUtils;
    private HttpInterface2 mHttp;
    private PayBean payBean;
    private String from;//一种是充值，一种是支付

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_form);
        money = getIntent().getStringExtra("money");
        payBean = (PayBean) getIntent().getSerializableExtra("payBean");
        from = getIntent().getStringExtra("from");
        initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new ListItemDecoration(ContextCompat.getColor(mContext, R.color.gray_box)));
        mRecyclerView.setSwipeItemClickListener(mItemClickListener); // Item点击。
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener); // Item的Menu点击。
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        mAdapter = new BankCardAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        addData();
        addListener();
    }

    private void initView() {
        mTitlBar = (TitleBar) findViewById(R.id.titlBar);
        mTitlBar.setImageTitleVisibility(false);
        mTitlBar.setTitle("我的银行卡");
        mTitlBar.setTitleVisibility(true);
        mTitlBar.mShare.setImageResource(R.drawable.fatie);
        mAdd_bankcard = (ImageView) findViewById(R.id.add_bankcard);
        mLl_list = (LinearLayout) findViewById(R.id.ll_list);
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recyclerView);
        paymentUtils = new PaymentUtils(mContext, PayResult);
        mHttp = new HttpUtils2(mContext);
    }

    private boolean flag;//true 显示check，false正常状态

    private void addListener() {
        mTitlBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlBar.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if ("BuyActivity".equals(from)) {
                    bundle.putSerializable("payBean", payBean);
                    bundle.putString("from", from);
                } else
                    bundle.putString("money", money);
                startActivityForResult(BankCardFormActivity.class, bundle, 2222);
            }
        });
        mAdd_bankcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if ("BuyActivity".equals(from)) {
                    bundle.putSerializable("payBean", payBean);
                    bundle.putString("from", from);
                } else
                    bundle.putString("money", money);
                startActivityForResult(BankCardActivity.class, bundle, 2222);
            }
        });
    }

    /**
     * Item点击监听。
     */
    private SwipeItemClickListener mItemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            BankCardBean.DataBean.CardInfoBean cardInfoBean = mList.get(position);
            String card_type = cardInfoBean.getCard_type();
            String no_agree = cardInfoBean.getNo_agree();
            if ("BuyActivity".equals(from))
                paymentUtils.buy(payBean, null, card_type, no_agree);
            else
                paymentUtils.lianlianWeb(money, null, card_type, no_agree);
        }
    };
    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                String no_agree = mList.get(adapterPosition).getNo_agree();
                if (TextUtils.isEmpty(no_agree)) {
                    Toast.makeText(mContext, "银行卡签约号为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
                Bundle params = new Bundle();
                params.putString("userId", LotteryApp.uid);
                params.putString("noAgree", no_agree);
                params.putString("timeStamp", System.currentTimeMillis() + "");
                mHttp.post(Url.UNBIND_CARD_INFO, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        loadingDialogUtils.setDimiss();
                        mList.clear();
                        addData();
                    }

                    @Override
                    public void onError() {
                        loadingDialogUtils.setDimiss();
                    }
                });
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                //Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int i) {
            int height = DisplayUtil.dip2px(120);
            int width = DisplayUtil.dip2px(70);
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                    .setBackgroundColor(Color.rgb(0xff, 0x2b, 0x30))
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };
    StringInterface PayResult = new StringInterface() {
        @Override
        public void callBack(String type, String msg) {
            if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(msg)) {
                switch (type) {
                    case "0000":// 0000 交易成功
                        HintDialogUtils.setHintDialog(mContext);
                        HintDialogUtils.setMessage("支付成功！");
                        HintDialogUtils.setVisibilityCancel();
                        HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                            @Override
                            public void callBack(View view) {
                                finish();
                            }
                        });
                        break;
                    case "2008":// 2008 支付处理中
                        HintDialogUtils.setHintDialog(mContext);
                        HintDialogUtils.setMessage("支付处理中！");
                        HintDialogUtils.setVisibilityCancel();
                        HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                            @Override
                            public void callBack(View view) {

                            }
                        });
                        break;
                    default:
                        HintDialogUtils.setHintDialog(mContext);
                        HintDialogUtils.setMessage(msg);
                        HintDialogUtils.setVisibilityCancel();
                        HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                            @Override
                            public void callBack(View view) {

                            }
                        });
                        break;
                }
            }
        }
    };
    int i = 0;
    List<BankCardBean.DataBean.CardInfoBean> mList = new ArrayList<>();

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        Bundle params = new Bundle();
        params.putString("userId", LotteryApp.uid);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttp.post(Url.BANK_CARD_FORM, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                BankCardBean bankCardBean = gson.fromJson(result, BankCardBean.class);
                int code = bankCardBean.getCode();
                String msg = bankCardBean.getMsg();
                if (code == 0) {
                    mList.clear();
                    BankCardBean.DataBean data = bankCardBean.getData();
                    mList.addAll(data.getCardInfo());

                    mAdapter.notifyDataSetChanged();
                    if (mList.size() > 0) {
                        mLl_list.setVisibility(View.VISIBLE);
                        mAdd_bankcard.setVisibility(View.GONE);
                        mTitlBar.mShare.setVisibility(View.VISIBLE);
                    } else {
                        mLl_list.setVisibility(View.GONE);
                        mAdd_bankcard.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {
        private int dividerHeight;
        private Paint dividerPaint;

        public MyItemDecoration() {
            dividerPaint = new Paint();
            dividerPaint.setColor(getResources().getColor(R.color.white));
            dividerHeight = DisplayUtil.dip2px(10);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = dividerHeight;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int childCount = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 0; i < childCount - 1; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + dividerHeight;
                c.drawRect(left, top, right, bottom, dividerPaint);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2222 && resultCode == 2223) {
            setResult(2222, data);
            finish();
        }
    }
}
