/** FontSizePreference is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson.
 * All Rights Reserved.
 * See LICENSE file for details. */

package com.codeshane.ui.preference;

import java.lang.ref.WeakReference;

import com.codeshane.ui.preference.AbsColorPreference.AbsColorPreferenceArrayAdapter;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Jun 3, 2013
 * @version 1
 */
public class FontSizePreference extends ListPreference {
	public static final String	TAG	= FontSizePreference.class.getPackage().getName() + "." + FontSizePreference.class.getSimpleName();

	CharSequence[] mValues;

	/** <p><b>default<b>: {@inheritDoc}</p>
	 * <p><b>changes</b>: </p>
	 *
	 * @see FontSizePreference */
	public FontSizePreference ( Context context, AttributeSet attrs ) {
		super(context, attrs);
		mValues = this.getEntryValues();
	}

	protected WeakReference<TextView> mTextView;

	public TextView getTextView() {
		return mTextView.get();
	}

	/** <p><b>default<b>: {@inheritDoc}</p>
	 * <p><b>changes</b>: n/a</p>
	 * @see android.preference.ListPreference#getEntries()
	 */
	@Override public CharSequence[] getEntries () {
		// TODO stub
		return super.getEntries();

	}

	/** @see android.preference.ListPreference#onPrepareDialogBuilder(android.app.AlertDialog.Builder) **/
	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		int index = findIndexOfValue(getSharedPreferences().getString(getKey(), "1"));
		ListAdapter listAdapter = (ListAdapter) new FontSizePreferenceArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, this.getEntryValues() ,index, this); // was this.getEntries() // ThemeArrayAdapter(getContext(), R.layout.themelist, this.getEntryValues() ,index, this);
		super.onPrepareDialogBuilder(builder);
		builder.setAdapter(listAdapter, this);
	}

	public void setResult(String newValue) {
		if(this.callChangeListener(newValue)) {
			this.setValue(newValue);
		}
		this.getDialog().dismiss();
	}

	public void onSetTextSize(TextView textView, float textSize) {
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
	}

	public class FontSizePreferenceArrayAdapter extends ArrayAdapter<CharSequence> implements OnClickListener {

		int index;

		public FontSizePreferenceArrayAdapter(Context context, int textViewResourceId, CharSequence[] objects, int selected, FontSizePreference fontSizePreference ) {
			super(context, textViewResourceId, objects);
			index = selected;
		}

		protected WeakReference<TextView> mTextView;

		public TextView getTextView() {
			return mTextView.get();
		}

		/** Creates the custom view(s) for each list-item. */
		@Override public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = super.getView(position, convertView, parent);
			if (itemView instanceof TextView) {
				TextView     tv    = ((TextView)itemView);
				CharSequence key = tv.getText();
				String value = (String) getTextSizeValue(key);
//				value = onGetColorValue(value);
				tv.setTag(value);
				float size = Float.parseFloat(value);
				FontSizePreference.this.onSetTextSize(tv, size);
				tv.setOnClickListener(this);
				mTextView = new WeakReference<TextView>(tv);
			}
			return itemView;
		}

		@Override public void onClick(View v) {
			FontSizePreference.this.setResult((String)v.getTag());
		}

		public CharSequence getTextSizeValue(CharSequence key){
			int pos = this.getPosition(key);
			if (-1==pos) { pos = 0; }
			return mValues[pos];
		}

	}

}
