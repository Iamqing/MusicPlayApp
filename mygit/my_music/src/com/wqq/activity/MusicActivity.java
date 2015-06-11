package com.wqq.activity;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.wqq.music.Music;
import com.wqq.util.ConvertTime;
import com.wqq.util.LrcView;
import com.wqq.util.MusicList;
/**
 * ���ֲ���ʱ��ʾ�Ľ���
 * @author ������
 */
public class MusicActivity extends Activity implements SensorEventListener{
	
	private TextView textName;
	private TextView textSinger;
	private TextView textStartTime;
	private TextView textEndTime;
	private SeekBar seekBar1;
	private ImageButton imageButLast;
	private ImageButton imageButRewind;
	public static ImageButton imageButPlay;
	private ImageButton imageButForward;
	private ImageButton imageButNext;
	private ImageButton imageButLoop;
	private SeekBar seekbarVolume;
	private ImageButton imageButRandom;
	public static LrcView lrcView;
	private static int id = 1;
	private static int currentId = 2;
	private SensorManager sensorManger;
	private AudioManager audioManger;  //����������
	private int maxVolumn;   //�������
	private int currentVolumn;  //��ǰ����
	private SeekBar seekBarVolumn;
	private List<Music> listMusic;
	public static Boolean isLoop=true;
	private Boolean isPlaying = false;
	private Boolean rePlaying = false;
	private MyProgressBroadCastReceiver receiver;
	private MyCompletionListner completionListner;
	private boolean mRegisteredSensor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.music);
		
		textName = (TextView) this.findViewById(R.id.music_name);
		textSinger = (TextView) this.findViewById(R.id.music_singer);
		textStartTime = (TextView) this.findViewById(R.id.music_start_time);
		textEndTime = (TextView) this.findViewById(R.id.music_end_time);
		seekBar1 = (SeekBar) this.findViewById(R.id.music_seeBar);
		imageButLast = (ImageButton) this.findViewById(R.id.music_lasted);
		imageButRewind = (ImageButton) this.findViewById(R.id.music_rewind);
		imageButPlay = (ImageButton) this.findViewById(R.id.music_play);
		imageButForward = (ImageButton) this.findViewById(R.id.music_forward);
		imageButNext =  (ImageButton) this.findViewById(R.id.music_next);
		imageButLoop = (ImageButton) this.findViewById(R.id.music_loop);
		seekbarVolume = (SeekBar) this.findViewById(R.id.music_volume);
		imageButRandom = (ImageButton) this.findViewById(R.id.music_random);
		lrcView = (LrcView) this.findViewById(R.id.LyricShow);
		
		imageButLast.setOnClickListener(new MyListener());
		imageButRewind.setOnClickListener(new MyListener());
		imageButPlay.setOnClickListener(new MyListener());
		imageButForward.setOnClickListener(new MyListener());
		imageButNext.setOnClickListener(new MyListener());
		imageButLoop.setOnClickListener(new MyListener());
		imageButRandom.setOnClickListener(new MyListener());
		sensorManger = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		listMusic = MusicList.getMusicData(this);
		audioManger = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		maxVolumn = audioManger.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// ����������
		currentVolumn = audioManger.getStreamVolume(AudioManager.STREAM_MUSIC);// ��õ�ǰ����
		seekBarVolumn.setMax(maxVolumn);
		seekBarVolumn.setProgress(currentVolumn);
		seekBarVolumn.setOnSeekBarChangeListener(new OnSeekBarChangeListener()  {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				audioManger.setStreamVolume(AudioManager.STREAM_MUSIC, 
						progress, AudioManager.FLAG_ALLOW_RINGER_MODES);
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		receiver = new MyProgressBroadCastReceiver();
		IntentFilter filter = new IntentFilter();
		this.registerReceiver(receiver, filter);
		
		id = getIntent().getIntExtra("id", 1);
		if(id == currentId){
			Music m = listMusic.get(id);
			textName.setText(m.getTitle());
			textSinger.setText(m.getSinger());
			textEndTime.setText(ConvertTime.toTime((int)m.getTime()));
			Intent intent = new Intent(MusicActivity.this,MusicService.class);
			intent.putExtra("play", "rePlaying");
			intent.putExtra("id", id);
			startService(intent);
			if(rePlaying == true){
				imageButPlay.setImageResource(R.drawable.pause1);
				isPlaying = true;
			}else{
				imageButPlay.setImageResource(R.drawable.play1);
			}
		}else{
			Music m = listMusic.get(id);
			textName.setText(m.getTitle());
			textSinger.setText(m.getSinger());
			textEndTime.setText(ConvertTime.toTime((int) m.getTime()));
			imageButPlay.setImageResource(R.drawable.pause1);
			Intent intent = new Intent(MusicActivity.this, MusicService.class);
			intent.putExtra("play", "play");
			intent.putExtra("id", id);
			startService(intent);
			isPlaying = true;
			rePlaying=true;
			currentId = id;
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		List<Sensor> sensors = sensorManger.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if(sensors.size()>0){
			Sensor sensor = sensors.get(0);
			mRegisteredSensor = sensorManger.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_FASTEST);
			Log.e("----------------", sensor.getName());
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(mRegisteredSensor){
			sensorManger.unregisterListener(this);
			mRegisteredSensor=false;
		}
		super.onPause();
	}
	
	 @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		this.unregisterReceiver(receiver);
		this.unregisterReceiver(completionListner);
		super.onDestroy();
	}
	 
	/*�����ڽ��еĶ������㲥��"������"*/
	public class MyProgressBroadCastReceiver extends BroadcastReceiver{

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				int position=intent.getIntExtra("position", 0);
				int total=intent.getIntExtra("total", 0);
				int progress = position * 100 / total;
				textStartTime.setText(ConvertTime.toTime(position));
				seekBar1.setProgress(progress);
				seekBar1.invalidate();
			}
	    }
	
	/*�����������Ķ������㲥��"������"*/
	private class MyCompletionListner extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Music m = listMusic.get(MusicService.currentId);
			textName.setText(m.getTitle());
			textSinger.setText(m.getSinger());
			textEndTime.setText(ConvertTime.toTime((int) m.getTime()));
			imageButPlay.setImageResource(R.drawable.pause1);
		}
			   
   }
	
	/*
	 * �������ڼ���������š��������һ�װ�ť��Щ��������Ҫ��MusicService����"��Ӧ"��Щ�������������"��Ӧ"��Intent�����
	 * һ���ڲ��࣬���Է���MusicActivity�����г�Ա�����ͷ���
	 */
	public class MyListener implements OnClickListener{
	    
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == imageButLast){
				//��һ��
				id = 0;
				Music m = listMusic.get(0);
				textName.setText(m.getTitle());
				textSinger.setText(m.getSinger());
				textEndTime.setText(ConvertTime.toTime((int)m.getTime()));
				imageButPlay.setImageResource(R.drawable.pause1);
				//ͨ��Intent��"����"��MusicActivity֪ͨ��MusicService
				Intent intent = new Intent(MusicActivity.this,MusicService.class);
				intent.putExtra("play", "first");
				intent.putExtra("id", id);
				startService(intent);
				isPlaying = true;
			}else if (v == imageButRewind) {
				// ǰһ��
				int id=MusicService.currentId-1;
				if(id>=listMusic.size()-1){
					id=listMusic.size()-1;
				}else if(id<=0){
					id=0;
				}
				Music m = listMusic.get(id);
				textName.setText(m.getTitle());
				textSinger.setText(m.getSinger());
				textEndTime.setText(ConvertTime.toTime((int) m.getTime()));
				imageButPlay.setImageResource(R.drawable.pause1);
				Intent intent = new Intent(MusicActivity.this,
						MusicService.class);
				intent.putExtra("play", "rewind");
				intent.putExtra("id", id);
				startService(intent);
				isPlaying = true;
			} else if (v == imageButPlay) {
				// ���ڲ���ʱ�����ͣ
				if (isPlaying == true) {
					Intent intent = new Intent(MusicActivity.this,
							MusicService.class);
					intent.putExtra("play", "pause");
					startService(intent);
					isPlaying = false;
					//��ͣ�󲥷�ֹͣ��ͼƬ��ť��ɲ���
					imageButPlay.setImageResource(R.drawable.play1);
					rePlaying=false;
				} else {
					//��ͣʱ�������
					Intent intent = new Intent(MusicActivity.this,MusicService.class);
					intent.putExtra("play", "playing");
					intent.putExtra("id", id);
					startService(intent);
					isPlaying = true;
					imageButPlay.setImageResource(R.drawable.pause1);
					rePlaying=true;
				}
			} else if (v == imageButForward) {
				// ��һ��
				int id=MusicService.currentId+1;
				if(id>=listMusic.size()-1){
					id=listMusic.size()-1;
				}else if(id<=0){
					id=0;
				}
				Music m = listMusic.get(id);
				textName.setText(m.getTitle());
				textSinger.setText(m.getSinger());
				textEndTime.setText(ConvertTime.toTime((int) m.getTime()));
				imageButPlay.setImageResource(R.drawable.pause1);
				Intent intent = new Intent(MusicActivity.this,
						MusicService.class);
				intent.putExtra("play", "forward");
				intent.putExtra("id", id);
				startService(intent);
				isPlaying = true;
			} else if (v == imageButNext) {
				// ���һ��
				int id=listMusic.size()-1;
				Music m = listMusic.get(id);
				textName.setText(m.getTitle());
				textSinger.setText(m.getSinger());
				textEndTime.setText(ConvertTime.toTime((int) m.getTime()));
				imageButPlay.setImageResource(R.drawable.pause1);
				Intent intent = new Intent(MusicActivity.this,
						MusicService.class);
				intent.putExtra("play", "last");
				intent.putExtra("id", id);
				startService(intent);
				isPlaying = true;
			}else if(v == imageButLoop){ 
				if(isLoop == true){
					//˳�򲥷�
					imageButLoop.setBackgroundResource(R.drawable.play_loop_spec);
					isLoop = false;
				}else{  //��������
					imageButLoop.setBackgroundResource(R.drawable.play_loop_sel);
					isLoop = true;
				}
			}else if(v == imageButRandom){
				imageButRandom.setBackgroundResource(R.drawable.play_random_sel);
			}
		}

	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
