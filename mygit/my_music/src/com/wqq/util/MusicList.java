package com.wqq.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.wqq.music.Music;

/**
 * 获取本地的音乐文件
 * @author 王庆庆
 *
 */
public class MusicList {
	
	public static List<Music> getMusicData(Context context){
		List<Music> musicList = new ArrayList<Music>();
		//ContentResolver用来实现应用程序之间数据共享的
		ContentResolver cr = context.getContentResolver();
		if(cr != null){
			//获取所有曲目
			//在Android查询数据是通过Cursor类来实现的
			Cursor cursor = cr.query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, 
					null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
			if(null == cursor){
				return null;
			}
			if(cursor.moveToFirst()){
				do{
					//MediaStore相对于一个ContentProvider
					Music m = new Music();
					String title = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.TITLE));
					String singer = cursor
							.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
					if("<unknown>".equals(singer)){
						singer = "未知艺术家";
					}
					String album = cursor
							.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
					long size = cursor.getLong(cursor
							.getColumnIndex(MediaStore.Audio.Media.SIZE));
					long time = cursor.getLong(cursor
							.getColumnIndex(MediaStore.Audio.Media.DURATION));
					String url = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.DATA));
					String name = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
					String sbr = name.substring(name.length() - 3,
							name.length());
					if(sbr.equals("mp3")){
						m.setTitle(title);
						m.setSinger(singer);
						m.setAblum(album);
						m.setSize(size);
						m.setTime(time);
						m.setUrl(url);
						m.setName(name);
						musicList.add(m);
					}
				}while(cursor.moveToNext());
			}
		}
		return musicList;
	} 
}
