package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;

/**
 * Created by benjamin on 10/10/16.
 */
public class RegularAsteroid extends Asteroid{

    //------------------------------------CONSTRUCTOR---------------------------------------------//
    public RegularAsteroid() {
        _noOfTimesItCanBeBroken = 1;
        _noOfTimesIsHasBroken = 0;
        set_breakable(true);
        _breakInto = 2;
        set_scale(2.0f);
    }
    //____________________________________________________________________________________________//

    private int _noOfTimesItCanBeBroken;
    private int _noOfTimesIsHasBroken;
    private int _breakInto;


    //-----------------------------------DRAW AND UPDATE------------------------------------------//
    public void draw() {
        super.draw();
    }
    public void update(double elapsedTime){
        super.update(elapsedTime);
    }
    //____________________________________________________________________________________________//



    //-----------------------------------DESCENDING FUNCTION--------------------------------------//
    public void collisionDetected(){
        ContentManager.getInstance().playSound(super._impactSoundID, 1, 1);

        int hp = get_hp();
        hp--;
        set_hp(hp);
        if(hp <= 0){
            set_alive(false);

            if(super.is_breakable()) {
                if(++_noOfTimesIsHasBroken >= _noOfTimesItCanBeBroken){
                    super.set_breakable(false);
                }
                for (int i = 0; i < _breakInto; i++) {
                    RegularAsteroid dividedGrowingAsteroid = new RegularAsteroid();
                    dividedGrowingAsteroid.initilizeDirection();
                    dividedGrowingAsteroid.initializeSpeed();

                    /** This copies the asteroid's parent hp and divide's it by 2. I have no idea why is working though
                     *      because a couple lines above from here I subtract to the hp. So if we get here hp should be 0.
                     *      some how is doing this correctly haha. Please remember to change it after semester ends...
                     *
                     * */
                    dividedGrowingAsteroid.set_hp(get_hp() / 2);
                    /**   If you don't create a new pointF it will point to the PointF of the asteroid before it broke.
                     *    Because since PointF is not a primitive type then it will pass a reference to the object.
                     * */
                    PointF position = new PointF(get_position().x, get_position().y);
                    dividedGrowingAsteroid.set_position(position);
                    dividedGrowingAsteroid.set_scale(get_scale() / 2.0f);

                    //Copy parents image properties
                    dividedGrowingAsteroid.set_imageHeight(get_imageHeight());
                    dividedGrowingAsteroid.set_imageWidth(get_imageWidth());
                    dividedGrowingAsteroid.set_imagePath(get_imagePath());
                    dividedGrowingAsteroid.set_imageID(get_imageID());

                    //Copy name
                    dividedGrowingAsteroid.set_name(get_name());
                    dividedGrowingAsteroid.set_type(get_type());

                    //Breakable?
                    dividedGrowingAsteroid.set_noOfTimesIsHasBroken(_noOfTimesIsHasBroken);
                    dividedGrowingAsteroid.set_breakable(is_breakable());

                    AsteroidsGame.SINGLETON.get_asteroidList().add(dividedGrowingAsteroid);
                }
            }
        }
    }
    //____________________________________________________________________________________________//



    //-------------------------------SETTERS AND GETTERS------------------------------------------//
    public int get_noOfTimesItCanBeBroken() {
        return _noOfTimesItCanBeBroken;
    }
    public void set_noOfTimesItCanBeBroken(int _noOfTimesItCanBeBroken) {
        this._noOfTimesItCanBeBroken = _noOfTimesItCanBeBroken;
    }

    public int get_noOfTimesIsHasBroken() {
        return _noOfTimesIsHasBroken;
    }
    public void set_noOfTimesIsHasBroken(int _noOfTimesIsHasBroken) {
        this._noOfTimesIsHasBroken = _noOfTimesIsHasBroken;
    }

    public int get_breakInto() {
        return _breakInto;
    }
    public void set_breakInto(int _breakInto) {
        this._breakInto = _breakInto;
    }
    //____________________________________________________________________________________________//
}
