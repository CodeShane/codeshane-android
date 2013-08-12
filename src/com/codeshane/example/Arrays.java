/** Arrays is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson.
 * All Rights Reserved.
 * See LICENSE file for details. */

package com.codeshane.example;

/** 
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Jun 11, 2013
 * @version 1
 */
public class Arrays {
	public static final String	TAG	= Arrays.class.getPackage().getName() + "." + Arrays.class.getSimpleName();

	int mo;
	Object ro;
	final Integer so = 0;
	final Object jo = null;
	
	/** Example Java Arrays and VarArgs (also arrays)
	 * @since Jun 11, 2013
	 * @version Jun 11, 2013
	 * @return void
	 */
	@SuppressWarnings ( "unused" )
	public static void main ( String[] args ) {

		/* Primitive Arrays */
		int[] intArray;				// Declare
		int intArray2[];			// Valid, but generally discouraged
		byte[] input = new byte[5];
		intArray = new int[]{0,0};	// Initialize
		intArray2 = new int[]{0,0};
		byte[] bytePrimitiveArray = {124,125,127}; // Initialize with an array constant (only in declarations)

		/* Wrapper & Object Arrays */
		Byte[] byteObjectArray;// Declare
		byteObjectArray = new Byte[]{124,125,127}; // Initialize
		Byte[] byteObjectArray2 = new Byte[]{124,125,127}; // Declare & Initialize
		
		/* Calling methods with array & VarArg parameters: */
		aryArgs(intArray); // arra
		varArgs(intArray);
		
		/* Single value */
		aryArgs(new int[]{42});
		varArgs(42);
		
	}
	
	static void aryArgs(int[] in){
		System.out.println("I doodled " + in.length + " times.");
	}
	
	static void varArgs(int... in){
		System.out.println("I voodled " + in.length + " times.");
	}
}
