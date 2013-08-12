/*
 * com.codeshane.AndLib - A CodeShane Solution.
 * Copyright (C) 2013 - Shane Ian Robinson (shane@codeshane.com)
 * All rights reserved.
 *
 */
package com.codeshane.io.sharedpreferences;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.codeshane.info.Device;
import com.codeshane.util.Numbers;
import com.googlecode.androidannotations.annotations.Background;

// TODO check Editor.putStringSet & Editor.getStringSet source:
// http://source.android.com/source/downloading.html  ( 16 GB )

/**
 * <p>
 * Facade adding array support and helper methods to {@code SharedPreferences}.
 * Lazy-loads and keeps references to each requested {@link SharedPreferences} (a system-provided singleton.)
 * </p>
 * <ul>
 * <li><b>General {@code SharedPreferences}</li>
 * <li>{@code ((Activity)this).getPreferences(0); }}</li>
 * <li>{@code // is equivalent to:}</li>
 * <li>{@code ((ContextWrapper)this).getSharedPreferences(getLocalClassName(), 0);}</li>
 * <li><b>Preference Framework</b></li>
 * <li>{@code PreferenceManager.getDefaultSharedPreferences(this);}</li>
 * </ul>
 * @author <a href="mailto:shane@codeshane.com">Shane Ian Robinson</a>
 * @since v1 - Mar 15, 2013
 * @version v3 - Mar 15, 2013
 * @version v4 - Mar 15, 2013 13:31 <a href="mailto:shane@codeshane.com">Shane Ian Robinson</a>
 */
public class Sprefs implements SharedPreferences {
	public static final String KEY_SUFFIX_QTY = "_QTY";
	public static final String KEY_SUFFIX_POS = "_POS";
	public static final String KEY_SUFFIX_ID  = "_ID#_";

	public static final String NAME_SUFFIX_DEFAULT = "_preferences";

	/** Create a {@code Sprefs} wrapper for the given {@code SharedPreferences}. */
	public Sprefs ( SharedPreferences sharedPreferences ) {
		mPrefs = sharedPreferences;
	}

	/** Create a {@code Sprefs} wrapper for the default {@code SharedPreferences}
	 * file, as used within the {@code Preference} Framework.
	 * @see Sprefs#getApplicationPreferences(Context)
	 * */
	public Sprefs ( Context context ) {
		mPrefs = getApplicationPreferences(context);
	}

	/** Returns the Sprefs wrapper for the Application's SharedPreferences file,
	 * used within the {@code Preference} Framework by {@code Preference}s and
	 * by {@code PreferenceActivity}s.
	 * Use this to retrieve the user preferences that have been stored there.
	 * @see PreferenceManager#getDefaultSharedPreferences(Context)
	 * */
	public static Sprefs getApplicationPreferences(final Context context) {
		return new Sprefs(context.getSharedPreferences(Sprefs.getApplicationSharedPreferencesName(context), 0));
	}

	/** Returns the Sprefs wrapper for the default SharedPreferences file for a given Activity.
	 *
	 * @see Activity#getPreferences(int)
	 * */
	public static Sprefs getActivityPreferences(final Activity activity) {
		return new Sprefs(activity.getSharedPreferences(Sprefs.getActivitySharedPreferencesName(activity), 0));
	}

	protected SharedPreferences mPrefs = null;

	/* Utility methods - Array KEYS, names */
	/** Returns the name of the {@code Preference}s' ({@code Preference} Framework)'s {@code SharedPreferences} file for the given {@code Context}.
	 * <p><i>ex: "<package-name>_preferences"</i></p>
	 * <p>For use with {@code PreferenceActivity} and it's associated {@code SharedPreferences}.</p>
	 * <p>Application Preferences.</p>
	 * @see PreferenceManager#getDefaultSharedPreferences(Context)
	 * @see SharedPreferences#getDefaultSharedPreferencesName() */
	public static String getApplicationSharedPreferencesName(Context context) { return context.getPackageName() + NAME_SUFFIX_DEFAULT; }

