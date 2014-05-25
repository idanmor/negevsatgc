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

public class MissionPanel  extends PanelsWithClickInterface  {
	
	public MissionPanel(BorderPane mainPane) {
		super(mainPane, "map.png");
	}
	@Override
	public void applyClickOnPanel() {
		new MissionSplitFrameIMPL(mainPane);
	}

}
