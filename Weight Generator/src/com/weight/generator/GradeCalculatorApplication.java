package com.weight.generator;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;

public class GradeCalculatorApplication extends Application {
	
	// Constants
	final String FILENAME = "MarkMaster_0_8.data";
	
	// Fields
	final String DATA_FILENAME = "GradeCalc.data"; // TODO This should be changed to a resource
	public Map<String, Course> myCourses = new HashMap<String, Course>(); // Collection of courses mapped by name
	
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
	
	Course deleteCourse(String courseName) {
		Course tempCourse = myCourses.get(courseName);
		myCourses.remove(tempCourse);
		return tempCourse;
	}
}
