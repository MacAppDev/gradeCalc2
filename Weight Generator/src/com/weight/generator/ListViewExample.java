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

public class ListViewExample extends FragmentActivity implements
		CourseItemDialogListener<CourseItem> {

	final int DEFAULT_INDEX = -1;
	private Button bAddNewItem;
	private CourseItem newItem;
	private ListView mainListView;
	private CourseItemAdapter courseItemAdapter;
	private CourseItemDialog courseItemDialog;
	private ArrayList<CourseItem> courseItemList = new ArrayList<CourseItem>();

	// Listener for clicking on an item in the ListView -> triggers dialog
	private OnItemClickListener courseItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1,
				int currentItemIndex, long arg3) {
			Toast.makeText(ListViewExample.this,
					String.valueOf(currentItemIndex), Toast.LENGTH_SHORT)
					.show();
			showCourseItemDialog(currentItemIndex);
		}
	};

	// ENTRY point for activity
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_element_list);
		mainListView = (ListView) findViewById(R.id.mainListView);

		CourseItem defaultItem = new CourseItem(" Add New Entry ", 0, 0);

		// Create array adapter to load into ListView using the list of course
		// items
		courseItemAdapter = new CourseItemAdapter(ListViewExample.this,
				R.layout.course_element, courseItemList);

		this.AddItemToAdapter(defaultItem, DEFAULT_INDEX); // Load in default
		// item

		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(courseItemAdapter);
		mainListView.setOnItemClickListener(courseItemClickListener);

		// Set up Add new item button
		bAddNewItem = (Button) findViewById(R.id.bAddItem);
		bAddNewItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// adds new item to the list AND shows dialog for the new item
				showCourseItemDialog(DEFAULT_INDEX);
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	// Method to invoke the modify/add item dialog
	private void showCourseItemDialog(int itemIndex) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		courseItemDialog = CourseItemDialog.newInstance(itemIndex);
		courseItemDialog.show(fragmentManager, "Edit Item Dialog");
	}

	// Method to add item to courseItemList (from dialog);
	// fulfills interface contract requirements
	public void AddItemToAdapter(CourseItem newItem, int itemIndex) {
		// Check if the item already exists
		if (itemIndex != DEFAULT_INDEX) {
			CourseItem modifyItem = courseItemAdapter.getItem(itemIndex);
			courseItemAdapter.remove(modifyItem);
			courseItemAdapter.insert(newItem, itemIndex);
		} else
			courseItemAdapter.add(newItem);

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
		Toast.makeText(this, newItem.itemName.toString(), Toast.LENGTH_SHORT)
				.show();
	}

	// Allow dialog to retrieve values from item being modified
	public CourseItem GetItem(int index) {
		return courseItemList.get(index);
	}
}
