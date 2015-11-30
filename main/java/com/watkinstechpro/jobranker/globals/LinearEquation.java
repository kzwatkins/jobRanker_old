package com.watkinstechpro.jobranker.globals;

/**
 * Created by kwatkins on 11/22/2015.
 */
public class LinearEquation {
    private double slope;
    private double intercept;

    public LinearEquation(double slope, double intercept){
        this.slope = slope;
        this.intercept = intercept;
    }

    public LinearEquation(double x1, double y1, double x2, double y2){
        this.slope = (y2 - y1)/(x2 - x1);
        this.intercept = y1 - slope * x1;
    }

    public double getSlope(){
        return slope;
    }

    public double getIntercept(){
        return intercept;
    }

    public double getResult(double x){
        return slope * x + intercept;
    }
}
