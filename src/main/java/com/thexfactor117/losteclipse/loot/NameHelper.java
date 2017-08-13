package com.thexfactor117.losteclipse.loot;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.google.common.collect.Lists;

/**
 * 
 * @author TheXFactor117
 *
 */
public class NameHelper 
{
	public static String getPrefix(String property)
	{
		try
		{
			return NameHelper.readNameFile("prefixes", property);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String getType(String property)
	{
		try
		{
			return NameHelper.readNameFile("types", property);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String getSuffix(String property)
	{
		try
		{
			return NameHelper.readNameFile("suffixes", property);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return "";
	}
	
	private static String readNameFile(String filePath, String property) throws IOException
	{
		InputStreamReader in = new InputStreamReader(NameHelper.class.getClassLoader().getResourceAsStream("assets/losteclipse/names/" + filePath + ".txt"), "UTF-8");
		Properties props = new Properties();
		props.load(in);
		Enumeration<?> e = props.propertyNames();
		List<String> list = Lists.newArrayList();
		
		while(e.hasMoreElements())
		{
			String key = (String) e.nextElement();
			
			if (key != null && key.contains(property))
			{
				list.add(props.getProperty(key));
			}
		}
		
		in.close();
		
		return list.size() > 0 ? list.get((int) (Math.random() * list.size())) : "Error";
	}
}
