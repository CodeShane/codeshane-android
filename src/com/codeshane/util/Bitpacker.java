/* com.codeshane.AndLib - A CodeShane Solution.
 * Copyright � 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 */
package com.codeshane.util;

import java.security.InvalidParameterException;

/** codeshane.util.Bitpacker
 * <p>  </p>
 * [code][/code]
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 1.0, Mar 29, 2013
 */
public class Bitpacker {

	/* Bit-packing functions */

	/** Returns the highest value that can be stored in a given number of bits.
	 *  Range of possible values is 0-max (inclusive) for a total quantity
	 *  of possible values equal to max+1 (aka '0'.)
	 * */
	public static long bitsMaxValue(int bits){
		return (long) (java.lang.Math.pow(2,bits)-1);
	}

	/** Insert a value into a packed short; returns the updated packed short. */
	public static short packShort(short shrunk, long insertValue, int insertBits){
		if (insertValue > bitsMaxValue(insertBits) ) throw new InvalidParameterException("Bitwise.packShort() - Insertion value cannot exceed insertion bits maximum value.");
		if (insertValue < 0 ) throw new InvalidParameterException("Bitwise.packShort() - Insertion value cannot be negative.");

		short output = shrunk;
		output = (short) (output << insertBits);
		output = (short) (output | insertValue);
		return output;
	}

	//TODO merge these two methods and refactor it and previous so
	// pack <T> (T into, long[] bitsPerValue, long[] values)
	// if value > bitsMax error
	// if val < 0 error

	/** Gets a value from the packed short, but does not modify the packed short.
	 * You must call unpackShort after this to actually remove the bits.
	 * */
	public static long getFromPackedShort(short packed_info, int removeBits) {
		long r = packed_info & bitsMaxValue(removeBits);
//		packed_info = (short) (packed_info >> removeBits); // commenting out as it doesnt appear to do anything
		return r;
	}

	/** Removes and DISCARDS bits from the packed short, returning the freshly unpacked short.
	 * Use this after every call to getFromPackedShort.
	 * */
	public static short unpackShort(short packed_info, int removeBits) {
		return (short) (packed_info >> removeBits);
	}

	public static short pack16(int[] bits, long[] values) {
		short packed = 0;
		int qty = values.length;
		int v, b;
		if (bits.length != qty) throw new InvalidParameterException("Bitwise.packShort() - must have same quantity of bits and values parameters");
		for (int i=0; i<qty; i++) {
			v = (int) values[i];
			b = bits[i];
			if (v > bitsMaxValue(b) ) throw new InvalidParameterException("Bitwise.packShort() - Insertion value cannot exceed insertion bits maximum value.");
			if (v < 0 ) throw new InvalidParameterException("Bitwise.packShort() - Insertion value cannot be negative.");
			packed = (short) (packed << b);
			packed = (short) (packed | v);
		}
		return packed;
	}

	public static int pack32(int[] bits, long[] values) {
		int packed = 0;
		int qty = values.length;
		int v, b;
		if (bits.length != qty) throw new InvalidParameterException("Bitwise.packShort() - must have same quantity of bits and values parameters");
		for (int i=0; i<qty; i++) {
			v = (int) values[i];
			b = bits[i];
			if (v > bitsMaxValue(b) ) throw new InvalidParameterException("Bitwise.packShort() - Insertion value cannot exceed insertion bits maximum value.");
			if (v < 0 ) throw new InvalidParameterException("Bitwise.packShort() - Insertion value cannot be negative.");
			packed = (short) (packed << b);
			packed = (short) (packed | v);
		}
		return packed;
	}

}
