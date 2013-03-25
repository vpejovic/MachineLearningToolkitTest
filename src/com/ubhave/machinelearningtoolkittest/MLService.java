package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.mltoolkit.MachineLearningManager;
import com.ubhave.mltoolkit.utils.Feature;
import com.ubhave.mltoolkit.utils.MLException;
import com.ubhave.mltoolkit.utils.Signature;

public class MLService extends Service {
	
	private MLService d_service;
	
	private MachineLearningManager d_manager;
	
	private HashMap<String, Integer> d_activeClassifiers;
	
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
						d_activeClassifiers = new HashMap<String, Integer>();
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
			Feature activity = new Feature("activity", Feature.NOMINAL, activityValues);
			
			ArrayList<Feature> features = new ArrayList<Feature>();		
			features.add(x_axis);
			features.add(y_axis);
			features.add(z_axis);
			
			Signature signature = new Signature(features, 3);
			int classifierID = d_manager.addClassifier(com.ubhave.mltoolkit.utils.Constants.TYPE_NAIVE_BAYES, signature);
			d_activeClassifiers.put("activity", classifierID);		
			
		} catch (MLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onDestroy() {
		// TODO: here we need to call MachineLearning<Manager
		// and make sure that all classifiers are saved.
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}