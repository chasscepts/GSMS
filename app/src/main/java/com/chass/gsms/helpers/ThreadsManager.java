package com.chass.gsms.helpers;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ThreadsManager {
  private static final int THREAD_COUNT = 4;
  private final ExecutorService executor;
  private final Handler handler;

  @Inject
  public ThreadsManager(){
    executor = Executors.newFixedThreadPool(THREAD_COUNT);
    handler = new Handler(Looper.getMainLooper());
  }

  /**
   * This will run in the background
   * @param runnable
   */
  public void execute(Runnable runnable){
    executor.execute(runnable);
  }

  /**
   * This will run on the main thread
   * @param runnable
   */
  public void post(Runnable runnable){
    handler.post(runnable);
  }
}
