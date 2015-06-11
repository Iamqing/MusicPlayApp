package com.wqq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wqq.adapter.AlbumAdapter;
import com.wqq.util.MusicList;

public class AlbumsActivity extends Activity{
	
	private ListView albumListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.albums);
		
		albumListView = (ListView) this.findViewById(R.id.albumListView);
		AlbumAdapter adapter = new AlbumAdapter(this, MusicList.getMusicData(this));
		albumListView.setAdapter(adapter);
		albumListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AlbumsActivity.this,
						MusicActivity.class);
				intent.putExtra("id", arg2);
				startActivity(intent);
			}
		});
	}
}
