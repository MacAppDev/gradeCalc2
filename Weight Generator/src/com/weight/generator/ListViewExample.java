/**
 * ListViewExample.java
 *
 * <h4>Description</h4>
 *
 * Activity for each course
 * Contains relevant course items
 * Items can be added, modified, or deleted by the user

 * <h4>Notes</h4>
 *
 * <h4>References</h4>
 *
 *
 * @authors      NA, MR, JS
 *
 */

package com.weight.generator;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ListViewExample extends FragmentActivity implements
		CourseItemDialogListener<CourseItem> {

	final int DEFAULT_INDEX = -1;
	final int NULL_VALUE = -1;
	private Button bAddNewItem;
	private Button bCalcNeeded;
	private ListView mainListView;
	private CourseItemAdapter courseItemAdapter;
	private CourseItemDialog courseItemDialog;
	private Course thisCourse;
	private GradeCalculatorApplication gradeCalcApp;
	private boolean isCalcNeededClicked;
	ArrayList<CourseItem> unfinishedItemList;

	// Listener for clicking on an item in the ListView -> triggers dialog
	private OnItemClickListener courseItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1,
				int currentItemIndex, long arg3) {
			ShowCourseItemDialog(currentItemIndex);
		}
	};

	// ENTRY point for activity
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_element_list);
		mainListView = (ListView) findViewById(R.id.mainListView);
		String courseName = getIntent().getStringExtra("courseName");
		gradeCalcApp = (GradeCalculatorApplication)getApplicationContext();
		thisCourse = gradeCalcApp.getCourse(courseName);
		

		// Create array adapter to load into ListView using the list of course
		// items
		courseItemAdapter = new CourseItemAdapter(ListViewExample.this,
				R.layout.course_element, thisCourse.courseItemList);

		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(courseItemAdapter);
		mainListView.setOnItemClickListener(courseItemClickListener);
		
		// Register for context menu calls to be caught
		registerForContextMenu(mainListView);

		// Set up Add new item button
		bAddNewItem = (Button) findViewById(R.id.bAddItem);
		bAddNewItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// adds new item to the list AND shows dialog for the new item
				ShowCourseItemDialog(thisCourse.courseItemList.size());
			}

		});
		
		bCalcNeeded = (Button) findViewById(R.id.bCalcNeeded);
		bCalcNeeded.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!isCalcNeededClicked)
					ActivateCalcNeeded();
				else {
					DeactivateCalcNeeded();
				}
				SetButtonStates();
			}
		});
		
		// Ensure buttons are properly activated/deactivated
		SetButtonStates();

	}
	
	// When back is pressed to return to calling activity
	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		super.onBackPressed();
		
	}
	
	@Override
	public void onStop() {
		gradeCalcApp.SaveAppData();
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		gradeCalcApp.SaveAppData();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	// Method to invoke the modify/add item dialog
	private void ShowCourseItemDialog(int itemIndex) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		double totalItemWeight = GetTotalItemWeight();
		courseItemDialog = CourseItemDialog.newInstance(itemIndex, totalItemWeight);
		courseItemDialog.show(fragmentManager, "Edit Item Dialog");
	}

	// Method to add item to courseItemList (from dialog);
	// fulfills interface contract requirements
	public void AddItemToAdapter(CourseItem newItem, int itemIndex) {
		if (isCalcNeededClicked)
			DeactivateCalcNeeded();
		// Check if the item already exists
		if (thisCourse.courseItemList.size() > itemIndex) {
			CourseItem modifyItem = courseItemAdapter.getItem(itemIndex);
			courseItemAdapter.remove(modifyItem);
			courseItemAdapter.insert(newItem, itemIndex);
		} else
			courseItemAdapter.add(newItem);
		
		thisCourse.modifyCourseItem(itemIndex, newItem);
		gradeCalcApp.modifyCourse(thisCourse.GetCourseName(), thisCourse);
		SetButtonStates();
	}

	// Allow dialog to retrieve values from item being modified
	public CourseItem GetItem(int itemIndex) {
		if (thisCourse.courseItemList.size() > itemIndex)
			return thisCourse.courseItemList.get(itemIndex);
		else
			return null;
	}
	
	// Allow dialog to retrieve total item weight for course
	public double GetTotalItemWeight() {
		return thisCourse.GetTotalItemWeight();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.mainListView) {
			AdapterView.AdapterContextMenuInfo info = 
					(AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(thisCourse.courseItemList.get(info.position).itemName);
			String[] menuItems = getResources().getStringArray(R.array.contextMenu);
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info =
				(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int menuItemIndex = item.getItemId();
		
		switch (menuItemIndex) {
		case 0:
			ShowCourseItemDialog(info.position);
			break;
		case 1:
			// Delete the item
			CourseItem deletedItem = thisCourse.deleteCourseItem(info.position);
			gradeCalcApp.modifyCourse(thisCourse.GetCourseName(), thisCourse);
			courseItemAdapter.remove(deletedItem);
			SetButtonStates();
			break;
		default:
			break;
		}
	
		return true;
	}
	
	void ActivateCalcNeeded() {
		unfinishedItemList = new ArrayList<CourseItem>();
		double totalUnfinishedWeight = 0.0;
		double totalFinishedWeightTimesGrade = 0.0;
		double totalWeight = 0.0;
		double neededGrade;
		int itemIndex;
		for (CourseItem tempItem : thisCourse.courseItemList) {
			if (tempItem.itemPercentWorth == NULL_VALUE)
				continue;
			if (tempItem.itemAchievedGrade == NULL_VALUE) {
				unfinishedItemList.add(tempItem);
				totalUnfinishedWeight += tempItem.itemPercentWorth;
			}
			else {
				totalFinishedWeightTimesGrade += 
						tempItem.itemPercentWorth*tempItem.itemAchievedGrade;
			}
			totalWeight += tempItem.itemPercentWorth;
		}
		neededGrade = (thisCourse.courseGoal - (totalFinishedWeightTimesGrade/totalWeight))/
				(totalUnfinishedWeight/totalWeight);
		for (CourseItem tempItem : unfinishedItemList) {
			itemIndex = courseItemAdapter.getPosition(tempItem);
			courseItemAdapter.remove(tempItem);
			tempItem.itemNeededGrade = neededGrade;
			courseItemAdapter.insert(tempItem, itemIndex);
			
			thisCourse.modifyCourseItem(itemIndex, tempItem);
			gradeCalcApp.modifyCourse(thisCourse.GetCourseName(), thisCourse);
		}
	}
	
	void DeactivateCalcNeeded() {
		unfinishedItemList = new ArrayList<CourseItem>();
		int itemIndex;
		for (CourseItem tempItem : thisCourse.courseItemList) {
			if (tempItem.itemNeededGrade != NULL_VALUE) {
				unfinishedItemList.add(tempItem);
			}
		}
		for (CourseItem tempItem : unfinishedItemList) {
				itemIndex = courseItemAdapter.getPosition(tempItem);
				courseItemAdapter.remove(tempItem);
				tempItem.itemNeededGrade = NULL_VALUE;
				courseItemAdapter.insert(tempItem, itemIndex);
				
				thisCourse.modifyCourseItem(itemIndex, tempItem);
		}
		gradeCalcApp.modifyCourse(thisCourse.GetCourseName(), thisCourse);
	}
	
	void SetButtonStates() {
		
		bAddNewItem.setEnabled(true);
		if (thisCourse.GetTotalItemWeight() >= 100) {
			bAddNewItem.setEnabled(false);
		}
		
		bCalcNeeded.setEnabled(false);
		isCalcNeededClicked = false;
		for (CourseItem courseItem : thisCourse.courseItemList) {
			if ((courseItem.itemAchievedGrade == NULL_VALUE) &&
			(courseItem.itemPercentWorth != NULL_VALUE)) {
				bCalcNeeded.setEnabled(true);
			}
			if (courseItem.itemNeededGrade != NULL_VALUE)
					isCalcNeededClicked = true;
		}
		if (isCalcNeededClicked)
			bCalcNeeded.setText("Show Achieved Grades");
		else
			bCalcNeeded.setText("Calculate Needed Grades");
	}
}
