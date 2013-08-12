package codeshane.data.collection;

import java.util.LinkedList;

/** com.codeshane.data.collections.CircularLinkedList
 * <p>  </p>
 * [code][/code]
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 1.0, Apr 30, 2013
 */
class CircularLinkedList<E> extends LinkedList<E> {
	private static final long serialVersionUID = 7332081357308202215L; //implements List<E>
	int size;
    transient Object[] array;

	/* Circles List instead of throwing IndexOutOfBoundsException.
	 * @see java.util.LinkedList#get(int)
	 */
    @Override
    public E get(int location) {
        if (location >= 0 && location < size) {
        	return super.get(location);
        } else {
        	// modulus returns division remainder, which will always be less than the size:
        	return super.get(location%size);
        }
    }

}