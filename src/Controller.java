/**
 * 
 * @author Nick Bauer
 * @version Controller
 * WeatherApp
 * 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Timer;

import org.json.JSONException;


public class Controller 
{
	private View theView;
	private Model theModel;
	
	/**
	 * Controller
	 * 
	 * sets theView and theModel instances 
	 * @param theView from main method in Driver
	 * @param theModel from main method in Driver
	 */
	public Controller(View theView, Model theModel)
	{
		this.theView = theView;
		this.theModel = theModel;
		
		this.theView.enterListener(new enterListener());
		this.theView.inputListener(new inputListener());
	}
	
	public class enterListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			theView.doClick();			
		}
	}

	/**
	 * class addListener
	 * 
	 * adds ActionListener for "Generate Weather" JButton
	 */
	public class inputListener implements ActionListener
	{
		
		//private final Timer timer = new Timer(i, null);
		private int i = 0;
		
		//starts immedaietly when program runs
		public inputListener()
		{
			ActionListener actionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        theView.doClick();
			        i++;
			        System.out.println(i);
			      }
			    };
			//on a 10/10 Minute Timer
		    Timer timer = new Timer(600000, actionListener);
		    timer.start();
		}
		/**
		 * actionPerformed
		 * 
		 * runs data through function to generate weather data
		 * @param ActionEvent's arg
		 */
		public void actionPerformed(ActionEvent arg)
		 {
			try {
				/**
				 * TODO
				 * Controller JSON Exception being thrown for 90210
				 * 
				 */
				theModel.resetValues();
				theModel.setZipCode(theView.getZipCode());
				theView.resetValues();
				theModel.setJSONObjects();
				
				/*IOExcepotion should be used for unreachable weather data
				if(theModel.invalidZipCode())
					throw new IOException();*/
				theModel.findCityStateName();
				setValues();
				theView.setImageIcon();
				System.out.println(theModel.getIconStr());
				
			} catch (IOException e) {
				//theView.connectivityError?
				theView.resetValues();
				theView.invalidInputError();
			} catch (JSONException e) {
				System.out.println("Controller JSON Exception");
				theView.resetValues();
				theView.invalidInputError();
			} 
		}
	}
	
	public void setInput()
	{
		theModel.setZipCode(theView.getZipCode());

	}
	
	public void setValues()
	{
		theView.setTemperature(theModel.getTemperature());
		theView.setWeatherCondition(theModel.getWeatherCondition());
		theView.setWind(theModel.getWind(), theModel.getWindSpeed());
		theView.setCityName(theModel.getCityName());
		theView.setStateName(theModel.getStateName());
		theView.setSunrise(theModel.getSunrise());
		theView.setSunset(theModel.getSunset());
		theView.setIconStr(theModel.getIconStr());

	}
}