package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;
import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.mltoolkit.MachineLearningManager;
import com.ubhave.mltoolkit.utils.Feature;
import com.ubhave.mltoolkit.utils.MLException;
import com.ubhave.mltoolkit.utils.Signature;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
	        case R.id.select_classifier_ballgame:
	        	state=Constants.CLASSIFIER_BALLGAME;
	            break;	            
		}		
		try {
			((MainActivity) getActivity()).setClassifierType(state);
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
			
			//manager.addClassifier(com.ubhave.mltoolkit.utils.Constants.TYPE_ZERO_R, signature, Constants.CLASSIFIER_ACTIVITY);
			
			TextView result = (TextView) getView().findViewById(R.id.select_classifier_result);
			result.setText(R.string.select_classifier_result_done);
		} else if (a_type.equals(Constants.CLASSIFIER_LOCATION)) {
			Feature latitude = new Feature("latitude", Feature.NUMERIC);
			Feature longitude = new Feature("longitude", Feature.NUMERIC);
			ArrayList<String> locationValues = new ArrayList<String>();
			locationValues.add(Constants.LOCATION_HOME);
			locationValues.add(Constants.LOCATION_WORK);
			locationValues.add(Constants.LOCATION_TRANSIT);
			Feature location = new Feature("Location", Feature.NOMINAL, locationValues);
						
			ArrayList<Feature> features = new ArrayList<Feature>();		
			features.add(latitude);
			features.add(longitude);
			features.add(location);
			Signature signature = new Signature(features, 2);
			
			//manager.addClassifier(com.ubhave.mltoolkit.utils.Constants.TYPE_NAIVE_BAYES, signature, Constants.CLASSIFIER_ACTIVITY);
			
			manager.addClassifier(com.ubhave.mltoolkit.utils.Constants.TYPE_ZERO_R, signature, Constants.CLASSIFIER_LOCATION);
			
			TextView result = (TextView) getView().findViewById(R.id.select_classifier_result);
			result.setText(R.string.select_classifier_result_done);	
		} else if (a_type.equals(Constants.CLASSIFIER_BALLGAME)) {
			
			Log.d(TAG, "Initializing ballgame classifier");
			
			ArrayList<String> outlookValues = new ArrayList<String>();
			outlookValues.add("sunny");
			outlookValues.add("overcast");
			outlookValues.add("rain");
			Feature outlook = new Feature("outlook", Feature.NOMINAL, outlookValues);
			
			ArrayList<String> tempValues = new ArrayList<String>();
			tempValues.add("hot");
			tempValues.add("mild");
			tempValues.add("cool");
			Feature temp = new Feature("temperature", Feature.NOMINAL, tempValues);
			
			ArrayList<String> humidityValues = new ArrayList<String>();
			humidityValues.add("high");
			humidityValues.add("normal");			
			Feature humidity = new Feature("humiditiy", Feature.NOMINAL, humidityValues);
			
			ArrayList<String> windValues = new ArrayList<String>();
			windValues.add("weak");
			windValues.add("strong");			
			Feature wind = new Feature("wind", Feature.NOMINAL, windValues);
			
			ArrayList<String> playValues = new ArrayList<String>();
			playValues.add("no");
			playValues.add("yes");			
			Feature play = new Feature("play", Feature.NOMINAL, playValues);
			
			ArrayList<Feature> features = new ArrayList<Feature>();		
			features.add(outlook);
			features.add(temp);
			features.add(humidity);
			features.add(wind);
			features.add(play);
			Signature signature = new Signature(features, 4);
			
			manager.addClassifier(com.ubhave.mltoolkit.utils.Constants.TYPE_ID3, signature, Constants.CLASSIFIER_BALLGAME);
			
			TextView result = (TextView) getView().findViewById(R.id.select_classifier_result);
			result.setText(R.string.select_classifier_result_done);	
		}
		
	}
	
}
