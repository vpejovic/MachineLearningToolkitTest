package com.ubhave.machinelearningtoolkittest;

import com.ubhave.machinelearningtoolkittest.utils.Constants;

import android.os.Bundle;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

	private static final String TAG = "MainActivity";

	public static String ACTIVE_TAB = "activeTab";
	
	private static String activeClassifier = Constants.CLASSIFIER_ACTIVITY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.addTab(actionBar.newTab().setText(R.string.select_classifier_tab).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.train_classifier_tab).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.classify_instance_tab).setTabListener(this));
	}

	public void setClassifierType(String a_type){
		activeClassifier = a_type;
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
		else if (tab.getPosition() == 1) {
			if (activeClassifier.equals(Constants.CLASSIFIER_ACTIVITY)){
				fragment = new TrainActivityClassifierFragment();
			}
			else if (activeClassifier.equals(Constants.CLASSIFIER_LOCATION)){
				fragment = new TrainLocationClassifierFragment();
			}
			else if (activeClassifier.equals(Constants.CLASSIFIER_BALLGAME)){
				fragment = new TrainBallgameClassifierFragment();
			}
		}			
		else if (tab.getPosition() == 2) {
			if (activeClassifier.equals(Constants.CLASSIFIER_ACTIVITY)){
				fragment = new ClassifyActivityInstanceFragment();
			}
			else {
				fragment = new ClassifyLocationInstanceFragment();
			}
		}
		
		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();	
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
}
