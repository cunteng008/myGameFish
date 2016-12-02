package com.myGameFish.souds;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.myGameFish.activity.MainActivity;
import com.myGameFish.activity.R;

import static android.util.JsonToken.NULL;

public class GameSoundPool {
	private MainActivity mainActivity;
	private SoundPool soundPool;
	private HashMap<Integer,Integer> map;
	public GameSoundPool(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		map = new HashMap<Integer,Integer>();
		//AudioManager.STREAM_MUSIC系统默认声音类型
		soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,0);
	}
	public void initGameSound(){
		map.put(1, soundPool.load(mainActivity, R.raw.audios_eatfish1, 1));
		map.put(2, soundPool.load(mainActivity, R.raw.audios_eatfish2, 1));
		map.put(3, soundPool.load(mainActivity, R.raw.audios_eatgold, 1));
		map.put(4, soundPool.load(mainActivity, R.raw.audios_myfish_death, 1));
		map.put(5, soundPool.load(mainActivity, R.raw.audios_growth, 1));
		map.put(6, soundPool.load(mainActivity, R.raw.audios_bg, 1));
		map.put(7, soundPool.load(mainActivity, R.raw.audios_fishstart, 1));
		map.put(8, soundPool.load(mainActivity, R.raw.audios_btn, 1));
	}
	//播放声音，loop表示循环的次数，loop等于-1时表示无限次
	public void playSound(int sound,int loop){
		AudioManager am = (AudioManager)mainActivity.getSystemService(Context.AUDIO_SERVICE);
		float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float stramMaxVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volume = stramVolumeCurrent/stramMaxVolumeCurrent;
		soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);	
	}
	public void stopSound(int sound){
		//PlaySound(NULL,NULL,NULL);
	}
	public void realseSoundPool(){
		soundPool.release();
	}
	public void createSoundPool(){
		soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,0);
	}
}
