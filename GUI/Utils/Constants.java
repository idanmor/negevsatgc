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
	public static final String ICON_RED = CSS_PACKAGE_LOCATION + "red.jpg";
	public static final String ICON_YELLOW = CSS_PACKAGE_LOCATION + "yellow.jpg";
	public static final String ICON_GREEN = CSS_PACKAGE_LOCATION + "green.png";
	//General
	public static final int MAIN_PANE_WIDTH = MainWindow.getMainWindow().getMainPane().widthProperty().intValue();
}
