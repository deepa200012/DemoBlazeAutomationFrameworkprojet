package com.genericutilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtility {

	public String getProperty(String key) throws IOException {

		FileInputStream fis = new FileInputStream(
				"src/test/resources/config.properties");

		Properties prop = new Properties();

		prop.load(fis);

		return prop.getProperty(key);
	}
}