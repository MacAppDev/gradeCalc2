package com.weight.generator;

import java.util.ArrayList;

public class Course {
		
	// Fields
	String courseName;
	double courseGoal;
	double courseGrade;
	ArrayList<CourseItem> courseItemList;
	
	// Overloaded
	Course(String name, double goal) {
		this.courseName = name;
		this.courseGoal = goal;
		courseItemList = new ArrayList<CourseItem>();
	}
	
	String GetCourseName() {
		return this.courseName;
	}
	
	CourseItem modifyCourseItem(int itemIndex, CourseItem courseItem) {
		CourseItem tempItem = courseItemList.set(itemIndex, courseItem);
		this.updateCourseGrade();
		return tempItem;
	}
	
	double updateCourseGrade() {
		double totalWeight = 0.0;
		double totalGrade = 0.0;
		for (CourseItem item : courseItemList) {
			totalWeight += item.itemPercentWorth;
			totalGrade += item.itemAchievedGrade * item.itemPercentWorth;
		}
		
		this.courseGrade = totalGrade / totalWeight;
		return this.courseGrade;
	}
	
}
