package com.chass.gsms.hilt;

import com.chass.gsms.interfaces.IStudentSelectedListener;
import com.chass.gsms.ui.classlist.ClassSelectedListener;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.scopes.ActivityRetainedScoped;

@Module
@InstallIn(ActivityRetainedComponent.class)
public abstract class AppActivityRetainedBindings {
  @ActivityRetainedScoped
  @Binds
  public abstract IStudentSelectedListener getIStudentListenerImplementation(ClassSelectedListener listener);
}
