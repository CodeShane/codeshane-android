/* com.codeshane.AndLib - A CodeShane Solution.
 * Copyright � 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 */
package com.codeshane.example;

// Import all static methods from a class ('Assert').
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
// Import one static method ('fail()') from a class ('Assert').
// import static org.junit.Assert.fail;

/**
 * <p>
 * <b>codeshane._example.junit4</b> is an example jUnit4 test of the 'pseudo'
 * class, which is intentionally broken for testing.
 * </p>
 * <p>
 * Lower-case class names and package (default) access used to protect the
 * innocent from accidental import.
 * </p>
 *
 * <b>New in junit4</b>
 * <ul>
 * <li></li>
 * </ul>
 * <b>Changes from junit3</b>
 * <ul>
 * <li></li>
 * <li><i>No longer extends TestCase</i></li>
 * <li><i>Now supports arbitrary method names.</i></li>
 * </ul>
 *
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 1.2, May 8, 2013
 */
public class junit4 {

	private static long started;
	private static long stopped;

	private static Pseudo myPseudo;

	private static int[] from = { 0, 5, -5, 1000 };
	private static int[] to = { 3, 15, 2, -3000 };
	private static int[] is = { 3, 10, 7, -2000 };

	/** Runs once before any tests begin. */
	@BeforeClass
	public static void beforeAllTests() throws Exception {
		started = System.nanoTime();
	}

	/** Runs once after all tests conclude. */
	@AfterClass
	public static void afterAllTests() throws Exception {
		stopped = System.nanoTime();
		long duration = stopped - started;
		System.out.println("Completed " + " in " + duration + "ns ("
				+ (duration / 1000) + "ms)");
	}

	/** This is a class, so you can define an instance constructor if you want. */
	public junit4() {
		System.out.println("Constructing!");
	}

	/** Runs before every test. */
	@Before
	public void beforeEachTest() throws Exception {

	}

	/** Runs after every test. */
	@After
	public void afterEachTest() throws Exception {
	}

	/**
	 * <ul>
	 * <li>Annotate each test case with <b>@Test</b>.</li>
	 * <li></li>
	 * </ul>
	 * */
	@Test
	public void junitTestExample() {
		for (int i=0; i<from.length; i++){
			myPseudo = new Pseudo(from[i]);
			assertEquals(myPseudo.getFoolishDelta(to[i]),is[i]);
		}
//		assertEquals(12, simpleMath.add(7, 5));
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testFoo() {
		// assertEquals(12, myPseudo.foo(7, 5));
		fail("Not yet implemented"); // TODO
	}

}

/*
 * //Getting Started JUnit 4.x
 * //http://tweeks4all.blogspot.com/2012/02/getting-started-junit-4x.html
 *
 * @Test public void addition() { }
 *
 * @Test public void subtraction() { assertEquals(9, simpleMath.substract(12,
 * 3)); }
 *
 * //Exception Handling Use �expected� paramater with @Test annotation for test
 * cases that expect exception. Write the class name of the exception that will
 * be thrown.
 *
 * @Test(expected = ArithmeticException.class) public void
 * divisionWithException() { // divide by zero simpleMath.divide(1, 0); }
 *
 * //Put @Ignore annotation for test cases you want to ignore. You can add a
 * string parameter that defines the reason of ignorance if you want.
 *
 * @Ignore(�Not Ready to Run�)
 *
 * @Test public void multiplication() { assertEquals(15, simpleMath.multiply(3,
 * 5)); }
 *
 * //Define a timeout period in miliseconds with �timeout� parameter. The test
 * fails when the timeout period exceeds.
 *
 * @Test(timeout = 1000) public void infinity() { while (true); }
 *
 * //Compare arrays with new assertion methods. Two arrays are equal if they
 * have the same length and each element is equal to the corresponding element
 * in the other array; otherwise, they�re not. //public static void
 * assertEquals(Object[] expected, Object[] actual); //public static void
 * assertEquals(String message, Object[] expected, Object[] actual);
 *
 * @Test public void listEquality() { List<Integer> expected = new
 * ArrayList<Integer>(); expected.add(5); List<integer> actual = new
 * ArrayList<Integer>(); actual.add(5); assertEquals(expected, actual); }
 *
 * // Run your Junit 4 tests in Junit 3 test runners with Junit4Adapter.
 *
 * public static junit.framework.Test suite() { return new
 * JUnit4TestAdapter(SimpleMathTest.class); }
 */