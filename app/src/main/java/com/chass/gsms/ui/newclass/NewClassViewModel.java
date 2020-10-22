package com.chass.gsms.ui.newclass;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.hilt.RetrofitRequestDefaultTimeout;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.ViewStateViewModel;

public class NewClassViewModel extends ViewModel {
  private final SavedStateHandle savedStateHandle;
  private final ApiClient apiClient;
  private final ViewStateViewModel viewState;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  @ViewModelInject
  public NewClassViewModel(@Assisted SavedStateHandle savedStateHandle, @RetrofitRequestDefaultTimeout ApiClient client, ViewStateViewModel viewState){
    this.savedStateHandle = savedStateHandle;
    this.viewState = viewState;
    this.apiClient = client;
  }

  /**
   * Save the state of this ViewModel
   */
  @Override
  protected void onCleared() {
    super.onCleared();
  }
}
