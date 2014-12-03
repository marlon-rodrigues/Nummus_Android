package com.redclockapps.nummus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class preferencesData 
{
	//Declares all the table rows
		static final String pfr000Sound = "sound";
		static final String pfr000ShowSavings = "showSavings";
		
		//Declares the database info
		static final String DATABASE_NAME = "DBNummus";
		static final String DATABASE_TABLE = "Pfr000";
		
		final Context context;
		
		DatabaseHelper DBHelper;
		SQLiteDatabase db;
		
		public preferencesData(Context ctx)
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
		public preferencesData open() throws SQLException
		{
			db = DBHelper.getWritableDatabase();
			return this;
		}
		
		//closes the database
		public void close()
		{
			DBHelper.close();
		}
		
		//return on of the columns of the preferences table
		public int getPreferences(int filter)
		{
			int preferences = -1;
			
			Cursor dataPreferences = db.query(DATABASE_TABLE, null, null, null, null, null, null, null);
			
			//0 - Sounds
			//1 - ShowBy
			if (dataPreferences.moveToFirst())
			{
				if (filter == 0)
				{
					preferences = dataPreferences.getInt(0); 
				}
				else
				{
					preferences = dataPreferences.getInt(1);
				}	
			}
			
			return preferences;
		}
		
		//update the preferences table
		public void updatePreferences(int playSound, int showBy)
		{
			ContentValues valuesToUpdate = new ContentValues();
			valuesToUpdate.put(pfr000Sound, playSound);
			valuesToUpdate.put(pfr000ShowSavings, showBy);
			
			db.update(DATABASE_TABLE, valuesToUpdate, null, null);
		}
	
}
