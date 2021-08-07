package models;

public class FactoryObjThrow {

	public ObjThrow getObjThrow (String type, int fruitImageNumber, int timeSizeMinimizer) {
		if(type == "fruit")return new Fruit(fruitImageNumber,timeSizeMinimizer);
		return null;
	}
}
