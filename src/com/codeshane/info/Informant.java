/* Informant is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson. All Rights Reserved.
 * See LICENSE file or visit codeshane.com for more information. */

package com.codeshane.info;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.codeshane.util.Bitwise;
import com.codeshane.util.Text;

//TODO convert to json-output
/**
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Aug 12, 2013
 * @version 1
 */
public class Informant {
	public static final String	TAG	= Informant.class.getPackage().getName() + "." + Informant.class.getSimpleName();

	public static final String inform(final Context c, boolean buildConfigDebug, boolean appConfigDebug){

		StringBuilder output = new StringBuilder();

		ApplicationInfo ai = c.getApplicationInfo();
		PackageInfo pi = null;
		String versionName = "";
		try {
			pi = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
			versionName = pi.versionName;
		} catch (NameNotFoundException e) { versionName = ""; }

		int aiFlags = ai.flags;

		boolean flaggedDebuggable = Bitwise.isSet(ApplicationInfo.FLAG_DEBUGGABLE, aiFlags);
		boolean flaggedTestOnly = Bitwise.isSet(ApplicationInfo.FLAG_TEST_ONLY, aiFlags);
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

		output.append("Application A\n").append(c.getApplicationContext().getPackageName()).append(" className ").append(ai.className).append(Text.EOL_UNIX);
		output.append("Application B\n").append(ai.packageName).append(" versionName ").append(versionName).append(" versionCode ");
		if (null!=pi) output.append(pi.versionCode);
		output.append(Text.EOL_UNIX);
		output.append("Application Targets API ")
		.append("applicationInfo.targetSdkVersion ").append(ai.targetSdkVersion).append(Text.EOL_UNIX)
		;

		output.append(Text.EOL_UNIX)
		.append("Device\n")
		.append("Build.VERSION.SDK_INT ").append(Build.VERSION.SDK_INT)
		.append(Text.EOL_UNIX)
		;

		output.append(Text.EOL_UNIX)
		.append(" Make: ").append(android.os.Build.MANUFACTURER)
		.append("Model: ").append(android.os.Build.MODEL)
		.append("  CPU: ").append(android.os.Build.CPU_ABI)
		.append("Android v").append(android.os.Build.VERSION.RELEASE).append("(API-").append(android.os.Build.VERSION.SDK_INT).append(")")
		.append(Text.EOL_UNIX)
		.append("Screen Density ").append(dm.density)
		.append(", xDpi: ").append(dm.xdpi)
		.append(", yDpi: ").append(dm.ydpi)
		.append("Size: ").append(dm.widthPixels)
		.append("x ").append(dm.heightPixels)
		.append("y (").append(dm.densityDpi).append("dpi)")
		;

		output.append(Text.EOL_UNIX)
		.append("Eclipse: Project > gen >  BuildConfig.DEBUG == ").append(buildConfigDebug).append(Text.EOL_UNIX)
		.append("Project > AffirmApp.java    AffirmApp.DEBUG == ").append(appConfigDebug).append(Text.EOL_UNIX)
		.append("Manifest -> ApplicationInfo.FLAG_DEBUGGABLE == ").append(flaggedDebuggable).append(Text.EOL_UNIX)
		.append("Manifest ->  ApplicationInfo.FLAG_TEST_ONLY == ").append(flaggedTestOnly).append(Text.EOL_UNIX)
		.append("System.getenv(): \n").append(System.getenv().toString()).append(Text.EOL_UNIX)
		.append("System.getProperties(): \n").append(System.getProperties().toString()).append(Text.EOL_UNIX)
		;
		return output.toString();
	}
}
