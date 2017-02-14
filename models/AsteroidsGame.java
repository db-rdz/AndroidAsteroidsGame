package edu.byu.cs.superasteroids.models;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by benjamin on 10/10/16.
 */
public class AsteroidsGame {

    public static AsteroidsGame SINGLETON = new AsteroidsGame();

    //----------------------------------CLASS SPECIFIC MEMBERS-------------------//
    private int _currentLevel = 1;
    private int _destroyedAsteroids = 0;
    private QuadTree _quadTree = new QuadTree(0, new Rect(0,0,0,0));
    private Ship _gameShip = new Ship();
    private Level _gameLevel = new Level();
    private World _gameWorldMap = new World();
    private MiniMap _gameMiniMap = new MiniMap();
    private ViewPort _gameViewPort = new ViewPort();
    private boolean _hint = false;
    private double _gameplayTime = 0.0;

    //__________________________________________________________________________//


    //----------------------------------LISTS-----------------------------------//

        //----------------ASTEROIDS-----------------//
    private List<Asteroid> _asteroidList = new ArrayList<>();
    private List<GrowingAsteroid> _GrowingAsteroidList = new ArrayList<>();
    private List<OcteroidAsteroid> _OcteroidList = new ArrayList<>();
    private List<RegularAsteroid> _RegularAsteroidList = new ArrayList<>();

        //-------------LEVEL OBJECTS-----------------//
    private List<LevelObject> _levelObjects = new ArrayList<>();

        //-------------BACKGROUND OBJECTS------------//
    private List<BackgroundObject> _BgObjects = new ArrayList<>();

    //------------------------------------------//
    private List<MovingObject> _allMovingObjects = new ArrayList<>();

    //___________________________________________________________________________//

    private Map<Integer, Integer> asteroidToQuantity = new HashMap<>();


    //-----------------------------------DRAW FUNCTION---------------------------------------------------//
    public void draw() {
        if(_hint == false){
            DrawingHelper.drawCenteredText(_gameLevel.get_hint() + ". Don't let Hillary get ya!", 20, -334477);
        }
        else {
            _gameWorldMap.draw();
            for (int i = 0; i < _BgObjects.size(); i++) {
                _BgObjects.get(i).draw();
            }
            _gameMiniMap.draw();
            for (int i = 0; i < _asteroidList.size(); i++) {
                _asteroidList.get(i).draw();
            }
            _gameShip.draw();
        }
    }
    //___________________________________________________________________________________________________//


    //-----------------------------------UPDATE FUNCTION------------------------------------------------//
    public void update(double elapsedTime){

        if(_hint == false) {
            _gameplayTime += elapsedTime;
            if (_gameplayTime > 5) {
                _hint = true;
                _gameplayTime = 0;
            }
        }
        else {
            _quadTree.clear();
            _gameViewPort.update();
            _gameShip.update(elapsedTime);
            for (int i = 0; i < _asteroidList.size(); i++) {
                if (!_asteroidList.get(i).is_alive()) {
                    _asteroidList.remove(i);
                    _destroyedAsteroids++;
                    continue;
                }
                _asteroidList.get(i).update(elapsedTime);
            }
            _gameMiniMap.update(elapsedTime);
        }
    }
    //____________________________________________________________________________________________________//

    public void clearGameModel(){

        //----------------------------------CLASS SPECIFIC MEMBERS-------------------//
        _currentLevel = 1;
        _destroyedAsteroids = 0;
        _quadTree = new QuadTree(0, new Rect(0,0,0,0));
        _gameLevel = new Level();
        _gameWorldMap = new World();
        _gameMiniMap = new MiniMap();
        _gameViewPort = new ViewPort();

        //__________________________________________________________________________//


        //----------------------------------LISTS-----------------------------------//

        //----------------ASTEROIDS-----------------//
        _asteroidList = new ArrayList<>();
        _GrowingAsteroidList = new ArrayList<>();
        _OcteroidList = new ArrayList<>();
        _RegularAsteroidList = new ArrayList<>();

        //-------------LEVEL OBJECTS-----------------//
       _levelObjects = new ArrayList<>();

        //-------------BACKGROUND OBJECTS------------//
        _BgObjects = new ArrayList<>();

        //------------------------------------------//
        _allMovingObjects = new ArrayList<>();

        //___________________________________________________________________________//

       asteroidToQuantity = new HashMap<>();

    }

    //-----------------------------------MODIFIERS--------------------------------------------------------//

        //-------------REGULAR ASTEROID LIST ADD---------------//
    public void addRegularAsteroid(RegularAsteroid asteroidObj) { _RegularAsteroidList.add(asteroidObj); }

        //-------------GROWING ASTEROID LIST ADD---------------//
    public void addGrowingAsteroid(GrowingAsteroid asteroidObj) { _GrowingAsteroidList.add(asteroidObj); }

