package com.daxiang.lottery.common;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Android on 2018/1/10.
 */

public class RandomNumber {
    private Context mContext;
    private int lvNum;
    private int gvNum;
    private int minNum = 1;
    private int minBlueNum;
    private int gvBlueNum;
    private String mLotcode;

    public void setNum(Context mContext, String mLotcode, int lvNum, int minNum, int gvNum, int minBlueNum, int gvBlueNum) {
        this.mContext = mContext;
        this.mLotcode = mLotcode;
        this.lvNum = lvNum;
        this.minNum = minNum;
        this.gvNum = gvNum;
        this.minBlueNum = minBlueNum;
        this.gvBlueNum = gvBlueNum;
    }

    public void getRandom(int mType,
                          Map<Integer, List<String>> mapBall, Map<Integer, List<Integer>> randomMapBall) {
        switch (mLotcode) {
            case LotCode.DLT_CODE:
                getDLTSSQ(mapBall, randomMapBall);
                break;
            case LotCode.SSQ_CODE:
                getDLTSSQ(mapBall, randomMapBall);
                break;
            default:
                getNumber(mType, mapBall, randomMapBall);
                break;
        }
    }

    private void getNumber(int mType, Map<Integer, List<String>> mapBall,
                           Map<Integer, List<Integer>> randomMapBall) {
        for (int i = 0; i < lvNum; i++) {
            List<Integer> list = new ArrayList<>();
            List<String> listmap = new ArrayList<>();
            for (int j = 0; j < minNum; j++) {
                int n;
                if (mType == ConstantNum.QIAN_2_ZHI_XUAN || mType == ConstantNum.QIAN_3_ZHI_XUAN) {
                    //让随机选择的数字不与别的行的数字重复
                    n = getRandom2(i, list, randomMapBall);
                } else {
                    n = getRandom(list, gvNum);
                }
                list.add(n);
                if ("33".equals(mLotcode) || "35".equals(mLotcode)
                        || "10022".equals(mLotcode) || "52".equals(mLotcode)) {
                    listmap.add("" + n);
                } else {
                    if (n < 10) {
                        listmap.add("0" + n);
                    } else {
                        listmap.add("" + n);
                    }
                }
            }
            randomMapBall.put(i, list);
            mapBall.put(i, listmap);
        }
    }

    /*
    * @minNum 红球最少选择的号码
    * @gvNum 红球总数
    * @minBlueNum 蓝球最少选择的号码
    * @gvBlueNum 蓝球总数
    * */
    private void getDLTSSQ(Map<Integer, List<String>> mapBall,
                           Map<Integer, List<Integer>> randomMapBall) {
        for (int i = 0; i < 2; i++) {
            List<Integer> list = new ArrayList<>();
            List<String> listmap = new ArrayList<>();
            if (i == 0) {
                for (int j = 0; j < minNum; j++) {
                    int n = getRandom(list, gvNum);
                    list.add(n);
                    if (n < 10) {
                        listmap.add("0" + n);
                    } else {
                        listmap.add("" + n);
                    }
                }
            } else {
                for (int j = 0; j < minBlueNum; j++) {
                    int n = getRandom(list, gvBlueNum);
                    list.add(n);
                    if (n < 10) {
                        listmap.add("0" + n);
                    } else {
                        listmap.add("" + n);
                    }
                }
            }
            randomMapBall.put(i, list);
            mapBall.put(i, listmap);
        }
    }

    public void getK3Auto(int mType,
                          Map<Integer, List<String>> mapBall, Map<Integer, List<Integer>> randomMapBall) {
        int[] randomArray = getRandom(mType);
        if (randomArray == null || randomArray.length == 0) return;
        if (mType == ConstantNum.HE_ZHI) {
            int randomNumber = randomArray[0] + randomArray[1] + randomArray[2];
            List<Integer> list = new ArrayList<>();
            List<String> listmap = new ArrayList<>();
            list.add(randomNumber - 3);
            listmap.add(randomNumber + "");
            randomMapBall.put(0, list);
            mapBall.put(0, listmap);
        } else if (mType == ConstantNum.SAN_TONG_HAO) {
            int randomNumber = randomArray[0];
            List<Integer> list = new ArrayList<>();
            List<String> listmap = new ArrayList<>();
            list.add(randomNumber - 1);
            listmap.add(randomNumber + "" + randomNumber + "" + randomNumber);
            randomMapBall.put(0, list);
            mapBall.put(0, listmap);
        } else if (mType == ConstantNum.SAN_BU_TONG_HAO) {
            List<Integer> list = new ArrayList<>();
            List<String> listmap = new ArrayList<>();
            list.add(randomArray[0] - 1);
            list.add(randomArray[1] - 1);
            list.add(randomArray[2] - 1);
            listmap.add(randomArray[0] + "");
            listmap.add(randomArray[1] + "");
            listmap.add(randomArray[2] + "");
            randomMapBall.put(0, list);
            mapBall.put(0, listmap);
        } else if (mType == ConstantNum.ER_TONG_HAO_FU) {//两个筛子
            int randomNumber = randomArray[0];
            List<Integer> list = new ArrayList<>();
            List<String> listmap = new ArrayList<>();
            list.add(randomNumber - 1);
            listmap.add(randomNumber + "" + randomNumber);
            randomMapBall.put(0, list);
            mapBall.put(0, listmap);
        } else if (mType == ConstantNum.ER_BU_TONG_HAO) {
            List<Integer> list = new ArrayList<>();
            List<String> listmap = new ArrayList<>();
            list.add(randomArray[0] - 1);
            list.add(randomArray[1] - 1);
            listmap.add(randomArray[0] + "");
            listmap.add(randomArray[1] + "");
            randomMapBall.put(0, list);
            mapBall.put(0, listmap);
        } else if (mType == ConstantNum.ER_TONG_HAO_DAN) {
            List<Integer> list = new ArrayList<>();
            List<String> listmap = new ArrayList<>();
            list.add(randomArray[0] - 1);
            listmap.add(randomArray[0] + "" + randomArray[1]);
            randomMapBall.put(0, list);
            mapBall.put(0, listmap);
            List<Integer> list2 = new ArrayList<>();
            List<String> listmap2 = new ArrayList<>();
            list2.add(randomArray[2] - 1);
            listmap2.add(randomArray[2] + "");
            randomMapBall.put(1, list2);
            mapBall.put(1, listmap2);
        }
    }

