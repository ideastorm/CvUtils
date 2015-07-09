package com.ideastormsoftware.cvutils.filters;

import com.ideastormsoftware.cvutils.sources.ImageSource;
import com.ideastormsoftware.cvutils.sources.ScaledSource;
import com.ideastormsoftware.cvutils.util.ImageUtils;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

public abstract class AbstractFilter extends ImageSource implements ScaledSource {

    private ImageSource source;

    public void setSource(ImageSource source) {
        this.source = source;
    }
    
    public <T extends AbstractFilter> T withSource(ImageSource source)
    {
        setSource(source);
        return (T) this;
    }

    public ImageSource getSource() {
        return source;
    }
    
    protected abstract BufferedImage filter(BufferedImage original, Dimension targetScreenSize);

    @Override
    public BufferedImage getCurrentImage() {
        return ImageUtils.emptyImage();
    }

    @Override
    public BufferedImage getCurrentImage(Dimension targetScreenSize) {
        return filter(ImageUtils.scaleSource(source).getCurrentImage(targetScreenSize), targetScreenSize);
    }

}
