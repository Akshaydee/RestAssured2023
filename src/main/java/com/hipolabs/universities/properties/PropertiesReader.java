package com.hipolabs.universities.properties;

import java.io.IOException;
import java.util.Properties;

public class PropertiesReader 
{
	public static Properties getProperties() 
	{
		var appPropsStream = PropertiesReader.class.getClassLoader().getResourceAsStream("application.properties");
		Properties appProps = new Properties();
		try 
		{
			appProps.load(appPropsStream);
		} catch (IOException e) 
		{
			throw new RuntimeException(e);
		}
		return appProps;
	}
/*
public class DumpStackTraceDemo 
{ 
    public static void main(String[] args) 
    {
        methodA(); 
    } 

    public static void methodA() 
    {
        try 
	{
            int num1 = 5/0; // java.lang.ArithmeticException: divide by zero
        }
        catch (Exception e) 
	{
            e.printStackTrace();
        }
    }
}
*/
}
