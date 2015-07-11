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
