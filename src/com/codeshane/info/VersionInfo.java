/* Version is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson. All Rights Reserved.
 * See LICENSE file or visit codeshane.com for more information. */

package com.codeshane.info;

/** Interface for getting & setting version information to/from foreign/legacy sources.
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Jun 23, 2013
 * @version 1
 * @see Versioned
 */
public interface VersionInfo<T> {

	/** Return the current implementation's incremental version id. */
	public int getNewestVersion(T version);

	/** Return the data store's incremental version id. */
	public int getVersion(T version);

	/** Set the data store's incremental version id.
	 * @return false if unsuccessful, true if successful.
	 * */
	public boolean setVersion(T version);

}
