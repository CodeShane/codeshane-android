/*
 * com.codeshane.AndLib - A CodeShane Solution.
 * Copyright (C) 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 *
 */
package com.codeshane.appwidget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.codeshane.andlib.R;

/**
 * <p>{@code SwidgetProvider} is a simple home-screen widget with state, such as a toggle button.</p>
 * <p>This {@link AppWidgetProvider} makes implementing a switch (or other widget with state)
 * trivial; just be sure to include in your {@code AndroidManifest.xml} the appropriate permissions
 * as well as {@link SwidgetProvider} and {@link SwidgetReceiver}.</p>
 * <p><i>Example snippets for your AndroidManifest.xml are included at:</i></p>
 * {@code ./res/xml/swidgetprovider_manifest.xml}<br/>
 * {@code ./res/xml/swidgetreceiver_manifest.xml}<br/>
 * <br/>
 * @since Jan 13, 2013
 * @version 1, Jan 13, 2013
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 2, Feb 23, 2013
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 3, 2013-05-27
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (shane@codeshane.com)</a>
 */
public class SwidgetProvider extends AppWidgetProvider {
	private static final String TAG = SwidgetProvider.class.getSimpleName();

	private static final String ACTION_SWIDGET_UPDATE = "ACTION_SWIDGET_UPDATE";
	private static final String ACTION_SWIDGET_CHANGE_STATE = "ACTION_SWIDGET_CHANGE_STATE";

	private static String SWIDGET_STATE_KEY = "SWIDGET_STATE_KEY";

	/** The R.layout.--- for the widget layout. */
	protected static int R_LAYOUT_SWIDGET = R.layout.swidget_layout;

	/** The R.id.--- for the widget ImageView that shows state. */
	protected static int R_ID_IMAGEVIEW = R.id.swidget_imageview;

	/** The R.drawable.--- representing this state. */
	protected static int R_DRAWABLE_BUTTON_STATE_ENABLED = R.drawable.eye_open;

	/** The R.drawable.--- representing this state. */
	protected static int R_DRAWABLE_BUTTON_STATE_DISABLED = R.drawable.eye_closed;

	@Deprecated // manual formats have poor i18n
	public static final String TIME_FORMAT_HHmm = new String("HH:mm");

	protected static boolean LOGGING = false;

	public static void setLogging(boolean enable){
		LOGGING = enable;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (LOGGING) { Log.v(TAG, "onReceive()"); }
		super.onReceive(context, intent);

	    final String action = intent.getAction();

	    if (ACTION_SWIDGET_UPDATE.equals(action)) {
	    	if (LOGGING) { Log.v(TAG,"Action = " + ACTION_SWIDGET_UPDATE); }
	    	// Received manual update request, usually because < 30min timers.
	    	AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	    	updateAllAppWidgets(context, appWidgetManager, getWidgetIds(appWidgetManager, getComponentName(context, SwidgetProvider.class)));
	    } else if (ACTION_SWIDGET_CHANGE_STATE.equals(action)){
	    	int appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
	    	if (LOGGING) { Log.v(TAG,"Action = " + ACTION_SWIDGET_CHANGE_STATE); }
	    	toggleState(context, intent, appWidgetId);
	    	RemoteViews remoteViews = getRemoteViews(context, R.layout.swidget_layout);
	    	setStateDrawable(remoteViews, appWidgetId, getState(context, appWidgetId));
	    	SwidgetProvider.updateAppWidget(appWidgetId, remoteViews, getAppWidgetManager(context));
	    } else {
	    	if (LOGGING) { Log.v(TAG,"Action..." + action); }
	    }
	}

	@Override
    public void onDeleted(Context context, int[] appWidgetIds) {
		// One or more AppWidget instances for this provider have been deleted.
		if (LOGGING) { Log.v(TAG, "onDeleted() " + appWidgetIds); }
		super.onDeleted(context, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
        	//TODO stop alarms?
        	//setAlarm(context, appWidgetId, -1);
        }
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		if (LOGGING) { Log.v(TAG, "onEnabled()"); }
	}

	@Override
	public void onDisabled(Context context) {
		// Last AppWidget instance for this provider has been deleted.
		if (LOGGING) { Log.v(TAG, "onDisabled()"); }

		Intent intent = new Intent(context, SwidgetReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);

		//TODO Stop any receiving IntentService
//		context.stopService(new Intent(context,TravelClockService.class));

		super.onDisabled(context);
	}

