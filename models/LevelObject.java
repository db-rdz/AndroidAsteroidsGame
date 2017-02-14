package edu.byu.cs.superasteroids.models;

/**
 * Created by benjamin on 10/10/16.
 */
public class LevelObject {
    //-----------------------------OBJECT DATABASE INFORMATION---------------------------------//
        //ROW ENTRY IN TABLE
    private long _id;
        //POINTER TO BG OBJECT TABLE
    private int _objectId;
        //POINTER TO LEVEL TABLE
    private int _levelId;
        //POSITION OF THE OBJECT
    private String _position;
        //SCALE
    private int _scale;
    //_________________________________________________________________________________________//


    //-----------------------------SETTERS AND GETTERS--------------------------------------------//

        //---------------ID SETTER AND GETTER---------------//
    public long get_id() {
        return _id;
    }
    public void set_id(long id) { _id = id; }

        //---------------LEVEL_ID SETTER AND GETTER---------//
    public int get_levelId() {
        return _levelId;
    }
    public void set_levelId(int levelId) {
        _levelId = levelId;
    }

        //---------------POSITION SETTER AND GETTER---------//
    public String get_position() {
        return _position;
    }
    public void set_position(String position) {
        _position = position;
    }

        //---------------BG POINTER SETTER AND GETTER-------//
    public int get_objectId() {
        return _objectId;
    }
    public void set_objectId(int objectId) {
        _objectId = objectId;
    }

        //---------------SCALE SETTER AND GETTER------------//
    public int get_scale() {
        return _scale;
    }
    public void set_scale(int scale) {
        _scale = scale;
    }

    //____________________________________________________________________________________________//
}
