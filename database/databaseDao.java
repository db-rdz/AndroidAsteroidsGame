package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.models.Asteroid;
import edu.byu.cs.superasteroids.models.BackgroundObject;
import edu.byu.cs.superasteroids.models.Cannon;
import edu.byu.cs.superasteroids.models.Engine;
import edu.byu.cs.superasteroids.models.ExtraPart;
import edu.byu.cs.superasteroids.models.GrowingAsteroid;
import edu.byu.cs.superasteroids.models.Level;
import edu.byu.cs.superasteroids.models.LevelAsteroid;
import edu.byu.cs.superasteroids.models.LevelObject;
import edu.byu.cs.superasteroids.models.MainBody;
import edu.byu.cs.superasteroids.models.OcteroidAsteroid;
import edu.byu.cs.superasteroids.models.PowerCore;
import edu.byu.cs.superasteroids.models.RegularAsteroid;

/**
 * Created by benjamin on 4/10/16.
 */
public class databaseDao {
    /** instance of db */

    public static databaseDao SINGLETON = new databaseDao();
    private static DbOpenHelper _openHelper;
    private SQLiteDatabase _db;

    public databaseDao() {
//        SINGLETON  = new databaseDao(db);
//        this._db = db;
    }

    public static void setDbOpenHelper(DbOpenHelper helper){
        SINGLETON._openHelper = helper;
        SINGLETON._db = SINGLETON._openHelper.getWritableDatabase();
    }

    public static databaseDao getSingletonInstance(){
        return SINGLETON;
    }

    public static SQLiteDatabase getDatabase(){
        return SINGLETON._openHelper.getWritableDatabase();
    }

//------------------------------ASTEROID DAO--------------------------------//

    /** Add an asteroid to the Database */
    public boolean addAsteroid(JSONObject asteroid){
        if (asteroid == null)
        {
            return false;
        }
        try {
            ContentValues values = new ContentValues();
            values.put("name", asteroid.getString("name"));
            values.put("image", asteroid.getString("image"));
            values.put("image_width", asteroid.getInt("imageWidth"));
            values.put("image_height", asteroid.getInt("imageHeight"));
            values.put("type", asteroid.getString("type"));
            long id = SINGLETON._db.insert("Asteroids", null, values);
            if (id >= 0) {
                return true;
            }
        }
        catch(JSONException e){
        }

        return false;
    }

    /** Gives back a list of Asteroids */
    public List<Asteroid> getAsteroids() {
        List<Asteroid> asteroidList = new ArrayList<Asteroid>();
        final String SQL = "SELECT * FROM Asteroids ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Asteroid asteroid;
                String type = cursor.getString(5);
                if(type.contentEquals("regular")) {
                    asteroid = new RegularAsteroid();
                    asteroid.set_id(cursor.getLong(0));
                    asteroid.set_name(cursor.getString(1));
                    asteroid.set_image(cursor.getString(2));
                    asteroid.set_imageWidth(cursor.getInt(3));
                    asteroid.set_imageHeight(cursor.getInt(4));
                    asteroid.set_type(cursor.getString(5));

                    asteroidList.add(asteroid);
                }
                else if(type.contentEquals("growing")) {
                    asteroid = new GrowingAsteroid();
                    asteroid.set_id(cursor.getLong(0));
                    asteroid.set_name(cursor.getString(1));
                    asteroid.set_image(cursor.getString(2));
                    asteroid.set_imageWidth(cursor.getInt(3));
                    asteroid.set_imageHeight(cursor.getInt(4));
                    asteroid.set_type(cursor.getString(5));

                    asteroidList.add(asteroid);
                }
                else if(type.contentEquals("octeroid")) {
                    asteroid = new OcteroidAsteroid();
                    asteroid.set_id(cursor.getLong(0));
                    asteroid.set_name(cursor.getString(1));
                    asteroid.set_image(cursor.getString(2));
                    asteroid.set_imageWidth(cursor.getInt(3));
                    asteroid.set_imageHeight(cursor.getInt(4));
                    asteroid.set_type(cursor.getString(5));

                    asteroidList.add(asteroid);
                }
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return asteroidList;
    }