	@Override // AppWidgetProvider being asked to provide RemoteViews for a set of AppWidgets (30min+)
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		if (LOGGING) { Log.v(TAG, "onUpdate() " + appWidgetIds); }
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		updateAllAppWidgets(context, appWidgetManager, appWidgetIds);

		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		//note: appWidgetId is irrelevant since this action will affect all appwidgets
		setFrequentUpdatesAlarm(0, context, am);
	}

	protected static int[] getWidgetIds(AppWidgetManager appWidgetManager, ComponentName component){
		return appWidgetManager.getAppWidgetIds(component);
	}

	protected static ComponentName getComponentName(Context context, Class<?> cls){
		return new ComponentName(context, cls);
	}
	protected static AppWidgetManager getAppWidgetManager(Context context){
		return AppWidgetManager.getInstance(context);
	}
	protected static RemoteViews getRemoteViews(Context context, int widgetLayoutResId){
		return new RemoteViews(context.getPackageName(), widgetLayoutResId);
	}
	protected static void updateAppWidget(int appWidgetId, RemoteViews remoteViews, AppWidgetManager appWidgetManager) {
		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
	}

	protected static void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		//final int APPWIDGET_COUNT = appWidgetIds.length;
		if (null==appWidgetManager) appWidgetManager = AppWidgetManager.getInstance(context);

		for (int appWidgetId : appWidgetIds) { // int i=0; i<N; i++
			int state = getState(context, appWidgetId);
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R_LAYOUT_SWIDGET);

			setStateDrawable(remoteViews, appWidgetId, state);
			setTextString(remoteViews, appWidgetId, context);
			setOnClick(remoteViews, appWidgetId, context);

			// Hey, it works: <3 !!!!!
			remoteViews.setInt(R.id.swidget_root_id, "setBackgroundResource", R.drawable.appwidget_bg_alpha_80);
			remoteViews.setTextColor(R.id.swidget_chronometer, context.getResources().getColor(android.R.color.white));
			updateAppWidget(appWidgetId, remoteViews, appWidgetManager);

		}
	}

	static boolean isFrequentAlarmActive=false;

	private static void setFrequentUpdatesAlarm(int appWidgetId, Context context, AlarmManager am) {
		isFrequentAlarmActive = true;
		if (LOGGING) { Log.v(TAG, "setFrequentUpdatesAlarm() id=" + appWidgetId); }

		AppWidgetManager appWidgetManager = SwidgetProvider.getAppWidgetManager(context);

		if (isFrequentAlarmActive) return;

		SwidgetProvider.updateAllAppWidgets(context, appWidgetManager,
		  getWidgetIds(appWidgetManager,
		  SwidgetProvider.getComponentName(context, SwidgetProvider.class))
		);

		//More Frequent Alarms
		Calendar c = Calendar.getInstance();
		//TODO customizeable timezone

		// Number of seconds to offset back to sync clock
		int s = c.get(Calendar.SECOND);
		am.setRepeating(
		  AlarmManager.RTC, // doesn't use RTC_WAKEUP - permit sleep.
		  System.currentTimeMillis()-(1000*s),
		  60000L,
		  getBroadcastPendingIntent(context, SwidgetProvider.class,
				// Can't use the system broadcast: AppWidgetManager.ACTION_APPWIDGET_UPDATE (System-only intent)
		    ACTION_SWIDGET_UPDATE
		    , appWidgetId)
		);

	}

	private static void setOnClick(RemoteViews remoteViews, int appWidgetId, Context context) {
		//mine:    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		// I like mine because the click will pass on only one appWidgetId, the one clicked.
		//vogella: intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

		// OnClickPendingIntent
		remoteViews.setOnClickPendingIntent(R_ID_IMAGEVIEW,
		  SwidgetProvider.getBroadcastPendingIntent(context, SwidgetProvider.class,
		    SwidgetProvider.ACTION_SWIDGET_CHANGE_STATE //AppWidgetManager.ACTION_APPWIDGET_UPDATE
		    , appWidgetId)
		);
	}



	public static void toggleState(Context context, Intent intent, int appWidgetId) {
		int state = getState(context, appWidgetId);

		if (0==state){
			setState(context, appWidgetId, 1);
		} else {
			setState(context, appWidgetId, 0);
		}

		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

		// appWidgetId is irrelevant since this action will affect all appwidgets
//		int appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,0);
		setFrequentUpdatesAlarm(0, context, am);
    }
	private static String getStateKey(int appWidgetId){
		return SWIDGET_STATE_KEY + com.codeshane.util.Numbers.getFixedHexString(appWidgetId);
	}
	private static int getState(Context context, int appWidgetId){
		SharedPreferences prefs = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
		int state = prefs.getInt(getStateKey(appWidgetId), 0);
		if (LOGGING) { Log.d(TAG, "getState() for " + appWidgetId + " is " + state ); }
		if (LOGGING) { Log.d(TAG, "using key " + getStateKey(appWidgetId) ); }
		return state;
	}

	protected static void setState(Context context, int appWidgetId, Intent intent){
		setState(context, appWidgetId, intent.getExtras().getInt(getStateKey(appWidgetId)));
	}

	protected static void setState(Context context, int appWidgetId, int state){
		if (LOGGING) { Log.d(TAG, "setState() for " + appWidgetId + " to " + state ); }
		if (LOGGING) { Log.d(TAG, "using key " + getStateKey(appWidgetId) ); }
		SharedPreferences prefs = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        prefs.edit().putInt(getStateKey(appWidgetId), state).commit();
	}

	protected static void setStateDrawable(RemoteViews remoteViews, int appWidgetId, int state){
		if (LOGGING) { Log.d(TAG, "setStateDrawable() for " + appWidgetId + " to " + state ); }

		switch (state){
	        case 0:
	        default:
	        	remoteViews.setImageViewResource(R_ID_IMAGEVIEW, R_DRAWABLE_BUTTON_STATE_DISABLED);
	        	break;
	        case 1:
	        	remoteViews.setImageViewResource(R_ID_IMAGEVIEW, R_DRAWABLE_BUTTON_STATE_ENABLED);
	        	break;
		}
	}

	static SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_HHmm, Locale.getDefault());

	protected static void setTextString(RemoteViews remoteViews, int appWidgetId, Context context){
		if (LOGGING) { Log.d(TAG, "setTextString()"); }

		// Set the text with the current time.
		remoteViews.setChronometer(R.id.swidget_chronometer, 0L, "H:MM:SS", true);
		remoteViews.setTextViewText(R.id.swidget_textview, sdf.format(new Date()));
		remoteViews.setTextColor(R.id.swidget_textview, context.getResources().getColor(android.R.color.white));
	}

	protected static void setOnClickPendingIntent(RemoteViews remoteViews, int appWidgetId, PendingIntent pendingIntent){
		remoteViews.setOnClickPendingIntent(R.id.swidget_imageview, pendingIntent);
	}

