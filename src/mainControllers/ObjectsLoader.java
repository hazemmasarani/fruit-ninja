package mainControllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.FactoryObjThrow;
import models.*;

public class ObjectsLoader {

	MainController mainController;
	GameState gameState = GameState.getGameState();
	FactoryObjThrow factoryObjThrow = new FactoryObjThrow();
	public ObjectsLoader() { mainController = MainController.getMainController(); }
	
	public void loadFruitsAndBombs(int Count) {
		
    	for( int i=0; i<Count; i++ ) {
    		ObjThrow fruit = factoryObjThrow.getObjThrow("fruit", mainController.randomRange(1,11), gameState.timeCounter);
    		gameState.fruits.add( fruit );
    		
    		mainController.throwTransition( fruit );
    	}}
	
	public void loadFruits(int Count) {
    	for( int i=0; i<Count; i++ ) {
    		ObjThrow fruit = factoryObjThrow.getObjThrow("fruit", mainController.randomRange(1,9), gameState.timeCounter);
    		gameState.fruits.add( fruit );
    		
    		mainController.throwTransition( fruit );
    	}}
	
	public void loadBombs(int Count) {
    	for( int i=0; i<Count; i++ ) {
    		ObjThrow fruit = factoryObjThrow.getObjThrow("fruit", mainController.randomRange(9,11), gameState.timeCounter);
    		gameState.fruits.add( fruit );
    		
    		mainController.throwTransition( fruit );
    	}}
	
	public void loadGameSuccessfullySavedImage() {
		ImageView image = new ImageView();
		image.setImage( new Image("file:imageLibrary/text/menu/saved.png") );
		image.setFitHeight(41);
		image.setFitWidth(600);

    	mainController.fadeDownTransition( image );
	}
	
	public void loadComboImage() {
		ImageView image = new ImageView();
		image.setFitHeight(35);
		image.setFitWidth( 175 );
		image.setLayoutX( 330 );
		image.setLayoutY( mainController.getPaneXYsize()[1]-370 );
		image.setMouseTransparent(true);
		
    	gameState.comboImage = image;
		mainController.pane.getChildren().add( image );
	}
	
	public void loadMouseKnife() {
		ImageView mouseKnife = new ImageView();
    	mouseKnife.setImage( new Image("file:imageLibrary/tools/knife.png") );
    	mouseKnife.setMouseTransparent(true);
    	mouseKnife.setFitHeight(70);
    	mouseKnife.setFitWidth(50);
    	
    	mainController.followMouseTransition( mouseKnife );
	}
	
	public void loadMouseDragKnife() {
		ImageView mouseTail = new ImageView();
		mouseTail.setImage( new Image("file:imageLibrary/tools/knifeT.png") );
		mouseTail.setMouseTransparent(true);

    	mainController.mouseTailTransition( mouseTail );
	}
}
