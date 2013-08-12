package com.codeshane.ui.preferencegroup;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

import com.codeshane.io.sharedpreferences.Sprefs;
import com.codeshane.io.sharedpreferences.Sprefs.Seditor;

/**
 * Concrete implementation of {@link BaseResizingPreferenceCategory} for
 * {@link EditTextPreference}s. It can be bound to 'adder'
 * {@code EditTextPreference}(s) which will actually save their changed value as
 * a new {@code EditTextPreference} item.
 */
public class ResizingEditTextPreferenceCategory
extends BaseResizingPreferenceCategory<EditTextPreference, String>
{
	public ResizingEditTextPreferenceCategory(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ResizingEditTextPreferenceCategory(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/** Preference list item - these are the previously persisted items. */
	/** @see com.codeshane.ui.preferencegroup.BaseResizingPreferenceCategory#createItemPreference(java.lang.String, int, java.lang.Object) **/
	@Override protected EditTextPreference createItemPreference(int i, String value) {
		EditTextPreference etp = new EditTextPreference(this.getContext());
		etp.setPersistent(true);
		etp.setKey(Sprefs.genArrayItemKey(getKey(), i));
		etp.setOrder(i);
		etp.setEnabled(true);
		return etp;
	}

	@Override public String[] getValues(){ return Sprefs.getStringArray(getSharedPreferences(), getKey(), ""); }

	/** Removes an emptied item, updates ui on changes {@inheritDoc} */
	@Override protected boolean onItemEdited(EditTextPreference preference, String newValue){
		newValue = newValue.trim();
		if (1 > newValue.length()) {
			removePreference(preference);
			return false;
		}
		updateWidget(preference, newValue);
		return true;
	}

	/** Sets the widget title to the text. */
	static void updateWidget(EditTextPreference preference, String newValue) {
		preference.setTitle(newValue);
	}

	/** @see com.codeshane.ui.preferencegroup.BaseResizingPreferenceCategory#onUpdateItem(android.preference.Preference) **/
	@Override protected EditTextPreference onUpdateItem ( EditTextPreference preference, String newValue ) {
		updateWidget(preference, newValue);
		return preference;
	}

	/** @see com.codeshane.ui.preferencegroup.BaseResizingPreferenceCategory#getPreference(int) */
	@Override public EditTextPreference getPreference ( int index ) {
		EditTextPreference preference = super.getPreference(index);
		// initial setting of Widget
		updateWidget(preference, preference.getText());
		return preference;
	}

	/** @see com.codeshane.ui.preferencegroup.BaseResizingPreferenceCategory#save(android.preference.Preference) **/
	@Override protected void save ( String key, String value ) {
		new Sprefs(this.getSharedPreferences()).edit().putString(key, value).apply();
	}

	/**
	 * @see com.codeshane.ui.preferencegroup.BaseResizingPreferenceCategory#onSetValues(Object[])
	 * @see com.codeshane.ui.preferencegroup.BaseResizingPreferenceCategory#removeAll()
	 *  */
	@Override protected void onSetValues ( String[] newValues ) {
		Sprefs prefs = new Sprefs(this.getSharedPreferences());
		Seditor sedit = prefs.edit();
		String key = getKey();
//		sedit.deleteArray(key);
		sedit.putStringArray(getKey(), newValues);
		sedit.apply();
	}

	/** @see com.codeshane.ui.preferencegroup.BaseResizingPreferenceCategory#deletePersistedValue(android.preference.Preference) **/
	@Override protected void deletePersistedValue ( EditTextPreference preference ) {
		new Sprefs(this.getSharedPreferences()).edit().deleteStringArrayItem(getKey(), preference.getOrder()).apply();
	}

//	AlertDialog getDialog(){
//		EditTextPreference etp = new EditTextPreference(this.getContext());
//		etp.setOnPreferenceChangeListener(adderChangeListener);
//
//		etp.onClick(dialog, which);
//	}

//	/** <p><b>default<b>: {@inheritDoc}</p>
//	 * <p><b>changes</b>: n/a</p>
//	 * @see com.codeshane.ui.preferencegroup.BaseResizingPreferenceCategory#getAdderDialogValue()
//	 */
//	@Override public String getAdderDialogValue () {
//		return "";
//		//XXX
//	}

}


