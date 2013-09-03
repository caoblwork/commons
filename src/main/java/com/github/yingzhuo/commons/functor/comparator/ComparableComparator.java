/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.yingzhuo.commons.functor.comparator;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A {@link Comparator Comparator} that compares 
 * {@link Comparable Comparable} objects.
 * <p />
 * This Comparator is useful, for example,
 * for enforcing the natural order in custom implementations
 * of SortedSet and SortedMap.
 * <p />
 */
@SuppressWarnings("rawtypes")
public class ComparableComparator implements Comparator, Serializable {

    /** Serialization version. */
    private static final long serialVersionUID=-291439688585137865L;

    /** The singleton instance. */
    public static final ComparableComparator INSTANCE = new ComparableComparator();
    
    //-----------------------------------------------------------------------
    /**
     * Gets the singleton instance of a ComparableComparator.
     * <p/>
     * Developers are encouraged to use the comparator returned from this method
     * instead of constructing a new instance to reduce allocation and GC overhead
     * when multiple comparable comparators may be used in the same VM.
     *
     * @return the singleton ComparableComparator
     */
	@SuppressWarnings("unchecked")
	public static <T> Comparator<T> getInstance() {
        return INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor whose use should be avoided.
     * <p>
     * Please use the {@link #getInstance()} method whenever possible.
     */
    private ComparableComparator() {
        super();
    }

    //-----------------------------------------------------------------------
    /**
     * Compare the two {@link Comparable Comparable} arguments.
     * This method is equivalent to:
     * <pre>((Comparable)obj1).compareTo(obj2)</pre>
     * 
     * @param obj1  the first object to compare
     * @param obj2  the second object to compare
     * @return negative if obj1 is less, positive if greater, zero if equal
     * @throws NullPointerException when <i>obj1</i> is <code>null</code>, 
     *         or when <code>((Comparable)obj1).compareTo(obj2)</code> does
     * @throws ClassCastException when <i>obj1</i> is not a <code>Comparable</code>,
     *         or when <code>((Comparable)obj1).compareTo(obj2)</code> does
     */
	@SuppressWarnings("unchecked")
	public int compare(Object obj1, Object obj2) {
        return ((Comparable) obj1).compareTo((Comparable)obj2);
    }

    //-----------------------------------------------------------------------
    /**
     * Implement a hash code for this comparator that is consistent with
     * {@link #equals(Object) equals}.
     *
     * @return a hash code for this comparator.
     */
    public int hashCode() {
        return getClass().getName().hashCode();
    }

    /**
     * Returns <code>true</code> iff <i>that</i> Object is 
     * is a {@link Comparator Comparator} whose ordering is 
     * known to be equivalent to mine.
     * <p>
     * This implementation returns <code>true</code>
     * iff <code><i>object</i>.{@link Object#getClass() getClass()}</code>
     * equals <code>this.getClass()</code>.
     * Subclasses may want to override this behavior to remain consistent
     * with the {@link Comparator#equals(Object)} contract.
     * 
     * @param object  the object to compare with
     * @return true if equal
     */
    public boolean equals(Object object) {
        return (this == object) || 
               ((null != object) && (object.getClass().equals(this.getClass())));
    }

}
