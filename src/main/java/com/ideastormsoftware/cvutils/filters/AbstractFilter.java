/*
 * Copyright 2015 Phillip Hayward <phil@pjhayward.net>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ideastormsoftware.cvutils.filters;

import com.ideastormsoftware.cvutils.sources.ImageSource;
import com.ideastormsoftware.cvutils.sources.ScaledSource;
import com.ideastormsoftware.cvutils.util.ImageUtils;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

public abstract class AbstractFilter extends ImageSource implements ScaledSource {

    private ImageSource source;

    public <T extends AbstractFilter> T setSource(ImageSource source) {
        this.source = source;
        return (T) this;
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
