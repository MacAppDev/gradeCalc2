package com.weight.generator;

public class CourseItem {
		
	// Fields
	String itemName;
	double itemPercentWorth;
	double itemAchievedGrade;
	double itemDesiredGrade;
	
	// Constructor
	CourseItem(String name, double worth, double desired) {
		this.itemName = name;
		this.itemPercentWorth = worth;
		this.itemDesiredGrade = desired;
	}
	
	// Overloaded
	CourseItem(String name, double worth, double desired, double achieved) {
		this.itemName = name;
		this.itemPercentWorth = worth;
		this.itemDesiredGrade = desired;
		this.itemAchievedGrade = achieved;
	}
	
}
