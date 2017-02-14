package edu.byu.cs.superasteroids.importer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.database.databaseDao;

/**
 * Created by benjamin on 15/10/16.
 */
public class DataImporter implements IGameDataImporter {

    private static final String GAME_OBJECT_TAG = "asteroidsGame";
    private static final String BG_OBJECTS_TAG = "objects";
    private static final String ASTEROIDS_TAG = "asteroids";
    private static final String LEVELS_TAG = "levels";
    private static final String LEVEL_ASTEROID_TAG = "levelAsteroids";
    private static final String MAIN_BODIES_TAG = "mainBodies";
    private static final String CANNONS_TAG = "cannons";
    private static final String EXTRA_PARTS_TAG = "extraParts";
    private static final String ENGINES_TAG = "engines";
    private static final String POWER_CORES_TAG = "powerCores";
    private static final String LEVEL_OBJECTS_TAG = "levelObjects";


    @Override
    public boolean importData(InputStreamReader dataInputReader) {

        BufferedReader bf = new BufferedReader( dataInputReader );
        StringBuilder sb = new StringBuilder();

        try{
            String JSON_LINE_STRING = bf.readLine();
            while (JSON_LINE_STRING != null){
                sb.append(JSON_LINE_STRING);
                sb.append("\n");
                JSON_LINE_STRING = bf.readLine();
            }
        }
        catch (IOException e){

        }

        String JSON_Object_String = sb.toString();

        try {
            JSONObject jsonObj = new JSONObject(JSON_Object_String);
            JSONObject gameObj = jsonObj.getJSONObject(GAME_OBJECT_TAG);

            parseBackgroundObjects(gameObj);
            parseAsteroidsObjects(gameObj);
            parseLevelObjects(gameObj);
            parseMainBodies(gameObj);
            parseCannons(gameObj);
            parseExtraParts(gameObj);
            parseEngines(gameObj);
            parsePowerCores(gameObj);

        }
        catch (JSONException e){
            return false;
        }
        return true;
    }

    public void parseBackgroundObjects(JSONObject objectTree){
        try{
            JSONArray jsonOBJ = objectTree.getJSONArray(BG_OBJECTS_TAG);
            for(int i = 0; i < jsonOBJ.length(); i++){
                databaseDao.SINGLETON.addBackGroundObject(jsonOBJ.getString(i));
            }
        }
        catch(JSONException e){
        }
    }

    public void parseAsteroidsObjects(JSONObject objectTree){
        try{
            JSONArray jsonOBJ = objectTree.getJSONArray(ASTEROIDS_TAG);
            for(int i = 0; i < jsonOBJ.length(); i++){
                JSONObject asteroid = jsonOBJ.getJSONObject(i);
                databaseDao.SINGLETON.addAsteroid(asteroid);
            }
        }
        catch(JSONException e){
        }
    }

    public void parseLevelObjects(JSONObject objectTree){
        List<JSONObject> levelObjectList = new ArrayList<>();
        List<JSONObject> levelAsteroidsList = new ArrayList<>();
        try{
            JSONArray jsonOBJ = objectTree.getJSONArray(LEVELS_TAG);
            for(int i = 0; i < jsonOBJ.length(); i++){
                JSONObject level = jsonOBJ.getJSONObject(i);
                int level_number = level.getInt("number");
                JSONArray levelObjects = level.getJSONArray(LEVEL_OBJECTS_TAG);
                JSONArray levelAsteroids = level.getJSONArray(LEVEL_ASTEROID_TAG);

                for(int j = 0; j < levelObjects.length(); j++){
                    levelObjectList.add(levelObjects.getJSONObject(j));
                }

                for(int j = 0; j < levelAsteroids.length(); j++){
                    levelAsteroidsList.add(levelAsteroids.getJSONObject(j));
                }

                databaseDao.SINGLETON.addLevel(level, levelObjectList, levelAsteroidsList);
            }
        }
        catch(JSONException e){
        }
    }

    public void parseMainBodies(JSONObject object){
        try {
            JSONArray objectTree = object.getJSONArray(MAIN_BODIES_TAG);
            for (int i = 0; i < objectTree.length(); i++) {
                databaseDao.SINGLETON.addMainBody( objectTree.getJSONObject(i) );
            }
        }
        catch(JSONException e){

        }
    }

    public void parseCannons(JSONObject object){
        try {
            JSONArray objectTree = object.getJSONArray(CANNONS_TAG);
            for (int i = 0; i < objectTree.length(); i++) {
                databaseDao.SINGLETON.addCannon( objectTree.getJSONObject(i) );
            }
        }
        catch(JSONException e){

        }
    }

    public void parseExtraParts(JSONObject object){
        try {
            JSONArray objectTree = object.getJSONArray(EXTRA_PARTS_TAG);
            for (int i = 0; i < objectTree.length(); i++) {
                databaseDao.SINGLETON.addExtraPart( objectTree.getJSONObject(i) );
            }
        }
        catch(JSONException e){

        }
    }

    public void parseEngines(JSONObject object){
        try {
            JSONArray objectTree = object.getJSONArray(ENGINES_TAG);
            for (int i = 0; i < objectTree.length(); i++) {
                databaseDao.SINGLETON.addEngine( objectTree.getJSONObject(i) );
            }
        }
        catch(JSONException e){

        }
    }

    public void parsePowerCores(JSONObject object){
        try {
            JSONArray objectTree = object.getJSONArray(POWER_CORES_TAG);
            for (int i = 0; i < objectTree.length(); i++) {
                databaseDao.SINGLETON.addPowerCore( objectTree.getJSONObject(i) );
            }
        }
        catch(JSONException e){

        }
    }
}
