package com.weight.generator;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class GradeCalculatorApplication extends Application {
	
	final String DATA_FILENAME = "GradeCalc.data"; // TODO This should be changed to a resource
	private Map<String, Course> myCourses = new HashMap<String, Course>(); // Collection of courses mapped by name
	
	@Override
	public void onCreate() {
		super.onCreate();
		
//		SharedPreferences applicationPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//		Editor editor = applicationPreferences.edit();
		
		// TODO Initialize and load application settings (e.g. language, University grading scheme, username, etc.)
	}
	
	Course getCourse(String courseName) {
		return myCourses.get(courseName);
	}
	
	Course modifyCourse(String courseName, Course course) {
		return myCourses.put(courseName, course);
	}
}
