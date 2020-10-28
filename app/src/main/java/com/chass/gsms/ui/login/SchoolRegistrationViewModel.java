package com.chass.gsms.ui.login;

import androidx.annotation.NonNull;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.helpers.UrlHelper;
import com.chass.gsms.hilt.RetrofitRequestExtendedTimeout;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.LoginResponse;
import com.chass.gsms.networks.clients.IFormData;
import com.chass.gsms.networks.clients.INetworkListener;
import com.chass.gsms.networks.clients.PostHttpClient;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.SchoolRegistrationFormViewModel;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import java.io.IOException;

import dagger.hilt.android.scopes.FragmentScoped;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FragmentScoped
public class SchoolRegistrationViewModel extends ViewModel {
  private static final String TAG = "SchoolRegistrationViewModel";
  private final SavedStateHandle savedStateHandle;
  private final SessionManager sessionManager;
  private final ApiClient apiClient;
  private final PostHttpClient client;
  private final ViewStateViewModel viewState;
  private final SchoolRegistrationFormViewModel formViewModel;
  private final UrlHelper urlHelper;
  private final ILogger logger;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  public SchoolRegistrationFormViewModel getFormViewModel(){
    return formViewModel;
  }

  @ViewModelInject
  public SchoolRegistrationViewModel(@Assisted SavedStateHandle savedStateHandle, SessionManager sessionManager, PostHttpClient client, @RetrofitRequestExtendedTimeout ApiClient apiClient, ViewStateViewModel viewState, SchoolRegistrationFormViewModel formViewModel, UrlHelper urlHelper, ILogger logger){
    this.savedStateHandle = savedStateHandle;
    this.sessionManager = sessionManager;
    this.client = client;
    this.apiClient = apiClient;
    this.viewState = viewState;
    this.formViewModel = formViewModel;
    this.urlHelper = urlHelper;
    this.logger = logger;
  }

  public void register1(){
    if(viewState.getState() == ViewStates.BUSY) return;
    if(!formViewModel.isValid()){
      viewState.setState(ViewStates.INFO, "Please provide all required fields before submitting");
      return;
    }

    viewState.setState(ViewStates.BUSY, "Registration in progress.\nPlease wait...");

    MultipartBody.Part schoolPicture = MultipartBody.Part.createFormData("schoolPicture", null, formViewModel.getSchoolPictureStream());

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
        if(response.isSuccessful()){
          LoginResponse loginResponse = response.body();
          if(loginResponse != null){
            sessionManager.login(loginResponse);
            viewState.setState(ViewStates.SUCCESS, "School Successfully Registered");
            return;
          }
        }
        else {
          ResponseBody error = response.errorBody();
          logger.print(TAG, error);
          if(error != null){
            try {
              viewState.error(error.string());
              return;
            } catch (IOException ignored) {}
          }
        }
        viewState.responseError("registering school");
      }

      @Override
      public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
        viewState.connectionError();
        logger.print("stub", t);
      }
    });
  }

  public void register(){
    if(viewState.getState() == ViewStates.BUSY) return;
    IFormData formData = formViewModel.getFormData();
    if(formData == null){
      viewState.error("Please provide all required fields before submitting");
      return;
    }
    viewState.setState(ViewStates.BUSY, "Registration in progress.\nPlease wait...");
    client.setData(formData);
    client.request(urlHelper.getRegisterUrl(), new INetworkListener() {
      @Override
      public void onResponse(int code, String response) {
        if(client.isSuccessful()){
          if(sessionManager.login(response)){
            viewState.restoreNormalState();
            return;
          }
        }
        logger.log(TAG, response);
        viewState.responseError("registering school");
      }

      @Override
      public void onFailure(Throwable t) {
        viewState.connectionError();
        logger.print("stub", t);
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
