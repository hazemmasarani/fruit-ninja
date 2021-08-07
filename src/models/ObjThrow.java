package models;

import javafx.animation.ParallelTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface ObjThrow {

	public void updateImageSize( int timeSizeMinimizer );
	public void setThrowTransition(ParallelTransition parallelTransition);
	public void setCutTransitionLeft(ParallelTransition parallelTransition);
	public void setCutTransitionRight(ParallelTransition parallelTransition);
	public ParallelTransition getThrowTransition();
	public ParallelTransition getCutTransitionLeft();
	public ParallelTransition getCutTransitionRight();
	public void cutTransition();
	public Image getImage();
	public ImageView getImageView();
	public ImageView getImageView1();
	public ImageView getImageView2();
	public ImageView getImageView3();
	public void updateImageView();
	public void updateImageViewOrigin();
	public boolean isBomb();
	public boolean isDenamite();
	public int getXposition();
	public int getYposition();
	public void updateXYposition();
	public int getXOrigin();
	public int getYOrigin();
	public void setOrigin(int x,int y);
	public int getCurrentDirection();
	public void setCurrentDirection(int direction);
	public Boolean isOffScreen();
	public Boolean isSliced();
	public void slice();

}
