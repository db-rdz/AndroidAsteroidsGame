package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import java.util.Random;

/**
 * Created by benjamin on 10/10/16.
 */
public abstract class  Asteroid extends MovingObject {

    /** Constructor to initialize data members*/
    public Asteroid() {}
    /** Primary key in table */
    private long _id;
    /** The id of the sound to be played when it gets hit by a bullet. I think I don't use it here though...*/
    public static int _impactSoundID;
    /** Image Id when loading the image to the content manager*/
    private int _imageID;
    /** Represents the asteroid name */
    private String _name;
    /** This is the path of the image */
    private String _imagePath;
    /** Represents the image width of the asteroid*/
    private int _imageWidth;
    /** Represents the image height of the asteroid*/
    private int _imageHeight;
    /** Represents the type of the asteroid (Same as name) */
    private String _type;
    /** The life of the asteroid*/
    private int _hp = 5;
    /** The life that it starts with or maximum hp. */
    private int _startingHp = 5;
    /** If it can be broken more. */
    private boolean _breakable;

    //-----------------------------DRAW-----------------------------------------------------------//

    public void draw() {
        super.draw(_imageID, get_scale());
    }

    //____________________________________________________________________________________________//



    //-----------------------------UPDATE---------------------------------------------------------//

    public void update(double elapsedTime){
        World map = AsteroidsGame.SINGLETON.get_gameWorldMap();
        float edge_x_right = get_position().x + (_imageWidth*get_scale())/2;
        float edge_x_left = get_position().x - (_imageWidth*get_scale())/2;
        float edge_y_bottom = get_position().y + (_imageHeight*get_scale())/2;
        float edge_y_top = get_position().y - (_imageHeight*get_scale())/2;
        if(edge_x_right >= map.get_width() || edge_x_left <= 0 ){
            set_ObjectDirectionAngle(-1*get_ObjectDirectionAngle());
        }
        if(edge_y_bottom >= map.get_height() || edge_y_top <= 0){
            set_ObjectDirectionAngle(180-get_ObjectDirectionAngle());
        }
        super.update(elapsedTime);
    }
    //____________________________________________________________________________________________//



    //-----------------------------INITIALIZE SPEED, POSITION, AND DIRECTION----------------------//
    public void initializeSpeed(){
        Random rand = new Random();
        int n = rand.nextInt(2) + 1;
        float ship_speed = AsteroidsGame.SINGLETON.get_gameShip().get_shipEngine().get_baseSpeed();
        float asteroidSpeed = ship_speed*n;

        set_ObjectSpeed(asteroidSpeed);
    }
    public void initalizePosition(){
        Random rand = new Random();
        World map = AsteroidsGame.SINGLETON.get_gameWorldMap();
        float x_split = map.get_width()/6.0f;
        float y_split = map.get_height()/6.0f;
        float x = x_split * (rand.nextInt(3) + 1);
        float y = y_split * ((rand.nextInt() % 2 == 0) ? 1 : 5);
        PointF randomPosition = new PointF(x,y);
        set_position(randomPosition);
    }

    public void initilizeDirection(){
        Random rand = new Random();
        float direction = rand.nextInt(360) + 1;
        set_ObjectDirectionAngle(direction);
    }

    //____________________________________________________________________________________________//



    //---------------------------------COPY ASTEROID INFO------------------------------------------//

    public void copy(Asteroid a){

        _id = a.get_id();
        _name = (a.get_name());
        _imagePath = (a.get_imagePath());
        _imageWidth = (a.get_imageWidth());
        _imageHeight = (a.get_imageHeight());
        _type = (a.get_type());

    }
    //____________________________________________________________________________________________//


    //-----------------------------SETTERS AND GETTERS--------------------------------------------//

    public String get_imagePath() {
        return _imagePath;
    }
    public void set_imagePath(String _imagePath) {
        this._imagePath = _imagePath;
    }

    public void set_type(String type) {
        _type = type;
    }
    public String get_type() {
        return _type;
    }

    public void set_name(String name) {
        _name = name;
    }
    public void set_image(String image) {
        _imagePath = image;
    }

    public int get_imageID() {
        return _imageID;
    }
    public void set_imageID(int _imageID) {
        this._imageID = _imageID;
    }

    public void set_imageWidth(int imageWidth) {
        _imageWidth = imageWidth;
    }
    public void set_imageHeight(int image_Height) {
        _imageHeight = image_Height;
    }

    public long get_id() {
        return _id;
    }
    public void set_id(long id) {
        this._id = id;
    }

    public int get_hp() { return _hp; }
    public void set_hp(int hp) { _hp = hp; }

    public int get_startingHp() { return _startingHp; }
    public void set_startingHp(int startingHp) { _startingHp = startingHp; }

    public boolean is_breakable() { return _breakable; }
    public void set_breakable(boolean breakable) { _breakable = breakable; }

    //____________________________________________________________________________________________//



    //-----------------------DESCENDING FUNCTIONS-------------------------------------------------//
    public String get_name() {
        return _name;
    }
    public int get_imageWidth() {
        return _imageWidth;
    }
    public int get_imageHeight() {
        return _imageHeight;
    }
    public abstract void collisionDetected();
    //____________________________________________________________________________________________//
}
