package com.redclockapps.nummus;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CategorySimpleCursorAdapter extends SimpleCursorAdapter 
{
	//creates the variables and the objects
	incomeExpensesData incExpObj;

	@SuppressWarnings("deprecation")
	public CategorySimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) 
	{
		super(context, layout, c, from, to);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) 
	{
		// TODO Auto-generated method stub
		super.bindView(view, context, cursor);
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		if(holder == null)
		{
			holder = new ViewHolder();
			holder.category = (TextView) view.findViewById(R.id.textListReportCategory);
			holder.amount = (TextView) view.findViewById(R.id.textListReportAmount);
			view.setTag(holder);
		}
		
		incExpObj = new incomeExpensesData(context);
		
		int idCategory = cursor.getInt(cursor.getColumnIndex("Category"));
		
		incExpObj.open();
		String nameCategory = incExpObj.getNameCategory(idCategory);
		incExpObj.close();
		
		double amounItem = Double.parseDouble(cursor.getString(cursor.getColumnIndex("SUM(Amount)")));
		
		holder.category.setText(nameCategory);
		holder.amount.setText("$" + String.format("%.2f", amounItem));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) 
	{
		return LayoutInflater.from(context).inflate(R.layout.list_report_category, parent, false);
	}

	static class ViewHolder
	{
		TextView category;
		TextView amount;
	}

}
