package com.chass.gsms.ui.login;

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
import com.chass.gsms.viewmodels.SchoolRegistrationFormViewModel;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import java.io.File;

import javax.annotation.Nonnull;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolRegistrationViewModel extends ViewModel {
  private final SavedStateHandle savedStateHandle;
  private final SessionManager sessionManager;
  private final ApiClient apiClient;
  private final ViewStateViewModel viewState;
  private final SchoolRegistrationFormViewModel formViewModel;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  public SchoolRegistrationFormViewModel getFormViewModel(){
    return formViewModel;
  }

  @ViewModelInject
  public SchoolRegistrationViewModel(@Assisted SavedStateHandle savedStateHandle, SessionManager sessionManager, @RetrofitRequestDefaultTimeout ApiClient client, ViewStateViewModel viewState, SchoolRegistrationFormViewModel formViewModel){
    this.savedStateHandle = savedStateHandle;
    this.sessionManager = sessionManager;
    this.apiClient = client;
    this.viewState = viewState;
    this.formViewModel = formViewModel;
  }

  public void register(){
    if(!formViewModel.isValid()){
      viewState.setState(ViewStates.INFO, "Please provide all required fields before submitting");
      return;
    }

    viewState.setState(ViewStates.BUSY, "Registration in progress. Please wait...");
    File file = formViewModel.getSchoolPicture();
    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

    MultipartBody.Part schoolPicture = MultipartBody.Part.createFormData("schoolPicture", file.getName(), requestFile);

    Call<LoginResponse> call = apiClient.register(
        getRequestBody(formViewModel.getSchoolName()),
        getRequestBody(formViewModel.getSchoolAddress()),
        getRequestBody(formViewModel.getSchoolEmail()),
        getRequestBody(formViewModel.getSchoolPhoneNumber()),
        schoolPicture,
        getRequestBody(formViewModel.getAdminFirstname()),
        getRequestBody(formViewModel.getAdminLastname()),
        getRequestBody(formViewModel.getAdminEmail()),
        getRequestBody(formViewModel.getAdminPhoneNumber()),
        getRequestBody(formViewModel.getPassword())
    );

    call.enqueue(new Callback<LoginResponse>() {
      @Override
      public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
        LoginResponse loginResponse = response.body();
        if(loginResponse != null){
          sessionManager.login(loginResponse);
          viewState.restoreNormalState();
        }
        else {
          viewState.setState(ViewStates.ERROR, "An Unknown error occurred during network request. Please try again.");
        }
      }

      @Override
      public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
        //Todo: handle according to response code
        viewState.setState(ViewStates.ERROR, "An error occurred during network request. Please try again.");
      }
    });
  }

  private RequestBody getRequestBody(String text) {
    if(text == null){
      text = "";
    }
    return RequestBody.create(MediaType.parse("multipart/form-data"), text);
  }

  /**
   * Save the state of this ViewModel
   */
  @Override
  protected void onCleared() {
    super.onCleared();
  }
}
