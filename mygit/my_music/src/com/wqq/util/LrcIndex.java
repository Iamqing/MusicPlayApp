package com.wqq.util;

import java.util.ArrayList;
import java.util.List;

import com.wqq.music.LrcContent;

import android.media.MediaPlayer;
/**
 * 歌词同步处理类
 * @author 王庆庆
 *
 */
public class LrcIndex {
	private MediaPlayer player;
	private List<LrcContent> lrcList = new ArrayList<LrcContent>();
	private int index = 0;   //初始化歌词检索值
	private int currentTime = 0;  //初始化歌曲播放时间的变量
	private int countTime = 0;  //初始化歌曲总时间的变量
	
	/*处理歌词位置跟播放进度条的时间同步的方法*/
    public int LrcIndex(){
    	if(player.isPlaying()){
    		//获得歌词播放在何处的时间
    		currentTime = player.getCurrentPosition();
    		//获得歌曲总时间的长度
    		countTime = player.getDuration();
    	}
    	if(currentTime < countTime){
    		for (int i = 0; i < lrcList.size(); i++) {
    			if (i < lrcList.size() - 1) {
    				if (currentTime < lrcList.get(i).getLrcTime() && i == 0) {
    					index = i;
    				}
    				if (currentTime > lrcList.get(i).getLrcTime()
    						&& currentTime < lrcList.get(i + 1).getLrcTime()) {
    					index = i;
    				}
    			}
    			if (i == lrcList.size() - 1 
    					&& currentTime > lrcList.get(i).getLrcTime()) {
    				index = i;
    			}
    		}
    	}
    	return index;
    }
}
