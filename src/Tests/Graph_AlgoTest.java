package Tests;


import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node;
import dataStructure.node_data;
import gui.Gui;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Graph_AlgoTest  {

    Graph_Algo text;

    public Graph_AlgoTest() {
        text=new Graph_Algo();
}

    @Test
    void init() {
        DGraph testDG = new DGraph();
        node_data zero = new node(800,600,0,0);
        node_data one = new node(900,300,1,0);
        node_data two = new node(1300,786,2,0);
        node_data three = new node(252,162,3,0);
        node_data four = new node(350,100,4,0);
        node_data five = new node(55,220,5,0);
        testDG.addtoGraph(zero,one,true,true,50,50);
        testDG.addtoGraph(zero,two,true,true,30,50);
        testDG.addtoGraph(zero,three,true,true,300,300);
        testDG.addtoGraph(one,two,true,true,30,50);
        testDG.addtoGraph(three,four,true,true,30,30);
        testDG.addtoGraph(two,three,true,true,10,10);
        testDG.addtoGraph(zero,five,true,true,100000,100000);
        testDG.addtoGraph(four,five,true,true,10,10);
        graph_algorithms test2=new Graph_Algo(testDG);
        assertTrue(test2.isConnected()==text.isConnected());

    }

    @Test
    void testInit() {
        text.init("text1");
        text.save("text2");
        graph_algorithms a = new Graph_Algo();
        a.init("text2");
        assertTrue(a.isConnected()==text.isConnected());
    }

    @Test
    void save() {



    }

    @Test
    void isConnected() {
        DGraph testDG = new DGraph();
        node_data zero = new node(800,600,0,0);
        node_data one = new node(900,300,1,0);
        node_data two = new node(1300,786,2,0);
        node_data three = new node(252,162,3,0);
        testDG.addNode(zero);
        testDG.addNode(one);
        testDG.addNode(two);
        testDG.addNode(three);
        testDG.addtoGraph(zero,one,true,true,50,50);
        testDG.addtoGraph(zero,two,true,true,30,50);
        testDG.addtoGraph(two,one,true,true,300,300);
        graph_algorithms saveTest=new Graph_Algo(testDG);
        assertTrue(!saveTest.isConnected());
        testDG.addtoGraph(three,one,true,false,300,300);
        assertTrue(!saveTest.isConnected());
        testDG.addtoGraph(three,one,true,true,300,300);
        assertTrue(saveTest.isConnected());
    }

    @Test
    void shortestPathDist() {
        text.init("text1");
        assertEquals(text.shortestPathDist(0,1),30.0);
        assertEquals(text.shortestPathDist(4,0),70.0);
    }

    @Test
    void shortestPath() {
        DGraph testDG = new DGraph();
        node_data zero = new node(800,600,0,0);
        node_data one = new node(900,300,1,0);
        node_data two = new node(1300,786,2,0);
        testDG.addtoGraph(zero,one,true,false,50,50);
        testDG.addtoGraph(zero,two,true,true,30,50);
        testDG.addtoGraph(two,one,true,false,300,300);
        graph_algorithms saveTest=new Graph_Algo(testDG);
        saveTest.shortestPath(zero.getKey(),one.getKey());
    }

    @Test
    void copy() {
        this.text.init("text1");
        DGraph copyTest=(DGraph)text.copy();
        assertTrue(copyTest.getNodeMap().size()==text.getAlgoGraph().getNodeMap().size());
        assertTrue( copyTest.getNeighbore().size()==text.getAlgoGraph().getNeighbore().size());
        text.getAlgoGraph().removeNode(0);
        assertTrue(!(copyTest.getNodeMap().size()==text.getAlgoGraph().getNodeMap().size()));
        assertTrue(!(copyTest.getNeighbore().size()==text.getAlgoGraph().getNeighbore().size()));
    }

    @Test
    void TSP() {
        text.init("text1");
        ArrayList<Integer> textTsp=new ArrayList<Integer>();
        textTsp.add(0);textTsp.add(1);textTsp.add(4);
        List<node_data> textTsp3=new ArrayList<node_data>();
        List<node_data> textTsp2=new ArrayList<node_data>();
        textTsp2.add(text.getAlgoGraph().getNode(0));
        textTsp2.add(text.getAlgoGraph().getNode(1));
        textTsp2.add(text.getAlgoGraph().getNode(4));
        textTsp3=text.TSP(textTsp);
        assertTrue(textTsp2.equals(textTsp3));
    }

    @Test
    void testToString() {
    }


}