        //-------------OCTEROID ASTEROID LIST ADD--------------//
    public void addOcteroidAsteroid(OcteroidAsteroid asteroidObj) { _OcteroidList.add(asteroidObj); }

        //-------------ALL ASTEROID LIST ADD------------------//
    public void addAsteroid(Asteroid asteroidObj) {
        _asteroidList.add(asteroidObj);
    }

    //-----------------BACKGROUND OBJECT LIST ADD-------------//
    public void addBackgroundObject(BackgroundObject backgroundObject) { _BgObjects.add(backgroundObject); }

    //___________________________________________________________________________________________________//


    //-----------------------------------LISTS SETTERS AND GETTERS---------------------------------------//
        //------------ALL ASTEROID LIST-----------------------//
    public void set_asteroidList(List<Asteroid> _asteroidList) { this._asteroidList = _asteroidList; }
    public List<Asteroid> get_asteroidList() {
        return _asteroidList;
    }


    //------------GROWING ASTEROID LIST-------------------//
    public void set_GrowingAsteroidList(List<GrowingAsteroid> _GrowingAsteroidList) { this._GrowingAsteroidList = _GrowingAsteroidList; }
    public List<GrowingAsteroid> get_GrowingAsteroidList() {
        return _GrowingAsteroidList;
    }


        //------------REGULAR ASTEROID LIST-------------------//
    public void set_RegularAsteroidList(List<RegularAsteroid> _RegularAsteroidList) { this._RegularAsteroidList = _RegularAsteroidList; }
    public List<RegularAsteroid> get_RegularAsteroidList() {
        return _RegularAsteroidList;
    }


        //------------OCTEROID ASTEROID LiST-----------------//
    public void set_OcteroidList(List<OcteroidAsteroid> _OcteroidList) { this._OcteroidList = _OcteroidList; }
    public List<OcteroidAsteroid> get_OcteroidList() {
            return _OcteroidList;
        }

    public List<MovingObject> get_allMovingObjects() { return _allMovingObjects; }

    public void set_allMovingObjects(List<MovingObject> allMovingObjects) { _allMovingObjects = allMovingObjects; }

    //__________________________________________________________________________________________________//



    //------------------------------------LEVEL SETTERS AND GETTERS-------------------------------------//
        //---------CURRENT LEVEL INDEX----------//
    public void set_currentLevel(int _currentLevel) {
        this._currentLevel = _currentLevel;
    }
    public int get_currentLevel() {
        return _currentLevel;
    }

        //---------ACTUAL LEVEL OBJECT----------//
    public void set_GameLevel(Level gameLevels) { this._gameLevel = gameLevels; }
    public Level get_GameLevel() {
            return _gameLevel;
        }

        //---------LEVEL TO BACKGROUND OBJECT---//
    public void set_levelObjects(List<LevelObject> _levelObjects) { this._levelObjects = _levelObjects; }
    public List<LevelObject> get_levelObjects() { return _levelObjects; }

    public int get_destroyedAsteroids() { return _destroyedAsteroids; }
    public void set_destroyedAsteroids(int destroyAsteroids) { _destroyedAsteroids = destroyAsteroids; }

    public boolean is_hint() { return _hint; }
    public void set_hint(boolean hint) { _hint = hint; }

    //__________________________________________________________________________________________________//



    //------------------------------------GAME SHIP SETTERS AND GETTERS---------------------------------//
    public void set_gameShip(Ship _gameShip) {
        this._gameShip = _gameShip;
    }
    public Ship get_gameShip() {
        return _gameShip;
    }
    //__________________________________________________________________________________________________//



    //------------------------------------MINI MAP SETTERS AND GETTERS----------------------------------//
    public void set_gameMiniMap(MiniMap _gameMiniMap) {
        this._gameMiniMap = _gameMiniMap;
    }
    public MiniMap get_gameMiniMap() {
        return _gameMiniMap;
    }

    public World get_gameWorldMap() { return _gameWorldMap; }
    public void set_gameWorldMap(World gameWorldMap) { _gameWorldMap = gameWorldMap; }

    public ViewPort get_gameViewPort() { return _gameViewPort; }
    public void set_gameViewPort(ViewPort gameViewPort) { _gameViewPort = gameViewPort; }
    //__________________________________________________________________________________________________//


    //------------------------------------QUAD TREE SETTER AND GETTER-----------------------------//
    public QuadTree get_quadTree() { return _quadTree; }
    public void set_quadTree(QuadTree quadTree) { _quadTree = quadTree; }



    public Map<Integer, Integer> getAsteroidToQuantity() {
        return asteroidToQuantity;
    }
    public void setAsteroidToQuantity(Map<Integer, Integer> asteroidToQuantity) {
        this.asteroidToQuantity = asteroidToQuantity;
    }
    public void addAsteroidToQuantityRelation(int asteroidID, int quantity){
        asteroidToQuantity.put(asteroidID, quantity);
    }

}
