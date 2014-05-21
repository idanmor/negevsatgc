package Panels;

import MissionFrames.MissionSplitFrameIMPL;
import Utils.Utils;
import javafx.animation.ScaleTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MissionPanel extends ImageView implements PanelsWithClickInterface  {
	private BorderPane mainPane;
	private ScaleTransition mouseEnteredTransition;
	private ScaleTransition mouseExitedTransition;
	private final double MAX_TRANSITION_SIZE = 1.05;
	public MissionPanel(BorderPane mainPane) {
		this.mainPane = mainPane;
		this.setImage(Utils.getImageViewFromLocation(this.getClass(),"map.png"));
		this.setPreserveRatio(true);
		this.setSmooth(true);
		this.setCache(true);
		this.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				applyClickOnPanel();
				
			}
		});
		applyOnHover();
	}
	@Override
	public void applyClickOnPanel() {
		new MissionSplitFrameIMPL(mainPane);
	}
	
	@Override
	public void applyOnHover() {
		this.setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {  
				getMouseExitedTransitionTransition().stop();
				
				getMouseEnteredTransitionTransition().play();
			}
		});
		this.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				getMouseEnteredTransitionTransition().stop();
				getMouseExitedTransitionTransition().play();
			}
		});
	}
	
	private ScaleTransition getMouseEnteredTransitionTransition(){
		if(mouseEnteredTransition == null){
			mouseEnteredTransition = new ScaleTransition();
			mouseEnteredTransition.setNode(this);
			mouseEnteredTransition.setFromX(1);
			mouseEnteredTransition.setFromY(1);
			mouseEnteredTransition.setToX(MAX_TRANSITION_SIZE);
			mouseEnteredTransition.setToY(MAX_TRANSITION_SIZE);
			mouseEnteredTransition.setCycleCount(1);
			mouseEnteredTransition.setAutoReverse(true);
		}
		return mouseEnteredTransition;
	}
	private ScaleTransition getMouseExitedTransitionTransition(){
		if(mouseExitedTransition == null){
			mouseExitedTransition = new ScaleTransition();
			mouseExitedTransition.setNode(this);
			mouseExitedTransition.setFromX(MAX_TRANSITION_SIZE);
			mouseExitedTransition.setFromY(MAX_TRANSITION_SIZE);
			mouseExitedTransition.setToX(1);
			mouseExitedTransition.setToY(1);
			mouseExitedTransition.setCycleCount(1);
			mouseExitedTransition.setAutoReverse(true);
		}
		return mouseExitedTransition;
	}

}
