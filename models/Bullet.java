package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;

/**
 * Created by benjamin on 30/10/16.
 */
public class Bullet extends MovingObject {


    private Ship _ship = AsteroidsGame.SINGLETON.get_gameShip();
    private Cannon _cannon = AsteroidsGame.SINGLETON.get_gameShip().get_shipCannon();
    private int _imageID = _cannon.get_attackImageID();
    private int _width = _cannon.get_attackImageWidth();
    private int _height = _cannon.get_attackImageHeight();
    private final String _name = "Bullet";


    public Bullet(){
        set_ObjectDirectionAngle(_ship.get_ObjectDirectionAngle());
        set_ObjectSpeed(_ship.get_shipEngine().get_baseSpeed() * 3.0f);
        set_position(getBulletStartingPosition());
        set_scale(.1f);
    }

    //---------------------------GET STARTING POSITIONS FUNCTION----------------------------------//

    public PointF getBulletStartingPosition(){

        float cannonImageXCenter = _ship.get_scale()*_cannon.get_imageWidth()/2.0f;
        float cannonImageYCenter = _ship.get_scale()*_cannon.get_imageHeight()/2.0f;

        PointF cannonImageCenter = new PointF(cannonImageXCenter, cannonImageYCenter);

        float _cannon_emmit_x = _ship.get_scale()*_cannon._bulletPoint.x;
        float _cannon_emmit_y = _ship.get_scale()*_cannon._bulletPoint.y;

        PointF emitPoint = new PointF(_cannon_emmit_x, _cannon_emmit_y);

        PointF offset = GraphicsUtils.subtract(emitPoint, cannonImageCenter);
        float radians = (float) GraphicsUtils.degreesToRadians(_ship.get_ObjectDirectionAngle());
        PointF rotatedPartOffset = GraphicsUtils.rotate(offset, radians);

        PointF position = GraphicsUtils.add(_cannon.get_position(), rotatedPartOffset);
        return position;
    }

    //____________________________________________________________________________________________//



    //---------------------------------DRAW AND UPDATE--------------------------------------------//
    public void update(double elapsedTime) {
            super.update(elapsedTime);
    }
    void draw() {
        super.draw(_imageID, get_scale());
    }
    //____________________________________________________________________________________________//



    //---------------------------------DESCENDING FUNCTIONS---------------------------------------//
    @Override
    public int get_imageWidth() {
        return _width;
    }
    @Override
    public int get_imageHeight() {
        return _height;
    }
    @Override
    public String get_name() {
        return _name;
    }

    /** Bullet has to disappear */
    public void collisionDetected(){
        set_alive(false);
    }
    //____________________________________________________________________________________________//
}
