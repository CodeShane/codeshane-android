/* Prefs is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson. All Rights Reserved.
 * See LICENSE file or visit codeshane.com for more information. */

package com.codeshane.info;

import java.io.PrintStream;
import java.util.Iterator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/** Static untilities for {@code SharedPreferences}.
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Jun 24, 2013
 * @version 1
 */
public final class Prefs {

	public static final String	TAG	= Prefs.class.getSimpleName();

	public static final SharedPreferences getSharedPreferences(Context c){
		return getSharedPreferences(c, null);
	}

	public static final SharedPreferences getSharedPreferences(Context c, String prefsFileName) {
		if (null==c) return null;
		c = c.getApplicationContext();
		if (null==prefsFileName) return PreferenceManager.getDefaultSharedPreferences(c);
		return c.getSharedPreferences(prefsFileName, 0);
	}

	public static final int getVersion(SharedPreferences prefs) { return prefs.getInt(Versioned.KEY_VERSION, 0); }

	public static final boolean setVersion(SharedPreferences prefs, int version) { return prefs.edit().putInt(Versioned.KEY_VERSION, version).commit(); }

	public static final void setVersion(SharedPreferences.Editor editor, int version) { editor.putInt(Versioned.KEY_VERSION, version); }

	public static final boolean increment(SharedPreferences prefs, String key ){//, String originClass, String originFunction){
		int v = prefs.getInt(key, 0);
		if (v == Integer.MAX_VALUE ) return true;
		Log.v("Info.prefs ", key + " = " + v + "+1 ");
		return prefs.edit().putInt(key,++v).commit();
	}

	public static final void echoAll(SharedPreferences prefs, String tag) {
		echoAll(prefs,tag,null);
	}

	public static final void echoAll(SharedPreferences prefs, String tag, PrintStream printStream) {
		Iterator<?> it = prefs.getAll().entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Object o = it.next();
			log(tag , i++ + " " + o.getClass().getSimpleName() + " " + o.toString(), printStream);
		}
	}
	private static final void log(String tag, String msg, PrintStream printStream){
		if (null==printStream) {
			Log.d(tag,msg);
		} else {
			printStream.println(tag + " " + msg);
		}
	}

}