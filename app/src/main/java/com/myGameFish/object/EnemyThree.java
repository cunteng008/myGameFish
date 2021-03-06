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
 * Created by CMJ on 2016/11/25.
 */

public class EnemyThree extends EnemyFish {
    private static int currentCount = 0;	 //	对象当前的数量
    private Bitmap enemyThree;  // 对象图片
    public static int sumCount = 3;	 	 	 //	屏幕上对象总的数量，若少于则生成新的鱼
    private List<Bitmap> enemyThrees = new ArrayList<Bitmap>();
    private int index = 0;
    public EnemyThree(Resources resources) {
        super(resources);
        initBitmap();			// 初始化图片资源
        this.score = 160;		// 为对象设置分数
    }
    //初始化数据
    @Override
    public void initial(int arg0,float arg1,float arg2){
        Random ran_1 = new Random();
        index = ran_1.nextInt(2);
        if(index == 0){
            enemyThree = enemyThrees.get(index);
            isAppearOnRight = true;
        }else if(index == 1){
            enemyThree = enemyThrees.get(index);
            isAppearOnRight = false;
        }
        isAlive = true;
        currentFrame = 0;
        Random ran = new Random();
        speed = ran.nextInt(6) + 6 * arg0;
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
        enemyThrees.add(BitmapFactory.decodeResource(resources, R.drawable.enemy_0301));
        enemyThrees.add(BitmapFactory.decodeResource(resources, R.drawable.enemy_0302));

        enemyThree = enemyThrees.get(0);
        object_width = enemyThree.getWidth();			//获得每一帧位图的宽
        object_height = enemyThree.getHeight()/3;		//获得每一帧位图的高
    }
    // 对象的绘图函数
    @Override
    public void drawSelf(Canvas canvas) {

        if(isVisible){
            int y = (int) (currentFrame * object_height);
            canvas.save();
            canvas.clipRect(object_x,object_y+object_height,object_x +object_width,object_y );
            canvas.drawBitmap(enemyThree, object_x , object_y-y,paint);
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
        for(int i=0;i<enemyThrees.size();i++){
            if(!enemyThrees.get(0).isRecycled()) {
                enemyThrees.get(0).recycle();
            }
        }
    }
}
