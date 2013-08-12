/* Services is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson. All Rights Reserved.
 * See LICENSE file or visit codeshane.com for more information. */

package com.codeshane.info;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Feb 12, 2013
 * @version 1
 */
public final class Services {

	public static final String	TAG	= Services.class.getPackage().getName() + "." + Services.class.getSimpleName();

	// FROYO
	public static final String UI_MODE_SERVICE = "uimode";
	// GINGERBREAD
	public static final String DOWNLOAD_SERVICE = "download";

	/** Load a service by it's title. The returned Object will be cast by callers. **/
	protected static final Object load(Context context, String key) {
		return context.getSystemService(key);
	}

	/** Lazy-loads a {@link android.telephony.TelephonyManager} for handling management the telephony features of the device.*/
	public static final TelephonyManager telephony(Context context) { return ((TelephonyManager)load(context, Context.TELEPHONY_SERVICE)); }

	/** Lazy-loads the top-level {@link android.view.WindowManager} in which you can place custom windows. */
	public static final WindowManager window(Context context) { return ((WindowManager)load(context, Context.WINDOW_SERVICE)); }

	/** Lazy-loads a {@link android.view.LayoutInflater} for inflating layout resources in this context. */
	public static final LayoutInflater layoutInflater(Context context) { return ((LayoutInflater)load(context, Context.LAYOUT_INFLATER_SERVICE)); }

	/** Lazy-loads a {@link android.app.ActivityManager} for interacting with the global activity state of the system.*/
	public static final ActivityManager activity(Context context) { return ((ActivityManager)load(context, Context.ACTIVITY_SERVICE)); }

	/** Lazy-loads a {@link android.os.PowerManager} for controlling power management.*/
	public static final PowerManager power(Context context) { return ((PowerManager)load(context, Context.POWER_SERVICE)); }

	/** Lazy-loads a {@link android.app.AlarmManager} for receiving intents at the time of your choosing.*/
	public static final AlarmManager alarm(Context context) { return ((AlarmManager)load(context, Context.ALARM_SERVICE)); }

	/** Lazy-loads a {@link android.app.NotificationManager} for informing the user of background events. */
    public static final NotificationManager notification(Context context) { return ((NotificationManager)load(context, Context.NOTIFICATION_SERVICE)); }

	/** Lazy-loads a {@link android.app.KeyguardManager} for controlling keyguard.*/
	public static final KeyguardManager keyguard(Context context) { return ((KeyguardManager)load(context, Context.KEYGUARD_SERVICE)); }

	/** Lazy-loads a {@link android.location.LocationManager} for controlling location (e.g., GPS) updates.*/
	public static final LocationManager location(Context context) { return ((LocationManager)load(context, Context.LOCATION_SERVICE)); }

	/** Lazy-loads a {@link android.app.SearchManager} for handling search.*/
	public static final SearchManager search(Context context) { return ((SearchManager)load(context, Context.SEARCH_SERVICE)); }

	/** Lazy-loads a {@link android.os.Vibrator} for interacting with the vibrator hardware.*/
	public static final Vibrator vibrator(Context context) { return ((Vibrator)load(context, Context.VIBRATOR_SERVICE)); }

	/** Lazy-loads a {@link android.net.ConnectivityManager ConnectivityManager} for handling management of network connections.*/
	public static final ConnectivityManager connectivity(Context context) { return ((ConnectivityManager)load(context, Context.CONNECTIVITY_SERVICE)); }

	/** Lazy-loads a {@link android.net.wifi.WifiManager WifiManager} for management of Wi-Fi connectivity.*/
	public static final WifiManager wifi(Context context) { return ((WifiManager)load(context, Context.WIFI_SERVICE)); }

	/** Lazy-loads an {@link android.view.inputmethod.InputMethodManager InputMethodManager} for management of input methods.*/
	public static final InputMethodManager inputMethod(Context context) { return ((InputMethodManager)load(context, Context.INPUT_METHOD_SERVICE)); }

	/** API8 Lazy-loads an {@link android.app.UiModeManager} for controlling UI modes.
	 * Returns null prior to API8. */
	@TargetApi(Build.VERSION_CODES.FROYO)
	public static final UiModeManager uiMode(Context context) { return ((UiModeManager)load(context, UI_MODE_SERVICE)); }

	/** API9 Lazy-loads a {@link android.app.DownloadManager} for requesting HTTP downloads.
	 * Returns null prior to API9. */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static final DownloadManager download(Context context) { return ((DownloadManager)load(context, DOWNLOAD_SERVICE)); }

}
