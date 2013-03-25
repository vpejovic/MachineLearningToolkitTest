/* **************************************************
Copyright (c) 2012, University of Cambridge
Neal Lathia, neal.lathia@cl.cam.ac.uk
Kiran Rachuri, kiran.rachuri@cl.cam.ac.uk

This application was developed as part of the EPSRC Ubhave (Ubiquitous and
Social Computing for Positive Behaviour Change) Project. For more
information, please visit http://www.emotionsense.org

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
************************************************** */

package com.ubhave.machinelearningtoolkittest.json;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ubhave.machinelearningtoolkittest.MLTestApplication;

public abstract class JSONLoader
{

	public static boolean isValidJson(String jsonString)
	{
		JSONParser p = new JSONParser();
		try
		{
			p.parse(jsonString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	protected static String loadFileContents(String fn) throws IOException
	{
		try
		{
			StringBuffer fileContents = new StringBuffer();
			String line;
			// BufferedReader in = new BufferedReader(new InputStreamReader(new
			// FileInputStream(Constants.CONFIG_DIR + "/" + fn)));
			BufferedReader in = new BufferedReader(new InputStreamReader(MLTestApplication.getContext().getAssets().open("dataset.json")));
			while ((line = in.readLine()) != null)
			{
				fileContents.append(line);
			}
			in.close();
			return fileContents.toString();
		}
		catch (FileNotFoundException e)
		{
			return null;
		}
	}

}
