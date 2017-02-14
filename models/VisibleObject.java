package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;
import android.graphics.Rect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by benjamin on 11/10/16.
 */
public abstract class VisibleObject {

    /**     Classes that inherit from VisibleObject:
     *              PositionedObject.
     *
     *      Class objects that can belong or be a VisibleObject:
     *
     *         PositionedObject:
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

    private Boolean _visible = true;
    private Rect _objectAreaRectangle = new Rect();

    //-------------------------------DRAW FUNCTION------------------------------------------------//
    public void draw(int imageID, float scale, float angle, PointF position){
        if(_visible) {

            ViewPort viewport = AsteroidsGame.SINGLETON.get_gameViewPort();
            float x = position.x - viewport.get_position().x;
            float y = position.y - viewport.get_position().y;
            DrawingHelper.drawImage(imageID, x, y, angle, scale, scale, 255);

        }
    }
    //____________________________________________________________________________________________//


    //-------------------------------UPDATE FUNCTION----------------------------------------------//
    public void update(double elapsedTime) {
        Map<Rect, MovingObject> returnObjects = new HashMap<>();
        QuadTree quadtree = AsteroidsGame.SINGLETON.get_quadTree();

        /** This line will retrieve all objects with which a collision is possible and store them in the
         *  returnObjects map.
         * */
        quadtree.retrieve(returnObjects, _objectAreaRectangle);

        /** Iterate through the map and see if the object that is being currently updated has collided
         *  with one of the objects in the map. (remember that objects in the map are only possible objects
         *  with which "this VisibleObject" can collide. )
         * */
        for(Iterator<Map.Entry<Rect, MovingObject>> it = returnObjects.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Rect, MovingObject> entry = it.next();

            if( Rect.intersects(_objectAreaRectangle,entry.getKey()) ){

                /**     Note that I have given every class a name. get_name is a descending function
                 * that grabs the name of the lowest descendant child. With this method we can see
                 * what class is the object we are working with.
                 *
                 * */

                switch(get_name()){
                    /** If this VisibleObject is a ship then... */
                    case "Ship" :
                        /** If the intersecting object is any kind of asteroid then collisionDetected() is called */
                        if( entry.getValue().get_name().contentEquals("regular") ||
                                entry.getValue().get_name().contentEquals("growing") ||
                                entry.getValue().get_name().contentEquals("octeroid") ){
                            collisionDetected();
                        }
                        break;

                    case "regular" :
                        if( entry.getValue().get_name().contentEquals("Bullet") || (
                                entry.getValue().get_name().contentEquals("Ship") &&
                                        !AsteroidsGame.SINGLETON.get_gameShip().is_damaged() )
                                ){
                            collisionDetected();
                        }
                        break;

                    case "growing" :
                        if( entry.getValue().get_name().contentEquals("Bullet") ||(
                                entry.getValue().get_name().contentEquals("Ship") &&
                                        !AsteroidsGame.SINGLETON.get_gameShip().is_damaged() )
                                ){
                            collisionDetected();
                        }
                        break;

                    case "octeroid" :
                        if( entry.getValue().get_name().contentEquals("Bullet") ||(
                                entry.getValue().get_name().contentEquals("Ship") &&
                                        !AsteroidsGame.SINGLETON.get_gameShip().is_damaged() )
                                ){
                            collisionDetected();
                        }
                        break;

                    case "Bullet" :
                        if( entry.getValue().get_name().contentEquals("regular") ||
                                entry.getValue().get_name().contentEquals("growing") ||
                                entry.getValue().get_name().contentEquals("octeroid") ){
                            collisionDetected();
                        }
                        break;
                }
            }
        }
    }
    //____________________________________________________________________________________________//



    //--------------------------------SETTERS AND GETTERS-----------------------------------------//
    public Rect get_objectAreaRectangle() { return _objectAreaRectangle; }
    public void set_objectAreaRectangle(Rect objectAreaRectangle) { _objectAreaRectangle = objectAreaRectangle; }

    public Boolean get_visible() { return _visible; }
    public void set_visible(Boolean _visible) { this._visible = _visible; }

    public abstract String get_name();
    public abstract void collisionDetected();
    //____________________________________________________________________________________________//
}
