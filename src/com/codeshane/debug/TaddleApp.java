/** TaddleApp - A CodeShane™ solution.
 * Copyright © 2013 by Shane Ian Robinson.
 * All Rights Reserved.
 * See LICENSE file for details.
 * Contact: <a href="mailto:shane@codeshane.com">shane@codeshane.com</a>
 */
package com.codeshane.debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   May 20, 2013
 * @version 1
 */
@SuppressLint ( "NewApi" )
public class TaddleApp extends Application implements ActivityLifecycleCallbacks {
	public static final String TAG = TaddleApp.class.getSimpleName();

	@Override public void onCreate () {
		Log.v(TAG, "onCreate");
		super.onCreate();
	}

	@Override public void onTerminate () {
		Log.v(TAG, "onTerminate");
		super.onTerminate();
	}

	@Override public void onConfigurationChanged ( Configuration newConfig ) {
		Log.v(TAG, "onConfigurationChanged");
		super.onConfigurationChanged(newConfig);
	}

	@Override public void onLowMemory () {
		Log.v(TAG, "onLowMemory");
		super.onLowMemory();
	}

	@Override public void onTrimMemory ( int level ) {
		Log.v(TAG, "onTrimMemory");
		super.onTrimMemory(level);
	}

	@Override public void registerComponentCallbacks (
		ComponentCallbacks callback ) {
		Log.v(TAG, "registerComponentCallbacks");
		super.registerComponentCallbacks(callback);
	}

	@Override public void unregisterComponentCallbacks (
		ComponentCallbacks callback ) {
		Log.v(TAG, "unregisterComponentCallbacks");
		super.unregisterComponentCallbacks(callback);
	}

	@Override public void registerActivityLifecycleCallbacks (
		ActivityLifecycleCallbacks callback ) {
		Log.v(TAG, "registerActivityLifecycleCallbacks");
		super.registerActivityLifecycleCallbacks(callback);
	}

	@Override public void unregisterActivityLifecycleCallbacks (
		ActivityLifecycleCallbacks callback ) {
		Log.v(TAG, "unregisterActivityLifecycleCallbacks");
		super.unregisterActivityLifecycleCallbacks(callback);
	}

	@Override protected void attachBaseContext ( Context base ) {
		Log.v(TAG, "attachBaseContext");
		super.attachBaseContext(base);
	}

	@Override public Context getBaseContext () {
		Log.v(TAG, "getBaseContext");
		return super.getBaseContext();
	}

	@Override public AssetManager getAssets () {
		Log.v(TAG, "getAssets");
		return super.getAssets();
	}

	@Override public Resources getResources () {
		Log.v(TAG, "getResources");
		return super.getResources();
	}

	@Override public PackageManager getPackageManager () {
		Log.v(TAG, "getPackageManager");
		return super.getPackageManager();
	}

	@Override public ContentResolver getContentResolver () {
		Log.v(TAG, "getContentResolver");
		return super.getContentResolver();
	}

	@Override public Looper getMainLooper () {
		Log.v(TAG, "getMainLooper");
		return super.getMainLooper();
	}

	@Override public Context getApplicationContext () {
		Log.v(TAG, "getApplicationContext");
		return super.getApplicationContext();
	}

	@Override public void setTheme ( int resid ) {
		Log.v(TAG, "setTheme");
		super.setTheme(resid);
	}

	@Override public Theme getTheme () {
		Log.v(TAG, "getTheme");
		return super.getTheme();
	}

	@Override public ClassLoader getClassLoader () {
		Log.v(TAG, "getClassLoader");
		return super.getClassLoader();
	}

	@Override public String getPackageName () {
		Log.v(TAG, "getPackageName");
		return super.getPackageName();
	}

	@Override public ApplicationInfo getApplicationInfo () {
		Log.v(TAG, "getApplicationInfo");
		return super.getApplicationInfo();
	}

	@Override public String getPackageResourcePath () {
		Log.v(TAG, "getPackageResourcePath");
		return super.getPackageResourcePath();
	}

	@Override public String getPackageCodePath () {
		Log.v(TAG, "getPackageCodePath");
		return super.getPackageCodePath();
	}

