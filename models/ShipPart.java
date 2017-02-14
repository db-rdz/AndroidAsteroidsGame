package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;

/**
 * Created by benjamin on 25/10/16.
 */
public abstract class ShipPart extends MovingObject {
    ShipPart(){
        set_scale(.1f);
    }

    private int _imageID;
    private long _id;
    private String _attachPoint;
    private PointF _mainBodyAttachPoint = new PointF();
    private PointF _attachPointF = new PointF();
    private PointF _PartOffSet = new PointF();
    /** The path to extra part image. */
    private String _image;
    /** The pixel width of the extra part image. */
    private int _imageWidth = 0;
    /** The pixel height of the extra part image. */
    private int _imageHeight = 0;
    /** Used for collision detection */
    private final String _name = "Ship";

    //------------------------------SETTERS AND GETTERS-------------------------------------------//
    //---------ATTACH POINT STRING---------------//
    public String get_attachPoint() {
        return _attachPoint;
    }
    public void set_attachPoint(String _attachPoint) {
        this._attachPoint = _attachPoint;
    }

    //---------IMAGE ID-------------------------//
    public int get_imageID() {
        return _imageID;
    }
    public void set_imageID(int _imageID) {
        this._imageID = _imageID;
    }

    //---------GET IMAGE PATH-------------------//
    public String get_image() {
        return _image;
    }
    public void set_image(String _image) {
        this._image = _image;
    }

    //---------GET IMAGE WIDTH------------------//
    public int get_imageWidth() {
        return _imageWidth;
    }
    public void set_imageWidth(int _imageWidth) {
        this._imageWidth = _imageWidth;
    }

    //---------GET IMAGE HEIGHT-----------------//
    public int get_imageHeight() {
        return _imageHeight;
    }
    public void set_imageHeight(int _imageHeight) {
        this._imageHeight = _imageHeight;
    }

    //---------GET OBJECT ID--------------------//
    public long get_id() {
        return _id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }

    //---------POINT-F MAIN-ATTACH POINTS-------//
    public PointF get_mainBodyAttachPoint() { return _mainBodyAttachPoint; }
    public void set_mainBodyAttachPoint(PointF mainBodyAttachPoint) { _mainBodyAttachPoint = mainBodyAttachPoint; }

    //---------POINT-F ATTACH POINT-------------//
    public PointF get_attachPointF() { return _attachPointF; }
    public void set_attachPointF(PointF attachPointF) { _attachPointF = attachPointF; }

    //____________________________________________________________________________________________//

    public void setPartOffSet(){

    }

    public void update(double elapsedTime) {

        MainBody _shipMainBody = AsteroidsGame.SINGLETON.get_gameShip().get_shipMainBody();
        float SHIPBUILDER_SCALE = AsteroidsGame.SINGLETON.get_gameShip().getSHIPBUILDER_SCALE();

        float partCenterX = 0;
        float partCenterY = 0;
        float mainBodyShipCenterX = (float) (_shipMainBody.get_imageWidth()/2.0);
        float mainBodyShipCenterY = (float) (_shipMainBody.get_imageHeight()/2.0);

        partCenterX = (float) (_imageWidth / 2.0);
        partCenterY = (float) (_imageHeight / 2.0);

        float mainBodyXOffset = _mainBodyAttachPoint.x - mainBodyShipCenterX;
        float mainBodyYOffset = _mainBodyAttachPoint.y - mainBodyShipCenterY;

        float partXOffset = partCenterX - _attachPointF.x;
        float partYOffset = partCenterY - _attachPointF.y;

        _PartOffSet.set(partXOffset+mainBodyXOffset, partYOffset+mainBodyYOffset);

        Ship gameShip = AsteroidsGame.SINGLETON.get_gameShip();
        float angleRadians = (float) GraphicsUtils.degreesToRadians(gameShip.get_ObjectDirectionAngle());
        PointF rotatedPartOffset = GraphicsUtils.rotate(_PartOffSet, angleRadians);


        float finalXOffset =  gameShip.get_position().x + SHIPBUILDER_SCALE*(rotatedPartOffset.x);
        float finalYOffset = gameShip.get_position().y + SHIPBUILDER_SCALE*(rotatedPartOffset.y);

        get_position().set(finalXOffset, finalYOffset);

        super.update(elapsedTime);
    }


    public void draw(float scale) {
        super.draw(_imageID, get_scale());
    }

    //-----------------------ABSTRACT PARENT METHODS IMPLEMENTATION-------------------------------//
    public String get_name(){
        return _name;
    }
    public abstract void collisionDetected();
    //____________________________________________________________________________________________//


    public void shipBuilderdraw(){


    }
}
