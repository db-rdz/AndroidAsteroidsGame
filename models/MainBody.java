package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

/**
 * Created by benjamin on 10/10/16.
 */
public class MainBody extends ShipPart {

    Ship _gameShip = AsteroidsGame.SINGLETON.get_gameShip();
    private final String _name = "Ship";

    //---------------------SHIP PART ATTACHING POINTS---------------------------//

        //----------------CANNON ATTACHING POINT-----------------//
    /** The point on the main body image where the cannon should be attached. */
    private String _cannonAttach;
    /** Interger representation of the X coordinate of the attach point.*/
    private PointF _cannonAttachPoint;

        //----------------ENGINE ATTACHING POINT-----------------//
    /** The point on the main body image where the engine should be attached. */
    private String _engineAttach;
    /** Interger representation of the X coordinate of the attach point.*/
    private PointF _engineAttachPoint;

        //----------------EXTRA PART ATTACHING POINT-------------//
    /** The point on the main body image where the extra part should be attached. */
    private String _extraAttach;
    /** Interger representation of the X coordinate of the attach point.*/
    private PointF _extraAttachPoint;
    //__________________________________________________________________________//


    //------------------------------DRAW FUNCTION-------------------------------------------------//
    public void draw(){
        super.draw(_gameShip.getSHIPBUILDER_SCALE());
    }
    //____________________________________________________________________________________________//


    //-----------------------------UPDATE FUNCTION------------------------------------------------//
    public void update(double elapsedTime){
        super.update(elapsedTime);
    }
    //____________________________________________________________________________________________//


    //-----------------------ABSTRACT PARENT METHODS IMPLEMENTATION-------------------------------//
    public  String get_name(){
        return _name;
    }
    public void collisionDetected(){
        _gameShip.collisionDetected();
    }
    //____________________________________________________________________________________________//



    //----------------------------ATTACH POINTS SETTERS AND GETTERS-------------------------------//

        //-----------CANNON ATTACH POINT-------------//
    public void set_cannonAttach(String _cannonAttach) {
        this._cannonAttach = _cannonAttach;
    }
    public String get_cannonAttach() {
        return _cannonAttach;
    }

        //-----------ENGINE ATTACH POINT-------------//
    public void set_engineAttach(String _engineAttach) {
            this._engineAttach = _engineAttach;
        }
    public String get_engineAttach() {
        return _engineAttach;
    }

        //-----------EXTRA PART ATTACH POINT---------//
    public void set_extraAttach(String _extraAttach) {
            this._extraAttach = _extraAttach;
        }
    public String get_extraAttach() {
        return _extraAttach;
    }

    //____________________________________________________________________________________________//


    //-----------------------------ATTACH POINTS SETTERS AND GETTERS------------------------------//

        //---------CANNON ATTACH POINTS---------------//
    public void set_cannonAttachPoint(PointF xAttachPoint) { _cannonAttachPoint = xAttachPoint; }
    public PointF get_cannonAttachPoint() { return _cannonAttachPoint; }

        //---------ENGINE ATTACH POINTS---------------//
    public void set_engineAttachPoint(PointF xAttachPoint) { _engineAttachPoint = xAttachPoint; }
    public PointF get_engineAttachPoint() { return _engineAttachPoint; }

        //--------EXTRA PART ATTACH POINTS-----------//
    public void set_extraAttachPoint(PointF xAttachPoint) { _extraAttachPoint = xAttachPoint; }
    public PointF get_extraAttachPoint() { return _extraAttachPoint; }


    //____________________________________________________________________________________________//



}