	@Override public SharedPreferences getSharedPreferences ( String name,
		int mode ) {
		Log.v(TAG, "getSharedPreferences");
		return super.getSharedPreferences(name, mode);
	}

	@Override public FileInputStream openFileInput ( String name )
			throws FileNotFoundException {
		Log.v(TAG, "openFileInput");
		return super.openFileInput(name);
	}

	@Override public FileOutputStream openFileOutput ( String name, int mode )
			throws FileNotFoundException {
		Log.v(TAG, "openFileOutput");
		return super.openFileOutput(name, mode);
	}

	@Override public boolean deleteFile ( String name ) {
		Log.v(TAG, "deleteFile");
		return super.deleteFile(name);
	}

	@Override public File getFileStreamPath ( String name ) {
		Log.v(TAG, "getFileStreamPath");
		return super.getFileStreamPath(name);
	}

	@Override public String[] fileList () {
		Log.v(TAG, "fileList");
		return super.fileList();
	}

	@Override public File getFilesDir () {
		Log.v(TAG, "getFilesDir");
		return super.getFilesDir();
	}

	@Override public File getExternalFilesDir ( String type ) {
		Log.v(TAG, "getExternalFilesDir");
		return super.getExternalFilesDir(type);
	}

	@Override public File getObbDir () {
		Log.v(TAG, "getObbDir");
		return super.getObbDir();
	}

	@Override public File getCacheDir () {
		Log.v(TAG, "getCacheDir");
		return super.getCacheDir();
	}

	@Override public File getExternalCacheDir () {
		Log.v(TAG, "getExternalCacheDir");
		return super.getExternalCacheDir();
	}

	@Override public File getDir ( String name, int mode ) {
		Log.v(TAG, "getDir");
		return super.getDir(name, mode);
	}

	@Override public SQLiteDatabase openOrCreateDatabase ( String name,
		int mode, CursorFactory factory ) {
		Log.v(TAG, "openOrCreateDatabase");
		return super.openOrCreateDatabase(name, mode, factory);
	}

	@Override public SQLiteDatabase openOrCreateDatabase ( String name,
		int mode, CursorFactory factory, DatabaseErrorHandler errorHandler ) {
		Log.v(TAG, "openOrCreateDatabase");
		return super.openOrCreateDatabase(name, mode, factory, errorHandler);
	}

	@Override public boolean deleteDatabase ( String name ) {
		Log.v(TAG, "deleteDatabase");
		return super.deleteDatabase(name);
	}

	@Override public File getDatabasePath ( String name ) {
		Log.v(TAG, "getDatabasePath");
		return super.getDatabasePath(name);
	}

	@Override public String[] databaseList () {
		Log.v(TAG, "databaseList");
		return super.databaseList();
	}

	@Override public Drawable getWallpaper () {
		Log.v(TAG, "getWallpaper");
		return super.getWallpaper();
	}

	@Override public Drawable peekWallpaper () {
		Log.v(TAG, "peekWallpaper");
		return super.peekWallpaper();
	}

	@Override public int getWallpaperDesiredMinimumWidth () {
		Log.v(TAG, "getWallpaperDesiredMinimumWidth");
		return super.getWallpaperDesiredMinimumWidth();
	}

	@Override public int getWallpaperDesiredMinimumHeight () {
		Log.v(TAG, "getWallpaperDesiredMinimumHeight");
		return super.getWallpaperDesiredMinimumHeight();
	}

	@Override public void setWallpaper ( Bitmap bitmap ) throws IOException {
		Log.v(TAG, "setWallpaper");
		super.setWallpaper(bitmap);
	}

	@Override public void setWallpaper ( InputStream data ) throws IOException {
		Log.v(TAG, "setWallpaper");
		super.setWallpaper(data);
	}

	@Override public void clearWallpaper () throws IOException {
		Log.v(TAG, "clearWallpaper");
		super.clearWallpaper();
	}

	@Override public void startActivity ( Intent intent ) {
		Log.v(TAG, "startActivity");
		super.startActivity(intent);
	}

	@Override public void startActivity ( Intent intent, Bundle options ) {
		Log.v(TAG, "startActivity");
		super.startActivity(intent, options);
	}

