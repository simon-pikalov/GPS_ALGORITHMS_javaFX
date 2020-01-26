package gameClient;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
//import gui.Gui;
import dataStructure.*;
import gui.Gui;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.*;


/**
 * This class represent a Utils for the game  catch The Terrorist.
 * The  class communicate with the server to send and receive data from it ;
 * This game uses Datastucture method's such as Shrtest path and more for the game algorithm
 *
 *
 */
public class killTheTerrorists implements Gamable,Runnable {
    private static game_service server;
    private Graph_Algo GameGraph;
    private  static fruits[] fruits;
    private fruits[] fruitsHistory;
    private static  robots[] robots;
    private int cunter = 0;
    private int scenario;
    private int seem = 0;
    private KML_ kml;
    private  double grade;
    private double maxLevel;
    private int moves;
    private static boolean manual;
    private int userId;





    /**
     * initiate a game instance with the specified scene
     *
     * @param scene the game Scene
     */
    @Override
    public void gameInit(int scene) {
        try {
            scenario = scene;
            server = Game_Server.getServer(scene);
            builderGame();
            initFruits();
            initRobot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * initiate the gameGraph from json with the current scene
     */
    @Override
    public void builderGame() {
        this.GameGraph = new Graph_Algo();
        if (server.equals(null)) throw new RuntimeException("server is empty  ");
        GameGraph.initJson(server.getGraph().toString());
    }

    /**
     * initiate the fruits from json with the current scene
     */
    @Override
    public synchronized void initFruits() {
        fruits = new fruit[server.getFruits().size()];
        int sum = 0;
        try {
            String TEMP = server.getFruits().toString();
            JSONArray temp2 = new JSONArray(TEMP);
            while (sum < temp2.length()) {
                JSONObject Fruit = new JSONObject(temp2.get(sum).toString());
                JSONObject f = new JSONObject(Fruit.getJSONObject("Fruit").toString());
                double FruitValue = f.getDouble("value");
                int type = f.getInt("type");
                Point3D pos = new Point3D(f.getString("pos").toString());
                fruits[sum] = new fruit(FruitValue, type, pos);

                sum++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initedgeFruit();

    }




    /**
     * @return The fruits arr of the cur scene
     */
    @Override
    public fruits[] getFruits() {
        if (this.fruits != null) return fruits;
        throw new RuntimeException("0-fruit ");
    }

    /**
     * @return The Robots arr of the cur scene
     */
    @Override
    public robots[] getRobots() {
        if (this.robots != null) return robots;
        throw new RuntimeException("0-robot ");
    }


    /**
     * Initiate The robot's arr of the curren scene
     */
    @Override
    public void initRobot() {
        robots = new robot[server.getRobots().size()];
        int sum = 0;
        double value, speed;
        int id, type, src, dest;
        Point3D LocationRobot;

        try {
            JSONObject line = new JSONObject(server.toString());
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("robots");
            robots = new robot[rs];
            for (int rob = 0; rob < rs; rob++) {
                if (rob >= fruits.length) break;
                edge_data temp = this.fruits[rob].getEdgeOfFruit();
                if (fruits[rob].getType() == 1)
                    server.addRobot(temp.getDest());
                else server.addRobot(temp.getSrc());
            }
            String TEMP = server.getRobots().toString();
            JSONArray temp2 = new JSONArray(TEMP);
            while (sum < temp2.length()) {
                JSONObject RobotT = new JSONObject(temp2.get(sum).toString());
                JSONObject Robot = new JSONObject(RobotT.getJSONObject("Robot").toString());
                id = Robot.getInt("id");
                value = Robot.getDouble("value");
                src = Robot.getInt("src");
                dest = Robot.getInt("dest");
                speed = Robot.getDouble("speed");
                robots[sum] = new robot(id, src, dest, value, speed);
                robots[sum].setTarget(new Point3D(0,0,0));
                sum++;
            }
        } catch (Exception e) {
            System.out.println("Exception robots");
        }
    }

    /**
     * Initiate The robot's arr of the curren scene
     */

    public synchronized void   updateRobots() {
        int robotsSize = server.getRobots().size();
        int sum = 0;
        double value, speed;
        int src, dest;
        Point3D LocationRobot;

        try {
            JSONObject line = new JSONObject(server.toString());
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("robots");
            String TEMP = server.getRobots().toString();
            JSONArray temp2 = new JSONArray(TEMP);
            while (sum < temp2.length()) {
                JSONObject RobotT = new JSONObject(temp2.get(sum).toString());
                JSONObject Robot = new JSONObject(RobotT.getJSONObject("Robot").toString());
                value = Robot.getDouble("value");
                src = Robot.getInt("src");
                dest = Robot.getInt("dest");
                speed = Robot.getDouble("speed");
                Point3D pos = new Point3D(Robot.getString("pos").toString());
                robots[sum].setSrc(src);
                robots[sum].setSpeed(speed);
                robots[sum].setValue(value);
                robots[sum].setSrc(src);
                robots[sum].setDestNode(dest);
                robots[sum].setLocation(pos);
                sum++;
            }
        } catch (Exception e) {
            System.out.println("Exception robots");
        }
    }


    /**
     * function for deep copy of a fruit
     */
    private  void deepCopyFruit (){
        fruitsHistory= new fruits[fruits.length];
        for (int i = 0; i <fruits.length ; i++) {
            fruitsHistory[i]=new fruit(fruits[i].getValue(),fruits[i].getType(),new Point3D(fruits[i].getLocation()));
        }
    }


    /**
     * function to move the robot's by find the shortest path  and telling the server to move ich time
     */
    public void move() {

        try {

            updateRobots();
            for (int i = 0; i < robots.length; i++) {
                if (robots[i].getRoute() == null || robots[i].getRoute().isEmpty() ||!robots[i].isOnWay() ) { //if robot already geted to it's target
                    robots[i].setOnWay(false);
                    calcShortestPath(true);}
                    deepCopyFruit();
                   if(robots[i].getRoute()!=null){ server.chooseNextEdge(robots[i].getID(), robots[i].getRoute().get(0).getKey());
                    if(robots[i].getSrcNode() == robots[i].getRoute().get(0).getKey()) {
                        robots[i].getRoute().remove(0);}
                        if(!Arrays.equals(fruitsHistory,fruits)) {
                            calcShortestPath(true);}

                    }
                }


        } catch (RuntimeException r) {
            System.out.println(r.getCause());
        }


    }

    /**
     * this function calcs the shrtest path using dijacsra algotithem
     * @param zeroFruit if you wnat to zero the fruit value
     */
    private synchronized void calcShortestPath(boolean zeroFruit) {
        int dst = -1;
        int end = -1;
        double tempCoast;
        initFruits();
        updateRobots();

        if(zeroFruit){ //if it's a second calc for this fruit dua a beeter robot captured it target do not zero the fruit frice
        for (int i = 0; i <fruits.length ; i++) { //zero the fruit  price tp infinity
            fruits[i].setCoast(Integer.MAX_VALUE);
        }}

        for (int i = 0; i < robots.length; i++) {


            robots[i].setCoast(Integer.MAX_VALUE);

            int src = robots[i].getSrcNode();

            for (int j = 0; j < fruits.length; j++) {

                if (fruits[j].getType() == 1) {
                    dst = Math.min(fruits[j].getEdgeOfFruit().getDest(), fruits[j].getEdgeOfFruit().getSrc());
                    end = Math.max(fruits[j].getEdgeOfFruit().getDest(), fruits[j].getEdgeOfFruit().getSrc());

                } else {
                    dst = Math.max(fruits[j].getEdgeOfFruit().getDest(), fruits[j].getEdgeOfFruit().getSrc());
                    end = Math.min(fruits[j].getEdgeOfFruit().getDest(), fruits[j].getEdgeOfFruit().getSrc());

                }

                LinkedList<node_data> shortTempList = new LinkedList<node_data>();
                    shortTempList = (LinkedList<node_data>) this.getGameGraph().shortestPath(src, dst); //get shortest path to one vertex before
                    if (shortTempList == null&&src!=dst){ continue;} // if there is no posiblle way
                    else if(src ==dst) { //the case it's null
                       shortTempList = new LinkedList<node_data>(); // if we need to add only yhe end yo the path zero the list so it wont be null
                    }

                shortTempList.addLast(this.getGameGraph().getAlgoGraph().getNode(end)); // puth the last node at the end of the list
                tempCoast = ((GameGraph.shortestPathDist(src, dst,false)+fruits[j].getEdgeOfFruit().getWeight())/robots[i].getSpeed());
                if ( shortTempList != null&&fruits[j].getWeight()>tempCoast&&robots[i].getCoast() > tempCoast) {// we found a faster way
                   // System.out.println("robot number "+robots[i].getID()+"has picked new target "+fruits[j].getValue()+"the time to the fruit is "+tempCoast);
                    robots[i].setTarget(fruits[j].getLocation());
                    robots[i].setRoute(shortTempList);
                    robots[i].setCoast(tempCoast);
                    robots[i].setOnWay(true);
                    fruits[j].setCoast(tempCoast);
                    for(int r =0 ; r<i ;r++) { // if some other robot is aiming at curr fruit recalculate fot them
                     if(robots[r].getTarget().equals(robots[i].getTarget())) {robots[r].setOnWay(false);
                  //   System.out.println("robot number "+robots[r].getID()+"target was deletet "+fruits[j].getValue());
                    }}

                }


            }

        }



    }


    /**
     * finction for update the player status
     */
    public void updatePlayerStatus() {

        try {
            JSONObject line = new JSONObject(server.toString());
            JSONObject server = line.getJSONObject("GameServer");
          grade  = server.getDouble("grade");
        grade  = server.getDouble("grade");
        maxLevel = server.getDouble("max_user_level");
        moves=server.getInt("moves");

        }
        catch (Exception r ){

        }




    }







    public game_service getServer() {
        return server;
    }

    public Graph_Algo getGameGraph() {
        return GameGraph;
    }

    public int getCunter() {
        return cunter;
    }

    public int getScenario() {
        return scenario;
    }

    public KML_ getKml() {
        return kml;
    }

    public void setKml(KML_ kml) {
        this.kml = kml;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(double maxLevel) {
        this.maxLevel = maxLevel;
    }


    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }


    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * A data recovery form server
     */

    @Override
    public void updateRobot() {
        int sum = 0;
        try {
            String TEMP = server.getRobots().toString();
            JSONArray temp2 = new JSONArray(TEMP);
            while (sum < temp2.length()) {
                JSONObject RobotT = new JSONObject(temp2.get(sum).toString());
                JSONObject Robot = new JSONObject(RobotT.getJSONObject("Robot").toString());
                robots[sum].setValue(Robot.getDouble("value"));
                robots[sum].setSrc(Robot.getInt("src"));
                robots[sum].setNextNode(Robot.getInt("dest"));
                robots[sum].setSpeed(Robot.getDouble("speed"));
                robots[sum].setLocation(new Point3D(Robot.getString("pos")));
                sum++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the rescue that fruit
     * You sat Alb and put it in O(v*n)
     * we could eventually pull it O(1)
     */

    private void initedgeFruit() {
        for (int i = 0; i < fruits.length; i++)
            for (int x : GameGraph.getAlgoGraph().getNeighbore().keySet()) {
                for (int y : GameGraph.getAlgoGraph().getNeighbore().get(x).keySet()) {
                    edge_data temp = GameGraph.getAlgoGraph().getNeighbore().get(x).get(y);
                    if (checker(temp, fruits[i])) {
                        fruits[i].setEdgeOfFruit(temp);
                    }
                }
            }
    }


    private double disstance(double x1, double y1, double x2, double y2) {


        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }


    /**
     * Internal function that checks if the point of fruits
     * between the two points
     *
     * @param temp
     * @param fruitsChecker
     * @return t, f
     */
    private boolean checker(edge_data temp, fruits fruitsChecker) {
        double x1 = this.GameGraph.getAlgoGraph().getNode(temp.getDest()).getLocation().x();
        double y1 = this.GameGraph.getAlgoGraph().getNode(temp.getDest()).getLocation().y();
        double x2 = this.GameGraph.getAlgoGraph().getNode(temp.getSrc()).getLocation().x();
        double y2 = this.GameGraph.getAlgoGraph().getNode(temp.getSrc()).getLocation().y();
        double fruitX = fruitsChecker.getLocation().x();
        double fruitY = fruitsChecker.getLocation().y();

        double m1 = (y2 - fruitY) / (x2 - fruitX);
        double m2 = (y1 - fruitY) / (x1 - fruitX);

        boolean equalM = Math.abs(m1 - m2) <= 0.00001;
        boolean equalD = Math.abs(disstance(x1, y1, fruitX, fruitY) + disstance(x2, y2, fruitX, fruitY) - disstance(x1, y1, x2, y2)) <= 0.00000001;

        return (equalD && equalM);
    }




    @Override
    public String toString() {
        return "SamCatchRon{" +
                "server=" + server +
                ", GameGraph=" + GameGraph +
                ", fruit=" + Arrays.toString(fruits) +
                ", robots=" + Arrays.toString(robots) +
                ", cunter=" + cunter +
                ", scenario=" + scenario +
                ", seem=" + seem +
                '}';
    }


    public  boolean chechFast(){
        boolean fast =false;
        for(int i = 0 ; i<robots.length;i++){
            if(robots[i].getCoast()<1) return  true;
        }

        return fast;
    }


    public double convertTOMeters(double lat1, double lon1, double lat2,double lon2){  // generally used geo measurement function

        if(lat2==Double.MAX_VALUE) return -1;

        double R = 6378.137; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return (d * 1000); // meters
    }



    public  long chechFastMil(){
        updateRobot();
        double min = Double.MAX_VALUE;
        for(int i = 0 ; i<robots.length;i++){
            double diss =convertTOMeters(robots[i].getLocation().y(),robots[i].getLocation().x(),robots[i].getTarget().y(),robots[i].getTarget().x());
            if(diss<min) {
                min =diss;
               min= min/robots[i].getSpeed();
            }
        }

        if(min>300) return  200;
        if(min<50) return  50;
        return (long) min;

    }



    @Override
    public void run() {
        boolean fast;
        try {
            while (server.isRunning()) {
                fast =false;
                server.move();
             if(!isManual())Thread.sleep(chechFastMil()); //70
                else Thread.sleep(20);

            }} catch (Exception e) {
            System.out.println("Game has ended");
            System.out.println();
        }

    }


    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.play(args);


    }
}

