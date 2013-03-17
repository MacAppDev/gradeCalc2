/**
 * CourseListActivity.java
 *
 * <h4>Description</h4>
 *
 * Activity for each course
 * Contains relevant course items
 * Items can be added, modified, or deleted by the user

 * <h4>Notes</h4>

 * <h4>References</h4>
 *
 *
 * @authors      NA, MR, JS
 *
 */

package com.weight.generator;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CourseListActivity extends FragmentActivity implements
		CourseItemDialogListener<Course> {

	final int INTENT_REQUEST_CODE = 0;
	private Button bAddNewItem;
	private ListView mainListView;
	private CourseAdapter courseAdapter;
	private CourseDialog courseDialog;
	private ArrayList<Course> courseList = new ArrayList<Course>();
	private GradeCalculatorApplication gradeCalcApp;

	// Listener for clicking on an item in the ListView -> launches the corresponding
	// activity to view/edit course items
	private OnItemClickListener courseItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1,
				int currentItemIndex, long arg3) {
			
			// Launch the course item list activity
			Intent courseDetailsActivity = new Intent(CourseListActivity.this, ListViewExample.class);
			courseDetailsActivity.putExtra("courseName", courseList.get(currentItemIndex).GetCourseName());
		    startActivityForResult(courseDetailsActivity, INTENT_REQUEST_CODE);
		}
	};
	
	// Listener for long click on an item in the ListView -> launches course dialog
	private OnItemLongClickListener courseItemLongClickListener = new OnItemLongClickListener() {
		public boolean onItemLongClick(AdapterView<?> arg0, View arg,
				int currentItemIndex, long arg3) {
			
			showCourseDialog(currentItemIndex);
			return true;
		}
	};

	// ENTRY point for activity
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list);
		mainListView = (ListView) findViewById(R.id.mainListView);
		gradeCalcApp = (GradeCalculatorApplication)getApplicationContext();

		// Create array adapter to load into ListView using the list of course
		// items
		courseAdapter = new CourseAdapter(CourseListActivity.this,
				R.layout.course_element, courseList);

		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(courseAdapter);
		mainListView.setOnItemClickListener(courseItemClickListener);
		mainListView.setOnItemLongClickListener(courseItemLongClickListener);

		// Set up Add new item button
		bAddNewItem = (Button) findViewById(R.id.addItem);
		bAddNewItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// adds new item to the list AND shows dialog for the new item
				showCourseDialog(courseList.size());
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	// Method to invoke the modify/add item dialog
	private void showCourseDialog(int itemIndex) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		courseDialog = CourseDialog.newInstance(itemIndex);
		courseDialog.show(fragmentManager, "Edit Item Dialog");
	}

	// Method to add item to courseList (from dialog);
	// fulfills interface contract requirements
	public void AddItemToAdapter(Course newItem, int itemIndex) {
		// Check if the item already exists
		if (courseList.size() > itemIndex) {
			Course modifyItem = courseAdapter.getItem(itemIndex);
			courseAdapter.remove(modifyItem);
			courseAdapter.insert(newItem, itemIndex);
		} else
			courseAdapter.add(newItem);
		gradeCalcApp.modifyCourse(newItem.GetCourseName(), newItem);
	}

	// Allow dialog to retrieve values from item being modified
	public Course GetItem(int index) {
		if (courseList.size() > index)
			return courseList.get(index);
		else
			return null;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == INTENT_REQUEST_CODE) {
			if (resultCode == RESULT_OK)
				courseAdapter.notifyDataSetChanged();
		}
	}
}

