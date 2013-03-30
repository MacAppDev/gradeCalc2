package com.weight.generator;

import java.util.ArrayList;

public class Course {
	
	// Fields
	String courseName;
	double courseGoal;
	double courseGrade;
	ArrayList<CourseItem> courseItemList;
	
	// Constant
	final int NULL_VALUE = -1;
	
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
	
	CourseItem deleteCourseItem(int itemIndex) {
		CourseItem tempItem = courseItemList.get(itemIndex);
		courseItemList.remove(itemIndex);
		this.updateCourseGrade();
		return tempItem;
	}
	
	double updateCourseGrade() {
		double totalWeight = 0.0;
		double totalGrade = 0.0;
		for (CourseItem item : courseItemList) {
			if (item.itemPercentWorth == NULL_VALUE || item.itemAchievedGrade == NULL_VALUE)
				continue;
			totalWeight += item.itemPercentWorth;
			totalGrade += item.itemAchievedGrade * item.itemPercentWorth;
		}
		
		this.courseGrade = totalGrade / totalWeight;
		return this.courseGrade;
	}
	
//	 Retrieves the total weight value for all items associated with
//	 a course. Used to ensure total weight does not exceed 100.
	double GetTotalItemWeight() {
		double totalWeight = 0.0;
		for (CourseItem item : courseItemList) {
			if (item.itemPercentWorth == NULL_VALUE)
				continue;
			totalWeight += item.itemPercentWorth;
		}
		return totalWeight;
	}
	
}
