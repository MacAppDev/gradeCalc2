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
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CourseListActivity extends FragmentActivity implements
		CourseItemDialogListener<Course> {

	final int DEFAULT_INDEX = -1;
	private Button bAddNewItem;
	private Course newItem;
	private ListView mainListView;
	private CourseAdapter courseAdapter;
	private CourseDialog courseDialog;
	private ArrayList<Course> courseItemList = new ArrayList<Course>();

	// Listener for clicking on an item in the ListView -> triggers dialog
	private OnItemClickListener courseItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1,
				int currentItemIndex, long arg3) {
			Toast.makeText(CourseListActivity.this,
					String.valueOf(currentItemIndex), Toast.LENGTH_SHORT)
					.show();
			showCourseDialog(currentItemIndex);
		}
	};

	// ENTRY point for activity
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list);
		mainListView = (ListView) findViewById(R.id.mainListView);

		Course defaultItem = new Course(" Add New Entry ", 0, 0);

		// Create array adapter to load into ListView using the list of course
		// items
		courseAdapter = new CourseAdapter(CourseListActivity.this,
				R.layout.course_element, courseItemList);

		this.AddItemToAdapter(defaultItem, DEFAULT_INDEX); // Load in default
		// item

		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(courseAdapter);
		mainListView.setOnItemClickListener(courseItemClickListener);

		// Set up Add new item button
		bAddNewItem = (Button) findViewById(R.id.addItem);
		bAddNewItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// adds new item to the list AND shows dialog for the new item
				showCourseDialog(DEFAULT_INDEX);
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

	// Method to add item to courseItemList (from dialog);
	// fulfills interface contract requirements
	public void AddItemToAdapter(Course newItem, int itemIndex) {
		// Check if the item already exists
		if (itemIndex != DEFAULT_INDEX) {
			Course modifyItem = courseAdapter.getItem(itemIndex);
			courseAdapter.remove(modifyItem);
			courseAdapter.insert(newItem, itemIndex);
		} else
			courseAdapter.add(newItem);

		// if (!courseItemList.contains(newItem)) {
		// courseItemList.add(courseItemList.size(), newItem); // Add to end
		// }
		//
		// else {
		// // Modify the current item
		// int position = courseItemList.indexOf(newItem);
		// courseItemList.remove(newItem);
		// courseItemList.add(position, newItem);
		// }

		// TODO TEST TOAST notification
		//Toast.makeText(this, newItem.itemName.toString(), Toast.LENGTH_SHORT)
		//		.show();
	}

	// Allow dialog to retrieve values from item being modified
	public Course GetItem(int index) {
		return courseItemList.get(index);
	}
}