	@Override public void startActivities ( Intent[] intents ) {
		Log.v(TAG, "startActivities");
		super.startActivities(intents);
	}

	@Override public void startActivities ( Intent[] intents, Bundle options ) {
		Log.v(TAG, "startActivities");
		super.startActivities(intents, options);
	}

	@Override public void startIntentSender ( IntentSender intent,
		Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags )
			throws SendIntentException {
		Log.v(TAG, "startIntentSender");
		super.startIntentSender(intent, fillInIntent, flagsMask, flagsValues,
				extraFlags);
	}

	@Override public void startIntentSender ( IntentSender intent,
		Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags,
		Bundle options ) throws SendIntentException {
		Log.v(TAG, "startIntentSender");
		super.startIntentSender(intent, fillInIntent, flagsMask, flagsValues,
				extraFlags, options);
	}

	@Override public void sendBroadcast ( Intent intent ) {
		Log.v(TAG, "sendBroadcast");
		super.sendBroadcast(intent);
	}

	@Override public void sendBroadcast ( Intent intent,
		String receiverPermission ) {
		Log.v(TAG, "sendBroadcast");
		super.sendBroadcast(intent, receiverPermission);
	}

	@Override public void sendOrderedBroadcast ( Intent intent,
		String receiverPermission ) {
		Log.v(TAG, "sendOrderedBroadcast");
		super.sendOrderedBroadcast(intent, receiverPermission);
	}

	@Override public void sendOrderedBroadcast ( Intent intent,
		String receiverPermission, BroadcastReceiver resultReceiver,
		Handler scheduler, int initialCode, String initialData,
		Bundle initialExtras ) {
		Log.v(TAG, "sendOrderedBroadcast");
		super.sendOrderedBroadcast(intent, receiverPermission, resultReceiver,
				scheduler, initialCode, initialData, initialExtras);
	}

//	@Override public void sendBroadcastAsUser ( Intent intent, UserHandle user ) {
//		Log.v(TAG, "sendBroadcastAsUser");
//		super.sendBroadcastAsUser(intent, user);
//	}
//
//	@Override public void sendBroadcastAsUser ( Intent intent, UserHandle user,
//		String receiverPermission ) {
//		Log.v(TAG, "sendBroadcastAsUser");
//		super.sendBroadcastAsUser(intent, user, receiverPermission);
//	}
//
//	@Override public void sendOrderedBroadcastAsUser ( Intent intent,
//		UserHandle user, String receiverPermission,
//		BroadcastReceiver resultReceiver, Handler scheduler, int initialCode,
//		String initialData, Bundle initialExtras ) {
//		Log.v(TAG, "sendOrderedBroadcastAsUser");
//		super.sendOrderedBroadcastAsUser(intent, user, receiverPermission,
//				resultReceiver, scheduler, initialCode, initialData,
//				initialExtras);
//	}

	@Override public void sendStickyBroadcast ( Intent intent ) {
		Log.v(TAG, "sendStickyBroadcast");
		super.sendStickyBroadcast(intent);
	}

	@Override public void sendStickyOrderedBroadcast ( Intent intent,
		BroadcastReceiver resultReceiver, Handler scheduler, int initialCode,
		String initialData, Bundle initialExtras ) {
		Log.v(TAG, "sendStickyOrderedBroadcast");
		super.sendStickyOrderedBroadcast(intent, resultReceiver, scheduler,
				initialCode, initialData, initialExtras);
	}

	@Override public void removeStickyBroadcast ( Intent intent ) {
		Log.v(TAG, "removeStickyBroadcast");
		super.removeStickyBroadcast(intent);
	}

//	@Override public void sendStickyBroadcastAsUser ( Intent intent,
//		UserHandle user ) {
//		Log.v(TAG, "sendStickyBroadcastAsUser");
//		super.sendStickyBroadcastAsUser(intent, user);
//	}
//
//	@Override public void sendStickyOrderedBroadcastAsUser ( Intent intent,
//		UserHandle user, BroadcastReceiver resultReceiver, Handler scheduler,
//		int initialCode, String initialData, Bundle initialExtras ) {
//		Log.v(TAG, "sendStickyOrderedBroadcastAsUser");
//		super.sendStickyOrderedBroadcastAsUser(intent, user, resultReceiver,
//				scheduler,
//				initialCode, initialData, initialExtras);
//	}
//
//	@Override public void removeStickyBroadcastAsUser ( Intent intent,
//		UserHandle user ) {
//		Log.v(TAG, "removeStickyBroadcastAsUser");
//		super.removeStickyBroadcastAsUser(intent, user);
//	}

