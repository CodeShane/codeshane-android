/** Log is part of a CodeShane™ solution.
 * Copyright © 2013 Shane Ian Robinson.
 * All Rights Reserved.
 * See LICENSE file for details. */

package com.codeshane.util;

import java.io.PrintStream;
import java.util.Properties;


/**
 * @author  Shane Ian Robinson <shane@codeshane.com>
 * @since   Jun 8, 2013
 * @version 2
 * @see java.io.PrintStream
 * @see java.io.PrintWriter
 * @see java.io.StringWriter
 * @see java.net.UnknownHostException
 * @see java.util.Properties
 * @see android.util.LogPrinter
 * @see android.util.Log
 */
public class Log {
	public static final String	TAG	= Log.class.getPackage().getName() + "." + Log.class.getSimpleName();

	/* Priorities */
    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    protected static int err_pri_level = 6;
    protected static boolean use_logcat = false;
    {
    	Properties s = System.getProperties();
    	use_logcat = s.getProperty("java.version", "1.5").equalsIgnoreCase("0") ||
    			s.getProperty("java.vm.name", "").equalsIgnoreCase("Dalvik");
    }

    public static int v(String tag, String msg) {
        return println(VERBOSE, tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        return println(VERBOSE, tag, msg + '\n' + android.util.Log.getStackTraceString(tr));
    }

    public static int d(String tag, String msg) {
        return println(DEBUG, tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        return println(DEBUG, tag, msg + '\n' + android.util.Log.getStackTraceString(tr));
    }

    public static int i(String tag, String msg) {
        return println(INFO, tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        return println(INFO, tag, msg + '\n' + android.util.Log.getStackTraceString(tr));
    }

    public static int w(String tag, String msg) {
        return println(WARN, tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        return println(WARN, tag, msg + '\n' + android.util.Log.getStackTraceString(tr));
    }

    public static int w(String tag, Throwable tr) {
        return println(WARN, tag, android.util.Log.getStackTraceString(tr));
    }

    public static int e(String tag, String msg) {
        return println(ERROR, tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return println(ERROR, tag, msg + '\n' + android.util.Log.getStackTraceString(tr));
    }

	private static int println (int priority, String tag, String message ) {
		if (use_logcat) {
			android.util.Log.println(priority, tag, message);
			return message.length();
		}

		message = mergeMessage(tag,message);

		if (priority >= err_pri_level) {
			System.err.println(message);
			return message.length();
		} else {
			System.out.println(tag+" "+message);
			return message.length();
		}
	}

	public static void setUseLogcat(boolean uselogcat){
		use_logcat = uselogcat;
	}

	public static String mergeMessage ( String tag, String message ) {
		return new StringBuffer(tag).append(' ').append(message).toString();
	}
//	public static String mergeMessage ( String tag, String message ) {
//	return new StringBuilder(tag).append(' ').append(message).toString();
//}

	public static int println ( PrintStream buffer, int priority, String tag, String message ) {
		buffer.println(message);
		return message.length();
	}

    private Log() {
    }
}
