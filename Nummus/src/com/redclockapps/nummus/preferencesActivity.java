package com.redclockapps.nummus;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class preferencesActivity extends Activity implements OnGestureListener
{
	//creates the local variables and the objects
	preferencesData prefObj;
	
	RelativeLayout settingsView;
	RelativeLayout helpView;
	RelativeLayout aboutView;
	
	ToggleButton isSoundOn;
	RadioButton radioMonth;
	RadioButton radioYear;
	RadioButton radioTotal;
	
	TextView textHelp;
	
	private GestureDetector gestureScanner;
    @SuppressWarnings("deprecation")
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //getWindow().setWindowAnimations(android.R.style.Animation_Toast);
        setContentView(R.layout.preferences_screen);
        
        this.additionalOnCreate();
        
        gestureScanner = new GestureDetector(this);
    }
	
    @Override
	public boolean onTouchEvent(MotionEvent event) 
    {
    	return gestureScanner.onTouchEvent(event);
	}
    
    private void additionalOnCreate()
    {
    	prefObj = new preferencesData(this);
    	
    	isSoundOn = (ToggleButton) findViewById(R.id.toggleButtonSound);
    	radioMonth = (RadioButton) findViewById(R.id.radioMonth);
    	radioYear = (RadioButton) findViewById(R.id.radioYear);
    	radioTotal = (RadioButton) findViewById(R.id.radioTotal);
    	
    	textHelp = (TextView) findViewById(R.id.textViewHelp);
    	
    	//get the values of the database and set up the screen
    	prefObj.open();
    	
    	int isOn = prefObj.getPreferences(0);
    	int showBy = prefObj.getPreferences(1);
    	
    	prefObj.close();
    	
    	if (isOn == 0)
    	{
    		isSoundOn.setChecked(true);
    	}
    	else
    	{
    		isSoundOn.setChecked(false);
    	}
    	
    	switch (showBy)
    	{
			case 0:
				radioMonth.setChecked(true);
				break;
			
			case 1:
				radioYear.setChecked(true);
				break;
			
			case 2:
				radioTotal.setChecked(true);
				break;
				
			default:
				break;
		}
    	
    	//set the help textview scrollable
    	textHelp.setMovementMethod(new ScrollingMovementMethod());
    	
    	//opens the settings subview
    	this.setSubViewScreen(1);
    }
    
  //Define which subview will be showing
    private void setSubViewScreen(int sender)
    {
    	settingsView = (RelativeLayout) findViewById(R.id.settingsSubViewPreferencesScreen);
    	helpView = (RelativeLayout) findViewById(R.id.helpSubViewPreferencesScreen);
    	aboutView = (RelativeLayout) findViewById(R.id.aboutSubViewPreferencesScreen);
    	    	
    	//1 - settings View
    	//2 - help View
    	//3 - about view
    	switch (sender)
    	{
    		case 1:
    			settingsView.setVisibility(View.VISIBLE);
    			helpView.setVisibility(View.INVISIBLE);
    			aboutView.setVisibility(View.INVISIBLE);
    			break;
    			
    		case 2:
    			settingsView.setVisibility(View.INVISIBLE);
    			helpView.setVisibility(View.VISIBLE);
    			aboutView.setVisibility(View.INVISIBLE);
    			break;
    			
    		case 3:
    			settingsView.setVisibility(View.INVISIBLE);
    			helpView.setVisibility(View.INVISIBLE);
    			aboutView.setVisibility(View.VISIBLE);
    			break;
    			
    		default:
    			break;
    	}
    	
    }
    
    private void updatePreferences()
    {
    	prefObj = new preferencesData(this);
    	
    	isSoundOn = (ToggleButton) findViewById(R.id.toggleButtonSound);
    	radioMonth = (RadioButton) findViewById(R.id.radioMonth);
    	radioYear = (RadioButton) findViewById(R.id.radioYear);
    	radioTotal = (RadioButton) findViewById(R.id.radioTotal);
    	
    	int isOn = -1;
    	int showBy = -1;
    	
    	if (isSoundOn.isChecked())
    	{
    		isOn = 0;
    	}
    	else
    	{
    		isOn = 1;
    	}
    	
    	if (radioMonth.isChecked())
    	{
    		showBy = 0;
    	} 
    	else if (radioYear.isChecked())
    	{
    		showBy = 1;
    	}
    	else if (radioTotal.isChecked())
    	{
    		showBy = 2;
    	}
    	
    	prefObj.open();
    	prefObj.updatePreferences(isOn, showBy);
    	prefObj.close();
    }
    
    //sends an email
    private void sendEmail(String[] emailAddresses, String[] carbonCopies, String subject, String message)
    {
    	Intent emailIntent = new Intent(Intent.ACTION_SEND);
    	emailIntent.setData(Uri.parse("mailto:"));
    	emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddresses);
    	emailIntent.putExtra(Intent.EXTRA_CC, carbonCopies);
    	emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
    	emailIntent.putExtra(Intent.EXTRA_TEXT, message);
    	emailIntent.setType("message/rfc822");
    	startActivity(Intent.createChooser(emailIntent, "Email"));
    }
    
    //Buttons
    public void settingsButtonClicked(View view)
    {
    	this.setSubViewScreen(1);
    }
    
    public void helpButtonClicked(View view)
    {
    	this.setSubViewScreen(2);
    }
    
    public void aboutButtonClicked(View view)
    {
    	this.setSubViewScreen(3);
    }
    
    public void rateButtonClicked(View view)
    {
    	Uri uri = Uri.parse("market://details?id=" + getPackageName());
    	Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
    	try
    	{
    		startActivity(goToMarket);
    	}
    	catch (ActivityNotFoundException e)
    	{
    		Toast.makeText(this, "Sorry, it was not possible to launch market place", Toast.LENGTH_SHORT).show();
    	}
    }
    
    public void addCategoryButtonClicked(View view)
    {
    	startActivity(new Intent("com.redclockapps.nummus.addCategoryActivity"));
    }
    
    public void requestSupportButtonClicked(View view)
    {
    	String to[] = {"nummussupport@redclockapps.com"};
    	this.sendEmail(to, null, "Nummus - Support Request", "Support:");
    }
    
    public void shareButtonClicked(View view)
    {
    	String message = "Hey! \nCheck it out Nummus, an easy and great app to control your budget.\n\nSee more about it on facebook:\nwww.facebook.com/nummusapp\n\nSee ya\n";
    	this.sendEmail(null, null, "Check it out this great app to control your budget", message);
    }
    
	public boolean onDown(MotionEvent e) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	{
		try
		{
			if(Math.abs(e1.getY() - e2.getY()) > 250)
			{
				return false;
			}
			
			if(e1.getX() - e2.getX() > 1 && Math.abs(velocityX) > 1)
			{
				//swipe left
				this.updatePreferences();
				this.finish();
			}
			else if (e2.getX() - e1.getX() > 1 && Math.abs(velocityX) > 1)
			{
				//swipe right
				this.updatePreferences();
				this.finish();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public void onLongPress(MotionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void onShowPress(MotionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	public boolean onSingleTapUp(MotionEvent e) 
	{
		// TODO Auto-generated method stub
		return false;
	}
}


