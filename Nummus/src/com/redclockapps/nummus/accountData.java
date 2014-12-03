package com.redclockapps.nummus;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class accountData 
{
	//Declares all the table rows
	static final String acc000ID = "_id";
	static final String acc000Name = "Name";
	
	//Declares the database info
	static final String DATABASE_NAME = "DBNummus";
	static final String DATABASE_TABLE = "Acc000";
	
	final Context context;
	
	DatabaseHelper DBHelper;
	SQLiteDatabase db;
	
	public accountData(Context ctx)
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
	public accountData open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	//closes the database
	public void close()
	{
		DBHelper.close();
	}
	
	//get the accounts to display in the income/expenses screen
	public Cursor getAccountsToDisplay()
	{
		return db.query(DATABASE_TABLE, new String[] {acc000ID, acc000Name}, null, null, null, null, null, null);
	}

}
