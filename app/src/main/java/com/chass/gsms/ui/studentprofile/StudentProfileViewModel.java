package com.chass.gsms.ui.studentprofile;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.helpers.SharedDataStore;
import com.chass.gsms.hilt.RetrofitRequestDefaultTimeout;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.interfaces.IMonthSelectedListener;
import com.chass.gsms.models.Class;
import com.chass.gsms.models.Student;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.MonthPicker;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentProfileViewModel extends ViewModel implements IMonthSelectedListener {
  private static final String TAG = "StudentProfileViewModel";
  private final SavedStateHandle savedStateHandle;
  private final SessionManager sessionManager;
  private final SharedDataStore dataStore;
  private final ApiClient apiClient;
  private final ILogger logger;
  private final AttendanceStatusAdapter adapter;
  private final ViewStateViewModel viewState;
  private final MonthPicker monthPicker;
  private final Student student;
  public static final int DISPLAY_CELL_COUNT = 42;

  public AttendanceStatusAdapter getAdapter(){
    return adapter;
  }

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  public MonthPicker getMonthPicker(){
    return monthPicker;
  }

  public Student getStudent(){
    return student;
  }

  public String getStudentClassName(){
    return dataStore.getCurrentClass().getName();
  }

  private String[] statuses;

  public final ObservableField<String> date = new ObservableField<>();

  //We don't want to stop interactions with the view while attendance is loading.
  //A loading status indicator should bind to this to indicate when loading is in progress.
  public final ObservableBoolean loadingAttendance = new ObservableBoolean();

  @ViewModelInject
  public StudentProfileViewModel(@Assisted SavedStateHandle savedStateHandle, SessionManager sessionManager, SharedDataStore dataStore, @RetrofitRequestDefaultTimeout ApiClient client, ViewStateViewModel viewState, ILogger logger, AttendanceStatusAdapter adapter, MonthPicker monthPicker){
    this.savedStateHandle = savedStateHandle;
    this.sessionManager = sessionManager;
    this.dataStore = dataStore;
    this.viewState = viewState;
    this.apiClient = client;
    this.logger = logger;
    this.adapter = adapter;
    this.monthPicker =  monthPicker;
    monthPicker.setOnMonthSelectedListener(this);
    this.student = dataStore.getCurrentStudent();
    setupAttendanceStatuses();
    getAttendance();
  }

  private void setupAttendanceStatuses(){
    String[] statuses = new String[DISPLAY_CELL_COUNT];
    for(int i = 0; i < DISPLAY_CELL_COUNT; i++){
      statuses[i] = "N";
    }
    adapter.setStatuses(statuses);
  }

  private void loadAttendances(List<String> attendances){
    String[] statuses = new String[DISPLAY_CELL_COUNT];
    int start = monthPicker.getStartWeekday(), n = attendances.size();
    for(int i = 0; i < start; i++){
      statuses[i] = "N";
    }
    for(int i = 0; i < n; i++){
      statuses[i + start] = attendances.get(i);
    }
    for(int i = start + n; i < DISPLAY_CELL_COUNT; i++){
      statuses[i] = "N";
    }
    adapter.setStatuses(statuses);
  }

  public void openPicker(){
    monthPicker.setOpen(true);
  }

  public void getAttendance(){
    if(loadingAttendance.get()) return;
    Class aClass = dataStore.getCurrentClass();
    if(aClass == null){
      return;
    }
    loadingAttendance.set(true);
    Call<List<String>> call = apiClient.getStudentAttendance(sessionManager.getSchool().getId(), aClass.getId(), student.getId(), monthPicker.getYear(), monthPicker.getMonth(), 1, monthPicker.getDaysInMonth());
    call.enqueue(new Callback<List<String>>() {
      @Override
      public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
        if(response.isSuccessful()){
          List<String> attendances = response.body();
          if(attendances != null){
            loadAttendances(attendances);
            loadingAttendance.set(false);
            return;
          }
        }
        loadingAttendance.set(false);
        viewState.error("Application encountered an error while loading attendance. The response we got from the server is not what we expected response. Be assured that we are working to resolve the issue. If the problem persists, please contact us so we can resolve it.");
      }

      @Override
      public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
        loadingAttendance.set(false);
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

  @Override
  public void onMonthSelected(MonthPicker picker) {
    getAttendance();
  }
}
