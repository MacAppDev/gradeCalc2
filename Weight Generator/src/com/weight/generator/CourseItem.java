package com.weight.generator;

public class CourseItem {
		
	// Fields
	String itemName;
	double itemPercentWorth;
	double itemAchievedGrade;
	
	// Overloaded
	CourseItem(String name, double worth, double achieved) {
		this.itemName = name;
		this.itemPercentWorth = worth;
		this.itemAchievedGrade = achieved;
	}
	
}
