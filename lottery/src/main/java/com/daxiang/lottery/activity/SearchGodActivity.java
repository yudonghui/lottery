package com.daxiang.lottery.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.SearchGodAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.SearchData;
import com.daxiang.lottery.search.FlowLayout;
import com.daxiang.lottery.search.GodAdapter;
import com.daxiang.lottery.search.TagFlowLayout;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.StatusBarUtil;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchGodActivity extends BaseNoTitleActivity {
    private Context mContext;
    private EditText mEditText;
    private TextView mCancel;
    private ImageView mDelete;
    private ImageView mDeleteHis;
    private ScrollView mScrollView;
    private LinearLayout mText_history;
    private TagFlowLayout mHistory;
    private TagFlowLayout mRecommend;
    private ListView mListView;
    private ArrayList<String> historyList = new ArrayList<>();
    private ArrayList<String> recommendList = new ArrayList<>();
    private SearchGodAdapter mGodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_god);
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        StatusBarUtil.StatusBarLightMode(this);
        mContext = this;
        initView();
        mGodAdapter = new SearchGodAdapter(mContext, dataList);
        mListView.setAdapter(mGodAdapter);
        addListener();
        addData();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.editText);
        mCancel = (TextView) findViewById(R.id.cancel);
        mDelete = (ImageView) findViewById(R.id.delete);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mText_history = (LinearLayout) findViewById(R.id.ll_history);
        mHistory = (TagFlowLayout) findViewById(R.id.history);
        mDeleteHis = (ImageView) findViewById(R.id.delete_history);
        mRecommend = (TagFlowLayout) findViewById(R.id.recommend);
        mListView = (ListView) findViewById(R.id.listView);
    }

    private void addListener() {
        mHistory.setOnTagClickListener(HistoryTagListener);
        mRecommend.setOnTagClickListener(RecommendTagListener);
        mEditText.addTextChangedListener(TextChangListener);
        mEditText.setOnEditorActionListener(SearchListener);
        mDeleteHis.setOnClickListener(DeleteHisListener);
        mCancel.setOnClickListener(CancelListener);
        mDelete.setOnClickListener(DeleteListener);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", dataList.get(position).getUserId());
                startActivity(GodInfoActivity.class, bundle);
            }
        });
    }

    View.OnClickListener DeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mEditText.setText("");
            mDelete.setVisibility(View.GONE);
        }
    };
    View.OnClickListener CancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mScrollView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            addData();
        }
    };
    View.OnClickListener DeleteHisListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HintDialogUtils2 mHintDialog = new HintDialogUtils2(mContext);
            mHintDialog.setTitleVisiable(true);
            mHintDialog.setTitle("温馨提示");
            mHintDialog.setMessage("确认删除全部历史记录？");
            mHintDialog.setConfirm("确定", new DialogHintInterface() {
                @Override
                public void callBack(View view) {
                    SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    sp.edit().putString("searchHistory", "").apply();
                    mHistory.setVisibility(View.GONE);
                    mText_history.setVisibility(View.GONE);
                }
            });

        }
    };
    TextView.OnEditorActionListener SearchListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String text = mEditText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(mContext, "搜索内容为空", Toast.LENGTH_SHORT).show();
                } else
                    searchData(text);
            }
            return false;
        }
    };
    TextWatcher TextChangListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            if (TextUtils.isEmpty(text)) {
                mDelete.setVisibility(View.GONE);
            } else {
                mDelete.setVisibility(View.VISIBLE);
            }
        }
    };
    ArrayList<SearchData.DataBean> dataList = new ArrayList<>();

    private void searchData(String text) {
        mMap.put(text, text);
        setMap(mMap);
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("userName", text);
        mHttp.get(Url.QUERY_BY_NAME, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                SearchData searchData = gson.fromJson(result, SearchData.class);
                int code = searchData.getCode();
                String msg = searchData.getMsg();
                if (code == 0) {
                    List<SearchData.DataBean> data = searchData.getData();
                    dataList.clear();
                    dataList.addAll(data);
                    if (dataList.size() > 0) {
                        mScrollView.setVisibility(View.GONE);
                        mListView.setVisibility(View.VISIBLE);
                        mGodAdapter.notifyDataSetChanged();
                    } else Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    TagFlowLayout.OnTagClickListener HistoryTagListener = new TagFlowLayout.OnTagClickListener() {
        @Override
        public boolean onTagClick(View view, int position, FlowLayout parent) {
            String string = historyList.get(position);
            searchData(string);
            // Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
            return true;
        }
    };
    TagFlowLayout.OnTagClickListener RecommendTagListener = new TagFlowLayout.OnTagClickListener() {
        @Override
        public boolean onTagClick(View view, int position, FlowLayout parent) {
            String string = recommendList.get(position);
            searchData(string);
            //Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    private HashMap<String, String> mMap = new HashMap<>();

    private void addData() {
        mMap = (HashMap<String, String>) getMap();
        if (mMap == null || mMap.size() == 0) {
            mHistory.setVisibility(View.GONE);
            mText_history.setVisibility(View.GONE);
        } else {
            mHistory.setVisibility(View.VISIBLE);
            mText_history.setVisibility(View.VISIBLE);
            historyList.clear();
            for (Map.Entry<String, String> entry : mMap.entrySet()) {
                historyList.add(entry.getKey());
            }
            GodAdapter godAdapter = new GodAdapter(historyList, mContext, mHistory);
            mHistory.setAdapter(godAdapter);
        }
    }

    public Map<String, String> getMap() {
        Map<String, String> defaultMonthMap = new HashMap<>();
        SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String year2month = sp.getString("searchHistory", "");
        if (year2month.length() > 0) {
            JSONTokener jsonTokener = new JSONTokener(year2month);
            try {
                JSONArray jsonArray = (JSONArray) jsonTokener.nextValue();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    defaultMonthMap.put(jsonObject.getString("year"), jsonObject.getString("month"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return defaultMonthMap;
    }

    /*
    * array（）  ——开始添加一个array
      endArray（）   ——结束一个array
      object（）    ——开始添加一个object
      endObject（） ——结束一个object
      key（String s）——添加一个键
      value系列方法  ——添加一个值
      toString（）     ——返回一个JSON文本的字符
    * */
    public void setMap(Map<String, String> year2monthMap) {
        if (year2monthMap != null) {
            JSONStringer jsonStringer = new JSONStringer();
            try {
                jsonStringer.array();
                for (String string : year2monthMap.keySet()) {
                    jsonStringer.object();
                    jsonStringer.key("year");
                    jsonStringer.value(string);
                    jsonStringer.key("month");
                    jsonStringer.value(year2monthMap.get(string));
                    jsonStringer.endObject();
                }
                jsonStringer.endArray();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            sp.edit().putString("searchHistory", jsonStringer.toString()).apply();
        }
    }
}
