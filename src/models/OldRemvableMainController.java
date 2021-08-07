package models;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.*;
import javafx.animation.PathTransition; 
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color; 
import javafx.scene.shape.Circle; 
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo; 
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

public class OldRemvableMainController {

	double[] mouseXY = {0,0};
	boolean mouseDragging=false;
	ImageView mouseKnife = new ImageView();
	boolean mouseKnifeLoaded=false;
    int[] paneXYsize= {600,400};

	Timeline timeline = new Timeline();
	Timeline timeline2 = new Timeline();
    int slicedCounter=0;
    int missedCounter=0;
    int currentTimeCounter=0;
    
	List<Fruit> fruits = new ArrayList<Fruit>();
    
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private AnchorPane pane;
    @FXML private Label currentTimeLabel;
    @FXML private Label slicedLabel;
    @FXML private Label missedLabel;
    
    @FXML  void start(ActionEvent event) {
    	for( int i=0; i<fruits.size(); i++ ) fruits.get( i ).playTransition();
    	timeline.play();
    	timeline2.play();
    }

    @FXML void stop(ActionEvent event) {
    	for( int i=0; i<fruits.size(); i++ ) fruits.get( i ).pauseTransition();
    	timeline.pause();
    	timeline2.pause();
    }
    
	public int randomRange(int min, int max) {
		return (int)( min+Math.random()*(max-min) );
	}
	
	public void setMouseKnife() {
		mouseKnife = new ImageView();
    	mouseKnife.setImage( new Image("file:imageLibrary/tools/knife.png") );
    	mouseKnife.setFitHeight(70);
    	mouseKnife.setFitWidth(50);
    	pane.getChildren().add(mouseKnife);
		pane.getScene().setOnMouseMoved(e -> {
			mouseXY[0] = e.getX();
			mouseXY[1] = e.getY();
		});
		pane.getScene().setOnMouseDragged(e -> {
			mouseDragging=true;
			mouseXY[0] = e.getX();
			mouseXY[1] = e.getY();
		});
    	pane.getScene().setOnMouseReleased(e -> {
			mouseDragging=false;
		});
    	mouseKnifeLoaded=true;
	}
	
	public void dragMouseKnife() {
		ImageView mouseTrail = new ImageView();
		mouseTrail.setImage( new Image("file:imageLibrary/tools/knifeT.png") );
		mouseTrail.setLayoutX( mouseXY[0]-30 );
		mouseTrail.setLayoutY( mouseXY[1]-50 );
    	pane.getChildren().add(mouseTrail);
    	
    	ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), mouseTrail);
    	scaleTransition.setFromX(1);
    	scaleTransition.setFromY(1);
    	scaleTransition.setToX(0.1);
    	scaleTransition.setToY(0.1);
        
		FadeTransition fadeOutTransition= new FadeTransition();
		fadeOutTransition.setNode(mouseTrail);
		fadeOutTransition.setDuration(Duration.seconds(0.5));
		fadeOutTransition.setFromValue(1.0);
		fadeOutTransition.setToValue(0.0);
		
		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(scaleTransition , fadeOutTransition);
		parallelTransition.play();
	}
    
    public void loadFruits(int amount) {
    	for( int i=0; i<amount; i++ ) {
    		fruits.add( new Fruit( randomRange(1,8) ) );
    		fruits.get( fruits.size()-1 ).setOrigin( randomRange(100,500),randomRange(300,400) );
    		pane.getChildren().add( fruits.get( fruits.size()-1 ).getImageView() );
    	}
    }
    
    public void loadBombs(int amount) {
    	for( int i=0; i<amount; i++ ) {
    		fruits.add( new Fruit( randomRange(9,10) ) );
    		fruits.get( fruits.size()-1 ).setOrigin( randomRange(100,500),randomRange(-400,-600) );
    		pane.getChildren().add( fruits.get( fruits.size()-1 ).getImageView() );
    	}
    }
    
    public void startTimer() {
    	timeline.setCycleCount(Animation.INDEFINITE);
    	KeyFrame timeplus = new KeyFrame(Duration.seconds(1), e -> {
    		currentTimeLabel.setText("time: "+currentTimeCounter);
	    	currentTimeCounter++;
	    });
    	timeline.getKeyFrames().add(timeplus);

    	timeline2.setCycleCount(Animation.INDEFINITE);
    	KeyFrame speedplus = new KeyFrame(Duration.millis(10), e -> {
			for( int i=0; i<fruits.size(); i++ ) {
				fruits.get( i ).updateCurrentSpeed();
				fruits.get( i ).updateXYposition();
			}
	    });
    	timeline2.getKeyFrames().add(speedplus);
    	
    	timeline.play();
//    	timeline2.play();
    }
    
    public void startUpdater() {
    	AnimationTimer timer;
		timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				if( !mouseKnifeLoaded ) setMouseKnife();
				if( fruits.size() == 0 ) {
					loadFruits(2);
			    	for( int i=0; i<fruits.size(); i++ ) fruits.get( i ).playTransition();
				}
				mouseKnife.setLayoutX( mouseXY[0]-20 );
				mouseKnife.setLayoutY( mouseXY[1]-30 );
				if( mouseDragging ) dragMouseKnife();
				
				for( int i=0; i<fruits.size(); i++ ) {
					if( fruits.get( i ).isSliced() ) {
					    if( fruits.get( i ).isBomb() ) slicedCounter--;
					    else slicedCounter++;
					    fruits.remove( i );
					    i--;
					}
					else if( fruits.get( i ).isOffScreen() ) {
					    missedCounter++;
					    fruits.remove( i );
					    i--;
					}
				}
			}
		};
		timer.start();
    }

    @FXML void initialize() {
    	pane.setOnDragDetected(e -> pane.startFullDrag());
    	startTimer();
    	startUpdater();
    }
}
