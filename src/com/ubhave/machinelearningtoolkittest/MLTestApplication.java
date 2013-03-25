package com.ubhave.machinelearningtoolkittest;

import android.app.Application;
import android.content.Context;

public class MLTestApplication extends Application {
	private static MLTestApplication instance;

	public MLTestApplication()
	{
		instance = this;
	}

	public static Context getContext()
	{
		return instance;
	}
	
}