//	public static void setAlarm(Context context, int appWidgetId, int updateRate) {
//        PendingIntent newPending = makeControlPendingIntent(context,TravelClockService.UPDATE,appWidgetId);
//        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        if (updateRate >= 0) {
//            alarms.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), updateRate, newPending);
//        } else {
//        	// on a negative updateRate stop the refreshing
//            alarms.cancel(newPending);
//        }
//    }

	public static Intent getLaunchIntent(Context context, Class<?> target, String action, int appWidgetId){
		if (LOGGING) { Log.v(TAG, "getLaunchServicePendingIntent() appWidgetId=" + appWidgetId); }

		Intent intent = new Intent(context, target);
        intent.setAction(action);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        // uri keeps pending intents unique. prevents overriding each other with updates by FLAG_UPDATE_CURRENT.
        Uri data = Uri.withAppendedPath(Uri.parse("travelclockwidget://widget/id/#"+action+appWidgetId), String.valueOf(appWidgetId));
        intent.setData(data);

        return intent;
	}

	public static PendingIntent getLaunchServicePendingIntent(Context context, Class<?> target, String action, int appWidgetId) {
		if (LOGGING) { Log.v(TAG, "getLaunchServicePendingIntent() appWidgetId=" + appWidgetId); }

        return(PendingIntent.getService(context, 0,
          getLaunchIntent(context, target, action, appWidgetId),
          PendingIntent.FLAG_UPDATE_CURRENT));

	}

	public static PendingIntent getLaunchActivityPendingIntent(Context caller, Class<?> target, String action, int appWidgetId) {
		if (LOGGING) { Log.v(TAG, "getLaunchActivityPendingIntent() appWidgetId=" + appWidgetId); }

        return(PendingIntent.getActivity(caller, 0,
        		getLaunchIntent(caller, target, action, appWidgetId),
        		PendingIntent.FLAG_UPDATE_CURRENT));
	}

	public static PendingIntent getBroadcastPendingIntent(Context caller, Class<?> target, String action, int appWidgetId) {
		if (LOGGING) { Log.v(TAG, "getBroadcastPendingIntent() action=" + action + " appWidgetId=" + appWidgetId); }

		return (PendingIntent.getBroadcast(caller, 0,
				getLaunchIntent(caller, target, action, appWidgetId),
				PendingIntent.FLAG_UPDATE_CURRENT));
	}

//	@Override
//	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
//		//Widget has changed its position and/or size.
//		Log.v(TAG, "onAppWidgetOptionsChanged()");
//	}

}

