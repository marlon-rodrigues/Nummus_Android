package com.redclockapps.nummus;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class viewPieChart extends View 
{
	public static final int WAIT = 0;
	public static final int IS_READY_TO_DRAW = 1;
	public static final int IS_DRAW = 2;
	public static final float START_INC = 30;
	private Paint mBagPaints = new Paint();
	private Paint mLinePaints = new Paint();
	
	private int mOverlayId;
	private int mWidth;
	private int mHeight;
	private int mGapTop;
	private int mGapBottom;
	private int mBgColor;
	private int mGapLeft;
	private int mGapRight;
	private int mState = WAIT;
	private float mStart;
	private float mSweep;
	private double mMaxConnection;
	
	private List<pieDetailsItem> mDataArray;
	
	public viewPieChart(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public viewPieChart(Context context, AttributeSet attr)
	{
		super(context, attr);
	}

	@Override
	protected void onDraw(Canvas canvas) 
	{
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		if(mState != IS_READY_TO_DRAW)
		{
			return;
		}
		
		canvas.drawColor(mBgColor);
		mBagPaints.setAntiAlias(true);
		mBagPaints.setStyle(Paint.Style.FILL);
		mBagPaints.setColor(0x88FF0000);
		mBagPaints.setStrokeWidth(0.0f);
		mLinePaints.setAntiAlias(true);
		mLinePaints.setColor(0xff000000);
		mLinePaints.setStrokeWidth(2.0f);
		mLinePaints.setStyle(Paint.Style.STROKE);
		
		RectF mOvals = new RectF(mGapLeft, mGapTop, mWidth - mGapRight, mHeight - mGapBottom);
		mStart = START_INC;
		
		pieDetailsItem item;
		
		for(int i=0; i<mDataArray.size(); i++)
		{
			item = (pieDetailsItem) mDataArray.get(i);
			mBagPaints.setColor(item.color);
			mSweep = (float)360 * ((float)item.amount / (float)mMaxConnection);
			canvas.drawArc(mOvals, mStart, mSweep, true, mBagPaints);
			canvas.drawArc(mOvals, mStart, mSweep, true, mLinePaints);
			mStart = mStart + mSweep;
		}
		
		mState = IS_DRAW; 
	}
	
	public void setGeometry(int widht, int height, int gapLeft, int gapRight, int gapTop, int gapBottom, int overlayId)
	{
		mWidth = widht;
		mHeight = height;
		mGapLeft = gapLeft;
		mGapRight = gapRight;
		mGapBottom = gapBottom;
		mGapTop = gapTop;
		mOverlayId = overlayId;
	}
	
	public void setSkinParams(int bgColor)
	{
		mBgColor = bgColor;
	}
	
	public void setData(List<pieDetailsItem>data, double maxConnection)
	{
		mDataArray = data;
		mMaxConnection = maxConnection;
		mState = IS_READY_TO_DRAW;
	}
	
	public void setState(int state)
	{
		mState = state;
	}
	
	public int getColorValues(int index)
	{
		if (mDataArray == null)
		{
			return 0;
		}
		else if (index < 0)
		{
			return ((pieDetailsItem)mDataArray.get(0)).color;
		}
		else if (index >= mDataArray.size())
		{
			return ((pieDetailsItem)mDataArray.get(mDataArray.size()-1)).color;
		}
		else
		{
			return ((pieDetailsItem)mDataArray.get(mDataArray.size()-1)).color;
		}
	}

}
