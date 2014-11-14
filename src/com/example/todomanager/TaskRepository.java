package com.example.todomanager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.todomanager.Task.Tasks;

public class TaskRepository {
	
	private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS task";
	
	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
		"create table task ( _id integer primary key autoincrement, title text not null, "
		+ "description text not null, duedate text not null);",
		"insert into task(title,description,duedate) values('Passeio cachorro','Levar o Tobby para dar uma voltinha pelo bairro','1950');",
		"insert into task(title,description,duedate) values('Lavar a Louça','Lavar a louça que está na pia e limpar o fogão','1960');",
		"insert into task(title,description,duedate) values('Fazer compras','Comprar pão e leite pro café','1970');" 
	};
	
	private static final String DATABASE_NAME = "task";
	
	private static final int DB_VERSION = 1;
	
	public static final String TASK_TABLE = "task";
	
	protected SQLiteDatabase db;
	
	public TaskRepository (Context ctx){
		db = new SQLiteHelper(ctx, TaskRepository.DATABASE_NAME,
									TaskRepository.DB_VERSION,
									TaskRepository.SCRIPT_DATABASE_CREATE,
									TaskRepository.SCRIPT_DATABASE_DELETE).getWritableDatabase();
	}
	
	public long save(Task task){
		if(task.id == 0){
			return insert(task);
		}
		
		refresh(task);
		
		return task.id;
	}
	
	private long insert(Task task){
		ContentValues values = new ContentValues();
		
		values.put(Tasks.TITLE, task.title);
		values.put(Tasks.DESCRIPTION, task.description);
		values.put(Tasks.DUEDATE, task.duedate);
		
		return insert(values);
	}
	
	private long insert(ContentValues values){
		return db.insert(TASK_TABLE, "", values);
	}
	
	
	private int refresh(Task task){
		ContentValues values = new ContentValues();
		
		values.put(Tasks.TITLE, task.title);
		values.put(Tasks.DESCRIPTION, task.description);
		values.put(Tasks.DUEDATE, task.duedate);
		
		String _id = String.valueOf(task.id);
		
		String where = Tasks._ID + "=?";
		
		String[] whereArgs = new String[] { _id };
		
		return refresh(values, where, whereArgs);
	}
	
	public int refresh(ContentValues values, String where, String[] whereArgs){
		return db.update(TASK_TABLE, values, where, whereArgs);
	}
	
	public int delete(long id){
		String where = Tasks._ID + "=?";
		
		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };
		
		return delete(where, whereArgs);
	}
	
	public int delete(String where, String[] whereArgs){
		return db.delete(TASK_TABLE, where, whereArgs);
	}
	
	public Task searchTask(long id){
		
		Cursor c = db.query(true, TASK_TABLE, Task.columns, Tasks._ID + "=" + id, null, null, null, null, null);
		
		if(c.getCount() > 0){
			
			c.moveToFirst();
			
			Task task = new Task();
			
			task.id = c.getLong(0);
			task.title = c.getString(1);
			task.description = c.getString(2);
			task.duedate = c.getString(3);
			
			return task;
		}
		
		return null;
	}
	
	public Cursor getCursor(){
		
		try {
			return db.query(TASK_TABLE, Task.columns, null, null, null, null, null, null);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public List<Task> listTasks(){
		Cursor c = getCursor();
//		String.valueOf(c.date)
		
		List<Task> tasks = new ArrayList<Task>();
		
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(Tasks._ID);
			int idxTitle = c.getColumnIndex(Tasks.TITLE);
			int idxDescription = c.getColumnIndex(Tasks.DESCRIPTION);
			int idxDueDate = c.getColumnIndex(Tasks.DUEDATE);
			
			do{
				
				Task task = new Task();
				
				task.id = c.getLong(idxId);
				task.title = c.getString(idxTitle);
				task.description = c.getString(idxDescription);
				task.duedate = c.getString(idxDueDate);
				
				tasks.add(task);
				
			} while (c.moveToNext());	
		}
		
		return tasks;
	}
	
	public void close(){
		if(db != null){
			db.close();
		}
	}
}