    public List<Asteroid> getAsteroidsByid(int _id) {
        List<Asteroid> asteroidList = new ArrayList<Asteroid>();
        final String SQL = "SELECT * FROM Asteroids WHERE id = " + _id;
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Asteroid asteroid;
                String type = cursor.getString(5);
                if(type.contentEquals("regular")) {
                    asteroid = new RegularAsteroid();
                    asteroid.set_id(cursor.getLong(0));
                    asteroid.set_name(cursor.getString(1));
                    asteroid.set_image(cursor.getString(2));
                    asteroid.set_imageWidth(cursor.getInt(3));
                    asteroid.set_imageHeight(cursor.getInt(4));
                    asteroid.set_type(cursor.getString(5));

                    asteroidList.add(asteroid);
                }
                else if(type.contentEquals("growing")) {
                    asteroid = new GrowingAsteroid();
                    asteroid.set_id(cursor.getLong(0));
                    asteroid.set_name(cursor.getString(1));
                    asteroid.set_image(cursor.getString(2));
                    asteroid.set_imageWidth(cursor.getInt(3));
                    asteroid.set_imageHeight(cursor.getInt(4));
                    asteroid.set_type(cursor.getString(5));

                    asteroidList.add(asteroid);
                }
                else if(type.contentEquals("octeroid")) {
                    asteroid = new OcteroidAsteroid();
                    asteroid.set_id(cursor.getLong(0));
                    asteroid.set_name(cursor.getString(1));
                    asteroid.set_image(cursor.getString(2));
                    asteroid.set_imageWidth(cursor.getInt(3));
                    asteroid.set_imageHeight(cursor.getInt(4));
                    asteroid.set_type(cursor.getString(5));

                    asteroidList.add(asteroid);
                }
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return asteroidList;
    }

    public void deleteAllAsteroids()
    {
        _db.delete("Asteroids", null, null);
        _db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "Asteroids" + "'");
    }
//____________________________________________________________________//

