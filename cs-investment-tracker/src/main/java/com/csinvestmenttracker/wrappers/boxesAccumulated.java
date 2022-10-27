package com.csinvestmenttracker.wrappers;

public class boxesAccumulated {

    singleBox[] allBoxes;
    String[] allNames;
    double totalPrice;
    double totalSellPrice;
    double totalDifference;
    int totalPcs;

    public boxesAccumulated(singleBox[] pAllBoxes, String[] pAllNames, double pTotalPrice, double pTotalSellPrice, double pTotalDifference, int pTotalPcs){
        allBoxes = pAllBoxes;
        allNames = pAllNames;
        totalPrice = pTotalPrice;
        totalSellPrice = pTotalSellPrice;
        totalDifference = pTotalDifference;
        totalPcs = pTotalPcs;
    }

    public singleBox[] getAllBoxes() {
        return allBoxes;
    }
    public String[] getNames() {
        return allNames;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public double getTotalSellPrice() {
        return totalSellPrice;
    }
    public double getTotalDifference() {
        return totalDifference;
    }
    public int getTotalPcs() {
        return totalPcs;
    }
}
