package edu.byu.cs.superasteroids.models;

/**
 * Created by benjamin on 10/10/16.
 */
public class PowerCore  {

    /**
     *      NOTE: _cannonBoost and _engineBoost are only used in the DAO but are not really used
     *      for the game implementation.
     *
     * */

    /** The Image id of the power core once it is loaded */
    private int _imageID;
    /** The id value of the object in the database. */
    private long _id;
    /** The value of extra damage that should be added to the cannon’s base damage. */
    private int _cannonBoost;
    /**  Adds to the base speed of the engine. */
    private int _engineBoost;
    /** The path to the power core image */
    private String _image;


    //---------------------------------GETTERS AND SETTERS----------------------------------------//

    //--------------------IMAGE--------------------------------//
    public int get_imageID() {
        return _imageID;
    }
    public void set_imageID(int _imageID) {
        this._imageID = _imageID;
    }

    public String get_image() {
        return _image;
    }
    public void set_image(String _image) {
        this._image = _image;
    }

    //--------------------BOOSTS-------------------------------//
    public int get_cannonBoost() {
        return _cannonBoost;
    }
    public void set_cannonBoost(int _cannonBoost) {
        this._cannonBoost = _cannonBoost;
    }

    public int get_engineBoost() {
        return _engineBoost;
    }
    public void set_engineBoost(int _engineBoost) {
        this._engineBoost = _engineBoost;
    }

    //--------------------IDs----------------------------------//
    public long get_id() {
        return _id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }

    //____________________________________________________________________________________________//

}
