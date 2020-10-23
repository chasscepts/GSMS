package com.chass.gsms.interfaces;

public interface ILogger {
  void log(String tag, String msg);
  void debug(String tag, String msg);
  void info(String tag, String msg);
  void error(String tag, String msg);
}
