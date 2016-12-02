package com.myGameFish.object;
import com.myGameFish.activity.R;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
/*导弹物品的类*/
public class MissileGoods extends GameGoods{

	public MissileGoods(Resources resources) {
		super(resources);
		initBitmap();
		// TODO Auto-generated constructor stub
	}
	// 初始化图片资源的
	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub
		bmp = BitmapFactory.decodeResource(resources, R.drawable.fruit);
		object_width = bmp.getWidth();
		object_height = bmp.getHeight();
	}
}
