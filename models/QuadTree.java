package edu.byu.cs.superasteroids.models;

import android.graphics.Rect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QuadTree {

    private int MAX_OBJECTS = 10;
    private int MAX_LEVELS = 5;

    private int level;
    private Map<Rect, MovingObject> objectMap;
    private Rect bounds;
    private QuadTree[] nodes;

    //----------------------------------------CONSTRUCTOR-----------------------------------------//
    /**
     *      pLevel is the level at which the rectangle you are passing belongs to. When you start
     *      the QuadTree you pass in a 0 and a rectangle the size of your screen. This will be the
     *      first node.
     * */
    public QuadTree(int pLevel, Rect pBounds) {
        level = pLevel;
        bounds = pBounds;
        nodes = new QuadTree[4];
        objectMap = new HashMap<>();
    }
    //____________________________________________________________________________________________//



    //---------------------------------------CLEAR FUNCTION---------------------------------------//
    public void clear() {
        /**
         *      Will delete every node and every rectangle in the map.
         * */
        objectMap.clear();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    //____________________________________________________________________________________________//



    //---------------------------------------SPLIT FUNCTION---------------------------------------//
    /**
     *      Will add 4 nodes to the current node. Or we can say it will split the current rectangle
     *      into 4 rectangles? I'm not very good explaining...
     * */
    private void split() {
        int subWidth = (int)(bounds.width() / 2);
        int subHeight = (int)(bounds.height() / 2);
        int x = (int)bounds.centerX();
        int y = (int)bounds.centerY();

        nodes[0] = new QuadTree(level+1, new Rect(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new QuadTree(level+1, new Rect(x, y, subWidth, subHeight));
        nodes[2] = new QuadTree(level+1, new Rect(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new QuadTree(level+1, new Rect(x + subWidth, y + subHeight, subWidth, subHeight));
    }
    //____________________________________________________________________________________________//

    /*
 * Determine which node the object belongs to. -1 means
 * object cannot completely fit within a child node and is part
 * of the parent node
 */
    //---------------------------------------GET INDEX FUNCTION-----------------------------------//
    /**
     *      It tells you what node of the current node the passed rectangle variable belongs to.
     * */
    private int getIndex(Rect pRect) {
        int index = -1;

        //----------------------GET THE CENTER OF THE RECTANGLE-----------------//
        double verticalMidpoint = bounds.centerX() + (bounds.width() / 2);
        double horizontalMidpoint = bounds.centerY() + (bounds.height() / 2);

        //----------------------QUADRANT PARTITIONS-----------------------------//
        /** topQuadrant tells you if it is on the top half of the current quadrant */
        /**                   |------------1st boolean------------|    |-----------------------2nd boolean------------------|*/
        boolean topQuadrant = (pRect.centerY() < horizontalMidpoint && pRect.centerY() + pRect.height() < horizontalMidpoint);
        boolean bottomQuadrant = (pRect.centerY() > horizontalMidpoint);

        /** if the object is on the left side of the current quadrant do... */
        if (pRect.centerX() < verticalMidpoint && pRect.centerX() + pRect.width() < verticalMidpoint) {
            if (topQuadrant) {
                index = 1;
            }
            else if (bottomQuadrant) {
                index = 2;
            }
        }

        /** If the object is on the right side of the current quadrant do... */
        else if (pRect.centerX() > verticalMidpoint) {
            if (topQuadrant) {
                index = 0;
            }
            else if (bottomQuadrant) {
                index = 3;
            }
        }
        return index;
    }

    //____________________________________________________________________________________________//

    /*
 * Insert the object into the quadtree. If the node
 * exceeds the capacity, it will split and add all
 * objects to their corresponding nodes.
 */
    //---------------------------------------INSERT FUNCTION--------------------------------------//
    public void insert(Rect pRect, MovingObject mObject) {

        /** If root node exists then... */
        if (nodes[0] != null) {
            // get where the object belongs to
            int index = getIndex(pRect);

            // if it belongs somewhere then... (-1 means is outside the bounds of any quadrant )
            if (index != -1) {
                nodes[index].insert(pRect, mObject);
                return;
            }
        }

        /** Object map is going to relate a rectangle to a moving object. We need this in the
         *  Asteroids game to be able to retreive a moving object using a rectangle.
         * */
        objectMap.put(pRect, mObject);

        /** The map also tells us how many objects are in a cuadrant. If that number exceeds the limit
         *  then split into 4.
         * */
        if (objectMap.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) {
                split();
            }

            for(Iterator<Map.Entry<Rect, MovingObject>> it = objectMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<Rect, MovingObject> entry = it.next();
                int index = getIndex(entry.getKey());
                if (index != -1) {
                    nodes[index].insert(entry.getKey(), entry.getValue());
                    objectMap.remove(entry);
                }
            }
        }
    }
    //____________________________________________________________________________________________//


    //--------------------------------------RETRIEVE FUNCTION-------------------------------------//
    /**
     *      Sends back a map with all the moving objects and rectangle with which a rectangle could
     *      possibly be colliding with.
     * */
    public Map<Rect, MovingObject> retrieve(Map<Rect, MovingObject> returnObjects, Rect pRect) {
        int index = getIndex(pRect);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, pRect);
        }

        returnObjects.putAll(objectMap);
        return returnObjects;
    }
    //____________________________________________________________________________________________//

}