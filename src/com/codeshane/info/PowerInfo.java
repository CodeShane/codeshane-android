/** PowerInfo is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson.
 * All Rights Reserved.
 * See LICENSE file for details. */

package com.codeshane.info;

import com.codeshane.util.Bitwise;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;

/** A utility class to simplify getting battery and charging state information.
 * <p>Although most methods are overloaded to accept Context, it is very inefficient
 * to do so if you are getting more than one piece of information. The most efficient
 * way is to call getStatus() and reuse the returned charge state Intent to retrieve
 * all required variables.</p>
 *
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   May 28, 2013
 * @version 1
 */
@TargetApi(Build.VERSION_CODES.ECLAIR)
public final class PowerInfo {
	public static final String	TAG	= PowerInfo.class.getPackage().getName() + "." + PowerInfo.class.getSimpleName();

	// Values from BatteryManager API that were added with
	// Eclair and Honeycomb APIs.
	// @see BatteryManager

	/**
     * Extra for {@link android.content.Intent#ACTION_BATTERY_CHANGED}:
     * integer containing the current status constant.
     */
    public static final String EXTRA_STATUS = "status";

    /**
     * Extra for {@link android.content.Intent#ACTION_BATTERY_CHANGED}:
     * integer containing the current health constant.
     */
    public static final String EXTRA_HEALTH = "health";

    /**
     * Extra for {@link android.content.Intent#ACTION_BATTERY_CHANGED}:
     * boolean indicating whether a battery is present.
     */
    public static final String EXTRA_PRESENT = "present";

    /**
     * Extra for {@link android.content.Intent#ACTION_BATTERY_CHANGED}:
     * integer field containing the current battery level, from 0 to
     * {@link #EXTRA_SCALE}.
     */
    public static final String EXTRA_LEVEL = "level";

    /**
     * Extra for {@link android.content.Intent#ACTION_BATTERY_CHANGED}:
     * integer containing the maximum battery level.
     */
    public static final String EXTRA_SCALE = "scale";

    /**
     * Extra for {@link android.content.Intent#ACTION_BATTERY_CHANGED}:
     * integer containing the resource ID of a small status bar icon
     * indicating the current battery state.
     */
    public static final String EXTRA_ICON_SMALL = "icon-small";


    /**
     * Extra for {@link android.content.Intent#ACTION_BATTERY_CHANGED}:
     * integer containing the current battery voltage level.
     */
    public static final String EXTRA_VOLTAGE = "voltage";

    /**
     * Extra for {@link android.content.Intent#ACTION_BATTERY_CHANGED}:
     * integer containing the current battery temperature.
     */
    public static final String EXTRA_TEMPERATURE = "temperature";

    /**
     * Extra for {@link android.content.Intent#ACTION_BATTERY_CHANGED}:
     * String describing the technology of the current battery.
     */
    public static final String EXTRA_TECHNOLOGY = "technology";

    public static final int BATTERY_STATUS_UNKNOWN = 1;
    public static final int BATTERY_STATUS_CHARGING = 2;
    public static final int BATTERY_STATUS_DISCHARGING = 3;
    public static final int BATTERY_STATUS_NOT_CHARGING = 4;
    public static final int BATTERY_STATUS_FULL = 5;

    public static final int BATTERY_HEALTH_UNKNOWN = 1;
    public static final int BATTERY_HEALTH_GOOD = 2;
    public static final int BATTERY_HEALTH_OVERHEAT = 3;
    public static final int BATTERY_HEALTH_DEAD = 4;
    public static final int BATTERY_HEALTH_OVER_VOLTAGE = 5;
    public static final int BATTERY_HEALTH_UNSPECIFIED_FAILURE = 6;
    public static final int BATTERY_HEALTH_COLD = 7;

    protected static final int getLevel(Intent state){
		return state.getIntExtra(EXTRA_LEVEL, 0);
    }
    protected static final int getLevelScale(Intent state) {
		return state.getIntExtra(EXTRA_SCALE, 100);
    }

    /**
     * Extra for {@link android.content.Intent#ACTION_BATTERY_CHANGED}:
     * Int value set to nonzero if an unsupported charger is attached
     * to the device.
     * {@hide}
     */
    public static final String EXTRA_INVALID_CHARGER = "invalid_charger";

    public static final boolean isChargerSupported(Intent state){
		return 0 == state.getIntExtra(EXTRA_INVALID_CHARGER, 0x1);
	}

    Intent state = null;

    public PowerInfo(Context context){
    	super();
    	update(context.getApplicationContext());
    }

