package com.chass.gsms.helpers;

import android.util.Log;

import com.chass.gsms.interfaces.ILogger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * For Application Logs
 */
@Singleton
public class AppLogger implements ILogger {
  @Inject
  public AppLogger(){}


  @Override
  public void log(String tag, String msg) {
    Log.i(tag, msg);
  }

  @Override
  public void debug(String tag, String msg) {
    log(tag, msg);
  }

  @Override
  public void info(String tag, String msg) {
    log(tag, msg);
  }

  @Override
  public void error(String tag, String msg) {
    log(tag, msg);
  }
}
