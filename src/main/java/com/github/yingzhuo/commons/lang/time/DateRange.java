/*
 * Copyright 2002-2012 the original author or authors.
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
package com.github.yingzhuo.commons.lang.time;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import com.github.yingzhuo.commons.exception.CloneFailedException;
import com.github.yingzhuo.commons.lang.Range;
import com.github.yingzhuo.commons.lang.Validate;

/**
 * <p>An immutable range of java.util.Date from a start to end point inclusive.</p>
 *
 *
 * @author YING Zhuo
 *
 */
public final class DateRange implements Serializable, Cloneable, Iterable<Date> {

	private static final long serialVersionUID = -4613299492845102323L;

	private static final String[] DEFAULT_PARSE_PATTERNS = new String[] {
		"yyyy-MM-dd",
		"yyyy/MM/dd"
	};

	private final Range<Date> _range;
	
	
	// Creates an instance
	// ----------------------------------------------------------------------------------

	/**
	 * <p>Obtains a range using the specified date as the start
     * and end in this range.</p>
	 * 
	 * @param date1 start or end date such as '1982-08-19'
	 * @param date2 start or end date such as '1982-08-19'
	 * @return the range, not null
	 */
	public static DateRange between(String date1, String date2) {
		return between(date1, date2, DEFAULT_PARSE_PATTERNS);
	}

	/**
	 * <p>Obtains a range using the specified date as the start
     * and end in this range.</p>
	 * 
	 * @param date1  start or end date such as '1982-08-19'
	 * @param date2  start or end date such as '1982-08-19'
	 * @param parsePatterns  the date format patterns to use, see SimpleDateFormat, not null
	 * @return the range, not null
	 */
	public static DateRange between(String date1, String date2, String... parsePatterns) {

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = DateUtils.parseDate(date1, parsePatterns);
			d2 = DateUtils.parseDate(date2, parsePatterns);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		
		return between(d1, d2);
	}
	
	/**
	 * <p>Obtains a range using the specified date as the start
     * and end in this range.</p>
	 * 
	 * @param date1  start or end date
	 * @param date2  start or end date
	 * @return the range, not null
	 */
	public static DateRange between(Date date1, Date date2) {
		return new DateRange(date1, date2);
	}
	
	/** private Constrctor */
	private DateRange(Date start, Date end) {
		Validate.notNull(start);
		Validate.notNull(end);

		start = DateUtils.truncate(start, Calendar.DATE);
		end = DateUtils.truncate(end, Calendar.DATE);
		_range = Range.between(start, end);
	}

	// Basic
	// ----------------------------------------------------------------------------------

	@Override
	public int hashCode() {
		return _range.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return _range.equals(obj);
	}

	@Override
	public String toString() {
		return toString("yyyy-MM-dd", null);
	}

	public String toString(String pattern, String separator) {
		return new StringBuilder()
				.append('[')
				.append(DateFormatUtils.format(getStart(), pattern))
				.append(separator == null ? " .. " : separator)
				.append(DateFormatUtils.format(getEnd(), pattern)).append(']')
				.toString();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Date start = new Date(getStart().getTime());
		Date end = new Date(getEnd().getTime());
		return new DateRange(start, end);
	}
	
	public DateRange deepClone() {
		try {
			return (DateRange) clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneFailedException(e.getMessage(), e);
		}
	}

	// Basic
	// ----------------------------------------------------------------------------------

	/**
	 * get start date 
	 * @return the start date null
	 */
	public Date getStart() {
		return _range.getMinimum();
	}

	/**
	 * get end date 
	 * @return the end date null
	 */
	public Date getEnd() {
		return _range.getMaximum();
	}

	/**
	 * get comparator of date (Natural Ordering comparator)
	 * @return the comparator not null
	 */
	public Comparator<Date> getComparator() {
		return _range.getComparator();
	}
	
	/**
	 * get duration of range by days
	 * 
	 * @return duration of range
	 */
	public int durationInDays() {
		return (int) ((getEnd().getTime() - getStart().getTime()) / DateUtils.MILLIS_PER_DAY + 1);
	}

//	@Deprecated
//	public boolean isNaturalOrdering() {
//		return true;
////		return _range.isNaturalOrdering();
//	}

	// Element tests
	// ----------------------------------------------------------------------------------

	/**
     * <p>Checks whether the specified date occurs within this range.</p>
     *
     * @param date  the element to check for, null returns false
     * @return true if the specified element occurs within this range
     */
	public boolean contains(Date date) {
		return _range.contains(date);
	}

	 /**
     * <p>Checks whether this range is after the specified date.</p>
     *
     * @param date  the element to check for, null returns false
     * @return true if this range is entirely after the specified date
     */
	public boolean isAfter(Date date) {
		return _range.isAfter(date);
	}

