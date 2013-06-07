package com.ubhave.machinelearningtoolkittest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.mltoolkit.utils.MLException;

public class TrainBallgameClassifierFragment extends Fragment {

	private static final String TAG = "TrainBallgameClassifierFragment";

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.train_ballgame_classifier_fragment, container, false);
		
		Button train = (Button) view.findViewById(R.id.train_classifier_button);
		train.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Training ballgame classifier clicked");

				trainClassifier();
			}
		});		
		return view;
	}
	
	private void trainClassifier() {
		
		new TrainTask()
		{		
			@Override
			protected void onProgressUpdate(Void[] values) {
				TextView result = (TextView) getView().findViewById(R.id.train_ballgame_result);
				result.setText(R.string.train_ballgame_result_classifying);
			};
			
			@Override
			public void onPostExecute(Boolean a_outcome){
								
				TextView result = (TextView) getView().findViewById(R.id.train_ballgame_result);
				
				if (a_outcome) {					
					result.setText(R.string.train_ballgame_result_trained);
				}
				else {
					result.setText(R.string.train_ballgame_result_error);
				}
			}
		}.execute(Constants.CLASSIFIER_BALLGAME, null, null);
	}
	
}
