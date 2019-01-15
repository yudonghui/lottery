package com.daxiang.lottery.common;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Android on 2018/1/11.
 */

public class DiceThread3 implements Runnable {
    private Handler diceHandler;
    private int xiaoTime;
    public DiceThread3(Handler diceHandler,int xiaoTime) {
        this.diceHandler = diceHandler;
        this.xiaoTime=xiaoTime;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(xiaoTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //逐渐缩小并且回去到指定位置之后
        Message messageDice = new Message();
        messageDice.what = 3;
        diceHandler.sendMessage(messageDice);
    }
}
