package edu.byu.cs.superasteroids.game;

import android.graphics.PointF;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.base.IGameDelegate;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.databaseDao;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.models.Asteroid;
import edu.byu.cs.superasteroids.models.AsteroidsGame;
import edu.byu.cs.superasteroids.models.BackgroundObject;
import edu.byu.cs.superasteroids.models.GrowingAsteroid;
import edu.byu.cs.superasteroids.models.Level;
import edu.byu.cs.superasteroids.models.LevelAsteroid;
import edu.byu.cs.superasteroids.models.LevelObject;
import edu.byu.cs.superasteroids.models.MovingObject;
import edu.byu.cs.superasteroids.models.OcteroidAsteroid;
import edu.byu.cs.superasteroids.models.QuadTree;
import edu.byu.cs.superasteroids.models.RegularAsteroid;
import edu.byu.cs.superasteroids.models.Ship;
import edu.byu.cs.superasteroids.models.ViewPort;
import edu.byu.cs.superasteroids.models.World;

/**
 * Created by benjamin on 23/10/16.
 */
public class GameController implements IGameDelegate{

    GameController(){}

    AsteroidsGame _game = AsteroidsGame.SINGLETON;
    List<MovingObject> _movingObjects= new ArrayList<>();
    World _world = _game.get_gameWorldMap();
    ViewPort _viewport = _game.get_gameViewPort();
    Ship _gameShip = AsteroidsGame.SINGLETON.get_gameShip();
    databaseDao _dao = databaseDao.SINGLETON;
    boolean _hint = false;


    //-------------------------------------LOAD MODEL TO THE GAME---------------------------------//
    /**
     *      This loads all the asteroids and level information into the game model.
     *      This could've been broken into more functions to make it more organized.
     *      Please do break it more when semester ends.
     *
     * */
    private void loadModel() {
        //PLEASE CHANGE THIS SO THAT YOU CAN GET LEVEL BY INDEX IN DAO
        int level = _game.get_currentLevel();
        Level lvl = _dao.getLevelByIndex(_game.get_currentLevel()).get(0);

        //--------------SETTING UP LEVEL OBJECT AND WORLD DIMENSIONS-----------------//
        _game.set_GameLevel(lvl);
        _world.set_width(lvl.get_width());
        _world.set_height(lvl.get_height());
        _game.get_gameMiniMap().setMiniMap();

        //--------------INITIALIZING QUAD TREE--------------------------------------//
        int world_width = (int) _world.get_width();
        int word_height = (int) _world.get_height();
        Rect quad_bounds = new Rect(0,0,world_width, word_height);
        _game.set_quadTree( new QuadTree(0, quad_bounds) );


        //--------------SET THE BACKGROUND IMAGE SCALE------------------------------//
        float scale_x = _world.get_width()/_world.get_Bgimagewidth();
        float scale_y = _world.get_height()/_world.get_Bgimageheight();
        _world.set_scale_x(scale_x);
        _world.set_scale_y(scale_y);

        //--------------SET THE BACKGROUND IMAGE POSITION--------------------------//
        _world.set_BGPosition( new PointF(_world.get_width()/2.0f, _world.get_height()/2.0f));

        //--------------SET THE SHIP INITAL POSITION TO THE CENTER OF THE WORLD MAP--//
        _gameShip.set_position(new PointF(_world.get_width()/2.0f, _world.get_height()/2.0f));

        //--------------SETTING UP VIEWPORT VARIABLES-------------------------------//
            //I head that you can only get the width and height from the draw function
            //That is why I did a fake class to set up the viewport width and height:

        //-------------------------------LOAD ASTEROIDS MODEL-------------------------------------//
        List<LevelObject> test = _dao.getLevelObjects();
        List<LevelAsteroid> LeveltoAsteroids = _dao.getLevelAsteroidsByLevel(level);

        for(int i = 0; i < LeveltoAsteroids.size(); i++){
            LevelAsteroid tmpLevelAsteroid = LeveltoAsteroids.get(i);
            int asteroidID = tmpLevelAsteroid.get_asteroidId();
            int quantity = tmpLevelAsteroid.get_number();
            _game.addAsteroidToQuantityRelation(asteroidID, quantity);

            //MAKE THE GAME LOADING WORK FIRST THEN YOU CAN CHANGE THE DAO TO RETURN A SINGLE ASTEROID
            Asteroid temp_asteroid = _dao.getAsteroidsByid(asteroidID).get(0);


            switch(temp_asteroid.get_type()){
                case "regular" :{
                    for(int j = 0; j < quantity; j++) {
                        RegularAsteroid newAsteroid = new RegularAsteroid();
                        newAsteroid.copy(temp_asteroid);
                        newAsteroid.initalizePosition();
                        newAsteroid.initializeSpeed();
                        newAsteroid.initilizeDirection();
                        _game.addAsteroid(newAsteroid);
                    }
                    break;
                }
                case "growing":{
                    for(int j = 0; j < quantity; j++) {
                        GrowingAsteroid newAsteroid = new GrowingAsteroid();
                        newAsteroid.copy(temp_asteroid);
                        newAsteroid.initalizePosition();
                        newAsteroid.initializeSpeed();
                        newAsteroid.initilizeDirection();
                        _game.addAsteroid(newAsteroid);
                    }
                    break;
                }
                case "octeroid" :{
                    for(int j = 0; j < quantity; j++) {
                        OcteroidAsteroid newAsteroid = new OcteroidAsteroid();
                        newAsteroid.copy(temp_asteroid);
                        newAsteroid.initalizePosition();
                        newAsteroid.initializeSpeed();
                        newAsteroid.initilizeDirection();
                        _game.addAsteroid(newAsteroid);
                    }
                    break;
                }
            }
        }

        String[] hint_arr = lvl.get_hint().split(" ");
        int goal = Integer.parseInt(hint_arr[1]);
        lvl.set_goal( _game.get_asteroidList().size() - goal);
    }


