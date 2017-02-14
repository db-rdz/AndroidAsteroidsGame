package edu.byu.cs.superasteroids.main_menu;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.databaseDao;
import edu.byu.cs.superasteroids.models.Asteroid;
import edu.byu.cs.superasteroids.models.AsteroidsGame;
import edu.byu.cs.superasteroids.models.Ship;

/**
 * Created by benjamin on 16/10/16.
 */
public class MainMenuController implements IMainMenuController {

    IMainMenuView MainMenuActivity;
    databaseDao _DAO = databaseDao.SINGLETON;

    Ship _gameShip = AsteroidsGame.SINGLETON.get_gameShip();

    int mainBodyImageId;
    int engineImageId;
    int powerCoreImageId;
    int extraPartImageId;
    int cannonsImageId;
    int cannonAttackSound;
    int impactSoundID;
    int MainMenuSongID;

    MainMenuController(IMainMenuView activity){
        MainMenuActivity = activity;
    }

    @Override

    /** LOAD THE GAME MODEL WITH INFO FROM THE DATABASE */
    public void onQuickPlayPressed() {
        if(_DAO.getMainBodyPathList().size() != 0) {

            loadContent();

            _gameShip.set_shipMainBody(_DAO.getMainBodyShips().get(0));
            _gameShip.get_shipMainBody().set_imageID(mainBodyImageId);

            _gameShip.set_shipEngine(_DAO.getEngines().get(0));
            _gameShip.get_shipEngine().set_imageID(engineImageId);
            _gameShip.get_shipEngine().setPartOffSet();

            _gameShip.set_shipCannon(_DAO.getCannons().get(0));
            _gameShip.get_shipCannon().set_imageID(cannonsImageId);
            _gameShip.get_shipCannon().set_attackSoundID(cannonAttackSound);
            _gameShip.get_shipCannon().setPartOffSet();

            _gameShip.set_shipExtraPart(_DAO.getExtraParts().get(0));
            _gameShip.get_shipExtraPart().set_imageID(extraPartImageId);
            _gameShip.get_shipExtraPart().setPartOffSet();

            _gameShip.set_shipPowerCore(_DAO.getPowerCores().get(0));
            _gameShip.get_shipPowerCore().set_imageID(powerCoreImageId);

            _gameShip.set_diameter(
                    (int) (_gameShip.getSHIPBUILDER_SCALE() * (
                            _gameShip.get_shipCannon().get_imageWidth() +
                                    _gameShip.get_shipMainBody().get_imageWidth() +
                                    _gameShip.get_shipExtraPart().get_imageWidth())));

            _gameShip.get_shipCannon().set_mainBodyAttachPoint(_gameShip.get_shipMainBody().get_cannonAttachPoint());
            _gameShip.get_shipEngine().set_mainBodyAttachPoint(_gameShip.get_shipMainBody().get_engineAttachPoint());
            _gameShip.get_shipExtraPart().set_mainBodyAttachPoint(_gameShip.get_shipMainBody().get_extraAttachPoint());

            Asteroid._impactSoundID = impactSoundID;
            MainMenuActivity.startGame();
        }

    }

    /** LOAD ALL IMAGES */
    void loadContent(){

        AsteroidsGame.SINGLETON.clearGameModel();

        String mainMenuSong = "sounds/SpyHunter.ogg";
        String impactSound = "sounds/impact.wav";

        String main_body_parts = _DAO.getMainBodyPathList().get(0);
        mainBodyImageId = ContentManager.getInstance().loadImage(main_body_parts);

        String engine_parts = _DAO.getEnginesPathList().get(0);
        engineImageId = ContentManager.getInstance().loadImage(engine_parts);

        String power_core_parts = _DAO.getPowerCoresPathsList().get(0);
        powerCoreImageId = ContentManager.getInstance().loadImage(power_core_parts);

        String extra_part_parts = _DAO.getExtraPartPathList().get(0);
        extraPartImageId = ContentManager.getInstance().loadImage(extra_part_parts);

        String cannon_parts = _DAO.getCannonsPathList().get(0);
        cannonsImageId = ContentManager.getInstance().loadImage(cannon_parts);

        String cannon_sound = _DAO.getCannons().get(0).get_attackSound();
        try {
            cannonAttackSound = ContentManager.getInstance().loadSound(cannon_sound);
            MainMenuSongID = ContentManager.getInstance().loadLoopSound(mainMenuSong);
            impactSoundID = ContentManager.getInstance().loadSound(impactSound);
        }
        catch(Exception e){}

        ContentManager.getInstance().playLoop(MainMenuSongID);
    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {

    }
}
