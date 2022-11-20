package com.csinvestmenttracker.wrappers;
/**
 * This class is used as a wrapper to return data more efficiently
 */
public class singleBox {

    String name;
    double oldPrice;
    double newPrice;
    int pcs;
    double difference;

    public singleBox(String pName, double pOldPrice, double pNewPrice, int pPcs, double pDifference) {

        name = pName;
        oldPrice = pOldPrice;
        newPrice = pNewPrice;
        pcs = pPcs;

        difference = pDifference;

    }
    public String getName() {
        return name;
    }
    public double getOldPrice() {
        return oldPrice;
    }
    public double getNewPrice() {
        return newPrice;
    }
    public int getPcs() {
        return pcs;
    }
    public double getDifference() {
        return difference;
    }

}
