package gameClient;

import dataStructure.node_data;
import oop_utils.OOP_Point3D;
import utils.Point3D;

import java.io.Serializable;
import java.util.List;


/**
 * This Interface represent a Object Type That "Catch" Anothe object in a
 * a Gamable Inetefece That can be geted from a server
 */
public interface robots extends Serializable {

    /**
     *
     * @return The ID Of the Robot
     */
    int getID();

    void setNextNode(int var1);

    /**
     *
     * @return The Next Node of The Robot
     */
    int getNextNode();


    /**
     *
     * @return The Location Of The Robot In GPS Cordinate
     */
    Point3D getLocation();


    /**
     *
     * @return Return The SrcNode Of The Robot
     */
    void setSrc(int x);

    /**
     *
     * @return
     */
    int getSrcNode();
    /**
     *
     * @return The Value of The Robot
     */
    double getValue();

    /**
     *get the Value of The Robot in server
     * @param va
     */
    void setValue(double va);

    /**
     *
     * @return The Speed of The Robot
     */
    double getSpeed();

    /**
     *
     * @param s-Takes a servant and puts in the Robot
     */
    void setSpeed(double s);
    /**
     *
     * @param location Set's The Location Of The Robot
     */
    void setLocation(Point3D location);

    void setRoute(List<node_data> Route);

    List<node_data> getRoute(int x);

    public void setID(int ID) ;



    public void setSrcNode(int srcNode);

    public int getDestNode() ;

    public void setDestNode(int destNode) ;

    public boolean isOnWay() ;

    public void setOnWay(boolean onWay) ;

    public List<node_data> getRoute() ;

    public void setCoast(double coast);

    public double getCoast();

    public fruits getTarget() ;

    public void setTarget(fruits target) ;



}
