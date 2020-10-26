package com.chass.gsms.ui.newstudent;

import androidx.annotation.NonNull;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.helpers.SharedDataStore;
import com.chass.gsms.hilt.RetrofitRequestDefaultTimeout;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.Student;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.NewStudentFormViewModel;
import com.chass.gsms.viewmodels.UserFormViewModel;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewStudentViewModel extends ViewModel {
  private static final String TAG = "NewStudentViewModel";
  private final SavedStateHandle savedStateHandle;
  private final ApiClient apiClient;
  private final ViewStateViewModel viewState;
  private final SessionManager sessionManager;
  private final NewStudentFormViewModel formViewModel;
  private final SharedDataStore dataStore;
  private final ILogger logger;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  public NewStudentFormViewModel getFormViewModel() {
    return formViewModel;
  }

  public SharedDataStore getDataStore(){
    return dataStore;
  }

  @ViewModelInject
  public NewStudentViewModel(@Assisted SavedStateHandle savedStateHandle, SessionManager sessionManager, SharedDataStore dataStore, @RetrofitRequestDefaultTimeout ApiClient client, ViewStateViewModel viewState, NewStudentFormViewModel formViewModel, ILogger logger){
    this.savedStateHandle = savedStateHandle;
    this.sessionManager = sessionManager;
    this.dataStore = dataStore;
    this.viewState = viewState;
    this.apiClient = client;
    this.formViewModel = formViewModel;
    this.logger = logger;
  }

  /**
   * Make an api request to create new Student and add it to a class
   */
  public void addStudent(){
    if(viewState.getState() == ViewStates.BUSY) return;
    if(!formViewModel.isValid()){
      viewState.setState(ViewStates.INFO, "Please provide all required fields before submitting");
      return;
    }

    viewState.setState(ViewStates.BUSY, "Registration student. Please wait...");

    UserFormViewModel parent1Form = formViewModel.getParent1Form(),
        parent2Form = formViewModel.getParent2Form();

    Call<Student> call = parent2Form.isValid()?
      apiClient.addStudent(
        sessionManager.getSchool().getId(),
        dataStore.getSelectedClassName(),
        formViewModel.getFirstname(),
        formViewModel.getLastname(),
        parent1Form.getFirstname(),
        parent1Form.getLastname(),
        parent1Form.getEmail(),
        parent1Form.getPhoneNumber(),
        parent2Form.getFirstname(),
        parent2Form.getLastname(),
        parent2Form.getEmail(),
        parent2Form.getPhoneNumber()
      ):
      apiClient.addStudent(
        sessionManager.getSchool().getId(),
        dataStore.getSelectedClassName(),
        formViewModel.getFirstname(),
        formViewModel.getLastname(),
        parent1Form.getFirstname(),
        parent1Form.getLastname(),
        parent1Form.getEmail(),
        parent1Form.getPhoneNumber()
      );


    call.enqueue(new Callback<Student>() {
      @Override
      public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
        if(response.isSuccessful()){
          Student student = response.body();
          if(student != null){
            dataStore.getCurrentClass().addStudent(student);
            viewState.setState(ViewStates.SUCCESS, "Student was successfully added to class.");
            return;
          }
        }
        else {
          logger.print(TAG, response.errorBody());
        }
        viewState.setState(ViewStates.ERROR, "Application encountered an error while registering student. The server returned an unexpected response. Be assured that we are working to resolve the issue. If the problem persists, please contact us so we can resolve it.");
      }

      @Override
      public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
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
  }
}
