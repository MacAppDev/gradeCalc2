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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CourseDialog extends DialogFragment {

	// Declare fields for user input text boxes
	private AutoCompleteTextView itemNameAutoComplete;
	private EditText itemGoalEditText;  
	private Button cancelButton;
	private Button saveButton;
	private Course modifyCourse;
	private CourseItemDialogListener<Course> callingActivity;
	private ArrayAdapter<String> autoCompleteAdapter;

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

		// Set the autocomplete options
		autoCompleteAdapter = new ArrayAdapter<String>(this.getActivity(), 
				android.R.layout.simple_dropdown_item_1line, LoadAutoCompleteOptions());
		itemNameAutoComplete = (AutoCompleteTextView) view.findViewById(R.id.acCourse);
		itemNameAutoComplete.setAdapter(autoCompleteAdapter);
		// // Show keyboard automatically
		itemNameAutoComplete.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		itemGoalEditText = (EditText) view.findViewById(R.id.etPercentWorth);

		callingActivity = (CourseItemDialogListener<Course>) getActivity();
		modifyCourse = callingActivity.GetItem(itemIndex);
		if (modifyCourse != null)
		{
			itemNameAutoComplete.setText(modifyCourse.courseName.toString());
			itemGoalEditText.setText(String.format("%.1f%n", modifyCourse.courseGoal));
		}
		
		// Set input filters to limit length and prevent '\n' occurrences
		itemNameAutoComplete.setFilters(new InputFilter[] {
				new InputFilter.LengthFilter(20)});
		itemNameAutoComplete.addTextChangedListener(new TextWatcher() {
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
		
		
		itemGoalEditText.setFilters(new InputFilter[] { 
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
			CourseDialog.this.dismiss(); // Dismiss (don't save anything)
		}
	};

	private OnClickListener saveClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {

			// Retrieve a reference to the activity that originally invoked the
			// dialog
			callingActivity = (CourseItemDialogListener<Course>) getActivity();
			
			
			String courseName = itemNameAutoComplete.getText().toString().trim();
			double itemGoal = 0.;
			try {
				itemGoal = Double.parseDouble(itemGoalEditText.getText().toString());
			}
			catch (Exception e) {
			}
			
			// Ensure that necessary inputs are provided
			if (courseName.length() > 0 && itemGoal != 0.) {
				
				Course newItem = new Course(courseName, itemGoal);
				callingActivity.AddItemToAdapter(newItem, itemIndex);

				// TODO update course here

				// for now just dismiss
				CourseDialog.this.dismiss();
				
			} else {
				// Prints out message if there is no Assignment name
				Toast.makeText((Context) getActivity(),
						(String) ("Please provide a name and goal for the entry."),
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	String[] LoadAutoCompleteOptions() {
		List<String> lineList = new ArrayList<String>();
		try {
			InputStream seedDataStream = getActivity().getAssets().open("CourseSubjects.data");
			InputStreamReader inputStreamReader = new InputStreamReader(seedDataStream);
			BufferedReader inputBufferedReader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = inputBufferedReader.readLine()) != null)
				lineList.add(line);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Exception", e.toString());
			e.printStackTrace();
		}
		return lineList.toArray(new String[lineList.size()]);
		
	}
}