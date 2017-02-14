package edu.byu.cs.superasteroids.models;

/**
 * Created by benjamin on 11/10/16.
 */

/** You probably need two constuctors one to set up a random position and direction (for the asteroids)
 * and the other with a predetermined position and no direction (for the ship )
 */

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/** POSITION SHOULD HAPPEN AT THE POSITIONED OBJECT CLASS!! */
public abstract class MovingObject extends PositionedObject {

    /**     Classes that inherit from VisibleObject:
     *                 Ship.
     *                 MainBody.
     *                 ExtraPart.
     *                 Cannon.
     *                 Engine.
     *                 Bullet.
     *                 Asteroid:
     *                     Octeroid.
     *                     GrowingAsteroid.
     *                     RegularAsteroid.
     * */



    //---------------------------------CONSTRUCTORS-----------------------------------------------//
        //---------PREDETERMINED POSITION NO DIRECTION------------//
    public MovingObject(){
        super();
    }

    //____________________________________________________________________________________________//



    //---------------------------------CLASS SPECIFIC MEMBERS-------------------------------------//
    /**the current angle of the object*/
    private float _ObjectDirectionAngle = 0;
    /**rotation speed*/
    private float _ObjectRotationSpeed = 0;
    /**tells whether to turn right or left*/
    private float _rotationDirection = 1;
    /**Angle to which the obect is going to rotate*/
    private float _rotateToAngle;
    /**object speed*/
    private float _ObjectSpeed = 0;
    /** rectangle position */
    private boolean _alive = true;

    //____________________________________________________________________________________________//

    //------------------------------DRAW FUNCTION-------------------------------------------------//
    public void draw(int imageID, float scale){
        ViewPort viewPort = AsteroidsGame.SINGLETON.get_gameViewPort();
        super.draw(imageID, scale, _ObjectDirectionAngle);
        DrawingHelper.drawRectangle(get_objectAreaRectangle(), 888888, 255);
    }
    //____________________________________________________________________________________________//



    //-----------------------------UPDATE FUNCTION------------------------------------------------//
        //it updates the position of the object.
    public void update(double elapsedTime){
        ViewPort viewPort = AsteroidsGame.SINGLETON.get_gameViewPort();
        QuadTree quadtree = AsteroidsGame.SINGLETON.get_quadTree();

        int image_height = (int) (get_scale()*get_imageHeight());
        int image_width = (int) (get_scale()*get_imageWidth());

        int left = (int)( get_position().x - viewPort.get_position().x - image_width/2.2);
        int top = (int)( get_position().y  - viewPort.get_position().y - image_height/2.2);
        int right = (int)( get_position().x - viewPort.get_position().x + image_width/2.2);
        int bottom = (int)( get_position().y - viewPort.get_position().y + image_height/2.2);

        //Object rectangle is in the visible object class.
        get_objectAreaRectangle().set(left, top, right, bottom);
        //You can put the adding of the rect in the visible object class...
        quadtree.insert(get_objectAreaRectangle(), this);


        float moving_distance = (float) (_ObjectSpeed*elapsedTime);
        float moving_angle = (float) ( _ObjectRotationSpeed*elapsedTime );
        if(_ObjectSpeed != 0)  { move(moving_distance); }
        if( _ObjectRotationSpeed != 0 ) { rotate(moving_angle); }
        super.update(elapsedTime);

    }
    //____________________________________________________________________________________________//

    //-------------------------CHILD CLASS CALLING FUNCTIONS--------------------------------------//
    public abstract int get_imageWidth();
    public abstract int get_imageHeight();
    public abstract String get_name();
    public abstract void collisionDetected();
    //____________________________________________________________________________________________//


    //----------------------------------ROTATION AND MOVING---------------------------------------//
    /** We take the rotating angle distance and break it up into "fractions" of the angle distance and
     * add each one of those fractions. We do this so that we can round more accurately the final
     * direction angle of the ship. All angles are in degrees and are converted into radians in the class
     * visibleObject in the draw function.
     * */
    public void rotate(float moving_angle){
        if(_ObjectDirectionAngle >= 360){
            _ObjectDirectionAngle -= 360;
        }
        else if(_ObjectDirectionAngle < 0 ){
            _ObjectDirectionAngle += 360;
        }

        if(_rotateToAngle < 0 ){
            _rotateToAngle += 360;
        }

        float fraction = moving_angle/10.0f;
        if(_ObjectDirectionAngle > _rotateToAngle) {
            if (_ObjectDirectionAngle - _rotateToAngle < 180){
                fraction = fraction*-1;
            }
        }
        else if(_ObjectDirectionAngle < _rotateToAngle) {
            if (_ObjectDirectionAngle - _rotateToAngle < -180){
                fraction = fraction*-1;
            }
        }
        for(int i = 0; i < 10; i++) {
            _ObjectDirectionAngle += fraction;
            if(_ObjectDirectionAngle - _rotateToAngle < 1 &&
                    _ObjectDirectionAngle - _rotateToAngle > -1){
                _ObjectDirectionAngle = _rotateToAngle;
                break;
            }
        }
    }

    public void move(float moving_distance) {
        //We flipped the variables because cosine is being calculated with normal orientation and not
        //horizontal screen orientation.
        double radians = GraphicsUtils.degreesToRadians(_ObjectDirectionAngle);
        float distance_y = (float) (Math.cos(radians)*moving_distance);
        float distance_x = (float) (Math.sin(radians)*moving_distance);
        float new_position_x = get_position().x + distance_x;
        float new_position_y = get_position().y - distance_y;
        get_position().set(new_position_x, new_position_y);
    }
    //____________________________________________________________________________________________//



    //----------------------------------SETTERS AND GETTERS---------------------------------------//

        //ANGLE SETTER AND GETTER
    public float get_ObjectDirectionAngle() { return _ObjectDirectionAngle; }
    public void set_ObjectDirectionAngle(float ObjectDirectionAngle) { _ObjectDirectionAngle = ObjectDirectionAngle; }

        //SPEED SETTER AND GETTER
    public float get_ObjectSpeed() { return _ObjectSpeed; }
    public void set_ObjectSpeed(float ObjectSpeed) { _ObjectSpeed = ObjectSpeed; }

        //ROTATION SPEED SETTER AND GETTER
    public float get_ObjectRotationSpeed() { return _ObjectRotationSpeed; }
    public void set_ObjectRotationSpeed(float ObjectRotationSpeed) { _ObjectRotationSpeed = ObjectRotationSpeed; }

        //ROTATION DIRECTION
    public float get_rotationDirection() { return _rotationDirection; }
    public void set_rotationDirection(float rotationDirection) { _rotationDirection = rotationDirection; }

    public float get_rotateToAngle() { return _rotateToAngle; }
    public void set_rotateToAngle(float rotateToAngle) { _rotateToAngle = rotateToAngle; }

        //STATE OF THE MOVING OBJECT
    public boolean is_alive() { return _alive; }
    public void set_alive(boolean alive) { _alive = alive; }


    //____________________________________________________________________________________________//

}
