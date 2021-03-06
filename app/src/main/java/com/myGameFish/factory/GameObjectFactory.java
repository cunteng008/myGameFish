package com.myGameFish.factory;

import android.content.res.Resources;

import com.myGameFish.object.EnemyThree;
import com.myGameFish.object.EnemyTwo;
import com.myGameFish.object.GameObject;
import com.myGameFish.object.MissileGoods;
import com.myGameFish.object.MyFish;
import com.myGameFish.object.EnemyOne;
/*游戏对象的工厂类*/
public class GameObjectFactory {
	//创建敌人的一号鱼
	public GameObject createEnemyOne(Resources resources){
		return new EnemyOne(resources);
	}
	//创建敌人的二号鱼
	public GameObject createEnemyTwo(Resources resources){
		return new EnemyTwo(resources);
	}
	//创建敌人的三号鱼
	public GameObject createEnemyThree(Resources resources){
		return new EnemyThree(resources);
	}
	//创建玩家鱼的方法
	public GameObject createMyFish(Resources resources){
		return new MyFish(resources);
	}
	//创建果实
	public GameObject createMissileGoods(Resources resources){
		return new MissileGoods(resources);
	}
}
