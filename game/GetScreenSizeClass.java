package edu.byu.cs.superasteroids.game;

import edu.byu.cs.superasteroids.base.IGameDelegate;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.models.AsteroidsGame;

/**
 * Created by benjamin on 28/10/16.
 */
public class GetScreenSizeClass implements IGameDelegate {

    public static GetScreenSizeClass SINGLETON = new GetScreenSizeClass();

    @Override
    public void update(double elapsedTime) {

    }

    @Override
    public void loadContent(ContentManager content) {

    }

    @Override
    public void unloadContent(ContentManager content) {

    }

    @Override
    public void draw() {
        AsteroidsGame.SINGLETON.get_gameViewPort().set_height(DrawingHelper.getGameViewHeight());
        AsteroidsGame.SINGLETON.get_gameViewPort().set_width(DrawingHelper.getGameViewWidth());
    }
}
