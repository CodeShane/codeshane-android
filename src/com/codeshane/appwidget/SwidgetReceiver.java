/*
 * SwidgetReceiver - A CodeShane Solution.
 * Copyright (C) 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 *
 */
package com.codeshane.appwidget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * <p>SwidgetReceiver
 * [code]
 * public void main(String[] args) throws Exception {
 * [/code]
 * </p>
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 0.1, Feb 24, 2013
 * @version 0.2, Feb 28, 2013
 */
public class SwidgetReceiver extends BroadcastReceiver {

	/** The R.id.--- for the widget TextView that receives text. */
	protected static int R_ID_SWIDGET_LAYOUT_TEXTVIEW = 0;
	protected static final int R_ID_SWIDGET_LAYOUT = SwidgetProvider.R_LAYOUT_SWIDGET;

	/**
	 * Calls:
	 * getRemoteViews() and passes the remote views to prepare()
	 * **/
	@Override
	public void onReceive(Context context, Intent intent) {
		RemoteViews remoteViews = getRemoteViews(context, getLayoutId());
		remoteViews = prepare(context, intent, remoteViews);
		updateAppWidget(context, this.getClass(), remoteViews);
	}

	/** Override to implement a custom layout.
	 * @return layout resource id
	 * */
	public static int getLayoutId(){return R_ID_SWIDGET_LAYOUT;}

	/** Override this method to decorate your views.
	 * <p>For example:</p>
	 * <p>{@code remoteViews.setTextViewText(R_ID_SWIDGET_LAYOUT_TEXTVIEW,}</p>
	 * <p>{@code "Updated:\n"+Time.getTimeFormatted(new Date(), Time.TIME_FORMAT_mmss));}</p>
	 * <p>{@code return remoteViews;</p>
	 * @return prepared RemoteViews
	 * */
	public static RemoteViews prepare(Context context, Intent intent, RemoteViews remoteViews){
		return remoteViews;
	}

	/** Loads the remoteViews from the layout resource.
	 * */
	private static RemoteViews getRemoteViews(Context context, int layoutResourceId){
		return new RemoteViews(context.getPackageName(), layoutResourceId);
	}

	/** Update the remoteViews of the given widget class.
	 * */
	private static void updateAppWidget(Context context, Class<?> cls, RemoteViews remoteViews){
		ComponentName thiswidget = new ComponentName(context, cls);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(thiswidget, remoteViews);
	}
}