	 /**
     * <p>Checks whether this range is before the specified date.</p>
     *
     * @param date  the date to check for, null returns false
     * @return true if this range is entirely after the specified date
     */
	public boolean isBefore(Date date) {
		return _range.isBefore(date);
	}

	/**
     * <p>Checks whether this range starts with the specified date.</p>
     *
     * @param date  the element to check for, null returns false
     * @return true if the specified date occurs within this range
     */
	public boolean isStartedBy(Date date) {
		return _range.isStartedBy(date);
	}

	 /**
     * <p>Checks whether this range ends with the specified element.</p>
     *
     * @param date  the element to check for, null returns false
     * @return true if the specified element occurs within this range
     */
	public boolean isEndedBy(Date date) {
		return _range.isEndedBy(date);
	}

	 /**
     * <p>Checks where the specified date occurs relative to this range.</p>
     * 
     * <p>The API is reminiscent of the Comparable interface returning {@code -1} if
     * the element is before the range, {@code 0} if contained within the range and
     * {@code 1} if the element is after the range. </p>
     *
     * @param date  the element to check for, not null
     * @return -1, 0 or +1 depending on the element's location relative to the range
     */
	public int dateCompareTo(Date date) {
		return _range.elementCompareTo(date);
	}

	// Range tests
	// --------------------------------------------------------------------------------------

	 /**
     * <p>Checks whether this range contains all the elements of the specified range.</p>
     *
     * <p>This method may fail if the ranges have two different comparators or element types.</p>
     *
     * @param otherRange  the range to check, null returns false
     * @return true if this range contains the specified range
     * @throws RuntimeException if ranges cannot be compared
     */
	public boolean contains(DateRange otherRange) {
		if (otherRange == null) {
			return false;
		}
		return _range.containsRange(_toRange(otherRange));
	}

	/**
     * <p>Checks whether this range is completely after the specified range.</p>
     *
     * @param otherRange  the range to check, null returns false
     * @return true if this range is completely after the specified range
     * @throws RuntimeException if ranges cannot be compared
     */
	public boolean isAfter(DateRange otherRange) {
		if (otherRange == null) {
			return false;
		}
		return _range.isAfterRange(_toRange(otherRange));
	}

	/**
     * <p>Checks whether this range is completely before the specified range.</p>
     *
     * @param otherRange  the range to check, null returns false
     * @return true if this range is completely before the specified range
     * @throws RuntimeException if ranges cannot be compared
     */
	public boolean isBeforeRange(DateRange otherRange) {
		if (otherRange == null) {
			return false;
		}
		return _range.isBeforeRange(_toRange(otherRange));
	}

	/**
     * <p>Checks whether this range is overlapped by the specified range.</p>
     * 
     * <p>Two ranges overlap if there is at least one element in common.</p>
     *
     * @param otherRange  the range to test, null returns false
     * @return true if the specified range overlaps with this
     *  range; otherwise, {@code false}
     * @throws RuntimeException if ranges cannot be compared
     */
	public boolean isOverlappedBy(DateRange otherRange) {
		if (otherRange == null) {
			return false;
		}
		return _range.isAfterRange(_toRange(otherRange));
	}

	 /**
     * Calculate the intersection of {@code this} and an overlapping Range.
     * @param other overlapping Range
     * @return range representing the intersection of {@code this} and {@code other} ({@code this} if equal)
     * @throws IllegalArgumentException if {@code other} does not overlap {@code this}
     */
	public DateRange intersectionWith(DateRange other) {
		if (isOverlappedBy(other) == false) {
			return null;
		}
		Range<Date> _other = _toRange(other);
		Range<Date> intersection = _range.intersectionWith(_other);
		return new DateRange(intersection.getMinimum(), intersection.getMaximum());
	}

	private Range<Date> _toRange(DateRange range) {
		return Range.between(range.getStart(), range.getEnd(),
				range.getComparator());
	}

	// Iterator
	// --------------------------------------------------------------------------------------

	/**
     * Returns an iterator over a set of elements of date.
     * 
     * @return an iterator
     */
	public Iterator<Date> iterator() {
		return new DateRangeIterator(getStart(), getEnd());
	}

}

class DateRangeIterator implements Iterator<Date> {

	private long start;
	private long end;
	private long i;

	public DateRangeIterator(Date start, Date end) {
		this.start = DateUtils.truncate(start, Calendar.DATE).getTime();
		this.end   = DateUtils.truncate(end, Calendar.DATE).getTime() + DateUtils.MILLIS_PER_DAY;
		i = this.start;
	}

	public boolean hasNext() {
		return i < end;
	}

	public Date next() {
		Date d = new Date(i);
		i += DateUtils.MILLIS_PER_DAY;
		return d;
	}

	public void remove() {
		throw new UnsupportedOperationException(
				"Remove unsupported on DateRangeIterator");
	}

}