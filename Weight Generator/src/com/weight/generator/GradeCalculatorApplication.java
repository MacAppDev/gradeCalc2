package com.weight.generator;

import java.util.List;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class GradeCalculatorApplication extends Application {
	
	
	final String DATA_FILENAME = "GradeCalc.data"; // TODO This should be changed to a resource
	List<List<CourseItem>> GradeInfo; // Contains list of courses, each composed of a list of type CourseItem
	
	@Override
	public void onCreate() {
		super.onCreate();
		
//		SharedPreferences applicationPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//		Editor editor = applicationPreferences.edit();
		
		// TODO Initialize and load application settings (e.g. language, University grading scheme, username, etc.)
	}
}
