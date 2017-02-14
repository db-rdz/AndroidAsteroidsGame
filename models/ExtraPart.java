package edu.byu.cs.superasteroids.models;

/**
 * Created by benjamin on 10/10/16.
 */
public class ExtraPart extends ShipPart {
    private final String _name = "Ship";
    Ship _gameShip = AsteroidsGame.SINGLETON.get_gameShip();

    //--------------------------------UPDATE AND DRAW FUNCTIONS-----------------------------------//
    public void update(double elapsedTime){
        set_ObjectDirectionAngle(_gameShip.get_ObjectDirectionAngle());
        super.update(elapsedTime);
    }

    public void draw(){
        super.draw(_gameShip.getSHIPBUILDER_SCALE());
    }

    //____________________________________________________________________________________________//


    //---------------------------------GETTERS AND SETTERS----------------------------------------//
    public  String get_name(){
        return _name;
    }
    //____________________________________________________________________________________________//



    //---------------------------------DESCENDING FUNCTIONS---------------------------------------//
    public void collisionDetected(){
        _gameShip.collisionDetected();
    }
    //____________________________________________________________________________________________//
}
