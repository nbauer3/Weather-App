/**
 * 
 * @author Nick Bauer
 * @version View
 * WeatherApp
 * 
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * TODO
 * -use JLabels instead of JTextFields for output
 * -fix formatting to look better
 * -icon setting
 *
 */

public class View {
	
	private String input;
	private String cityStr;
	private String stateStr;
	private String windStr;
	private String conditionStr;
	private String iconStr = "";
	
	private JFrame f = new JFrame("Weather Live-Data App");

	private JPanel p = new JPanel();
	
	private JTextField inputField = new JTextField(20);
	private JLabel temp = new JLabel("");
	private JLabel weatherCondition = new JLabel("");
	private JLabel wind = new JLabel("");
	private JLabel city = new JLabel("");
	private JLabel state = new JLabel("");
	private JLabel sunrise = new JLabel("");
	private JLabel sunset = new JLabel("");
	
	private JButton jb = new JButton("Generate Weather");
	
	private ImageIcon ii = new ImageIcon(this.getClass().getResource("/Icons/general.png"));
	
	private JLabel promptInput = new JLabel("Enter Zip Code to Retrieve Weather");
	private JLabel cityLabel = new JLabel("City");
	private JLabel stateLabel = new JLabel("State");
	private JLabel tempLabel = new JLabel("Current Temperature");
	private JLabel weatherConditionLabel = new JLabel("Weather Condition");
	private JLabel windLabel = new JLabel("Wind Speed");
	private JLabel sunriseLabel = new JLabel("Sunrise");
	private JLabel sunsetLabel = new JLabel("Sunset");
	private JLabel imageLabel = new JLabel(ii);
	
	private Double windSpeed = 10.0;
	
	public View()
	{
		scaleImageIcon();
		addPanelElements();
		setFrameStyle();
		configureFrame();
	}
	
	/**
	 * setFrameStyle
	 * 
	 * setting JFrame's style
	 */
	public void setFrameStyle()
	{
		Font labelFont = new Font("Helvetica", Font.BOLD, 15);
		setLabelColors();
		promptInput.setFont(labelFont);
		tempLabel.setFont(labelFont);
		temp.setFont(labelFont);
		weatherConditionLabel.setFont(labelFont);
		weatherCondition.setFont(labelFont);
		windLabel.setFont(labelFont);
		wind.setFont(labelFont);
		cityLabel.setFont(labelFont);
		city.setFont(labelFont);
		stateLabel.setFont(labelFont);
		state.setFont(labelFont);
		sunriseLabel.setFont(labelFont);
		sunrise.setFont(labelFont);
		sunsetLabel.setFont(labelFont);
		sunset.setFont(labelFont);
		
		p.setBackground(Color.GRAY);
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
	}
	
	public void setLabelColors() 
	{
		promptInput.setForeground(Color.WHITE);
		tempLabel.setForeground(Color.WHITE);
		weatherConditionLabel.setForeground(Color.WHITE);
		windLabel.setForeground(Color.WHITE);
		stateLabel.setForeground(Color.WHITE);
		cityLabel.setForeground(Color.WHITE);
		sunriseLabel.setForeground(Color.WHITE);
		sunsetLabel.setForeground(Color.WHITE);
	}
	
	public void setCityName(String cityName) 
	{
		cityStr = cityName;
		city.setText(cityName);
	}
	
	public void setSunrise(String str) 
	{
		sunrise.setText(str);
	}
	
	public void setSunset(String str) 
	{
		sunset.setText(str);
	}

	public void setStateName(String stateName) 
	{
		stateStr = stateName;
		state.setText(stateName);
	}
	
	public void setTemperature(String str)
	{
		temp.setText(str);
	}
	
	public void setWeatherCondition(String str)
	{
		conditionStr = str;
		weatherCondition.setText(str);
	}
	
	public void setWind(String str, Double speed) 
	{
		windSpeed = speed;
		windStr = str;
		wind.setText(str);
	}
	
