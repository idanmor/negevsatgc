package Utils;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Utils {

	
	
	public static Label getTextPictureLabel(String text, Image picture){
		return new TextAndPicture(text, picture);
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
