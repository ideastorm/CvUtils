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
