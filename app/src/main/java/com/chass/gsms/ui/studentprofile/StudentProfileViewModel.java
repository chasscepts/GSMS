package com.chass.gsms.ui.studentprofile;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
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
import com.chass.gsms.interfaces.IMonthSelectedListener;
import com.chass.gsms.models.Attendance;
import com.chass.gsms.models.Class;
import com.chass.gsms.models.Student;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.viewmodels.AttendanceStatusViewModel;
import com.chass.gsms.viewmodels.MonthPicker;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    return dataStore.getSelectedClassName();
  }

  private AttendanceStatusViewModel[] statuses;

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
    int length = 42;
    statuses = new AttendanceStatusViewModel[length];
    for(int i = 0; i < length; i++){
      statuses[i] = new AttendanceStatusViewModel();
    }
    adapter.setStatuses(statuses);
  }

  private void loadAttendances(List<Attendance> attendances){
    AttendanceStatusViewModel[] statusViewModels = adapter.getStatuses();
    for(AttendanceStatusViewModel viewModel : statusViewModels){
      viewModel.clear();
    }

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
    Call<List<Attendance>> call = apiClient.getStudentAttendances(sessionManager.getSchool().getId(), aClass.getName(), student.getId(), monthPicker.getFirstDay(), monthPicker.getLastDay());
    call.enqueue(new Callback<List<Attendance>>() {
      @Override
      public void onResponse(@NonNull Call<List<Attendance>> call, @NonNull Response<List<Attendance>> response) {
        if(response.isSuccessful()){
          List<Attendance> attendance = response.body();
          if(attendance != null){
            loadAttendances(attendance);
            loadingAttendance.set(false);
            return;
          }
        }
        loadingAttendance.set(false);
        viewState.error("Application encountered an error while loading attendance. The response we got from the server is not what we expected response. Be assured that we are working to resolve the issue. If the problem persists, please contact us so we can resolve it.");
      }

      @Override
      public void onFailure(@NonNull Call<List<Attendance>> call, @NonNull Throwable t) {
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