	/** Returns the of the {@code Activity}'s default {@code SharedPreferences} file.
	 * <i>ex: "<class-name>"</i>
	 * @see Activity#getPreferences(int)
	 * @see Activity#getLocalClassName()
	 * */
	public static String getActivitySharedPreferencesName(Activity activity) { return activity.getLocalClassName(); }

	@Deprecated
	public static String genLegacyArrayItemKey(String arrayKeyPrefix, int index){ return arrayKeyPrefix + index; }
	public static String genArrayItemKey(String arrayKeyPrefix, int index){ return arrayKeyPrefix.concat(KEY_SUFFIX_ID).concat(Numbers.getFixedHexString(index)); }
	public static String genArrayLengthKey(String arrayKeyPrefix){ return arrayKeyPrefix.concat(KEY_SUFFIX_QTY); }
	public static String genArrayPositionKey(String arrayKeyPrefix){ return arrayKeyPrefix.concat(KEY_SUFFIX_POS); }

	/* Utility methods - Array LENGTH */
	public static int getLength(SharedPreferences prefs, String arrayKeyPrefix){
		return prefs.getInt(Sprefs.genArrayLengthKey(arrayKeyPrefix), 0); }

	/** Sets the variable storing the length of an array.
	 *
	 * @param prefs
	 *            the prefs
	 * @param arrayKeyPrefix
	 *            the array key prefix
	 * @param value
	 *            the value */
	public static void setLength(SharedPreferences prefs, String arrayKeyPrefix, int value){
		prefs.edit().putInt(genArrayLengthKey(arrayKeyPrefix), value).commit(); }

	/* Utility methods - Array POSITION */

	public static int getPosition(SharedPreferences prefs, String arrayKeyPrefix) {
		return prefs.getInt(genArrayPositionKey(arrayKeyPrefix), 0); }
	public static void setPosition(SharedPreferences prefs, String arrayKeyPrefix, int value) {
		prefs.edit().putInt(genArrayPositionKey(arrayKeyPrefix), value).commit(); }

	/* Load an array from SharedPreferences */
	public static String[] getStringArray(SharedPreferences prefs, String key, String defValue) {
		int len = getLength(prefs, key);
		String[] values = new String[len];
		for (int i=0;i<len;i++) {
			values[i] = prefs.getString(genArrayItemKey(key,i), defValue);
		}
		return values;
	}
	public static int[] getIntArray(SharedPreferences prefs, String key, int defValue) {
		int len = getLength(prefs, key);
		int[] values = new int[len-1];
		for (int i=0;i<len;i++) {
			values[i] = prefs.getInt(genArrayItemKey(key,i), defValue);
		}
		return values;
	}
	public static long[] getLongArray(SharedPreferences prefs, String key, long defValue) {
		int len = getLength(prefs, key);
		long[] values = new long[len-1];
		for (int i=0;i<len;i++) {
			values[i] = prefs.getLong(genArrayItemKey(key,i), defValue);
		}
		return values;
	}
	public static float[] getFloatArray(SharedPreferences prefs, String key, float defValue) {
		int len = getLength(prefs, key);
		float[] values = new float[len-1];
		for (int i=0;i<len;i++) {
			values[i] = prefs.getFloat(genArrayItemKey(key,i), defValue);
		}
		return values;
	}
	public static boolean[] getBooleanArray(SharedPreferences prefs, String key, boolean defValue) {
		int len = getLength(prefs, key);
		boolean[] values = new boolean[len-1];
		for (int i=0;i<len;i++) {
			values[i] = prefs.getBoolean(genArrayItemKey(key,i), defValue);
		}
		return values;
	}

