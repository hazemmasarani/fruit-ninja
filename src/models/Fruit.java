package models;

import java.text.DecimalFormat;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import mainControllers.MainController;


public class Fruit {

	MainController mainController = MainController.getMainController();
	Image image;
	ImageView imageView=new ImageView();
	ImageView imageView1=new ImageView();
	ImageView imageView2=new ImageView();
	int fruitImageNumber=0;
    int[] paneXYsize= {600,400};

	boolean isBomb=false;
	boolean isDenamite=false;
	boolean isSliced=false;
	
	ParallelTransition throwTransition;
	ParallelTransition cutTransitionLeft;
	ParallelTransition cutTransitionRight;
	double speed=1;
	int direction=1;
	int[] origin={0,0};
	int[] XYposition={0,0};
	
	public Fruit(int fruitImageNumber) {
		this.fruitImageNumber = fruitImageNumber;
		image=new Image("file:imageLibrary/fruits/"+fruitImageNumber+".png");
		updateImageView();
		imageView.setOnMouseDragEntered( e -> this.slice() );
		if( fruitImageNumber == 9 ) isBomb=true;
		if( fruitImageNumber == 10 ) isDenamite=true;
		
	}
	
	public void setThrowTransition(ParallelTransition parallelTransition) { this.throwTransition = parallelTransition; }
	public void setCutTransitionLeft(ParallelTransition parallelTransition) { this.cutTransitionLeft = parallelTransition; }
	public void setCutTransitionRight(ParallelTransition parallelTransition) { this.cutTransitionRight = parallelTransition; }
	public ParallelTransition getThrowTransition() { return throwTransition; }
	public ParallelTransition getCutTransitionLeft() { return cutTransitionLeft; }
	public ParallelTransition getCutTransitionRight() { return cutTransitionRight; }

	public void cutTransition(){
		Image image1=new Image("file:imageLibrary/fruits/"+fruitImageNumber+"c.png");
		Image image2=new Image("file:imageLibrary/fruits/"+fruitImageNumber+"s.png");
		imageView1.setImage( image1 );
		imageView2.setImage( image2 );
		mainController.cutTransition( this, -1 );
		mainController.cutTransition( this, 1 );
	}
	public void playTransition(){ }
	public void pauseTransition(){  }
	
	public Image getImage(){ return image; }
	public ImageView getImageView(){ return imageView; }
	public ImageView getImageView1(){ return imageView1; }
	public ImageView getImageView2(){ return imageView2; }
	public void updateImageView() { imageView.setImage( image ); }
	public void updateImageViewOrigin() {
		imageView.setLayoutX( origin[0] );
		imageView.setLayoutY( origin[1] );
	}
	
	public boolean isBomb(){ return isBomb; }
	public boolean isDenamite(){ return isDenamite; }

	public int getXposition(){ return XYposition[0]; }
	public int getYposition(){ return XYposition[1]; }
	public void setXYposition(int x,int y){ XYposition[0]=x; XYposition[1]=y; }
	public void updateXYposition(){
		XYposition[0]=(int) ( imageView.localToScene(imageView.getBoundsInLocal()).getMinX()+50 );
		XYposition[1]=(int) ( imageView.localToScene(imageView.getBoundsInLocal()).getMinY()+50 );
	}

	public int getXOrigin(){ return origin[0]; }
	public int getYOrigin(){ return origin[1]; }
	public void setOrigin(int x,int y){
		origin[0]=x; origin[1]=y;
		updateImageViewOrigin();
	}

	public double getCurrentSpeed(){ return speed; }
	public void setCurrentSpeed(double speed){ this.speed = speed; }
	public void updateCurrentSpeed(){
		if( speed==0 ) return;
		this.pauseTransition();
		if( direction==-1 ) {
			speed += 0.4*direction;
			if( speed < 0.025 ) speed = 0.025;
//			if( parallelTransition.getCurrentTime().toSeconds() > parallelTransition.getCycleDuration().toSeconds()/2 ) direction=1;
		}
		else {
			//speed += direction*((4*parallelTransition.getCurrentTime().toSeconds())-1);
			//if( speed > 1) speed = 1;
		}
		speed=0.01;
//		parallelTransition.setRate(speed);
		DecimalFormat df = new DecimalFormat("#.###"); System.out.println("#"+fruitImageNumber+":  "+df.format(speed));
		System.out.println();
		this.playTransition();
	}
	
	public int getCurrentDirection(){ return direction;  }
	public void setCurrentDirection(int direction){ this.direction = direction; }

	public Boolean isOffScreen(){ if( XYposition[1] > paneXYsize[1]+45 && direction < 0 ) return true; else return false; }

	public Boolean isSliced(){ return isSliced; }
	public void slice(){
		if( !isSliced ) {
			isSliced=true;
			cutTransition();
		}
	}
}
