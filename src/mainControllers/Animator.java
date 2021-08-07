package mainControllers;

import models.*;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import javafx.event.EventHandler;

public class Animator {

	MainController mainController;
	GameState gameState = GameState.getGameState();

	public Animator() { mainController = MainController.getMainController(); }
	
    public double getSlowOutValue(double ratio) { return Math.sqrt(1 - (ratio - 1) * (ratio - 1)); }  
    public double getSlowInValue(double ratio) {  return -(Math.sqrt(1 - ratio*ratio) - 1); }
    public double getSlowInValueX2(double ratio) { return ratio*ratio*ratio*ratio*ratio; }
	
	public void throwTransition(ObjThrow fruit) {
		int[] paneXYsize= mainController.getPaneXYsize();
		fruit.setOrigin( mainController.randomRange(30,paneXYsize[0]-30),mainController.randomRange(paneXYsize[1]+50,paneXYsize[1]+200) );
		ImageView object = fruit.getImageView();
		
		MoveTo movePathTo1;
		MoveTo movePathTo2;
		QuadCurveTo quadCurveTo1;
		QuadCurveTo quadCurveTo2;
		Path path1 = new Path();
		Path path2 = new Path();
		PathTransition pathTransition1 = new PathTransition(Duration.seconds(1.2), path1, object);
		PathTransition pathTransition2 = new PathTransition(Duration.seconds(1.4), path2, object);
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), object);
        SequentialTransition sequentialTransition = new SequentialTransition( pathTransition1, pathTransition2 );
        ParallelTransition parallelTransition = new ParallelTransition( sequentialTransition, rotateTransition );
   		
		if( object.getLayoutX() < mainController.getPaneXYsize()[0]/2 ) {
			movePathTo1 = new MoveTo(0, 0);
			movePathTo2 = new MoveTo(40, -320);
			quadCurveTo1 = new QuadCurveTo(20, -300, 40, -320);
			quadCurveTo2 = new QuadCurveTo(40, -330, 50, 0);
			rotateTransition.setByAngle( 1260 );
		}
		else {
			movePathTo1 = new MoveTo(0, 0);
			movePathTo2 = new MoveTo(-40, -320);
			quadCurveTo1 = new QuadCurveTo(-20, -300, -40, -320);
			quadCurveTo2 = new QuadCurveTo(-40, -330, -50, 0);
			rotateTransition.setByAngle( -1260 );
		}
		
		path1.getElements().add(movePathTo1);
		path1.getElements().add(quadCurveTo1);
		pathTransition1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		path2.getElements().add(movePathTo2);
		path2.getElements().add(quadCurveTo2);
		pathTransition2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		
		pathTransition1.setInterpolator(new Interpolator() {
            @Override protected double curve(double value) {
            	return getSlowOutValue(value);
        }});
		pathTransition2.setInterpolator(new Interpolator() {
            @Override protected double curve(double value) {
            	return getSlowInValue(value);
        }});
		rotateTransition.setInterpolator(new Interpolator() {
            @Override protected double curve(double value) {
        		fruit.updateXYposition();
        		object.toFront();
            	return getSlowOutValue(value);
        }});
		pathTransition1.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) { fruit.setCurrentDirection(-1); }
		});
		parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) { mainController.pane.getChildren().remove( object ); }
		});

		object.setOnMouseDragEntered( e -> {
			parallelTransition.pause(); 
			mainController.getPane().getChildren().remove(object);
			fruit.slice();
		});
		
		fruit.setThrowTransition(parallelTransition);
		mainController.pane.getChildren().add( object );
		parallelTransition.play(); 
	}
	
	public void cutTransition(ObjThrow fruit, int direction) {
		ImageView object;
		if( direction > 0) object = fruit.getImageView2();
		else object = fruit.getImageView1();
		object.setLayoutX( fruit.getXposition() );
		object.setLayoutY( fruit.getYposition() );
		
		MoveTo movePathTo = new MoveTo(0, 0);
		QuadCurveTo quadCurveTo;
		Path path = new Path();
		PathTransition pathTransition = new PathTransition(Duration.seconds(1), path, object);
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), object);
        ParallelTransition parallelTransition = new ParallelTransition( pathTransition, rotateTransition );

        if( direction > 0 ) {
			quadCurveTo = new QuadCurveTo(100, 50, 110, 320);
			rotateTransition.setByAngle( 660 );
        }
        else{
    		quadCurveTo = new QuadCurveTo(-100, 50, -110, 320);
    		rotateTransition.setByAngle( -660 );
        }
        
		path.getElements().add(movePathTo);
		path.getElements().add(quadCurveTo);
		pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		
		parallelTransition.setInterpolator(new Interpolator() {
            @Override protected double curve(double value) {
        		object.toFront();
            	return (value);
        }});
		parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) { mainController.pane.getChildren().remove( object ); }
		});

		if( direction > 0) fruit.setCutTransitionRight(parallelTransition);
		else fruit.setCutTransitionLeft(parallelTransition);
		mainController.getPane().getChildren().add( object );
        parallelTransition.play(); 
	}
	
	public void fadeShrinkTransition(ImageView object) {
    	ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), object);
    	scaleTransition.setFromX(1);
    	scaleTransition.setFromY(1);
    	scaleTransition.setToX(0.1);
    	scaleTransition.setToY(0.1);
        
		FadeTransition fadeOutTransition= new FadeTransition(Duration.seconds(0.5), object);
		fadeOutTransition.setFromValue(1.0);
		fadeOutTransition.setToValue(0.0);

		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1.2), object);
		rotateTransition.setByAngle( 5360 );
		
		ParallelTransition parallelTransition = new ParallelTransition(scaleTransition , fadeOutTransition, rotateTransition);

		parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) { mainController.pane.getChildren().remove( object ); }
		});
		
		mainController.getPane().getChildren().add(object);
		parallelTransition.play();
	}
	
	public void fadeDownTransition(ImageView object) {
		object.setLayoutX( 50 );
		object.setLayoutY( mainController.getPaneXYsize()[1]-100 );
		
    	TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), object);
    	translateTransition.setByY(100);
        
		FadeTransition fadeOutTransition= new FadeTransition(Duration.seconds(2), object);
		fadeOutTransition.setFromValue(1.0);
		fadeOutTransition.setToValue(0.0);
		
		ParallelTransition parallelTransition = new ParallelTransition(translateTransition , fadeOutTransition);
		
		parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) { mainController.pane.getChildren().remove( object ); }
		});
		
		mainController.getPane().getChildren().add(object);
		parallelTransition.play();
	}
	
	public void fadeUpTransition(ImageView object) {
    	TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), object);
    	translateTransition.setByY(-200);
        
		FadeTransition fadeOutTransition= new FadeTransition(Duration.seconds(2), object);
		fadeOutTransition.setFromValue(1.0);
		fadeOutTransition.setToValue(0.0);
		
		ParallelTransition parallelTransition = new ParallelTransition(translateTransition , fadeOutTransition);
		
		parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) { mainController.pane.getChildren().remove( object ); }
		});
		
		parallelTransition.play();
	}
	
	public void fadeTransition(ObjThrow fruit) {
		ImageView object;
		object = fruit.getImageView3();
		object.setLayoutX( fruit.getXposition()-50 );
		object.setLayoutY( fruit.getYposition()-50 );
        
		FadeTransition fadeOutTransition= new FadeTransition(Duration.seconds(6), object);
		fadeOutTransition.setFromValue(0.7);
		fadeOutTransition.setToValue(0.0);
		
		fadeOutTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) { mainController.pane.getChildren().remove( object ); }
		});
		
		mainController.getPane().getChildren().add(object);
		fadeOutTransition.play();
	}
	
	public void fadeEnlargeTransition(ObjThrow fruit) {
		ImageView object;
		object = fruit.getImageView1();
		object.setLayoutX( fruit.getXposition()-50 );
		object.setLayoutY( fruit.getYposition()-50 );
		
    	ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), object);
    	scaleTransition.setFromX(0.1);
    	scaleTransition.setFromY(0.1);
    	scaleTransition.setToX(2);
    	scaleTransition.setToY(2);
        
		FadeTransition fadeOutTransition= new FadeTransition(Duration.seconds(2), object);
		fadeOutTransition.setFromValue(1.0);
		fadeOutTransition.setToValue(0.0);

		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), object);
		rotateTransition.setByAngle( 0 );
		
		ParallelTransition parallelTransition = new ParallelTransition(scaleTransition , fadeOutTransition, rotateTransition);
		
		parallelTransition.setInterpolator(new Interpolator() {
            @Override protected double curve(double value) {
        		object.toFront();
            	return (value);
        }});
		
		parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) { mainController.pane.getChildren().remove( object ); }
		});
		
		mainController.getPane().getChildren().add(object);
		parallelTransition.play();
	}

	public void followMouseTransition(ImageView object) {
		object.setLayoutX(0);
		object.setLayoutY(0);

    	TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), object);
    	translateTransition.setCycleCount(Animation.INDEFINITE);
    	translateTransition.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double value) {
            	object.setLayoutX( gameState.mouseXY[0]-20 );
				object.setLayoutY( gameState.mouseXY[1]-30 );
				object.toFront();
				return value;
        }});
    	
    	mainController.getPane().getChildren().add(object);
    	translateTransition.play();
	}
	
	public void mouseTailTransition(ImageView object) {
		object.setLayoutX( gameState.mouseXY[0]-30 );
		object.setLayoutY( gameState.mouseXY[1]-50 );
		
		fadeShrinkTransition(object);
	}
}
