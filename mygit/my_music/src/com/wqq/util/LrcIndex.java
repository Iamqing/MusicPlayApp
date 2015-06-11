package com.wqq.util;

import java.util.ArrayList;
import java.util.List;

import com.wqq.music.LrcContent;

import android.media.MediaPlayer;
/**
 * ���ͬ��������
 * @author ������
 *
 */
public class LrcIndex {
	private MediaPlayer player;
	private List<LrcContent> lrcList = new ArrayList<LrcContent>();
	private int index = 0;   //��ʼ����ʼ���ֵ
	private int currentTime = 0;  //��ʼ����������ʱ��ı���
	private int countTime = 0;  //��ʼ��������ʱ��ı���
	
	/*������λ�ø����Ž�������ʱ��ͬ���ķ���*/
    public int LrcIndex(){
    	if(player.isPlaying()){
    		//��ø�ʲ����ںδ���ʱ��
    		currentTime = player.getCurrentPosition();
    		//��ø�����ʱ��ĳ���
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
