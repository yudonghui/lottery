package com.daxiang.lottery.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.utils.dialogutils.DialogAnimotion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2018/8/23.
 */

public class ReportDialog {
    private GridView mGridView;
    private TextView mCancel;
    private TextView mConfirm;
    String[] lotteryStr = {"政治/色情", "辱骂/造谣", "违法信息", "广告/营销",
            "抄袭/违规", "人身攻击", "不实信息", "其他"};
    private Context mContext;
    private AlertDialog dialog;
    private String postId;
    private String commentId;
    List<ReportBean> mList = new ArrayList<>();
    private DialogAdapter mAdapter;

    public void selecte(Context mContext, String postId, String commentId) {
        this.mContext = mContext;
        this.postId = postId;
        this.commentId = commentId;
        View inflate = View.inflate(mContext, R.layout.dialog_report, null);
        mGridView = (GridView) inflate.findViewById(R.id.gridView);
        mCancel = (TextView) inflate.findViewById(R.id.cancel);
        mConfirm = (TextView) inflate.findViewById(R.id.confirm);

        for (int i = 0; i < lotteryStr.length; i++) {
            ReportBean reportBean = new ReportBean();
            reportBean.setContent(lotteryStr[i]);
            if (i == 0) {//默认选中第一条
                reportBean.setIsSelect(1);
            } else {
                reportBean.setIsSelect(0);
            }
            mList.add(reportBean);
        }


        mAdapter = new DialogAdapter();
        mGridView.setAdapter(mAdapter);
        dialog = new AlertDialog.Builder(mContext).create();
        dialog.setView(inflate);
        dialog.show();
        DialogAnimotion.setAnimotion(dialog);
        addListener();
    }

    private void addListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReportBean reportBean = mList.get(position);
                if (reportBean.getIsSelect() == 1) {
                    reportBean.setIsSelect(0);
                } else reportBean.setIsSelect(1);

                mAdapter.notifyDataSetChanged();

            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mList.size(); i++) {
                    ReportBean reportBean = mList.get(i);
                    int isSelect = reportBean.getIsSelect();
                    String content = reportBean.getContent();
                    if (isSelect == 1)
                        sb.append(content + ",");
                }
                if (TextUtils.isEmpty(sb)) {
                    Toast.makeText(mContext, "请选择举报内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                String s = String.valueOf(sb);
                String substring = s.substring(0, s.length() - 1);
                HttpInterface2 mHttp = new HttpUtils2(mContext);
                Bundle params = new Bundle();
                params.putString("postId", postId);
                params.putString("userName", LotteryApp.nikeName);
                params.putString("token", LotteryApp.token);
                params.putString("content", substring);
                params.putString("timeStamp", System.currentTimeMillis() + "");
                if (!TextUtils.isEmpty(commentId))
                    params.putString("commentId", commentId);
                mHttp.post(Url.POST_REPORT, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 0) {
                                dialog.dismiss();
                                Toast.makeText(mContext, "举报完成", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    class DialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lotteryStr.length;
        }

        @Override
        public Object getItem(int position) {
            return lotteryStr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.item_report_dialog, null);
            final TextView mButton = (TextView) convertView.findViewById(R.id.tv_item);
            ReportBean reportBean = mList.get(position);
            String content = reportBean.getContent();
            int isSelect = reportBean.getIsSelect();
            mButton.setText(TextUtils.isEmpty(content) ? "" : content);
            if (isSelect == 0) {
                mButton.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mButton.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            } else {
                mButton.setBackgroundResource(R.drawable.shape_btn);
                mButton.setTextColor(mContext.getResources().getColor(R.color.white));
            }
            return convertView;
        }
    }

    class ReportBean {
        private String content;
        private int isSelect;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(int isSelect) {
            this.isSelect = isSelect;
        }
    }

    public interface CenterClickListener {
        void onClick(ArrayList<String> clickList, String type);
    }
}
