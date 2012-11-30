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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewExample extends FragmentActivity implements CourseItemDialogListener {
	private ListView mainListView;
	private ArrayAdapter<CourseItem> listAdapter;
	private CourseItemAdapter arrayAdapter;
	private CourseItemDialog courseItemDialog;
	private ArrayList<CourseItem> courseItemList = new ArrayList<CourseItem>();
	
	// Listener for clicking on an item in the ListView -> triggers dialog
	private OnItemClickListener courseItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int itemIndex,
				long arg3) {
			Toast.makeText(ListViewExample.this, String.valueOf(itemIndex), Toast.LENGTH_SHORT).show();
			showCourseItemDialog(itemIndex);
		}
	};

	// ENTRY point for activity
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		mainListView = (ListView) findViewById(R.id.mainListView);
		
//		String[] courseItems = new String[] { "Add New Entry" };
//		ArrayList<String> planetList = new ArrayList<String>();
//		planetList.addAll(Arrays.asList(courseItems));
//		// Create ArrayAdapter using the planet list.
//		listAdapter = new ArrayAdapter<String>(this, R.layout.row_row,
//				planetList);
		
		CourseItem defaultItem = new CourseItem(" Add New Entry ", 0, 0, 0);
		this.AddItemToList(defaultItem); // Load in default item
		
		// Create ArrayAdapter to load into ListView using the list of course items
		arrayAdapter = new CourseItemAdapter(ListViewExample.this, R.layout.row_element, courseItemList);
//		listAdapter = new ArrayAdapter<CourseItem>(this, R.layout.row_row, courseItemList);

		// Add more items. If you passed a String[] instead of a List<String>
		// into the ArrayAdapter constructor, you must not add more items.
		// Otherwise an exception will occur.
		
		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(arrayAdapter);
		mainListView.setOnItemClickListener(courseItemClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}
	
	// Method to invoke the modify/add item dialog
	private void showCourseItemDialog(int itemIndex) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		courseItemDialog = new CourseItemDialog();
		courseItemDialog.show(fragmentManager, "Edit Item Dialog");
	}

	// Method to add item to courseItemList (from dialog); 
	// fulfills interface contract requirements
	public void AddItemToList(CourseItem newItem) {
		// Check if the item already exists
		if (!courseItemList.contains(newItem)) {
			courseItemList.add(courseItemList.size(), newItem); // Add to end
		}
		
		// TODO TEST TOAST notification
		Toast.makeText(this, newItem.itemName.toString(), Toast.LENGTH_SHORT).show();
	}
}
