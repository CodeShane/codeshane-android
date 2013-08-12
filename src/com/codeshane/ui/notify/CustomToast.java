/**
 *    Copyright © 2012, Shane Ian Robinson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * */
package com.codeshane.ui.notify;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Allows simple creation of Toast messages with custom layout
 * and arbitrary durations.
 *
 * @author Shane Ian Robinson <shane@codeshane.com>
 * @since Nov 20, 2012
 * @version 5
 * @see com.google.android.gms.internal.c
 * */
public class CustomToast extends Toast {

	//private static final String	TAG	= CustomToast.class.getSimpleName();

	/** The display duration in millis. */
	private long				mDuration;

	/** The update rate in millis. */
	private long				mRate;

	/** Convenience constructor to make a CustomToast with a basic layout.
	 *
	 * @param context
	 *            the context */
	public CustomToast ( Context context ) {
		this(context, com.codeshane.andlib.R.layout.custom_toast);
	}

	/** Constructor that allows using a custom layout resource. <p><i>Be sure to
	 * include a {@link TextView} with an {@code id} of
	 * {@code android.R.id.message} (aka
	 * "@android:id/message")</p>
	 *
	 * @param context
	 *            the context
	 * @param layoutResource
	 *            the layout resource */
	public CustomToast ( Context context, int layoutResource ) {
		super(context);
		LayoutInflater inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		View layout = inflater.inflate(layoutResource, null);
		this.setView(layout);
		this.setDuration(Toast.LENGTH_LONG);
	}

	/** This method captures Toast.LENGTH_SHORT (0) and Toast.LENGTH_LONG (1) for
	 * compatibility.
	 * It converts these values by adding 2 then multiplying by 1000L.
	 * Use of setDurationMillis() is preferred.
	 *
	 * @param duration
	 *            the new duration */
	@Deprecated @Override public void setDuration ( int duration ) {
		setDurationMillis((duration + 2) * 1000L);
	}

	/** Allows setting a custom Toast duration in milliseconds.
	 *
	 * @param duration
	 *            the new duration millis */
	public CustomToast setDurationMillis ( long duration ) {
		mDuration = duration;
		// The redraw rate has to be increased for shorter durations to prevent flashing effects
		mRate = (duration < 1000) ? 100 : 700;
		return this;
	}

	/** <p><b>default<b>: {@inheritDoc}</p>
	 * <p><b>changes</b>: n/a</p>
	 * @see android.widget.Toast#cancel()
	 */
	@Override public void cancel () {
		if (player!=null) {
			synchronized (player){
				if (player!=null){
					player.cancel();
					player=null;
				}
			}
		}
		super.cancel();
	}

	/** {@inheritDoc}<p><i>This override is to get around the
	 * "no custom Toast messages" restriction.</i></p> */
	@Override public void setText ( CharSequence s ) {
		getTextView().setText(s);
	}

	/** Returns a View from the Toast layout.
	 *
	 * @param viewId
	 *            to find
	 * @return <b>View</b> */
	public View findViewById ( int viewId ) {
		return getView().findViewById(viewId);
	}

	/** Returns the root view of the Toast.
	 *
	 * @return <b>View</b> Root view of the Toast. */
	public View getRootView () {
		return getView();
	}

	/** Returns the TextView that will be shown as a Toast. Mostly you can
	 * just use the chained convenience methods to set it's properties.
	 *
	 * @return <b>TextView</b> with R.id.message */
	public TextView getTextView () {
		return (TextView) getView().findViewById(android.R.id.message);
	}

	/** Convenience method for setting the size of the TextView text.
	 *
	 * @param size
	 *            the size
	 * @return <b>this</b> (fluent/chainable) */
	public CustomToast textSize ( float size ) {
		getTextView().setTextSize(size);
		return this;
	}

	/** Convenience method for setting the size of the TextView text.
	 *
	 * @param size
	 *            the size
	 * @return <b>this</b> (fluent/chainable)
	 * @since v5 2012-05-26
	 * */
	public CustomToast textSize ( int unit, float size ) {
		getTextView().setTextSize(unit, size);
		return this;
	}

