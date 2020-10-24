package com.chass.gsms.helpers;

import android.util.Log;

import com.chass.gsms.interfaces.ILogger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;

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

  @Override
  public void print(String tag, Throwable t){
    StringWriter sw = new StringWriter();
    t.printStackTrace(new PrintWriter(sw));
    error(tag, t.getLocalizedMessage() + "\n" + sw.toString());
  }

  @Override
  public void print(String tag, ResponseBody error){
    if(error != null){
      try {
        error(tag, error.string());
      } catch (IOException ignored) {
        error(tag, "Logger was unable to convert the ResponseBody of this error to String");
      }
    }
  }
}
