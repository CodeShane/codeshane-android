/* Pref is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson. All Rights Reserved.
 * See LICENSE file or visit codeshane.com for more information. */

package com.codeshane.prefs;

/**
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Aug 8, 2013
 * @version 1
 */
@Deprecated // WIP
public interface Pref<T> {
	T getValue();
	void setValue(T value);
	T getDefault();

	String getName();
	String getFullName();
}
