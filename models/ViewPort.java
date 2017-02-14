package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

/**
 * Created by benjamin on 27/10/16.
 */
public class ViewPort {

    private float _height;
    private float _width;
    PointF _position = new PointF(0.0f,0.0f);


    //-------------------------------UPDATE FUNCTION----------------------------------------------//
    public void update(){
        AsteroidsGame _game = AsteroidsGame.SINGLETON;
        Ship _gameShip = AsteroidsGame.SINGLETON.get_gameShip();

        if(_gameShip.get_position().x + _width/2.0 < _game.get_gameWorldMap().get_width() &&
           _gameShip.get_position().x - _width/2.0 > 0) {
            float x = _gameShip.get_position().x - (_width/2.0f);
            float y = _position.y;

            _position.set(x,y);
        }
        if(_gameShip.get_position().y + _height/2.0 < _game.get_gameWorldMap().get_height() &&
           _gameShip.get_position().y - _height/2.0 > 0)
        {
            float x = _position.x;
            float y = _gameShip.get_position().y - (_height/2.0f);

           _position.set(x,y);
        }
    }
    //____________________________________________________________________________________________//



    //-------------------------------DRAW FUNCTION------------------------------------------------//
    public void draw(){

    }
    //____________________________________________________________________________________________//


    //------------------------------------SETTERS AND GETTERS-------------------------------------//

    public float get_height() { return _height; }
    public void set_height(float height) { _height = height; }

    public float get_width() { return _width; }
    public void set_width(float width) { _width = width; }

    public PointF get_position() { return _position; }
    public void set_position(PointF position) { _position = position; }

    //____________________________________________________________________________________________//


}
