package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class SelectClassifierFragment extends Fragment {
	
	private View d_view = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		d_view = inflater.inflate(R.layout.select_classifier_fragment, container, false);
		
		//ListView lv = initListView();		
		//setListOnClick(lv);
		
		return d_view;
	}
	
	public void selectClassifier(View view){
		RadioGroup classifierButton = (RadioGroup) d_view.findViewById(R.id.select_classifier_radioGroup);
		if (classifierButton.getCheckedRadioButtonId() == -1){
			// TODO: There is no setError method for radio button.
			Toast.makeText(MLTestApplication.getContext(), "Please select a classifier", Toast.LENGTH_LONG);
		}
		else {
			RadioButton selectedClassifier = (RadioButton) classifierButton.findViewById(classifierButton.getCheckedRadioButtonId());
			String type = selectedClassifier.getText().toString();
			if (type.equalsIgnoreCase("naive bayes")) {
				//createActivityClassifier();
			}
			else if (type.equalsIgnoreCase("bayes net")){
				
			}
		}
	}
	
	/*protected ListView initListView()
	{
		//loadData();
		ArrayList<String> availableClassifiers = new ArrayList<String>();
		availableClassifiers.add("Naive Bayes");
		availableClassifiers.add("Bayes net");
		
		ListView lv = (ListView) d_view.findViewById(R.id.select_classifier_list);
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MLTestApplication.getContext(), android.R.layout.simple_list_item_1, availableClassifiers);
		//ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MLTestApplication.getContext(), android.R.layout.simple_list_item_1, availableClassifiers);				
		lv.setAdapter(arrayAdapter); 
		return lv;
	}
	
	protected void loadData()
	{
		ArrayList<String> details = question.getDetails();
		for (int i = 0; i < answers.size(); i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put(classifierText, d_classifiers.get(i));
			map.put(descriptionText, d_descriptions.get(i));
			data.add(map);
		}
	}
	
	protected void setListOnClick(ListView lv) {		
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				
				//TODO: Spawn new activity depending on the selected classifier.
				// 
			}
		});
	}
	*/
	
}
