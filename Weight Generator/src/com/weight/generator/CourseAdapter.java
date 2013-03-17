package com.weight.generator;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CourseAdapter extends ArrayAdapter<Course>{
	
	int resource;
	String response;
	Context context;
	
	// Initialize adapter
	public CourseAdapter(Context context, int resource, List<Course> courses) {
		super(context, resource, courses);
		this.resource = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout itemView;
		//Gets the current item object
		Course course = getItem(position);
		
		//Inflate the view
		if(convertView == null) {
			itemView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi; 
			vi = (LayoutInflater)getContext().getSystemService(inflater);
			vi.inflate(resource, itemView, true);
		}
		else {
			itemView = (LinearLayout) convertView;
		}
		
		// Get the text boxes from the row
		TextView courseRowName = (TextView) itemView.findViewById(R.id.tvName);
		TextView courseRowGoal = (TextView) itemView.findViewById(R.id.tvDesired);
		TextView courseRowGrade = (TextView) itemView.findViewById(R.id.tvAvg);
		
		// Assign the appropriate data from item object above
		
		courseRowName.setText(course.courseName.toString());
		courseRowGoal.setText(String.valueOf(course.courseGoal));
		courseRowGrade.setText(String.format("%.2f", course.courseGrade));
		
		return itemView;
	}
}
