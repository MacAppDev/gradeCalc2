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
import android.text.InputFilter;
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
	private EditText itemDesiredEditText;
	private Button cancelButton;
	private Button saveButton;
	private CourseItem modifyItem;
	private CourseItemDialogListener callingActivity;
	
	int itemIndex;
	
    /**
     * Create a new instance of CourseItemDialog, providing "index"
     * as an argument.
     */
    static CourseItemDialog newInstance(int index) {
        CourseItemDialog f = new CourseItemDialog();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }
	
	public interface EditNameDialogListener {
		void onFinishEditDialog(String inputText);
	}
	public CourseItemDialog() {
		// Empty constructor required for DialogFragment
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		itemIndex = getArguments().getInt("index");
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog, container);
		if (itemIndex == -1) // If new item
			getDialog().setTitle("New Course Item");
		else
			getDialog().setTitle("Change Course Item"); // TODO Use a resource for the dialog title
		
		itemNameEditText = (EditText) view.findViewById(R.id.etAssName);
//		// Show keyboard automatically
		itemNameEditText.requestFocus();
		getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		itemWeightEditText = (EditText) view.findViewById(R.id.etPercentWorth);
		itemWeightEditText.setFilters(new InputFilter[]{ new InputPercentFilter("0", "100")});
		itemGradeEditText = (EditText) view.findViewById(R.id.etPercentMark);
		itemGradeEditText.setFilters(new InputFilter[]{ new InputPercentFilter("0", "100")});
		itemDesiredEditText = (EditText) view.findViewById(R.id.etPercentDesired);
		itemDesiredEditText.setFilters(new InputFilter[] { new InputPercentFilter("0", "100")});
		
		if (itemIndex != -1) { // if modifying an item
			callingActivity = (CourseItemDialogListener) getActivity();
			modifyItem = callingActivity.GetItem(itemIndex);
			itemNameEditText.setText(modifyItem.itemName.toString());
			itemWeightEditText.setText(String.valueOf(modifyItem.itemPercentWorth));
			itemGradeEditText.setText(String.valueOf(modifyItem.itemAchievedGrade));
			itemDesiredEditText.setText(String.valueOf(modifyItem.itemDesiredGrade));
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
			CourseItemDialog.this.dismiss(); // Dismiss (don't save anything)
		}
	};
	
	private OnClickListener saveClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			
			// Retrieve a reference to the activity that originally invoked the dialog
			callingActivity = (CourseItemDialogListener) getActivity();
			
			// Generate a course item
			double itemWeight = -1;
			double itemGrade = -1;
			double itemDesired = -1;
			try {
				itemWeight = Double.parseDouble(itemWeightEditText.getText().toString());
				itemGrade = Double.parseDouble(itemGradeEditText.getText().toString());
				itemDesired = Double.parseDouble(itemDesiredEditText.getText().toString());
			}
			
			catch(Exception e) {
			}
			
			CourseItem newItem = new CourseItem(itemNameEditText.getText().toString(), 
												itemWeight,
												itemDesired,
												itemGrade);
			callingActivity.AddItemToList(newItem);
			
			// TODO update course here
			// for now just dismiss
			CourseItemDialog.this.dismiss();
		}
	};
}