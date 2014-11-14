package com.example.todomanager;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter {

	private Context context;
	private List<Task> list;

	public TaskListAdapter(Context context, List<Task> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Task t = list.get(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.activity_task_list, null);

		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(t.title);

		TextView description = (TextView) view.findViewById(R.id.description);
		description.setText(t.description);

		TextView dueDate = (TextView) view.findViewById(R.id.date);
		dueDate.setText(String.valueOf(t.duedate));

		return view;
	}

}
