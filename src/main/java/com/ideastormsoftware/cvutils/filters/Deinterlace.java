/*
 * Copyright 2015 phil.
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

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Deinterlace extends MatFilter{

    @Override
    public Mat filter(Mat original) {
        for (int i = 0; i < original.rows()-2; i+=2) {
            Mat row = original.row(i);
            Mat nextRow = original.row(i+1);
            Mat rowAfter = original.row(i+2);
            Core.addWeighted(row, 0.5, rowAfter, 0.5, 0, nextRow);
//            row.copyTo(nextRow);
        }
        return original;
    }
    
}
