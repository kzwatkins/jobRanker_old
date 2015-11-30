package com.watkinstechpro.jobranker.startup;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

/**
 * Created by kwatkins on 11/23/2015.
 */
public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private int startVal;
    private int  endVal;

    public ProgressBarAnimation(ProgressBar progressBar, int startVal, int endVal) {
        super();
        this.progressBar = progressBar;
        this.startVal = startVal;
        this.endVal = endVal;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        int value = startVal + (int)((endVal - startVal) * interpolatedTime);
        progressBar.setProgress(value);
    }
}