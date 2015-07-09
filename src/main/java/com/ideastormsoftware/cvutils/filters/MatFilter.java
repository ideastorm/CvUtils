package com.ideastormsoftware.cvutils.filters;

import com.ideastormsoftware.cvutils.util.ImageUtils;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import org.opencv.core.Mat;

public abstract class MatFilter extends AbstractFilter {

    public abstract Mat filter(Mat original);

    @Override
    protected BufferedImage filter(BufferedImage original, Dimension targetScreenSize) {
        return ImageUtils.copyAspectScaled(getCurrentImage(), targetScreenSize);
    }

    @Override
    public BufferedImage getCurrentImage() {
        return ImageUtils.convertToImage(getCurrentImageAsMat());
    }

    @Override
    public Mat getCurrentImageAsMat() {
        Mat src = getSource().getCurrentImageAsMat();
        if (src == null)
            return null;
        return filter(src);
    }

    @Override
    public BufferedImage getCurrentImage(Dimension targetScreenSize) {
        return ImageUtils.copyAspectScaled(getCurrentImage(), targetScreenSize);
    }
}
