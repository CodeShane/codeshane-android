/* com.codeshane.Affirm - A CodeShane Solution.
 * Copyright � 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 */
package com.codeshane.ui.preference;

import java.lang.ref.WeakReference;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Color;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/** com.codeshane.and.preference.FontColorPreference
 * <p>  </p>
 * [code][/code]
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 1.0, Apr 7, 2013
 */
public abstract class AbsColorPreference extends ListPreference {
	private static final String TAG = AbsColorPreference.class.getSimpleName();

	CharSequence[] mColors;

	/** Requires the colors to be defined as an XML string array,
	 * referenced in FontColorPreference xml as entryValues or
	 * by id at runtime, or as a string array at runtime.
	 * */
	public AbsColorPreference ( Context context, AttributeSet attrs ) {
		super(context, attrs);
		mColors = this.getEntryValues();
	}

	/** @see android.preference.ListPreference#onPrepareDialogBuilder(android.app.AlertDialog.Builder) **/
	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		int index = findIndexOfValue(getSharedPreferences().getString(getKey(), "1"));
		ListAdapter listAdapter = (ListAdapter) new AbsColorPreferenceArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, this.getEntryValues() ,index, this); // was this.getEntries() // ThemeArrayAdapter(getContext(), R.layout.themelist, this.getEntryValues() ,index, this);
		super.onPrepareDialogBuilder(builder);
		builder.setAdapter(listAdapter, this);
	}

	public void setResult(String newValue) {
		if(this.callChangeListener(newValue)) {
			this.setValue(newValue);
		}
		this.getDialog().dismiss();
	}

	/** Override to modify color values. */
	public CharSequence onGetColorValue(CharSequence color){return color;}

	public abstract void setColor(TextView textView, int color);

	public class AbsColorPreferenceArrayAdapter extends ArrayAdapter<CharSequence> implements OnClickListener {

		int index;

		public AbsColorPreferenceArrayAdapter(Context context, int textViewResourceId, CharSequence[] objects, int selected, AbsColorPreference absColorPreference ) {
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
				CharSequence value = getColorValue(key);
				value = onGetColorValue(value);
				tv.setTag(value);
				int color = getColorInt(value);
				AbsColorPreference.this.setColor(tv, color);
				tv.setOnClickListener(this);
				mTextView = new WeakReference<TextView>(tv);
			}
			return itemView;
		}

		@Override public void onClick(View v) {
			AbsColorPreference.this.setResult((String)v.getTag());
		}

		public CharSequence getColorValue(CharSequence key){
			int pos = this.getPosition(key);
			if (-1==pos) {
				pos = 0;
			}
			return mColors[pos];
		}

		public int getColorInt(CharSequence colorValue){
			return Color.parseColor((String) colorValue);
		}
	}
}
