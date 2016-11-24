package com.myGameFish.view;

import java.util.ArrayList;
import java.util.List;

import com.myGameFish.constant.ConstantUtil;
import com.myGameFish.factory.GameObjectFactory;
import com.myGameFish.activity.R;
import com.myGameFish.object.EnemyFish;
import com.myGameFish.object.EnemyThree;
import com.myGameFish.object.EnemyTwo;
import com.myGameFish.object.GameObject;
import com.myGameFish.object.MyFish;
import com.myGameFish.object.EnemyOne;
import com.myGameFish.souds.GameSoundPool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.SoundPool;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
/*游戏进行的主界面*/
public class MainView extends BaseView{
	private int sumScore = 0;			// 游戏总得分
	private int speedTime;			// 游戏速度的倍数
	private float bg_y;				// 图片的坐标
	private float bg_y2;
	private float play_bt_w;
	private float play_bt_h;
	private boolean isPlay;			// 标记游戏运行状态
	private boolean isTouchFish;	// 判断玩家是否按下屏幕
	private Bitmap background; 		// 背景图片
	private Bitmap background2; 	// 背景图片
	private Bitmap playButton; 		// 开始/暂停游戏的按钮图片
	private MyFish myFish;		// 玩家的鱼
	private List<EnemyFish> enemyFishs;
	private GameObjectFactory factory;
	private int levelsScore[] = {2000,4000,8000,16000,3200};
	private int levelsIndex = 0;
	public MainView(Context context,GameSoundPool sounds) {
		super(context,sounds);
		//背景音乐播放停不下来
		//sounds.playSound(6,-1);
		isPlay = true;
		speedTime = 1;
		factory = new GameObjectFactory();						  //工厂类
		enemyFishs = new ArrayList<EnemyFish>();
		myFish = (MyFish) factory.createMyFish(getResources());//生产玩家的
		myFish.setMainView(this);
		//一次产生sumCount条鱼，实际上整个游戏鱼一条都没减少
		//设置isAlive为true时把鱼画出来
		for(int i = 0; i < EnemyOne.sumCount; i++){
			//生产敌人一号鱼
			EnemyOne enemyOne = (EnemyOne) factory.createEnemyOne(getResources());
			enemyFishs.add(enemyOne);
		}
		for(int i = 0; i < EnemyTwo.sumCount;i++){
			//生产敌人二号鱼
			EnemyTwo enemyTwo = (EnemyTwo) factory.createEnemyTwo(getResources());
			enemyFishs.add(enemyTwo);
		}
		for(int i = 0; i < EnemyTwo.sumCount;i++){
			//生产敌人三号鱼
			EnemyThree enemyThree = (EnemyThree) factory.createEnemyThree(getResources());
			enemyFishs.add(enemyThree);
		}
		thread = new Thread(this);
	}
	// 视图改变的方法
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}
	// 视图创建的方法
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		super.surfaceCreated(arg0);
		sounds.playSound(7,1);
		initBitmap(); // 初始化图片资源
		for(GameObject obj: enemyFishs){
			obj.setScreenWH(screen_width,screen_height);
		}
		myFish.setScreenWH(screen_width,screen_height);
		myFish.setAlive(true);
		if(thread.isAlive()){
			thread.start();
		}
		else{
			thread = new Thread(this);
			thread.start();
		}
	}
	// 视图销毁的方法
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		super.surfaceDestroyed(arg0);
		release();
	}
	// 响应触屏事件的方法
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){
			isTouchFish = false;
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_DOWN){
			float x = event.getX();
			float y = event.getY();
			if(x > 10 && x < 10 + play_bt_w && y > 10 && y < 10 + play_bt_h){
				if(isPlay){
					isPlay = false;
				}
				else{
					isPlay = true;
					synchronized(thread){
						thread.notify();
					}
				}
				return true;
			}
			//判断玩家的鱼是否被按下
			else if(x > myFish.getObject_x() && x < myFish.getObject_x() + myFish.getObject_width()
					&& y > myFish.getObject_y() && y < myFish.getObject_y() + myFish.getObject_height()){
				if(isPlay){
					isTouchFish = true;
				}
				return true;
			}

		}
		//响应手指在屏幕移动的事件
		else if(event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1){
			//判断触摸点是否为玩家的飞机
			if(isTouchFish){
				float x = event.getX();
				float y = event.getY();
				if(x > myFish.getMiddle_x() + 5){
					if(myFish.getMiddle_x() + myFish.getSpeed() <= screen_width){
						myFish.setMiddle_x(myFish.getMiddle_x() + myFish.getSpeed());
					}
				}
				else if(x < myFish.getMiddle_x() - 5){
					if(myFish.getMiddle_x() - myFish.getSpeed() >= 0){
						myFish.setMiddle_x(myFish.getMiddle_x() - myFish.getSpeed());
					}
				}
				if(y > myFish.getMiddle_y() + 5){
					if(myFish.getMiddle_y() + myFish.getSpeed() <= screen_height){
						myFish.turnAround(0);
						myFish.setMiddle_y(myFish.getMiddle_y() + myFish.getSpeed());
					}
				}
				else if(y < myFish.getMiddle_y() - 5){
					if(myFish.getMiddle_y() - myFish.getSpeed() >= 0){
						myFish.turnAround(1);
						myFish.setMiddle_y(myFish.getMiddle_y() - myFish.getSpeed());
					}
				}
				return true;
			}
		}
		return false;
	}
	// 初始化图片资源方法
	@Override
	public void initBitmap() {
		playButton = BitmapFactory.decodeResource(getResources(),R.drawable.play);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
		background2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);

		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
		play_bt_w = playButton.getWidth();
		play_bt_h = playButton.getHeight()/2;
		bg_y = 0;
		bg_y2 = bg_y - screen_height;

	}
	//初始化游戏对象
	public void initObject(){
		for(EnemyFish obj: enemyFishs){
			//初始化敌人一号鱼
			if(obj instanceof EnemyOne){
				if(!obj.isAlive()){
					obj.initial(speedTime,0,0);
					break;
				}
			} else if(obj instanceof EnemyTwo){  //初始化敌人二号鱼

					if(!obj.isAlive()){
						obj.initial(speedTime,0,0);
						break;
					}

			} else if(obj instanceof EnemyThree){  //初始化敌人三号鱼

				if(!obj.isAlive()){
					obj.initial(speedTime,0,0);
					break;
				}

			}
		}
		//提升等级
		if(sumScore >= speedTime*100000 && speedTime < 6){
			speedTime++;
		}
	}
	// 释放图片资源的方法
	@Override
	public void release() {
		for(GameObject obj: enemyFishs){
			obj.release();
		}
		myFish.release();
		if(!playButton.isRecycled()){
			playButton.recycle();
		}
		if(!background.isRecycled()){
			background.recycle();
		}
		if(!background2.isRecycled()){
			background2.recycle();
		}

	}
	// 绘图方法
	@Override
	public void drawSelf() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK); // 绘制背景色
			canvas.save();
			// 计算背景图片与屏幕的比例
			canvas.scale(scalex, scaley, 0, 0);
			canvas.drawBitmap(background, 0, bg_y, paint);   // 绘制背景图
			canvas.restore();
			//绘制按钮
			canvas.save();
			canvas.clipRect(10, 10, 10 + play_bt_w,10 + play_bt_h);
			if(isPlay){
				canvas.drawBitmap(playButton, 10, 10, paint);
			}
			else{
				canvas.drawBitmap(playButton, 10, 10 - play_bt_h, paint);
			}
			canvas.restore();

			//绘制敌人的鱼
			for(EnemyFish obj: enemyFishs){
				if(obj.isAlive()){
					obj.drawSelf(canvas);
					//检验敌人的鱼是否与我的鱼相碰
					if(obj.isCanCollide() && myFish.isAlive()){
						if(obj.isCollide(myFish)){
							if(myFish.isBiggerMe(obj.getObject_width())){
								myFish.setAlive(false);
								sounds.playSound(4,0);
							}
							else{
								sounds.playSound(1,0);
								sumScore += obj.getScore();
								if(sumScore >= levelsScore[levelsIndex]){
									myFish.amplify();
									sounds.playSound(5,0);
									levelsIndex++;
									speedTime++;
									if(speedTime == 5){
										//胜利结束游戏
										myFish.setAlive(false);
									}
								}
								obj.setAlive(false);
							}
						}
					}
				}
			}
			if(!myFish.isAlive()){
				threadFlag = false;
			}

			myFish.drawSelf(canvas);	//绘制玩家的飞机
			//绘制积分文字
			paint.setTextSize(30);
			paint.setColor(Color.rgb(235, 161, 1));
			canvas.drawText("积分:"+String.valueOf(sumScore), 30 + play_bt_w, 40, paint);		//绘制文字
			canvas.drawText("等级 X "+String.valueOf(speedTime), screen_width - 150, 40, paint); //绘制文字
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	// 增加游戏分数的方法
	public void addGameScore(int score){
		sumScore += score;			// 游戏总得分
	}

	// 线程运行的方法
	@Override
	public void run() {
		while (threadFlag) {
			long startTime = System.currentTimeMillis();
			//每次都尝试初始化敌人的鱼的数据，保证敌人的鱼的数量
			initObject();
			drawSelf();
			long endTime = System.currentTimeMillis();
			if(!isPlay){
				synchronized (thread) {
					try {
						thread.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				if (endTime - startTime < 100)
					Thread.sleep(100 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Message message = new Message();
		message.what = 	ConstantUtil.TO_END_VIEW;
		message.arg1 = Integer.valueOf(sumScore);
		mainActivity.getHandler().sendMessage(message);
	}
}
