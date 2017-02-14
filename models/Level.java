package edu.byu.cs.superasteroids.models;

import java.util.List;

/**
 * Created by benjamin on 10/10/16.
 */
public class Level {


    //----------------------DIMENSION VARIABLES-------------------//
    /** width of the level (in pixels) */
    private float _width;
    /** Integer. The pixel height of the level. */
    private float _height;
    //___________________________________________________________//

    //----------------------IDENTIFIER VARIABLES-----------------//
    private long _id;
    /**  Integer. The level number. */
    int _levelNumber;
    //___________________________________________________________//


    //-----------------------DESCRIPTIVE VARIABLES--------------//
    /** Title fo the Level */
    private String _title;
    /** Some instructions displayed to the user */
    private String _hint;
    /** The number of asteroids to destroy to pass to the next level */
    private int _goal;
    //___________________________________________________________//


    //------------------------MEDIA AND OTHER-------------------//
    /** The path to the music file to be played with the level. */
    private String _music;
    /** List of objects in the level */
    private List<LevelObject> _levelObjects;
    //___________________________________________________________//


    public Level(){

    }

    //----------------------------------SETTERS AND GETTERS---------------------------------------//

    //----------------------------------IDENTIFIERS----------------------------------//
    public long get_id() {
        return _id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }

    public int get_levelNumber() {
        return _levelNumber;
    }
    public void set_levelNumber(int _levelNumber) {
        this._levelNumber = _levelNumber;
    }
    //________________________________________________________________________________//


    //----------------------------------DESCRIPTIVE----------------------------------//
    public String get_title() {
        return _title;
    }
    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_hint() {
        return _hint;
    }
    public void set_hint(String _hint) {
        this._hint = _hint;
    }

    public int get_goal() { return _goal; }
    public void set_goal(int goal) { _goal = goal; }

    //_______________________________________________________________________________//


    //----------------------------------DIMENSIONS-----------------------------------//
    public float get_width() {
        return _width;
    }
    public void set_width(float _width) {
        this._width = _width;
    }

    public float get_height() {
        return _height;
    }
    public void set_height(float _height) {
        this._height = _height;
    }
    //_______________________________________________________________________________//


    //----------------------------------OTHERS AND MEDIA-----------------------------//
    public String get_music() {
        return _music;
    }
    public void set_music(String _music) {
        this._music = _music;
    }

    public List<LevelObject> get_levelObjects() {
        return _levelObjects;
    }
    public void set_levelObjects(List<LevelObject> _levelObjects) { this._levelObjects = _levelObjects; }
    //_______________________________________________________________________________//

    //____________________________________________________________________________________________//



}
