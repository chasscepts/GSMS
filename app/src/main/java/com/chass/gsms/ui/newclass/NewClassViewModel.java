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
import com.chass.gsms.models.Class;
import com.chass.gsms.models.LoginResponse;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.NewClassFormViewModel;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewClassViewModel extends ViewModel {
  private final SavedStateHandle savedStateHandle;
  private final SessionManager sessionManager;
  private final ApiClient apiClient;
  private final ViewStateViewModel viewState;
  private final NewClassFormViewModel formViewModel;
  private final ClassesCache cache;

  private Call<Class> call;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  public NewClassFormViewModel getFormViewModel(){
    return formViewModel;
  }

  @ViewModelInject
  public NewClassViewModel(@Assisted SavedStateHandle savedStateHandle, SessionManager sessionManager, @RetrofitRequestDefaultTimeout ApiClient client, ViewStateViewModel viewState, NewClassFormViewModel form, ClassesCache cache){
    this.savedStateHandle = savedStateHandle;
    this.sessionManager = sessionManager;
    this.viewState = viewState;
    this.apiClient = client;
    this.formViewModel = form;
    this.cache = cache;
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
    viewState.setState(ViewStates.BUSY, "Retrieving class information from the server. Please wait...");
    call = apiClient.addClass(sessionManager.getSchool().getId(), formViewModel.getClassName(), formViewModel.getTeacherFirstname(), formViewModel.getTeacherLastname(), formViewModel.getTeacherEmail(), formViewModel.getTeacherPhoneNumber());
    call.enqueue(new Callback<Class>() {
      @Override
      public void onResponse(@NonNull Call<Class> call, @NonNull Response<Class> response) {
        Class aClass = response.body();
        if(aClass != null){
          sessionManager.getSchool().addClass(aClass);
          cache.save(aClass);
          viewState.restoreNormalState();
        }
        else {
          viewState.setState(ViewStates.ERROR, "An Unknown error occurred while creating class. Please try again.");
        }
      }

      @Override
      public void onFailure(@NonNull Call<Class> call, @NonNull Throwable t) {
        viewState.setState(ViewStates.ERROR, "An error occurred during network request. Please try again.");
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
