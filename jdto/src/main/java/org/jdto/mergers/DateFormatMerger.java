/*
 *    Copyright 2012 Juan Alberto López Cavallotti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jdto.mergers;

import org.jdto.SinglePropertyValueMerger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.ArrayUtils;

/**
 * Merge a {@link Date} or {@link Calendar} using a {@link SimpleDateFormat} 
 * format String. <br />
 * 
 * You must provide a format string (as you were using SimpleDateFormat), otherwise
 * an IllegalArgumentException will be thrown. <br />
 * 
 * The input value can be either a {@link Date} or {@link Calendar} instance.
 * 
 * @author Juan Alberto Lopez Cavallotti
 */
public class DateFormatMerger implements SinglePropertyValueMerger<String, Object> {
    private static final long serialVersionUID = 1L;
    
    /**
     * Format a date using a pattern (provided by the user as extraParam) 
     * applied to a {@link Date} or {@link Calendar} instance.
     * @param value the date or calendar to format.
     * @param extraParam the format string.
     * @return the formatted value or null if the value argument is null.
     */
    @Override
    public String mergeObjects(Object value, String[] extraParam) {
        
        if (value == null) {
            return null;
        }
        
        if (ArrayUtils.isEmpty(extraParam)) {
            throw new IllegalArgumentException("Date format String parameter is required");
        }
        
        if (!(value instanceof Calendar) && !(value instanceof Date)) {
            throw new IllegalArgumentException("source value is not a Date or Calendar instance!!");
        }
        
        //create a dateformat with the format String
        SimpleDateFormat format = new SimpleDateFormat(extraParam[0]);
        
        Date target = null;
        
        if (value instanceof Calendar) {
            target = ((Calendar) value).getTime();
        } else {
            target = (Date) value;
        }
        
        
        return format.format(target);
    }
    
}
