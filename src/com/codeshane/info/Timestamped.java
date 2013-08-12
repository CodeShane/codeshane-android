/* This is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson. All Rights Reserved.
 * See LICENSE file or visit codeshane.com for more information. */

package com.codeshane.info;

/**
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Jun 19, 2013
 * @version 1
 *
 * @see codeshane.io.db._Versioned
 * @see com.codeshane.info.Versionable
 */
public interface Timestamped {
	/** Returns UTC unix time. */
	public long getTimestamp();
}
