package com.chass.gsms.ui.login;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.helpers.UrlHelper;
import com.chass.gsms.hilt.RetrofitRequestExtendedTimeout;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.CIError;
import com.chass.gsms.models.LoginResponse;
import com.chass.gsms.models.School;
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

  private LoginResponse loginResponse;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  public SchoolRegistrationFormViewModel getFormViewModel(){
    return formViewModel;
  }

  public final ObservableField<School> school = new ObservableField<>();

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
    logger.stub("Created!");
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
          loginResponse = LoginResponse.parse(response);
          if(loginResponse != null){
            viewState.restoreNormalState();
            school.set(loginResponse.getSchool());
            return;
          }
        }
        CIError ciError = CIError.fromJson(response);
        if(canDisplay(ciError)){
          String error = ciError.getMessages().getError();
          if(error == null || error.length() <= 0){
            error = "The server failed to provide reasons for this error.";
          }
          viewState.error("The following error was encountered while attempting to register school.\n\n" + error);
          return;
        }

        logger.error(TAG, response);
        viewState.responseError("registering school");
      }

      @Override
      public void onFailure(Throwable t) {
        viewState.connectionError();
        logger.print("stub", t);
      }
    });
  }

  private boolean canDisplay(CIError error){
    logger.info(TAG, "Checking if CIError can be displayed");
    if(error == null) return false;
    logger.info(TAG, "CIError is NOT NULL");
    if(error.getMessages() == null) return false;
    logger.info(TAG, "CIError:messages is NOT NULL");
    int status = error.getStatus();
    logger.info(TAG, "Error status code is: " + status);
    return status == 400 || status == 401 || status == 500;
  }

  public void login(){
    sessionManager.login(loginResponse);
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
