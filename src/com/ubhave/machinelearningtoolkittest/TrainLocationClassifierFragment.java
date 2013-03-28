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

public class TrainLocationClassifierFragment extends Fragment {

	private static final String TAG = "TrainLocationClassifierFragment";

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.train_location_classifier_fragment, container, false);
		
		Button train = (Button) view.findViewById(R.id.train_classifier_button);
		train.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				senseLocation();
			}
		});
        		
		return view;
	}
	
	private void senseLocation () {
		Log.d(TAG, "senseLocation");
		
		try {
			
			new SampleOnceTask(SensorUtils.SENSOR_TYPE_LOCATION) {		
				@Override
				protected void onProgressUpdate(Void[] values) {
					TextView result = (TextView) getView().findViewById(R.id.train_location_result);
					result.setText(R.string.train_location_result_sensing);
				};
				@Override
				public void onPostExecute(SensorData a_data){

					TextView result = (TextView) getView().findViewById(R.id.train_location_result);
					
					if (a_data != null) {
						result.setText(R.string.train_location_result_sensed);
						trainLocation(a_data);
					}
					else {
						result.setText(R.string.train_location_result_error );
					}
				}
			}.execute();
		} catch (ESException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void trainLocation (SensorData a_data) {
		
		Log.d(TAG, "trainLocation");
		
		RadioGroup rGroup = (RadioGroup)getView().findViewById(R.id.location_group);
		int checkedSelection = rGroup.getCheckedRadioButtonId(); 
		if (checkedSelection == -1){
			TextView result = (TextView) getView().findViewById(R.id.train_location_result);
			result.setText(R.string.train_location_radio_error);
			return;
		}
		
		RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedSelection);
		
		String state="";		
		
		switch(checkedRadioButton.getId()) {
	        case R.id.location_home_radio:
	            state=Constants.LOCATION_HOME;
	            break;
	        case R.id.location_work_radio:
	        	state=Constants.LOCATION_WORK;
	            break;
	        case R.id.location_transit_radio:
	        	state=Constants.LOCATION_TRANSIT;
	            break;
		}
		
		if (state.length() == 0) {
			TextView result = (TextView) getView().findViewById(R.id.train_location_result);
			result.setText(R.string.train_location_radio_unknown_error);
			return;
		}
		
		Value classValue = new Value(state, Value.NOMINAL_VALUE);
		
		new TrainTask()
		{		
			@Override
			protected void onProgressUpdate(Void[] values) {
				TextView result = (TextView) getView().findViewById(R.id.train_location_result);
				result.setText(R.string.train_location_result_classifying);
			};
			@Override
			public void onPostExecute(Boolean a_outcome){
								
				TextView result = (TextView) getView().findViewById(R.id.train_location_result);
				
				if (a_outcome) {					
					result.setText(R.string.train_location_result_trained);
				}
				else {
					result.setText(R.string.train_location_result_error);
				}
			}
		}.execute(Constants.CLASSIFIER_LOCATION, a_data, classValue);
	}
	
}
