package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;

import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.mltoolkit.MachineLearningManager;
import com.ubhave.mltoolkit.classifier.Classifier;
import com.ubhave.mltoolkit.utils.Feature;
import com.ubhave.mltoolkit.utils.Instance;
import com.ubhave.mltoolkit.utils.MLException;
import com.ubhave.mltoolkit.utils.Signature;
import com.ubhave.mltoolkit.utils.Value;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.data.pullsensor.AccelerometerData;
import com.ubhave.sensormanager.data.pullsensor.LocationData;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

public class TrainTask extends AsyncTask<Object, Void, Boolean> {

	private static final String TAG = "TrainTask";

	@Override
	protected Boolean doInBackground(Object... params) {
		
		String classifierName = (String) params[0];
		SensorData data = (SensorData) params[1];
		Value label = (Value) params[2]; 

		MachineLearningManager manager;
		
		Boolean outcome = false;
		
		try {
			manager = MachineLearningManager.getMLManager(MLTestApplication.getContext());
			
			Classifier classifier = manager.getClassifier(classifierName);	
			
			if (classifier == null) return false;
			
			if (data != null)
			{
				if (classifierName == Constants.CLASSIFIER_ACTIVITY) {
					ArrayList<float[]> readings = ((AccelerometerData)data).getSensorReadings();
									
					ArrayList<Instance> instances = new ArrayList<Instance>();
					
					for (float[] sample : readings) {

						ArrayList<Value> instanceValues = new ArrayList<Value>();

						Value xValue = new Value(sample[0], Value.NUMERIC_VALUE);
						Value yValue = new Value(sample[1], Value.NUMERIC_VALUE);
						Value zValue = new Value(sample[2], Value.NUMERIC_VALUE);
						
						instanceValues.add(xValue);
						instanceValues.add(yValue);
						instanceValues.add(zValue);
						instanceValues.add(label);
						Instance instance = new Instance(instanceValues);
						
						instances.add(instance);
					}			
					
					classifier.train(instances);
					
					outcome = true;
				}
				
				if (classifierName == Constants.CLASSIFIER_LOCATION) {
					Location location = ((LocationData)data).getLocation();					
					
					if (location == null) return false;
					
					Log.d(TAG, "location: "+location.toString());
					
					ArrayList<Instance> instances = new ArrayList<Instance>();
					
					// TODO: check if the location was found successfully
					
					Value latitude = new Value(location.getLatitude(), Value.NUMERIC_VALUE);
					Value longitude = new Value(location.getLongitude(), Value.NUMERIC_VALUE);
					ArrayList<Value> instanceValues = new ArrayList<Value>();
					instanceValues.add(latitude);
					instanceValues.add(longitude);
					Instance instance = new Instance(instanceValues);
					instances.add(instance);
					
					classifier.train(instances);
					
					outcome = true;
				}
				
			}
		} catch (MLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return outcome;
		
	}

}
