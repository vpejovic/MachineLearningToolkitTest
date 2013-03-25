package com.ubhave.machinelearningtoolkittest.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.Log;

import com.ubhave.machinelearningtoolkittest.utils.Constants;
import com.ubhave.mltoolkit.utils.Feature;
import com.ubhave.mltoolkit.utils.MLException;
import com.ubhave.mltoolkit.utils.Signature;

/** Reads a file that contains classifier description:
 * 
 * @author Veljko Pejovic (v.pejovic@cs.bham.ac.uk)
 *
 */

public class ClassifierDefLoader extends JSONLoader {

	private static final String CLASSIFIER = "classifier";
	private static final String TYPE = "type";
	private static final String SIGNATURE = "signature";
	private static final String CLASS_INDEX = "class_index";
	private static final String FEATURES = "features";
	private static final String FEATURE_TYPE = "feature_type";
	private static final String FEATURE_NAME = "feature_name";
	private static final String FEATURE_VALUES = "feature_values";

	
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
	 */
	public static Signature loadClassifier() {
		
		try {
			
			String rawJSON = loadFileContents(Constants.CLASSIFIER_JSON_FILE);
			
			if (rawJSON != null) {
				
				JSONParser p = new JSONParser();
				
				JSONObject data = (JSONObject) p.parse(rawJSON);

				JSONObject c = (JSONObject) data.get(CLASSIFIER);			
				
				int type = ((Integer) c.get(TYPE)).intValue();
				
				JSONObject signature = (JSONObject) c.get(SIGNATURE);
								
				int classIndex = ((Integer) signature.get(CLASS_INDEX)).intValue();
				
				JSONArray featuresJSON = (JSONArray) signature.get(FEATURES);
								
				ArrayList<Feature> features = new ArrayList<Feature>();
								
				for (Object featureJSONdata : featuresJSON)
				{
					JSONObject featureJSON = (JSONObject) featureJSONdata;
					
					int featureType = ((Integer) featureJSON.get(FEATURE_TYPE)).intValue();
							
					String featureName = (String) featureJSON.get(FEATURE_NAME);
					
					if (featureType == com.ubhave.mltoolkit.utils.Feature.NOMINAL) {
						
						ArrayList<String> featureValues = new ArrayList<String>();
						
						JSONArray featureValuesJSON = (JSONArray) featureJSON.get(FEATURE_VALUES);
						
						for (int i = 0; i < featureValuesJSON.size(); i++){
							featureValues.add((String) featureValuesJSON.get(i));
						}
						
						Feature feature = new Feature(featureName, featureType, featureValues);
						
						features.add(feature);
					}
					
				}
				
				return new Signature(features, classIndex);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		} catch (MLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
