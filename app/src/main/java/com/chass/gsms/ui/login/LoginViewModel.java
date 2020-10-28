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
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.LoginResponse;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.LoginFormViewModel;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import dagger.hilt.android.scopes.FragmentScoped;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FragmentScoped
public class LoginViewModel extends ViewModel {
  private static final String TAG ="LoginViewModel" ;
  private final SavedStateHandle savedStateHandle;
  private final SessionManager sessionManager;
  private final ApiClient apiClient;
  private final ViewStateViewModel viewState;
  private final LoginFormViewModel formViewModel;
  private final ILogger logger;

  private Call<LoginResponse> call;

  public ViewStateViewModel getViewState(){
    return viewState;
  }

  public LoginFormViewModel getFormViewModel(){
    return formViewModel;
  }

  @ViewModelInject
  public LoginViewModel(@Assisted SavedStateHandle savedStateHandle, SessionManager sessionManager, @RetrofitRequestDefaultTimeout ApiClient client, ViewStateViewModel viewState, LoginFormViewModel formViewModel, ILogger logger){
    this.savedStateHandle = savedStateHandle;
    this.sessionManager = sessionManager;
    this.apiClient = client;
    this.viewState = viewState;
    this.formViewModel = formViewModel;
    this.logger = logger;
  }

  public void login(){
    if(viewState.getState() == ViewStates.BUSY){
      return;
    }
    if(!formViewModel.isValid()){
      viewState.error("Please complete all fields before submitting form!");
      return;
    }
    viewState.setState(ViewStates.BUSY, "Authenticating with the server. Please wait...");
    call = apiClient.login(formViewModel.getSchoolId(), formViewModel.getEmail(), formViewModel.getPassword());
    call.enqueue(new Callback<LoginResponse>() {
      @Override
      public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
        if(response.isSuccessful()){
          LoginResponse loginResponse = response.body();
          if(loginResponse != null){
            sessionManager.login(loginResponse);
            viewState.restoreNormalState();
            return;
          }
          else {
            viewState.responseError("trying to authenticate with the server");
            logger.print(TAG, response.errorBody());
          }
          return;
        }
        if(response.code() == 401){
          viewState.error("The server failed to authenticate you. This is most likely because at least one of the information you provided is incorrect. Please try again.");
          return;
        }
        viewState.error("A server error occurred during authentication. Please keep trying while we work to resolve the issue.");
        logger.print(TAG, response.errorBody());
      }

      @Override
      public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
        viewState.connectionError();
        logger.print(TAG, t);
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
