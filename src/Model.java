/**
 * 
 * @author Nick Bauer
 * @version Model
 * WeatherApp
 * 
 */

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.*;
import org.apache.commons.io.IOUtils;

/**
 * TODO
 * Different API URL to get highg and low temps for the day 
 *
 * API Keys: 	211a37a59e0229f6d33358b0b22eea83
 *				7a086ad7ece3c6f5b6c05b2d58e29ea7
 *
 * Web Service URL: https://api.openweathermap.org/data/2.5/weather?q=Sterling&appid=211a37a59e0229f6d33358b0b22eea83	
 */

public class Model 
{
	private String zipCode = "";
	private String temperature = "";
	private String cityName = "";
	private String stateName = "";
	private String wind = "";
	private String sunrise = "";
	private String sunset = "";
	private String windOrientation = "";
	private String weatherCondition = "";
	private String iconStr = "";
	
	private int sunriseInt = 0;
	private int sunsetInt = 0;
	private double windSpeed = 0.0;
	private double windDegrees = 0;
	
	private boolean EST = false;
	private boolean PST = false;
	private boolean CST = false;
	private boolean MST = false;
	
	
	private JSONObject targetTemp = new JSONObject();
	private JSONObject targetWind = new JSONObject();
	//private JSONObject targetZip = new JSONObject();
	private JSONObject targetSun = new JSONObject();
	

	
	private boolean invalidZipCode = false;
	
	/**
	 * resetValues
	 * 
	 * resets all instance variables for reuse
	 */
	public void resetValues()
	{
		invalidZipCode = false;
		temperature = "";
		cityName = "";
		stateName = "";
		zipCode = "";
		wind = "";
		sunriseInt = 0;
		sunsetInt = 0;
		sunrise = "";
		sunset = "";
		windDegrees = 0;
		windOrientation = "";
		weatherCondition = "";
		iconStr = "general";
		EST = false;
		PST = false;
		CST = false;
		MST = false;

	}
	
	/**
	 * setJSONObjects
	 * 
	 * sets target JSONObject to be used to get weather data
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void setJSONObjects() throws MalformedURLException, IOException
	{
		try {
			String urlString = "https://api.openweathermap.org/data/2.5/weather?zip="+ zipCode + 
				"&units=imperial&appid=211a37a59e0229f6d33358b0b22eea83";
	        URLConnection conn = new URL(urlString).openConnection();
	        InputStream inputStream = conn.getInputStream(); 
	        @SuppressWarnings("resource")
			Scanner response = new Scanner(inputStream);
	        String str = response.useDelimiter("//A").next();
	        JSONObject urlObject = new JSONObject(str);
	        /**
	         * TODO
	         * maybe incorporate a 5-day forecast
	         */
	        targetTemp = urlObject.getJSONObject("main");
	        temperature = Double.toString(targetTemp.getDouble("temp")) + "°F";
	        targetWind = urlObject.getJSONObject("wind");
	        windDegrees = targetWind.getDouble("deg");
	        setWindOrientation();
	        wind = Double.toString(targetWind.getDouble("speed")) + " mph " + windOrientation;
	        windSpeed = targetWind.getDouble("speed");
	        targetSun = urlObject.getJSONObject("sys");
	        sunriseInt = targetSun.getInt("sunrise");
	        sunsetInt = targetSun.getInt("sunset");
	        
	        weatherCondition = urlObject.getJSONArray("weather").getJSONObject(0).get("main").toString();
	        iconStr = urlObject.getJSONArray("weather").getJSONObject(0).get("icon").toString();
	        
	        //System.out.println(target.getString("temp"));
	        
