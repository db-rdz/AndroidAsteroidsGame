package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;

/**
 * Created by benjamin on 10/10/16.
 */
public class Ship extends MovingObject {

    public Ship() {
        super();
        set_scale(.1f);
    }

    private final float SHIPBUILDER_SCALE = (float) .1;
    private final String _name = "Ship";

    //---------------------------------SHIP STATE-----------------------------------//
    /**
     *      These are variables describing the state of the ship. Notice that the hp or _alive
     *      variables are not used because we did not set up the game so that the ship could die but
     *      if we would we would do it with these variables.
     * */
    private int hp = 10;
    private boolean _damaged;
    private boolean _alive = true;
    private double _fiveSeconds = 0;
    //______________________________________________________________________________//


    //----------------------------------SHIP PARTS----------------------------------//
    /**
     *      These are the actual parts of the ship that are being used together.
     * */
    private Cannon _shipCannon;
    private MainBody _shipMainBody;
    private Engine _shipEngine;
    private ExtraPart _shipExtraPart;
    private PowerCore _shipPowerCore;
    //_____________________________________________________________________________//


    //----------------------------SHIP PARTS COORDINATES--------------------------//
    /**
     *      These are the attach point of every part and not their position in the map.
     * */
    private float _cannonX;
    private float _cannonY;

    private float _extraPartX;
    private float _extraPartY;

    private float _engineX;
    private float _engineY;
    private int _diameter = 1;
    //_____________________________________________________________________________//



    //--------------------------------------UPDATE FUNCTION----------------------------//
    public void update(double elapsedTime) {

        //--------------------------------SHIP GETS HIT BY ASTEROID-------------------------------//
        /**
         * section description:
         *      If the ship gets hit then the _damaged variable will be set to true
         *      Note: collisionDectection function in this class sets the _damage variable to true and
         *      it gets called from the parent class visibleObject.
         */

        if(_damaged) {
            _fiveSeconds += elapsedTime;
            if(_fiveSeconds >= 5){
                _fiveSeconds = 0;
                _damaged = false;
            }
        }
        //________________________________________________________________________________________//



        //----------------------------------MOVING AND ROTATING THE SHIP--------------------------//
        /***
         * section description:
         *      If we get a touch input then we need to set the speed, rotating speed and angle in the parent
         *      class MovingObject. If no touch input is detected then it'll set up speed to 0 causing the ship not
         *      to move or rotate.
         */

        if(InputManager.movePoint != null) {
            _shipMainBody.set_ObjectSpeed(_shipEngine.get_baseSpeed());
            _shipMainBody.set_ObjectRotationSpeed(_shipEngine.get_baseTurnRate());
            float rotateToAngle = calculateRotateToAngle();
            _shipMainBody.set_rotateToAngle(rotateToAngle);

        }
        else{
            if(_shipMainBody != null) {
                _shipMainBody.set_ObjectSpeed(0.0f);
                _shipMainBody.set_ObjectRotationSpeed(0.0f);
            }
        }
        //________________________________________________________________________________________//



        ViewPort viewport = AsteroidsGame.SINGLETON.get_gameViewPort();

        //----------------------------------SHIP GOING OUT OF BOUNDS-------------------------------//
        /**
         *  Section description: if ship is going out of bounds then rotate the ship before it exits the viewport.
         * */
        if( get_position().x - viewport.get_position().x - 1.5*_diameter <= 0 ){
            int leftOrRight = get_ObjectDirectionAngle() > 0? 1 : 0;
            if(leftOrRight == 1){
                set_rotateToAngle(0);
            }
            else{
                set_rotateToAngle(180);
            }
        }

        if( get_position().y - viewport.get_position().y - 1.5*_diameter <= 0 ){
            int leftOrRight = get_ObjectDirectionAngle() > 0? 1 : 0;
            if(leftOrRight == 1){
                set_rotateToAngle(90);
            }
            else{
                set_rotateToAngle(270);
            }
        }

        if( get_position().x - viewport.get_position().x + 1.5*_diameter >= viewport.get_width() ){
            int leftOrRight = get_ObjectDirectionAngle() > 0? 1 : 0;
            if(leftOrRight == 1){
                set_rotateToAngle(180);
            }
            else{
                set_rotateToAngle(0);
            }
        }

        if( get_position().y - viewport.get_position().y - 1.5*_diameter >= viewport.get_height() ){
            int leftOrRight = get_ObjectDirectionAngle() > 0? 1 : 0;
            if(leftOrRight == 1){
                set_rotateToAngle(0);
            }
            else{
                set_rotateToAngle(180);
            }
        }
        //_____________________________END OF SHIP GOING OUT OF BOUNDS-----------------------------//



        //-----------------------------UPDATE SHIP PARTS------------------------------------------//
        /**
         * section description:
         *    Update every single part of the ship after checking it is not null.
         *    Note: Checking if it is null is not really necessary as this function wont be call in the
         *    Ship builder.
         * */
        if(_shipMainBody != null) {
            _shipMainBody.update(elapsedTime);
        }
        if(_shipCannon != null) {
            _shipCannon.update(elapsedTime);
        }
        if(_shipEngine != null) {
            _shipEngine.update(elapsedTime);
        }
        if(_shipExtraPart != null) {
            _shipExtraPart.update(elapsedTime);
        }

        if(_shipMainBody != null) {
            set_position(_shipMainBody.get_position());
            set_ObjectDirectionAngle(_shipMainBody.get_ObjectDirectionAngle());
        }
        //____________________________END OF SHIP UPDATING________________________________________//


        //----------------------------FIRING BULLET-----------------------------------------------//
        /**
         * section description:
         *      You call this function here so that you can use the updated position of the cannon.
         *      The cannon will tell the bullet where to start.
         * */
        if(InputManager.firePressed == true) {
            _shipCannon.fire();
        }

        //----------------------------------------------------------------------------------------//
    }

