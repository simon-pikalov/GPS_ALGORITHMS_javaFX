package gui;


import Server.Game_Server;
import gameClient.Gamable;
import javafx.scene.Group;

public interface Drawable {

    /**
     *
     * @param game the game server of your game
     */
    public  void init (Gamable game);


    /**
     * draw statistics of the game
     */
    public  void  drawStatistics();


    /**
     * manual game of the game with use as player
     * @param game
     */
    public void menualGame(Group game);

    /**
     * automatic play of the game
     */
    public void automatic();

    /**
     *
     */
    public void play(String[] args) ;

}
