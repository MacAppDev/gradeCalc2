package com.weight.generator;

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CourseItemAdapter extends ArrayAdapter<CourseItem>{
	
	final double NULL_VALUE = -1.;
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
		TextView itemRowWeight = (TextView) itemView.findViewById(R.id.tvDesired);
		
		String itemName = courseItem.itemName.toString();
		double itemAchievedGrade = courseItem.itemAchievedGrade;
		String itemAchievedGradeString;
		double itemPercentWorth = courseItem.itemPercentWorth;
		String itemPercentWorthString;
		double itemNeededGrade = courseItem.itemNeededGrade;
		
		if (itemAchievedGrade == NULL_VALUE) {
			if (itemNeededGrade != NULL_VALUE) {
				itemAchievedGradeString = String.format("%.1f", itemNeededGrade);
				if (itemNeededGrade > 100)
					itemRowGrade.setTextColor(Color.RED);
				else
					itemRowGrade.setTextColor(Color.BLUE);
			}
			else
				itemAchievedGradeString = "";
		}
		else {
			itemAchievedGradeString = String.valueOf(itemAchievedGrade);
		}

		itemPercentWorthString = (itemPercentWorth == NULL_VALUE)? "" : 
			String.valueOf(itemPercentWorth);
		
		// Assign the appropriate data from item object above
		
		itemRowText.setText(itemName);
		itemRowGrade.setText(itemAchievedGradeString);
		itemRowWeight.setText(itemPercentWorthString);
		
		return itemView;
	}
}
