//Note: I changed this to be generic and accept both CourseItems and Courses -Evan

package com.weight.generator;

public interface CourseItemDialogListener<Type> {
	
	public void AddItemToAdapter(Type newItem, int itemIndex);
	public Type GetItem(int index);

}