/* com.codeshane.AndLib - A CodeShane Solution.
 * Copyright � 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 */
package com.codeshane.example;

/** codeshane._example.Pseudo
 * <p>Used to provide a simple test subject for the junit4 example.</p>
 * [code][/code]
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 1.0, May 8, 2013
 */
class Pseudo {

	int from = 0;

	Pseudo(int from) {
		this.from = from;
	}

	/**
	 * <p>
	 * Intended to show how much a number has changed from 'from' to 'to'.
	 * </p>
	 * <ul>
	 * <li>(0,5) should return 5</li>
	 * <li>(3,5) should return 2</li>
	 * <li>and so on...</li>
	 * </ul>
	 * */
	int getFoolishDelta(int to) {
		return to - from;
	}

	/**
	 * <p>
	 * Intended to show how much a number has changed from 'from' to 'to'.
	 * </p>
	 * <ul>
	 * <li>(0,5) should return 5</li>
	 * <li>(3,5) should return 2</li>
	 * <li>and so on...</li>
	 * </ul>
	 * */
	int getPoorDelta(int to) {
		return (to > from) ? to - from : from - to;
	}

	/**
	 * <p>
	 * Intended to show how much a number has changed from 'from' to 'to'.
	 * </p>
	 * <ul>
	 * <li>(0,5) should return 5</li>
	 * <li>(3,5) should return 2</li>
	 * <li>and so on...</li>
	 * </ul>
	 * */
	int getWarmerDelta(int to) {
		return (to > from) ? to - from : from - to;
	}

	int getChange(int to) {
		return com.codeshane.util.Math.diff(from, to);
	}

	int divide(int i, int j) {
		return i / j;
	}

	int isFair(int i) {
		return i >> 3;

	}

}
