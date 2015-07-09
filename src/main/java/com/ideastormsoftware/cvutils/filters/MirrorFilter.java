package com.ideastormsoftware.cvutils.filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class MirrorFilter extends MatFilter {

    private Integer flipType;

    public MirrorFilter() {
        this(true, false);
    }

    public MirrorFilter(boolean flipHorizontal, boolean flipVertical) {
        setFlip(flipHorizontal, flipVertical);
    }

    @Override
    public Mat filter(Mat original) {
        if (flipType == null) {
            return original;
        }
        Mat flipped = new Mat();
        Core.flip(original, flipped, flipType);
        return flipped;
    }

    public final void setFlip(boolean flipHorizontal, boolean flipVertical) {
        if (flipHorizontal && flipVertical) {
            flipType = -1;
        } else if (flipHorizontal) {
            flipType = 1;
        } else if (flipVertical) {
            flipType = 0;
        } else {
            flipType = null;
        }
    }
}