	/**
	 * setImageIcon
	 * 
	 * sets corresponding ImageIcon to appropriate weather condition
	 * @param num passed from Model
	 * @return desired ImageIcon
	 */
	public void setImageIcon() 
	{
		ImageIcon temp = new ImageIcon();
		if(iconStr.equals("01d") || iconStr.equals("01n"))
			temp = new ImageIcon(this.getClass().getResource("/Icons/01d.png"));
		else if(iconStr.equals("02d") || iconStr.equals("02n"))
			temp = new ImageIcon(this.getClass().getResource("/Icons/02d.png"));
		else if(iconStr.equals("03d") || iconStr.equals("03n"))
			temp = new ImageIcon(this.getClass().getResource("/Icons/03d_04d.png"));
		else if(iconStr.equals("04d") || iconStr.equals("04n"))
			temp = new ImageIcon(this.getClass().getResource("/Icons/03d_04d.png"));
		else if(iconStr.equals("09d") || iconStr.equals("09n"))
			temp = new ImageIcon(this.getClass().getResource("/Icons/09d.png"));
		else if(iconStr.equals("10d") || iconStr.equals("10n"))
			temp = new ImageIcon(this.getClass().getResource("/Icons/10d.png"));
		else if(iconStr.equals("11d") || iconStr.equals("11n"))
			temp = new ImageIcon(this.getClass().getResource("/Icons/11d.png"));
		else if(iconStr.equals("13d") || iconStr.equals("13n"))
			temp = new ImageIcon(this.getClass().getResource("/Icons/13d.png"));
		else if(iconStr.equals("50d") || iconStr.equals("50n"))
			temp = new ImageIcon(this.getClass().getResource("/Icons/50d.png"));
		
		if((iconStr.equals("01d") || iconStr.equals("01n")) && windSpeed >= 10)
			temp = new ImageIcon(this.getClass().getResource("/Icons/windAndSun.png"));
		else if((iconStr.equals("03d")  || iconStr.equals("03n") || iconStr.equals("04d") 
				|| iconStr.equals("04n")) && windSpeed >= 10)
			temp = new ImageIcon(this.getClass().getResource("/Icons/windNoSun.png"));
		
		if(iconStr.equals("general"))
			temp = new ImageIcon(this.getClass().getResource("/Icons/general.png"));
		try {
			Image img = temp.getImage();
			Image newImg = img.getScaledInstance(250, 200,  java.awt.Image.SCALE_SMOOTH); 
			ii.setImage(newImg);
			/**
			 * method works to reset JLabel
			 * 
			 * TODO
			 * if Exception is thrown, then iconStr should = general to go back to original 
			 * photo
			 * 
			 * format JLabels, HORIZONTAL not VERTICAL?
			 * 
			 */
			//not needed
			//imageLabel = new JLabel(ii);
			p.repaint();
		} catch(NullPointerException e) {
			connectivityError();
		}
	}
	
	/**
	 * scaleImageIcon
	 * 
	 * scales a weather condition ImageIcon
	 */
	public void scaleImageIcon()
	{
		try {
			Image image = ii.getImage();
			Image newimg = image.getScaledInstance(250, 200,  java.awt.Image.SCALE_SMOOTH); 
			ii = new ImageIcon(newimg);
		} catch(NullPointerException e) {
			connectivityError();
		}
	}
	
	public void setIconStr(String str)
	{
		iconStr = str;
	}
	
	public void doClick()
	{
		jb.doClick();
	}

	public void resetValues() {
		
		city.setText("");
		state.setText("");
		temp.setText("");
		weatherCondition.setText("");
		wind.setText("");
		stateStr = "";
		cityStr = "";
		iconStr = "";
		conditionStr = "";
		sunrise.setText("");
		sunset.setText("");
		windSpeed = 0.0;
	}

	public String getZipCode() 
	{
		return inputField.getText();
	}

	public void inputListener(ActionListener listener)
	{
		jb.addActionListener(listener);
	}
	
	public void enterListener(ActionListener listener)
	{
		inputField.addActionListener(listener); 
	}
	
