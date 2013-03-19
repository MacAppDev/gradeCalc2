package com.weight.generator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class GradeCalculatorApplication extends Application {
	
	// Constants
	final String FILENAME = "MarkMaster_0_8.data";
	
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
		myCourses.remove(courseName);
		return tempCourse;
	}
	
	void SaveAppData() {
		Gson gson = new Gson();
		String jsonString = gson.toJson(myCourses);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fileOutputStream.write(jsonString.getBytes());
			fileOutputStream.close();
		} catch (Exception e) {
			Log.d("Exception", e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
