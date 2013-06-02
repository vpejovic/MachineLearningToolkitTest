package com.ubhave.machinelearningtoolkittest.sense;

import android.os.AsyncTask;
import android.util.Log;

import com.ubhave.machinelearningtoolkittest.MLTestApplication;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.ESSensorManager;
import com.ubhave.sensormanager.config.SensorConfig;
import com.ubhave.sensormanager.data.SensorData;

public class SampleOnceTask extends AsyncTask<Void, Void, SensorData>
{
	private static final String TAG = "SampleOnceTask";

	private final ESSensorManager sensorManager;
	private final int sensorType;

	public SampleOnceTask(int sensorType) throws ESException
	{
		this.sensorType = sensorType;
		sensorManager = ESSensorManager.getSensorManager(MLTestApplication.getContext());
		long millis = 1000;
        //sensorManager.setSensorConfig(sensorType, SensorConfig.SENSE_WINDOW_LENGTH_MILLIS, millis);
	}

	@Override
	protected SensorData doInBackground(Void... params)
	{
		try
		{
			Log.d(TAG, "Sampling from sensor");
			return sensorManager.getDataFromSensor(sensorType);
		}
		catch (ESException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
