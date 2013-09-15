// GenericsNote: Converted.
/*
 *  Copyright 2003-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.yingzhuo.commons.collections.collection;

import java.util.Collection;
import java.util.Iterator;

import com.github.yingzhuo.commons.functor.iterator.UnmodifiableIterator;

/**
 * <code>UnmodifiableBoundedCollection</code> decorates another
 * <code>BoundedCollection</code> to ensure it can't be altered.
 * <p/>
 * If a BoundedCollection is first wrapped in some other collection decorator,
 * such as synchronized or predicated, the BoundedCollection methods are no
 * longer accessible.
 * The factory on this class will attempt to retrieve the bounded nature by
 * examining the package scope variables.
 * <p/>
 * This class is Serializable from Commons Collections 3.1.
 *
 * @author Matt Hall, John Watkinson, Stephen Colebourne
 * @version $Revision: 1.1 $ $Date: 2005/10/11 17:05:20 $
 * @since Commons Collections 3.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class UnmodifiableBoundedCollection <E> extends AbstractSerializableCollectionDecorator<E> implements BoundedCollection<E> {

    /**
     * Serialization version
     */
    private static final long serialVersionUID = -7112672385450340330L;

    /**
     * Factory method to create an unmodifiable bounded collection.
     *
     * @param coll the <code>BoundedCollection</code> to decorate, must not be null
     * @return a new unmodifiable bounded collection
     * @throws IllegalArgumentException if bag is null
     */
    public static <E> BoundedCollection<E> decorate(BoundedCollection<E> coll) {
        return new UnmodifiableBoundedCollection<E>(coll);
    }

    /**
     * Factory method to create an unmodifiable bounded collection.
     * <p/>
     * This method is capable of drilling down through up to 1000 other decorators
     * to find a suitable BoundedCollection.
     *
     * @param coll the <code>BoundedCollection</code> to decorate, must not be null
     * @return a new unmodifiable bounded collection
     * @throws IllegalArgumentException if bag is null
     */
	public static <E> BoundedCollection<E> decorateUsing(Collection<E> coll) {
        if (coll == null) {
            throw new IllegalArgumentException("The collection must not be null");
        }
        
        // handle decorators
        for (int i = 0; i < 1000; i++) {  // counter to prevent infinite looping
            if (coll instanceof BoundedCollection) {
                break;  // normal loop exit
            } else if (coll instanceof AbstractCollectionDecorator) {
                coll = ((AbstractCollectionDecorator<E>) coll).collection;
            } else if (coll instanceof SynchronizedCollection) {
                coll = ((SynchronizedCollection<E>) coll).collection;
            } else {
                break;  // normal loop exit
            }
        }

        if (coll instanceof BoundedCollection == false) {
            throw new IllegalArgumentException("The collection is not a bounded collection");
        }
        return new UnmodifiableBoundedCollection<E>((BoundedCollection) coll);
    }

    /**
     * Constructor that wraps (not copies).
     *
     * @param coll the collection to decorate, must not be null
     * @throws IllegalArgumentException if coll is null
     */
    private UnmodifiableBoundedCollection(BoundedCollection<E> coll) {
        super(coll);
    }

    //-----------------------------------------------------------------------
    public Iterator<E> iterator() {
        return UnmodifiableIterator.decorate(getCollection().iterator());
    }

    public boolean add(E object) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends E> coll) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> coll) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> coll) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------    
    public boolean isFull() {
        return ((BoundedCollection) collection).isFull();
    }

    public int maxSize() {
        return ((BoundedCollection) collection).maxSize();
    }

}