    //------------------------------ROTATING ANGLE CALCULATION------------------------------------//
    private float calculateRotateToAngle() {
        ViewPort viewport = AsteroidsGame.SINGLETON.get_gameViewPort();

        PointF movePointWorld = GraphicsUtils.add(InputManager.movePoint, viewport.get_position());
        movePointWorld = GraphicsUtils.subtract(movePointWorld, get_position());
        float newShipRotation = 0.0f;
        if(movePointWorld.x != 0 && movePointWorld.y != 0){
            newShipRotation = (float) Math.atan2(movePointWorld.y, movePointWorld.x);
        }
        else if( movePointWorld.x != 0 ){
            newShipRotation = (float)((movePointWorld.x > 0) ? 0 : Math.PI);
        }
        else if( movePointWorld.y != 0 ){
            newShipRotation = (float)((movePointWorld.y > 0) ? (0.5 * Math.PI) : (1.5 * Math.PI));
        }

        newShipRotation = (float) GraphicsUtils.radiansToDegrees(newShipRotation);
        return (float) (newShipRotation + 90);
    }
    //____________________________________________________________________________________________//


    //---------------------------------------DRAW FUNCTION----------------------------------------//
    public void draw() {
        if(_shipMainBody != null){
            _shipMainBody.draw();
        }
        if(_shipCannon != null && _shipMainBody != null){
            _shipCannon.draw();
        }
        if(_shipExtraPart != null && _shipMainBody != null){
            _shipExtraPart.draw();
        }
        if(_shipEngine != null && _shipMainBody != null){
            _shipEngine.draw();
        }

        if(_damaged) {
            ViewPort viewport = AsteroidsGame.SINGLETON.get_gameViewPort();
            PointF circlePosition = GraphicsUtils.subtract(get_position(), viewport.get_position());
            DrawingHelper.drawFilledCircle(circlePosition, _diameter / 2, -65536 , 100);
        }
    }

    //____________________________________________________________________________________________//


    public float getSHIPBUILDER_SCALE() {
        return SHIPBUILDER_SCALE;
    }


