/**
 * CourseItemDialog.java
 *
 * <h4>Description</h4>
 *
 * Custom class for course item add/modify dialog
 * Extends DialogFragment and supports v4 backward-compatibility
 * 
 * <h4>Notes</h4>
 *
 * <h4>References</h4>
 *
 *
 * @authors      JS
 *
 */

package com.weight.generator;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CourseItemDialog extends DialogFragment  {
	
	// Declare fields for user input text boxes
	private EditText itemNameEditText;
	private EditText itemWeightEditText;
	private EditText itemGradeEditText;
	private Button cancelButton;
	private Button saveButton;
	
	public interface EditNameDialogListener {
		void onFinishEditDialog(String inputText);
	}
	public CourseItemDialog() {
		// Empty constructor required for DialogFragment
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog, container);
		getDialog().setTitle("Change Course Item"); // TODO Use a resource for the dialog title
		
		itemNameEditText = (EditText) view.findViewById(R.id.etAssName);
//		// Show keyboard automatically
		itemNameEditText.requestFocus();
		getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		itemWeightEditText = (EditText) view.findViewById(R.id.etPercentWorth);
		itemGradeEditText = (EditText) view.findViewById(R.id.etPercentMark);
		
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
			CourseItemDialog.this.dismiss(); // Dismiss (don't save anything)
		}
	};
	
	private OnClickListener saveClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			
			// Retrieve a reference to the activity that originally invoked the dialog
			CourseItemDialogListener callingActivity = (CourseItemDialogListener) getActivity();
			
			// Generate a course item
			CourseItem newItem = new CourseItem(itemNameEditText.getText().toString(), 
												(double) Double.parseDouble(itemWeightEditText.getText().toString()), 
												(double) Double.parseDouble(itemGradeEditText.getText().toString()));
			callingActivity.AddItemToList(newItem);
			
			// TODO update course here
			// for now just dismiss
			CourseItemDialog.this.dismiss();
		}
	};
}