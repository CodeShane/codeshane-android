package com.codeshane.util;

import java.util.Arrays;

// TODO refactor into TextBuilder
/** Static utility methods relating to text.
 *.
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Aug 12, 2013
 * @version 1
 * @see java.util.regex.Pattern
 * @see com.google.common.base.CaseFormat
 * @see com.google.common.base.Strings
 * @see codeshane.util.text.TextBuilder
 * @see android.text.TextUtils
 * @see com.google.common.base.Strings.*
 */
public final class Text {
	public static final String	TAG	= Text.class.getPackage().getName() + "." + Text.class.getSimpleName();

	/** Static utility class. */
	private Text () {}

//	/** Create a StringBuilder */
//	public static final StringBuilder init() {
//		return new StringBuilder();
//	}

	/** Returns true if the exemplar (comparison) String matches any string in the String[] values array. */
	public static boolean equalsAnyIgnoreCase(String exemplar, String[] values){
		for (String s: values) {
			if (exemplar.equalsIgnoreCase(s)) return true;
		}
		return false;
	}

	/** Returns the input String, or an empty String if input was null.
	 * @see com.google.common.base.Strings.nullToEmpty(String) */
	@Deprecated
	public static String getStringNotNull(String in){
		if (null!=in) return in;
		return "";
	}

//	Pattern p;

	public static final String EOL_UNIX = "\n";
	public static final String EOL_PC = "\r\n";
	public static final String EOL_MACTIQUE = "\r";
	public static final String EOL_SYSTEM = System.getProperty("line.separator", EOL_UNIX);
	public static final String CHAR_TAB = "\t";
	public static final String CHAR_COMMA = ",";
	public static final String CHAR_SPACE = " ";
	public static final String CHARARRAY_COMMA_SPACE = ", ";
	public static final String CHAR_PIPE = "|";

	/** Concatenates {@code Strings}. Alias for merge(strings, ""); */
	public static String mergeNone(String[] strings){ return merge(strings, ""); }
	/** Concatenates {@code Strings}, delimited by a comma after every input {@code String}.<br/>Alias for merge(strings, ","); */
	public static String mergeComma(String[] strings){ return merge(strings, CHAR_COMMA); }
	/** Concatenates {@code Strings}, delimited with the tab character after every input {@code String}.<br/>Alias for merge(strings, "\t"); */
	public static String mergeTab(String[] strings){ return merge(strings, CHAR_TAB); }
	/** Concatenates {@code Strings}, delineated with the system end-of-line character(s) after every input {@code String}.
	 * Alias for merge(strings, EOL_SYSTEM).
	 * */
	public static String mergeLines(String[] strings){ return merge(strings,EOL_SYSTEM); }
	/** Concatenates strings as lines, delineated with local system line-endings.
	 * Alias for merge(strings, "\n").
	 * */
	public static String mergeLinesUnix(String[] strings){ return merge(strings,EOL_UNIX); }
	/** Concatenates strings as lines, delineated with windows line-endings.
	 * Alias for merge(strings, "\r\n"). Deprecated from the get-go as an analog relic.
	 * @deprecated Windows OS.
	 * */
	@Deprecated public static String mergeLinesPc(String[] strings){ return merge(strings, EOL_PC); }
	/** Concatenates strings as lines, delineated with windows line-endings.
	 * Alias for merge(strings, "\r"). Deprecated from the get-go as an analog relic.
	 * @deprecated Antiquated Macintosh Systems (OSX 9?)
	 * */
	@Deprecated public static String mergeLinesMactique(String[] strings){ return merge(strings, EOL_MACTIQUE); }

//	convert these to some sort of iterator? merge(), also?
	public static String caseUpper(String string) { // UPPER, lower, UpperCamel,LowerCamel
		StringBuilder sb = new StringBuilder(string);
		int l = sb.length();
		for (int i=0; i < l;i++){
//			java.lang.Character et al;
//			java.lang.Character.toLowerCase(sb.charAt(i));
			char c = sb.charAt(i);
			if (Character.isLowerCase(c)){
				Character.toUpperCase(c);
				sb.deleteCharAt(i);
				sb.insert(i, c);
			}
		}
		return sb.toString();
	}
	public static String caseLower(String string) {
		StringBuilder sb = new StringBuilder(string);
		int l = sb.length();
		for (int i=0; i < l;i++){
//			java.lang.Character et al;
//			java.lang.Character.toLowerCase(sb.charAt(i));
			char c = sb.charAt(i);
			if (Character.isLowerCase(c)){
				Character.toUpperCase(c);
				sb.deleteCharAt(i);
				sb.insert(i, c);
			}
		}
		return sb.toString();
	}
//	public static String spacedUnderscore(String string){ // dash/soft-hyphen,comma,space!,tab,none!
//		return merge(strings,EOL_UNIX);
//	}

