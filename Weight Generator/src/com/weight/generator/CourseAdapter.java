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
	public CourseAdapter(Context context, int resource, List<Course> courseItems) {
		super(context, resource, courseItems);
		this.resource = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout itemView;
		//Gets the current item object
		Course courseItem = getItem(position);
		
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
		TextView itemRowText = (TextView) itemView.findViewById(R.id.tvName);
		TextView itemRowGrade = (TextView) itemView.findViewById(R.id.tvAvg);
		TextView itemRowWeight = (TextView) itemView.findViewById(R.id.tvDesired);
		
		String itemName = courseItem.itemName.toString();
		double itemAchievedGrade = courseItem.itemGrade;
		String itemAchievedGradeString;
		double itemPercentWorth = courseItem.itemGoal;
		String itemPercentWorthString;
		
		itemAchievedGradeString = (itemAchievedGrade == -1.)? "N/A" : String.valueOf(itemAchievedGrade);
		itemPercentWorthString = (itemPercentWorth == -1.)? "N/A" : String.valueOf(itemPercentWorth);
		
		// Assign the appropriate data from item object above
		
		itemRowText.setText(itemName);
		itemRowGrade.setText(itemAchievedGradeString);
		itemRowWeight.setText(itemPercentWorthString);
		
		return itemView;
	}
}
