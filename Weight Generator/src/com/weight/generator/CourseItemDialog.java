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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CourseItemDialog extends DialogFragment {

	// Declare fields for user input text boxes
	private EditText itemNameEditText;
	private EditText itemWeightEditText;
	private EditText itemGradeEditText;
	private Button cancelButton;
	private Button saveButton;
	private CourseItem modifyItem;
	private CourseItemDialogListener<CourseItem> callingActivity;
	
	final int NULL_VALUE = -1;

	int itemIndex;
	double totalItemWeight;

	/**
	 * Create a new instance of CourseItemDialog, providing "index" as an
	 * argument.
	 */
	static CourseItemDialog newInstance(int index, double totalItemWeight) {
		CourseItemDialog f = new CourseItemDialog();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		args.putDouble("totalItemWeight", totalItemWeight);
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
		totalItemWeight = getArguments().getDouble("totalItemWeight");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog, container);

		itemNameEditText = (EditText) view.findViewById(R.id.etAssName);
		// // Show keyboard automatically
		itemNameEditText.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		itemWeightEditText = (EditText) view.findViewById(R.id.etPercentWorth);
		itemGradeEditText = (EditText) view.findViewById(R.id.etPercentMark);

		callingActivity = (CourseItemDialogListener<CourseItem>) getActivity();
		modifyItem = callingActivity.GetItem(itemIndex);
		if (modifyItem != null) {
			getDialog().setTitle("Change Course Item");
			itemNameEditText.setText(modifyItem.itemName.toString());
			if (modifyItem.itemPercentWorth != NULL_VALUE)
				itemWeightEditText.setText(String.valueOf(modifyItem.itemPercentWorth));
			if (modifyItem.itemAchievedGrade != NULL_VALUE)
			itemGradeEditText.setText(String.valueOf(modifyItem.itemAchievedGrade));
		}
		else {
			getDialog().setTitle("New Course Item");
		}

		// Set input filters to limit length and prevent '\n' occurrences
		itemNameEditText.setFilters(new InputFilter[] {
				new InputFilter.LengthFilter(20)});
		itemNameEditText.addTextChangedListener(new TextWatcher() {
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count,
	                int after) {
	      }
	        public void afterTextChanged(Editable s) {
	            for(int i = s.length(); i > 0; i--){
	                if(s.subSequence(i-1, i).toString().equals("\n"))
	                     s.replace(i-1, i, "");
	            }
	        }
	    });
		
		itemWeightEditText.setFilters(new InputFilter[] { 
				new InputPercentFilter(0.0, 100.0)
				, new InputFilter.LengthFilter(4)});
		itemGradeEditText.setFilters(new InputFilter[] {
				new InputPercentFilter(0.0, 100.0)
				, new InputFilter.LengthFilter(4)});

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

			// Retrieve a reference to the activity that originally invoked the
			// dialog
			callingActivity = (CourseItemDialogListener<CourseItem>) getActivity();
			
			// Generate a course item
			double itemWeight = NULL_VALUE;
			double itemGrade = NULL_VALUE;
			try {
				itemWeight = Double.parseDouble(itemWeightEditText.getText().toString());
				itemGrade = Double.parseDouble(itemGradeEditText.getText().toString());
			}
			catch (Exception e) {
			}
			
			// Ensure that an assignment/item name has been provided
			if (itemNameEditText.getText().toString().trim().length() <= 0) {
				Toast.makeText((Context) getActivity(),
						(String) ("Please type a Name for the entry."),
						Toast.LENGTH_SHORT).show();
			}
			double maxAllowWeight = (modifyItem == null) ?
					(100 - totalItemWeight) :
						((100 - totalItemWeight) + modifyItem.itemPercentWorth);
			// Ensure that the total course weight will not be greater than 100
			if (itemWeight > maxAllowWeight) {

				Toast.makeText((Context) getActivity(), 
						(String) ("Please enter a weight up to " + 
								String.valueOf(maxAllowWeight)) + "%",
								Toast.LENGTH_SHORT).show();
			}
			
			else {
				CourseItem newItem = new CourseItem(itemNameEditText.getText()
						.toString(), itemWeight, itemGrade);
				callingActivity.AddItemToAdapter(newItem, itemIndex);
				CourseItemDialog.this.dismiss();
			}
		}
	};
	
}
