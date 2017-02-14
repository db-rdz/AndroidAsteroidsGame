package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by benjamin on 26/10/16.
 */
public class World {

    //-------------DIMENSION VARIABLES------------//
    private float _width;
    private float _height;
    private static final float _BGIMAGEWIDTH = 2048.0f;
    private static final float _BGIMAGEHEIGHT = 2048.0f;

    //-------------SCALE VARIABLES----------------//
    private float _scale_x = 1;
    private float _scale_y = 1;

    //-------------POSITION VARIABLES-------------//
    private PointF _BGPosition = new PointF(_width/2.0f, _height/2.0f);

    //-------------IMAGE VARIABLES---------------//
    private String _imagePath = "images/space.bmp";
    private int _imageID;


    //--------------------------------------SETTERS AND GETTERS------------------------------------//



    //---------------DIMENSIONS SETTERS AND GETTERS--------------//
    public float get_width() { return _width; }
    public void set_width(float width) { _width = width; }

    public float get_height() { return _height; }
    public void set_height(float height) { _height = height; }

    public static float get_Bgimagewidth() { return _BGIMAGEWIDTH; }
    public static float get_Bgimageheight() { return _BGIMAGEHEIGHT; }


    //---------------IMAGE SETTERS AND GETTERS--------------------//
    public String get_imagePath() {
        return _imagePath;
    }
    public void set_imagePath(String _imagePath) {
        this._imagePath = _imagePath;
    }

    public int get_imageID() {
        return _imageID;
    }
    public void set_imageID(int _imageID) {
        this._imageID = _imageID;
    }



    //---------------SCALE SETTERS AND GETTERS------------------//
    public float get_scale_x() { return _scale_x; }
    public void set_scale_x(float scale_x) { _scale_x = scale_x; }

    public float get_scale_y() { return _scale_y; }
    public void set_scale_y(float scale_y) { _scale_y = scale_y; }



    //---------------POSITION SETTERS AND GETTERS---------------//
    public PointF get_BGPosition() { return _BGPosition; }
    public void set_BGPosition(PointF BGPosition) { _BGPosition = BGPosition; }


    //____________________________________________________________________________________________//




    //---------------------------------------UPDATE FUNCTIONS-------------------------------------//
    public void update(){

    }
    //____________________________________________________________________________________________//




    //---------------------------------------DRAW FUNCTION----------------------------------------//
    public void draw(){
        ViewPort viewport = AsteroidsGame.SINGLETON.get_gameViewPort();
        float x = _BGPosition.x - viewport.get_position().x;
        float y = _BGPosition.y - viewport.get_position().y;
        DrawingHelper.drawImage(_imageID, x, y, 0.0f, _scale_x, _scale_y, 255);
    }
    //____________________________________________________________________________________________//
}
