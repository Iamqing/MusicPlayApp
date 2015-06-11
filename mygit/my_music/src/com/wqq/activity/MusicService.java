package com.wqq.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.view.animation.AnimationUtils;

import com.wqq.music.LrcContent;
import com.wqq.music.Music;
import com.wqq.util.LrcIndex;
import com.wqq.util.LrcProcess;
import com.wqq.util.LrcView;
import com.wqq.util.MusicList;
/**
 * 控制音乐播放的后台服务类
 * @author 王庆庆
 *
 */
public class MusicService extends Service implements Runnable{
    
	private MediaPlayer player;
	private List<Music> listMusic;
	public static int currentId = 1; //当前播放位置
	public static Boolean isRun = true;
	public LrcProcess mLrcProcess;
	public LrcView lrcView;
	public static int playingId = 0; 
	public static Boolean playing = false;
	
	private List<LrcContent> lrcList = new ArrayList<LrcContent>();
	MusicActivity muscicActivity = new MusicActivity();
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		listMusic = MusicList.getMusicData(getApplicationContext());
		//在程序中注册广播
		SeekBarBroadcastReceiver receiver = new SeekBarBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		this.registerReceiver(receiver, filter);
		new Thread(this).start();
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub

		String play = intent.getStringExtra("play");
		currentId = intent.getIntExtra("id", 1);
		if (play.equals("play")) {
			if (null != player) {
				player.release();
				player = null;
			}
			playMusic(currentId);

		} else if (play.equals("pause")) {
			if (null != player) {
				player.pause();
			}
		} else if (play.equals("playing")) {
			if (player != null) {
				player.start();
			} else {
				playMusic(currentId);
			}
		} else if (play.equals("rePlaying")) {

		} else if (play.equals("first")) {
			int id = intent.getIntExtra("id", 0);
			playMusic(id);
		} else if (play.equals("rewind")) {
			int id = intent.getIntExtra("id", 0);
			playMusic(id);
		} else if (play.equals("forward")) {
			int id = intent.getIntExtra("id", 0);
			playMusic(id);
		} else if (play.equals("last")) {
			int id = intent.getIntExtra("id", 0);
			playMusic(id);
		}

	}
	/*
	 * 歌词滚动线程
	 */
	Handler mHandler = new Handler();
	Runnable mRunnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			MusicActivity.lrcView.SetIndex(new LrcIndex().LrcIndex());
			MusicActivity.lrcView.invalidate();
			mHandler.postDelayed(mRunnable, 100);
		}
		
	};
	/*
	 * 后台处理音乐播放的方法
	 */
	private void playMusic(int id){
		
		/*初始化歌词配置*/
		mLrcProcess = new LrcProcess();
		//读取歌词文件
		mLrcProcess.readLRC(listMusic.get(currentId).getUrl());
		//传回处理后的歌词文件
		lrcList = mLrcProcess.getLrcContent();
		MusicActivity.lrcView.setmSentenceEntities(lrcList);
		//切换带动画显示歌词
		MusicActivity.lrcView.setAnimation(AnimationUtils.loadAnimation(
				MusicService.this,R.anim.alpha_z));
		/*启动线程*/
		mHandler.post(mRunnable);
		/*初始化歌词配置 */
		if(null != player){
			player.release();
			player = null;
		}
		if(id <= listMusic.size() - 1 ){
			currentId = listMusic.size() - 1;
		}else if(id <= 0){
			currentId = 0;
		}
		Music m = listMusic.get(currentId);
		String url = m.getUrl();
		Uri mUri = Uri.parse(url);
		player = new MediaPlayer();
		player.reset();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			//抛出异常，是有可能本地资源文件不存在
			player.setDataSource(getApplicationContext(),mUri);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		player.start();
		//注册一个回调对象，这个回调对象用于一个结束的媒体资源在需要被重放时被调用
		player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				//播放下一首
				if(MusicActivity.isLoop == true){
					player.reset();
					Intent intent = new Intent();
					sendBroadcast(intent);
					currentId = currentId + 1;
					playMusic(currentId);
				}else{  //单曲播放
					player.reset();
					Intent intent = new Intent();
					sendBroadcast(intent);
					playMusic(currentId);
				}
			}
		});
		//注册一个回调对象，这个回调对象将在异步操作发生错误时被调用
		player.setOnErrorListener(new OnErrorListener() {
			
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// TODO Auto-generated method stub
				if(null != player){
					player.release();
					player = null;
				}
				Music m = listMusic.get(currentId);
				String url = m.getUrl();
				Uri mUri = Uri.parse(url);
				player = new MediaPlayer();
				player.reset();
				player.setAudioStreamType(AudioManager.STREAM_MUSIC);
				try {
					player.setDataSource(getApplicationContext(),mUri);
					player.prepare();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				player.start();
				return false;
			}
		});
	}
	 /*
     * 滑动SeekBar时，在广播解释器中接收它的位置滚动情况，然后再通知给Activity
     */
	private class SeekBarBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int seekBarPosition = intent.getIntExtra("seekBarPosition", 0);
			player.seekTo(seekBarPosition * player.getDuration() / 100);
			player.start();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRun){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(null != player){
				int position = player.getCurrentPosition();
				int total = player.getDuration();
				Intent intent = new Intent();
				intent.putExtra("position", position);
				intent.putExtra("total", total);
				sendBroadcast(intent);
			}
			if(null != player){
				if(player.isPlaying()){
					playing = true;
				}else{
					playing = false;
				}
			}
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
