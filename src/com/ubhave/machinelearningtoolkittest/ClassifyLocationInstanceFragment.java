package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;

import com.ubhave.machinelearningtoolkittest.sense.SampleOnceTask;
import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.mltoolkit.utils.Value;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ClassifyLocationInstanceFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classify_location_instance_fragment, container, false);
        
        Button classify = (Button) view.findViewById(R.id.classify_instance_button);
        classify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				senseLocation();
			}
		});
        		
		return view;
        		
	}
	
	private void senseLocation () {
		try {
			new SampleOnceTask(SensorUtils.SENSOR_TYPE_LOCATION)
			{		
				@Override
				protected void onProgressUpdate(Void[] values) {
					TextView result = (TextView) getView().findViewById(R.id.classify_location_result);
					result.setText(R.string.classify_location_result_sensing);
				};
				@Override
				public void onPostExecute(SensorData a_data){
					
					TextView result = (TextView) getView().findViewById(R.id.classify_location_result);
					
					if (a_data != null) {
						result.setText(R.string.classify_location_result_sensed);
						classifyLocation(a_data);
					}
					else {
						result.setText(R.string.classify_location_result_error);
					}
				}
			}.execute();
		} catch (ESException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void classifyLocation (SensorData a_data) {
		new ClassifyTask()
		{		
			@Override
			protected void onProgressUpdate(Void[] values) {
				TextView result = (TextView) getView().findViewById(R.id.classify_location_result);
				result.setText(R.string.classify_location_result_classifying);
			};
			@Override
			public void onPostExecute(ArrayList<Value> a_values){
				
				TextView result = (TextView) getView().findViewById(R.id.classify_location_result);
				
				if (a_values != null) {
					String out = "";
					for (Value value : a_values) {
						out += value.getValue().toString() + ", ";
					}
					result.setText(out);
				}
				else {
					result.setText(R.string.classify_location_result_error);
				}
			}
		}.execute(Constants.CLASSIFIER_LOCATION, a_data);		
	}
}
