package com.myGameFish.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.myGameFish.activity.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by CMJ on 2016/11/18.
 */

public class EnemyTwo extends EnemyFish {
    private static int currentCount = 0;	 //	对象当前的数量
    private Bitmap enemyTwo; // 对象图片
    public static int sumCount = 4;	 	 	 //	屏幕上对象总的数量，若少于则生成新的鱼
    private  List<Bitmap> enemyTwos = new ArrayList<Bitmap>();
    private int index = 0;

    public EnemyTwo(Resources resources) {
        super(resources);
        initBitmap();			// 初始化图片资源
        this.score = 80;		// 为对象设置分数
    }
    //初始化数据
    @Override
    public void initial(int arg0,float arg1,float arg2){
        Random ran_1 = new Random();
        index = ran_1.nextInt(2);
        if(index == 0){
            enemyTwo = enemyTwos.get(index);
            isAppearOnRight = true;
        }else if(index == 1){
            enemyTwo = enemyTwos.get(index);
            isAppearOnRight = false;
        }
        isAlive = true;
        currentFrame = 0;
        Random ran = new Random();
        speed = ran.nextInt(8) + 8 * arg0;
        //以防敌人的鱼隐藏在边以外
        if(isAppearOnRight){
            object_y = ran.nextInt((int)(screen_height - object_height));
            object_x = -object_width * (currentCount*2 + 1);
        }else {
            object_y = ran.nextInt((int)(screen_height - object_height));
            object_x = object_height * (currentCount*2 + 1)+screen_width;
        }
        currentCount ++;
        if(currentCount >= sumCount){
            currentCount = 0;
        }
    }
    // 初始化图片资源
    @Override
    public void initBitmap() {
        enemyTwos.add(BitmapFactory.decodeResource(resources, R.drawable.enemy_0201));
        enemyTwos.add(BitmapFactory.decodeResource(resources, R.drawable.enemy_0202));

        enemyTwo = enemyTwos.get(0);
        object_width = enemyTwo.getWidth();			//获得每一帧位图的宽
        object_height = enemyTwo.getHeight()/3;		//获得每一帧位图的高
    }
    // 对象的绘图函数
    @Override
    public void drawSelf(Canvas canvas) {

        if(isVisible){
            int y = (int) (currentFrame * object_height);
            canvas.save();
            canvas.clipRect(object_x,object_y+object_height,object_x +object_width,object_y );
            canvas.drawBitmap(enemyTwo, object_x , object_y-y,paint);
            canvas.restore();
            currentFrame++;
            if (currentFrame >= 3) {
                currentFrame = 0;
            }
        }
        logic();
    }
    // 释放资源
    @Override
    public void release() {
        if(!enemyTwo.isRecycled()){
            enemyTwo.recycle();
        }
    }
}
