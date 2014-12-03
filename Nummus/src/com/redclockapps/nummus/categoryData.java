package com.redclockapps.nummus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class categoryData 
{
	//Declares all the table rows
	static final String ctg000ID = "_id";
	static final String ctg000Name = "Name";
	static final String ctg000isIncome = "isIncome";
	
	//Declares the database info
	static final String DATABASE_NAME = "DBNummus";
	static final String DATABASE_TABLE = "Ctg000";
	
	final Context context;
	
	DatabaseHelper DBHelper;
	SQLiteDatabase db;
	
	public categoryData(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{

		public DatabaseHelper(Context context) 
		{
			//use version 1 because the app doesnt control the database version
			super(context, DATABASE_NAME, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			//No need to be implemented for now
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			//No need to be implemented for now
		}
	}
	
	//opens the database
	public categoryData open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	//closes the database
	public void close()
	{
		DBHelper.close();
	}
	
	//get the categories to display in the income/expenses screen
	public Cursor getCategoriesToDisplay(int filter)
	{
		return db.query(DATABASE_TABLE, new String[] {ctg000ID, ctg000Name}, ctg000isIncome + " = " + filter, null, null, null, ctg000Name, null);
	}
	
	//get the categories to validate the user entry
	public boolean validateNewCategory(int filter, String nameCategory)
	{
		boolean _validated = true;
		
		Cursor categories = db.query(DATABASE_TABLE, new String[] {ctg000Name, ctg000isIncome}, null, null, null, null, null, null);
		
		if(categories.moveToFirst())
		{
			do
			{
				String stringToCompare = categories.getString(0);
				int _isIncome = categories.getInt(1);
				
				if (filter == 0)
				{
					if (_isIncome == 0 && stringToCompare.equalsIgnoreCase(nameCategory))
					{
						_validated = false;
					}
				}
				else
				{
					if (_isIncome == 1 && stringToCompare.equalsIgnoreCase(nameCategory))
					{
						_validated = false;
					}
				}
			}
			while (categories.moveToNext());
		}
		
		return _validated;
	}

	//add category to the database
	public void addCategory(String nameCategory, int isIncome)
	{
		ContentValues dataEntry = new ContentValues();
		
		dataEntry.put(ctg000Name, nameCategory);
		dataEntry.put(ctg000isIncome, isIncome);
		db.insert(DATABASE_TABLE, null, dataEntry);
	}
}
