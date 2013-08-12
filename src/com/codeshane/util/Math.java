package com.codeshane.util;

public class Math {

	/** Returns the lower of two {@code int}s. */
	public static final int  lower  (final int  a,  final int b) { return (a>b)? b : a; }

	/** Returns the higher of two {@code int}s. */
	public static final int  higher (final int  a,  final int b) { return (a<b)? b : a; }

	/** Returns the lower of two {@code long}s. */
	public static final long lower  (final long a, final long b) { return (a>b)? b : a; }

	/** Returns the higher of two {@code long}s. */
	public static final long higher (final long a, final long b) { return (a<b)? b : a; }

	/** Divides numerator by divisor and returns an int[] where:
	 * int[0] is the resultant integer - division /
	 * int[1] is the remainder (integer) - modulus %
	 * (It's more efficient to perform these operations directly as opposed to creating an Array.) **/
	protected static final int[] div(final int numerator, final int denominator) {
		int[] r = {numerator/denominator,numerator%denominator};
		return r;
	}

	// TODO WIP
	protected static final long add(final long a, final long b) {
		// determines if it will pass max_value
//		int aa = Long.valueOf(a). .SIZE;
//		int bb = Long.valueOf(b).SIZE;
//		return (aa+bb<8)?a+b:Long.MAX_VALUE;
		return a+b;
	}

	// TODO WIP
	protected static final int add(final int a, final int b) {
//		int aa = Integer.valueOf(a).SIZE;
//		int bb = Integer.valueOf(b).SIZE;
//		return (aa+bb<8)?a+b:Integer.MAX_VALUE;
		return a+b;
	}

	// public static int wrapChange(int min, int max){ return diff(min,max)+1; }
	// TODO WIP // math.ods
	protected static final int  wrap (int value, int min, int max){

		while ( value<min || value>max ){
			if (value<min){
				value += diff(min,max) + 1;
			} else {
				value -= diff(min,max);
			}
		}
		return value;

//		int low = min - value;
//		if (high>low){
//
//		}
//		if (value>max) {
//			return min+(max-value);
//		}
//		if (value<min) { // -4  [-3
//			value = java.lang.Math.abs(value);
//			//min = java.lang.Math.max()
//
//		}
//		return 0;
	}

	/** From an inclusive integer range of min through max, circularly iterate to the <i>n</i>th duck, then return the goose. */
	public static final int roundRobin (final int count, final int min, final int max) {
		int possibilities = max - min + 1; // inclusive, inclusive//
		return (count % possibilities)+min;
	}

	public static final int wrapIntoRange(final int value, final int min, final int max){
		return (min<=value && value<=max)?value:(max<value)?value-max+min:min;
	}
	public static final int forceIntoRange(final int value, final int min, final int max){
		return (min<=value && value<=max)?value:(max<value)?max:min;
	}

	/** Returns the change between two numbers by subtraction
	 *  (the second number's position relative the first)
	 **/
	public static final int change(final int from, final int to){ return to - from; }

	/** Returns the change between two numbers by subtraction
	 * (the second number's position relative the first)
	 * change(3,1) // -2
	 * change(1, 3) // 2
	 * change(-3,3) // 6
	 * change(3,-3) // -6 */
	//TODO primitive version of Long.compareTo(Long)?
	public static final long change(final long from, final long to){ return to - from; }

	/** Returns the difference (distance) between two numbers by subtraction
	 * (the second number's position relative the first) **/
	public static final int diff(final int min, final int max){ if (max<min) return java.lang.Math.abs(change(max,min)); return java.lang.Math.abs(change(min,max)); }
	public static final long diff(final long min, final long max){ if (max<min) return java.lang.Math.abs(change(max,min)); return java.lang.Math.abs(change(min,max)); }

}
