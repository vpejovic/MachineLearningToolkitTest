package com.ubhave.machinelearningtoolkittest;

import java.util.ArrayList;

import com.ubhave.machinelearningtoolkittest.utils.BallgameRecord;
import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.mltoolkit.utils.Value;
import com.ubhave.sensormanager.data.SensorData;

import android.R.raw;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ClassifyBallgameInstanceFragment extends Fragment {

	private static final String TAG = "ClassifyBallgameInstanceFragment";
	
	private String tempValue, humidityValue, outlookValue, windValue;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classify_ballgame_instance_fragment, container, false);
        
        Spinner spinner;
        ArrayAdapter<CharSequence> adapter;

        Log.d(TAG, "Setting outlook");
        spinner = (Spinner) view.findViewById(R.id.outlook_spinner);
        adapter = ArrayAdapter.createFromResource(MLTestApplication.getContext(),
        		R.array.outlook_array, 
        		android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Object item = parent.getItemAtPosition(pos);
				outlookValue = item.toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {				
			}
        });
       
        Log.d(TAG, "Setting temperature");
        spinner = (Spinner) view.findViewById(R.id.temp_spinner);
        adapter = ArrayAdapter.createFromResource(MLTestApplication.getContext(),
        		R.array.temp_array, 
        		android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Object item = parent.getItemAtPosition(pos);
				tempValue = item.toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {				
			}
        });
        
        spinner = (Spinner) view.findViewById(R.id.humidity_spinner);
        adapter = ArrayAdapter.createFromResource(MLTestApplication.getContext(),
        		R.array.humidity_array, 
        		android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Object item = parent.getItemAtPosition(pos);
				humidityValue = item.toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {				
			}
        });
        
        spinner = (Spinner) view.findViewById(R.id.wind_spinner);
        adapter = ArrayAdapter.createFromResource(MLTestApplication.getContext(),
        		R.array.wind_array, 
        		android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Object item = parent.getItemAtPosition(pos);
				windValue = item.toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {				
			}
        });
        
        Button classify = (Button) view.findViewById(R.id.classify_instance_button);
        classify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				classifyDay();
			}
		});
        		
		return view;
        		
	}
	
	private void classifyDay () {
		
		BallgameRecord rawInstance = new BallgameRecord();
		rawInstance.humidity = humidityValue;
		rawInstance.outlook = outlookValue;
		rawInstance.wind = windValue;
		rawInstance.temp = tempValue;
		
		new ClassifyTask()
		{		
			@Override
			protected void onProgressUpdate(Void[] values) {
				TextView result = (TextView) getView().findViewById(R.id.classify_ballgame_result);
				result.setText(R.string.classify_ballgame_result_classifying);
			};
			@Override
			public void onPostExecute(ArrayList<Value> a_values){
				
				TextView result = (TextView) getView().findViewById(R.id.classify_ballgame_result);
				
				if (a_values != null) {
					String out = "";
					for (Value value : a_values) {
						out += value.getValue().toString() + ", ";
					}
					result.setText(out);
				}
				else {
					result.setText(R.string.classify_activity_result_error);
				}
			}
		}.execute(Constants.CLASSIFIER_BALLGAME, rawInstance);		
	}
}
