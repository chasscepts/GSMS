package com.chass.gsms.hilt;

import com.chass.gsms.helpers.AppLogger;
import com.chass.gsms.interfaces.ILogger;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class LoggingModule {
  @Singleton
  @Binds
  public abstract ILogger bindLogger(AppLogger logger);
}
