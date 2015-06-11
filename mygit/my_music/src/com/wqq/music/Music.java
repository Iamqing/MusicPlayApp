package com.wqq.music;
/**
 * 存放音乐相关属性的类
 * @author 王庆庆
 *
 */
public class Music {
	private String title;
	private String singer;
	private String ablum;
	private String url;
	private long size;
	private long time;
	private String name;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getAblum() {
		return ablum;
	}
	public void setAblum(String ablum) {
		this.ablum = ablum;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
