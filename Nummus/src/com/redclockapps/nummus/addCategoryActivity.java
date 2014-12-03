package com.redclockapps.nummus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;

public class addCategoryActivity extends Activity implements OnGestureListener
{
	categoryData ctgObj;
	
	EditText nameCategory;
	RadioButton radioIncome;
	RadioButton radioExpenses;
	
	private GestureDetector gestureScanner;
	@SuppressWarnings("deprecation")
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category_screen);
        
		ctgObj = new categoryData(this);
		
		nameCategory = (EditText) findViewById(R.id.categoyNameDescription);
		radioIncome = (RadioButton) findViewById(R.id.radioIncome);
		radioExpenses = (RadioButton) findViewById(R.id.radioExpenses);
		
		this.setFocusNameTextView();
		
		gestureScanner = new GestureDetector(this);
    }
	
    @Override
	public boolean onTouchEvent(MotionEvent event) 
    {
    	return gestureScanner.onTouchEvent(event);
	}
    
	private boolean validateNewCategory()
	{
		boolean _validated = true;
		
		String messageTitle = null;
		String message = null;
		
		int isIncome = -1;
		
		if (nameCategory.getText().toString().length() == 0)
		{
			_validated = false;
			messageTitle = "Ops!";
			message = "Please, provide the category name";
		}
		
		if(radioIncome.isChecked())
		{
			isIncome = 0;
		}
		else if (radioExpenses.isChecked())
		{
			isIncome = 1;
		}
		
		//opens the databsae - it closes when finish saving
		ctgObj.open();
		boolean _validateDatabase = ctgObj.validateNewCategory(isIncome, nameCategory.getText().toString());
		
		if (_validateDatabase == false)
		{
			_validated = false;
			messageTitle = "Ops!";
			message = "Category name already exist";
		}
		
		if (_validated == false)
		{
			AlertDialog.Builder builderDialog = new AlertDialog.Builder(this);
			builderDialog.setMessage(message)
			.setTitle(messageTitle)
			.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						dialog.cancel();
						addCategoryActivity.this.setFocusNameTextView();
					}
				});
			
			AlertDialog alert = builderDialog.create();
			alert.show();
		}
		
		return _validated;		
	}
	
	private void saveNewCategory()
	{
		String nameNewCategory = null;
		int isIncome = -1;
		
		nameNewCategory = this.nameCategory.getText().toString();
		
		if (radioIncome.isChecked())
		{
			isIncome = 0;
		}
		else if (radioExpenses.isChecked())
		{
			isIncome = 1;
		}
		
		ctgObj.addCategory(nameNewCategory, isIncome);
		
		//closes the database - it opens when its validating the entry
		ctgObj.close();
	}

    //show keyboard when focus on the name category textview
    public void setFocusNameTextView()
    {	
    	//mgr.toggleSoftInput(nameCategory.getId(),InputMethodManager.SHOW_FORCED);
    	
    	nameCategory.postDelayed(new Runnable() 
    	{
			public void run() 
			{
				InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				mgr.showSoftInput(nameCategory, InputMethodManager.SHOW_IMPLICIT);
			}
		}, 200);
    }
    
    //cancel operation, return to previous screen
    private void cancelOperation()
    {
		AlertDialog.Builder builderDialog = new AlertDialog.Builder(this);
		builderDialog.setMessage("Confirm cancel?")
		.setTitle("Cancel add category")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			    	mgr.hideSoftInputFromWindow(nameCategory.getWindowToken(), 0);
					addCategoryActivity.this.finish();
				}
			})
		.setNegativeButton("No", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.cancel();
			}
		});
		
		AlertDialog alert = builderDialog.create();
		alert.show();
    }

    public void radioButtonClicked(View view)
    {
    	InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	mgr.hideSoftInputFromWindow(nameCategory.getWindowToken(), 0);
    }
    
    
    //******** Touch Functions ***************************************
	public boolean onDown(MotionEvent e) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		try
		{
			if(Math.abs(e1.getY() - e2.getY()) > 250)
			{
				return false;
			}
			
			if(e1.getX() - e2.getX() > 1 && Math.abs(velocityX) > 1)
			{
				//left swipe
				this.cancelOperation();
			}
			else if (e2.getX() - e1.getX() > 1 && Math.abs(velocityX) > 1)
			{
				//right swipe
				boolean _isValidate = this.validateNewCategory();
				if (_isValidate == true)
				{
					this.saveNewCategory();
					this.finish();
				}
			}
		} catch (Exception e)
		{
			
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