	public static void renameInt(SharedPreferences prefs, String oldKey, String newKey) { prefs.edit().putInt(newKey, prefs.getInt(oldKey, 0)).remove(oldKey).commit(); }
	public static void renameLong(SharedPreferences prefs, String oldKey, String newKey) { prefs.edit().putLong(newKey, prefs.getLong(oldKey, 0L)).remove(oldKey).commit(); }
	public static void renameFloat(SharedPreferences prefs, String oldKey, String newKey) { prefs.edit().putFloat(newKey, prefs.getFloat(oldKey, 0F)).remove(oldKey).commit(); }
	public static void renameString(SharedPreferences prefs, String oldKey, String newKey) { prefs.edit().putString(newKey, prefs.getString(oldKey, "")).remove(oldKey).commit(); }
	public static void renameBoolean(SharedPreferences prefs, String oldKey, String newKey) { prefs.edit().putBoolean(newKey, prefs.getBoolean(oldKey, false)).remove(oldKey).commit(); }

	public static void renameIntArray(Sprefs prefs, String oldKey, String newKey) { prefs.edit().putIntArray(newKey, Sprefs.getIntArray(prefs, oldKey, 0)).deleteArray(oldKey).commit(); }
	public static void renameLongArray(Sprefs prefs, String oldKey, String newKey) { prefs.edit().putLongArray(newKey, Sprefs.getLongArray(prefs, oldKey, 0)).deleteArray(oldKey).commit(); }
	public static void renameFloatArray(Sprefs prefs, String oldKey, String newKey) { prefs.edit().putFloatArray(newKey, Sprefs.getFloatArray(prefs, oldKey, 0)).deleteArray(oldKey).commit(); }
	public static void renameStringArray(Sprefs prefs, String oldKey, String newKey) { prefs.edit().putStringArray(newKey, Sprefs.getStringArray(prefs, oldKey, "")).deleteArray(oldKey).commit(); }
	public static void renameBooleanArray(Sprefs prefs, String oldKey, String newKey) { prefs.edit().putBooleanArray(newKey, Sprefs.getBooleanArray(prefs, oldKey, false)).deleteArray(oldKey).commit(); }

	@Deprecated
	public Editor editLegacy() { return mPrefs.edit(); }
	public Seditor edit() { return new Seditor(); }

	/* SharedPreferences Pass-Through Accessors */
	public Map<String, ?> getAll() { return mPrefs.getAll(); }
	public String getString(String key, String defValue) { return mPrefs.getString(key, defValue); }
	public int getInt(String key, int defValue) { return mPrefs.getInt(key, defValue); }
	public long getLong(String key, long defValue) { return mPrefs.getLong(key, defValue); }
	public float getFloat(String key, float defValue) { return mPrefs.getFloat(key, defValue); }
	public boolean getBoolean(String key, boolean defValue) { return mPrefs.getBoolean(key, defValue); }
	public boolean contains(String key) { return mPrefs.contains(key); }
	public void registerOnSharedPreferenceChangeListener( OnSharedPreferenceChangeListener listener ) {
		mPrefs.registerOnSharedPreferenceChangeListener(listener); }
	public void unregisterOnSharedPreferenceChangeListener( OnSharedPreferenceChangeListener listener ) {
		mPrefs.unregisterOnSharedPreferenceChangeListener(listener); }

	//TODO
	// Issue 130724 Google Guava Proguard
	// Map<String,?> r = Maps.filterKeys(p, m);
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Deprecated
	public Set<String> getStringSet(String key, Set<String> defValues) {
		if (Device.SUPPORTS_API_11_HONEYCOMB_3_0) return mPrefs.getStringSet(key, defValues);

		int count = Sprefs.getLength(mPrefs, key);
		if (count<1) return defValues;

		Set<String> r = new LinkedHashSet<String>();
		String d = (null!=defValues)?defValues.iterator().next():"";
		String[] values = getStringArray(mPrefs, key, d);
		Collections.addAll(r, values);
		return r;
	}

	public class Seditor implements SharedPreferences.Editor {

		private final Editor	mEditor;

		Seditor() {
			mEditor = mPrefs.edit();
		}

		/* Insert an array into the Seditor */
		public Seditor putIntArray ( String key, int[] values ) {
			deleteArray(key);
			mEditor.putInt(genArrayLengthKey(key), values.length);
			for (int i = 0; i < values.length; i++) {
				mEditor.putInt(genArrayItemKey(key, i), values[i]);
			}
			return this;
		}

