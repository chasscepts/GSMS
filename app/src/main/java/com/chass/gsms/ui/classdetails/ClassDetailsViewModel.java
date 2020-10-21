package com.chass.gsms.ui.classdetails;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.hilt.RetrofitRequestDefaultTimeout;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.ViewStateViewModel;

public class ClassDetailsViewModel extends ViewModel {
  private SavedStateHandle savedStateHandle;
  private String className;

  public String getClassName() {
    return className;
  }

  private final ApiClient apiClient;
  private final ViewStateViewModel viewState;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  @ViewModelInject
  public ClassDetailsViewModel(@Assisted SavedStateHandle savedStateHandle, @RetrofitRequestDefaultTimeout ApiClient client, ViewStateViewModel viewState){
    this.savedStateHandle = savedStateHandle;
    this.viewState = viewState;
    this.apiClient = client;
  }
  /**
   * When a class_item_view is clicked, we set the name of the class so it will be loaded and available when we
   * navigate to ClassDetailsFragment
   * @param className the name of the class we are navigating to
   */
  public void setClassName(String className) {
    this.className = className;
  }

  /**
   * Save the state of this ViewModel
   */
  @Override
  protected void onCleared() {
    super.onCleared();
  }
}
