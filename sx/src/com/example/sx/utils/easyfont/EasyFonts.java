/*
 * Copyright (C) 2015 EasyFonts
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.example.sx.utils.easyfont;

import android.content.Context;
import android.graphics.Typeface;

import com.example.sx.R;





/**
 * Wraps Typeface object creation.
 * Developer should not have to worry about adding fonts in asset folder.
 *
 * @author vijay.s.vankhede@gmail.com (Vijay Vankhede)
 */
public final class EasyFonts {

    private EasyFonts(){}

      /**
     * CAC-Champagne font face
     * @param context Context
     * @return Typeface object for CAC-Champagne
     */
    public static Typeface num(Context context){
        return FontSourceProcessor.process(R.raw.num, context);
    }
}