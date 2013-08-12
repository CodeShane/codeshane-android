/* Email is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson. All Rights Reserved.
 * See LICENSE file or visit codeshane.com for more information. */

package com.codeshane.net;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/** An email to be sent. Extras can be loaded via bundle or using method chaining.
 * <p>{@code Email a = new Email(this).setTo("shane@codeshane.com").setSubject("howdy").setBody("Nice to see you.").send();}</p>
 * <p>{@code a.setSubject("spam?").send();}</p>
 * <p>{@code a.clear().setTo("spam@example.com").setSubject("spam alert").setBody("I found some spam.").send();}</p>
 * */
public class Email {

	protected Context	mContext;
	protected Bundle	mExtras;
	protected CharSequence mDialogTitle;

	public static final String	EXTRA_MIME = "com.codeshane.types.MIME";

	public Email ( Context context ) {
		mContext = context;
		mExtras = new Bundle();
	}

	public Email ( Context context, Bundle bundle ) {
		mContext = context;
		mExtras = bundle;
	}

	public Email clear() {
		mExtras = new Bundle();
		return this;
	}

	public Email set(Bundle b) {
		mExtras = b;
		return this;
	}

	/** Set the title displayed in the {@link Dialog}.
	 *
	 * @return this Email instance for chaining */
	public Email setDialogTitle ( CharSequence title ) {
		mDialogTitle = title;
		return this;
	}

	/** Set the dialog title using the given resource id.
	 *
	 * @return This Email instance for chaining.  */
	public Email setDialogTitle ( int titleResId ) {
		return setDialogTitle(mContext.getText(titleResId));
	}

	/** Set the email body.
	 *
	 * @return This Email instance for chaining.  */
	public Email setBody ( String body ) {
		mExtras.putString(Intent.EXTRA_TEXT, body);
		return this;
	}

	/** Set the email subject.
	 *
	 * @return This Email instance for chaining.  */
	public Email setSubject ( String subject ) {
		mExtras.putString(Intent.EXTRA_SUBJECT, subject);
		return this;
	}

	/** Set the email recipient(s).
	 *
	 * @return This Email instance for chaining.  */
	public Email setTo ( String... emails ) {
		mExtras.putStringArray(Intent.EXTRA_EMAIL, emails);
		return this;
	}

	/** Set the email CC recipient(s).
	 *
	 * @return This Email instance for chaining.  */
	public Email setToCc ( String... emails ) {
		mExtras.putStringArray(Intent.EXTRA_CC, emails);
		return this;
	}

	/** Set the email BCC recipient(s).
	 *
	 * @return This Email instance for chaining.  */
	public Email setToBcc ( String... emails ) {
		mExtras.putStringArray(Intent.EXTRA_BCC, emails);
		return this;
	}

	/** @see Intent#setType(String) */
	public Email setType ( String mime ) {
		mExtras.putString(EXTRA_MIME, mime);
		return this;
	}

	public void send () {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.putExtras(mExtras);

		String mime = mExtras.getString(EXTRA_MIME);
		mime = (null==mime)?"":mime;

		try {
			mContext.startActivity(Intent.createChooser(i, mDialogTitle));
		} catch (ActivityNotFoundException ex) {
			onActivityNotFoundException(ex);
		}

	}

	/** Override this method to provide special handling for a blank chooser. By default this method does nothing; Android already informs the user of the problem via a message within the chooser.
	 * @see ActivityNotFoundException
	 * */
	public void onActivityNotFoundException(ActivityNotFoundException ex){}

}
