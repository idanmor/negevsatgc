package Utils;

import negevsatgui.MainWindow;

public interface Constants {

	//css
	public static final String CSS_PACKAGE_LOCATION = "/CssPackage/";
	public static final String CSS_LOGIN = CSS_PACKAGE_LOCATION + "logInCss.css";
	public static final String CSS_MAIN = CSS_PACKAGE_LOCATION + "mainCss.css";
	public static final String CSS_WEB_MAP = CSS_PACKAGE_LOCATION + "WebMap.css";
	public static final String CSS_CHART = CSS_PACKAGE_LOCATION + "ChartCss.css";
	
	
	//Images locations TODO 
	public static final String ICON_TEMPERATURE = CSS_PACKAGE_LOCATION + "thermometer.gif";
	public static final String ICON_VOLTAGE = CSS_PACKAGE_LOCATION + "Voltage.gif";
	public static final String ICON_CPU = CSS_PACKAGE_LOCATION + "calc.gif";
	public static final String ICON_RED = "red.jpg";
	public static final String ICON_YELLOW =  "yellow.jpg";
	public static final String ICON_GREEN =  "green.png";
	public static final String ICON_ORANGE ="orange.jpg";
	public static final String EXCEL = "excel.jpg";
	
	
	//MainPane Images
	public static final String MAIN_IMAGES_LOCATION = CSS_PACKAGE_LOCATION;
	
	//General
	public static final int MAIN_PANE_WIDTH = MainWindow.getMainWindow().getMainPane().widthProperty().intValue();
}
