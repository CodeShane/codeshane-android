/*
 * com.codeshane.AndLib - A CodeShane Solution.
 * Copyright (C) 2013 - Shane Robinson (http://codeshane.com/)
 * All rights reserved.
 *
 */
package com.codeshane.appwidget;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.RemoteViewsService;

/**
 * <p>SwidgetRemoteViewsService
 * [code]
 * public void main(String[] args) throws Exception {
 * [/code]
 * </p>
 * <p><b>Requires Android API 11</b></p>
 * @author <a href="mailto:shane@codeshane.com">Shane Robinson (@codeshane)</a>
 * @version 0.1, Feb 28, 2013
 */
@TargetApi ( Build.VERSION_CODES.HONEYCOMB )
public abstract class SwidgetRemoteViewsService extends RemoteViewsService {

}
