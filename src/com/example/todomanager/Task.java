package com.example.todomanager;

import android.provider.BaseColumns;

public class Task {

	public static String[] columns = new String[] {Tasks._ID, Tasks.TITLE, Tasks.DESCRIPTION, Tasks.DUEDATE};
	
	public long id;
	public String title;
	public String description;
	public String duedate;
	
	public static final class Tasks implements BaseColumns {
		
		private Tasks(){
			
		}
		
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		public static final String DUEDATE = "duedate";
		
	}
	
	@Override
	public String toString() {
		return "Título: " + title + ", Descrição: " + description + ", Data: " + duedate;
	}
	
}
