/* com.codeshane.AndLib - A CodeShane Solution.
 * Copyright � 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 */
package com.codeshane.util;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** codeshane.util.MathTest
 * <p>  </p>
 * [code][/code]
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 1.0, May 8, 2013
 */
public class MathTest {

	/**void
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**void
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	public static final long LONG_HIGHER = Byte.MAX_VALUE / 2;
	public static final long LONG_HIGH = Byte.MAX_VALUE / 4;
	// public static final long LONG_MID = Byte.MAX_VALUE / 8;
	public static final long LONG_LOW = Byte.MIN_VALUE / 4;
	public static final long LONG_LOWER = Byte.MIN_VALUE / 2;

	/**
	 * Test method for {@link com.codeshane.util.Math#lower(int, int)}.
	 */
	@Test(timeout = 200)
	public void testLowerIntInt() {
		Math.lower(LONG_LOWER, LONG_LOW);
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#higher(int, int)}.
	 */
	@Test
	public void testHigherIntInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#lower(long, long)}.
	 */
	@Test
	public void testLowerLongLong() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#higher(long, long)}.
	 */
	@Test
	public void testHigherLongLong() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#div(int, int)}.
	 */
	@Test
	public void testDiv() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#add(long, long)}.
	 */
	@Test
	public void testAddLongLong() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#add(int, int)}.
	 */
	@Test
	public void testAddIntInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#wrapChange(int, int)}.
	 */
	@Test
	public void testWrapChange() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#wrap(int, int, int)}.
	 */
	@Test
	public void testWrap() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#roundRobin(int, int, int)}.
	 */
	@Test
	public void testRoundRobin() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#wrapIntoRange(int, int, int)}.
	 */
	@Test
	public void testWrapIntoRange() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#forceIntoRange(int, int, int)}.
	 */
	@Test
	public void testForceIntoRange() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#change(int, int)}.
	 */
	@Test
	public void testChangeIntInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#change(long, long)}.
	 */
	@Test
	public void testChangeLongLong() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#diff(int, int)}.
	 */
	@Test
	public void testDiffIntInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.codeshane.util.Math#diff(long, long)}.
	 */
	@Test
	public void testDiffLongLong() {
		fail("Not yet implemented"); // TODO
	}

}