	/**
	 * addPanelElements
	 * 
	 * adds elements to JPanel
	 */
	public void addPanelElements()
	{
		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = new Insets(10,10,10,10);
		
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		p.add(promptInput, c);
		c.gridx = 0;
		c.gridy = 1;
		p.add(inputField, c);
		c.gridx = 0;
		c.gridy = 2;
		p.add(jb, c);
		
		//c.weightx = 1.5;
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		
		/*cheap way to fill a border, not visually appealing
		c.fill = GridBagConstraints.BOTH; 
		city.setBackground(Color.WHITE); 
		city.setOpaque(true); */
		
		c.gridx = 0;
		c.gridy = 3;
		p.add(cityLabel, c);
		c.gridx = 1;
		c.gridy = 3;
		p.add(stateLabel, c);
		c.gridx = 0;
		c.gridy = 4;
		p.add(city, c);
		c.gridx = 1;
		c.gridy = 4;
		p.add(state, c);
		
		c.gridx = 0;
		c.gridy = 5;
		p.add(tempLabel, c);
		c.gridx = 1;
		c.gridy = 5;
		p.add(weatherConditionLabel, c);
		
		c.gridx = 0;
		c.gridy = 6;
		p.add(temp, c);
		c.gridx = 1;
		c.gridy = 6;
		p.add(weatherCondition, c);
		
		c.gridx = 0;
		c.gridy = 7;
		p.add(sunriseLabel, c);
		c.gridx = 1;
		c.gridy = 7;
		p.add(sunsetLabel, c);
		
		c.gridx = 0;
		c.gridy = 8;
		p.add(sunrise, c);
		c.gridx = 1;
		c.gridy = 8;
		p.add(sunset, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 9;
		p.add(windLabel, c);
		c.gridx = 0;
		c.gridy = 10;
		p.add(wind, c);
		c.gridx = 0;
		c.gridy = 11;
		p.add(new JLabel(ii), c);
		
		
		
		/*c.gridy = 3;
		p.add(cityLabel, c);
		c.gridy = 4;
		p.add(city, c);
		c.gridy = 5;
		p.add(stateLabel, c);
		c.gridy = 6;
		p.add(state, c);*/
		
		/* ORIGINAL FORMATTING
		c.gridwidth = 5;
		c.gridx = 3;
		c.gridy = 0;
		p.add(promptInput, c);
		c.gridy = 1;
		p.add(inputField, c);
		c.gridy = 2;
		p.add(jb, c);
		c.gridy = 3;
		p.add(cityLabel, c);
		c.gridy = 4;
		p.add(city, c);
		c.gridy = 5;
		p.add(stateLabel, c);
		c.gridy = 6;
		p.add(state, c);
		c.gridy = 7;
		p.add(tempLabel, c);
		c.gridy = 8;
		p.add(temp, c);
		
		c.gridy = 9;
		p.add(weatherConditionLabel, c);
		c.gridy = 10;
		p.add(weatherCondition, c);
		
		c.gridy = 11;
		p.add(windLabel, c);
		c.gridy = 12;
		p.add(wind, c);
		c.gridy = 13;
		p.add(sunriseLabel, c);
		c.gridy = 14;
		p.add(sunrise, c);
		c.gridy = 15;
		p.add(sunsetLabel, c);
		c.gridy = 16;
		p.add(sunset, c);*/

	}
	
	/**
	 * configureFrame
	 * 
	 * configures JFrame specifications
	 */
	public void configureFrame()
	{
		f.setPreferredSize(new Dimension(500,700));
		f.setMinimumSize(new Dimension(500, 650));
		f.setLocation(400,50);
		f.add(p);
    	f.pack();
    	f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	/**
	 * invalidInputError
	 * 
	 * error to be used when an invalid input occurs
	 */
	public void invalidInputError()
	{
		String str = "Error: Invalid Input,\nZip Code Does Not Exist";
		JOptionPane.showMessageDialog(new JFrame(), str, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * connectivityError
	 * 
	 * error to be used when no network is found for API
	 */
	public void connectivityError()
	{
		String str = "Error: Cannot Connect to Network,\nCheck Internet Settings";
		JOptionPane.showMessageDialog(new JFrame(), str, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
