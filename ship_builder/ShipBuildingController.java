package edu.byu.cs.superasteroids.ship_builder;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.databaseDao;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.models.Asteroid;
import edu.byu.cs.superasteroids.models.AsteroidsGame;
import edu.byu.cs.superasteroids.models.Ship;
import edu.byu.cs.superasteroids.models.ShipBuilder;
// Me quede en la primer funcion on view loaded
/**
 * Created by benjamin on 16/10/16.
 */
public class ShipBuildingController implements IShipBuildingController {
    private ShipBuildingActivity ShipBActivity;

    private IShipBuildingView.PartSelectionView MAIN_BODY = IShipBuildingView.PartSelectionView.MAIN_BODY;
    private IShipBuildingView.PartSelectionView ENGINE = IShipBuildingView.PartSelectionView.ENGINE;
    private IShipBuildingView.PartSelectionView POWER_CORE = IShipBuildingView.PartSelectionView.POWER_CORE;
    private IShipBuildingView.PartSelectionView EXTRA_PART = IShipBuildingView.PartSelectionView.EXTRA_PART;
    private IShipBuildingView.PartSelectionView CANNON = IShipBuildingView.PartSelectionView.CANNON;

    private IShipBuildingView.ViewDirection RIGHT = IShipBuildingView.ViewDirection.RIGHT;
    private IShipBuildingView.ViewDirection LEFT = IShipBuildingView.ViewDirection.LEFT;
    private IShipBuildingView.ViewDirection UP = IShipBuildingView.ViewDirection.UP;
    private IShipBuildingView.ViewDirection DOWN = IShipBuildingView.ViewDirection.DOWN;

    private List<String> main_body_parts = new ArrayList<>();
    private List<String> engine_parts = new ArrayList<>();
    private List<String> power_core_parts = new ArrayList<>();
    private List<String> extra_part_parts = new ArrayList<>();
    private List<String> cannon_parts = new ArrayList<>();

    ShipBuilder _shipBuilder;

    private boolean isMainBodySelected = false;
    private boolean isEngineSelected = false;
    private boolean isPowerCoreSelected = false;
    private boolean isCannonSelected = false;
    private boolean isExtraPartSelected = false;

    private List<Integer> mainBodyImageId;
    private List<Integer> engineImageId;
    private List<Integer> powerCoreImageId;
    private List<Integer> extraPartImageId;
    private List<Integer> cannonsImageId;

    private IShipBuildingView.PartSelectionView state;

    databaseDao _DAO = databaseDao.SINGLETON;
    Ship _game_ship = AsteroidsGame.SINGLETON.get_gameShip();

    public ShipBuildingController(ShipBuildingActivity ShipActivity){
        _shipBuilder = new ShipBuilder();
        ShipBActivity = ShipActivity;
    }

