package com.wqq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wqq.adapter.ArtistsAdapter;
import com.wqq.util.MusicList;

public class ArtistsActivity extends Activity{
	private ListView artistListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.artist);
		
		artistListView=(ListView) this.findViewById(R.id.artistListView);
		ArtistsAdapter adapter=new ArtistsAdapter(this, MusicList.getMusicData(this));
		artistListView.setAdapter(adapter);
		artistListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ArtistsActivity.this,
						MusicActivity.class);
				intent.putExtra("id", arg2);
				startActivity(intent);
			}
		});
	}
}
