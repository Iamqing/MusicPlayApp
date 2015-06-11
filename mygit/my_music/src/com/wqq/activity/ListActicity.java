package com.wqq.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wqq.adapter.MusicAdapter;
import com.wqq.music.Music;
import com.wqq.util.MusicList;

public class ListActicity extends Activity{
	
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmusic);
		
		listView= (ListView) this.findViewById(R.id.listAllMusic);
		List<Music> listMusic=MusicList.getMusicData(getApplicationContext());
		MusicAdapter adapter=new MusicAdapter(this, listMusic);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ListActicity.this,MusicActivity.class);
				intent.putExtra("id", arg2);
				startActivity(intent);
			}
		});
	}
}
