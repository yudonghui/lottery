package com.daxiang.lottery.utils.dialogutils;

import android.app.Dialog;
import android.view.Window;

import com.daxiang.lottery.R;
/**
 * Created by Administrator on 2016/11/2 0002.
 */
public class DialogAnimotion {
    public static void setAnimotion(Dialog dialog){
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.anim_bottom_top);
    }
    public static void setAnimotionin(Dialog dialog){
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.anim_match_wheel);
    }
}
