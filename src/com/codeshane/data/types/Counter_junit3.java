package com.codeshane.data.types;

import junit.framework.TestCase;

public class Counter_junit3 extends TestCase {

	/* Setup */
	
	private Counter c; // test variables?
	private long v = 1L;
	private long f = 5L;
	private long t = 12L;
	
	public Counter_junit3(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		c = new Counter(v);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		c = null;
	}

	/* Tests */
	
	/*System.out.println("Test use of Addition clas
int z = Addition.twoValues(x,y);
System.out.println("    Result: " + z);
assertEquals(8,z);*/
	
	public void testTimerUseCase() {	
		//c.resume();
		long fn = System.currentTimeMillis();
		c.resumeCountingFrom(fn);
		assertEquals(fn,c.mCountingFrom);
		assertEquals(v,c.mValue);
		for (long i=0L; i < 100000L; i++){
			System.out.println(" duh" + String.valueOf(i));
		}
		long tn = System.currentTimeMillis();
		c.pauseCountingAt(tn);
		assertEquals(tn,c.mCountingFrom);
		assertEquals(v+(tn-fn),c.mValue);
		System.out.println("Test time: "+c.mValue);
	}
	
	public void testResumeCountingFrom() {		
		c.resumeCountingFrom(f);
		assertEquals(f,c.mCountingFrom);
		assertEquals(v,c.mValue);
	}

	public void testPauseCountingAt() {
		c.mCountingFrom = f;
		c.mIsCounting = true;
		c.pauseCountingAt(t);
		assertEquals(t,c.mCountingFrom);
		assertEquals(v+(t-f),c.mValue);
	}

	public void testIsPaused() {
		c.mIsCounting = true;
		assertEquals(false,c.isPaused());
		c.mIsCounting = false;
		assertEquals(true,c.isPaused());
	}

	public void testPause() {
		//fail("Not yet implemented");
	}

	public void testResume() {
		//fail("Not yet implemented");
	}

	public void testToggle() {
		//fail("Not yet implemented");
	}

	public void testStart() {
		//fail("Not yet implemented");
	}

	public void testStop() {
		//fail("Not yet implemented");
	}

}