    //---------------------------MAIN BODY SETTERS AND GETTERS------------------------------//
    public MainBody get_shipMainBody() {
        return _shipMainBody;
    }
    public void set_shipMainBody(MainBody _shipMainBody) {
        this._shipMainBody = _shipMainBody;
    }
    //_______________________________________________________________________________________//



    //-----------------------------CANNON SETTERS AND GETTERS--------------------------------//
        //-----------GETTERS--------//
    public float getCannonX() {
        return _cannonX;
    } //MAP COORDINATES
    public float getCannonY() {
        return _cannonY;
    } //MAP COORDINATES
    public Cannon get_shipCannon() {
        return _shipCannon;
    } //SHIP PART
        //-----------SETTERS: Map Coordinates--------//
    public void setCannonX(float cannonX) {
        this._cannonX = cannonX;
    } //MAP COORDINATES
    public void setCannonY(float cannonY) {
        this._cannonY = cannonY;
    } //MAP COORDINATES
    public void set_shipCannon(Cannon _shipCannon) {
        this._shipCannon = _shipCannon;
    } //SHIP PART
    //_____________________________________________________________________________________//



    //-------------------------EXTRA PART SETTERS AND GETTERS-------------------------------//
        //----------GETTERS---------//
    public float getExtraPartX() {
        return _extraPartX;
    } //MAP COORDINATES
    public float getExtraPartY() {
        return _extraPartY;
    } //MAP COORDINATES
    public ExtraPart get_shipExtraPart() { return _shipExtraPart; } //SHIP PART
        //----------SETTERS---------//
    public void setExtraPartX(float extraPartX) {
        this._extraPartX = extraPartX;
    } //MAP COORDINATES
    public void setExtraPartY(float extraPartY) {
        this._extraPartY = extraPartY;
    } //MAP COORDINATES
    public void set_shipExtraPart(ExtraPart _shipExtraPart) { this._shipExtraPart = _shipExtraPart; } // SHIP PART
    //____________________________________________________________________________________//



    //-------------------------ENGINE SETTERS AND GETTERS----------------------------------//
        //---------GETTERS---------//
    public float getEngineX() {
        return _engineX;
    } //MAP COORDINATES
    public float getEngineY() {
        return _engineY;
    } //MAP COORDINATES
    public Engine get_shipEngine() { return _shipEngine; } //SHIP PART
        //---------SETTERS---------//
    public void setEngineX(float engineX) {
        this._engineX = engineX;
    } //MAP COORDINATES
    public void setEngineY(float engineY) {
        this._engineY = engineY;
    } //MAP COORDINATES
    public void set_shipEngine(Engine _shipEngine) { this._shipEngine = _shipEngine; } //SET SHIP PART
    //____________________________________________________________________________________//



    //-------------------------POWER CORE SETTERS AND GETTERS----------------------------//
        //---------GETTERS---------//
    public PowerCore get_shipPowerCore() { return _shipPowerCore; } //SHIP PART
        //---------SETTERS---------//
    public void set_shipPowerCore(PowerCore _shipPowerCore) { this._shipPowerCore = _shipPowerCore; } //SHIP PART
    //___________________________________________________________________________________//

    public int get_diameter() { return _diameter; }
    public void set_diameter(int diameter) { _diameter = diameter; }

    //---------------------------------STATUS SETTERS AND GETTERS------------------------//
    public boolean is_damaged() { return _damaged; }
    public void set_damaged(boolean _damaged) { this._damaged = _damaged; }
    //___________________________________________________________________________________//


    //----------------------------------DESCENDING FUNCTIONS--------------------------------------//
    @Override
    public int get_imageWidth() {
        return 0;
    }

    @Override
    public int get_imageHeight() {
        return 0;
    }

    @Override
    public String get_name() {
        return _name;
    }

    public void collisionDetected(){
        if(_damaged == false){
            hp--;
        }
        _damaged = true;
    }
    //____________________________________________________________________________________________//




}


