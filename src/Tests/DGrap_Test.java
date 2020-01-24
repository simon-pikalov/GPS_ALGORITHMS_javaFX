package Tests;

import algorithms.Graph_Algo;
import dataStructure.*;
import gui.Gui;
import org.junit.jupiter.api.Test;

import static java.time.Duration.*;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import static org.junit.jupiter.api.Assertions.*;

class DGrap_Test {

    public  DGraph dgraph = new DGraph();

    public DGrap_Test() {
        DGraph test = new DGraph();
        node_data zero = new node(800,600,0,0);
        node_data one = new node(900,300,1,0);
        node_data two = new node(1300,786,2,0);
        node_data three = new node(252,162,3,0);
        node_data four = new node(350,100,4,0);
        node_data five = new node(55,220,5,0);
        test.addtoGraph(zero,one,true,true,50,50);
        test.addtoGraph(zero,two,true,true,30,50);
        test.addtoGraph(zero,three,true,true,300,300);
        test.addtoGraph(one,two,true,true,30,50);
        test.addtoGraph(three,four,true,true,30,30);
        test.addtoGraph(two,three,true,true,10,10);
        test.addtoGraph(zero,five,true,true,100000,100000);
        test.addtoGraph(four,five,true,true,10,10);
        this.dgraph=test;
    }





    @Test
    void addtoGraph() { //test to creat a graph of 1,000,000 nodes and 10,000,000 edges .
        assertTimeout(ofSeconds(10),()->{
            DGraph test = new DGraph();
            int edge =0;
            int node = 0;
            for (int i = 0; i < 1000000; i++) {
                node_data right = new node(i+1,i+2,1);
                node_data left = new node(i+3,i+4,1);
                test.addtoGraph(right,left , false, false, 0, 0);
                node++;



                for (int j = i-10; j < 10; j++) {
                    try {
                        int p = 0;
                        test.connect(j, j + p, 0);
                        p++;
                    }
                    catch (RuntimeException r ) {

                    }
                 }

            }

        });


    }

    @Test
    void getNode() {

        //get a node from graph
        DGrap_Test a = new DGrap_Test();
        node_data b = a.dgraph.getNode(0);
        assertEquals(new node(800,600,0,0),b);
        a.dgraph.getNode(130); //should'nt do anything
        }




    @Test
    void getEdge() {

        node_data zero = new node(800,600,0,0);
        node_data one = new node(900,300,1,0);
        edge_data expected = new edge(zero,one,50);

        DGrap_Test a = new DGrap_Test();
        edge_data b = (edge) a.dgraph.getEdge(0,1);
        assertEquals(expected,b);

       a.dgraph.getEdge(0,687468894); // shouldnt do anything



    }

    @Test
    void addNode() {
        node_data six = new node(800,600,6,0);
        DGrap_Test a = new DGrap_Test();
        a.dgraph.addNode(six);
        assertTrue(a.dgraph.getNodeMap().containsKey(6));
        try {
            a.dgraph.addNode(new node(800,600,0,0));
            fail();

        }
        catch (RuntimeException r ) {

        }
    }

    @Test
    void connect() {
        DGrap_Test a = new DGrap_Test();
        node_data six = new node(800,600,6,0);
        a.dgraph.addNode(six);
        a.dgraph.connect(0,6,0);
        assertTrue(a.dgraph.getNeighbore().get(0).containsKey(6));

        try {
            a.dgraph.connect(0,666,0);
            fail();
        }
        catch (RuntimeException r) {

        }

    }



    @Test
    void removeNode() {
        DGrap_Test a = new DGrap_Test();
        a.dgraph.removeNode(0);
        assertTrue(!a.dgraph.getNodeMap().containsKey(0));

    }

    @Test
    void removeEdge() {
        DGrap_Test a = new DGrap_Test();
        a.dgraph.removeEdge(0,1);
        assertTrue(!a.dgraph.getNeighbore().get(0).containsKey(1));

    }

    @Test
    void nodeSize() {
        DGrap_Test a = new DGrap_Test();
        assertEquals(6,a.dgraph.nodeSize());
    }

    @Test
    void edgeSize() {
        DGrap_Test a = new DGrap_Test();
        assertEquals(16,a.dgraph.edgeSize());
    }

    @Test
    void getMC() {
        DGrap_Test a = new DGrap_Test();
        assertEquals(a.dgraph.getMC(),0);
        a.dgraph.removeEdge(0,1);
        assertEquals(1,a.dgraph.getMC());

    }


    @Test
    void gui() {
        DGrap_Test a = new DGrap_Test();
   //     Gui.drawGraph(a.dgraph);
    }




}