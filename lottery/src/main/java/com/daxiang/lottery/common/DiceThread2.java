package com.daxiang.lottery.common;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Android on 2018/1/11.
 */

public class DiceThread2 implements Runnable {
    private Handler diceHandler;
    private int time;

    public DiceThread2(Handler diceHandler, int time) {
        this.diceHandler = diceHandler;
        this.time = time;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message messageDice = new Message();
        messageDice.what = 2;
        diceHandler.sendMessage(messageDice);
    }
}
