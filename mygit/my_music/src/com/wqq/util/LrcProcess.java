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
 * �������ļ���ʽ����
 * @author ������
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
	 * ��ȡ����ļ�������
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
					//�滻�ַ�
					s = s.replace("[", "");
					s = s.replace("]", "@");
					//����@�ַ�
					String splitLrcData[] = s.split("@");
					if(splitLrcData.length > 1){
						mLrcContent.setLrc(splitLrcData[1]);
						//������ȡ�ø���ʱ��
						int LrcTime = TimeStr(splitLrcData[0]);
						mLrcContent.setLrcTime(LrcTime);
							//��ӽ��б�����
							LrcList.add(mLrcContent);
							//��������
							mLrcContent = new LrcContent();
				    }
				}
				br.close();
				isr.close();
				fis.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				stringBuilder.append("û�и���ļ�����ȥ���أ�");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				stringBuilder.append("û�ж�ȡ�����");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return stringBuilder.toString();
	}
	/**
	 * ��������ʱ�䴦����
	 */
	public int TimeStr(String timeStr){
		timeStr = timeStr.replace(":", ".");
		timeStr = timeStr.replace(".", "@");
		String timeData[] = timeStr.split("@");
		//������֡��룬��ת��������
		int minute = Integer.parseInt(timeData[0]);
		int second = Integer.parseInt(timeData[1]);
		int millisecond = Integer.parseInt(timeData[2]);
		//������һ�к���һ�е�ʱ�䲢ת���ɺ�����
		int currentTime = (minute * 60 + second) * 1000 + millisecond * 10;
		return currentTime;
	}
	
	public List<LrcContent> getLrcContent(){
		return LrcList;
	}
}