	/** Concatenates strings with separated by the specified delimiter.
	 * Uses a StringBuilder for efficiency. */
	public static String merge(String[] lines, String delimiter) {
		StringBuilder sb = new StringBuilder();
		int len = lines.length;
		String s;
		for (int i = 0; i < len; i++) {
			s = lines[i].trim();
			if (0<s.length()) sb.append(s).append(delimiter);
		}
		//TODO: sb.UNappend(delimiter); DON'T append last?
		return sb.toString();
	}

	/** Splits the text by regex delimiter, see Pattern */
	@Deprecated // yagni
	public static String[] getTextFromBlock (String block, String delimiter){
		return block.split(delimiter);// "[\r\nn]+"); // or maybe [\\r\\nn]+
	}

	public static StringBuilder insertCharEvery (StringBuilder sb, char insert, int afterEveryQty ) {
		int l = sb.length();
		int i=afterEveryQty+1;
		for (; i < l;i+=afterEveryQty){ sb.insert(i, insert); }
		return sb;
	}

	public static StringBuilder replaceCharEvery (StringBuilder sb, char insert, int afterEveryQty ) {
		int l = sb.length();
		int i=afterEveryQty+1;
		for (; i < l;i+=afterEveryQty) { sb.setCharAt(i, insert); }
		return sb;
	}

	@Deprecated
	public static String getFixedWidth(String string, int targetLength, char filler) {
		return getFixedWidth(string, targetLength, filler, true, false);
	}

	private static String pad(String string, int targetLength, char filler, boolean left){
		if (null==string) return null;
		int len = string.length();
		char[] fillers = new char[targetLength-len];
		Arrays.fill(fillers, filler);
		if (targetLength<len) return string;
		StringBuilder sb = new StringBuilder();
		if (left) sb.append(fillers);
		sb.append(string);
		if (!left) sb.append(fillers);
		return sb.toString();
	}

	public static String padLeft(String string, int targetLength, char filler){
		return pad(string, targetLength, filler, true);
	}

	public static String padRight(String string, int targetLength, char filler){
		return pad(string, targetLength, filler, false);
	}

	/** @see PadLeft
	 * */
	@Deprecated
	public static String getFixedWidth(String string, int targetLength, char filler, boolean alignRight, boolean elipsis) {
		int haveLength = string.length();
		int change = targetLength - haveLength;
		//      -6 = 2 - 8;

		switch (Integer.signum(change)) {
			case 1: // Too short, pad with filler
				char[] out = new char[targetLength];
				char[] in = string.toCharArray();
				if (alignRight) {
					for (int i=0;i<targetLength;i++){
						out[i] = (i<change)?filler:in[i-change];
					}
				} else { // i=6 d[8] o[6]
					for (int i=0;i<targetLength;i++){
						out[i] = (i>=in.length)?filler:in[i];
					}
				}
				return String.copyValueOf(out);
			case -1: // Too long, remove extra chars
				if (alignRight) {
					return string.substring(java.lang.Math.abs(change));
				} else {
					return string.substring(0,targetLength);
				}

			case 0: // Correct length
			default: // horrible mess up
				return string;
		}
	}

//	public static StringBuilder paddy(StringBuilder sb){
//		sb.append(c)
//	}
}

/*

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.Formatter;
import javax.annotation.Nullable;

public final class com.google.common.base.Strings

	public static String nullToEmpty(@Nullable String string);
	public static @Nullable String emptyToNull(@Nullable String string);
	public static boolean isNullOrEmpty(@Nullable String string);
	public static String padStart(String string, int minLength, char padChar);
	public static String padEnd(String string, int minLength, char padChar);
	public static String repeat(String string, int count);
  // ...taking care not to split surrogate pairs.
	public static String commonPrefix(CharSequence a, CharSequence b);
	public static String commonSuffix(CharSequence a, CharSequence b);

   // True when a valid surrogate pair starts at the given {@code index} in the
   // given {@code string}. Out-of-range indexes return false.
	@VisibleForTesting
	static boolean validSurrogatePairAt(CharSequence string, int index) {
	return index >= 0 && index <= (string.length() - 2)
		&& Character.isHighSurrogate(string.charAt(index))
		&& Character.isLowSurrogate(string.charAt(index + 1));
	}
}

 */




