package com.wqq.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wqq.activity.R;
import com.wqq.music.Music;

/**
 * 给艺术家对象的View提供数据的类
 * @author 王庆庆
 *
 */
public class ArtistsAdapter extends BaseAdapter{

	private List<Music> ListMusic;
	private Context context;
	
	public ArtistsAdapter(Context context,List<Music> listMusic) {
		this.context = context;
		this.ListMusic = listMusic;
	}

	public void setListItem(List<Music> listMusic) {
		this.ListMusic = listMusic;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ListMusic.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return ListMusic.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.music_item, null);
		}
		Music m = ListMusic.get(position);
		//音乐名
		TextView musicName = (TextView)convertView.findViewById(R.id.music_item_name);
		musicName.setText(m.getSinger());
		//歌手
		TextView singer = (TextView)convertView.findViewById(R.id.music_item_singer);
		singer.setText(m.getAblum()); 
		return convertView;
	}

}
