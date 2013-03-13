package com.weight.generator;

public interface CourseItemDialogListener {
	
	public void AddItemToAdapter(CourseItem newItem, int itemIndex);
	public CourseItem GetItem(int index);

}