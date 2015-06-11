package com.wqq.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.wqq.music.LrcContent;

/**
 * �Զ���滭��ʣ���������Ч��
 * @author ������
 *
 */
public class LrcView extends TextView{
    
	private float width;
	private float high;
	private Paint CurrentPaint;
	private Paint NoCurrentPaint;
	private float TextHigh = 25;
	private float TextSize = 18;
	private int Index = 0;
	
	private List<LrcContent> mSentenceEntities = new ArrayList<LrcContent>();

	public void setmSentenceEntities(List<LrcContent> mSentenceEntities) {
		this.mSentenceEntities = mSentenceEntities;
	}

	public LrcView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}	

	public LrcView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public LrcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
 
	private void init() {
		// TODO Auto-generated method stub
        setFocusable(true);
		//��������
		CurrentPaint = new Paint();
        CurrentPaint.setAntiAlias(true);
        CurrentPaint.setTextAlign(Paint.Align.CENTER);
        //�Ǹ�������
        NoCurrentPaint = new Paint();
        NoCurrentPaint.setAntiAlias(true);
        NoCurrentPaint.setTextAlign(Paint.Align.CENTER);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		if(canvas==null){
			return;
		}
		CurrentPaint.setColor(Color.argb(210, 251, 248, 29));
		NoCurrentPaint.setColor(Color.argb(140, 255, 255, 255));
		
		CurrentPaint.setTextSize(25);
		CurrentPaint.setTypeface(Typeface.SERIF);
		
		NoCurrentPaint.setTextSize(TextSize);
		NoCurrentPaint.setTypeface(Typeface.DEFAULT);
		
		try {
			setText("");
			canvas.drawText(mSentenceEntities.get(Index).getLrc(), width/2, high/2, CurrentPaint);
			float tempY = high/2;
			//������=����֮ǰ�ľ���
			for(int i = Index - 1;i >= 0;i--){
				//��������
				tempY = tempY - TextHigh; 
				canvas.drawText(mSentenceEntities.get(i).getLrc(), width/2, high/2, NoCurrentPaint);
			}
			//��������֮��ľ���
			for(int i = Index + 1;i < mSentenceEntities.size();i++){
				//��������
				tempY = tempY + TextHigh;
				canvas.drawText(mSentenceEntities.get(i).getLrc(), width/2, high/2, NoCurrentPaint);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setText("û�и����ļ��������أ�");
		}
	}
    
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		this.width = w;
		this.high = h;
	}
	
	public void SetIndex(int index){
		this.Index = index;
	}
}