//----------------------------Ship DAO-------------------------------//


    /** Adds an MainBodyShip to the database */
    public boolean addMainBody(JSONObject mainBody) throws JSONException {
        if (mainBody == null)
        {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("cannon_attach", mainBody.getString("cannonAttach"));
        values.put("engine_attach", mainBody.getString("engineAttach"));
        values.put("extra_attach", mainBody.getString("extraAttach"));
        values.put("image", mainBody.getString("image"));
        values.put("image_width", mainBody.getInt("imageWidth"));
        values.put("image_height", mainBody.getInt("imageHeight"));
        long id = _db.insert("MainBodies", null, values);
        if (id >= 0)
        {
            return true;
        }
        return false;
    }

    /** Gives back a list of MainBody objects */
    public List<MainBody> getMainBodyShips() {
        List<MainBody> mainBodyShipList = new ArrayList<MainBody>();
        final String SQL = "SELECT * FROM MainBodies ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            PointF attachPoint = new PointF();
            float x,y;
            while (!cursor.isAfterLast())
            {
                MainBody mainBodyShip = new MainBody();
                mainBodyShip.set_id(cursor.getInt(0));
                mainBodyShip.set_cannonAttach(cursor.getString(1));
                mainBodyShip.set_engineAttach(cursor.getString(2));;
                mainBodyShip.set_extraAttach(cursor.getString(3));

                String[] cannonAttach = mainBodyShip.get_cannonAttach().split(",");
                x = Float.parseFloat(cannonAttach[0]);
                y = Float.parseFloat(cannonAttach[1]);
                attachPoint = new PointF(x,y);
                mainBodyShip.set_cannonAttachPoint(attachPoint);

                String[] engineAttach = mainBodyShip.get_engineAttach().split(",");
                x = Float.parseFloat(engineAttach[0]);
                y = Float.parseFloat(engineAttach[1]);
                attachPoint = new PointF(x,y);
                mainBodyShip.set_engineAttachPoint(attachPoint);

                String[] extraAttach = mainBodyShip.get_extraAttach().split(",");
                x = Float.parseFloat(extraAttach[0]);
                y = Float.parseFloat(extraAttach[1]);
                attachPoint = new PointF(x,y);
                mainBodyShip.set_extraAttachPoint(attachPoint);

                mainBodyShip.set_image(cursor.getString(4));
                mainBodyShip.set_imageWidth(cursor.getInt(5));
                mainBodyShip.set_imageHeight(cursor.getInt(6));
                mainBodyShipList.add(mainBodyShip);
                cursor.moveToNext();

            }
        }
        finally {
            cursor.close();
        }
        return mainBodyShipList;
    }

    public MainBody getMainBodyPartByIndex(int index) {
        final String SQL = "SELECT * FROM MainBodies where id = '" + index + "'";
        Cursor cursor = _db.rawQuery(SQL, null);
        MainBody mainBodyShip = new MainBody();
        try {
            mainBodyShip.set_id(cursor.getInt(0));
            mainBodyShip.set_cannonAttach(cursor.getString(1));
            mainBodyShip.set_engineAttach(cursor.getString(2));;
            mainBodyShip.set_extraAttach(cursor.getString(3));
            mainBodyShip.set_image(cursor.getString(4));
            mainBodyShip.set_imageWidth(cursor.getInt(5));
            mainBodyShip.set_imageHeight(cursor.getInt(6));
        }
        finally {
            cursor.close();
        }
        return mainBodyShip;
    }

    public List<String> getMainBodyPathList() {
        List<String> MainBodyPathList = new ArrayList<String>();
        final String SQL = "SELECT * FROM MainBodies ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                MainBodyPathList.add(cursor.getString(4));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return MainBodyPathList;
    }



    /** Adds an Cannon to the database */
    public boolean addCannon(JSONObject cannon) throws JSONException {
        if (cannon == null)
        {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("attach_point", cannon.getString("attachPoint"));
        values.put("emit_point", cannon.getString("emitPoint"));
        values.put("image", cannon.getString("image"));
        values.put("image_width", cannon.getInt("imageWidth"));
        values.put("image_height", cannon.getInt("imageHeight"));
        values.put("attack_image", cannon.getString("attackImage"));
        values.put("attack_image_width", cannon.getInt("attackImageWidth"));
        values.put("attack_image_height", cannon.getInt("attackImageHeight"));
        values.put("attack_sound", cannon.getString("attackSound"));
        values.put("damage", cannon.getInt("damage"));

        long id = _db.insert("Cannons", null, values);
        if (id >= 0)
        {
            return true;
        }
        return false;
    }

    /** Gives back a list of Cannons */
    public List<Cannon> getCannons() {
        List<Cannon> cannonList = new ArrayList<Cannon>();
        final String SQL = "SELECT * FROM Cannons ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Cannon cannon = new Cannon();
                cannon.set_id(cursor.getLong(0));
                cannon.set_attachPoint(cursor.getString(1));
                cannon.set_emitPoint(cursor.getString(2));

                String[] cannonEmitPoint = cannon.get_emitPoint().split(",");
                float ex = Float.parseFloat(cannonEmitPoint[0]);
                float ey = Float.parseFloat(cannonEmitPoint[1]);
                PointF emitPoint = new PointF(ex,ey);

                cannon.set_bulletPoint(emitPoint);
                cannon.set_image(cursor.getString(3));
                cannon.set_imageWidth(cursor.getInt(4));
                cannon.set_imageHeight(cursor.getInt(5));
                cannon.set_attackImage(cursor.getString(6));
                cannon.set_attackImageWidth(cursor.getInt(7));
                cannon.set_attackImageHeight(cursor.getInt(8));
                cannon.set_attackSound(cursor.getString(9));
                cannon.set_Damage(cursor.getInt(10));

                String[] attachCoordinates = cannon.get_attachPoint().split(",");
                float x = Float.parseFloat(attachCoordinates[0]);
                float y = Float.parseFloat(attachCoordinates[1]);
                PointF attachPoint = new PointF(x,y);

                cannon.set_attachPointF(attachPoint);
                cannonList.add(cannon);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return cannonList;
    }

    public Cannon getCannonByIndex(int index) {
        final String SQL = "SELECT * FROM Cannons ";
        Cursor cursor = _db.rawQuery(SQL, null);
        Cannon cannon = new Cannon();
        try {
            cursor.move(index);
            cannon.set_id(cursor.getLong(0));
            cannon.set_attachPoint(cursor.getString(1));
            cannon.set_emitPoint(cursor.getString(2));
            cannon.set_image(cursor.getString(3));
            cannon.set_imageWidth(cursor.getInt(4));
            cannon.set_imageHeight(cursor.getInt(5));
            cannon.set_attackImage(cursor.getString(6));
            cannon.set_attackImageWidth(cursor.getInt(7));
            cannon.set_attackImageHeight(cursor.getInt(8));
            cannon.set_attackSound(cursor.getString(9));
            cannon.set_Damage(cursor.getInt(10));
        }
        finally {
            cursor.close();
        }
        return cannon;
    }

    public List<String> getCannonsPathList() {
        List<String> CannonsPathList = new ArrayList<String>();
        final String SQL = "SELECT * FROM Cannons ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                CannonsPathList.add(cursor.getString(3));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return CannonsPathList;
    }

    /** Adds an ExtraPart to the database*/
    public boolean addExtraPart(JSONObject extraParts) throws JSONException {
        if (extraParts == null)
        {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("attach_point", extraParts.getString("attachPoint"));
        values.put("image", extraParts.getString("image"));
        values.put("image_width", extraParts.getInt("imageWidth"));
        values.put("image_height", extraParts.getInt("imageHeight"));

        long id = _db.insert("ExtraParts", null, values);
        if (id >= 0)
        {
            return true;
        }
        return false;
    }

    /** Gives back a list of ExtraParts */
    public List<ExtraPart> getExtraParts() {
        List<ExtraPart> extraPartsList = new ArrayList<ExtraPart>();
        final String SQL = "SELECT * FROM ExtraParts ";
        Cursor cursor = _db.rawQuery(SQL, new String[]{});
        try {

            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                ExtraPart extraPart = new ExtraPart();
                extraPart.set_id(cursor.getInt(0));
                extraPart.set_attachPoint(cursor.getString(1));
                extraPart.set_image(cursor.getString(2));
                extraPart.set_imageWidth(cursor.getInt(3));
                extraPart.set_imageHeight(cursor.getInt(4));

                String[] attachCoordinates = extraPart.get_attachPoint().split(",");
                float x = Float.parseFloat(attachCoordinates[0]);
                float y = Float.parseFloat(attachCoordinates[1]);
                PointF attachPoint = new PointF(x,y);

                extraPart.set_attachPointF(attachPoint);
                extraPartsList.add(extraPart);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return extraPartsList;
    }

    public ExtraPart getExtraPartByIndex( int index ) {
        final String SQL = "SELECT * FROM ExtraParts ";
        Cursor cursor = _db.rawQuery(SQL, new String[]{});
        ExtraPart extraPart = new ExtraPart();
        cursor.move(index);
        try {
            extraPart.set_id(cursor.getLong(0));
            extraPart.set_attachPoint(cursor.getString(1));
            extraPart.set_image(cursor.getString(2));
            extraPart.set_imageWidth(cursor.getInt(3));
            extraPart.set_imageHeight(cursor.getInt(4));
        }
        finally {
            cursor.close();
        }
        return extraPart;
    }

    public List<String> getExtraPartPathList() {
        List<String> extraPartPathList = new ArrayList<String>();
        final String SQL = "SELECT * FROM ExtraParts ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                extraPartPathList.add(cursor.getString(2));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return extraPartPathList;
    }




    /** Adds an Engine to the database*/
    public boolean addEngine(JSONObject engine) throws JSONException {
        if (engine == null)
        {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("base_speed", engine.getInt("baseSpeed"));
        values.put("base_turn_rate", engine.getInt("baseTurnRate"));
        values.put("attach_point", engine.getString("attachPoint"));
        values.put("image", engine.getString("image"));
        values.put("image_width", engine.getInt("imageWidth"));
        values.put("image_height", engine.getInt("imageHeight"));

        long id = _db.insert("Engines", null, values);
        if (id >= 0)
        {
            return true;
        }
        return false;
    }

    /** Gives back a list of Engines */
    public List<Engine> getEngines() {
        List<Engine> engineList = new ArrayList<Engine>();
        final String SQL = "SELECT * FROM Engines ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Engine engine = new Engine();
                engine.set_id(cursor.getLong(0));
                engine.set_baseSpeed(cursor.getInt(1));
                engine.set_baseTurnRate(cursor.getInt(2));
                engine.set_attachPoint(cursor.getString(3));
                engine.set_image(cursor.getString(4));
                engine.set_imageWidth(cursor.getInt(5));
                engine.set_imageHeight(cursor.getInt(6));

                String[] attachCoordinates = engine.get_attachPoint().split(",");
                float x = Float.parseFloat(attachCoordinates[0]);
                float y = Float.parseFloat(attachCoordinates[1]);
                PointF attachPoint = new PointF(x,y);

                engine.set_attachPointF(attachPoint);
                engineList.add(engine);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return engineList;
    }

    public Engine getEngineByIndex(int index) {
        final String SQL = "SELECT * FROM Engines ";
        Cursor cursor = _db.rawQuery(SQL, null);
        Engine engine = new Engine();
        try {
            cursor.move(  index );
            engine.set_id(cursor.getLong(0));
            engine.set_baseSpeed(cursor.getInt(1));
            engine.set_baseTurnRate(cursor.getInt(2));
            engine.set_attachPoint(cursor.getString(3));
            engine.set_image(cursor.getString(4));
            engine.set_imageWidth(cursor.getInt(5));
            engine.set_imageHeight(cursor.getInt(6));
        }
        finally {
            cursor.close();
        }
        return engine;
    }

    public List<String> getEnginesPathList() {
        List<String> enginesPathList = new ArrayList<String>();
        final String SQL = "SELECT * FROM Engines ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                enginesPathList.add(cursor.getString(4));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return enginesPathList;
    }


    /** Adds an PowerCore to the model */
    public boolean addPowerCore(JSONObject powerCore) throws JSONException {
        if (powerCore == null)
        {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("cannon_boost", powerCore.getInt("cannonBoost"));
        values.put("engine_boost", powerCore.getInt("engineBoost"));
        values.put("image", powerCore.getString("image"));

        long id = _db.insert("PowerCores", null, values);
        if (id >= 0)
        {
            return true;
        }
        return false;
    }

    /** Gives back a list of PowerCores */
    public List<PowerCore> getPowerCores() {
        List<PowerCore> powerCoreList = new ArrayList<PowerCore>();
        final String SQL = "SELECT * FROM PowerCores ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                PowerCore powerCore = new PowerCore();
                powerCore.set_id(cursor.getInt(0));
                powerCore.set_cannonBoost(cursor.getInt(1));
                powerCore.set_engineBoost(cursor.getInt(2));
                powerCore.set_image(cursor.getString(3));

                powerCoreList.add(powerCore);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return powerCoreList;
    }

    public PowerCore getPowerCoreByIndex( int index ) {
        final String SQL = "SELECT * FROM PowerCores ";
        Cursor cursor = _db.rawQuery(SQL, null);
        PowerCore powerCore = new PowerCore();
        try {
            cursor.move(index);

            powerCore.set_id(cursor.getLong(0));
            powerCore.set_cannonBoost(cursor.getInt(1));
            powerCore.set_engineBoost(cursor.getInt(2));
            powerCore.set_image(cursor.getString(3));
        }
        finally {
            cursor.close();
        }
        return powerCore;
    }

    public List<String> getPowerCoresPathsList() {
        List<String> powerCorePathList = new ArrayList<String>();
        final String SQL = "SELECT * FROM PowerCores ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                powerCorePathList.add(cursor.getString(3));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return powerCorePathList;
    }


    /** Deltes all Ship information in database */
    public void deleteShipData() {
        _db.delete("MainBodies", null, null);
        _db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "MainBodies" + "'");

        _db.delete("Cannons", null, null);
        _db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "Cannons" + "'");

        _db.delete("ExtraParts", null, null);
        _db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "ExtraParts" + "'");

        _db.delete("Engines", null, null);
        _db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "Engines" + "'");

        _db.delete("PowerCores", null, null);
        _db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "PowerCores" + "'");
    }