		public Seditor putLongArray ( String key, long[] values ) {
			deleteArray(key);
			mEditor.putInt(genArrayLengthKey(key), values.length);
			for (int i = 0; i < values.length; i++) {
				mEditor.putLong(genArrayItemKey(key, i), values[i]);
			}
			return this;
		}

		public Seditor putFloatArray ( String key, float[] values ) {
			deleteArray(key);
			mEditor.putInt(genArrayLengthKey(key), values.length);
			for (int i = 0; i < values.length; i++) {
				mEditor.putFloat(genArrayItemKey(key, i), values[i]);
			}
			return this;
		}

		public Seditor putStringArray ( String key, String[] values ) {
			deleteArray(key);
			mEditor.putInt(genArrayLengthKey(key), values.length);
			for (int i = 0; i < values.length; i++) {
				mEditor.putString(genArrayItemKey(key, i), values[i]);
			}
			return this;
		}

		public Seditor putBooleanArray ( String key, boolean[] values ) {
			deleteArray(key);
			mEditor.putInt(genArrayLengthKey(key), values.length);
			for (int i = 0; i < values.length; i++) {
				mEditor.putBoolean(genArrayItemKey(key, i), values[i]);
			}
			return this;
		}

		/* Insert a single array-item into the Seditor */
		public Seditor putIntArrayItem (String arrayKeyPrefix, int index, int value) {
			mEditor.putInt(genArrayItemKey(arrayKeyPrefix,index), value); return this; }
		public Seditor putLongArrayItem (String arrayKeyPrefix, int index, long value) {
			mEditor.putLong(genArrayItemKey(arrayKeyPrefix,index), value); return this; }
		public Seditor putFloatArrayItem (String arrayKeyPrefix, int index, float value) {
			mEditor.putFloat(genArrayItemKey(arrayKeyPrefix,index), value); return this; }
		public Seditor putStringArrayItem (String arrayKeyPrefix, int index, String value) {
			mEditor.putString(genArrayItemKey(arrayKeyPrefix,index), value); return this; }
		public Seditor putBooleanArrayItem (String arrayKeyPrefix, int index, boolean value) {
			mEditor.putBoolean(genArrayItemKey(arrayKeyPrefix,index), value); return this; }

		public Seditor deleteArray(String arrayKeyPrefix) {
			for (int id=0;id<Sprefs.getLength(mPrefs, arrayKeyPrefix);id++){
				mEditor.remove(genArrayItemKey(arrayKeyPrefix, id));
			}
			mEditor.remove(genArrayLengthKey(arrayKeyPrefix));
			mEditor.remove(genArrayPositionKey(arrayKeyPrefix));
			return this;
		}

		public Seditor renameInt(String oldKey, String newKey) { mEditor.putInt(newKey, mPrefs.getInt(oldKey, 0)).remove(oldKey).commit(); return this; }
		public Seditor renameLong(String oldKey, String newKey) { mEditor.putLong(newKey, mPrefs.getLong(oldKey, 0L)).remove(oldKey).commit(); return this; }
		public Seditor renameFloat(String oldKey, String newKey) { mEditor.putFloat(newKey, mPrefs.getFloat(oldKey, 0F)).remove(oldKey).commit(); return this; }
		public Seditor renameString(String oldKey, String newKey) { mEditor.putString(newKey, mPrefs.getString(oldKey, "")).remove(oldKey).commit(); return this; }
		public Seditor renameBoolean(String oldKey, String newKey) { mEditor.putBoolean(newKey, mPrefs.getBoolean(oldKey, false)).remove(oldKey).commit(); return this; }

