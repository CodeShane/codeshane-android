/* com.codeshane.AndLib - A CodeShane Solution.
 * Copyright � 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 */
package com.codeshane.util;

//import static codeshane.data.Types.*;
import static com.codeshane.util.Bitwise.*;
import static com.codeshane.util.Numbers.*;
import static com.codeshane.util.Text.*;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.math.BigDecimal;
import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Nullable;

import android.util.Log;

//import com.google.common.base.Function;
//import com.google.common.base.Strings;

/**
 * codeshane.util.Numbers
 * Handles text-formatting of numerical values.
 *
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 1.0, Apr 19, 2013
 */
public class Numbers {
//	javax.persistence.
//	com.google.common.

//	Integer.toHexString(i);
//	Integer.toOctalString(i);
//	Integer.toBinaryString(a);

	public static void define(String name, int a){
		//XXX
		System.out.println("S " +
				getFixedWidth(name, 12, ' ') + ' ' +
				getFixedWidth(Integer.toString(a),20,' ') + " (dec)\t" +
				getFixedHexString(a) + " (hex)\t" +
				toBinaryString(a) + " (bin)"
		);
		System.out.println("G " +
				Text.padRight(name, 12, ' ') +
				Text.padRight(Integer.toString(a), 20, ' ') + " (dec)\t" +
				"0x" + Text.padLeft(Integer.toHexString(a), 8, ' ') + " (hex)\t" +
				Text.padLeft(Integer.toBinaryString(a), 32, ' ') + " (bin)\t"
		);
		// Strings.padEnd -> Text.padRight
		// Strings.padStart -> Text.padLeft

	}



  public static void printConstantsToConsole(){

		System.out.println();
//		define("Double.MAX_VALUE",Double.MAX_VALUE);
//		define("Double.MIN_VALUE",Double.MIN_VALUE);
//		define("Float.MAX_VALUE",Float.MAX_VALUE);
//		define("Float.MIN_VALUE",Float.MIN_VALUE);
//		define("Long.MAX_VALUE",Long.MAX_VALUE);
//		define("Long.MIN_VALUE",Long.MIN_VALUE);
		define("Integer.MAX_VALUE",Integer.MAX_VALUE);
		define("Integer.MIN_VALUE",Integer.MIN_VALUE);
		define("Short.MAX_VALUE",Short.MAX_VALUE);
		define("Short.MIN_VALUE",Short.MIN_VALUE);
		define("Character.MAX_VALUE",Character.MAX_VALUE);
		define("Character.MIN_VALUE",Character.MIN_VALUE);
		define("Byte.MAX_VALUE",Byte.MAX_VALUE);
		define("Byte.MIN_VALUE",Byte.MIN_VALUE);
		System.out.println();
	}


	//TODO needs to be in Types
	public static final byte BYTE = 0; // B // calculator byte
	public static final byte SHORT = 1; // S // calc word
	public static final byte INT = 2; // I // calc dword
	public static final byte LONG = 3; // L // qword

	public static final byte CHAR = 1; // (byte) (10 * TYPES + 1); // C

	public static final byte FLOAT = 2; // (byte) (10 * TYPES + 1); // C
	public static final byte DOUBLE = 3; // (byte) (10 * TYPES + 1); // C

	public static final int[] BITS_PER = new int[]{8,16,32,64};
	public static final String[] FORMAT_AS = new String[]{"%8s","%16s","%32s","%64s"};

	//# chars
	public static final int BIN = 2;
	public static final int OCT = 8;
	public static final int DEC = 10;
	public static final int HEX = 16;

	public class Builder {

		StringBuilder sb;
		int radix;

		private Builder(int radix){
			sb = new StringBuilder();
		}

		public Builder(Long value, int radix) {
			this(radix); this.sb.append(Long.toHexString((Long)value)); }
		public Builder(Byte value, int radix) {
			this(radix); this.sb.append(Integer.toHexString(((Byte)value).intValue())); }
		public Builder(Integer value, int radix) {
			this(radix); this.sb.append(Integer.toHexString((Integer)value)); }
		public Builder(Short value, int radix) {
			this(radix); this.sb.append(Integer.toHexString(((Short)value).intValue())); }

		// (Number value, Radix format)
		// (4 types      x     4 types)

		public Builder hex(Long value){
			sb.append(Long.toHexString((Long)value));
			return this;
		}
		public Builder hex(Short value){
			sb.append(Long.toHexString(((Short)value).intValue()));
			return this;
		}
		public Builder hex(Integer value){
			sb.append(Integer.toHexString(value));
			return this;
		}
		public Builder hex(Byte value){
			sb.append(Long.toHexString(((Byte)value).intValue()));
			return this;
		}

//		public <T> Builder(T n, int radix) {}

