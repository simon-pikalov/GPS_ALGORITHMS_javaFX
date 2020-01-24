package gameClient;

import Server.game_service;
import dataStructure.edge;
import dataStructure.node_data;
import oop_dataStructure.oop_graph;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.List;

/**
 * Thic class Implement Robot interface and it's an inctence of
 * an Object Type That "Catch" Anothe object in a
 *  * a Gamable Inetefece That can be geted from a server
 */
public class robot implements robots {

    int ID;
    Point3D location;
    int srcNode;
    int destNode;
    double value;
    double speed;
    double coast;
    boolean onWay;
    List<node_data> Route;

    /**
     * The Constractor of the robot
     * @param id The id of the robot
     * @param src The src node of the robot
     * @param dest The dest Node of the robot
     * @param val The Value of the robot
     * @param spe The speed of the robot
     */
    public robot(int id, int src, int dest, double val, double spe) {
        ID = id;
        srcNode = src;
        destNode = dest;
        value = val;
        speed = spe;
        Route=null;
        coast=Double.MAX_VALUE;
        onWay=false;
    }

    /**
     *
     * @return The id of the robot
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     *
     * @param nextID The next id to be seted
     */
    @Override
    public void setNextNode(int nextID) {
        destNode = nextID;
    }

    /**
     *
     * @return the next node of the robot
     */
    @Override
    public int getNextNode() {
        return destNode;
    }

    /**
     *
     * @return The location of th robot
     */
    @Override
    public Point3D getLocation() {
        return this.location;
    }

    /**
     *
     * @return The src node of the robot
     */

    @Override
    public int getSrcNode() {
        return srcNode;
    }

    /**
     *
     * @param x-get Src from servant and puts in the Robot
     */
    @Override
    public void setSrc(int x) {
        this.srcNode=x;
    }
    /**
     *
     * @return The value of the robot
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     *
     * @param va- get Value from servant and puts in the Robot
     */

    @Override
    public void setValue(double va) {
        value=va;
    }

    /**
     *
     * @return The speed of the robot
     */
    @Override
    public double getSpeed() {
        return speed;
    }

    /**
     *
     * @param s- speed of the robot
     */
    @Override
    public void setSpeed(double s){
        speed=s;
    }

    /**
     *
     * @param location Set's The Location Of The Robot
     */
    @Override
    public void setLocation(Point3D location) {
        this.location = location;
    }

    @Override
    public void setRoute(List<node_data> R) {
//    if (Route==null)
    Route=R;
//    else throw  new RuntimeException("robot in thy Route");
    }

    @Override
    public List<node_data> getRoute(int x) {
        if (x==0){
            if (Route.size()<=1) {
                return Route;
            }else{
            Route.remove(0);
            return Route;
        }
            }
        else {return Route;}
    }

    @Override
    public String toString() {
        return "robot{" +
                "ID=" + ID +
                ", location=" + location +
                ", srcNode=" + srcNode +
                ", destNode=" + destNode +
                ", value=" + value +
                ", speed=" + speed +
                ", onWay=" + onWay +
                ", Route=" + Route +
                '}';
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setSrcNode(int srcNode) {
        this.srcNode = srcNode;
    }

    public int getDestNode() {
        return destNode;
    }

    public void setDestNode(int destNode) {
        this.destNode = destNode;
    }

    public boolean isOnWay() {
        return onWay;
    }

    public void setOnWay(boolean onWay) {
        this.onWay = onWay;
    }

    public List<node_data> getRoute() {
        return Route;
    }

    @Override
    public void setCoast(double coast) {
this.coast=coast;
    }

    @Override
    public double getCoast() {
        return coast;
    }




}