	@Override public Intent registerReceiver ( BroadcastReceiver receiver,
		IntentFilter filter ) {
		Log.v(TAG, "registerReceiver");
		return super.registerReceiver(receiver, filter);
	}

	@Override public Intent registerReceiver ( BroadcastReceiver receiver,
		IntentFilter filter, String broadcastPermission, Handler scheduler ) {
		Log.v(TAG, "registerReceiver");
		return super.registerReceiver(receiver, filter, broadcastPermission,
				scheduler);
	}

	@Override public void unregisterReceiver ( BroadcastReceiver receiver ) {
		Log.v(TAG, "unregisterReceiver");
		super.unregisterReceiver(receiver);
	}

	@Override public ComponentName startService ( Intent service ) {
		Log.v(TAG, "startService");
		return super.startService(service);
	}

	@Override public boolean stopService ( Intent name ) {
		Log.v(TAG, "stopService");
		return super.stopService(name);
	}

	@Override public boolean bindService ( Intent service,
		ServiceConnection conn, int flags ) {
		Log.v(TAG, "bindService");
		return super.bindService(service, conn, flags);
	}

	@Override public void unbindService ( ServiceConnection conn ) {
		Log.v(TAG, "unbindService");
		super.unbindService(conn);
	}

	@Override public boolean startInstrumentation ( ComponentName className,
		String profileFile, Bundle arguments ) {
		Log.v(TAG, "startInstrumentation");
		return super.startInstrumentation(className, profileFile, arguments);
	}

	@Override public Object getSystemService ( String name ) {
		Log.v(TAG, "getSystemService");
		return super.getSystemService(name);
	}

	@Override public int checkPermission ( String permission, int pid, int uid ) {
		Log.v(TAG, "checkPermission");
		return super.checkPermission(permission, pid, uid);
	}

	@Override public int checkCallingPermission ( String permission ) {
		Log.v(TAG, "checkCallingPermission");
		return super.checkCallingPermission(permission);
	}

	@Override public int checkCallingOrSelfPermission ( String permission ) {
		Log.v(TAG, "checkCallingOrSelfPermission");
		return super.checkCallingOrSelfPermission(permission);
	}

	@Override public void enforcePermission ( String permission, int pid,
		int uid, String message ) {
		Log.v(TAG, "enforcePermission");
		super.enforcePermission(permission, pid, uid, message);
	}

	@Override public void enforceCallingPermission ( String permission,
		String message ) {
		Log.v(TAG, "enforceCallingPermission");
		super.enforceCallingPermission(permission, message);
	}

	@Override public void enforceCallingOrSelfPermission ( String permission,
		String message ) {
		Log.v(TAG, "enforceCallingOrSelfPermission");
		super.enforceCallingOrSelfPermission(permission, message);
	}

	@Override public void grantUriPermission ( String toPackage, Uri uri,
		int modeFlags ) {
		Log.v(TAG, "grantUriPermission");
		super.grantUriPermission(toPackage, uri, modeFlags);
	}

	@Override public void revokeUriPermission ( Uri uri, int modeFlags ) {
		Log.v(TAG, "revokeUriPermission");
		super.revokeUriPermission(uri, modeFlags);
	}

	@Override public int checkUriPermission ( Uri uri, int pid, int uid,
		int modeFlags ) {
		Log.v(TAG, "checkUriPermission");
		return super.checkUriPermission(uri, pid, uid, modeFlags);
	}

	@Override public int checkCallingUriPermission ( Uri uri, int modeFlags ) {
		Log.v(TAG, "checkCallingUriPermission");
		return super.checkCallingUriPermission(uri, modeFlags);
	}

