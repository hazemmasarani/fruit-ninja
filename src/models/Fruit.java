package models;

import javafx.animation.ParallelTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainControllers.MainController;

public class Fruit implements ObjThrow {

	MainController mainController = MainController.getMainController();
	Image image;
	ImageView imageView=new ImageView();
	ImageView imageView1=new ImageView();
	ImageView imageView2=new ImageView();
	ImageView imageView3=new ImageView();
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
	
	public Fruit(int fruitImageNumber, int timeSizeMinimizer ) {
		this.fruitImageNumber = fruitImageNumber;
		image=new Image("file:imageLibrary/fruits/"+fruitImageNumber+".png");
		updateImageView();
		updateImageSize( timeSizeMinimizer );
		imageView.setOnMouseDragEntered( e -> this.slice() );
		if( fruitImageNumber == 9 ) isBomb=true;
		if( fruitImageNumber == 10 ) isDenamite=true;
	}
	
	public void updateImageSize( int timeSizeMinimizer ) {
		if( timeSizeMinimizer>60 ) timeSizeMinimizer=60;
		
		imageView.setFitHeight( (int)(100-timeSizeMinimizer/1.5) );
		imageView.setFitWidth( (int)(100-timeSizeMinimizer/1.5) );
		imageView1.setFitHeight( (int)(100-timeSizeMinimizer/1.5) );
		imageView1.setFitWidth( (int)(100-timeSizeMinimizer/1.5) );
		imageView2.setFitHeight( (int)(100-timeSizeMinimizer/1.5) );
		imageView2.setFitWidth( (int)(100-timeSizeMinimizer/1.5) );
		imageView3.setFitHeight( (int)(100-timeSizeMinimizer/1.5) );
		imageView3.setFitWidth( (int)(100-timeSizeMinimizer/1.5) );
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
		Image image3=new Image("file:imageLibrary/tools/cutE.png");
		imageView1.setImage( image1 );
		imageView2.setImage( image2 );
		imageView3.setImage( image3 );

		if( isBomb || isDenamite ) {
			image1=new Image("file:imageLibrary/tools/boom.png");
			image3=new Image("file:imageLibrary/tools/boomE.png");
			imageView1.setImage( image1 );
			imageView3.setImage( image3 );
			mainController.fadeEnlargeTransition( this );
		}
		else {
			mainController.cutTransition( this, -1 );
			mainController.cutTransition( this, 1 );
		}
		mainController.fadeTransition( this );
	}
	
	public Image getImage(){ return image; }
	public ImageView getImageView(){ return imageView; }
	public ImageView getImageView1(){ return imageView1; }
	public ImageView getImageView2(){ return imageView2; }
	public ImageView getImageView3(){ return imageView3; }
	public void updateImageView() { imageView.setImage( image ); }
	public void updateImageViewOrigin() {
		imageView.setLayoutX( origin[0] );
		imageView.setLayoutY( origin[1] );
	}
	
	public boolean isBomb(){ return isBomb; }
	public boolean isDenamite(){ return isDenamite; }

	public int getXposition(){ return XYposition[0]; }
	public int getYposition(){ return XYposition[1]; }
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
