package com.chass.gsms.ui.login;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.hilt.RetrofitRequestDefaultTimeout;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.ViewStateViewModel;

public class SchoolRegistrationViewModel extends ViewModel {
  private final SavedStateHandle savedStateHandle;
  private final ApiClient apiClient;
  private final ViewStateViewModel viewState;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  @ViewModelInject
  public SchoolRegistrationViewModel(@Assisted SavedStateHandle savedStateHandle, @RetrofitRequestDefaultTimeout ApiClient client, ViewStateViewModel viewState){
    this.savedStateHandle = savedStateHandle;
    this.apiClient = client;
    this.viewState = viewState;
  }

  /**
   * Save the state of this ViewModel
   */
  @Override
  protected void onCleared() {
    super.onCleared();
  }
}
