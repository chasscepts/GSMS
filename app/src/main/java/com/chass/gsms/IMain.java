package com.chass.gsms;

/**
 * This interface is implemented by MainActivity.
 * We pass context from xml to methods of ViewModels used in binding.
 * By casting the context to IMain we will have access to methods implemented in MainActivity.
 */
public interface IMain {
  void selectClass(String className);
}