		/** Returns the current value of this NumberBuilder. **/
		@Override public String toString() { return sb.toString(); }

		/** Prints the current value of this NumberBuilder to the console using System.out.print(). **/
		public void print() { System.out.print(toString()); }

		/** Prints the current value of this NumberBuilder to the console using System.out.print(). **/
		public void println() { System.out.println(toString()); }

	} /* end builder */

//	public static <E> void populateSetting(Bundle bun, String key, E defaultValue) {
//	public static <T extends Number> NumberBuilder hex(T n) { return new <T> NumberBuilder(n,16); }

//	public static String toBinaryString(Float value)	 { return toBinaryString(CHAR,value);  }
//	public static String toBinaryString(Double value)	 { return toBinaryString(BYTE,value);  }

	public static String toBinaryString(Character value) { return toBinaryString(CHAR,value);  }
	public static String toBinaryString(Byte value)      { return toBinaryString(BYTE,value);  }
	public static String toBinaryString(Short value)     { return toBinaryString(SHORT,value); }
	public static String toBinaryString(Integer value)   { return toBinaryString(INT,value);   }
	public static String toBinaryString(Long value)      { return toBinaryString(LONG,value);  }

	private static String toBinaryString(byte type, long value) {
		StringBuilder sb = new StringBuilder();//"\n"

		//Format string and fill leading zeros.
		String f = String.format(FORMAT_AS[type], Long.toBinaryString(value)).replace(' ', '0');

		sb.append(f.substring(f.length()-BITS_PER[type]));

		// Nibble Spacing
		boolean odd = true;
		for (int i=sb.length()-8; i>0; i=i-8){
			sb.insert(i, (odd)?' ':"  ");
			odd = !odd;
		}

		// begin second line
//		int l = BITS_PER[type];
//		sb.append("\n");
//
//		//
//		for(int i=l; i>0; i = i-8){
//			sb.append(i);
//			if (i>99) { sb.append("     ");
//			} else if (i>9) { sb.append("      ");
//			} else { sb.append("       "); }
//		}
//
//		// Duplicate nibble spacing for  second line
//		odd = true;
//		for (int i=sb.length()-8; i>l+8; i=i-8){
//			sb.insert(i, (odd)?' ':"  ");
//			odd = !odd;
//		}

		return sb.toString();
	}

  /**
   * Convert byte array to hex string
   * @param bytes
   * @return
   * @author http://stackoverflow.com/questions/6064510/how-to-get-ip-address-of-the-device/13007325#13007325
   */
	public static String getHexString(byte[] bytes) {
      StringBuilder sb = new StringBuilder();
      for(int i=0; i < bytes.length; i++) {
          int intVal = bytes[i] & 0xff;
          if (intVal < 0x10) sb.append('0');
          sb.append(Integer.toHexString(intVal).toUpperCase(Locale.US));
      }
      return sb.toString();
    }

	public static String getFixedHexString(char v){
		Log.v("getFixedHexString", "char");
		String t = Integer.toHexString(v);
		return getFixedWidth(t, 4, '0');
	}

	public static String getFixedHexString(byte v){
		Log.v("getFixedHexString", "byte");
		String t = Integer.toHexString(v);
		//TODO casting checks, retain sign & trim
		return getFixedWidth(t, 2, '0');
	}

	public static String getFixedHexString(short v){
		Log.v("getFixedHexString", "short");
		String t = Integer.toHexString(v);
		return getFixedWidth(t, 4, '0');
	}

	public static String getFixedHexString(int v){
		Log.v("getFixedHexString", "int");
		String t = Integer.toHexString(v);
		return getFixedWidth(t, 8, '0');
	}

	public static String getFixedHexString(long v){
		Log.v("getFixedHexString", "long");
		String t = Long.toHexString(v);
		return getFixedWidth(t, 16, '0');
	}

	/** Get utf8 byte array.
	 *
	 * @param str
	 * @return array of NULL if error was found
	 * @author
	 *         http://stackoverflow.com/questions/6064510/how-to-get-ip-address-of
	 *         -the-device/13007325#13007325 */
	public static byte[] getUTF8Bytes ( String str ) {
		try {
			return str.getBytes("UTF-8");
		} catch (Exception ex) {
			return null;
		}
	}

	private Numbers () {}

}