	@Override public int checkCallingOrSelfUriPermission ( Uri uri,
		int modeFlags ) {
		Log.v(TAG, "checkCallingOrSelfUriPermission");
		return super.checkCallingOrSelfUriPermission(uri, modeFlags);
	}

	@Override public int checkUriPermission ( Uri uri, String readPermission,
		String writePermission, int pid, int uid, int modeFlags ) {
		Log.v(TAG, "checkUriPermission");
		return super.checkUriPermission(uri, readPermission, writePermission,
				pid, uid,
				modeFlags);
	}

	@Override public void enforceUriPermission ( Uri uri, int pid, int uid,
		int modeFlags, String message ) {
		Log.v(TAG, "enforceUriPermission");
		super.enforceUriPermission(uri, pid, uid, modeFlags, message);
	}

	@Override public void enforceCallingUriPermission ( Uri uri, int modeFlags,
		String message ) {
		Log.v(TAG, "enforceCallingUriPermission");
		super.enforceCallingUriPermission(uri, modeFlags, message);
	}

	@Override public void enforceCallingOrSelfUriPermission ( Uri uri,
		int modeFlags, String message ) {
		Log.v(TAG, "enforceCallingOrSelfUriPermission");
		super.enforceCallingOrSelfUriPermission(uri, modeFlags, message);
	}

	@Override public void enforceUriPermission ( Uri uri,
		String readPermission, String writePermission, int pid, int uid,
		int modeFlags, String message ) {
		Log.v(TAG, "enforceUriPermission");
		super.enforceUriPermission(uri, readPermission, writePermission, pid,
				uid,
				modeFlags, message);
	}

	@Override public Context createPackageContext ( String packageName,
		int flags ) throws NameNotFoundException {
		Log.v(TAG, "createPackageContext");
		return super.createPackageContext(packageName, flags);
	}

//	@Override public Context createConfigurationContext (
//		Configuration overrideConfiguration ) {
//		Log.v(TAG, "createConfigurationContext");
//		return super.createConfigurationContext(overrideConfiguration);
//	}
//
//	@Override public Context createDisplayContext ( Display display ) {
//		Log.v(TAG, "createDisplayContext");
//		return super.createDisplayContext(display);
//	}

	@Override public boolean isRestricted () {
		Log.v(TAG, "isRestricted");
		return super.isRestricted();
	}

	@Override protected Object clone () throws CloneNotSupportedException {
		Log.v(TAG, "clone");
		return super.clone();
	}

	@Override public boolean equals ( Object o ) {
		Log.v(TAG, "equals");
		return super.equals(o);
	}

	@Override protected void finalize () throws Throwable {
		Log.v(TAG, "finalize");
		super.finalize();
	}

	@Override public int hashCode () {
		Log.v(TAG, "hashCode");
		return super.hashCode();
	}

	@Override public String toString () {
		Log.v(TAG, "toString");
		return super.toString();
	}

	@Override public void onActivityCreated ( Activity activity, Bundle savedInstanceState ) {
		Log.v(TAG,"ActivityLifecycleCallbacks.onActivityCreated()");
	}

	@Override public void onActivityStarted ( Activity activity ) {
		Log.v(TAG,"ActivityLifecycleCallbacks.onActivityStarted()");
	}

	@Override public void onActivityResumed ( Activity activity ) {
		Log.v(TAG,"ActivityLifecycleCallbacks.onActivityResumed()");
	}

	@Override public void onActivityPaused ( Activity activity ) {
		Log.v(TAG,"ActivityLifecycleCallbacks.onActivityPaused()");
	}

	@Override public void onActivityStopped ( Activity activity ) {
		Log.v(TAG,"ActivityLifecycleCallbacks.onActivityStopped()");
	}

	@Override public void onActivitySaveInstanceState ( Activity activity, Bundle outState ) {
		Log.v(TAG,"ActivityLifecycleCallbacks.onActivitySaveInstanceState()");
	}

	@Override public void onActivityDestroyed ( Activity activity ) {
		Log.v(TAG,"ActivityLifecycleCallbacks.onActivityDestroyed()");
	}

}
