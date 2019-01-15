package com.daxiang.lottery.forum.selfview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.BaseMovementMethod;
import android.text.method.Touch;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.forum.bean.CommentsBean;
import com.daxiang.lottery.forum.bean.UserBean;

import java.util.List;

/**
 * CommentsView
 *
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/9/3 下午9:58
 * @desc :
 */

public class CommentsView extends LinearLayout {

    private Context mContext;
    private List<CommentsBean> mDatas;
    private onItemClickListener listener;
    private int textSize = 15;

    public CommentsView(Context context) {
        this(context, null);
    }

    public CommentsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        this.mContext = context;
    }

    /**
     * 设置评论列表信息
     *
     * @param list
     */
    public void setList(List<CommentsBean> list) {
        mDatas = list;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void notifyDataSetChanged() {
        removeAllViews();
        if (mDatas == null || mDatas.size() <= 0) {
            return;
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 10);
        for (int i = 0; i < mDatas.size(); i++) {
            View view = getView(i);
            if (view == null) {
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }
            addView(view, i, layoutParams);
        }
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    private View getView(final int position) {
        final CommentsBean item = mDatas.get(position);
        UserBean replyUser = item.getReplyUser();
        String commentsId = item.getCommentsId();
        boolean hasReply = false;   // 是否有回复
        if (replyUser != null) {
            hasReply = true;
        }
        TextView textView = new TextView(mContext);
        textView.setTextSize(textSize);
        textView.setTextColor(0xff686868);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        UserBean comUser = item.getCommentsUser();
        String name = comUser.getUserName();
        if (hasReply) {
            builder.append(setClickableSpan(commentsId, name, item.getCommentsUser()));
            builder.append(" 回复 ");
            builder.append(setClickableSpan(commentsId, replyUser.getUserName(), item.getReplyUser()));

        } else {
            builder.append(setClickableSpan(commentsId, name, item.getCommentsUser()));
        }
        builder.append(" : ");
        builder.append(setClickableSpanContent(item, position));
        builder.append(" ");
        textView.setText(builder);
        // 设置点击背景色
        textView.setHighlightColor(0x00000000);
        final CircleMovementMethod method = new CircleMovementMethod(0xffcccccc, 0xffcccccc);
        textView.setMovementMethod(method);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (method.isParseTv()) {
                    if (listener != null) {
                        listener.onItemClick(position, item);
                    }
                }
            }
        });

        return textView;
    }

    /**
     * 设置评论内容点击事件
     *
     * @param position
     * @return
     */
    public SpannableString setClickableSpanContent(final CommentsBean bean, final int position) {
        final SpannableString string = new SpannableString(bean.getContent());
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO: 2017/9/3 评论内容点击事件
                if (listener != null)
                    listener.onContentClick(position, bean);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置显示的内容文本颜色
                ds.setColor(0xff686868);
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    /**
     * 设置评论用户名字点击事件
     *
     * @param item
     * @param bean
     * @return
     */
    public SpannableString setClickableSpan(final String commentsId, final String item, final UserBean bean) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO: 2017/9/3 评论用户名字点击事件
                if (listener != null)
                    listener.onUserClick(commentsId, bean);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置显示的用户名文本颜色
                ds.setColor(0xff387dcc);
                ds.setUnderlineText(false);
            }
        };

        string.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    /**
     * 定义一个用于回调的接口
     */
    public interface onItemClickListener {
        //点击整个条目
        void onItemClick(int position, CommentsBean bean);

        //点击评论人
        void onUserClick(String commentsId, UserBean bean);

        //点击评论内容
        void onContentClick(int position, CommentsBean bean);
    }

    class CircleMovementMethod extends BaseMovementMethod {
        private final static int DEFAULT_COLOR_ID = android.R.color.transparent;
        /**
         * 整个textView的背景色
         */
        private int textViewBgColor;
        /**
         * 点击部分文字时部分文字的背景色
         */
        private int clickableSpanBgClor;

        private BackgroundColorSpan mBgSpan;
        private ClickableSpan[] mClickLinks;

        private boolean isParseTv = false;

        public boolean isParseTv() {
            return isParseTv;
        }

        public void setParseTv(boolean parseTv) {
            isParseTv = parseTv;
        }

        /**
         * @param clickableSpanBgClor 点击选中部分时的背景色
         */
        public CircleMovementMethod(int clickableSpanBgClor) {
            this.clickableSpanBgClor = clickableSpanBgClor;
        }

        /**
         * @param clickableSpanBgClor 点击选中部分时的背景色
         * @param textViewBgColor     整个textView点击时的背景色
         */
        public CircleMovementMethod(int clickableSpanBgClor, int textViewBgColor) {
            this.textViewBgColor = textViewBgColor;
            this.clickableSpanBgClor = clickableSpanBgClor;
        }

        public boolean onTouchEvent(TextView widget, Spannable buffer,
                                    MotionEvent event) {

            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                mClickLinks = buffer.getSpans(off, off, ClickableSpan.class);
                if (mClickLinks.length > 0) {
                    setParseTv(false);
                    // 点击的是Span区域，不要把点击事件传递
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(mClickLinks[0]),
                            buffer.getSpanEnd(mClickLinks[0]));
                    //设置点击区域的背景色
                    mBgSpan = new BackgroundColorSpan(clickableSpanBgClor);
                    buffer.setSpan(mBgSpan,
                            buffer.getSpanStart(mClickLinks[0]),
                            buffer.getSpanEnd(mClickLinks[0]),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    setParseTv(true);
                    // textview选中效果
//                widget.setBackgroundColor(textViewBgColor);
                    widget.setBackgroundResource(DEFAULT_COLOR_ID);
                }

            } else if (action == MotionEvent.ACTION_UP) {
                if (mClickLinks.length > 0) {
                    mClickLinks[0].onClick(widget);
                    if (mBgSpan != null) {//移除点击时设置的背景span
                        buffer.removeSpan(mBgSpan);
                    }
                } else {

                }
                Selection.removeSelection(buffer);
                widget.setBackgroundResource(DEFAULT_COLOR_ID);
            } else if (action == MotionEvent.ACTION_MOVE) {
                //这种情况不用做处理
            } else {
                if (mBgSpan != null) {//移除点击时设置的背景span
                    buffer.removeSpan(mBgSpan);
                }
                widget.setBackgroundResource(DEFAULT_COLOR_ID);
            }
            return Touch.onTouchEvent(widget, buffer, event);
        }
    }

}
