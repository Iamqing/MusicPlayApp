package com.wqq.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.wqq.music.LrcContent;

/**
 * 处理歌词文件格式的类
 * @author 王庆庆
 *
 */
public class LrcProcess {
	
	private List<LrcContent> LrcList;
	private LrcContent mLrcContent;
	
	public LrcProcess(){
		mLrcContent = new LrcContent();
		LrcList = new ArrayList<LrcContent>();
	}
	/**
	 * 获取歌词文件的内容
	 */
	public String readLRC(String songpath){
		StringBuilder stringBuilder = new StringBuilder();
		File f = new File(songpath.replace(".mp3", ".lrc"));
			try {
				FileInputStream fis = new FileInputStream(f);
				InputStreamReader isr = new InputStreamReader(fis,"GB2312");
				BufferedReader br = new BufferedReader(isr);
				String s = "";			
				while((s = br.readLine()) != null){
					//替换字符
					s = s.replace("[", "");
					s = s.replace("]", "@");
					//分离@字符
					String splitLrcData[] = s.split("@");
					if(splitLrcData.length > 1){
						mLrcContent.setLrc(splitLrcData[1]);
						//处理歌词取得歌曲时间
						int LrcTime = TimeStr(splitLrcData[0]);
						mLrcContent.setLrcTime(LrcTime);
							//添加进列表数组
							LrcList.add(mLrcContent);
							//创建对象
							mLrcContent = new LrcContent();
				    }
				}
				br.close();
				isr.close();
				fis.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				stringBuilder.append("没有歌词文件！快去下载！");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				stringBuilder.append("没有读取到歌词");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return stringBuilder.toString();
	}
	/**
	 * 解析歌曲时间处理类
	 */
	public int TimeStr(String timeStr){
		timeStr = timeStr.replace(":", ".");
		timeStr = timeStr.replace(".", "@");
		String timeData[] = timeStr.split("@");
		//分离出分、秒，并转换成整型
		int minute = Integer.parseInt(timeData[0]);
		int second = Integer.parseInt(timeData[1]);
		int millisecond = Integer.parseInt(timeData[2]);
		//计算上一行和下一行的时间并转换成毫秒数
		int currentTime = (minute * 60 + second) * 1000 + millisecond * 10;
		return currentTime;
	}
	
	public List<LrcContent> getLrcContent(){
		return LrcList;
	}
}
