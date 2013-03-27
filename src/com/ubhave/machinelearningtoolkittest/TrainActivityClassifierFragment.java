package com.ubhave.machinelearningtoolkittest;


import com.ubhave.machinelearningtoolkittest.sense.SampleOnceTask;
import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.mltoolkit.utils.Value;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TrainActivityClassifierFragment extends Fragment {

	private static final String TAG = "TrainActivityClassifierFragment";

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.train_activity_classifier_fragment, container, false);
		
		Button train = (Button) view.findViewById(R.id.train_classifier_button);
		train.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				senseAccelerometer();
			}
		});
        		
		return view;
	}
	
	private void senseAccelerometer () {
		Log.d(TAG, "senseAccelerometer");
		
		try {
			FragmentActivity activity = getActivity();
			
			new SampleOnceTask(SensorUtils.SENSOR_TYPE_ACCELEROMETER) {		
				@Override
				protected void onProgressUpdate(Void[] values) {
					TextView result = (TextView) getView().findViewById(R.id.train_activity_result);
					result.setText(R.string.train_activity_result_sensing);
				};
				@Override
				public void onPostExecute(SensorData a_data){

					TextView result = (TextView) getView().findViewById(R.id.train_activity_result);
					
					if (a_data != null) {
						result.setText(R.string.train_activity_result_sensed);
						trainAccelerometer(a_data);
					}
					else {
						result.setText(R.string.train_activity_result_error );
					}
				}
			}.execute();
		} catch (ESException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void trainAccelerometer (SensorData a_data) {
		
		Log.d(TAG, "trainAccelerometer");
		
		RadioGroup rGroup = (RadioGroup)getView().findViewById(R.id.activity_group);
		int checkedSelection = rGroup.getCheckedRadioButtonId(); 
		if (checkedSelection == -1){
			TextView result = (TextView) getView().findViewById(R.id.train_activity_result);
			result.setText(R.string.train_activity_radio_error);
			return;
		}
		
		RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedSelection);
		
		String state="";		
		
		switch(checkedRadioButton.getId()) {
	        case R.id.activity_sit_radio:
	            state=Constants.ACTIVITY_SITTING;
	            break;
	        case R.id.activity_stand_radio:
	        	state=Constants.ACTIVITY_STANDING;
	            break;
	        case R.id.activity_walk_radio:
	        	state=Constants.ACTIVITY_WALKING;
	            break;
		}
		
		if (state.length() == 0) {
			TextView result = (TextView) getView().findViewById(R.id.train_activity_result);
			result.setText(R.string.train_activity_radio_unknown_error);
			return;
		}
		
		Value classValue = new Value(state, Value.NOMINAL_VALUE);
		
		new TrainTask()
		{		
			@Override
			protected void onProgressUpdate(Void[] values) {
				TextView result = (TextView) getView().findViewById(R.id.train_activity_result);
				result.setText(R.string.train_activity_result_classifying);
			};
			@Override
			public void onPostExecute(Boolean a_outcome){
								
				TextView result = (TextView) getView().findViewById(R.id.train_activity_result);
				
				if (a_outcome) {					
					result.setText(R.string.train_activity_result_trained);
				}
				else {
					result.setText(R.string.train_activity_result_error);
				}
			}
		}.execute(Constants.CLASSIFIER_ACTIVITY, a_data, classValue);	
	}
	
}
