package Utils;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

import com.j256.ormlite.field.types.TimeStampType;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Utils {



	public static Label getTextPictureLabel(String text, Image picture){
		return new TextAndPicture(text, picture);
	}
	
	public static Timestamp stripTimePortion(Timestamp timestamp) {
	    long msInDay = 1000 * 60 * 60 * 24; // Number of milliseconds in a day
	    long msPortion = timestamp.getTime() % msInDay;
	    timestamp.setTime(timestamp.getTime() - msPortion);
	    return timestamp;
	}
	public static Image getImageViewFromLocation(Class cs, String location){
		return new Image(cs.getResourceAsStream(Constants.MAIN_IMAGES_LOCATION + location));
		//ImageView iv = new ImageView(new Image(cs.getResourceAsStream(location)));
		//return iv;

	}
	public static GregorianCalendar createCalendar(String Date){
		String[] beforeSplitted = Date.split("/");
		return new GregorianCalendar(Integer.parseInt(beforeSplitted[2]), Integer.parseInt(beforeSplitted[1]), Integer.parseInt((beforeSplitted[0])));         
	}
	public static class TextAndPicture extends Label{
		private String text = null;
		private Image picture = null;
		public TextAndPicture(String text, Image picture){
			super();
			this.text = text;
			this.picture = picture;	
			init();
		}

		private void init(){
			this.setText(text);
			this.setGraphic(new ImageView(picture));
		}

		@Override
		public String toString(){
			return text;
		}
	}
}
