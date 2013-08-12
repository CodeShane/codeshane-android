package com.codeshane.data.types;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Counter_junit4 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCounter() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCounterLong() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetValue() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSetValue() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testResumeCountingFrom() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testPauseCountingAt() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIsPaused() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testPause() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testResume() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testToggle() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testStart() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testStop() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIsActive() {
		fail("Not yet implemented"); // TODO
	}

}
/*
//Getting Started JUnit 4.x
//http://tweeks4all.blogspot.com/2012/02/getting-started-junit-4x.html

// Mark your test cases with @Test annotation:
// You no longer need to prefix your test cases with �test�.
// Your class no longer needs to extend "TestCase" class.

@Test
public void addition() {
	assertEquals(12, simpleMath.add(7, 5));
}
@Test
public void subtraction() {
	assertEquals(9, simpleMath.substract(12, 3));
}

//Use @Before and @After annotations for �setup� and �tearDown� methods respectively. They run before and after every test case.
@Before
public void runBeforeEveryTest() {
	simpleMath = new SimpleMath();
}
@After
public void runAfterEveryTest() {
	simpleMath = null;
}

@BeforeClass // run once before all test cases
public static void runBeforeClass() {}
 
@AfterClass // run once after all test cases
public static void runAfterClass() {}

 
//Exception Handling Use �expected� paramater with @Test annotation for test cases that expect exception. Write the class name of the exception that will be thrown.
@Test(expected = ArithmeticException.class)
public void divisionWithException() {
	// divide by zero
	simpleMath.divide(1, 0);
}

//Put @Ignore annotation for test cases you want to ignore. You can add a string parameter that defines the reason of ignorance if you want.

@Ignore(�Not Ready to Run�)
@Test
public void multiplication() {
	assertEquals(15, simpleMath.multiply(3, 5));
}

//Define a timeout period in miliseconds with �timeout� parameter. The test fails when the timeout period exceeds.

@Test(timeout = 1000)
public void infinity() {
	while (true);
}

//Compare arrays with new assertion methods. Two arrays are equal if they have the same length and each element is equal to the corresponding element in the other array; otherwise, they�re not.
//public static void assertEquals(Object[] expected, Object[] actual);
//public static void assertEquals(String message, Object[] expected, Object[] actual);

@Test
public void listEquality() {
	List<Integer> expected = new ArrayList<Integer>();
	expected.add(5);
	List<integer> actual = new ArrayList<Integer>();
	actual.add(5);
	assertEquals(expected, actual);
}

// Run your Junit 4 tests in Junit 3 test runners with Junit4Adapter.

public static junit.framework.Test suite() {
	return new JUnit4TestAdapter(SimpleMathTest.class);
}

*/