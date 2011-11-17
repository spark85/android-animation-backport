/*
 * Copyright (C) 2007 The Android Open Source Project
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

package backports.animation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.util.Xml;
import android.os.SystemClock;

import java.io.IOException;

/**
 * Defines common utilities for working with animations.
 *
 */
public class AnimationUtils {

    /**
     * Returns the current animation time in milliseconds. This time should be used when invoking
     * {@link Animation#setStartTime(long)}. Refer to {@link android.os.SystemClock} for more
     * information about the different available clocks. The clock used by this method is
     * <em>not</em> the "wall" clock (it is not {@link System#currentTimeMillis}).
     *
     * @return the current animation time in milliseconds
     *
     * @see android.os.SystemClock
     */
    public static long currentAnimationTimeMillis() {
        return SystemClock.uptimeMillis();
    }

    /**
     * Loads an {@link Interpolator} object from a resource
     * 
     * @param context Application context used to access resources
     * @param id The resource id of the animation to load
     * @return The animation object reference by the specified id
     * @throws NotFoundException
     */
    public static Interpolator loadInterpolator(Context context, int id) throws NotFoundException {
        XmlResourceParser parser = null;
        try {
            parser = context.getResources().getAnimation(id);
            return createInterpolatorFromXml(context, parser);
        } catch (XmlPullParserException ex) {
            NotFoundException rnf = new NotFoundException("Can't load animation resource ID #0x" +
                    Integer.toHexString(id));
            rnf.initCause(ex);
            throw rnf;
        } catch (IOException ex) {
            NotFoundException rnf = new NotFoundException("Can't load animation resource ID #0x" +
                    Integer.toHexString(id));
            rnf.initCause(ex);
            throw rnf;
        } finally {
            if (parser != null) parser.close();
        }

    }
    
    private static Interpolator createInterpolatorFromXml(Context c, XmlPullParser parser)
            throws XmlPullParserException, IOException {
        
        Interpolator interpolator = null;
 
        // Make sure we are on a start tag.
        int type;
        int depth = parser.getDepth();

        while (((type=parser.next()) != XmlPullParser.END_TAG || parser.getDepth() > depth)
               && type != XmlPullParser.END_DOCUMENT) {

            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            AttributeSet attrs = Xml.asAttributeSet(parser);
            
            String  name = parser.getName();
    
            
            if (name.equals("linearInterpolator")) {
                interpolator = new LinearInterpolator(c, attrs);
            } else if (name.equals("accelerateInterpolator")) {
                interpolator = new AccelerateInterpolator(c, attrs);
            } else if (name.equals("decelerateInterpolator")) {
                interpolator = new DecelerateInterpolator(c, attrs);
            }  else if (name.equals("accelerateDecelerateInterpolator")) {
                interpolator = new AccelerateDecelerateInterpolator(c, attrs);
            }  else if (name.equals("cycleInterpolator")) {
                interpolator = new CycleInterpolator(c, attrs);
            } else if (name.equals("anticipateInterpolator")) {
                interpolator = new AnticipateInterpolator(c, attrs);
            } else if (name.equals("overshootInterpolator")) {
                interpolator = new OvershootInterpolator(c, attrs);
            } else if (name.equals("anticipateOvershootInterpolator")) {
                interpolator = new AnticipateOvershootInterpolator(c, attrs);
            } else if (name.equals("bounceInterpolator")) {
                interpolator = new BounceInterpolator(c, attrs);
            } else {
                throw new RuntimeException("Unknown interpolator name: " + parser.getName());
            }

        }
    
        return interpolator;

    }
}
