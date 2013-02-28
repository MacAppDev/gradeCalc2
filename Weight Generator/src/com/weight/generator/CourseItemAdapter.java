package com.weight.generator;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CourseItemAdapter extends ArrayAdapter<CourseItem>{
	
	int resource;
	String response;
	Context context;
	
	// Initialize adapter
	public CourseItemAdapter(Context context, int resource, List<CourseItem> courseItems) {
		super(context, resource, courseItems);
		this.resource = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout itemView;
		//Gets the current item object
		CourseItem courseItem = getItem(position);
		
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
		TextView itemRowDesired = (TextView) itemView.findViewById(R.id.tvDesired);
		
		String itemName = courseItem.itemName.toString();
		double itemAchievedGrade = courseItem.itemAchievedGrade;
		String itemAchievedGradeString;
		double itemDesiredGrade = courseItem.itemDesiredGrade;
		String itemDesiredGradeString;
		
		itemAchievedGradeString = (itemAchievedGrade == -1.)? "N/A" : String.valueOf(itemAchievedGrade);
		itemDesiredGradeString = (itemDesiredGrade == -1.)? "N/A" : String.valueOf(itemDesiredGrade);
		
		// Assign the appropriate data from item object above
		//The next three lines were causing errors when trying to run the app so they have
		//been commented out.
		
		//itemRowText.setText(itemName);
		//itemRowGrade.setText(itemAchievedGradeString);
		//itemRowDesired.setText(itemDesiredGradeString);
		
		return itemView;
	}
}
