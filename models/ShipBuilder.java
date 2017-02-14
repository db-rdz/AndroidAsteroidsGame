package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by benjamin on 1/11/16.
 */
public class ShipBuilder {

    /**
     *      We are not using the game functions to draw the ship in the ship builder because they are
     *      two different things of the game. The ShipBuilder is not the game and should not use the
     *      functions that are used to draw the ship in the game. This can cause things to crash and not
     *      work properly.
     *
     *      Some reasons why you should use functions that are used in the game:
     *          1- In the ship builder the ship doesn't move but in the game the ship inherits from
     *                 moving object.
     *
     *          2- The update function in the game updates position and sets rectangle for every moving object
     *                  used for the with the intention of detecting collisions. We do not need these things
     *                  in the ship builder.
     *
     *          3- The ship builder should be an mediator between the DAO and the gameModel. In other words
     *                  it should only provide the game with the model of the ship.
     * */



    private PointF _position = new PointF();
    private Ship _gameShip = new Ship();
    public static float _SHIPBUILDERSCALE = .2f;

    //----------------------------------UPDATE FUNCTION-------------------------------------------//
    public void update(){
        float mainbodyCenterX;
        float mainbodyCenterY;
        PointF mainBodyCenter = new PointF();

        /**
         *      Every section below calculates the offset and the position of a ship part if
         *      the main body and a specific ship part is selected. This could've been implemented
         *      in a single block taking advantage of the ShipPart class.
         *
         *      Change it when semester ends...
         *
         * */

        //---------------IF MAIN BODY IS SET----------------------------------------//
        if(_gameShip.get_shipMainBody() != null){
            _gameShip.get_shipMainBody().set_position(_position);
            mainbodyCenterX = (_gameShip.get_shipMainBody().get_imageWidth())/2.0f;
            mainbodyCenterY = (_gameShip.get_shipMainBody().get_imageHeight())/2.0f;
            mainBodyCenter = new PointF(mainbodyCenterX, mainbodyCenterY);
        }

        //---------------IF CANNON IS SET-------------------------------------------//
        if(_gameShip.get_shipCannon() != null){
            PointF attachmentMain =_gameShip.get_shipCannon().get_mainBodyAttachPoint();
            PointF attachment = _gameShip.get_shipCannon().get_attachPointF();

            float shipPart_x = (_gameShip.get_shipCannon().get_imageWidth())/2.0f;
            float shipPart_y = (_gameShip.get_shipCannon().get_imageHeight())/2.0f;
            PointF partCenter = new PointF(shipPart_x, shipPart_y);

            PointF mainOffset = GraphicsUtils.subtract(attachmentMain, mainBodyCenter );
            PointF partOffset = GraphicsUtils.subtract(partCenter, attachment);

            PointF finalOffset = GraphicsUtils.add(partOffset,mainOffset);
            finalOffset.set(_SHIPBUILDERSCALE*finalOffset.x, _SHIPBUILDERSCALE*finalOffset.y);
            PointF position = GraphicsUtils.add(_position, finalOffset);
            _gameShip.get_shipCannon().set_position(position);
        }

        //--------------IF EXTRA PART IS SET-----------------------------------------------//
        if(_gameShip.get_shipExtraPart() != null){
            PointF attachmentMain =_gameShip.get_shipExtraPart().get_mainBodyAttachPoint();
            PointF attachment = _gameShip.get_shipExtraPart().get_attachPointF();

            float shipPart_x = (_gameShip.get_shipExtraPart().get_imageWidth())/2.0f;
            float shipPart_y = (_gameShip.get_shipExtraPart().get_imageHeight())/2.0f;
            PointF partCenter = new PointF(shipPart_x, shipPart_y);

            PointF mainOffset = GraphicsUtils.subtract(attachmentMain, mainBodyCenter );
            PointF partOffset = GraphicsUtils.subtract(partCenter, attachment);

            PointF finalOffset = GraphicsUtils.add(partOffset,mainOffset);
            finalOffset.set(_SHIPBUILDERSCALE*finalOffset.x, _SHIPBUILDERSCALE*finalOffset.y);
            PointF position = GraphicsUtils.add(_position, finalOffset);
            _gameShip.get_shipExtraPart().set_position(position);

        }

        //--------------IF ENGINE IS SET---------------------------------------------------//
        if(_gameShip.get_shipEngine() != null){
            PointF attachmentMain =_gameShip.get_shipEngine().get_mainBodyAttachPoint();
            PointF attachment = _gameShip.get_shipEngine().get_attachPointF();

            float shipPart_x = (_gameShip.get_shipEngine().get_imageWidth())/2.0f;
            float shipPart_y = (_gameShip.get_shipEngine().get_imageHeight())/2.0f;
            PointF partCenter = new PointF(shipPart_x, shipPart_y);

            PointF mainOffset = GraphicsUtils.subtract(attachmentMain, mainBodyCenter );
            PointF partOffset = GraphicsUtils.subtract(partCenter, attachment);

            PointF finalOffset = GraphicsUtils.add(partOffset,mainOffset);
            finalOffset.set(_SHIPBUILDERSCALE*finalOffset.x, _SHIPBUILDERSCALE*finalOffset.y);
            PointF position = GraphicsUtils.add(_position, finalOffset);
            _gameShip.get_shipEngine().set_position(position);

        }
    }
    //____________________________________________________________________________________________//



    //-----------------------------------DRAW FUNCTION--------------------------------------------//
    public void draw(){
        if(_gameShip.get_shipMainBody() != null){
            drawMainBody();
        }
        if(_gameShip.get_shipCannon() != null){
            drawCannon();
        }
        if(_gameShip.get_shipExtraPart() != null){
            drawExtraPart();
        }
        if(_gameShip.get_shipEngine() != null){
            drawEngine();
        }
    }
    //____________________________________________________________________________________________//



    //----------------------------------SHIP PART DRAWING FUNCTIONS-------------------------------//
    private void drawEngine() {
        Engine engine = _gameShip.get_shipEngine();
        PointF position = engine.get_position();
        DrawingHelper.drawImage(engine.get_imageID(), position.x, position.y, 0, _SHIPBUILDERSCALE, _SHIPBUILDERSCALE, 255);
    }

    private void drawExtraPart() {
        ExtraPart extrapart = _gameShip.get_shipExtraPart();
        PointF position = extrapart.get_position();
        DrawingHelper.drawImage(extrapart.get_imageID(), position.x, position.y, 0, _SHIPBUILDERSCALE, _SHIPBUILDERSCALE, 255);
    }

    private void drawCannon() {
        Cannon cannon = _gameShip.get_shipCannon();
        PointF position = cannon.get_position();
        DrawingHelper.drawImage(cannon.get_imageID(), position.x, position.y, 0, _SHIPBUILDERSCALE, _SHIPBUILDERSCALE, 255);

    }

    private void drawMainBody() {
        MainBody mainbody = _gameShip.get_shipMainBody();
        PointF position = mainbody.get_position();
        DrawingHelper.drawImage(mainbody.get_imageID(), position.x, position.y, 0, _SHIPBUILDERSCALE, _SHIPBUILDERSCALE, 255);
    }

    //____________________________________________________________________________________________//



    //---------------------------------SETTERS AND GETTERS----------------------------------------//
    public PointF get_position() {
        return _position;
    }
    public void set_position(PointF _position) {
        this._position = _position;
    }

    public Ship get_gameShip() {
        return _gameShip;
    }
    public void set_gameShip(Ship _gameShip) {
        this._gameShip = _gameShip;
    }
    //____________________________________________________________________________________________//
}
