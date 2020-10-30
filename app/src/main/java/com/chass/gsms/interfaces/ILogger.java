package com.chass.gsms.interfaces;

import okhttp3.ResponseBody;

public interface ILogger {
  void log(String tag, String msg);
  void debug(String tag, String msg);
  void info(String tag, String msg);
  void error(String tag, String msg);
  void print(String tag, Throwable t);
  void print(String tag, ResponseBody responseBody);
  void stub(String msg);
}