	        /*
	        target = urlObject.getJSONObject(zipCode);
	        
	        cityName = target.getString("City");
	        stateName = target.getString("State_Name");
	        */
			
			} catch (IOException e) {
				System.out.println("Model");
				iconStr = "general";
				invalidZipCode = true;
				e.getStackTrace();
		}
	}
	
	public void setWindOrientation() 
	{
		if(windDegrees >= 337.5)
			windOrientation = "North";
		else if(windDegrees < 22.5)
			windOrientation = "North";
		else if(windDegrees >= 22.5 && windDegrees < 67.5)
			windOrientation = "North-East";
		else if(windDegrees >= 67.5 && windDegrees < 112.5)
			windOrientation = "East";
		else if(windDegrees >= 112.5 && windDegrees < 157.5)
			windOrientation = "South-East";
		else if(windDegrees >= 157.5 && windDegrees < 202.5)
			windOrientation = "South";
		else if(windDegrees >= 202.5 && windDegrees < 247.5)
			windOrientation = "South-West";
		else if(windDegrees >= 247.5 && windDegrees < 292.5)
			windOrientation = "West";
		else if(windDegrees >= 292.5 && windDegrees < 337.5)
			windOrientation = "North-West";
	}

	/**
	 * findCompanyName
	 * 
	 * searches through JSONArray for company's same
	 */
	//took off throws json
	public void findCityStateName() throws IOException
	{
		try {
			InputStream is = this.getClass().getResourceAsStream("/Sources/locationLookup.json");
			String str = IOUtils.toString(is);
			System.out.println("input zip:" + zipCode);
			
			JSONObject file = new JSONObject(str);
			JSONObject targetData = file.getJSONObject(zipCode);

			cityName = targetData.getString("city");
			stateName = targetData.getString("state_name");
			System.out.println("City: " + cityName);
			System.out.println("State: " + stateName);
			convertUTC();

			
			/* DO NOT NEED PROB
			
			Iterator<String> iter = file.keys();
			boolean done = false;
			 
			while(iter.hasNext() && !done)
			{
				String key = iter.next();
				//targetZip = new JSONObject(key);
				if(key.equals(zipCode))
				{
					System.out.println("FOUND: " + key);
					done = true;
					targetZip = new JSONObject(key);
					cityName = obj.getJSONObject("pageInfo").getString("pageName");
					
					not sure yet
					/*JSONObject cityJSON = targetZip.getJSONObject("city");
					cityName = cityJSON.toString();
					System.out.println("City: " + cityName);
				}

			}*/

		  	} catch (IOException e) {
		  		invalidZipCode = true;
		  		System.out.println("Model IOException");
		  		throw new IOException();
		  		
			} catch (JSONException e) {
				invalidZipCode = true;
				System.out.println("Model JSON Exception");
		}
	}
	
	public void convertUTC()
	{
		setTimeZones();
		Date sunriseDate = new java.util.Date(sunriseInt*1000L); 
		Date sunsetDate = new java.util.Date(sunsetInt*1000L); 
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm a z"); 
		if(EST)
			sdf.setTimeZone(TimeZone.getTimeZone("EST"));
		else if(PST)
			sdf.setTimeZone(TimeZone.getTimeZone("PST"));
		else if(CST)
			sdf.setTimeZone(TimeZone.getTimeZone("CST"));
		else if(MST)
			sdf.setTimeZone(TimeZone.getTimeZone("MST"));
		sunrise = sdf.format(sunriseDate);
		sunset = sdf.format(sunsetDate);
	}
	
	public void setTimeZones()
	{
		//can use another API to search coords and find time zone
		if(stateName.equals("Alabama") || stateName.equals("Arkansas") || stateName.equals("Illinois") || 
				stateName.equals("Iowa") || stateName.equals("Kansas") || stateName.equals("Louisiana") || 
				stateName.equals("Minnesota") || stateName.equals("North Dakota") || stateName.equals("South Dakota") || 
				stateName.equals("Missouri") || stateName.equals("Oklahoma") || stateName.equals("Texas") || 
				stateName.equals("Tennessee") || stateName.equals("Mississippi") || stateName.equals("Wisconsin") || 
				stateName.equals("Nebraska") || stateName.equals("Kentucky"))
			CST = true;
		else if(stateName.equals("Washington") || stateName.equals("California") || stateName.equals("Nevada") || 
				stateName.equals("Oregon"))
			PST = true;
		else if(stateName.equals("Montana") || stateName.equals("Idaho") || stateName.equals("Wyoming") || 
				stateName.equals("Utah") || stateName.equals("Colorado") || stateName.equals("New Mexico") || 
				stateName.equals("Arizona"))
			MST = true;
		/* Rhode Island Massachusetts New Jersey Florida Delaware North Carolina Maryland South Carolina Georgia
		else if(stateName.equals("Maine") || stateName.equals("New Hampshire") || stateName.equals("Vermont") || 
				stateName.equals("New York") || stateName.equals("Pennsylvania") || stateName.equals("Michigan") || 
				stateName.equals("Ohio") || stateName.equals("Indiana") || stateName.equals("West Virginia") || 
				stateName.equals("Virginia") || stateName.equals("Rhode Island") || stateName.equals("Massachusetts") || 
				stateName.equals("New Jersey") || stateName.equals("Florida") || stateName.equals("Delaware") || 
				stateName.equals("North Carolina") || stateName.equals("Maryland") || stateName.equals("South Carolina") || 
				stateName.equals("Georgia") || stateName.equals("Connecticut"))
			EST = true;*/
			
	}
	
	public String getSunrise()
	{
		return sunrise;
	}
	
	public String getSunset()
	{
		return sunset;
	}
	
	public String getWind()
	{
		return wind;
	}
	
	public String getTemperature()
	{
		return temperature;
	}

	public String getCityName() 
	{
		return cityName;
	}

	public String getStateName() 
	{
		return stateName;
	}

	public boolean invalidZipCode() 
	{
		return invalidZipCode;
	}

	public void setZipCode(String str) 
	{
		zipCode = str;
	}
	
	public String getWeatherCondition()
	{
		return weatherCondition;
	}
	
	public String getIconStr()
	{
		return iconStr;
	}
	
	public Double getWindSpeed()
	{
		return windSpeed;
	}
	
}
