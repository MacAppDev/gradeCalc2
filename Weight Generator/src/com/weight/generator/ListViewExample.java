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
	private Button bAddNewItem;
	private ListView mainListView;
	private CourseItemAdapter courseItemAdapter;
	private CourseItemDialog courseItemDialog;
	private Course thisCourse;
	private GradeCalculatorApplication gradeCalcApp;

	// Listener for clicking on an item in the ListView -> triggers dialog
	private OnItemClickListener courseItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1,
				int currentItemIndex, long arg3) {
			showCourseItemDialog(currentItemIndex);
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
				showCourseItemDialog(thisCourse.courseItemList.size());
			}

		});
		
		// Set up context menu for editing / 
		

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
	private void showCourseItemDialog(int itemIndex) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		courseItemDialog = CourseItemDialog.newInstance(itemIndex);
		courseItemDialog.show(fragmentManager, "Edit Item Dialog");
	}

	// Method to add item to courseItemList (from dialog);
	// fulfills interface contract requirements
	public void AddItemToAdapter(CourseItem newItem, int itemIndex) {
		// Check if the item already exists
		if (thisCourse.courseItemList.size() > itemIndex) {
			CourseItem modifyItem = courseItemAdapter.getItem(itemIndex);
			courseItemAdapter.remove(modifyItem);
			courseItemAdapter.insert(newItem, itemIndex);
		} else
			courseItemAdapter.add(newItem);
		
		thisCourse.modifyCourseItem(itemIndex, newItem);
		gradeCalcApp.modifyCourse(thisCourse.GetCourseName(), thisCourse);
	}

	// Allow dialog to retrieve values from item being modified
	public CourseItem GetItem(int itemIndex) {
		if (thisCourse.courseItemList.size() > itemIndex)
			return thisCourse.courseItemList.get(itemIndex);
		else
			return null;
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
			showCourseItemDialog(info.position);
			break;
		case 1:
			// Delete the item
			CourseItem deletedItem = thisCourse.deleteCourseItem(info.position);
			gradeCalcApp.modifyCourse(thisCourse.GetCourseName(), thisCourse);
			courseItemAdapter.remove(deletedItem);
			break;
		default:
			break;
		}
	
		return true;
	}
}
