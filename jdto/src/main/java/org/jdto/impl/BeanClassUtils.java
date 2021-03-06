/*
 *    Copyright 2011 Juan Alberto López Cavallotti
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

package org.jdto.impl;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DO NOT USE THIS CLASS AS IS NOT PART OF JDTO PUBLIC API <br />
 * 
 * Utility class for creating instances of classes by reflection. The methods
 * on this class handle reflective creation exceptions and log them properly.
 * @author Juan Alberto Lopez Cavallotti
 */
public class BeanClassUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanClassUtils.class);

    /**
     * Create a new instance of a class or log the exception if the class is
     * not instanceable. This method will rethrow any exception as an unchecked
     * {@link RuntimeException}.
     * @param <T>
     * @param cls
     * @return a new instance of the type given in the argument.
     */
    public static <T> T createInstance(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (Throwable t) {
            logger.error("Could not create bean instance of class " + cls.toString(), t);
            throw new RuntimeException(t);
        }
    }
    
    /**
     * Create a new instance of a class using a specific constructor or log the
     * exception if the class is not instanceable. This method will rethrow any
     * exception as an unchecked {@link RuntimeException}.
     * @param <T>
     * @param cls
     * @param constructor
     * @param argValues
     * @return a new instance of the type given on the argument by using the specified constructor.
     */
    public static <T> T createInstance(Class<T> cls, Constructor constructor, ArrayList argValues) {
        try {
            Object[] args = argValues.toArray();
            return (T) constructor.newInstance(args);
        } catch (Throwable t) {
            logger.error("Could not create bean instance of class" + cls.toString(), t);
            throw new RuntimeException(t);
        }
    }
    
    /**
     * Check if the class has a default constructor.
     * @param cls
     * @return true if the type has default constructor, false if not.
     */
    public static boolean hasDefaultConstructor(Class cls) {
        
        Constructor[] constructors = cls.getConstructors();
        
        //go through all the constructors trying to find one with no
        //parameters
        for (Constructor constructor : constructors) {
            if (constructor.getParameterTypes().length == 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Try to find a class out of a string or return null;
     * @param type
     * @return the Class object represented on the given parameter.
     */
    static Class safeGetClass(String type) {
        //if no type then no class :D
        if (StringUtils.isEmpty(type)) {
            return null;
        }

        try {
            return Class.forName(type);
        } catch (Exception ex) {
            logger.error("Error while trying to read the dto class", ex);
            return null;
        }
    }
    
    /**
     * Find a constructor in the class for the argument types. This method converts
     * any checked exception in unchecked exception.
     * @param cls
     * @param types
     * @return the constructor of the given class which has the given parameter types.
     */
    static Constructor safeGetConstructor(Class cls, Class[] types) {
        try {
            return cls.getDeclaredConstructor(types);
        } catch (Exception ex) {
            logger.error("Error while trying to find constructor for class "+cls.getCanonicalName(), ex);
            throw new RuntimeException("Error while trying to find constructor for class "+cls.getCanonicalName(), ex);
        }
    }
}
