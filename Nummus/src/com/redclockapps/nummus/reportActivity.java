package com.redclockapps.nummus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class reportActivity extends Activity implements OnGestureListener
{
	//creates the local variables and the objects
	incomeExpensesData incExpObj;
	
	RelativeLayout incomeSubView;
	RelativeLayout expensesSubView;
	RelativeLayout reportSubVIew;
	
	RelativeLayout dateIncomeSubView;
	RelativeLayout accountIncomeSubView;
	RelativeLayout categoryIncomeSubView;
	
	RelativeLayout dateExpensesSubView;
	RelativeLayout accountExpensesSubView;
	RelativeLayout categoryExpensesSubView;
	
	ListView dateIncomeListView;
	ListView categoryIncomeListView;
	ListView dateExpensesListView;
	ListView categoryExpensesListView;
	
	TextView textTitle;
	
	TextView textDateIncome;
	TextView textAccountIncome;
	TextView textTotalAccountIncome;
	TextView textCategoryIncome;
	TextView textTotalCategoryIncome;
	
	TextView textDateExpenses;
	TextView textAccountExpenses;
	TextView textTotalAccountExpenses;
	TextView textCategoryExpenses;
	TextView textTotalCategoryExpenses;
	
	TextView textReport;
	TextView textTotalReport;
	
	//Graph labels income
	TextView textGraphIncome1;
	TextView textGraphIncome2;
	TextView textGraphIncome3;
	TextView textGraphIncome4;
	
	ImageView imageGraphIncome1;
	ImageView imageGraphIncome2;
	ImageView imageGraphIncome3;
	ImageView imageGraphIncome4;
	
	//Graph labels expenses
	TextView textGraphExpenses1;
	TextView textGraphExpenses2;
	TextView textGraphExpenses3;
	TextView textGraphExpenses4;
	
	ImageView imageGraphExpenses1;
	ImageView imageGraphExpenses2;
	ImageView imageGraphExpenses3;
	ImageView imageGraphExpenses4;
	
	//Graph labels report
	TextView textGraphReport1;
	TextView textGraphReport2;
	
	ImageView imageGraphReport1;
	ImageView imageGraphReport2;;
	
	Time today;
	
	List<pieDetailsItem> pieIncomeData = new ArrayList<pieDetailsItem>(0);
	
	private GestureDetector gestureScanner;
    @SuppressWarnings("deprecation")
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //getWindow().setWindowAnimations(android.R.style.Animation_Toast);
        setContentView(R.layout.reports_screen);
        
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
    	//instantiates the listviews
    	dateIncomeListView = (ListView) findViewById(R.id.listViewDateIncome);
    	categoryIncomeListView = (ListView) findViewById(R.id.listViewCategoryIncome);
    	dateExpensesListView = (ListView) findViewById(R.id.listViewDateExpenses);
    	categoryExpensesListView = (ListView) findViewById(R.id.listViewCategoryExpenses);
		
    		//date income list view
    	//set the title of the date income listview
    	TextView titleDateIncome = new TextView(this);
    	titleDateIncome.setText("List Income");
    	titleDateIncome.setTextSize(15);
    	titleDateIncome.setTextColor(Color.parseColor("#FFFFFF"));
    	titleDateIncome.setGravity(Gravity.CENTER);
    	titleDateIncome.setBackgroundResource(R.drawable.list_view_title);
    	dateIncomeListView.addHeaderView(titleDateIncome);
    	
    		//category income list view
    	//set the title of the category income listview
    	TextView titleCategoryIncome = new TextView(this);
    	titleCategoryIncome.setText("SubTotal by Categories");
    	titleCategoryIncome.setTextSize(15);
    	titleCategoryIncome.setTextColor(Color.parseColor("#FFFFFF"));
    	titleCategoryIncome.setGravity(Gravity.CENTER);
    	titleCategoryIncome.setBackgroundResource(R.drawable.list_view_title);
    	categoryIncomeListView.addHeaderView(titleCategoryIncome);
    	
    		//date expenses list view
		//set the title of the date expenses listview
		TextView titleDateExpenses = new TextView(this);
		titleDateExpenses.setText("List Expenses");
		titleDateExpenses.setTextSize(15);
		titleDateExpenses.setTextColor(Color.parseColor("#FFFFFF"));
		titleDateExpenses.setGravity(Gravity.CENTER);
		titleDateExpenses.setBackgroundResource(R.drawable.list_view_expenses_title);
		dateExpensesListView.addHeaderView(titleDateExpenses);
    	
			//category income list view
		//set the title of the category expenses listview
		TextView titleCategoryExpenses = new TextView(this);
		titleCategoryExpenses.setText("SubTotal by Categories");
		titleCategoryExpenses.setTextSize(15);
		titleCategoryExpenses.setTextColor(Color.parseColor("#FFFFFF"));
		titleCategoryExpenses.setGravity(Gravity.CENTER);
		titleCategoryExpenses.setBackgroundResource(R.drawable.list_view_expenses_title);
		categoryExpensesListView.addHeaderView(titleCategoryExpenses);
			
    	//set the dates
    	//Get the current date
    	today = new Time(Time.getCurrentTimezone());
    	today.setToNow();
    	
    	int monthShowing = (today.month) + 1;
    	int yearShowing = (today.year);
    	
    	this.monthToShow(monthShowing, yearShowing, 0, 1);
    	this.populateListViews(1);
    	
    	this.monthToShow(monthShowing, yearShowing, 0, 2);
    	this.drawGraphs(0);
    	
    	this.monthToShow(monthShowing, yearShowing, 0, 3);
    	this.populateListViews(3);
    	
    	this.monthToShow(monthShowing, yearShowing, 0, 4);
    	this.populateListViews(4);
    	
    	this.monthToShow(monthShowing, yearShowing, 0, 5);
    	this.drawGraphs(1);
    	
    	this.monthToShow(monthShowing, yearShowing, 0, 6);
    	this.populateListViews(6);
    	
    	this.monthToShow(monthShowing, yearShowing, 0, 7);
    	this.drawGraphs(2);

		this.setSubViewScreen(1);
	}
	
	private void populateListViews(int sender)
	{
		//instantiates the objects
    	incExpObj = new incomeExpensesData(this);
    	
    	//creates and initializes the variables
    	Cursor listToShow = null;
    	int monthShowing = -1;
    	String year = null;
    	String month = null;
    	String dateShowing = null;
    	double total = 0;
    	
    	incExpObj.open();
    	
    	//1 - date income listview
    	//3 - category income listview
    	//4 - date expenses listview
    	//6 - category expenses listview
    	if (sender == 1)
    	{
        	//instantiates the listview and textview
        	dateIncomeListView = (ListView) findViewById(R.id.listViewDateIncome);        	
        	textDateIncome = (TextView) findViewById(R.id.textDateIncome);

        	dateShowing = textDateIncome.getText().toString();
        	
        	monthShowing = this.returnMonth(dateShowing.substring(0, 3));
    		year = dateShowing.substring(6);
    		month = String.valueOf(monthShowing);
    		
    		if (month.length() == 1)
    		{
    			month = "0" + month;
    		}
        	
        	listToShow = incExpObj.getDataToDisplayInReport(0, month, year);
        	
        	if (listToShow.getCount() > 0)
        	{
        		dateIncomeListView.setVisibility(View.VISIBLE);
        		
            	MySimpleCursorAdapter listIncomeAdapter = new MySimpleCursorAdapter(this, R.layout.list_incexp, listToShow, new String[] {"Category", "Date", "Account", "Amount"}, new int[] {R.id.textCategory, R.id.textDate, R.id.textAccount, R.id.textAmount});
            	
            	dateIncomeListView.setAdapter(listIncomeAdapter);
            	dateIncomeListView.setSelected(true);
        	}
        	else
        	{
        		dateIncomeListView.setVisibility(View.INVISIBLE);
        		Toast.makeText(this, "No income this month", Toast.LENGTH_LONG).show();
        	}
    	}
    	else if (sender == 3)
    	{
        	//instantiates the listview and textview
        	categoryIncomeListView = (ListView) findViewById(R.id.listViewCategoryIncome);
        	textCategoryIncome = (TextView) findViewById(R.id.textCategoryIncome);
        	textTotalCategoryIncome = (TextView) findViewById(R.id.textTotalCategoryIncome);

        	dateShowing = textCategoryIncome.getText().toString();
        	
        	monthShowing = this.returnMonth(dateShowing.substring(0, 3));
    		year = dateShowing.substring(6);
    		month = String.valueOf(monthShowing);
    		
    		if (month.length() == 1)
    		{
    			month = "0" + month;
    		}
        	
        	listToShow = incExpObj.getDataToDisplayByCategory(0, month, year);
        	
        	if (listToShow.getCount() > 0)
        	{
        		categoryIncomeListView.setVisibility(View.VISIBLE);
        		
        		CategorySimpleCursorAdapter listByCategoryIncome = new CategorySimpleCursorAdapter(this, R.layout.list_report_category, listToShow, new String[] {"Category", "SUM(Amount)"}, new int[] {R.id.textListReportCategory, R.id.textListReportAmount});
   
            	categoryIncomeListView.setAdapter(listByCategoryIncome);
            	categoryIncomeListView.setSelected(true);
            	
            	if (listToShow.moveToFirst())
            	{
            		do
            		{
            			total += listToShow.getDouble(2);
            		}
            		while (listToShow.moveToNext());
            	}
        	}
        	else
        	{
        		categoryIncomeListView.setVisibility(View.INVISIBLE);
        		Toast.makeText(this, "No income this month", Toast.LENGTH_LONG).show();
        	}
        	
        	textTotalCategoryIncome.setText("Total Income: $" + String.format("%.2f", total));
    	}
    	else if (sender == 4)
    	{
        	//instantiates the listview and textview
        	dateExpensesListView = (ListView) findViewById(R.id.listViewDateExpenses);
        	textDateExpenses = (TextView) findViewById(R.id.textDateExpenses);

        	dateShowing = textDateExpenses.getText().toString();
        	
        	monthShowing = this.returnMonth(dateShowing.substring(0, 3));
    		year = dateShowing.substring(6);
    		month = String.valueOf(monthShowing);
    		
    		if (month.length() == 1)
    		{
    			month = "0" + month;
    		}
        	
        	listToShow = incExpObj.getDataToDisplayInReport(1, month, year);
        	
        	if (listToShow.getCount() > 0)
        	{
        		dateExpensesListView.setVisibility(View.VISIBLE);
        		
            	MySimpleCursorAdapter listExpensesAdapter = new MySimpleCursorAdapter(this, R.layout.list_incexp, listToShow, new String[] {"Category", "Date", "Account", "Amount"}, new int[] {R.id.textCategory, R.id.textDate, R.id.textAccount, R.id.textAmount});
            	
            	dateExpensesListView.setAdapter(listExpensesAdapter);
            	dateExpensesListView.setSelected(true);
        	}
        	else
        	{
        		dateExpensesListView.setVisibility(View.INVISIBLE);
        		Toast.makeText(this, "No expenses this month", Toast.LENGTH_LONG).show();
        	}
    	}
    	else if (sender == 6)
    	{
        	//instantiates the listview and textview
        	categoryExpensesListView = (ListView) findViewById(R.id.listViewCategoryExpenses);
        	textCategoryExpenses = (TextView) findViewById(R.id.textCategoryExpenses);
        	textTotalCategoryExpenses = (TextView) findViewById(R.id.textTotalCategoryExpenses);

        	dateShowing = textCategoryExpenses.getText().toString();
        	
        	monthShowing = this.returnMonth(dateShowing.substring(0, 3));
    		year = dateShowing.substring(6);
    		month = String.valueOf(monthShowing);
    		
    		if (month.length() == 1)
    		{
    			month = "0" + month;
    		}
        	
        	listToShow = incExpObj.getDataToDisplayByCategory(1, month, year);
        	
        	if (listToShow.getCount() > 0)
        	{
        		categoryExpensesListView.setVisibility(View.VISIBLE);
        		
        		CategorySimpleCursorAdapter listByCategoryExpenses = new CategorySimpleCursorAdapter(this, R.layout.list_report_category, listToShow, new String[] {"Category", "SUM(Amount)"}, new int[] {R.id.textListReportCategory, R.id.textListReportAmount});
   
            	categoryExpensesListView.setAdapter(listByCategoryExpenses);
            	categoryExpensesListView.setSelected(true);
            	
            	if (listToShow.moveToFirst())
            	{
            		do
            		{
            			total += listToShow.getDouble(2);
            		}
            		while (listToShow.moveToNext());
            	}
        	}
        	else
        	{
        		categoryExpensesListView.setVisibility(View.INVISIBLE);
        		Toast.makeText(this, "No income this month", Toast.LENGTH_LONG).show();
        	}
        	
        	textTotalCategoryExpenses.setText("Total Expenses: $" + String.format("%.2f", total));
    	}
    	
    	listToShow = null;    	
    	total = 0;
    	
    	dateIncomeListView.setOnItemClickListener(new OnItemClickListener() 
    	{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				final AdapterView<?> listViewToRemove = arg0;
				final int positionToRemove = arg2;
				
				AlertDialog.Builder builderDialog = new AlertDialog.Builder(reportActivity.this);
				builderDialog.setMessage("This option is used to DELETE income. Are you sure that you want to DELETE the selected income?")
				.setTitle("WARNING - DELETE INCOME!")
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which) 
						{
							reportActivity.this.deleteIncomeExpenses(positionToRemove, listViewToRemove, 0);		
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
		});
    	
    	dateExpensesListView.setOnItemClickListener(new OnItemClickListener() 
    	{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				final AdapterView<?> listViewToRemove = arg0;
				final int positionToRemove = arg2;
				
				AlertDialog.Builder builderDialog = new AlertDialog.Builder(reportActivity.this);
				builderDialog.setMessage("This option is used to DELETE expenses. Are you sure that you want to DELETE the selected expenses?")
				.setTitle("WARNING - DELETE EXPENSES!")
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which) 
						{
							reportActivity.this.deleteIncomeExpenses(positionToRemove, listViewToRemove, 1);		
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
		});
    	
    	incExpObj.close();
	}
	
	private void deleteIncomeExpenses(int positionToRemove, AdapterView<?> viewToRemove, int sender)
	{
		//sender = 0 - income
		//sender = 1 - expenses
		
		Cursor tempCursorToDelete = (Cursor) viewToRemove.getItemAtPosition(positionToRemove);
		
		int idToDelete = tempCursorToDelete.getInt(tempCursorToDelete.getColumnIndex("_id"));
		
		incExpObj = new incomeExpensesData(this);
		
		incExpObj.open();
        boolean itemDeleted = incExpObj.deleteIncomeExpenses(idToDelete);
        incExpObj.close();

        if (itemDeleted == true)
        {	
        	if (sender == 0)
        	{
            	this.populateListViews(1);
            	this.populateListViews(3);
            	
            	this.setSubtitleInvisible(0);
            	this.setSubtitleInvisible(2);
            	
            	this.drawGraphs(0);
            	this.drawGraphs(2);
            	
            	Toast.makeText(this, "Income deleted with success", Toast.LENGTH_SHORT).show();
        	}
        	else if (sender == 1)
        	{
            	this.populateListViews(4);
            	this.populateListViews(6);
            	
            	this.setSubtitleInvisible(1);
            	this.setSubtitleInvisible(2);
            	
            	this.drawGraphs(1);
            	this.drawGraphs(2);
            	
            	Toast.makeText(this, "Expenses deleted with success", Toast.LENGTH_SHORT).show();
        	}
        }
	}
	
	private void drawGraphs(int sender)
	{
		//sender = 0 - income
		//sender = 1 - expenses
		//sender = 2 - income/expenses
		
		//creates and initializes the variables
		incExpObj = new incomeExpensesData(this);
		
		Cursor tempCursor = null;
		
		RelativeLayout graphLayout = null;
		
		pieDetailsItem item;
		
		double maxCount = 0;
		double itemCount = 0;
		int colors[] = {Color.parseColor("#A2CB31"), Color.parseColor("#EB2F46"), Color.parseColor("#E38D27"), Color.parseColor("#397BFC")};
		
		//for income/expenses report calculation
		double totalIncome = 0;
		double totalExpenses = 0;
		
    	int monthShowing = -1;
    	String year = null;
    	String month = null;
    	String dateShowing = null;
		
		incExpObj.open();
		
		if (sender == 0)
		{
        	textAccountIncome = (TextView) findViewById(R.id.textAccountIncome);

        	dateShowing = textAccountIncome.getText().toString();
        	
        	monthShowing = this.returnMonth(dateShowing.substring(0, 3));
    		year = dateShowing.substring(6);
    		month = String.valueOf(monthShowing);
    		
    		if (month.length() == 1)
    		{
    			month = "0" + month;
    		}
			
			tempCursor = incExpObj.getDataToDisplayByAccount(sender, month, year);
			graphLayout = (RelativeLayout) findViewById(R.id.graphAccountIncome);
		}
		else if (sender == 1)
		{
        	textAccountExpenses = (TextView) findViewById(R.id.textAccountExpenses);

        	dateShowing = textAccountExpenses.getText().toString();
        	
        	monthShowing = this.returnMonth(dateShowing.substring(0, 3));
    		year = dateShowing.substring(6);
    		month = String.valueOf(monthShowing);
    		
    		if (month.length() == 1)
    		{
    			month = "0" + month;
    		}
			
			tempCursor = incExpObj.getDataToDisplayByAccount(sender, month, year);
			graphLayout = (RelativeLayout) findViewById(R.id.graphAccountExpenses);
		}
		else if (sender == 2)
		{
			textReport = (TextView) findViewById(R.id.textReport);
			
        	dateShowing = textReport.getText().toString();
        	
        	monthShowing = this.returnMonth(dateShowing.substring(0, 3));
    		year = dateShowing.substring(6);
    		month = String.valueOf(monthShowing);
    		
    		if (month.length() == 1)
    		{
    			month = "0" + month;
    		}
    		
    		tempCursor = incExpObj.getDataToDisplayByIsIncome(month, year);
    		graphLayout = (RelativeLayout) findViewById(R.id.graphReport);
		}
		
		if (tempCursor.getCount() > 0)
		{
			if (tempCursor.moveToFirst())
			{
				int i = 0;
				
				do
				{
					itemCount = tempCursor.getDouble(2);
					
					item = new pieDetailsItem();
					
					if (sender == 2)
					{
						this.setSubtitleForGraph(tempCursor.getInt(1), itemCount, tempCursor.getInt(1), sender, incExpObj);
						
						item.color = colors[tempCursor.getInt(1)];
						
						if (tempCursor.getInt(1) == 0)
						{
							totalIncome += itemCount;
						}
						else
						{
							totalExpenses += itemCount;
						}
					}
					else
					{
						this.setSubtitleForGraph(tempCursor.getInt(1), itemCount, i, sender, incExpObj);
						
						item.color = colors[i];
					}
					
					item.amount = itemCount;
					item.label = null;
					
					pieIncomeData.add(item);
					
					maxCount = maxCount + itemCount;
					
					i++;
				}
				while (tempCursor.moveToNext());
			}
			
			if (sender == 0)
			{
				textTotalAccountIncome = (TextView) findViewById(R.id.textTotalAccountIncome);
				textTotalAccountIncome.setVisibility(View.VISIBLE);
				textTotalAccountIncome.setText("Total Income: $" + String.format("%.2f", maxCount));
			}
			else if (sender == 1)
			{
				textTotalAccountExpenses = (TextView) findViewById(R.id.textTotalAccountExpenses);
				textTotalAccountExpenses.setVisibility(View.VISIBLE);
				textTotalAccountExpenses.setText("Total Expenses: $" + String.format("%.2f", maxCount));
			}
			else if (sender == 2)
			{
				textTotalReport = (TextView) findViewById(R.id.textTotalReport);
				textTotalReport.setVisibility(View.VISIBLE);
				textTotalReport.setText("Total Savings: $" + String.format("%.2f", (totalIncome - totalExpenses)));
			}
			
			int size = 355;
			Bitmap mBackgroundImage = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
			
			viewPieChart pieChart = new viewPieChart(this);
			pieChart.setLayoutParams(new LayoutParams(size, size));
			pieChart.setGeometry(size, size, 2, 2, 2, 2, 2130837504);
			pieChart.setSkinParams(Color.TRANSPARENT);
			pieChart.setData(pieIncomeData, maxCount);
			pieChart.invalidate();
			pieChart.draw(new Canvas(mBackgroundImage));
			pieChart = null;
			
			ImageView graph = new ImageView(this);
			graph.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			graph.setBackgroundColor(Color.TRANSPARENT);
			graph.setImageBitmap(mBackgroundImage);
			
			graphLayout.setVisibility(View.VISIBLE);
			graphLayout.addView(graph);
		}
		else
		{
			if (sender == 0)
			{
				textTotalAccountIncome = (TextView) findViewById(R.id.textTotalAccountIncome);
				textTotalAccountIncome.setVisibility(View.INVISIBLE);
				graphLayout.setVisibility(View.INVISIBLE);
				
				Toast.makeText(this, "No income this month", Toast.LENGTH_LONG).show();
			}
			else if (sender == 1)
			{
				textTotalAccountExpenses = (TextView) findViewById(R.id.textTotalAccountExpenses);
				textTotalAccountExpenses.setVisibility(View.INVISIBLE);
				graphLayout.setVisibility(View.INVISIBLE);
				
				Toast.makeText(this, "No expenses this month", Toast.LENGTH_LONG).show();
			}
			else if (sender == 2)
			{
				textTotalReport = (TextView) findViewById(R.id.textTotalReport);
				textTotalReport.setVisibility(View.INVISIBLE);
				graphLayout.setVisibility(View.INVISIBLE);
				
				Toast.makeText(this, "No registers this month", Toast.LENGTH_LONG).show();
			}
		}
		
		incExpObj.close();
	}
	
	//sets up the subtitle for the graph
	private void setSubtitleForGraph(int idAccount, double amount, int order, int sender, incomeExpensesData incExpObj)
	{
		//incExpObj = new incomeExpensesData(this);
		
		String nameAccount = null;
		
		//sender = 0 - graph account income
		//sender = 1 - graph account expenses
		//sender = 2 - graph income/expenses
		if (sender == 0)
		{
			//instantiates the objects
			textGraphIncome1 = (TextView) findViewById(R.id.textIncomeGraph1);
			textGraphIncome2 = (TextView) findViewById(R.id.textIncomeGraph2);
			textGraphIncome3 = (TextView) findViewById(R.id.textIncomeGraph3);
			textGraphIncome4 = (TextView) findViewById(R.id.textIncomeGraph4);
			
			imageGraphIncome1 = (ImageView) findViewById(R.id.imageViewIncomeGraph1);
			imageGraphIncome2 = (ImageView) findViewById(R.id.imageViewIncomeGraph2);
			imageGraphIncome3 = (ImageView) findViewById(R.id.imageViewIncomeGraph3);
			imageGraphIncome4 = (ImageView) findViewById(R.id.imageViewIncomeGraph4);
			
			//determines which one is gonna show
			switch(order)
			{
				case 0:
					nameAccount = incExpObj.getNameAccount(idAccount);
					
					textGraphIncome1.setVisibility(View.VISIBLE);
					textGraphIncome1.setText(nameAccount + " - $" + String.format("%.2f", amount)); 
					
					imageGraphIncome1.setVisibility(View.VISIBLE);
					imageGraphIncome1.setBackgroundColor(Color.parseColor("#A2CB31"));
					break;
					
				case 1:
					nameAccount = incExpObj.getNameAccount(idAccount);
					
					textGraphIncome2.setVisibility(View.VISIBLE);
					textGraphIncome2.setText(nameAccount + " - $" + String.format("%.2f", amount)); 
					
					imageGraphIncome2.setVisibility(View.VISIBLE);
					imageGraphIncome2.setBackgroundColor(Color.parseColor("#EB2F46"));
					break;
					
				case 2:
					nameAccount = incExpObj.getNameAccount(idAccount);
					
					textGraphIncome3.setVisibility(View.VISIBLE);
					textGraphIncome3.setText(nameAccount + " - $" + String.format("%.2f", amount)); 
					
					imageGraphIncome3.setVisibility(View.VISIBLE);
					imageGraphIncome3.setBackgroundColor(Color.parseColor("#E38D27"));
					break;
					
				case 3:
					nameAccount = incExpObj.getNameAccount(idAccount);
					
					textGraphIncome4.setVisibility(View.VISIBLE);
					textGraphIncome4.setText(nameAccount + " - $" + String.format("%.2f", amount)); 
					
					imageGraphIncome4.setVisibility(View.VISIBLE);
					imageGraphIncome4.setBackgroundColor(Color.parseColor("#397BFC"));
					break;	

				default:
					break;
			}
		}
		else if (sender == 1)
		{
			//instantiates the objects
			textGraphExpenses1 = (TextView) findViewById(R.id.textExpensesGraph1);
			textGraphExpenses2 = (TextView) findViewById(R.id.textExpensesGraph2);
			textGraphExpenses3 = (TextView) findViewById(R.id.textExpensesGraph3);
			textGraphExpenses4 = (TextView) findViewById(R.id.textExpensesGraph4);
			
			imageGraphExpenses1 = (ImageView) findViewById(R.id.imageViewExpensesGraph1);
			imageGraphExpenses2 = (ImageView) findViewById(R.id.imageViewExpensesGraph2);
			imageGraphExpenses3 = (ImageView) findViewById(R.id.imageViewExpensesGraph3);
			imageGraphExpenses4 = (ImageView) findViewById(R.id.imageViewExpensesGraph4);
			
			//determines which one is gonna show
			switch(order)
			{
				case 0:
					nameAccount = incExpObj.getNameAccount(idAccount);
					
					textGraphExpenses1.setVisibility(View.VISIBLE);
					textGraphExpenses1.setText(nameAccount + " - $" + String.format("%.2f", amount)); 
					
					imageGraphExpenses1.setVisibility(View.VISIBLE);
					imageGraphExpenses1.setBackgroundColor(Color.parseColor("#A2CB31"));
					break;
					
				case 1:
					nameAccount = incExpObj.getNameAccount(idAccount);
					
					textGraphExpenses2.setVisibility(View.VISIBLE);
					textGraphExpenses2.setText(nameAccount + " - $" + String.format("%.2f", amount)); 
					
					imageGraphExpenses2.setVisibility(View.VISIBLE);
					imageGraphExpenses2.setBackgroundColor(Color.parseColor("#EB2F46"));
					break;
					
				case 2:
					nameAccount = incExpObj.getNameAccount(idAccount);
					
					textGraphExpenses3.setVisibility(View.VISIBLE);
					textGraphExpenses3.setText(nameAccount + " - $" + String.format("%.2f", amount)); 
					
					imageGraphExpenses3.setVisibility(View.VISIBLE);
					imageGraphExpenses3.setBackgroundColor(Color.parseColor("#E38D27"));
					break;
					
				case 3:
					nameAccount = incExpObj.getNameAccount(idAccount);
					
					textGraphExpenses4.setVisibility(View.VISIBLE);
					textGraphExpenses4.setText(nameAccount + " - $" + String.format("%.2f", amount)); 
					
					imageGraphExpenses4.setVisibility(View.VISIBLE);
					imageGraphExpenses4.setBackgroundColor(Color.parseColor("#397BFC"));
					break;	

				default:
					break;
			}
		}
		else if (sender == 2)
		{
			//instantiates the objects
			textGraphReport1 = (TextView) findViewById(R.id.textReportGraph1);
			textGraphReport2 = (TextView) findViewById(R.id.textReportGraph2);
			
			imageGraphReport1 = (ImageView) findViewById(R.id.imageViewReportGraph1);
			imageGraphReport2 = (ImageView) findViewById(R.id.imageViewReportGraph2);
			
			//determines which one is gonna show
			switch(order)
			{
				case 0:
					textGraphReport1.setVisibility(View.VISIBLE);
					textGraphReport1.setText("Income" + " - $" + String.format("%.2f", amount)); 
					
					imageGraphReport1.setVisibility(View.VISIBLE);
					imageGraphReport1.setBackgroundColor(Color.parseColor("#A2CB31"));
					break;
					
				case 1:
					textGraphReport2.setVisibility(View.VISIBLE);
					textGraphReport2.setText("Expenses" + " - $" + String.format("%.2f", amount)); 
					
					imageGraphReport2.setVisibility(View.VISIBLE);
					imageGraphReport2.setBackgroundColor(Color.parseColor("#EB2F46"));
					break;
					
				default:
					break;
			}
		}
	}
	
	//Define which subview will be showing
    private void setSubViewScreen(int sender)
    {
    	incomeSubView = (RelativeLayout) findViewById(R.id.incomeSubViewReportScreen);
    	expensesSubView = (RelativeLayout) findViewById(R.id.expensesSubViewReportScreen);
    	reportSubVIew = (RelativeLayout) findViewById(R.id.reportSubViewReportScreen);
    	
    	dateIncomeSubView = (RelativeLayout) findViewById(R.id.dateIncomeSubViewReportScreen);
    	accountIncomeSubView = (RelativeLayout) findViewById(R.id.accountIncomeSubViewReportScreen);
    	categoryIncomeSubView = (RelativeLayout) findViewById(R.id.categoryIncomeSubViewReportScreen);
    	
    	dateExpensesSubView = (RelativeLayout) findViewById(R.id.dateExpensesSubViewReportScreen);
    	accountExpensesSubView = (RelativeLayout) findViewById(R.id.accountExpensesSubViewReportScreen);
    	categoryExpensesSubView = (RelativeLayout) findViewById(R.id.categoryExpensesSubViewReportScreen);
    	
    	textTitle = (TextView) findViewById(R.id.titelReportScreen);
    	
    	//1 - date income subview
    	//2 - account income subview
    	//3 - category income subview
    	//4 - date expenses subview
    	//5 - account expenses subview
    	//6 - category expenses subview
    	//7 - report subview
    	switch (sender)
    	{
    		case 1:
    			incomeSubView.setVisibility(View.VISIBLE);
    			dateIncomeSubView.setVisibility(View.VISIBLE);
    			accountIncomeSubView.setVisibility(View.INVISIBLE);
    			categoryIncomeSubView.setVisibility(View.INVISIBLE);
    			
    			expensesSubView.setVisibility(View.INVISIBLE);
    			reportSubVIew.setVisibility(View.INVISIBLE);
    			textTitle.setText("income report");
    			break;
    			
    		case 2:
    			incomeSubView.setVisibility(View.VISIBLE);
    			dateIncomeSubView.setVisibility(View.INVISIBLE);
    			accountIncomeSubView.setVisibility(View.VISIBLE);
    			categoryIncomeSubView.setVisibility(View.INVISIBLE);
    			
    			expensesSubView.setVisibility(View.INVISIBLE);
    			reportSubVIew.setVisibility(View.INVISIBLE);
    			textTitle.setText("income report");
    			break;
    			
    		case 3:
    			incomeSubView.setVisibility(View.VISIBLE);
    			dateIncomeSubView.setVisibility(View.INVISIBLE);
    			accountIncomeSubView.setVisibility(View.INVISIBLE);
    			categoryIncomeSubView.setVisibility(View.VISIBLE);
    			
    			expensesSubView.setVisibility(View.INVISIBLE);
    			reportSubVIew.setVisibility(View.INVISIBLE);
    			textTitle.setText("income report");
    			break;
    			
    		case 4:
    			expensesSubView.setVisibility(View.VISIBLE);
    			dateExpensesSubView.setVisibility(View.VISIBLE);
    			accountExpensesSubView.setVisibility(View.INVISIBLE);
    			categoryExpensesSubView.setVisibility(View.INVISIBLE);
    			
    			incomeSubView.setVisibility(View.INVISIBLE);
    			reportSubVIew.setVisibility(View.INVISIBLE);
    			textTitle.setText("expenses report");
    			break;
    			
    		case 5:
    			expensesSubView.setVisibility(View.VISIBLE);
    			dateExpensesSubView.setVisibility(View.INVISIBLE);
    			accountExpensesSubView.setVisibility(View.VISIBLE);
    			categoryExpensesSubView.setVisibility(View.INVISIBLE);
    			
    			incomeSubView.setVisibility(View.INVISIBLE);
    			reportSubVIew.setVisibility(View.INVISIBLE);
    			textTitle.setText("expenses report");
    			break;
    			
    		case 6:
    			expensesSubView.setVisibility(View.VISIBLE);
    			dateExpensesSubView.setVisibility(View.INVISIBLE);
    			accountExpensesSubView.setVisibility(View.INVISIBLE);
    			categoryExpensesSubView.setVisibility(View.VISIBLE);
    			
    			incomeSubView.setVisibility(View.INVISIBLE);
    			reportSubVIew.setVisibility(View.INVISIBLE);
    			textTitle.setText("expenses report");
    			break;
    			
    		case 7:
    			reportSubVIew.setVisibility(View.VISIBLE);
    			incomeSubView.setVisibility(View.INVISIBLE);
    			expensesSubView.setVisibility(View.INVISIBLE);
    			textTitle.setText("income/expenses report");
    			break;
    			
    		default:
    			break;
    	}
    	
    }
    
    private void monthToShow(int month, int year, int operation, int sender)
    {
    	//sender = 1 - income list screen
    	//sender = 2 - income account graph screen
        //sender = 3 - income category table view screen
        //sender = 4 - expense list screen
        //sender = 5 - expense account graph screen
        //sender = 6 - expense category table view screen
        //sender = 7 - all graph screen
        
        //Operation = 0 - just show
        //Operation = 1 - previous month
        //Operation = 2 - next month
    	
		//instantiate the textviews
		textDateIncome = (TextView) findViewById(R.id.textDateIncome);
		textAccountIncome = (TextView) findViewById(R.id.textAccountIncome);
		textCategoryIncome = (TextView) findViewById(R.id.textCategoryIncome);
		
		textDateExpenses = (TextView) findViewById(R.id.textDateExpenses);
		textAccountExpenses = (TextView) findViewById(R.id.textAccountExpenses);
		textCategoryExpenses = (TextView) findViewById(R.id.textCategoryExpenses);
		
		textReport = (TextView) findViewById(R.id.textReport);
    	
    	String title = null;
    	
    	if (operation == 0)
    	{
    		title = this.defineMonth(month);
    		title = title + " - " + String.valueOf(year);
    	}
    	else if (operation == 1)
    	{
    		if (month == 1)
    		{
    			year -= 1;
    			month = 12;
    		}
    		else
    		{
    			month -= 1;
    		}
    		
    		title = this.defineMonth(month);
    		title = title + " - " + String.valueOf(year);
    	}
    	else if (operation == 2)
    	{
    		if (month == 12)
    		{
    			year += 1;
    			month = 1;
    		}
    		else
    		{
    			month += 1;
    		}
    		
    		title = this.defineMonth(month);
    		title = title + " - " + String.valueOf(year);
    	}
    	
    	switch(sender)
    	{
    		case 1:
    			textDateIncome.setText(title);
    			break;
    		case 2:
    			textAccountIncome.setText(title);
    			break;
    		case 3:
    			textCategoryIncome.setText(title);
    			break;
    		case 4:
    			textDateExpenses.setText(title);
    			break;
    		case 5:
    			textAccountExpenses.setText(title);
    			break;
    		case 6:
    			textCategoryExpenses.setText(title);
    			break;
    		case 7:
    			textReport.setText(title);
    			break;
    		default:
    			break;
    	}
    }
    
    private String defineMonth(int month)
    {
    	String monthName = null;
    	
    	switch(month)
    	{
    		case 1:
    			monthName = "Jan";
    			break;
    		case 2:
    			monthName = "Feb";
    			break;
    		case 3:
    			monthName = "Mar";
    			break;
    		case 4:
    			monthName = "Apr";
    			break;
    		case 5:
    			monthName = "May";
    			break;
    		case 6:
    			monthName = "Jun";
    			break;
    		case 7:
    			monthName = "Jul";
    			break;
    		case 8:
    			monthName = "Aug";
    			break;
    		case 9:
    			monthName = "Sep";
    			break;
    		case 10:
    			monthName = "Oct";
    			break;
    		case 11:
    			monthName = "Nov";
    			break;
    		case 12:
    			monthName = "Dec";
    			break;
    		default:
    			break;
    	}
    	
    	return monthName;
    }
	
    private int returnMonth(String month)
    {
    	int numericMonth = -1;
    	
    	if (month.equalsIgnoreCase("Jan"))
    	{
    		numericMonth = 1;
    	}
    	else if(month.equalsIgnoreCase("Feb"))
    	{
    		numericMonth = 2;
    	}
    	else if(month.equalsIgnoreCase("Mar"))
    	{
    		numericMonth = 3;
    	}
    	else if(month.equalsIgnoreCase("Apr"))
    	{
    		numericMonth = 4;
    	}
    	else if(month.equalsIgnoreCase("May"))
    	{
    		numericMonth = 5;
    	}
    	else if(month.equalsIgnoreCase("Jun"))
    	{
    		numericMonth = 6;
    	}
    	else if(month.equalsIgnoreCase("Jul"))
    	{
    		numericMonth = 7;
    	}
    	else if(month.equalsIgnoreCase("Aug"))
    	{
    		numericMonth = 8;
    	}
    	else if(month.equalsIgnoreCase("Sep"))
    	{
    		numericMonth = 9;
    	}
    	else if(month.equalsIgnoreCase("Oct"))
    	{
    		numericMonth = 10;
    	}
    	else if(month.equalsIgnoreCase("Nov"))
    	{
    		numericMonth = 11;
    	}
    	else if(month.equalsIgnoreCase("Dec"))
    	{
    		numericMonth = 12;
    	}
    	
    	return numericMonth;
    }
    
    private void setSubtitleInvisible(int sender)
    {
    	//sender = 0 - income
    	//sender = 1 - expenses
    	//sender = 2 - income/expenses
    	
    	if (sender == 0)
    	{
    		//set the subtitle invisible
    		textGraphIncome1 = (TextView) findViewById(R.id.textIncomeGraph1);
    		textGraphIncome2 = (TextView) findViewById(R.id.textIncomeGraph2);
    		textGraphIncome3 = (TextView) findViewById(R.id.textIncomeGraph3);
    		textGraphIncome4 = (TextView) findViewById(R.id.textIncomeGraph4);
    		
    		imageGraphIncome1 = (ImageView) findViewById(R.id.imageViewIncomeGraph1);
    		imageGraphIncome2 = (ImageView) findViewById(R.id.imageViewIncomeGraph2);
    		imageGraphIncome3 = (ImageView) findViewById(R.id.imageViewIncomeGraph3);
    		imageGraphIncome4 = (ImageView) findViewById(R.id.imageViewIncomeGraph4);
    		
    		textGraphIncome1.setVisibility(View.INVISIBLE);
    		textGraphIncome2.setVisibility(View.INVISIBLE);
    		textGraphIncome3.setVisibility(View.INVISIBLE);
    		textGraphIncome4.setVisibility(View.INVISIBLE);
    		
    		imageGraphIncome1.setVisibility(View.INVISIBLE);
    		imageGraphIncome2.setVisibility(View.INVISIBLE);
    		imageGraphIncome3.setVisibility(View.INVISIBLE);
    		imageGraphIncome4.setVisibility(View.INVISIBLE);
    	}
    	else if (sender == 1)
    	{
    		//set the subtitle invisible
    		textGraphExpenses1 = (TextView) findViewById(R.id.textExpensesGraph1);
    		textGraphExpenses2 = (TextView) findViewById(R.id.textExpensesGraph2);
    		textGraphExpenses3 = (TextView) findViewById(R.id.textExpensesGraph3);
    		textGraphExpenses4 = (TextView) findViewById(R.id.textExpensesGraph4);
    		
    		imageGraphExpenses1 = (ImageView) findViewById(R.id.imageViewExpensesGraph1);
    		imageGraphExpenses2 = (ImageView) findViewById(R.id.imageViewExpensesGraph2);
    		imageGraphExpenses3 = (ImageView) findViewById(R.id.imageViewExpensesGraph3);
    		imageGraphExpenses4 = (ImageView) findViewById(R.id.imageViewExpensesGraph4);
    		
    		textGraphExpenses1.setVisibility(View.INVISIBLE);
    		textGraphExpenses2.setVisibility(View.INVISIBLE);
    		textGraphExpenses3.setVisibility(View.INVISIBLE);
    		textGraphExpenses4.setVisibility(View.INVISIBLE);
    		
    		imageGraphExpenses1.setVisibility(View.INVISIBLE);
    		imageGraphExpenses2.setVisibility(View.INVISIBLE);
    		imageGraphExpenses3.setVisibility(View.INVISIBLE);
    		imageGraphExpenses4.setVisibility(View.INVISIBLE);
    	}
    	else if (sender == 2)
    	{
    		//set the subtitle invisible
			textGraphReport1 = (TextView) findViewById(R.id.textReportGraph1);
			textGraphReport2 = (TextView) findViewById(R.id.textReportGraph2);
			
			imageGraphReport1 = (ImageView) findViewById(R.id.imageViewReportGraph1);
			imageGraphReport2 = (ImageView) findViewById(R.id.imageViewReportGraph2);
			
			textGraphReport1.setVisibility(View.INVISIBLE);
			textGraphReport2.setVisibility(View.INVISIBLE);
			
			imageGraphReport1.setVisibility(View.INVISIBLE);
			imageGraphReport2.setVisibility(View.INVISIBLE);
    	}
    }
    
    //generates txt
    private void generateText()
    {
    	incExpObj = new incomeExpensesData(this);
    	
    	String lineToWrite = null;
    	
    	String nameCategory;
    	String nameAccount;
    	String isIncome;
    	String amount;
    	
    	boolean printedLine = false;
    	
    	String dateShowing;
    	int monthShowing = -1;
    	String monthTitle;
    	String month;
    	String year;
    	
    	Cursor tempCursor;
    	
    	textReport = (TextView) findViewById(R.id.textReport);
    	textTotalReport = (TextView) findViewById(R.id.textTotalReport);
    	
    	dateShowing = textReport.getText().toString();
    	
    	monthShowing = this.returnMonth(dateShowing.substring(0, 3));
    	monthTitle = dateShowing.substring(0, 3);
		year = dateShowing.substring(6);
		month = String.valueOf(monthShowing);
		
		if (month.length() == 1)
		{
			month = "0" + month;
		}
    	
		incExpObj.open();
		tempCursor = incExpObj.getDataForText(month, year);
		
        try
        {
        	FileOutputStream f = openFileOutput("report.txt", MODE_WORLD_READABLE);
        	OutputStreamWriter osw = new OutputStreamWriter(f);
        	
        	osw.write("NUMMUS - Report Income/Expenses - " + monthTitle + " - " + year + "\n");
        	osw.write("--------------------------------------------------------------------------------------");
        	osw.write("\nCategory                  Date          Amount             Account     Type\n");
        	osw.write("--------------------------------------------------------------------------------------");
        	osw.write("\nIncome\n");
        	osw.write("--------------------------------------------------------------------------------------\n");
        	
        	if (tempCursor.moveToFirst())
        	{
        		do
        		{
        			nameAccount = incExpObj.getNameAccount(tempCursor.getInt(4));
        			nameCategory = incExpObj.getNameCategory(tempCursor.getInt(1));
        			amount = "$" + String.format("%.2f", tempCursor.getDouble(2));
        			
        			if (tempCursor.getInt(5) == 0)
        			{
        				isIncome = "Income";
        			}
        			else
        			{
        				isIncome = "Expenses";
        				
        				if (printedLine == false)
        				{
        					osw.write("--------------------------------------------------------------------------------------");
        		        	osw.write("\nExpenses\n");
        		        	osw.write("--------------------------------------------------------------------------------------\n");
        		        	
        		        	printedLine = true;
        				}
        			}
        			
        			lineToWrite = nameCategory + String.format("%1$" + (35 - nameCategory.length())  + "s", tempCursor.getString(3)) + "     " + amount +  " " + String.format("%1$" + (25 - amount.length())  + "s",nameAccount)  + "     " + isIncome + "\n";
        			
        			osw.write(lineToWrite);
        		}
        		while (tempCursor.moveToNext());
        	}
        	
        	osw.write("\n\n\n" + textTotalReport.getText().toString());
        	
        	osw.flush();
        	osw.close();
        } catch (IOException e) 
        {
        	e.printStackTrace();
		}
        
        incExpObj.close();
        
        this.sendEmail(null, null, "Nummus - Your report is here!", "Attached.");
    }
    
    //sends an email
    private void sendEmail(String[] emailAddresses, String[] carbonCopies, String subject, String message)
    {
    	String destinyPath = "/data/data/" + getPackageName() + "/files";
    	File f = new File(destinyPath, "report.txt");
    	
    	if (!f.exists() || !f.canRead())
    	{
    		Toast.makeText(this, "Sorry,  it is not possible to send the file", Toast.LENGTH_LONG).show();
    	}
    	
    	Uri uri = Uri.parse("file://" + f.getAbsolutePath());
    	
    	Intent emailIntent = new Intent(Intent.ACTION_SEND);
    	emailIntent.setData(Uri.parse("mailto:"));
    	emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddresses);
    	emailIntent.putExtra(Intent.EXTRA_CC, carbonCopies);
    	emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
    	emailIntent.putExtra(Intent.EXTRA_TEXT, message);
    	emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
    	emailIntent.setType("message/rfc822");
    	startActivity(Intent.createChooser(emailIntent, "Email"));
    }
    
    public static String padRight(String s, int n) 
    {
        return String.format("%1$-" + n + "s", s);  
    }

	//General buttons
	public void incomeButtonClicked(View view)
	{
		this.setSubViewScreen(1);
	}
	
	public void expensesButtonClicked(View view)
	{
		this.setSubViewScreen(4);
	}
	
	public void reportButtonClicked(View view)
	{
		this.setSubViewScreen(7);
	}
	
	//Income view buttons
	public void dateIncomeButtonClicked(View view)
	{
		this.setSubViewScreen(1);
	}
	
	public void accountIncomeButtonClicked(View view)
	{
		this.setSubViewScreen(2);
	}
	
	public void categoryIncomeButtonClicked(View view)
	{
		this.setSubViewScreen(3);
	}
	
	public void lefArrowDateIncomeButtonClicked(View view)
	{
		textDateIncome = (TextView) findViewById(R.id.textDateIncome);

		String dateShowing = textDateIncome.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 1, 1);
		
		this.populateListViews(1);
	}
	
	public void rightArrowDateIncomeButtonClicked(View view)
	{
		textDateIncome = (TextView) findViewById(R.id.textDateIncome);

		String dateShowing = textDateIncome.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 2, 1);
		
		this.populateListViews(1);
	}
	
	public void lefArrowAccountIncomeButtonClicked(View view)
	{
		this.setSubtitleInvisible(0);
		
		textAccountIncome = (TextView) findViewById(R.id.textAccountIncome);

		String dateShowing = textAccountIncome.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 1, 2);
		
		this.drawGraphs(0);
	}
	
	public void rightArrowAccountIncomeButtonClicked(View view)
	{
		this.setSubtitleInvisible(0);
		
		textAccountIncome = (TextView) findViewById(R.id.textAccountIncome);

		String dateShowing = textAccountIncome.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 2, 2);
		
		this.drawGraphs(0);
	}
	
	public void lefArrowCategoryIncomeButtonClicked(View view)
	{
		textCategoryIncome = (TextView) findViewById(R.id.textCategoryIncome);
		
		String dateShowing = textCategoryIncome.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 1, 3);
		
		this.populateListViews(3);
	}
	
	public void rightArrowCategoryIncomeButtonClicked(View view)
	{
		textCategoryIncome = (TextView) findViewById(R.id.textCategoryIncome);
		
		String dateShowing = textCategoryIncome.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 2, 3);
		
		this.populateListViews(3);
	}
	
	//Expenses screen buttons
	public void dateExpensesButtonClicked(View view)
	{
		this.setSubViewScreen(4);
	}
	
	public void accountExpensesButtonClicked(View view)
	{
		this.setSubViewScreen(5);
	}
	
	public void categoryExpensesButtonClicked(View view)
	{
		this.setSubViewScreen(6);
	}
	
	public void lefArrowDateExpensesButtonClicked(View view)
	{
		textDateExpenses = (TextView) findViewById(R.id.textDateExpenses);

		String dateShowing = textDateExpenses.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 1, 4);
		
		this.populateListViews(4);
	}
	
	public void rightArrowDateExpensesButtonClicked(View view)
	{
		textDateExpenses = (TextView) findViewById(R.id.textDateExpenses);

		String dateShowing = textDateExpenses.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 2, 4);
		
		this.populateListViews(4);
	}
	
	public void lefArrowAccountExpensesButtonClicked(View view)
	{
		this.setSubtitleInvisible(1);
		
		textAccountExpenses = (TextView) findViewById(R.id.textAccountExpenses);

		String dateShowing = textAccountExpenses.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 1, 5);
		
		this.drawGraphs(1);
	}
	
	public void rightArrowAccountExpensesButtonClicked(View view)
	{
		this.setSubtitleInvisible(1);
		
		textAccountExpenses = (TextView) findViewById(R.id.textAccountExpenses);

		String dateShowing = textAccountExpenses.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 2, 5);
		
		this.drawGraphs(1);
	}
	
	public void lefArrowCategoryExpensesButtonClicked(View view)
	{
		textCategoryExpenses = (TextView) findViewById(R.id.textCategoryExpenses);
		
		String dateShowing = textCategoryExpenses.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 1, 6);
		
		this.populateListViews(6);	
	}
	
	public void rightArrowCategoryExpensesButtonClicked(View view)
	{
		textCategoryExpenses = (TextView) findViewById(R.id.textCategoryExpenses);
		
		String dateShowing = textCategoryExpenses.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 2, 6);
		
		this.populateListViews(6);	
	}
	
	//Report screen buttons
	public void sendPDFButtonClicked(View view)
	{
		this.generateText();
	}
	
	public void lefArrowAccountReportButtonClicked(View view)
	{
		this.setSubtitleInvisible(2);
		
		textReport = (TextView) findViewById(R.id.textReport);

		String dateShowing = textReport.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 1, 7);
		
		this.drawGraphs(2);
	}
	
	public void rightArrowAccountReportButtonClicked(View view)
	{
		this.setSubtitleInvisible(2);
		
		textReport = (TextView) findViewById(R.id.textReport);

		String dateShowing = textReport.getText().toString();

		int month = this.returnMonth(dateShowing.substring(0, 3));
		int year = Integer.parseInt(dateShowing.substring(6));
		
		this.monthToShow(month, year, 2, 7);
		
		this.drawGraphs(2);
	}
	
	//Screen touch functions
	public boolean onDown(MotionEvent arg0) 
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
				this.finish();
			}
			else if (e2.getX() - e1.getX() > 1 && Math.abs(velocityX) > 1)
			{
				//swipe right
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

	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
