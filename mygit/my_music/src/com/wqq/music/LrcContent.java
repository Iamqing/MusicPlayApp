package com.wqq.music;
/**
 * 存放歌词和歌词时间的类
 * @author 王庆庆
 *
 */
public class LrcContent {
	private String Lrc;
	private int LrcTime;
	
	public String getLrc() {
		return Lrc;
	}
	public void setLrc(String lrc) {
		Lrc = lrc;
	}
	public int getLrcTime() {
		return LrcTime;
	}
	public void setLrcTime(int lrcTime) {
		LrcTime = lrcTime;
	}
}