		protected Seditor decrementIntArrayItem(String arrayKeyPrefix, int index) {
			mEditor.putInt(genArrayItemKey(arrayKeyPrefix, index-1), mPrefs.getInt(genArrayItemKey(arrayKeyPrefix, index),0)); return this;
		}
		protected Seditor decrementLongArrayItem(String arrayKeyPrefix, int index) {
			mEditor.putLong(genArrayItemKey(arrayKeyPrefix, index-1), mPrefs.getLong(genArrayItemKey(arrayKeyPrefix, index),0)); return this;
		}
		protected Seditor decrementFloatArrayItem(String arrayKeyPrefix, int index) {
			mEditor.putFloat(genArrayItemKey(arrayKeyPrefix, index-1), mPrefs.getFloat(genArrayItemKey(arrayKeyPrefix, index),0)); return this;
		}
		protected Seditor decrementStringArrayItem(String arrayKeyPrefix, int index) {
			mEditor.putString(genArrayItemKey(arrayKeyPrefix, index-1), mPrefs.getString(genArrayItemKey(arrayKeyPrefix, index),"")); return this;
		}
		protected Seditor decrementBooleanArrayItem(String arrayKeyPrefix, int index) {
			mEditor.putBoolean(genArrayItemKey(arrayKeyPrefix, index-1), mPrefs.getBoolean(genArrayItemKey(arrayKeyPrefix, index),false)); return this;
		}

		/** Delete an ArrayItem and decrement any following items */
		public Seditor deleteIntArrayItem(String arrayKeyPrefix, int index) {
			//increase index by one before beginning so the old 'index' value will be overwritten while
			//by the new 'index' value
			int length = Sprefs.getLength(Sprefs.this, arrayKeyPrefix);
			for (index++; index < length; index++) {
				decrementIntArrayItem(arrayKeyPrefix, index);
			}
			mEditor.remove(genArrayItemKey(arrayKeyPrefix, --index));
			mEditor.putInt(genArrayLengthKey(arrayKeyPrefix), length-1);
			return this;
		}

		/** Delete an ArrayItem and decrement any following items */
		public Seditor deleteLongArrayItem(String arrayKeyPrefix, int index) {
			int length = Sprefs.getLength(Sprefs.this, arrayKeyPrefix);
			for (index++; index < length; index++) {
				decrementLongArrayItem(arrayKeyPrefix, index);
			}
			mEditor.remove(genArrayItemKey(arrayKeyPrefix, --index));
			mEditor.putInt(genArrayLengthKey(arrayKeyPrefix), length-1);
			return this;
		}

		/** Delete an ArrayItem and decrement any following items */
		public Seditor deleteFloatArrayItem(String arrayKeyPrefix, int index) {
			int length = Sprefs.getLength(Sprefs.this, arrayKeyPrefix);
			for (index++; index < length; index++) {
				decrementFloatArrayItem(arrayKeyPrefix, index);
			}
			mEditor.remove(genArrayItemKey(arrayKeyPrefix, --index));
			mEditor.putInt(genArrayLengthKey(arrayKeyPrefix), length-1);
			return this;
		}

		/** Delete an ArrayItem and decrement any following items */
		public Seditor deleteStringArrayItem(String arrayKeyPrefix, int index) {
			int length = Sprefs.getLength(Sprefs.this, arrayKeyPrefix);
			for (index++; index < length; index++) {
				decrementStringArrayItem(arrayKeyPrefix, index);
			}
			mEditor.remove(genArrayItemKey(arrayKeyPrefix, --index));
			mEditor.putInt(genArrayLengthKey(arrayKeyPrefix), length-1);
			return this;
		}

		/** Delete an ArrayItem and decrement any following items */
		public Seditor deleteBooleanArrayItem(String arrayKeyPrefix, int index) {
			int length = Sprefs.getLength(Sprefs.this, arrayKeyPrefix);
			for (index++; index < length; index++) {
				decrementBooleanArrayItem(arrayKeyPrefix, index);
			}
			mEditor.remove(genArrayItemKey(arrayKeyPrefix, --index));
			mEditor.putInt(genArrayLengthKey(arrayKeyPrefix), length-1);
			return this;
		}

		/* Editor compatibility methods */

		// very loosely based on http://stackoverflow.com/questions/12272397/android-backward-compatibility-but-still-utilise-latest-api-features

