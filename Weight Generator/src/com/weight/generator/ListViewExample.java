//Test comment, suck my dick

package com.weight.generator;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewExample extends Activity {
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	Dialog d;
	String classes[] = { "startingPoint" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		mainListView = (ListView) findViewById(R.id.mainListView);
		String[] planets = new String[] { "Add New Entry" };
		ArrayList<String> planetList = new ArrayList<String>();
		planetList.addAll(Arrays.asList(planets));
		// Create ArrayAdapter using the planet list.
		listAdapter = new ArrayAdapter<String>(this, R.layout.row_row,
				planetList);

		// Add more planets. If you passed a String[] instead of a List<String>
		// into the ArrayAdapter constructor, you must not add more items.
		// Otherwise an exception will occur.
		listAdapter.add("Ceres");
		d = new Dialog(this);
		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(listAdapter);
		mainListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				d.setTitle("New Assignment");
				d.setContentView(R.layout.dialog);
				d.show();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}
}
