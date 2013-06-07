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
		
		Log.d(TAG, "Training classifier...");

		String classifierName = (String) params[0];
		SensorData data = (SensorData) params[1];
		Value label = (Value) params[2]; 
		
		Log.d(TAG, "Training classifier pass...");

		MachineLearningManager manager;
		
		Boolean outcome = false;
		
		try {
			manager = MachineLearningManager.getMLManager(MLTestApplication.getContext());
			
			Classifier classifier = manager.getClassifier(classifierName);	
			
			if (classifier == null) {
				Log.d(TAG, "Classifier is null");	
				return false;
			}
			
			if (true) //data != null)
			{
				
				Log.d(TAG, "Training classifier with name "+classifierName);
				
				if (classifierName == Constants.CLASSIFIER_BALLGAME) {
					
					Log.d(TAG, "Training ballgame classifier");
					
					ArrayList<Instance> instances = new ArrayList<Instance>();
					
					ArrayList<Value> instanceValues;
					Value outlook, temp, humidity, wind, play;	
					Instance instance;
					
					outlook = new Value("sunny", Value.NOMINAL_VALUE);
					temp = new Value("hot", Value.NOMINAL_VALUE);
					humidity = new Value("high", Value.NOMINAL_VALUE);
					wind = new Value("weak", Value.NOMINAL_VALUE);
					play = new Value("no", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("sunny", Value.NOMINAL_VALUE);
					temp = new Value("hot", Value.NOMINAL_VALUE);
					humidity = new Value("high", Value.NOMINAL_VALUE);
					wind = new Value("strong", Value.NOMINAL_VALUE);
					play = new Value("no", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("overcast", Value.NOMINAL_VALUE);
					temp = new Value("hot", Value.NOMINAL_VALUE);
					humidity = new Value("high", Value.NOMINAL_VALUE);
					wind = new Value("weak", Value.NOMINAL_VALUE);
					play = new Value("yes", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("rain", Value.NOMINAL_VALUE);
					temp = new Value("mild", Value.NOMINAL_VALUE);
					humidity = new Value("high", Value.NOMINAL_VALUE);
					wind = new Value("weak", Value.NOMINAL_VALUE);
					play = new Value("yes", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("rain", Value.NOMINAL_VALUE);
					temp = new Value("cool", Value.NOMINAL_VALUE);
					humidity = new Value("normal", Value.NOMINAL_VALUE);
					wind = new Value("weak", Value.NOMINAL_VALUE);
					play = new Value("yes", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("rain", Value.NOMINAL_VALUE);
					temp = new Value("cool", Value.NOMINAL_VALUE);
					humidity = new Value("normal", Value.NOMINAL_VALUE);
					wind = new Value("strong", Value.NOMINAL_VALUE);
					play = new Value("no", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("overcast", Value.NOMINAL_VALUE);
					temp = new Value("cool", Value.NOMINAL_VALUE);
					humidity = new Value("normal", Value.NOMINAL_VALUE);
					wind = new Value("strong", Value.NOMINAL_VALUE);
					play = new Value("yes", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("sunny", Value.NOMINAL_VALUE);
					temp = new Value("mild", Value.NOMINAL_VALUE);
					humidity = new Value("high", Value.NOMINAL_VALUE);
					wind = new Value("weak", Value.NOMINAL_VALUE);
					play = new Value("no", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("sunny", Value.NOMINAL_VALUE);
					temp = new Value("cool", Value.NOMINAL_VALUE);
					humidity = new Value("normal", Value.NOMINAL_VALUE);
					wind = new Value("weak", Value.NOMINAL_VALUE);
					play = new Value("yes", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("rain", Value.NOMINAL_VALUE);
					temp = new Value("mild", Value.NOMINAL_VALUE);
					humidity = new Value("normal", Value.NOMINAL_VALUE);
					wind = new Value("weak", Value.NOMINAL_VALUE);
					play = new Value("yes", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("sunny", Value.NOMINAL_VALUE);
					temp = new Value("mild", Value.NOMINAL_VALUE);
					humidity = new Value("normal", Value.NOMINAL_VALUE);
					wind = new Value("strong", Value.NOMINAL_VALUE);
					play = new Value("yes", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("overcast", Value.NOMINAL_VALUE);
					temp = new Value("mild", Value.NOMINAL_VALUE);
					humidity = new Value("high", Value.NOMINAL_VALUE);
					wind = new Value("strong", Value.NOMINAL_VALUE);
					play = new Value("yes", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("overcast", Value.NOMINAL_VALUE);
					temp = new Value("hot", Value.NOMINAL_VALUE);
					humidity = new Value("normal", Value.NOMINAL_VALUE);
					wind = new Value("weak", Value.NOMINAL_VALUE);
					play = new Value("yes", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					outlook = new Value("rain", Value.NOMINAL_VALUE);
					temp = new Value("mild", Value.NOMINAL_VALUE);
					humidity = new Value("high", Value.NOMINAL_VALUE);
					wind = new Value("strong", Value.NOMINAL_VALUE);
					play = new Value("no", Value.NOMINAL_VALUE);
					instanceValues = new ArrayList<Value>();
					instanceValues.add(outlook);
					instanceValues.add(temp);
					instanceValues.add(humidity);
					instanceValues.add(wind);	
					instanceValues.add(play);		
					instance = new Instance(instanceValues);
					instances.add(instance);
					
					classifier.train(instances);
					
					classifier.printClassifierInfo();
					
					outcome = true;
				}
				
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
