package com.ideastormsoftware.cvutils.filters;

import org.opencv.core.Mat;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorMOG;

public class BackgroundSubtractorFilter extends MatFilter{

    private final BackgroundSubtractor bgSub = new BackgroundSubtractorMOG();
    private final Double learningRate;

    public BackgroundSubtractorFilter(Double learningRate) {
        this.learningRate = learningRate;
    }
    
    @Override
    public Mat filter(Mat original) {
        Mat fg = new Mat();
        if (learningRate != null)
            bgSub.apply(original, fg, learningRate);
        else
            bgSub.apply(original, fg);
        return fg;
    }
}
