package com.daxiang.lottery.common;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Android on 2018/1/11.
 */

public class DiceThread implements Runnable{
    private Handler diceHandler;
    public DiceThread(Handler diceHandler){
        this.diceHandler=diceHandler;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message messageDice = new Message();
        messageDice.what = 1;
        diceHandler.sendMessage(messageDice);

    }
}
