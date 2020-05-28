package Simulators;

import java.util.LinkedList;

public class Airport {
	private final String airportName;
    private final int numberOfLanes;
    private int fullLanesCounter;
    private final Boolean[] lanes;
    private final LinkedList waitingLine;

    protected Airport(String airportName, int numberOfLanes) {//making an airport
        this.airportName = airportName;
        this.numberOfLanes = numberOfLanes;
        fullLanesCounter = 0;
        waitingLine = new LinkedList();
        lanes = new Boolean[numberOfLanes];
        for (int i = 0; i < numberOfLanes; i++) {
            lanes[i] = true;
        }


    }

    protected synchronized int depart(int flightNumber) {//departing from the airport
        waitingLine.add(flightNumber);
        System.out.println("Flight " + flightNumber + " waiting to leave Airport " + airportName);

        while (!(fullLanesCounter < lanes.length && flightNumber == (Integer) waitingLine.getFirst())) {//checking if theres a free lane and ur first in line
            try {
                wait();
            } catch (InterruptedException ignored) {
            }

        }
        int i = 0;
        for (; i < lanes.length; i++) {//searching for the free lane

            if (lanes[i])
                break;

        }
        lanes[i] = false;
        fullLanesCounter++;
        waitingLine.removeFirst();
        System.out.println("Flight " + flightNumber + " leaving " + airportName + " from lane number " + i);
        return i;
    }


    protected synchronized int land(int flightNumber) {//landing

        waitingLine.add(flightNumber);
        System.out.println("Flight " + flightNumber + " waiting to land at Airport " + airportName);

        while (!(fullLanesCounter < lanes.length && flightNumber == (Integer) waitingLine.getFirst())) {
            try {
                wait();
            } catch (InterruptedException error) {
            }

        }
        int i = 0;
        for (; i < lanes.length; i++) {

            if (lanes[i])
                break;


        }
        lanes[i] = false;
        fullLanesCounter++;
        waitingLine.removeFirst();
        System.out.println("Flight " + flightNumber + " landed at " + airportName + " at lane number " + i);
        return i;

    }

    protected synchronized void freeRunway(int flightNumber, int laneNumber) {//freeing a lane and waking all the flights


        lanes[laneNumber] = true;
        fullLanesCounter--;
        notifyAll();
        System.out.println("lane number " + laneNumber + " is free! from flight number " + flightNumber + " in airport " + airportName);

    }


}    