    public float x;//筛子的初始x
    public float y;//筛子的初始y
    public float x2;//筛子的初始x
    public float y2;//筛子的初始y
    public float x3;//筛子的初始x
    public float y3;//筛子的初始y
    public FrameLayout.LayoutParams layoutParams;

    public void getK3(Handler diceHandler, FrameLayout mFl_saizi, ImageView mImageDice, ImageView mImageDice2, ImageView mImageDice3) {
        int displayHeight = DisplayUtil.getDisplayHeight();
        int displayWidth = DisplayUtil.getDisplayWidth();
        int size = DisplayUtil.dip2px(80);
        x = displayWidth / 2;
        y = displayHeight / 3;
        x2 = displayWidth / 2 - DisplayUtil.dip2px(10);
        y2 = displayHeight * 4 / 9;
        x3 = displayWidth / 4;
        y3 = (float) (displayHeight * 0.38);
        layoutParams = new FrameLayout.LayoutParams(size, size);
        mImageDice.setImageDrawable(mContext.getResources().getDrawable(
                R.drawable.anim_dice));
        mImageDice2.setImageDrawable(mContext.getResources().getDrawable(
                R.drawable.anim_dice));
        mImageDice3.setImageDrawable(mContext.getResources().getDrawable(
                R.drawable.anim_dice));
        AnimationDrawable drawable = (AnimationDrawable) mImageDice.getDrawable();
        AnimationDrawable drawable2 = (AnimationDrawable) mImageDice2.getDrawable();
        AnimationDrawable drawable3 = (AnimationDrawable) mImageDice3.getDrawable();
        drawable.start();
        drawable2.start();
        drawable3.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            path(mImageDice, mImageDice2, mImageDice3);
        } else {
            pathLine(mImageDice, mImageDice2, mImageDice3);
        }
        new Thread(new DiceThread(diceHandler)).start();
    }

    private void pathLine(ImageView mImageDice, ImageView mImageDice2, ImageView mImageDice3) {
        ObjectAnimator mAnimatorX = ObjectAnimator.ofFloat(mImageDice, "translationX", 0, -100, 100, -90, 200, 80, -80, 0);
        mAnimatorX.setDuration(2000);
        ObjectAnimator mAnimatorY = ObjectAnimator.ofFloat(mImageDice, "translationY", 0, -100, 90, -200, 200, 80 - 80, 0);
        mAnimatorY.setDuration(2000);
        ObjectAnimator mAnimatorX2 = ObjectAnimator.ofFloat(mImageDice2, "translationX", 0, 160, -180, 180, -190, -80, 80, 0);
        mAnimatorX2.setDuration(2000);
        ObjectAnimator mAnimatorY2 = ObjectAnimator.ofFloat(mImageDice2, "translationY", 0, -180, 130, -180, 190, 80, -80, 0);
        mAnimatorY2.setDuration(2000);
        ObjectAnimator mAnimatorX3 = ObjectAnimator.ofFloat(mImageDice3, "translationX", 0, 150, -150, -150, 140, 80, -80, 0);
        mAnimatorX3.setDuration(2000);
        ObjectAnimator mAnimatorY3 = ObjectAnimator.ofFloat(mImageDice3, "translationY", 0, 220, -150, -150, 150, -80, 80, 0);
        mAnimatorY3.setDuration(2000);
        mAnimatorX.start();
        mAnimatorY.start();
        mAnimatorX2.start();
        mAnimatorY2.start();
        mAnimatorX3.start();
        mAnimatorY3.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void path(ImageView mImageDice, ImageView mImageDice2, ImageView mImageDice3) {
        Path path = new Path();
        path.moveTo(x, y);
        path.quadTo(50, 300, 200, 700);
        path.cubicTo(200, 400, 300, 700, 200, 800);
        path.cubicTo(200, 400, 300, 700, 600, 300);
        path.quadTo(50, 300, 600, 800);
        path.quadTo(500, 100, x, y);
        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(mImageDice, View.X, View.Y, path);
        mAnimator.setDuration(2000);
        mAnimator.start();
        Path path2 = new Path();
        path2.moveTo(x2, y2);
        path2.quadTo(50, 300, 100, 400);
        path2.cubicTo(300, 600, 500, 250, 600, 700);
        path2.cubicTo(500, 200, 600, 350, 300, 400);
        path2.quadTo(50, 300, 200, 300);
        path2.quadTo(500, 0, x2, y2);
        ObjectAnimator mAnimator2 = ObjectAnimator.ofFloat(mImageDice2, View.X, View.Y, path2);
        mAnimator2.setDuration(2000);
        mAnimator2.start();
        Path path3 = new Path();
        path3.moveTo(x3, y3);
        path3.quadTo(500, 300, 600, 700);
        path3.cubicTo(800, 200, 600, 350, 600, 500);
        path3.cubicTo(500, 200, 600, 350, 100, 200);
        path3.quadTo(700, 300, 500, 600);
        path3.quadTo(500, 100, x3, y3);
        ObjectAnimator mAnimator3 = ObjectAnimator.ofFloat(mImageDice3, View.X, View.Y, path3);
        mAnimator3.setDuration(2000);
        mAnimator3.start();
    }

    //递归判断是否随机有重复的球
    private int getRandom(List<Integer> random, int r) {
        int j;
        if ("33".equals(mLotcode) || "35".equals(mLotcode) || "10022".equals(mLotcode) || "52".equals(mLotcode)) {
            j = (int) (Math.random() * r);
        } else {
            j = (int) (Math.random() * r + 1);
        }
        if (!random.contains(j)) {
            return j;
        } else {
            return getRandom(random, r);
        }

    }

    private int getRandom2(int i, List<Integer> list, Map<Integer, List<Integer>> randomMapBall) {
        int n = getRandom(list, gvNum);
        Iterator it = randomMapBall.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            //这个判断的目的是只判断除自身这一行除外的行中的数字有没有相同的。
            if ((int) entry.getKey() != i) {
                List<Integer> value = (List<Integer>) entry.getValue();
                if (value.contains(n)) {
                    return getRandom2(i, list, randomMapBall);
                }
            } else {
                return n;
            }
        }
        return n;
    }

    private int getRandomNumber(int n) {
        return new Random().nextInt(n);
    }


    public int[] getRandom(int mode) {
        int[] ints = null;
        switch (mode) {
            case ConstantNum.HE_ZHI:
                int randomInt = 1 + new Random().nextInt(6); // 1~6
                int randomInt2 = 1 + new Random().nextInt(6); // 1~6
                int randomInt3 = 1 + new Random().nextInt(6); // 1~6
                ints = new int[]{randomInt, randomInt2, randomInt3};
                Arrays.sort(ints);//升序
                break;
            case ConstantNum.SAN_TONG_HAO:
                int randomInt4 = 1 + new Random().nextInt(6); // 1~6
                ints = new int[]{randomInt4, randomInt4, randomInt4};
                break;
            case ConstantNum.ER_TONG_HAO_DAN:
                int randomInt5 = 1 + new Random().nextInt(6); // 1~6
                int randomInt6 = getNumber(randomInt5);
                ints = new int[]{randomInt5, randomInt5, randomInt6};
                break;
            case ConstantNum.SAN_BU_TONG_HAO:
                int randomInt7 = 1 + new Random().nextInt(6); // 1~6
                int randomInt8 = getNumber(randomInt7);
                int randomInt9 = getNumber(randomInt7, randomInt8);
                ints = new int[]{randomInt7, randomInt8, randomInt9};
                Arrays.sort(ints);//升序
                break;
            case ConstantNum.ER_BU_TONG_HAO:
                int randomInt10 = 1 + new Random().nextInt(6); // 1~6
                int randomInt11 = getNumber(randomInt10);
                ints = new int[]{randomInt10, randomInt11};
                Arrays.sort(ints);//升序
                break;
            case ConstantNum.ER_TONG_HAO_FU:
                int randomInt12 = 1 + new Random().nextInt(6); // 1~6
                ints = new int[]{randomInt12, randomInt12};
                //Arrays.sort(ints);//升序
                break;
            default:
                break;

        }
        return ints;
    }

    private int getNumber(int random) {
        int randomInt = 1 + new Random().nextInt(6); // 1~6
        if (random == randomInt) {
            return getNumber(random);
        } else return randomInt;
    }

    private int getNumber(int random, int random2) {
        int randomInt = 1 + new Random().nextInt(6); // 1~6
        if (random == randomInt || random2 == randomInt) {
            return getNumber(random);
        } else return randomInt;
    }
}
