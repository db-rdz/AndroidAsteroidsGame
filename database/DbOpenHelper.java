package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbOpenHelper extends SQLiteOpenHelper {
    /** represents the name of the database*/
    private static String DB_NAME = "TestingDB2.sqlite";

    /** represents version of the database */
    private static final int DB_VERSION = 1;

    /** constructor will initialize the database */
    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /** Will create database */
    public void onCreate(SQLiteDatabase db) {
        dropTables(db);
        createBackGroundObjectsTable(db);
        createAsteroidsTable(db);
        createLevelsTable(db);
        createLevelObjects(db);
        createLevelAsteroids(db);
        createMainBodiesTable(db);
        createCannonsTable(db);
        createExtraPartsTable(db);
        createEnginesTable(db);
        createPowerCoresTable(db);
    }

    public void dropTables(SQLiteDatabase db) {
        db.execSQL("drop table if exists BackgroundObjects;");
        db.execSQL("drop table if exists Asteroids;");
        db.execSQL("drop table if exists Levels;");
        db.execSQL("drop table if exists LevelObjects;");
        db.execSQL("drop table if exists LevelAsteroids;");
        db.execSQL("drop table if exists MainBodies;");
        db.execSQL("drop table if exists Cannons;");
        db.execSQL("drop table if exists ExtraParts;");
        db.execSQL("drop table if exists Engines;");
        db.execSQL("drop table if exists PowerCores;");
    }

    private void createBackGroundObjectsTable(SQLiteDatabase db) {
        final String SQL = "create table BackgroundObjects " +
                "(" +
                "   id integer not null primary key autoincrement," +
                "   path varchar(255) not null" +
                ")";
        db.execSQL(SQL);
    }

    private void createAsteroidsTable(SQLiteDatabase db) {
        final String SQL = "create table Asteroids " +
                "(" +
                "   id integer not null primary key autoincrement," +
                "   name varchar(255) not null," +
                "   image varchar(255) not null," +
                "   image_width integer not null," +
                "   image_height integer not null," +
                "   type varchar(255) not null" +
                ")";
        db.execSQL(SQL);
    }

    private void createLevelsTable(SQLiteDatabase db) {
        final String SQL = "create table Levels " +
                "(" +
                "   level_id integer not null primary key autoincrement," +
                "   number int not null," +
                "   title varchar(255) not null," +
                "   hint varchar(255) not null," +
                "   width int not null," +
                "   height int not null," +
                "   music varchar(255) not null" +
                ")";
        db.execSQL(SQL);
    }

    private void createLevelObjects(SQLiteDatabase db) {
        final String SQL = "create table LevelObjects " +
                "(" +
                "   id integer not null primary key autoincrement," +
                "   level_id integer not null," +
                "   position varchar(255) not null," +
                "   object_id integer not null," +
                "   scale integer not null" +
                ")";
        db.execSQL(SQL);
    }

    private void createLevelAsteroids(SQLiteDatabase db) {
        final String SQL = "create table LevelAsteroids " +
                "(" +
                "   id integer not null primary key autoincrement," +
                "   level_id int not null," +
                "   number int not null," +
                "   asteroid_id int not null" +
                ")";
        db.execSQL(SQL);
    }

    private void createMainBodiesTable(SQLiteDatabase db) {
        final String SQL = "create table MainBodies " +
                "(" +
                "   id integer not null primary key autoincrement," +
                "   cannon_attach varchar(255) not null," +
                "   engine_attach varchar(255) not null," +
                "   extra_attach varchar(255) not null," +
                "   image varchar(255) not null," +
                "   image_width int not null," +
                "   image_height int not null" +
                ")";
        db.execSQL(SQL);
    }

    private void createCannonsTable(SQLiteDatabase db) {
        final String SQL = "create table Cannons " +
                "(" +
                "   id integer not null primary key autoincrement," +
                "   attach_point int not null," +
                "   emit_point int not null," +
                "   image varchar(255) not null," +
                "   image_width int not null," +
                "   image_height int not null," +
                "   attack_image varchar(255) not null," +
                "   attack_image_width int not null," +
                "   attack_image_height int not null," +
                "   attack_sound varchar(255) not null," +
                "   damage int not null" +
                ")";
        db.execSQL(SQL);
    }

    private void createExtraPartsTable(SQLiteDatabase db) {
        final String SQL = "create table ExtraParts " +
                "(" +
                "   id integer not null primary key autoincrement," +
                "   attach_point int not null," +
                "   image varchar(255) not null," +
                "   image_width int not null," +
                "   image_height int not null" +
                ")";
        db.execSQL(SQL);
    }

    private void createEnginesTable(SQLiteDatabase db) {
        final String SQL = "create table Engines " +
                "(" +
                "   id integer not null primary key autoincrement," +
                "   base_speed int not null," +
                "   base_turn_rate int not null," +
                "   attach_point int not null," +
                "   image varchar(255) not null," +
                "   image_width int not null," +
                "   image_height int not null" +
                ")";
        db.execSQL(SQL);
    }

    private void createPowerCoresTable(SQLiteDatabase db) {
        final String SQL = "create table PowerCores " +
                "(" +
                "   id integer not null primary key autoincrement," +
                "   cannon_boost int not null," +
                "   engine_boost int not null," +
                "   image varchar(255) not null" +
                ")";
        db.execSQL(SQL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}