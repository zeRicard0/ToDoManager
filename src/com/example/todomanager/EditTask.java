package com.example.todomanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.todomanager.Task.Tasks;

public class EditTask extends Activity {

	static final int RESULT_SAVE = 1;
	static final int RESULT_DELETE = 2;

	private EditText titleField;
	private EditText descriptionField;
	private EditText dateField;
	private Long id;

	@Override
	protected void onCreate(Bundle icicle){
		super.onCreate(icicle);
		
		setContentView(R.layout.edit_task_form);
		
		titleField = (EditText) findViewById(R.id.titleField);
		descriptionField = (EditText) findViewById(R.id.descriptionField);
		dateField = (EditText) findViewById(R.id.dateField);
		
		id = null;
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null){
			id = extras.getLong(Tasks._ID);
			
			if(id != null){
				Task t = searchTask(id);
				titleField.setText(t.title);
				descriptionField.setText(t.description);
				dateField.setText(String.valueOf(t.duedate));
			}	
		}
		
		ImageButton cancelButton = (ImageButton) findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				
				finish();
			}
		});
		
		ImageButton saveButton = (ImageButton) findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
			save();				
			}
		});
		
		ImageButton deleteButton = (ImageButton) findViewById(R.id.deleteButton);
		
		if(id == null){
			deleteButton.setVisibility(View.INVISIBLE);
		} else {
		
			deleteButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					delete();
					
				}
			});
			
		}
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		setResult(RESULT_CANCELED);
		finish();
	}
	
	public void save(){
		String date = "";
		
		Task task = new Task();
		
		if(id != null){
			task.id = id;
		}
		
		task.title = titleField.getText().toString();
		task.description = descriptionField.getText().toString();
		task.duedate = dateField.getText().toString();
		
		saveTask(task);
		
		setResult(RESULT_OK, new Intent());
		
		finish();		
	}
	
	public void delete(){
		if (id != null){
			deleteTask(id);
		}
		
		setResult(RESULT_OK, new Intent());
		finish();	
	}
	
	protected Task searchTask(long id){
		return TaskReg.repository.searchTask(id);
	}
	
	protected void saveTask(Task task){
		TaskReg.repository.save(task);
	}
	
	protected void deleteTask(long id){
		TaskReg.repository.delete(id);
	}
	
}
