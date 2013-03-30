package com.weight.generator;

public class CourseItem {
		
	// Fields
	final int NULL_VALUE = -1;
	String itemName;
	double itemPercentWorth;
	double itemAchievedGrade;
	double itemNeededGrade;
	boolean isCalcNeeded;
	
	// Overloaded
	CourseItem(String name, double worth, double achieved) {
		this.itemName = name;
		this.itemPercentWorth = worth;
		this.itemAchievedGrade = achieved;
		this.itemNeededGrade = NULL_VALUE;
		this.isCalcNeeded = false;
	}

}
