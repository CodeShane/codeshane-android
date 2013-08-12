/** Exceptions is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson.
 * All Rights Reserved.
 * See LICENSE file for details. */

package com.codeshane.example;

/**
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Jun 10, 2013
 * @version 1
 */
public class Exceptions {
	public static final String	TAG	= Exceptions.class.getPackage().getName() + "." + Exceptions.class.getSimpleName();

	public static final void error() {
		throw new Error();
	}

	public static final void throw_RuntimeException() {
		throw new RuntimeException();
	}

	public static final void consume_thrown_Exception() {
		try {
			throw new Exception();
		} catch (Exception ex) {
			System.out.println("Something happened, but I'm not saying what.");
		}
	}

	public static final void throw_checked_Exception() throws Exception {
		Thread.setDefaultUncaughtExceptionHandler(new CowboyCoderExceptionHandler());
		// Exception must be flagged in the method signature ("throws
		// SomeException") of every calling method that doesn't catch it.
		throw new Exception();
	}

	public static void main ( String[] args ) {
		System.out.println();
		System.out.println("Exceptions.main()");

		// This method will catch its exception internally.
		// Typically used to handle frequent, known exceptions.
		consume_thrown_Exception();

		// This catches a checked exception manually.
		// Used for infrequent Exceptions, especially those requiring user
		// notification/interaction.
		try {
			throw_checked_Exception(); // <-- Checked Exceptions MUST be caught.
		} catch (Exception ex) {
			// The exception thrown by the exception() method is caught here as 'ex'.
			// It could be handled..
			// or just printed to System.err like this:
			ex.printStackTrace();
			// or rethrown:
//			throw ex;
			// requiring the method to declare the Exception:
//			public static void main ( String[] args ) throws Exception {
		}

		// This typically represents a rare exception situation, and
		// MAY be caught/handled locally or using an UncaughttExceptionHandler.
		throw_RuntimeException(); // <-- Uncaught RuntimeException

		// This method will throw an error at runtime.
		// An Error is generally expected to crash the process, but
		// MAY be caught/handled.
		try {
			error();
		} catch (Error err) {
			err.printStackTrace();
		}

		error(); // <-- Uncaught Error

	}

}

class CowboyCoderExceptionHandler implements Thread.UncaughtExceptionHandler {
	/** @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable) */
	@Override public void uncaughtException ( Thread thread, Throwable ex ) {
		System.out.println("Exception \"Handled,\" System mangled. Happy coding.");
	}
}
