package edu.byu.cs.superasteroids.models;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by benjamin on 11/10/16.
 */
public class BackgroundObject {

    private long _id;
    private int _imageID;
    private float _scale;
    private String _path;
    private float _position_x;
    private float _position_y;


    //------------------------------DRAW FUNCTION-------------------------------------------------//
    /**
     *      I am drawing the background object only if the object is visible in the viewport
     *      but because I don't have the dimensions of the image it looks like it suddenly appears.
     *      Remember to check the image dimensions and add the dimensions here so that it look smooth.
     *
     * */
    public void draw(){
        ViewPort viewport = AsteroidsGame.SINGLETON.get_gameViewPort();
        float x = _position_x - viewport.get_position().x;
        float y= _position_y - viewport.get_position().y;
        if(x >= 0 && x<= viewport.get_width() && y >= 0 && y<= viewport.get_height() ) {
            DrawingHelper.drawImage(_imageID, x, y, 0.0f, _scale, _scale, 255);
        }
    }
    //____________________________________________________________________________________________//



    //------------------------------SETTERS AND GETTERS-------------------------------------------//

    public int get_imageID() {
        return _imageID;
    }
    public void set_imageID(int _imageID) {
        this._imageID = _imageID;
    }

    public long get_id() {
        return _id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_path() {
        return _path;
    }
    public void set_path(String _path) {
        this._path = _path;
    }

    public float get_position_x() {
        return _position_x;
    }
    public void set_position_x(float _position_x) {
        this._position_x = _position_x;
    }

    public float get_position_y() {
        return _position_y;
    }
    public void set_position_y(float _position_y) {
        this._position_y = _position_y;
    }

    public float get_scale() {
        return _scale;
    }
    public void set_scale(float _scale) {
        this._scale = _scale;
    }

    //____________________________________________________________________________________________//




}
