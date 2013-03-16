/**
 * CourseDialog.java
 *
 * <h4>Description</h4>
 *
 * Custom class for course add/modify dialog
 * Extends DialogFragment and supports v4 backward-compatibility
 * 
 * <h4>Notes</h4>
 *TODO: Make a Course Data structure (currently using CourseItem)
 *TODO: Start new intent when we save a course
 * <h4>References</h4>
 *
 *
 * @authors      JS
 *
 */

package com.weight.generator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CourseDialog extends DialogFragment {

	// Declare fields for user input text boxes
	private EditText itemNameEditText;
	private EditText itemGoalEditText;
	private EditText itemGradeEditText;
	private Button cancelButton;
	private Button saveButton;
	private Course modifyItem;
	private CourseItemDialogListener<Course> callingActivity;

	int itemIndex;

	/**
	 * Create a new instance of CourseDialog, providing "index" as an
	 * argument.
	 */
	static CourseDialog newInstance(int index) {
		CourseDialog f = new CourseDialog();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public interface EditNameDialogListener {
		void onFinishEditDialog(String inputText);
	}

	public CourseDialog() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		itemIndex = getArguments().getInt("index");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.courses_dialog, container);
		if (itemIndex == -1) // If new item
			getDialog().setTitle("New Course");
		else
			getDialog().setTitle("Change Course"); // TODO Use a resource
														// for the dialog title

		itemNameEditText = (EditText) view.findViewById(R.id.tvCourse);
		// // Show keyboard automatically
		itemNameEditText.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		itemGoalEditText = (EditText) view.findViewById(R.id.etPercentWorth);

		if (itemIndex != -1) { // if modifying an item
			callingActivity = (CourseItemDialogListener<Course>) getActivity();
			modifyItem = callingActivity.GetItem(itemIndex);
			itemNameEditText.setText(modifyItem.itemName.toString());
			if (modifyItem.itemGoal != -1)
				itemGoalEditText.setText(String
						.valueOf(modifyItem.itemGoal));
		}

		else {
			itemGoalEditText
					.setFilters(new InputFilter[] { new InputPercentFilter("0",
							"100") });
		}

		cancelButton = (Button) view.findViewById(R.id.bCancel);
		saveButton = (Button) view.findViewById(R.id.bOk);

		cancelButton.setOnClickListener(cancelClickListener);
		saveButton.setOnClickListener(saveClickListener);

		return view;
	}

	// Listener for cancel button click on dialog
	private OnClickListener cancelClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			CourseDialog.this.dismiss(); // Dismiss (don't save anything)
		}
	};

	private OnClickListener saveClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {

			// Retrieve a reference to the activity that originally invoked the
			// dialog
			callingActivity = (CourseItemDialogListener<Course>) getActivity();
			// if there is no Assignment name inserted
			if (itemNameEditText.getText().toString().trim().length() > 0) {
				
				// Generate a course item
				double itemWeight = -1;
				double itemGrade = -1;
				try {
					itemWeight = Double.parseDouble(itemGoalEditText
							.getText().toString());
				}

				catch (Exception e) {
				}

				Course newItem = new Course(itemNameEditText.getText()
						.toString(), itemWeight, 0);
				callingActivity.AddItemToAdapter(newItem, itemIndex);

				// TODO update course here
				Intent intent = new Intent(getActivity(), ListViewExample.class);
			    startActivity(intent);
				// for now just dismiss
				CourseDialog.this.dismiss();
				
			} else {
				// Prints out message if there is no Assignment name
				Toast.makeText((Context) getActivity(),
						(String) ("Please type a Name for the entry."),
						Toast.LENGTH_SHORT).show();
			}
		}
	};
}