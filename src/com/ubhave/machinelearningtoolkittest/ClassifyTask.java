package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;

import com.ubhave.machinelearningtoolkittest.utils.BallgameRecord;
import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.mltoolkit.MachineLearningManager;
import com.ubhave.mltoolkit.classifier.Classifier;
import com.ubhave.mltoolkit.utils.Instance;
import com.ubhave.mltoolkit.utils.MLException;
import com.ubhave.mltoolkit.utils.Value;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.data.pullsensor.AccelerometerData;
import com.ubhave.sensormanager.data.pullsensor.LocationData;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

public class ClassifyTask extends AsyncTask<Object, Void, ArrayList<Value>> {

	private static final String TAG = "ClassifyTask";

	@Override
	protected ArrayList<Value> doInBackground(Object... params) {
		
		String classifierName = (String) params[0];
		
		Object rawInstance =  params[1];
		
		ArrayList<Value> values = new ArrayList<Value>();

		MachineLearningManager manager;
		
		try {
			manager = MachineLearningManager.getMLManager(MLTestApplication.getContext());
		
			Classifier classifier = manager.getClassifier(classifierName);	
			
			
			if (rawInstance != null)
			{
				if (classifierName == Constants.CLASSIFIER_ACTIVITY) {
					
					SensorData data = (SensorData) rawInstance;
					
					ArrayList<float[]> readings = ((AccelerometerData)data).getSensorReadings();
									
					for (float[] sample : readings) {

						ArrayList<Value> instanceValues = new ArrayList<Value>();

						Value xValue = new Value(sample[0], Value.NUMERIC_VALUE);
						Value yValue = new Value(sample[1], Value.NUMERIC_VALUE);
						Value zValue = new Value(sample[2], Value.NUMERIC_VALUE);
						
						instanceValues.add(xValue);
						instanceValues.add(yValue);
						instanceValues.add(zValue);
						
						Instance instance = new Instance(instanceValues);
						
						
						Log.d(TAG, "Instance size: "+instanceValues.size());
						Log.d(TAG, "Values: "+sample[0]+" "+sample[1]+" "+sample[2]);
						values.add(classifier.classify(instance));				
					}			
				}
				
				else if (classifierName == Constants.CLASSIFIER_LOCATION) {
					
					SensorData data = (SensorData) rawInstance;
					
					Location location = ((LocationData)data).getLocation();					
					
					ArrayList<Instance> instances = new ArrayList<Instance>();
					
					if (location == null) return null;
					
					Log.d(TAG, "location: "+location.toString());
					
					Value latitude = new Value(location.getLatitude(), Value.NUMERIC_VALUE);
					Value longitude = new Value(location.getLongitude(), Value.NUMERIC_VALUE);
					ArrayList<Value> instanceValues = new ArrayList<Value>();
					instanceValues.add(latitude);
					instanceValues.add(longitude);
					Instance instance = new Instance(instanceValues);
					instances.add(instance);
					
					values.add(classifier.classify(instance));
					
				}
				
				else if (classifierName == Constants.CLASSIFIER_BALLGAME) {
					BallgameRecord data = (BallgameRecord) rawInstance;
					
					Value temp = new Value(data.temp, Value.NOMINAL_VALUE);
					Value humidity = new Value(data.humidity, Value.NOMINAL_VALUE);
					Value outlook = new Value(data.outlook, Value.NOMINAL_VALUE);
					Value wind = new Value(data.wind, Value.NOMINAL_VALUE);
					
					ArrayList<Value> instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);
					Instance instance = new Instance(instanceValues);
					
					values.add(classifier.classify(instance));
				}
				
			}		
		} catch (MLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return values;
		
	}

}
