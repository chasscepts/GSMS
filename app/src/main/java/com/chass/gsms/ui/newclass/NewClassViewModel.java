package com.chass.gsms.ui.newclass;

import androidx.annotation.NonNull;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.ClassesCache;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.hilt.RetrofitRequestDefaultTimeout;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.Class;
import com.chass.gsms.models.LoginResponse;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.NewClassFormViewModel;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewClassViewModel extends ViewModel {
  private static final String TAG = "NewClassViewModel";
  private final SavedStateHandle savedStateHandle;
  private final SessionManager sessionManager;
  private final ApiClient apiClient;
  private final ViewStateViewModel viewState;
  private final NewClassFormViewModel formViewModel;
  private final ClassesCache cache;
  private final ILogger logger;

  private Call<Class> call;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  public NewClassFormViewModel getFormViewModel(){
    return formViewModel;
  }

  @ViewModelInject
  public NewClassViewModel(@Assisted SavedStateHandle savedStateHandle, SessionManager sessionManager, @RetrofitRequestDefaultTimeout ApiClient client, ViewStateViewModel viewState, NewClassFormViewModel form, ClassesCache cache, ILogger logger){
    this.savedStateHandle = savedStateHandle;
    this.sessionManager = sessionManager;
    this.viewState = viewState;
    this.apiClient = client;
    this.formViewModel = form;
    this.cache = cache;
    this.logger = logger;
  }


  /**
   * Makes a post request to the server to create a class.
   */
  public void addClass(){
    if(viewState.getState() == ViewStates.BUSY){
      return;
    }
    if(!formViewModel.isValid()){
      viewState.setState(ViewStates.INFO, "Please complete all fields before submitting form!");
      return;
    }
    viewState.setState(ViewStates.BUSY, "Setting up class on the server. Please wait...");
    call = apiClient.addClass(sessionManager.getSchool().getId(), formViewModel.getClassName(), formViewModel.getTeacherFirstname(), formViewModel.getTeacherLastname(), formViewModel.getTeacherEmail(), formViewModel.getTeacherPhoneNumber());
    call.enqueue(new Callback<Class>() {
      @Override
      public void onResponse(@NonNull Call<Class> call, @NonNull Response<Class> response) {
        if(response.isSuccessful()){
          Class aClass = response.body();
          if(aClass != null){
            sessionManager.getSchool().addClass(aClass);
            cache.save(aClass);
            viewState.setState(ViewStates.SUCCESS, "Class successfully added");
            return;
          }
        }
        else {
          logger.print(TAG, response.errorBody());
        }
        viewState.setState(ViewStates.ERROR, "Application encountered an error while registering school. The response we got from the server is not what we expected response. Be assured that we are working to resolve the issue. If the problem persists, please contact us so we can resolve it.");
      }

      @Override
      public void onFailure(@NonNull Call<Class> call, @NonNull Throwable t) {
        viewState.setState(ViewStates.ERROR, "An error occurred while trying to communicate with the server. The most observed cause of this error is unavailability of internet connection. Please ensure that you are connected to the internet then try again");
        logger.print(TAG, t);
      }
    });
  }

  /**
   * Save the state of this ViewModel
   */
  @Override
  protected void onCleared() {
    super.onCleared();
    if(call != null){
      call.cancel();
    }
  }
}
