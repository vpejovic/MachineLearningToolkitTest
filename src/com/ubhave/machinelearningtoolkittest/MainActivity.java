package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;

import com.ubhave.machinelearningtoolkittest.sense.SampleOnceTask;

import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.data.pullsensor.AccelerometerData;
import com.ubhave.sensormanager.sensors.SensorUtils;

import android.os.Bundle;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.app.ActionBar;
import android.content.Intent;
/**
 * Test for the machine learning library. The following functionalities are available:
 * - Add a classifier of a certain type
 * - Add a training set programmatically 
 * - Load a training set from a JSON file
 * - Classify an instance read programmaticaly, or from a JSON file
 * 
 * @author veljko
 *
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	public static String ACTIVE_TAB = "activeTab";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Fragment fragment = new QuestionnaireFragment();
		//getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

		MLTestApplication.getContext().startService(new Intent(MLTestApplication.getContext(), MLService.class));
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.addTab(actionBar.newTab().setText(R.string.select_classifier_tab).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.train_classifier_tab).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.classify_instance_tab).setTabListener(this));

	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState.containsKey(ACTIVE_TAB)) {
			getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(ACTIVE_TAB));
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ACTIVE_TAB, getActionBar().getSelectedNavigationIndex());
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		Fragment fragment = null;
		if (tab.getPosition() == 0) fragment = new SelectClassifierFragment();			
//		if (tab.getPosition() == 0) fragment = new CreateClassifierFragment();			
		else if (tab.getPosition() == 1) {
			RadioGroup classifierGroup = (RadioGroup)findViewById(R.id.select_classifier_radioGroup);
			if (classifierGroup.getCheckedRadioButtonId() == R.id.select_classifier_activity){
				fragment = new TrainActivityClassifierFragment();
			}
			else {
				//TODO: Location fragment 
			}
		}			
		else if (tab.getPosition() == 2) {
			RadioGroup classifierGroup = (RadioGroup)findViewById(R.id.select_classifier_radioGroup);			
			if (classifierGroup.getCheckedRadioButtonId() == R.id.select_classifier_activity){
				fragment = new ClassifyActivityInstanceFragment();
			}
			else {
				//TODO: Location fragment
			}
		}
		
		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();	
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	public void selectClassifier(View a_view) {		
	}
	
	public void trainClassifier(View a_view) {
		try
		{
			new SampleOnceTask(SensorUtils.SENSOR_TYPE_ACCELEROMETER)
			{				
				@Override
				public void onPostExecute(SensorData a_data)
				{
					
					if (a_data != null)
					{
						ArrayList<float[]> readings = ((AccelerometerData)data).getSensorReadings();

						RadioGroup rGroup = (RadioGroup)findViewById(R.id.activity);
						RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
						String state="";
						switch(checkedRadioButton.getId()) {
					        case R.id.active:
					            state="active";
					            break;
					        case R.id.still:
					        	state="still";
					            break;
						}	
						
						String result="";//Size "+Integer.toString(readings.size())+"; ";
						
						for (float[] sample : readings) {
							if (counter < 200){
							    learner.addTrainData(sample[0], sample[1], sample[2], state);
							    //result += "Gathering... "+sample[0]+","+sample[1]+","+sample[2]+","+state+"; ";
							    result = "Gathering "+state;
							}
							else if (counter == 200){
								learner.trainClassifier();
							    //result += "Learning... "+sample[0]+","+sample[1]+","+sample[2]+","+state+"; ";
							    result = "Learning "+state;
							}
							else{
							    try {
									//result += "Classifying... "+sample[0]+","+sample[1]+","+sample[2]+", "+learner.classifyInstance(sample[0], sample[1], sample[2], state);
							    	/*if (learner.classifyInstance(sample[0], sample[1], sample[2], state)==0){
							    		result = "active";
							    	}
							    	else {
							    		result = "still";							    		
							    	}*/
							    	result = Double.toString(learner.classifyInstance(sample[0], sample[1], sample[2], state));
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									setSensorDataField(e.getMessage());
								}							
							}
							counter++;
						    updateUI(result+" "+counter);
						}
					}
					else updateUI("Null (e.g., sensor off)");
				}
			}.execute();
		}
		catch (ESException e)
		{
			e.printStackTrace();
			setSensorDataField(e.getMessage());
		}
	}
}
