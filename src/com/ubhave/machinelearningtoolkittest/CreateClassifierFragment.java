package com.ubhave.machinelearningtoolkittest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreateClassifierFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		
        View view = inflater.inflate(R.layout.create_classifier_fragment, container, false);

		Spinner spinner = (Spinner) getView().findViewById(R.id.choose_classifier_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MLTestApplication.getContext(),
		        R.array.classifiers_list, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		return view;
	}

}