package com.chass.gsms.ui.login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.hilt.RetrofitRequestDefaultTimeout;
import com.chass.gsms.models.LoginResponse;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.LoginFormViewModel;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
  private final SavedStateHandle savedStateHandle;
  private final SessionManager sessionManager;
  private final ApiClient apiClient;
  private final ViewStateViewModel viewState;
  private final LoginFormViewModel formViewModel;

  private Call<LoginResponse> call;
  private Call<LoginResponse> c;

  public ViewStateViewModel getViewState(){
    return viewState;
  }

  public LoginFormViewModel getFormViewModel(){
    return formViewModel;
  }

  @ViewModelInject
  public LoginViewModel(@Assisted SavedStateHandle savedStateHandle, SessionManager sessionManager, @RetrofitRequestDefaultTimeout ApiClient client, ViewStateViewModel viewState, LoginFormViewModel formViewModel){
    this.savedStateHandle = savedStateHandle;
    this.sessionManager = sessionManager;
    this.apiClient = client;
    this.viewState = viewState;
    this.formViewModel = formViewModel;
  }

  public void login(){
    if(viewState.getState() == ViewStates.BUSY){
      return;
    }
    if(!formViewModel.isValid()){
      viewState.setState(ViewStates.INFO, "Please complete all fields before submitting form!");
      return;
    }
    viewState.setState(ViewStates.BUSY, "Authenticating with the server. Please wait...");
    call = apiClient.login(formViewModel.getSchoolId(), formViewModel.getEmail(), formViewModel.getPassword());
    c.enqueue(new Callback<LoginResponse>() {
      @Override
      public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
        LoginResponse loginResponse = response.body();
        if(loginResponse != null){
          sessionManager.login(loginResponse);
          viewState.restoreNormalState();
        }
        else {
          viewState.setState(ViewStates.ERROR, "An Unknown error occurred during Authentication. Please try again.");
        }
      }

      @Override
      public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
        viewState.setState(ViewStates.ERROR, "An error occurred during network request. Please try again.");
      }
    });
  }

  /**
   * Save the state of this ViewModel and cancel network requests
   */
  @Override
  protected void onCleared() {
    super.onCleared();
    if(call != null){
      call.cancel();
    }
  }
}
