package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;

import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.machinelearningtoolkittest.utils.LocalPersistence;
import com.ubhave.mltoolkit.MachineLearningManager;
import com.ubhave.mltoolkit.classifier.Classifier;
import com.ubhave.mltoolkit.utils.Instance;
import com.ubhave.mltoolkit.utils.Value;
import com.ubhave.sensormanager.data.pullsensor.AccelerometerData;

import android.os.AsyncTask;

public class ClassifyTask extends AsyncTask<Void, Void, Value> {

	@Override
	protected Value doInBackground(Void... params) {
		
		MachineLearningManager mlmanager = (MachineLearningManager) LocalPersistence.readObjectFromFile(MLTestApplication.getContext(), Constants.CLASSIFIER_MANAGER_FILE); 
		Classifier activityClassifier = mlmanager.getClassifier(d_activeClassifiers.get("activity"));	
				
				d_manager.getClassifier(d_activeClassifiers.get("activity"));
		
		ArrayList<Value> values = new ArrayList<Value>();
		
		if (a_data != null)
		{
			ArrayList<float[]> readings = ((AccelerometerData)a_data).getSensorReadings();
												
			for (float[] sample : readings) {

				Value xValue = new Value(sample[0], Value.NUMERIC_VALUE);
				Value yValue = new Value(sample[1], Value.NUMERIC_VALUE);
				Value zValue = new Value(sample[2], Value.NUMERIC_VALUE);
				
				ArrayList<Value> instanceValues = new ArrayList<Value>();
				instanceValues.add(xValue);
				instanceValues.add(yValue);
				instanceValues.add(zValue);
				
				Instance instance = new Instance(instanceValues);
				
				values.add(activityClassifier.classify(instance));				
			}			
		}		
	}

}
