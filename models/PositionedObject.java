package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by benjamin on 11/10/16.
 */
public abstract class PositionedObject extends VisibleObject {
    /**     Classes that inherit from VisibleObject:
     *              MovingObject.
     *
     *      Class objects that can belong or be a VisibleObject:
     *
     *             MovingObject:
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

    //--------------------------------CONSTRUCTORS------------------------------------------------//
        //--------------NO PARAMS CENTERS THE OBJECT POSITION TO THE CENTER OF THE MAP------------//
    PositionedObject(){
        float x = DrawingHelper.getGameViewWidth()/2;
        float y = DrawingHelper.getGameViewHeight()/2;
        _position.set(x,y);
    }

    //____________________________________________________________________________________________//

    private PointF _position = new PointF();
    private float _scale = 1.0f;


    //-----------------------------------UPDATE AND DRAW FUNCTIONS--------------------------------//
    /**
     *      In here we tell the parent class VisibleObject if the moving object is visible.
     * */
    public void update(double elapsedTime){
        ViewPort viewport = AsteroidsGame.SINGLETON.get_gameViewPort();

        if(_position.x + get_imageWidth() < viewport.get_position().x ||
                _position.x - get_imageWidth() > ( viewport.get_position().x + viewport.get_width() ) ||
                _position.y + get_imageHeight() < viewport.get_position().y ||
                _position.y - get_imageHeight() > ( viewport.get_position().y + viewport.get_height() )
                ){
            set_visible(false);
        }
        else{
            set_visible(true);
        }
    }

    public void draw(int imageID, float scale, float angle){
        super.draw(imageID, scale, angle, _position);
    }
    //____________________________________________________________________________________________//



    //---------------------------------POSITION SETTER AND GETTER---------------------------------//
    public PointF get_position() { return _position; }
    public void set_position(PointF position) { _position = position; }

    public float get_scale() { return _scale; }
    public void set_scale(float scale) { _scale = scale; }
    //____________________________________________________________________________________________//



    //-------------------------DESCENDING FUNCTIONS-----------------------------------------------//
    public abstract int get_imageWidth();
    public abstract int get_imageHeight();
    public abstract String get_name();
    public abstract void collisionDetected();
    //____________________________________________________________________________________________//
}
