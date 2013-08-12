/* com.codeshane.Affirm - A CodeShane Solution.
 * Copyright � 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 */
package com.codeshane.ui.preference;

import android.content.Context;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/** com.codeshane.and.preference.FontColorPreference
 * <p>  </p>
 * [code][/code]
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 1.0, Apr 7, 2013
 */
public class BackgroundColorPreference extends AbsColorPreference implements OnPreferenceChangeListener {
	private static final String TAG = BackgroundColorPreference.class.getSimpleName();

	CharSequence opacity = null;
//	CharSequence value = getColorValue(key);
//	tv.setTag(value);

	public BackgroundColorPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/** @see com.codeshane.ui.preference.AbsColorPreference#setColor(android.widget.TextView, int) **/
	@Override
	public void setColor(TextView textView, int color) {
		textView.setBackgroundColor(color);
	}

	/** @see com.codeshane.ui.preference.AbsColorPreference#onGetColorValue(com.codeshane.ui.preference.AbsColorPreference, java.lang.CharSequence) **/
	@Override
	public CharSequence onGetColorValue(CharSequence color) {
		Log.w(TAG, "onGetColorValue in "+color);
		if (null==opacity) return color;
		StringBuilder sb = new StringBuilder("#");
		sb.append(opacity);
		sb.append(color.subSequence(3,opacity.length()));
		Log.w(TAG, "onGetColorValue out "+color);
		return color;
	}

	//TODO
//	public void bindOpacitySlider(SeekbarPreference seekbar) {
//		seekbar.setOnPreferenceChangeListener((OnPreferenceChangeListener) this);
//	}
//
	/** @see android.preference.Preference.OnPreferenceChangeListener#onPreferenceChange(android.preference.Preference, java.lang.Object) **/
	public boolean onPreferenceChange(Preference preference, Object newValue) {
//		int v = ((SeekbarPreference)preference).getValue();
//		opacity = Integer.toHexString(v);
//		Log.w(TAG, "opacity="+opacity);
		return false;
	}

}