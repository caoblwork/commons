package com.github.yingzhuo.commons.functor.predicate;

import java.io.Serializable;

import com.github.yingzhuo.commons.functor.Predicate;
import com.github.yingzhuo.commons.lang.Validate;

/**
 * Predicate implementation that returns true if the result of predicates are not the same.
 * 
 * @author YING Zhuo
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class XorPredicate<T> implements Predicate<T>, PredicateDecorator<T>, Serializable {

	/** Serial version UID */
	private static final long serialVersionUID = 1935970135652556805L;
    
    private final Predicate<T> iPredicate1;
    private final Predicate<T> iPredicate2;
    
    /**
     * Factory to create the predicate.
     * 
     * @param predicate1  the first predicate to check, not null
     * @param predicate2  the second predicate to check, not null
     * @return the <code>or</code> predicate
     * @throws IllegalArgumentException if either predicate is null
     */
	public static <T> Predicate<T> getInstance(Predicate<T> predicate1, Predicate<T> predicate2) {
        return new XorPredicate(predicate1, predicate2);
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     * 
     * @param predicate1  the first predicate to check, not null
     * @param predicate2  the second predicate to check, not null
     */
    private XorPredicate(Predicate<T> predicate1, Predicate<T> predicate2) {
        Validate.notNull(predicate1);
        Validate.notNull(predicate2);
        iPredicate1 = predicate1;
        iPredicate2 = predicate2;
    }

    /**
     * Evaluates the predicate returning true if either predicate returns are not the same.
     * 
     * @param object  the input object
     * @return true if either decorated predicate returns true
     */
    public boolean evaluate(T object) {
       return (iPredicate1.evaluate(object) ^ iPredicate2.evaluate(object));
    }

    /**
     * Gets the two predicates being decorated as an array.
     * 
     * @return the predicates
     */
    public Predicate[] getPredicates() {
        return new Predicate[] {iPredicate1, iPredicate2};
    }
}
