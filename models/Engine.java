package edu.byu.cs.superasteroids.models;

/**
 * Created by benjamin on 10/10/16.
 */
public class Engine extends ShipPart{
    public Engine() {
    }

    private final String _name = "Ship";
    Ship _gameShip = AsteroidsGame.SINGLETON.get_gameShip();


    //-----------------------------MOTION VARIABLES----------------------//
    /**The base maximum velocity of the ship in pixels per second.*/
    private int _baseSpeed;
    /** The base turn rate of the ship in degrees per second. */
    private int _baseTurnRate;
    //___________________________________________________________________//


    //-----------------------------UPDATE AND DRAW FUNCTIONS--------------------------------------//
    public void update(double elapsedTime){
        set_ObjectDirectionAngle(_gameShip.get_ObjectDirectionAngle());
        super.update(elapsedTime);
    }

    public void draw(){
        super.draw(_gameShip.getSHIPBUILDER_SCALE());
    }

    //____________________________________________________________________________________________//



    //--------------------------------SETTER AND GETTERS------------------------------------------//

    public  String get_name(){
        return _name;
    }

    public int get_baseSpeed() {
        return _baseSpeed;
    }

    public void set_baseSpeed(int _baseSpeed) {
        this._baseSpeed = _baseSpeed;
    }

    public int get_baseTurnRate() {
        return _baseTurnRate;
    }

    public void set_baseTurnRate(int _baseTurnRate) {
        this._baseTurnRate = _baseTurnRate;
    }

    //____________________________________________________________________________________________//


    //--------------------------------DESCENDING FUNCTIONS----------------------------------------//
    public void collisionDetected(){
        _gameShip.collisionDetected();
    }
    //____________________________________________________________________________________________//
}
