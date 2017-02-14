package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;
import android.graphics.Rect;

import java.util.List;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by benjamin on 11/10/16.
 */
public class MiniMap {

    private int _left = 0;
    private int _top = 0;
    private int _right = 0;
    private int _bottom = 0;
    private float _width = 10;
    private float _height = 10;
    private final float _SCALE = 25;
    public void update(double elapsedTime){

    }

    //----------------------------------------MINI MAP SETUP--------------------------------------//
        /**
         *      Sets the width and height of the mini map according to the world's dimensions.
         * */
    public void setMiniMap(){
        World map = AsteroidsGame.SINGLETON.get_gameWorldMap();
        _width = map.get_width()/_SCALE;
        _height = map.get_height()/_SCALE;
    }
    //____________________________________________________________________________________________//


    //----------------------------------------DRAW FUNCTIONS--------------------------------------//
        /**
         *      Grabs the positions of every moving object except for bullets and scales down its
         *      position using the mini map _SCALE constant and draws a dot representing that moving
         *      object.
         * */
    public void draw(){
        List<Asteroid> asteroids = AsteroidsGame.SINGLETON.get_asteroidList();
        float x = DrawingHelper.getGameViewWidth() - _width;
        float y = DrawingHelper.getGameViewHeight() - _height;

        _left = (int) (DrawingHelper.getGameViewWidth() - _width);
        _top = (int) (DrawingHelper.getGameViewHeight() - _height);
        _right = (int) (DrawingHelper.getGameViewWidth());
        _bottom = (int) (DrawingHelper.getGameViewHeight());

        Rect minimap = new Rect(_left, _top, _right, _bottom);
        DrawingHelper.drawFilledRectangle(minimap, 999, 255);
        DrawingHelper.drawRectangle(minimap, -256, 255);

        for(int i = 0; i < asteroids.size(); i++){
            float a_x = asteroids.get(i).get_position().x/_SCALE;
            float a_y = asteroids.get(i).get_position().y/_SCALE;

            PointF dot = new PointF(a_x + x, a_y + y);
            DrawingHelper.drawPoint(dot, asteroids.get(i).get_imageWidth()/_SCALE, 333333, 255);
        }

        PointF ship_position = AsteroidsGame.SINGLETON.get_gameShip().get_position();
        float ship_x = ship_position.x/_SCALE;
        float ship_y = ship_position.y/_SCALE;
        PointF ship_in_minimap = new PointF(ship_x + x, ship_y + y);

        DrawingHelper.drawPoint(ship_in_minimap, 10, 888888, 255);
    }

    //____________________________________________________________________________________________//
}
