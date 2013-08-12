/* IntListPref is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson. All Rights Reserved.
 * See LICENSE file or visit codeshane.com for more information. */

package com.codeshane.prefs;

/**
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Aug 8, 2013
 * @version 1
 */
@Deprecated // WIP
public class IntListPref implements Pref<Integer> {
	public static final String NAME_SIMPLE = IntListPref.class.getSimpleName();
	public static final String NAME_FULL = IntListPref.class.getPackage().getName() + "." + IntListPref.class.getSimpleName();

	public Integer value = 18;

	@Override public Integer getValue () {
		return value;
	}

	@Override public void setValue ( Integer value ) {
		this.value = (Integer) value;
	}

	@Override public Integer getDefault () {
		return Integer.valueOf(18);
	}

	@Override public String getName () {
		return NAME_SIMPLE;
	}

	@Override public final String getFullName () {
		return NAME_FULL;
	}
}
