package com.redclockapps.nummus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class splashActivity extends Activity 
{
	protected boolean _active = true;
	protected int _splahTime = 1500;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		Thread splashTime = new Thread()
		{
			@Override
			public void run() 
			{
				try
				{
					int waited = 0;
					while (_active && (waited < _splahTime))
					{
						sleep(100);
						if (_active)
						{
							waited += 100;
						}
					}
				} 
				catch (InterruptedException e)
				{
					
				}
				finally 
				{
					startActivity(new Intent("com.redclockapps.nummus.MainActivity"));
					finish();
				}
			}
		};
		
		splashTime.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			_active = false;
		}
		
		return true;
	}

}
