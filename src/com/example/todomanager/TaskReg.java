package com.example.todomanager;

import java.util.List;

import com.example.todomanager.Task.Tasks;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.example.todomanager.EditTask;

public class TaskReg extends ListActivity{
	
	protected static final int INSERT_EDIT = 1;
	protected static final int SEARCH = 2;
	
	public static TaskRepository repository;
	
	private List<Task> tasks;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		repository = new TaskRepository(this);
		refreshList();
	}
	
	protected void refreshList(){
		tasks = repository.listTasks();
		setListAdapter(new TaskListAdapter(this, tasks));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_EDIT, 0, "Inserir novo");
//		menu.add(0, SEARCH, 0, "Buscar");
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch(item.getItemId()){
			
			case INSERT_EDIT:
				
				startActivityForResult(new Intent(this, EditTask.class), INSERT_EDIT);
				
				break;
//			
//			case SEARCH:
//				
//				startActivity(new Intent(this, SearchTask.class));
//
//				break;
		}
		return true;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){ 
		super.onListItemClick(l, v, position, id);
//		editTask(position);
		deleteTask((int)id);
		refreshList();
	}
	
	protected void editTask(int position){
		Task task = tasks.get(position);
		
		Intent it = new Intent(this, EditTask.class);
		
		it.putExtra(Tasks._ID, task.id);
		
		startActivityForResult(it, INSERT_EDIT);
	}
	
	protected void deleteTask(int id){
		TaskReg.repository.delete(id);
		TaskReg.repository.listTasks();
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent it) {
		super.onActivityResult(requestCode, resultCode, it);
		
		if(resultCode == RESULT_OK){
			refreshList();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		repository.close();
	}	
}
