package com.daxiang.lottery.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Android on 2017/12/27.
 */

public class BaseNoDataFragment extends Fragment {
    public void startActivity(Context mContext, Class classss, Bundle bundle){
        Intent intent = new Intent(mContext, classss);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
