package gameClient;

import Server.game_service;
import Server.robot;

/**
 * This interface represents a directional weighted MyGameGUI.
 * The interface has a road-system or communication network in mind - and should support a large number of nodes (over 100,000).
 * The implementation should be based on an efficient compact representation (should NOT be based on a n*n matrix).
 *
 */
public interface Gamable {

    /**
     * Builds a scenario get Server
     */
    public void gameInit(int index);

    /**
     * get is number Server
     * Game Builder: (Map, Points, Ribs)
     */
    public void builderGame();

    /**
     *  get oll tey Figures end in Game
     */
    public void initFruits();

    /**
     *  update robot data from server
     */
    public void updateRobot();

    public fruits[] getFruits() ;

    public robots[] getRobots();

    /**
     *  Accepts amount of users
     */
    public void initRobot() ;
        /**
         *  Automatic play: Move the players to win best
         */


    public  void run();


}