    //----------------------------------UPDATE FUNCTION-------------------------------------------//
    @Override
    public void update(double elapsedTime) {
        if(_game.get_asteroidList().size() <= _game.get_GameLevel().get_goal() && _game.is_hint()){
            _game.set_currentLevel(_game.get_currentLevel() + 1);
            loadContent(ContentManager.getInstance());
            _game.set_hint(false);
        }
        _game.update(elapsedTime);
    }
    //____________________________________________________________________________________________//



    //--------------------------------LOAD IMAGES AND SOUNDS--------------------------------------//
    @Override
    public void loadContent(ContentManager content) {
        int level = _game.get_currentLevel();
        _game.get_asteroidList().clear();
        loadModel();


        //-------------------------------LOAD BACKGROUND------------------------------------//
        String world_image_path = _world.get_imagePath();
        int world_image_id = content.loadImage(world_image_path);
        _world.set_imageID(world_image_id);

        //-------------------------------LOAD ASTEROIDS-------------------------------------//
        for(int i = 0; i < _game.get_asteroidList().size(); i++){
            Asteroid temp = _game.get_asteroidList().get(i);
            String image_path = temp.get_imagePath();
            int image_id = content.loadImage(image_path);
            temp.set_imageID(image_id);
        }

        //----------------------------------LOAD BG OBJECTS---------------------------//
        List<LevelObject> LeveltoObject = _dao.getLevelObjectsByLevel(level);

        for(int i = 0; i < LeveltoObject.size(); i++){

            LevelObject tempLvlObject = LeveltoObject.get(i);

            String position = tempLvlObject.get_position();
            String[] position_xy = position.split(",");
            float scale = tempLvlObject.get_scale();
            float position_x = Float.parseFloat(position_xy[0]);
            float position_y = Float.parseFloat(position_xy[0]);

            int object_id = tempLvlObject.get_objectId();
            BackgroundObject tempBgObject = _dao.getBackGroundObjects().get(object_id);
            String image_path = tempBgObject.get_path();
            int image_id = content.loadImage(image_path);

            tempBgObject.set_imageID(image_id);
            tempBgObject.set_position_x(position_x);
            tempBgObject.set_position_y(position_y);
            tempBgObject.set_scale(scale);

            _game.addBackgroundObject(tempBgObject);
        }

        int laser_image_ID = content.loadImage(_gameShip.get_shipCannon().get_attackImage());
        _gameShip.get_shipCannon().set_attackImageID(laser_image_ID);
    }
    //____________________________________________________________________________________________//



    @Override
    public void unloadContent(ContentManager content) {

    }


    @Override
    public void draw()
    {
        //----------------SETTING THE VIEWPORT POSITION----------------//
        /**
         * We are setting the viewport position in this part. Since we cannot get the size of the
         * screen anywhere else in the project it has to be done here. Trying to get the screen size
         * anywhere else will result in a width of 0 and height of 0.
         *
         *    ---------------------------------------(map)
         *    |         * <--------(screen width)/2 |
         *    |         ^ |----------------|        |
         *    |         | |                |        |
         *    |       H/2 |       *(center)|        |
         *    |           ------------------        |
         *    |                                     |
         *    ---------------------------------------
         *
         *    Keep in mind that the Viewport initial position is the center. Our goal is to move it to
         *    where the star is (tha '*' that is not the center). To do that we subtract half the screen width
         *    to the center and half the screen height as shown in the code below:
         *
         * */

        _viewport.set_height(DrawingHelper.getCanvas().getHeight());
        _viewport.set_width(DrawingHelper.getCanvas().getWidth());
        //------------------------------------------------------------//
        _game.draw();
    }
}
