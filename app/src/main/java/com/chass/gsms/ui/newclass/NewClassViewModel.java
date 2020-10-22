package com.chass.gsms.ui.newclass;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.hilt.RetrofitRequestDefaultTimeout;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.NewClassFormViewModel;
import com.chass.gsms.viewmodels.ViewStateViewModel;

public class NewClassViewModel extends ViewModel {
  private final SavedStateHandle savedStateHandle;
  private final SessionManager sessionManager;
  private final ApiClient apiClient;
  private final ViewStateViewModel viewState;
  private final NewClassFormViewModel formViewModel;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  public NewClassFormViewModel getFormViewModel(){
    return formViewModel;
  }

  @ViewModelInject
  public NewClassViewModel(@Assisted SavedStateHandle savedStateHandle, SessionManager sessionManager, @RetrofitRequestDefaultTimeout ApiClient client, ViewStateViewModel viewState, NewClassFormViewModel form){
    this.savedStateHandle = savedStateHandle;
    this.sessionManager = sessionManager;
    this.viewState = viewState;
    this.apiClient = client;
    this.formViewModel = form;
  }

  /**
   * Makes a post request to the server to create a class.
   */
  public void create(){

  }

  /**
   * Save the state of this ViewModel
   */
  @Override
  protected void onCleared() {
    super.onCleared();
  }
}
