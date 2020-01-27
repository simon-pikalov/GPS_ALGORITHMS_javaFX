package gameClient;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class killTheTerroristsTest {

    killTheTerrorists sgame;

    @BeforeEach
    void init() {
        killTheTerrorists sgame = new killTheTerrorists();
        sgame = new killTheTerrorists();
        sgame.updatePlayerStatus();
        sgame.gameInit(23);
        sgame.builderGame();
        sgame.initFruits();
        sgame.getFruits();
        sgame.initRobot();
        sgame.getServer().startGame();
        this.sgame=sgame;
    }


    @Test
    void initRobot() //make sure that all the robo's are near the src nodes
    {
        assertEquals(new robot(0,40,-1,0,1),(robot) sgame.getRobots()[0]);
        assertEquals(new robot(1,21,-1,0,1),(robot) sgame.getRobots()[1]);
        assertEquals(new robot(2,14,-1,0,1),(robot) sgame.getRobots()[2]);
    }

    @Test
    void move() {
        sgame.getServer().startGame();
        new Thread  (new killTheTerrorists()).start();
        try {
            new Thread(new KML_(   sgame.getServer(),sgame.getScenario())).start();
        } catch (IOException e) {
            fail("eror has ");
        }



    }

    @Test
    void setGrade() {
        sgame.setGrade(50);
        assertEquals(50,sgame.getGrade());

        sgame.setGrade(-50);
        assertEquals(-50,sgame.getGrade());
    }

    @Test
    void getMaxLevel() { // level should be

        sgame.updatePlayerStatus();
        assertEquals(sgame.getMaxLevel(),-1);

    }

    @Test
    void setMaxLevel() {
        sgame.setMaxLevel(23);
        assertEquals(sgame.getMaxLevel(),23);
    }




    @Test
    void convertTOMeters() {
        double a1 =32.1065;
        double b1 = 35.18449;
        double a2 =32.16627;
        double b2 =34.82536;

       double m =  sgame.convertTOMeters(a1,b1,a2,b2);
        System.out.println(m);
        assertTrue(Math.abs(m-34461.92)<100); // https://gps-coordinates.org/distance-between-coordinates.php
    }


}