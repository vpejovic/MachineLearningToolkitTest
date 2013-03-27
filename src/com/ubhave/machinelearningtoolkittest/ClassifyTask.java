package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;

import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.mltoolkit.MachineLearningManager;
import com.ubhave.mltoolkit.classifier.Classifier;
import com.ubhave.mltoolkit.utils.Instance;
import com.ubhave.mltoolkit.utils.MLException;
import com.ubhave.mltoolkit.utils.Value;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.data.pullsensor.AccelerometerData;

import android.os.AsyncTask;
import android.util.Log;

public class ClassifyTask extends AsyncTask<Object, Void, ArrayList<Value>> {

	private static final String TAG = "ClassifyTask";

	@Override
	protected ArrayList<Value> doInBackground(Object... params) {
		
		String classifierName = (String) params[0];
		
		SensorData data = (SensorData) params[1];
		
		ArrayList<Value> values = new ArrayList<Value>();

		MachineLearningManager manager;
		
		try {
			manager = MachineLearningManager.getMLManager(MLTestApplication.getContext());
		
			Classifier activityClassifier = manager.getClassifier(classifierName);	
			
			
			if (data != null)
			{
				if (classifierName == Constants.CLASSIFIER_ACTIVITY) {
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
						values.add(activityClassifier.classify(instance));				
					}			
				}
				
				if (classifierName == Constants.CLASSIFIER_LOCATION) {
					// TODO: location classifier should be used here
				}
				
			}		
		} catch (MLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return values;
		
	}

}
