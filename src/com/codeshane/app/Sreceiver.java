/*
 * com.codeshane.AndLib - A CodeShane Solution.
 * Copyright (C) 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 *
 */
package com.codeshane.app;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/** Sreceiver handles system events (broadcast Intents).
 * Remember to include any Permission in your AndroidManifest.xml,
 * as well as any Intent-Filters you'd like Sreceiver to listen for.
 *
 * <p><b>Example:</b></p>
 * <pre>
 * &lt;uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
 * ...
 * &lt;receiver android:name="com.example.MyReceiver"
 * android:enabled="true"
 * android:exported="false"
 * android:permission="android.permission.RECEIVE_BOOT_COMPLETED" >
 *   &lt;intent-filter>
 *     &lt;action android:name="android.intent.action.BOOT_COMPLETED" />
 *     ...
 *   &lt;/intent-filter>
 * &lt;/receiver>
 *
 * package com.example.MyReceiver;
 *
 * public class MyReceiver extends Sreceiver {
 *
 * }
 * </pre>
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (shane@codeshane.com)</a>
 * @since 2013-02-28
 * @version 2013-02-28
 *  */
public abstract class Sreceiver extends BroadcastReceiver {
	private static final String TAG = Sreceiver.class.getSimpleName();

	//TODO implement runtime IntentFilter changes.

    @Override
    public void onReceive(Context context, Intent intent) {
    	String action = ((null==intent)?"":intent.getAction());
    	handleIntent(context, intent, action);
    }

    /** Override this method to handle received Intents.
	 * @param context
	 * @param intent
	 * @param action
	 */
    protected abstract void handleIntent(Context context, Intent intent, String action);

	/** Changes the 'enabled' state of this component. When disabled it will not receive intents. */
    protected final void setEnabled(Context context, boolean isEnabled){
    	setEnabledState(context, this.getClass(), isEnabled);
    }

	/** Changes the 'enabled' state of a component. <i>Intended to become a public utility method once settled,
	 * though possibly in another package.</i> */
	private static final void setEnabledState(Context context, Class<? extends Sreceiver> cls, boolean enabled) {
    	ComponentName receiver = new ComponentName(context, cls);
    	PackageManager pm = context.getPackageManager();
    	pm.setComponentEnabledSetting(receiver,
		  (enabled)? PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
		  PackageManager.COMPONENT_ENABLED_STATE_DISABLED , 0 );
    }

}