	/** Convenience method for setting the color of the TextView text. Returns
	 * 'this' to be chainable.
	 *
	 * @param color
	 *            the color
	 * @return the custom toast */
	public CustomToast textColor ( int color ) {
		getTextView().setTextColor(color);
		return this;
	}

	/** Convenience method for setting the color of the TextView background.
	 *
	 * @param color
	 *            the color
	 * @return <b>this</b> (fluent/chainable) */
	public CustomToast backgroundColor ( int color ) {
		getRootView().setBackgroundColor(color);
		return this;
	}

	/** Convenience method for setting the color of the TextView background.
	 *
	 * @param resid
	 *            the resid
	 * @return <b>this</b> (fluent/chainable) */
	public CustomToast background ( int resid ) {
		getView().setBackgroundResource(resid);
		return this;
	}

	/** Convenience method for setting the text of the TextView. {@inheritDoc}
	 *
	 * @param text
	 *            the {@link CharSequence} of text.
	 * @return <b>this</b> (fluent/chainable) */
	public CustomToast text ( CharSequence text ) {
		setText(text);
		return this;
	}

	/** Convenience method for setting the gravity of the TextView.
	 *
	 * @see android.view.Gravity
	 * @param gravity
	 *            the desired {@link android.view.Gravity}
	 * @param offsetX
	 *            the desired x-offset
	 * @param offsetY
	 *            the desired y-offset
	 * @return <b>this</b> (fluent/chainable) */
	public CustomToast gravity ( int gravity, int offsetX, int offsetY ) {
		super.setGravity(gravity, offsetX, offsetY);
		return this;
	}

	/** Timer for customizing the display duration of the {@code CustomToast}. */
	private CountDownTimer	player	= null;

	/** Show this {@code CustomToast} using the system's {@code Toast} mechanism. */
	private synchronized void showToast () {
		super.show();
	}

	/** Show the CustomToast for the specified (custom) duration
	 * (instead of the Toast.SHORT or Toast.LONG durations.) */
	@Override public void show () {
		// Not synchronized here to allow us to catch (and likely discard)
		// stacking show requests (usually happens when system is busy,
		// or called too frequently.
		if (null != player) {
			if (!showCollisions()) return;
		}
		player = new Player(mDuration, mRate).start();
	}

	/** Displays the {@code Toast} with a specific duration and drawing interval,
	 * both in milliseconds.
	 * <p>Default {@link Toast} only has two durations which cannot be changed,
	 * so this class continuously calls .show(), at the given interval, until
	 * the duration will be reached. */
	private class Player extends CountDownTimer {
		// TODO BOLO for better alternative for Toast from background

		/** <p>Create a {@code Toast} {@code Player} capable of custom
		 * durations.</p>
		 *
		 * @param durationMillis
		 *            how long the {@link Toast} should be shown.
		 * @param intervalMillis
		 *            how often to refresh the {@code Toast}.
		 * @see android.os.CountDownTimer#CountDownTimer(long,long) */
		public Player ( long millisInFuture, long countDownInterval ) {
			super(millisInFuture, countDownInterval);
		}

		/** <p>Continues showing the {@code Toast}.</p>
		 *
		 * @see android.os.CountDownTimer#onTick(long) */
		@Override public void onTick ( long millisUntilFinished ) {
			CustomToast.this.showToast();
		}

		/** <p>Cancels the {@code Toast}.</p>
		 *
		 * @see android.os.CountDownTimer#onFinish() */
		@Override public void onFinish () {
			CustomToast.this.cancel();
		}

	}

	/** This method is called when a CustomToast is trying to start while another
	 * is still active. Returns false if the CustomToast should be discarded,
	 * or true if it should be shown.
	 * By default just returns false. Override to implement logging, pooling,
	 * request throttling, or other behaviors.
	 * @return boolean true to show CustomToast collisions */
	protected boolean showCollisions() {
		return false;
	}

}