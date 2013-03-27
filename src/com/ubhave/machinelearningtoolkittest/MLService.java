package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

public class MLService extends Service {
	
	private MLService d_service;
	
	private MachineLearningManager d_manager;
	
	private static final Object lock = new Object();

	private static final String TAG = "MLService";
	
	public void onCreate()
	{
		if (d_service != null)
		{
			Log.d(TAG, "Service already started");
		}
		else
		{
			synchronized (lock)
			{
				if (d_service == null)
				{
					Log.d(TAG, "Service starting");
					super.onCreate();

					d_service = this;
					try
					{
						d_manager = MachineLearningManager.getMLManager(d_service);					
						createActivityClassifier();
					}
					catch (MLException e){
						e.printStackTrace();
					}
				}
			}
		}
	
	}
	
	/**
	 * Creates a classifier from a given JSON file. The format in which the classifier is given is:
	 * 
	 *  int type: type of classifier, can be Naive Bayes, Hoeffding Tree, etc. 
	 *  	      A list of implemented classifier types is available in the Constants class.
	 *  
	 *  Signature signature:
	 *  
	 *  	int class_index: Determines which of the features is going to be the dependent variable.
	 *  
	 *  	Feature feature[]: a list of features/attributes.  
	 * 			
	 * 			Feature feature:
	 * 		
	 * 				int feature_type: can be either NOMINAL or NUMERIC
	 * 			   
	 * 				String name: feature name.
	 * 
	 * 				String[] values: a list of possible values for a NOMINAL feature
	 * 
	 * @param filename
	 */
	//public void createClassifierFromJSON(String filename){
	//	d_manager.addClassifier(a_type, a_signature)
	//}
	
	public void createActivityClassifier(){
		
		try {
			Feature x_axis = new Feature("xaxis", Feature.NUMERIC);
			Feature y_axis = new Feature("yaxis", Feature.NUMERIC);
			Feature z_axis = new Feature("zaxis", Feature.NUMERIC);
			ArrayList<String> activityValues = new ArrayList<String>();
			activityValues.add("Sitting");
			activityValues.add("Standing");
			activityValues.add("Walking");
			Feature activity = new Feature("Activity", Feature.NOMINAL, activityValues);
			
			ArrayList<Feature> features = new ArrayList<Feature>();		
			features.add(x_axis);
			features.add(y_axis);
			features.add(z_axis);
			features.add(activity);
			Signature signature = new Signature(features, 3);
		    d_manager.addClassifier(com.ubhave.mltoolkit.utils.Constants.TYPE_NAIVE_BAYES, signature, "activity");			
		} catch (MLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void trainActivityClassifier(SensorData a_data, String a_label){
		Classifier activityClassifier = d_manager.getClassifier("activity");
		ArrayList<Instance> instances;
		
		if (a_data != null)
		{
			ArrayList<float[]> readings = ((AccelerometerData)a_data).getSensorReadings();

			instances = new ArrayList<Instance>();
									
			for (float[] sample : readings) {

				Value xValue = new Value(sample[0], Value.NUMERIC_VALUE);
				Value yValue = new Value(sample[1], Value.NUMERIC_VALUE);
				Value zValue = new Value(sample[2], Value.NUMERIC_VALUE);
				Value stateValue = new Value(a_label, Value.NOMINAL_VALUE);
				
				ArrayList<Value> instanceValues = new ArrayList<Value>();
				instanceValues.add(xValue);
				instanceValues.add(yValue);
				instanceValues.add(zValue);
				instanceValues.add(stateValue);
				
				Instance instance = new Instance(instanceValues);
				instances.add(instance);
			}
			
			try {
				activityClassifier.train(instances);
			} catch (MLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void classifyActivityInstance(SensorData a_data) {
		
		Classifier activityClassifier = d_manager.getClassifier("activity");
		
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
	
	
	public void onDestroy() {
		d_manager.saveToPersistent();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}