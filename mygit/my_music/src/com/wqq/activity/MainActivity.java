package com.wqq.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;


@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity{
	/**
	 * ����Ӧ�ã���һ��Activity������
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		
		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec Spec;
		Intent intent;
		
		intent = new Intent().setClass(this, ListActicity.class);
		Spec = tabHost.newTabSpec("����").setIndicator("����",
				res.getDrawable(R.drawable.item)).setContent(intent);
		tabHost.addTab(Spec);
		
		intent = new Intent().setClass(this, ArtistsActivity.class);
		Spec = tabHost.newTabSpec("������").setIndicator("������",
				res.getDrawable(R.drawable.artist)).setContent(intent);
		tabHost.addTab(Spec);
		
		intent = new Intent().setClass(this, AlbumsActivity.class);
		Spec = tabHost.newTabSpec("ר��").setIndicator("ר��",
				res.getDrawable(R.drawable.album)).setContent(intent);
		tabHost.addTab(Spec);
		
		intent = new Intent().setClass(this, SongsActivity.class);
		Spec = tabHost.newTabSpec("�������").setIndicator("�������",
				res.getDrawable(R.drawable.album)).setContent(intent);
		tabHost.addTab(Spec);
		
		tabHost.setCurrentTab(0);
	}

}