//_____________________________________________________________________//

//-----------------------------LEVEL DAO-------------------------------//


    /** Adds a level to the model */
    public boolean addLevel(JSONObject level, List<JSONObject> levelObjects,
                            List<JSONObject> levelAsteroids) throws JSONException {
        if (level == null)
        {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("number", level.getInt("number"));
        values.put("title", level.getString("title"));
        values.put("hint", level.getString("hint"));
        values.put("width", level.getInt("width"));
        values.put("height", level.getInt("height"));
        values.put("music", level.getString("music"));
        long id = _db.insert("Levels", null, values);
        int level_number = level.getInt("number");

        addLevelObjects(levelObjects, (int) id);
        addLevelAsteroids(levelAsteroids, (int) id);

        if (id >= 0)
        {
            return true;
        }
        return false;
    }

    /** Gives back a list of Levels */
    public List<Level> getLevels() {
        List<Level> levelsList = new ArrayList<Level>();
        final String SQL = "SELECT * FROM Levels ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Level level = new Level();
                level.set_id(cursor.getLong(0));
                level.set_levelNumber(cursor.getInt(1));
                level.set_title(cursor.getString(2));
                level.set_hint(cursor.getString(3));
                level.set_width(cursor.getInt(4));
                level.set_height(cursor.getInt(5));
                level.set_music(cursor.getString(6));

                levelsList.add(level);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return levelsList;
    }

    public List<Level> getLevelByIndex(int index) {
        List<Level> levelsList = new ArrayList<Level>();
        final String SQL = "SELECT * FROM Levels WHERE number = " + index;
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Level level = new Level();
                level.set_id(cursor.getLong(0));
                level.set_levelNumber(cursor.getInt(1));
                level.set_title(cursor.getString(2));
                level.set_hint(cursor.getString(3));
                level.set_width(cursor.getInt(4));
                level.set_height(cursor.getInt(5));
                level.set_music(cursor.getString(6));

                levelsList.add(level);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return levelsList;
    }


    /** Adds level objects for a level */
    public boolean addLevelObjects(List<JSONObject> levelObjects, int levelId) throws JSONException {
        for(int i = 0; i < levelObjects.size(); i++)
        {
            JSONObject levelObj = levelObjects.get(i);
            ContentValues levelObjValues = new ContentValues();
            levelObjValues.put("level_id", levelId);
            levelObjValues.put("position", levelObj.getString("position"));
            levelObjValues.put("object_id", levelObj.getInt("objectId"));
            levelObjValues.put("scale", levelObj.getDouble("scale"));
            long levelObjId = _db.insert("LevelObjects", null, levelObjValues);
            if (levelObjId < 0)
            {
                return false;
            }
        }
        return true;
    }

    /** Gives back a list of Background Objects for a level */
    public List<LevelObject> getLevelObjects() {
        List<LevelObject> backGroundObjectList = new ArrayList<LevelObject>();
        final String SQL = "SELECT * FROM LevelObjects ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                LevelObject LevelObject = new LevelObject();
                LevelObject.set_id(cursor.getLong(0));
                LevelObject.set_levelId(cursor.getInt(1));
                LevelObject.set_position(cursor.getString(2));
                LevelObject.set_objectId(cursor.getInt(3));
                LevelObject.set_scale(cursor.getInt(4));
                backGroundObjectList.add(LevelObject);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return backGroundObjectList;
    }

    public List<LevelObject> getLevelObjectsByLevel(int lvl) {
        List<LevelObject> backGroundObjectList = new ArrayList<LevelObject>();
        final String SQL = "SELECT * FROM LevelObjects WHERE level_id = " + lvl;
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                LevelObject LevelObject = new LevelObject();
                LevelObject.set_id(cursor.getLong(0));
                LevelObject.set_levelId(cursor.getInt(1));
                LevelObject.set_position(cursor.getString(2));
                LevelObject.set_objectId(cursor.getInt(3));
                LevelObject.set_scale(cursor.getInt(4));
                backGroundObjectList.add(LevelObject);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return backGroundObjectList;
    }


    /** Adds a level asteroids for a specific level */
    public boolean addLevelAsteroids(List<JSONObject> levelAsteroids, int levelId)
            throws JSONException {
        for(int i = 0; i < levelAsteroids.size(); i++) {
            JSONObject levelAsteroidObj = levelAsteroids.get(i);
            ContentValues levelAsteroidObjValues = new ContentValues();
            levelAsteroidObjValues.put("level_id", levelId);
            levelAsteroidObjValues.put("number", levelAsteroidObj.getInt("number"));
            levelAsteroidObjValues.put("asteroid_id", levelAsteroidObj.getInt("asteroidId"));
            long levelAsteroidObjId = _db.insert("LevelAsteroids", null, levelAsteroidObjValues);
            if (levelAsteroidObjId < 0)
            {
                return false;
            }
        }
        return true;
    }

    /** Gives back a list asteroids for a specific level */
    public List<LevelAsteroid> getLevelAsteroids() {
        //NEEDS AN WHERE ON THE SQL
        List<LevelAsteroid> levelAsteroidsList = new ArrayList<LevelAsteroid>();
        final String SQL = "SELECT * FROM LevelAsteroids ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                LevelAsteroid levelAsteroids = new LevelAsteroid();
                levelAsteroids.set_id(cursor.getLong(0));
                levelAsteroids.set_levelId(cursor.getInt(1));
                levelAsteroids.set_number(cursor.getInt(2));
                levelAsteroids.set_asteroidId(cursor.getInt(3));
                levelAsteroidsList.add(levelAsteroids);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return levelAsteroidsList;
    }

    public List<LevelAsteroid> getLevelAsteroidsByLevel(int lvl) {
        //NEEDS AN WHERE ON THE SQL
        List<LevelAsteroid> levelAsteroidsList = new ArrayList<LevelAsteroid>();
        final String SQL = "SELECT * FROM LevelAsteroids WHERE level_id = " + lvl;
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                LevelAsteroid levelAsteroids = new LevelAsteroid();
                levelAsteroids.set_id(cursor.getLong(0));
                levelAsteroids.set_levelId(cursor.getInt(1));
                levelAsteroids.set_number(cursor.getInt(2));
                levelAsteroids.set_asteroidId(cursor.getInt(3));
                levelAsteroidsList.add(levelAsteroids);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return levelAsteroidsList;
    }

    public void deleteLevelData() {
        _db.delete("Levels", null, null);
        _db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "Levels" + "'");
        _db.delete("LevelObjects", null, null);
        _db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "LevelObjects" + "'");
        _db.delete("LevelAsteroids", null, null);
        _db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "LevelAsteroids" + "'");
    }
