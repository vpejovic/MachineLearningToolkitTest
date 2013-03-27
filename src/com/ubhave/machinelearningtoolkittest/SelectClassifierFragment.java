package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.mltoolkit.MachineLearningManager;
import com.ubhave.mltoolkit.classifier.Classifier;
import com.ubhave.mltoolkit.utils.Feature;
import com.ubhave.mltoolkit.utils.MLException;
import com.ubhave.mltoolkit.utils.Signature;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class SelectClassifierFragment extends Fragment {

	private static final String TAG = "SelectClassifierFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.select_classifier_fragment, container, false);
		
		Button classify = (Button) view.findViewById(R.id.select_classifier_button);
		classify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectClassifier();
			}
		});
        		
		return view;
	}
	
	public void selectClassifier(){
		
		RadioGroup rGroup = (RadioGroup)getView().findViewById(R.id.select_classifier_radioGroup);
		int checkedSelection = rGroup.getCheckedRadioButtonId(); 
		if (checkedSelection == -1){
			TextView result = (TextView) getView().findViewById(R.id.select_classifier_result);
			result.setText(R.string.train_activity_radio_error);
			return;
		}
		
		RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedSelection);
		
		String state="";		
		
		switch(checkedRadioButton.getId()) {
	        case R.id.select_classifier_activity:
	            state=Constants.CLASSIFIER_ACTIVITY;
	            break;
	        case R.id.select_classifier_location:
	        	state=Constants.CLASSIFIER_LOCATION;
	            break;
		}		
		try {
			createClassifier(state);
		} catch (MLException e) {
			TextView result = (TextView) getView().findViewById(R.id.select_classifier_result);
			result.setText(R.string.select_classifier_result_instantiated);
			e.printStackTrace();
		}		
	}
	
	public void createClassifier(String a_type) throws MLException {
		MachineLearningManager manager = MachineLearningManager.getMLManager(MLTestApplication.getContext());

		if (manager.getClassifier(a_type) != null) {
			TextView result = (TextView) getView().findViewById(R.id.select_classifier_result);
			result.setText(R.string.select_classifier_result_instantiated);
			return; // a_type classifier already exists.
		}
		
		if (a_type.equals(Constants.CLASSIFIER_ACTIVITY)) {
			Feature x_axis = new Feature("xaxis", Feature.NUMERIC);
			Feature y_axis = new Feature("yaxis", Feature.NUMERIC);
			Feature z_axis = new Feature("zaxis", Feature.NUMERIC);
			ArrayList<String> activityValues = new ArrayList<String>();
			activityValues.add(Constants.ACTIVITY_SITTING);
			activityValues.add(Constants.ACTIVITY_STANDING);
			activityValues.add(Constants.ACTIVITY_WALKING);
			Feature activity = new Feature("Activity", Feature.NOMINAL, activityValues);
						
			ArrayList<Feature> features = new ArrayList<Feature>();		
			features.add(x_axis);
			features.add(y_axis);
			features.add(z_axis);
			features.add(activity);
			Signature signature = new Signature(features, 3);
			manager.addClassifier(com.ubhave.mltoolkit.utils.Constants.TYPE_NAIVE_BAYES, signature, Constants.CLASSIFIER_ACTIVITY);
			TextView result = (TextView) getView().findViewById(R.id.select_classifier_result);
			result.setText(R.string.select_classifier_result_done);
		} else if (a_type.equals(Constants.CLASSIFIER_LOCATION)) {
			// TODO			
		}
		
	}
	
}
