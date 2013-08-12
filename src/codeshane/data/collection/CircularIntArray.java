/* com.codeshane.AndLib - A CodeShane Solution.
 * Copyright � 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 */
package codeshane.data.collection;

/** com.codeshane.data.collections.CircularIntArray
 * <p>  </p>
 * [code][/code]
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 1.0, Apr 30, 2013
 */
public class CircularIntArray {

	protected int[] mArray;

	protected int pos = 0;

	public CircularIntArray(int size){
		mArray = new int[size];
	}

	public void setValue(int pos, int value){
		mArray[pos]=value;
	}

	/** Returns the value at the current position.
	 * */
	public int getValue(){
		return mArray[pos];
	}

	/** Returns the value at the specified position. */
	public int getValue(int position){
		return mArray[position];
	}

	/** Manually set the current position. */
	public void setPosition(int position){ pos = position; }

	/** Circularly increments the position and returns its new position. */
	public int next() {
		pos = (++pos<mArray.length)?pos:0;
		return pos;
	}

	/** Circularly decrements the position and returns its new position. */
	public int prev() {
		pos = (--pos>0)?pos:mArray.length-1;
		return pos;
	}

	/** Get the current position. */
	public int getPosition(){ return pos; }

	/** Returns the length of the backing int array. */
	public int length(){
		return mArray.length;
	}

	/** Returns the value at the current position, then increments the position. (Typical stream behavior.) */
	public int read(){
		int r = mArray[pos];
		next();
		return r;
	}

	/** Convenience method for get(next()).
	 * Circularly increments the position and returns the value at the new position. */
	public int getNext() { return getValue(next()); }

	/** Convenience method for get(prev()).
	 * Circularly decrements the position and returns the value at the new position. */
	public int getPrev() { return getValue(prev()); }



}