    //---------------------------------ON VIEW LOADED FUNCTION------------------------------------//
    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        state = partView;
        switch (partView)
        {
            case MAIN_BODY:
                ShipBActivity.setArrow(MAIN_BODY, RIGHT, true, "Engine");
                ShipBActivity.setArrow(MAIN_BODY, LEFT, true, "Power Core");
                ShipBActivity.setArrow(MAIN_BODY, UP, false, null);
                ShipBActivity.setArrow(MAIN_BODY, DOWN, false, null);
                break;

            case ENGINE:
                ShipBActivity.setArrow(ENGINE, RIGHT, true, "Cannon");
                ShipBActivity.setArrow(ENGINE, LEFT, true, "Main Body");
                ShipBActivity.setArrow(ENGINE, UP, false, null);
                ShipBActivity.setArrow(ENGINE, DOWN, false, null);
                break;

            case CANNON:
                ShipBActivity.setArrow(CANNON, RIGHT, true, "Extra Part");
                ShipBActivity.setArrow(CANNON, LEFT, true, "Engine");
                ShipBActivity.setArrow(CANNON, UP, false, null);
                ShipBActivity.setArrow(CANNON, DOWN, false, null);
                break;

            case EXTRA_PART:
                ShipBActivity.setArrow(EXTRA_PART, RIGHT, true, "Power Core");
                ShipBActivity.setArrow(EXTRA_PART, LEFT, true, "Cannon");
                ShipBActivity.setArrow(EXTRA_PART, UP, false, null);
                ShipBActivity.setArrow(EXTRA_PART, DOWN, false, null);
                break;

            case POWER_CORE:
                ShipBActivity.setArrow(POWER_CORE, RIGHT, true, "Main Body");
                ShipBActivity.setArrow(POWER_CORE, LEFT, true, "Extra Part");
                ShipBActivity.setArrow(POWER_CORE, UP, false, null);
                ShipBActivity.setArrow(POWER_CORE, DOWN, false, null);
                break;
            default:
        }
    }
    //____________________________________________________________________________________________//



    //--------------------------------UPDATE AND DRAW FUNCTIONS-----------------------------------//
    @Override
    public void update(double elapsedTime) {
        _shipBuilder.update();
        if(isMainBodySelected && isPowerCoreSelected && isExtraPartSelected && isCannonSelected && isEngineSelected){
            ShipBActivity.setStartGameButton(true);
        }
    }
    @Override
    public void draw() {
        float x = DrawingHelper.getCanvas().getWidth()/2.0f;
        float y = DrawingHelper.getCanvas().getHeight()/2.0f;
        PointF position = new PointF(x,y);

        _shipBuilder.set_position(position);
        _shipBuilder.draw();
    }
    //____________________________________________________________________________________________//



    //-------------------------------LOAD IMAGES--------------------------------------------------//
    @Override
    public void loadContent(ContentManager content) {

        main_body_parts = _DAO.getMainBodyPathList();
        mainBodyImageId = content.loadImages(main_body_parts);
        ShipBActivity.setPartViewImageList(MAIN_BODY, mainBodyImageId);

        engine_parts = _DAO.getEnginesPathList();
        engineImageId = content.loadImages(engine_parts);
        ShipBActivity.setPartViewImageList(ENGINE, engineImageId);

        power_core_parts = _DAO.getPowerCoresPathsList();
        powerCoreImageId = content.loadImages(power_core_parts);
        ShipBActivity.setPartViewImageList(POWER_CORE, powerCoreImageId);

        extra_part_parts = _DAO.getExtraPartPathList();
        extraPartImageId = content.loadImages(extra_part_parts);
        ShipBActivity.setPartViewImageList(EXTRA_PART, extraPartImageId);

        cannon_parts = _DAO.getCannonsPathList();
        cannonsImageId = content.loadImages(cannon_parts);
        ShipBActivity.setPartViewImageList(CANNON, cannonsImageId);
    }
    //____________________________________________________________________________________________//



    @Override
    public void unloadContent(ContentManager content) {}

    //-------------------------------ON SLIDE VIEW------------------------------------------------//
    /**
     *      It animates what ever view follows when the user swipes the screen.
     * */
    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        IShipBuildingView.ViewDirection oppositeDirection = getOppositeDirection(direction);
        IShipBuildingView.PartSelectionView nextView = getWhatsInThatDirection(state, oppositeDirection);
        ShipBActivity.animateToView(nextView, oppositeDirection);
    }
    //____________________________________________________________________________________________//



    //--------------------------------GET WHAT IS IN DIRECTION------------------------------------//
    /**
     *      It tells you what is in the direction the the user swipes!
     * */
    public IShipBuildingView.PartSelectionView getWhatsInThatDirection(IShipBuildingView.PartSelectionView currentView, IShipBuildingView.ViewDirection direction){
        IShipBuildingView.PartSelectionView thisIsInThatDirection = null;
        switch (state){
            case MAIN_BODY:
                if(direction == RIGHT){
                    thisIsInThatDirection = ENGINE;
                }
                else{
                    thisIsInThatDirection = POWER_CORE;
                }
                break;
            case ENGINE:
                if(direction == RIGHT){
                    thisIsInThatDirection = CANNON;
                }
                else{
                    thisIsInThatDirection = MAIN_BODY;
                }
                break;
            case CANNON:
                if(direction == RIGHT){
                    thisIsInThatDirection = EXTRA_PART;
                }
                else{
                    thisIsInThatDirection = ENGINE;
                }
                break;
            case EXTRA_PART:
                if(direction == RIGHT){
                    thisIsInThatDirection = POWER_CORE;
                }
                else{
                    thisIsInThatDirection = CANNON;
                }
                break;
            case POWER_CORE:
                if(direction == RIGHT){
                    thisIsInThatDirection = MAIN_BODY;
                }
                else{
                    thisIsInThatDirection = EXTRA_PART;
                }
                break;
            default:
        }
        return thisIsInThatDirection;
    }
    //____________________________________________________________________________________________//



    //---------------------------------GET OPPOSITE DIRECTION-------------------------------------//
    /**
     *      For some reason when the user swipes the screen the controller tells you the wrong way.
     *      For example if the user swipes left the controller tells you right. This function will revert
     *      the bad behaviour of the controller.
     * */
    private IShipBuildingView.ViewDirection getOppositeDirection(IShipBuildingView.ViewDirection direction) {
        switch(direction){
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
        }
        return null;
    }
    //____________________________________________________________________________________________//



    //--------------------------------PART SELECTION----------------------------------------------//
    /**
     *      Every time a part is selection that selection is stored i the ShipBuilder model.
     *      When the game start information is passed from the ShipBuilder model to the game model.
     *      In this case the ShipBuilder model draws and updates the ship when the user is building
     *      the ship and the game model draws and updates the ship when the user is playing the game.
     *
     * */
    @Override
    public void onPartSelected(int index) {
        switch (state)
        {
            case MAIN_BODY:
                _shipBuilder.get_gameShip().set_shipMainBody(_DAO.getMainBodyShips().get(index));
                _shipBuilder.get_gameShip().get_shipMainBody().set_imageID(mainBodyImageId.get(index));

                _game_ship.set_shipMainBody(_DAO.getMainBodyShips().get(index));
                _game_ship.get_shipMainBody().set_imageID(mainBodyImageId.get(index));
                isMainBodySelected = true;
                break;

            case ENGINE:
                _shipBuilder.get_gameShip().set_shipEngine(_DAO.getEngines().get(index));
                _shipBuilder.get_gameShip().get_shipEngine().set_imageID(engineImageId.get(index));

                _game_ship.set_shipEngine(_DAO.getEngines().get(index));
                _game_ship.get_shipEngine().set_imageID(engineImageId.get(index));
                _game_ship.get_shipEngine().setPartOffSet();
                _game_ship.get_shipEngine().set_mainBodyAttachPoint(_game_ship.get_shipMainBody().get_engineAttachPoint());
                isEngineSelected = true;
                break;

            case CANNON:
                _shipBuilder.get_gameShip().set_shipCannon(_DAO.getCannons().get(index));
                _shipBuilder.get_gameShip().get_shipCannon().set_imageID(cannonsImageId.get(index));

                _game_ship.set_shipCannon(_DAO.getCannons().get(index));
                _game_ship.get_shipCannon().set_imageID(cannonsImageId.get(index));
                _game_ship.get_shipCannon().setPartOffSet();
                _game_ship.get_shipCannon().set_mainBodyAttachPoint(_game_ship.get_shipMainBody().get_cannonAttachPoint());
                try {
                    int attackSound = ContentManager.getInstance().loadSound(_game_ship.get_shipCannon().get_attackSound());
                    _game_ship.get_shipCannon().set_attackSoundID(attackSound);
                }
                catch (Exception e){

                }
                isCannonSelected = true;
                break;

            case EXTRA_PART:
                _shipBuilder.get_gameShip().set_shipExtraPart(_DAO.getExtraParts().get(index));
                _shipBuilder.get_gameShip().get_shipExtraPart().set_imageID(extraPartImageId.get(index));

                _game_ship.set_shipExtraPart(_DAO.getExtraParts().get(index));
                _game_ship.get_shipExtraPart().set_imageID(extraPartImageId.get(index));
                _game_ship.get_shipExtraPart().setPartOffSet();
                _game_ship.get_shipExtraPart().set_mainBodyAttachPoint(_game_ship.get_shipMainBody().get_extraAttachPoint());
                isExtraPartSelected = true;
                break;

            case POWER_CORE:
                _shipBuilder.get_gameShip().set_shipPowerCore(_DAO.getPowerCores().get(index));
                _shipBuilder.get_gameShip().get_shipPowerCore().set_imageID(powerCoreImageId.get(index));

                _game_ship.set_shipPowerCore(_DAO.getPowerCores().get(index));
                _game_ship.get_shipPowerCore().set_imageID(powerCoreImageId.get(index));
                isPowerCoreSelected = true;
                break;
            default:
        }

        if( isMainBodySelected && isCannonSelected ) {
            _shipBuilder.get_gameShip().get_shipCannon().set_mainBodyAttachPoint(_shipBuilder.get_gameShip().get_shipMainBody().get_cannonAttachPoint());
        }
        if( isMainBodySelected && isEngineSelected ) {
            _shipBuilder.get_gameShip().get_shipEngine().set_mainBodyAttachPoint(_shipBuilder.get_gameShip().get_shipMainBody().get_engineAttachPoint());
        }
        if( isMainBodySelected && isExtraPartSelected ){
            _shipBuilder.get_gameShip().get_shipExtraPart().set_mainBodyAttachPoint(_shipBuilder.get_gameShip().get_shipMainBody().get_extraAttachPoint());
        }

        if( isMainBodySelected && isCannonSelected && isExtraPartSelected ){
            int diameter = _shipBuilder.get_gameShip().get_shipExtraPart().get_imageWidth();
            diameter += _shipBuilder.get_gameShip().get_shipCannon().get_imageWidth();
            diameter += _shipBuilder.get_gameShip().get_shipMainBody().get_imageWidth();
            Ship ship = AsteroidsGame.SINGLETON.get_gameShip();
            ship.set_diameter((int) (ship.getSHIPBUILDER_SCALE()*diameter));
        }
    }
    //____________________________________________________________________________________________//




    //---------------------------START GAME FUNCTION----------------------------------------------//
    @Override
    public void onStartGamePressed() {
        AsteroidsGame.SINGLETON.clearGameModel();
        String impactSound = "sounds/impact.wav";
        String mainMenuSong = "sounds/SpyHunter.ogg";
        try {
            int impactSoundID = ContentManager.getInstance().loadSound(impactSound);
            int MainMenuSongID = ContentManager.getInstance().loadLoopSound(mainMenuSong);
            ContentManager.getInstance().playLoop(MainMenuSongID);
            Asteroid._impactSoundID = impactSoundID;
        }
        catch(Exception e){}

        ShipBActivity.startGame();
    }
    //____________________________________________________________________________________________//


    @Override
    public void onResume() { }
    @Override
    public IView getView() {
        return null;
    }
    @Override
    public void setView(IView view) { }
}
