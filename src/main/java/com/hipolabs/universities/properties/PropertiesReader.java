package com.hipolabs.universities.properties;

import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
	public static Properties getProperties() {
		var appPropsStream = PropertiesReader.class.getClassLoader().getResourceAsStream("application.properties");
		Properties appProps = new Properties();
		try {
			appProps.load(appPropsStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return appProps;
	}
}
