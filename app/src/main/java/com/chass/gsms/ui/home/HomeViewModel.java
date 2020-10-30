package com.chass.gsms.ui.home;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.R;
import com.chass.gsms.helpers.SessionManager;

public class HomeViewModel extends ViewModel {
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  private SessionManager sessionManager;

  public int getPlaceholderId(){
    return R.drawable.building;
  }

  @ViewModelInject
  public HomeViewModel(SessionManager sessionManager){
    this.sessionManager = sessionManager;
  }
}