		/** Newer {@code apply()} calls older {@code commit()} so calls to {@code commit()}
		 * will still be returned a boolean, while calls to {@code apply()} will not. */
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		public void apply() {
			Log.v("threax", "apply()  pre  " + Thread.currentThread().getName());
			commit();
			Log.v("threax", "apply()  post " + Thread.currentThread().getName());
		}

//		private Object commitLock = new Object();

		/** <p><b>{@code apply()}</b> commits to memory immediately and begins an asynchronous commit to persistent storage.</p>
		 * <p><b>{@code commit()}</b></p>Performs a synchronous (blocking) call to commit to persistent storage.
		 * <p>On devices that don't support apply(), commit()
		 * @see SharedPreferences.Editor#commit()
		 * */
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@Background
		public boolean commit() {
			Log.v("threax", "commit() start " + Thread.currentThread().getName());
			if (!Device.SUPPORTS_API_09_GINGERBREAD_2_3) {
				// Atomic write.
				boolean c = mEditor.commit();
				Log.v("Sprefs","commit() succeeded? "+c);
				return c;
			}
			// Atomic write.
			mEditor.apply();
			Log.v("threax", "commit()finish " + Thread.currentThread().getName());
			return true;
		}

		/** <p>{@inheritDoc}</p>
		 * <p>For devices that don't support Honeycomb (3.0, API 11) this will
		 * call putStringArray(), which uses a different mechanism. Devices upgraded
		 * from one OS to the other may have to manually migrate data stored with this
		 * call. This can be done with getStringArray and putStringSet.</p>
		 * @see SharedPreferences.Editor#putStringSet(String, Set)
		 * */
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public Seditor putStringSet(String key, Set<String> values) {
			if (Device.SUPPORTS_API_11_HONEYCOMB_3_0) {
				mEditor.putStringSet(key, values);
				return this;
			}
			String[] v = values.toArray(new String[values.size()]);
			return this.putStringArray(key, v);
		}


		/* Delegators - delegate to standard SharedPreferences.Editor methods */

		/** Delegates this call to the {@code SharedPreferences.Editor}.
		 * @return this {@code Seditor} (fluent/builder interface).
		 * @see SharedPreferences.Editor#putInt(String, int) */
		public Seditor putInt(String key, int value) { mEditor.putInt(key, value); return this; }
		/** Delegates this call to the {@code SharedPreferences.Editor}.
		 * @return this {@code Seditor} (fluent/builder interface).
		 * @see SharedPreferences.Editor#putLong(String, long) */
		public Seditor putLong(String key, long value) { mEditor.putLong(key, value); return this; }
		/** Delegates this call to the {@code SharedPreferences.Editor}.
		 * @return this {@code Seditor} (fluent/builder interface).
		 * @see SharedPreferences.Editor#putFloat(String, float) */
		public Seditor putFloat(String key, float value) { mEditor.putFloat(key, value); return this; }
		/** Delegates this call to the {@code SharedPreferences.Editor}.
		 * @return this {@code Seditor} (fluent/builder interface).
		 * @see SharedPreferences.Editor#putString(String, String) */
		public Seditor putString(String key, String value) { mEditor.putString(key, value); return this; }
		/** Delegates this call to the {@code SharedPreferences.Editor}.
		 * @return this {@code Seditor} (fluent/builder interface).
		 * @see SharedPreferences.Editor#putBoolean(String, boolean) */
		public Seditor putBoolean(String key, boolean value) { mEditor.putBoolean(key, value); return this; }
		/** Delegates this call to the {@code SharedPreferences.Editor}.
		 * @return this {@code Seditor} (fluent/builder interface).
		 * @see SharedPreferences.Editor#remove(String) */
		public Seditor remove(String key) { mEditor.remove(key); return this; }
		/** Delegates this call to the {@code SharedPreferences.Editor}.
		 * @return this {@code Seditor} (fluent/builder interface).
		 * @see SharedPreferences.Editor#clear() */
		public Seditor clear() { mEditor.clear(); return this; }

	}

}
