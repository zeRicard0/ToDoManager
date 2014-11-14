package com.example.todomanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	private String[] scriptSQLCreate;
	private String scriptSQLDelete;
	
	SQLiteHelper (Context context, String dbName, int dbVersion, String[] scriptSQLCreate, String scriptSQLDelete){
		super(context, dbName, null, dbVersion);
		
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		int scriptAmount = scriptSQLCreate.length;
		
		for(int i=0; i<scriptAmount; i++){
			db.execSQL(scriptSQLCreate[i]);
		}
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(scriptSQLDelete);
		onCreate(db);
	}
	
	
	
}
