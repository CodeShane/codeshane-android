/* codeshane.ui.preference.FontColorPreference - Part of a CodeShane Solution.
 * Copyright � 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 */
package com.codeshane.ui.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/** com.codeshane.and.preference.FontColorPreference
 * <p>  </p>
 * [code][/code]
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 1.0, Apr 7, 2013
 */
public class TextColorPreference extends AbsColorPreference {

	public TextColorPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/** @see com.codeshane.ui.preference.AbsColorPreference#setColor(android.widget.TextView, int) **/
	@Override
	public void setColor(TextView textView, int color) {
		textView.setTextColor(color);
	}

}