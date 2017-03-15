package com.example.kati.stash;

/**
 * The class which holds the information for each yarn type
 * Created by Kati on 2/17/2017
 */

public class Yarn {
    private int id;
    private String brandName;
    private String yarnName;
    private String color;
    private String fiber;
    private double ballsAvailable;
    private double yardage;

    public void setId(int id) {
        this.id = id;
    }
    public int getID() {
        return id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFiber() {
        return fiber;
    }

    public void setFiber(String fiber) {
        this.fiber = fiber;
    }

    public double getBallsAvailable() {
        return ballsAvailable;
    }

    public void setBallsAvailable(double ballsAvailable) {
        this.ballsAvailable = ballsAvailable;
    }

    public double getYardage() {
        return yardage;
    }

    public void setYardage(double yardage) {
        this.yardage = yardage;
    }

    public Yarn()
    {
    }

    public Yarn(int id,String brandName,String yarnName, String color, String fiber, double ballsAvailable, double yardage)
    {
        this.id=id;
        this.brandName=brandName;
        this.yarnName=yarnName;
        this.fiber=fiber;
        this.color=color;
        this.ballsAvailable=ballsAvailable;
        this.yardage=yardage;
    }

    /**
     * Created by Kati on 2/17/2017.
     */

}

