package com.ideastormsoftware.cvutils.sources;

import com.ideastormsoftware.cvutils.util.ImageUtils;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import org.opencv.core.Mat;

public abstract class ImageSource {

    public BufferedImage getCurrentImage() {
        return ImageUtils.emptyImage();
    }
    
    public Mat getCurrentImageAsMat() {
        return ImageUtils.convertToMat(getCurrentImage());
    }

    protected void warn(String title, String format, Object... items) {
        JOptionPane.showMessageDialog(null, String.format(format, items), title, JOptionPane.WARNING_MESSAGE);
    }
}
