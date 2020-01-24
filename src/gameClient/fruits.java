package gameClient;

import dataStructure.edge;
import dataStructure.edge_data;
import oop_utils.OOP_Point3D;
import utils.Point3D;

import java.io.Serializable;
/**
 * This Interface represent a Object Type That " neede to be Catched " by onother object in a
 * a Gamable Inetefece That can be geted from a server
 */
public interface fruits extends Serializable {

    /**
     *
     * @return The Location of The Current Node
     */
    Point3D getLocation();

    /**
     *
     * @return The value of the corrent Fruit
     */
    double getValue();


    /**
     *
     * @return The Type of the current Fruir
     */
    int getType();


    public void setLocation(Point3D location);

    public void setValue(double value);

    public void setType(int type) ;

    public edge_data getEdgeOfFruit();

    public void setEdgeOfFruit(edge_data edgeOfFruit) ;


    public void setCoast(double Minweight);


    public double getWeight();


    }

