/** Copyright © 2013 Shane Ian Robinson. All Rights Reserved.
 * See LICENSE file or visit codeshane.com for details. */
package com.codeshane.ui.preferencegroup;

import java.util.Iterator;

import android.annotation.TargetApi;
import android.content.Context;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemLongClickListener;

import com.codeshane.info.Device;
import com.codeshane.io.sharedpreferences.Sprefs;

/** <p>
 * Base class for a dynamically-sized PreferenceGroup accepting standard or
 * custom Preference instances of a generic type
 * <P extends Preference>
 * named by concrete sub classes. It helps with loading, updating, insertion,
 * and removal of a given type of Preference and their data.
 * </p>
 * <p>
 * <b>InvocationTargetException</b> means you're trying to inflate XML that
 * references an abstract class <i>(like this one.)</i>
 * </p>
 * [code] <com.codeshane.and.preference.DynamicEditTextPreferences
 * android:key="dynamic_prefs" android:title="@string/dynamic_prefs" /> [/code]
 *
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 1.3, Mar 3, 2013 */
public abstract class BaseResizingPreferenceCategory<P extends Preference, T> extends PreferenceCategory // TaddlePreferences
// implements OnDismissListener, OnClickListener
{
	/** <p><b>default<b>: {@inheritDoc}</p>
	 * <p><b>changes</b>: n/a</p>
	 * @see android.preference.Preference#onCreateView(android.view.ViewGroup)
	 */
	@Override protected View onCreateView ( ViewGroup parent ) {
//		this.setWidgetLayoutResource(0);
		View r = super.onCreateView(parent);

//		r.findViewById(android.R.id.title).setVisibility(View.INVISIBLE);
//		r.findViewById(android.R.id.summary).setVisibility(View.INVISIBLE);
//		r.findViewById(android.R.id.icon).setVisibility(View.INVISIBLE);
//		ViewParent vp = r.findViewById(android.R.id.title).getParent();
//		if (vp instanceof View) {
//			((View)vp).setVisibility(View.INVISIBLE);
//		}
//		r.findViewById(android.R.id.widget_frame).setVisibility(View.INVISIBLE);
		this.setEnabled(true);
		this.setSelectable(false);
		return r;
	}

	public void setOnPreferenceLongClickListener ( PreferenceActivity activity, OnItemLongClickListener listener ) {
		activity.getListView().setOnItemLongClickListener(listener);
	}

	/* UI defaults */
	protected int		dialogIcon		= -1;
	protected String	dialogMessage	= "";
	protected String	dialogTitle		= "";
	protected int		icon			= -1;
	protected int		mDialogLayoutResId = 0;

//	protected AlertDialog.Builder mBuilder = null;

	/* Set UI defaults */
	public void setDefaultDialogIcon ( int rid ) {
		this.dialogIcon = rid;
	}

	public void setDefaultDialogMessage ( String dialogMessage ) {
		this.dialogMessage = dialogMessage;
	}

	public void setDefaultDialogTitle ( String dialogTitle ) {
		this.dialogTitle = dialogTitle;
	}

	public void setDefaultIcon ( int rid ) {
		this.icon = rid;
	}

	public OnPreferenceChangeListener adderChangeListener = new AdderChangeListener();

	public OnPreferenceChangeListener itemChangeListener  = new ItemChangeListener();

//	public OnPreferenceClickListener  baseClickListener	  = new BaseClickListener();

	public BaseResizingPreferenceCategory ( Context context, AttributeSet attrs ) {
		super(context, attrs);
//		this.setOnPreferenceClickListener(baseClickListener);
	}

	public BaseResizingPreferenceCategory ( Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//		this.setOnPreferenceClickListener(baseClickListener);
	}

	/** <p><b>default</b>: {@inheritDoc}</p>
	 * <p><b>changes</b>: Cast to subclass' Preference type.</p>
	 *
	 * @see BaseResizingPreferenceCategory */
	@SuppressWarnings ( "unchecked" )//
	@Override public P getPreference ( int index ) {
		return (P) super.getPreference(index);
	}

	/** {@inheritDoc}<br><br><i>Override when you want to validate input,
	 * just be sure to call super(pref). */
	@Override protected boolean onPrepareAddPreference ( Preference preference ) {
		if (!super.isEnabled()) {
			preference.setEnabled(false);
		}
		return true; // allow adding the preference?
	}

	/* ----------------- create ----------------------------- */

	/** Determine the length of the backing SharedPreferences array,
	 * create Preferences (without values) to support the array,
	 * assign keys to those Preferences that reference the array.
	 * The Preferences will load their values themselves onAttachToActivity.super() */
	public void loadItems () {
		this.removeAll();
		int len = Sprefs.getLength(this.getSharedPreferences(), getKey());
		for (int i = 0; i < len; i++) {
			this.addPreference(loadItem(i));
		}
	}

	/** For initialization only. Requests an item preference that will load its
	 * own value later. */
	@SuppressWarnings ( "unchecked" )
	@TargetApi ( 0xb ) // Build.VERSION_CODES.HONEYCOMB
	private P loadItem ( int id ) {
//		Log.v(TAG, "createSelfLoadingPreference id=" + id);
		P pref = fetchItemPreference(id, null);
		if (-1 != this.icon && Device.SUPPORTS_API_11_HONEYCOMB_3_0) { pref.setIcon(this.icon); }
		if (pref instanceof DialogPreference) {
			DialogPreference dp = (DialogPreference) pref;
			dp.setDialogMessage(dialogMessage);
			dp.setDialogMessage(dialogTitle);
			if (-1 != this.dialogIcon) dp.setDialogIcon(this.dialogIcon);
			pref = (P) dp;
		}
		return pref;
	}

	/** Create new item Preference to be added;
	 * request that a new Preference be created by subclass.
	 * It is not added to the group here. */
	private P fetchItemPreference ( int id, T value ) {
		P pref = createItemPreference(id, value);
		pref.setKey(Sprefs.genArrayItemKey(getKey(), id));
		pref.setOrder(id); // prefgroup.add calls this if unset and group.orderasaddeed true
		pref.setPersistent(true);
		pref.setOnPreferenceChangeListener(itemChangeListener);
		pref = styleItemPreference(pref);
		return pref;
	}

	/** Subclasses should return a newly instantiated Preference using
	 * this.getContext() and assign its value
	 * (if not null.) key, order, listener handled elsewhere. */
	protected abstract P createItemPreference ( int i, T value );

	/** Adder preference should be statically defined, and then automatically
	 * loaded
	 * when the XML preferences are inflated. Setup the adder preferences by
	 * passing
	 * a reference to them into this method. */
	public void registerAdderPreference ( Preference pref ) {
		styleAdderPreference((P) pref);
		pref.setOnPreferenceChangeListener(adderChangeListener);
	}

    /** The dialog, if it is showing. */
//    private Dialog mDialog;

    /** Which button was clicked. */
//    private int mWhichButtonClicked;

	/* ----------------- modify ----------------------------- */

//	/** <p><b>default<b>: {@inheritDoc}</p>
//	 * <p><b>changes</b>: n/a</p>
//	 * @see android.preference.Preference#onClick()
//	 */
//	@Override protected void onClick () {
//		// super.onClick();
////        if (mDialog != null && mDialog.isShowing()) return;
////        showDialog(null);
//
//	}

	/**
     * Shows the dialog associated with this Preference. This is normally initiated
     * automatically on clicking on the preference. Call this method if you need to
     * show the dialog on some other event.
     *
     * @param state Optional instance state to restore on the dialog
     */
//    protected void showDialog(Bundle state) {
//        // Create the dialog
////        final Dialog dialog = mDialog = createDialog();
////        if (state != null) {
////            dialog.onRestoreInstanceState(state);
////        }
////        dialog.setOnDismissListener(this);
////        dialog.show();
//
//
//    }

//    protected Dialog createDialog() {
//        Context context = getContext();
//
////        mWhichButtonClicked = DialogInterface.BUTTON_NEGATIVE;
//
//        mBuilder = new AlertDialog.Builder(context)
//            .setTitle(this.dialogTitle)
//            .setIcon(this.dialogIcon)
//            .setPositiveButton(android.R.string.ok, this)
//            .setNegativeButton(android.R.string.cancel, this);
//
//        View contentView = onCreateDialogView();
//        if (contentView != null) {
//            //onBindDialogView(contentView); // basically set message text.
//            mBuilder.setView(contentView);
//        } else {
//            mBuilder.setMessage(dialogMessage);
//        }
//
////      onPrepareDialogBuilder(mBuilder); // to prepare the dialog , customization
//
////      getPreferenceManager().registerOnActivityDestroyListener(this);
//
//        return mBuilder.create();
//    }
//	/**
//	 * @see android.content.DialogInterface.OnDismissListener#onDismiss(android.content.DialogInterface)
//	 */
//	@Override public void onDismiss ( DialogInterface dialog ) {
////		getPreferenceManager().unregisterOnActivityDestroyListener(this);
//		mDialog = null;
//		onDialogClosed(mWhichButtonClicked == DialogInterface.BUTTON_POSITIVE);
//	}

//	/**
//	 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
//	 */
//	@Override public void onClick ( DialogInterface dialog, int which ) {
//		mWhichButtonClicked = which;
//	}

//    /**
//     * Called when the dialog is dismissed and should be used to save data to
//     * the {@link SharedPreferences}.
//     *
//     * @param positiveResult Whether the positive button was clicked (true), or
//     *            the negative button was clicked or the dialog was canceled (false).
//     * @see AdderChangeListener#onPreferenceChange(Preference, Object)
//     */
//    protected void onDialogClosed(boolean positiveResult) {
//    	if(!positiveResult) return;
//    	//XXX
//    	// 419 <-relate-> 255
//    	addPreference(getAdderDialogValue());
//    }

//    public abstract T getAdderDialogValue();

	public void addPreference(Object newValue) {
		int c = BaseResizingPreferenceCategory.this.getPreferenceCount();
		P pref = fetchItemPreference(c, (T) newValue);
		save(pref.getKey(), (T) newValue);
		if (BaseResizingPreferenceCategory.this.addPreference(pref)) c++;

		Sprefs.setLength(getSharedPreferences(),getKey(), c);
	}

//    /**
//     * Creates the content view for the dialog (if a custom content view is
//     * required). By default, it inflates the dialog layout resource if it is
//     * set.
//     *
//     * @return The content View for the dialog.
//     * @see #setLayoutResource(int)
//     */
//    protected View onCreateDialogView() {
//        if (mDialogLayoutResId == 0) {
//            return null;
//        }
//
//        LayoutInflater inflater = LayoutInflater.from(this.getContext()); // mBuilder.getContext()
//        return inflater.inflate(mDialogLayoutResId, null);
//    }

	/** Implement to modify style/behavior of the "add new item" Preference(s).
	 * Default: returns Preference unchanged. */
	protected P styleAdderPreference ( P pref ) {
		return pref;
	}

	/** Implement to modify style/behavior of the item Preferences.
	 * Default: returns Preference unchanged. */
	protected P styleItemPreference ( P pref ) {
		return pref;
	}

	/* ----------------- delete ----------------------------- */

	@Override public boolean removePreference ( Preference preference ) {
		preference.setPersistent(false);
		deletePersistedValue((P) preference);
		preference.setKey(null);
		return super.removePreference(preference);
	}

	protected void deletePersistedValue ( P preference ){
		Sprefs prefs = new Sprefs(this.getSharedPreferences());
		prefs.edit().deleteStringArrayItem(getKey(), preference.getOrder()).apply();
	}

	/* ----------------- update ----------------------------- */

	/**
	 * <p>Called when an item is changed to determine if the change should be persisted.
	 * Returns true by default, indicating all edits should persist.</p>
	 * <p>Override to implement validation logic, returning true to persist or false to discard.
	 * <b>Do not</b> call through to super, as this will just 'return true;'</p>
	 * OnPreferenceChangeListener.itemChangeListener Should save changes? */
	protected boolean onItemEdited ( P preference, T newValue ) {return true;}

	public void setValues ( T[] newValues ) {
		onSetValues(newValues);
		loadItems();
	}

	@Override public void removeAll () {
		//TODO should delete extra values, but deleting before commit is too risky.
		//TODO easier way to handle primitives, this Android way is verbose and
		//repetitive but Classes hurt performance too much; perhaps using
		//.type() and .class() ?
//		Sprefs.renameArray(this.getSharedPreferences(),this.getKey(), this.getKey()+"_backup");
		super.removeAll();
	}

	@SuppressWarnings ( "unchecked" )
	public T[] getValues () {
		int c = ((PreferenceGroup) this).getPreferenceCount();
		Object[] r = new Object[c];
		for (int i = 0; i < c; i++) {
			r[i] = ((PreferenceGroup) this).getPreference(i);
		}
		return (T[]) r;
	}

	/** Replaces all the persisted preferences with these.
	 *
	 * Sprefs.prefs().edit().putStringArray(getKey(), newValues).apply(); */
	protected void onSetValues ( T[] values ) {
		if (null==values) return;
		int len = values.length;
		if (len < 1) return;

		String TAG = this.getKey();
		Sprefs sprefs = new Sprefs(this.getSharedPreferences());
		if (values[0] instanceof String){
			sprefs.edit().putStringArray(TAG, (String[])values).commit();
		} else if (values[0] instanceof Integer) {
			int[] all = new int[values.length];
			for (int i = 0;i<len;i++) all[i] = (Integer) values[i];
			sprefs.edit().putIntArray(TAG, all).commit();
		} else if (values[0] instanceof Boolean) {
			boolean[] all = new boolean[values.length];
			for (int i = 0;i<len;i++) all[i] = (Boolean) values[i];
			sprefs.edit().putBooleanArray(TAG, all).commit();
		} else if (values[0] instanceof Long) {
			long[] all = new long[values.length];
			for (int i = 0;i<len;i++) all[i] = (Long) values[i];
			sprefs.edit().putLongArray(TAG, all).commit();
		} else if (values[0] instanceof Float) {
			float[] all = new float[values.length];
			for (int i = 0;i<len;i++) all[i] = (Float) values[i];
			sprefs.edit().putFloatArray(TAG, all).commit();
		}
	}

	/** <p>Saves a key-value pair to SharedPreferences. Uses inefficient 'instanceof' calls,
	 * so sub-classes should override it and make a put commit for the value type used by their
	 * Preference, for example:</p>
	 * <pre>
	 *   this.getSharedPreferences().edit().putString(key, (String)value).commit();
	 * </pre>
	 * */
	protected void save ( String key, T value ){
		if (value instanceof String){
			this.getSharedPreferences().edit().putString(key, (String)value).commit();
		} else if (value instanceof Integer) {
			this.getSharedPreferences().edit().putInt(key, (Integer)value).commit();
		} else if (value instanceof Boolean) {
			this.getSharedPreferences().edit().putBoolean(key, (Boolean)value).commit();
		} else if (value instanceof Long) {
			this.getSharedPreferences().edit().putLong(key, (Long)value).commit();
		} else if (value instanceof Float) {
			this.getSharedPreferences().edit().putFloat(key, (Float)value).commit();
		}
	}

	/** Data is loaded, so now is a good time to update. */
	@Override
	protected void onAttachedToActivity () {
		super.onAttachedToActivity();
		loadItems();
	}

	Iterator<P> iterator () {
		return new Iterator<P>() {
			int	next	= 0;

			public boolean hasNext () {
				return (next < BaseResizingPreferenceCategory.this.getPreferenceCount());
			}

			public P next () {
				return BaseResizingPreferenceCategory.this.getPreference(next++);
			}

			public void remove () {
				BaseResizingPreferenceCategory.this.removePreference(BaseResizingPreferenceCategory.this.getPreference(next - 1));
			}
		};
	};

	/** Implement to modify style/behavior of the item Preferences. Similar to
	 * styleItemPreference, but called whenever preference items are being
	 * changed (before the change has been persisted) and This method has access
	 * to the new value. */
	@SuppressWarnings ( "static-method" ) protected P onUpdateItem ( P pref, T newValue ) {
		return pref;
	}

	public class BaseClickListener implements OnPreferenceClickListener {

		/**
		 * @see android.preference.Preference.OnPreferenceClickListener#onPreferenceClick(android.preference.Preference)
		 */
		@Override public boolean onPreferenceClick ( Preference preference ) {
	        Preference p = fetchItemPreference(0, null);
	        p.setPersistent(false);
	        p.setOnPreferenceChangeListener(adderChangeListener);
			return false;
		}
	}

	/**
	 * @see BaseResizingPreferenceCategory#onDialogClosed(boolean)
	 * */
	public class AdderChangeListener implements OnPreferenceChangeListener {
		public boolean onPreferenceChange ( Preference preference, Object newValue ) {
			//XXX
			int c = BaseResizingPreferenceCategory.this.getPreferenceCount();
			P pref = fetchItemPreference(c, (T) newValue);
			save(pref.getKey(), (T) newValue);
			if (BaseResizingPreferenceCategory.this.addPreference(pref)) c++;

			Sprefs.setLength(preference.getSharedPreferences(),
					BaseResizingPreferenceCategory.this.getKey(), c);

			return false;
		}
	}

	public class ItemChangeListener implements OnPreferenceChangeListener {
		@SuppressWarnings ( "unchecked" ) public boolean onPreferenceChange (
			Preference preference, Object newValue ) {
			P p = (P) preference;
			if (onItemEdited(p, (T) newValue)) {
				onUpdateItem(p, (T) newValue);
				return true;
			}
			return false;
		}
	}

}