//_____________________________________________________________________//


//----------------------------OBJECTS DAO------------------------------//

    /** Adds a Background Object to the model */
    public boolean addBackGroundObject(String backgroundImageLocation) {
        if (backgroundImageLocation == null)
        {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("path", backgroundImageLocation);
        long id = _db.insert("BackgroundObjects", null, values);
        if (id >= 0)
        {
            return true;
        }
        return false;
    }

    /** Gives back a list of BackGroundObjects */
    public List<BackgroundObject> getBackGroundObjects() {
        List<BackgroundObject> backGroundObjectList = new ArrayList<BackgroundObject>();
        final String SQL = "SELECT * FROM BackgroundObjects ";
        Cursor cursor = _db.rawQuery(SQL, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                BackgroundObject backGroundObject = new BackgroundObject();
                backGroundObject.set_id(cursor.getLong(0));
                backGroundObject.set_path(cursor.getString(1));
                backGroundObjectList.add(backGroundObject);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return backGroundObjectList;
    }

    /** Delete all background objects in database */
    public void deleteBackGroundData()
    {
        //final String deleteSQL = "DELETE FROM BackgroundObjects; ";
        //MainGameClass.getDatabase().execSQL("delete from " + "BackgroundObjects"); THIS ONE WORKS
        _db.delete("BackgroundObjects", null, null);
        _db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "BackgroundObjects" + "'");
    }

//_____________________________________________________________________//



}