package gui;


import Server.Game_Server;
import gameClient.Gamable;

public interface Drawable {

    /**
     *
     * @param game the game server of your game
     */
    public  void init (Gamable game);

    /**
     *
     */
    public void play(String[] args) ;

}