    // Need a context for updates; although null state is handled by returning
    // values for "unknown", allowing empty constructor would give users a
    // sense it should be working, although it couldn't. Easier to just
    // prevent.
    @SuppressWarnings ( "unused" )
	private PowerInfo(){}

	public final void update(Context context) {
		// by calling registerReceiver with null, we can retrieve the current sticky intent without registering a receiver.
		this.state = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	/** API below 5 will receive the default, BatteryManager.BATTERY_HEALTH_UNKNOWN == 0x1 */
	public final int getHealth(){
		return getHealth(state);
	}
	/** API below 5 will receive the default, BatteryManager.BATTERY_HEALTH_UNKNOWN == 0x1 */
	public static final int getHealth(Intent state){
		if (null==state) return -1;
		return state.getIntExtra("health", 0x1); // BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN == 0x1
	}
	/** API below 5 will receive the default, BatteryManager.BATTERY_STATUS_UNKNOWN == 0x1 */
	public final int getStatus(){
		return getStatus(state);
	}
	/** API below 5 will receive the default, BatteryManager.BATTERY_STATUS_UNKNOWN == 0x1 */
	public static final int getStatus(Intent state){
		if (null==state) return 0x1;
		return state.getIntExtra(EXTRA_STATUS, BATTERY_STATUS_UNKNOWN);
	}

	/** Returns true if battery is charging or full */
	public final boolean isCharging(){
		return isCharging(state);
	}
	/** Returns true if battery is charging or full */
	public static final boolean isCharging(Intent state){
		return isCharging(getStatus(state));
	}
	/** Returns true if battery is charging or full */
	public static final boolean isCharging(int status) { //charging/charged
		return BATTERY_STATUS_CHARGING == status || BATTERY_STATUS_FULL == status;
	}

	/** Returns true if battery health is good or unknown (as API4- won't report.) */
	public final boolean isHealthy(){ return isHealthy(getHealth(state)); }
	/** Returns true if battery health is good or unknown (as API4- won't report.) */
	public static final boolean isHealthy(Intent state){ return isHealthy(getHealth(state)); }
	/** Returns true if battery health is good or unknown (as API4- won't report.) */
	public static final boolean isHealthy(int health) { return ( BATTERY_HEALTH_GOOD == health || BATTERY_HEALTH_UNKNOWN == health); }

    /**
     * Extra for {@link android.content.Intent#ACTION_BATTERY_CHANGED}:
     * integer indicating whether the device is plugged in to a power
     * source; 0 means it is on battery, other constants are different
     * types of power sources.
     */
    public static final String EXTRA_PLUGGED = "plugged";

	/** Required for legacy API levels */
	public static final int PLUGGED_UNKNOWN = 0x40000000;
	public static final int PLUGGED_UNPLUGGED = 0;
    public static final int PLUGGED_AC = 1;
    public static final int PLUGGED_USB = 2;
    public static final int PLUGGED_WIRELESS = 4;
    private static final int PLUGGED_ANY = PLUGGED_AC | PLUGGED_USB | PLUGGED_WIRELESS;

    /** API < 5 will return default of -1. */
	public static final int getPlugged(Intent state) {
		if (null==state) return PLUGGED_UNKNOWN;
		return state.getIntExtra(EXTRA_PLUGGED, PLUGGED_UNKNOWN);
	}

	public final boolean isPlugged() { return isPlugged(getPlugged(state)); }
	public static final boolean isPlugged(Intent state) { return isPlugged(getPlugged(state)); }
	public static final boolean isPlugged(int plugged) {
		return Bitwise.isAnySet(PLUGGED_ANY, plugged);
	}

	public final boolean isPluggedUsb() { return isPluggedUsb(getPlugged(state)); }
	public static final boolean isPluggedUsb(Intent state) { return isPluggedUsb(getPlugged(state)); }
	public static final boolean isPluggedUsb(int plugged) { return plugged == BatteryManager.BATTERY_PLUGGED_USB;  } // BatteryManager.BATTERY_PLUGGED_USB == 0x2

	public final boolean isPluggedAc() { return isPluggedAc(getPlugged(state)); }
	public static final boolean isPluggedAc(Intent state) { return isPluggedAc(getPlugged(state)); }
	public static final boolean isPluggedAc(int plugged) { return plugged == BatteryManager.BATTERY_PLUGGED_AC; }

	public final boolean isPluggedWireless() { return isPluggedWireless(getPlugged(state)); }
	public static final boolean isPluggedWireless(Intent state) { return isPluggedWireless(getPlugged(state)); }
	public static final boolean isPluggedWireless(int plugged) { return plugged == PLUGGED_WIRELESS; }

}