package gameClient;

import dataStructure.edge;
import dataStructure.edge_data;
import oop_utils.OOP_Point3D;
import utils.Point3D;

import java.util.Objects;

/**
 * This Class represent a Object Type fruit the implement's fruit's interface
 * That " neede to be Catched " by onother object in a
 * a Gamable Inetefece That can be geted from a server
 */
public class fruit implements fruits {

    Point3D location;
    double value;
    int type;
    edge_data edgeOfFruit;
    double Minweight;

    /**
     *
     * @param fruitValue  the Value  of the fruit
     * @param typ the Type   of the fruit
     * @param pos The Cordinate of the fruit
     */
    public fruit(double fruitValue, int typ, Point3D pos) {
        value=fruitValue;
        type=typ;
        location=pos;

    }

    /**
     *
     * @return The location of the fruit
     */
    @Override
    public Point3D getLocation() {
       return location;
    }

    /**
     *
     * @return The Point of the fruit
     */
    @Override
    public double getValue() {
       return value;
    }

    /**
     *
     * @return The Type of the fruit
     */
    @Override
    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "fruit{" +
                "location=" + location +
                ", value=" + value +
                ", type=" + type +
                '}';
    }
    @Override
    public void setLocation(Point3D location) {
        this.location = location;
    }
    @Override
    public void setValue(double value) {
        this.value = value;
    }
    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public edge_data getEdgeOfFruit() {
        return edgeOfFruit;
    }

    @Override
    public void setEdgeOfFruit(edge_data edgeOfFruit) {
        this.edgeOfFruit=edgeOfFruit;
    }

    @Override
    public void setCoast(double Minweight) {
    this.Minweight=Minweight;
    }

    @Override
    public double getWeight() {
        return Minweight;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        fruit fruit = (fruit) o;
        return Double.compare(fruit.value, value) == 0 &&
                type == fruit.type &&
                Double.compare(fruit.Minweight, Minweight) == 0 &&
                Objects.equals(location, fruit.location) &&
                Objects.equals(edgeOfFruit, fruit.edgeOfFruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, value, type, edgeOfFruit, Minweight);
    }



}
