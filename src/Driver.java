/**
 * 
 * @author Nick Bauer
 * @version Driver
 * WeatherApp
 * 
 */

public class Driver 
{
	public static void main(String[] args)
	{
		View theView = new View();
		Model theModel = new Model();
		new Controller(theView, theModel);
	}
}
