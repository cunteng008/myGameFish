package com.myGameFish.object;

import com.myGameFish.factory.GameObjectFactory;
import com.myGameFish.interfaces.IMyFish;
import com.myGameFish.activity.R;
import com.myGameFish.view.MainView;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;

/*玩家鱼的类*/
public class MyFish extends GameObject implements IMyFish {
	private float middle_x;			 // 飞机的中心坐标
	private float middle_y;
	private Bitmap myfish;			 // 飞机飞行时的图片
	private MainView mainView;
	//private GameObjectFactory factory;
	private  double scale = 1;    //缩放倍数
	private boolean isWin = false;
	private List<Bitmap> myFishs = new ArrayList<Bitmap>();

	public MyFish(Resources resources) {
		super(resources);
		initBitmap();
		this.speed = 8;
		//factory = new GameObjectFactory();
	}
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
	// 设置屏幕宽度和高度
	@Override
	public void setScreenWH(float screen_width, float screen_height) {
		super.setScreenWH(screen_width, screen_height);
		object_x = screen_width/2 - object_width/2;
		object_y = screen_height/2 - object_height/2;
		middle_x = object_x + object_width/2;
		middle_y = object_y + object_height/2;
	}
	// 初始化图片资源的
	@Override
	public void initBitmap() {
		myFishs.add(BitmapFactory.decodeResource(resources, R.drawable.myfish_01));
		myFishs.add(BitmapFactory.decodeResource(resources, R.drawable.myfish_02));
		myfish = myFishs.get(0) ;
		object_width = myfish.getWidth() ; // 获得每一帧位图的宽
		object_height = myfish.getHeight()/2; 	// 获得每一帧位图的高
	}
	// 对象的绘图方法
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			int y = (int) (currentFrame * object_height);
			canvas.save();
			canvas.clipRect(object_x,object_y+object_height,object_x +object_width,object_y );
			canvas.drawBitmap(myfish, object_x , object_y-y,paint);
			canvas.restore();
			currentFrame++;
			if (currentFrame >= 2) {
				currentFrame = 0;
			}
		}
		else{
			int x = (int) (currentFrame * object_width); // 获得当前帧相对于位图的Y坐标
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y
					+ object_height);
			canvas.restore();
			currentFrame++;
			if (currentFrame >= 2) {
				currentFrame = 1;
			}
		}
	}
	// 释放资源的方法
	@Override
	public void release() {
		if(!myfish.isRecycled()){
			myfish.recycle();
		}
	}

	@Override
	public float getMiddle_x() {
		return middle_x;
	}
	@Override
	public void setMiddle_x(float middle_x) {
		this.middle_x = middle_x;
		this.object_x = middle_x - object_width/2;
	}
	@Override
	public float getMiddle_y() {
		return middle_y;
	}
	@Override
	public void setMiddle_y(float middle_y) {
		this.middle_y = middle_y;
		this.object_y = middle_y - object_height/2;
	}

	public boolean isBiggerMe(float w){
		 return w > object_width;
	}

	public void amplify(){
        /* 放大变量 */
		scale += 0.5;
        /* 产生resize后的Bitmap对象 */
		Matrix matrix = new Matrix();
		if(!isWin){
			matrix.postScale((float) scale, (float) scale);
			myFishs.set(0,Bitmap.createBitmap(myFishs.get(0), 0, 0, myFishs.get(0).getWidth(), myFishs.get(0).getHeight(),
					matrix, true));
			myFishs.set(1,Bitmap.createBitmap(myFishs.get(1), 0, 0, myFishs.get(1).getWidth(), myFishs.get(1).getHeight(),
					matrix, true));
			myfish = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth(), myfish.getHeight(),
					matrix, true);
			if(myfish.getWidth() >= screen_width ){
				isAlive = false;
			}
			object_width = myfish.getWidth() ; // 获得每一帧位图的宽
			object_height = myfish.getHeight()/2; 	// 获得每一帧位图的高
		}
	}
	public void turnAround(int direction){
		if(direction == 1){
			myfish = myFishs.get(1);

		}else if(direction == 0){
			myfish = myFishs.get(0);
		}
	}
}
