package com.chass.gsms.ui.attendance;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.helpers.SharedDataStore;
import com.chass.gsms.hilt.RetrofitRequestDefaultTimeout;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.Class;
import com.chass.gsms.models.PlainResponse;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.AttendanceStatusViewModel;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceViewModel extends ViewModel {
  private static final String TAG = "AttendanceViewModel";
  private final SavedStateHandle savedStateHandle;
  private final SessionManager sessionManager;
  private final SharedDataStore dataStore;
  private final ViewStateViewModel viewState;
  private final ApiClient apiClient;
  private final ILogger logger;
  private final ClassAttendanceAdapter adapter;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  public ClassAttendanceAdapter getAdapter(){
    return adapter;
  }

  public SharedDataStore getDataStore(){
    return dataStore;
  }

  public final ObservableField<String> date = new ObservableField<>();

  @ViewModelInject
  public AttendanceViewModel(@Assisted SavedStateHandle savedStateHandle, SessionManager sessionManager, SharedDataStore dataStore, ViewStateViewModel viewState, @RetrofitRequestDefaultTimeout ApiClient apiClient, ILogger logger, ClassAttendanceAdapter adapter){
    this.savedStateHandle = savedStateHandle;
    this.sessionManager = sessionManager;
    this.viewState = viewState;
    this.dataStore = dataStore;
    this.apiClient = apiClient;
    this.logger = logger;
    this.adapter = adapter;
    setTodayDate();
    setup();
  }

  private void setTodayDate(){
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    date.set(dateFormat.format(Calendar.getInstance().getTime()));
  }

  private void setup(){
    Class aClass = dataStore.getCurrentClass();
    if(aClass == null){
      viewState.setState(ViewStates.ERROR, "No Class was selected!");
      return;
    }
    adapter.loadStudents(aClass.getStudents());
  }

  public void submitAttendance(){
    Class aClass = dataStore.getCurrentClass();
    if(aClass == null){
      viewState.error( "No Class was selected!");
      return;
    }
    viewState.busy("Submitting Attendance. Please wait...");
    AttendanceStatusViewModel[] statuses = adapter.getAttendanceStatuses();
    String json = AttendanceStatusViewModel.getJsonString(adapter.getAttendanceStatuses());
    Call<PlainResponse> call = apiClient.postAttendance(sessionManager.getSchool().getId(), aClass.getId(), date.get(), json);
    call.enqueue(new Callback<PlainResponse>() {
      @Override
      public void onResponse(@NonNull Call<PlainResponse> call, @NonNull Response<PlainResponse> response) {
        if(response.isSuccessful()){
          viewState.success("Attendance successfully submitted!");
          return;
        }
        else {
          logger.print(TAG, response.errorBody());
        }
        viewState.error("Application encountered an error while submitting attendance. The response we got from the server is not what we expected response. Be assured that we are working to resolve the issue. If the problem persists, please contact us so we can resolve it.");
      }

      @Override
      public void onFailure(@NonNull Call<PlainResponse> call, @NonNull Throwable t) {
        viewState.connectionError();
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
