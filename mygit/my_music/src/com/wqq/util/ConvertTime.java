package com.wqq.util;
/**
 * ǿ��ʱ���ʽת������
 * @author ������
 *
 */
public class ConvertTime {
	public static String toTime(int time) {
        
		time /= 1000;
		int minute = time / 60;
		int hour = minute / 60;
		int second = time % 60;
		minute %= 60;
		return String.format("%02d:%02d", minute, second);
	}

}
