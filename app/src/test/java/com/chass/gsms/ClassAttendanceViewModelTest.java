package com.chass.gsms;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.SavedStateHandle;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.helpers.SharedDataStore;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.Class;
import com.chass.gsms.models.PlainResponse;
import com.chass.gsms.models.School;
import com.chass.gsms.models.Student;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.ui.attendance.AttendanceViewModel;
import com.chass.gsms.ui.attendance.ClassAttendanceAdapter;
import com.chass.gsms.viewmodels.AttendanceStatusViewModel;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ClassAttendanceViewModelTest {
  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @Mock
  Student student1;

  @Mock
  Student student2;

  Student[] students = {student1, student2};

  @Mock
  AttendanceStatusViewModel status1;

  @Mock
  AttendanceStatusViewModel status2;

  AttendanceStatusViewModel[] statuses = { status1, status2};

  @Mock
  Class aClass;

  @Mock
  SessionManager sessionManager;

  @Mock
  SharedDataStore dataStore;

  @Mock
  ApiClient apiClient;

  @Mock
  Call<PlainResponse> apiCall;

  @Mock
  ILogger logger;

  @Mock
  School school;

  @Mock
  ClassAttendanceAdapter adapter;

  SavedStateHandle savedStateHandle;
  ViewStateViewModel viewState;
  AttendanceViewModel viewModel;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
    when(school.getId()).thenReturn(100);
    when(sessionManager.getSchool()).thenReturn(school);
    when(student1.getId()).thenReturn(1);
    when(student2.getId()).thenReturn(2);
    when(status1.toString()).thenReturn("{\"studentId\": 1, \"isPresent\": true}");
    when(status1.toString()).thenReturn("{\"studentId\": 2, \"isPresent\": false}");
    when(adapter.getAttendanceStatuses()).thenReturn(new AttendanceStatusViewModel[] { new AttendanceStatusViewModel(student1), new AttendanceStatusViewModel(student2) });
    when(apiClient.postAttendance(anyInt(), anyInt(), anyString(), anyString())).thenReturn(apiCall);
    savedStateHandle = new SavedStateHandle();
    viewState = new ViewStateViewModel();
  }

  @Test
  public void SetupWhenCurrentClassIsNullShowsErrorAndReturns(){
    when(dataStore.getCurrentClass()).thenReturn(null);
    viewModel = new AttendanceViewModel(savedStateHandle, sessionManager, dataStore, viewState, apiClient, logger, adapter);
    assertEquals(ViewStates.ERROR, viewState.getState());
    verify(adapter, never()).loadStudents(any());
  }

  @Test
  public void SetupCorrectlyLoadsClassAdapterStatuses(){
    when(aClass.getStudents()).thenReturn(students);
    when(dataStore.getCurrentClass()).thenReturn(aClass);
    ArgumentCaptor<Student[]> captor = ArgumentCaptor.forClass(Student[].class);
    viewModel = new AttendanceViewModel(savedStateHandle, sessionManager, dataStore, viewState, apiClient, logger, adapter);
    verify(adapter).loadStudents(captor.capture());
    assertArrayEquals(students, captor.getValue());
  }

  @Test
  public void SubmitAttendanceWhenClassIsNullShowsError(){
    when(dataStore.getCurrentClass()).thenReturn(null);
    viewModel = new AttendanceViewModel(savedStateHandle, sessionManager, dataStore, viewState, apiClient, logger, adapter);
    viewState.restoreNormalState();
    viewModel.submitAttendance();
    assertEquals(ViewStates.ERROR, viewState.getState());
    verify(apiClient, never()).postAttendance(anyInt(), anyInt(), anyString(), anyString());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void SubmitAttendanceWhenNetworkRequestFailsShowsError(){
    when(aClass.getName()).thenReturn("S S 1 A");
    when(dataStore.getCurrentClass()).thenReturn(aClass);
    viewModel = new AttendanceViewModel(savedStateHandle, sessionManager, dataStore, viewState, apiClient, logger, adapter);
    viewModel.submitAttendance();
    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<PlainResponse>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    verify(apiCall).enqueue(argumentCaptor.capture());

    Callback<PlainResponse> callback = argumentCaptor.getValue();
    callback.onFailure(apiCall, new Throwable());

    assertEquals(ViewStates.ERROR, viewState.getState());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void SubmitAttendanceWhenNetworkRequestSucceedsShowsSuccess(){
    when(aClass.getName()).thenReturn("S S 1 A");
    when(dataStore.getCurrentClass()).thenReturn(aClass);
    viewModel = new AttendanceViewModel(savedStateHandle, sessionManager, dataStore, viewState, apiClient, logger, adapter);
    viewModel.submitAttendance();
    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<PlainResponse>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    Mockito.verify(apiCall).enqueue(argumentCaptor.capture());

    Callback<PlainResponse> callback = argumentCaptor.getValue();
    PlainResponse response = Mockito.mock(PlainResponse.class);
    callback.onResponse(apiCall, Response.success(response));

    assertEquals(ViewStates.SUCCESS, viewState.getState());
  }
}