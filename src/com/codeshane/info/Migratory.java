/* Migratory is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson. All Rights Reserved.
 * See LICENSE file or visit codeshane.com for more information. */

package com.codeshane.info;

/** Handles data version migrations for a kind of Storage.
 *
 * @param S the type of data store.
 *
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Jun 20, 2013
 * @version 1
 */
public interface Migratory<S> extends Versioned {

	/** Return the data store's incremental version id. */
	@Override public int getVersion();

	/** Return the current implementation's incremental version id. */
	public int getLatestVersion();

	/** Update data store to latest version. */
	public void updateVersion();

	/** Create a new data store. */
	public boolean createStore();

	/** Populate data store with the default data set in the current version. */
	public boolean initializeStore(S store);

}

//public interface Migration<T> {
//	/** Migrate rhe data store (of given type) from one version to another.
//	 * */
//	public boolean migrate(T migratee, int from, int to);
//}
//
//public interface MigrationAgent {
//
//}