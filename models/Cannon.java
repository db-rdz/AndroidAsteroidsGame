package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.content.ContentManager;

/**
 * Created by benjamin on 10/10/16.
 */
public class Cannon extends ShipPart{

    Ship _gameShip = AsteroidsGame.SINGLETON.get_gameShip();
    private final String _name = "Ship";

    //---------------------------------POINTS--------------------------------------------//
    /** Coordinate String. The point of the cannon image the projectile is emitted from. */
    private String _emitPoint;
    PointF _bulletPoint = new PointF();
    //_________________________________________________________________________________________//


    //---------------------------------IMAGE---------------------------------------------------//
    /** The path to the cannon’s projectile image. */
    private String _attackImage;
    /** The pixel width of the cannon’s projectile image. */
    private int _attackImageWidth;
    /**  The pixel height of the cannon’s projectile image. */
    private int _attackImageHeight;
    /** TThe ID of the attack image once its loaded */
    private int _attackImageID;
    //_________________________________________________________________________________________//


    //---------------------------------OTHER------------------------------------------------------//
    /**  A list of all the bullets that are fired */
    private List<Bullet> firedBullets = new ArrayList<>();
    /** The path to the cannon’s projectile sound file. */
    private String _attackSound;
    /** The ID of the attack sound once is loaded. */
    private int _attackSoundID;
    /** The base damage for each projectile. */
    private int _damage;

    //____________________________________________________________________________________________//



    //---------------------------------UPDATE FUNCTION--------------------------------------------//
    public void update(double elapsedTime){
        set_ObjectDirectionAngle(_gameShip.get_ObjectDirectionAngle());
        super.update(elapsedTime);
        //Remove bullet if it goes out of bounds or update if it didn't.
        for(int i = 0; i < firedBullets.size(); i++){
            if(!firedBullets.get(i).is_alive()){
                firedBullets.remove(i);
                continue;
            }
            if(firedBullets.get(i).get_position().x > AsteroidsGame.SINGLETON.get_gameWorldMap().get_width() ||
               firedBullets.get(i).get_position().x < 0 ||
               firedBullets.get(i).get_position().y > AsteroidsGame.SINGLETON.get_gameWorldMap().get_height() ||
               firedBullets.get(i).get_position().y < 0 ){
               firedBullets.remove(i);
            }
            else {
                firedBullets.get(i).update(elapsedTime);
            }
        }
    }
    //____________________________________________________________________________________________//



    //---------------------------------DRAW FUNCTION----------------------------------------------//

    public void draw(){
        super.draw(_gameShip.getSHIPBUILDER_SCALE());
        for(int i = 0; i < firedBullets.size(); i++){
            firedBullets.get(i).draw();
        }
    }

    //____________________________________________________________________________________________//


    //---------------------------------FIRE-------------------------------------------------------//
    public void fire() {
        ContentManager.getInstance().playSound(_attackSoundID, 1, 1);

        Bullet new_bullet = new Bullet();
        firedBullets.add(new_bullet);
    }
    //____________________________________________________________________________________________//


    //-----------------------ABSTRACT PARENT METHODS IMPLEMENTATION-------------------------------//
    public  String get_name(){
        return _name;
    }
    public void collisionDetected(){
        _gameShip.collisionDetected();
    }
    //____________________________________________________________________________________________//



    //--------------------------------IMAGE GETTERS AND SETTERS-----------------------------------//

        //-----------------ATTACK IMAGE GETTERS------------------//
    public int get_attackImageHeight() {
        return _attackImageHeight;
    }
    public int get_attackImageWidth() {
        return _attackImageWidth;
    }
    public int get_attackImageID() {
        return _attackImageID;
    }
    public String get_attackImage() { return _attackImage; }

        //-----------------ATTACK IMAGE SETTERS-----------------//
    public void set_attackImageHeight(int attackImageHeight) { _attackImageHeight = attackImageHeight; }
    public void set_attackImageWidth(int attackImageWidth) { _attackImageWidth = attackImageWidth; }
    public void set_attackImageID(int attackImageID) {
        _attackImageID = attackImageID;
    }
    public void set_attackImage(String attackImage) {
        _attackImage = attackImage;
    }


        //-----------------EMMIT POINT----------------------//
    public String get_emitPoint() {
        return _emitPoint;
    }
    public void set_emitPoint(String emitPoint) {
        _emitPoint = emitPoint;
    }
    public PointF get_bulletPoint() { return _bulletPoint; }
    public void set_bulletPoint(PointF bulletPoint) { _bulletPoint = bulletPoint; }

        //-----------------ATTACH SOUND---------------------//
    public String get_attackSound() {
        return _attackSound;
    }
    public void set_attackSound(String attackSound) {
        _attackSound = attackSound;
    }
    public int get_attackSoundID() { return _attackSoundID; }
    public void set_attackSoundID(int _attackSoundID) { this._attackSoundID = _attackSoundID; }

        //-----------------DAMAGE---------------------------//
    public int get_Damage() {
        return _damage;
    }
    public void set_Damage(int damage) {
        _damage = damage;
    }
    //____________________________________________________________________________________________//



}
