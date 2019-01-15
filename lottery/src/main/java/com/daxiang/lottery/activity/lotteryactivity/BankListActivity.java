package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daxiang.lottery.R;

import java.util.ArrayList;

public class BankListActivity extends AppCompatActivity {
    private ArrayList<String> bankList;
    private String[] mStrs = {"aaa", "bbb", "ccc", "airsaid"};
    private EditText mSearchView;
    private ListView mListView;
    private BankListAdaper adapter;
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);
        bankList = getIntent().getStringArrayListExtra("bankList");
        initView();
        mList.addAll(bankList);
        adapter = new BankListAdaper();
        mListView.setAdapter(adapter);
        mListView.setTextFilterEnabled(false);
        //mSearchView.setSubmitButtonEnabled(false);
        //mSearchView.setSuggestionsAdapter();
        addListener();
    }

    private void addListener() {
        //返回
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty( s.toString())) {
                    newData( s.toString());
                    //mListView.setFilterText(newText);
                } else {
                    mList.clear();
                    mList.addAll(bankList);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        // 设置搜索文本监听
       /* mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    newData(newText);
                    //mListView.setFilterText(newText);
                } else {
                    mList.clear();
                    mList.addAll(bankList);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }

        });*/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("bankName",mList.get(position));
                setResult(3333,intent);
                finish();
            }
        });
    }

    ArrayList<String> mList = new ArrayList<>();

    private void newData(String newText) {
        mList.clear();
        for (int i = 0; i < bankList.size(); i++) {
            boolean flag=true;
            //对输入的字符串进行遍历，看本次循环的银行中是否包含输入的所有字符。
            //只要有一个输入的有一个字符不被包含那么就让flag为false。
            for (int j = 0; j < newText.length(); j++) {
                String c = String.valueOf(newText.charAt(j));
                flag=flag&&bankList.get(i).contains(c);
            }
            if (flag) {
                mList.add(bankList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listView);
        mSearchView = (EditText) findViewById(R.id.et_search);
        mBack= (ImageView) findViewById(R.id.iamge_back);
    }

    class BankListAdaper extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.item_bank_list, null);
                mViewHolder = new ViewHolder();
                mViewHolder.mTextView = (TextView) convertView.findViewById(R.id.text_bank_name);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.mTextView.setText(mList.get(position));
            return convertView;
        }
    }

    class ViewHolder {
        TextView mTextView;
    }
}
