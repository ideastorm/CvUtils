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
