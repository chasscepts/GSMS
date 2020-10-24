package com.chass.gsms;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.SavedStateHandle;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.ClassesCache;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.helpers.SharedDataStore;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.Class;
import com.chass.gsms.models.School;
import com.chass.gsms.models.Student;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.ui.newclass.NewClassViewModel;
import com.chass.gsms.ui.newstudent.NewStudentViewModel;
import com.chass.gsms.viewmodels.NewClassFormViewModel;
import com.chass.gsms.viewmodels.NewStudentFormViewModel;
import com.chass.gsms.viewmodels.UserFormViewModel;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class NewStudentViewModelTest {
  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @Mock
  ApiClient apiClient;

  @Mock
  Call<Student> apiCall;

  @Mock
  School school;

  @Mock
  Class aClass;

  @Mock
  Student student;

  @Mock
  UserFormViewModel parent1Form;

  @Mock
  UserFormViewModel parent2Form;

  @Mock
  NewStudentFormViewModel form;

  @Mock
  //ClassesCache cache;
  SharedDataStore dataStore;

  @Mock
  SessionManager sessionManager;

  @Mock
  ILogger logger;

  SavedStateHandle savedStateHandle;
  ViewStateViewModel viewState;
  NewStudentViewModel viewModel;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
    savedStateHandle = new SavedStateHandle();
    viewState = new ViewStateViewModel();
    when(form.getFirstname()).thenReturn("AAA");
    when(form.getLastname()).thenReturn("BBB");
    when(form.getParent1Form()).thenReturn(parent1Form);
    when(form.getParent2Form()).thenReturn(parent2Form);
    when(parent1Form.getFirstname()).thenReturn("AAA");
    when(parent1Form.getLastname()).thenReturn("BBB");
    when(parent1Form.getEmail()).thenReturn("test@test.com");
    when(parent1Form.getPhoneNumber()).thenReturn("08012345678");
    when(parent2Form.getFirstname()).thenReturn("AAA");
    when(parent2Form.getLastname()).thenReturn("BBB");
    when(parent2Form.getEmail()).thenReturn("test@test.com");
    when(parent2Form.getPhoneNumber()).thenReturn("08012345678");
    when(parent1Form.isValid()).thenReturn(true);
    when(parent2Form.isValid()).thenReturn(false);
    when(apiClient.addStudent(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(apiCall);
    when(apiClient.addStudent(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(apiCall);
    when(school.getId()).thenReturn(2);
    when(sessionManager.getSchool()).thenReturn(school);
    when(aClass.getName()).thenReturn("S S 1 A");
    when(dataStore.getCurrentClass()).thenReturn(aClass);
    when(dataStore.getSelectedClassName()).thenReturn("S S 1 A");
    viewModel = new NewStudentViewModel(savedStateHandle, sessionManager, dataStore, apiClient, viewState, form, logger);
  }

  @Test
  public void RegisterWhenFormIsInvalidSetsStateToInfo() {
    when(form.isValid()).thenReturn(false);
    viewModel.addStudent();
    assertEquals(ViewStates.INFO, viewState.getState());
    verify(apiClient, never()).addStudent(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void AddStudentWhenNetworkRequestFails(){
    when(form.isValid()).thenReturn(true);
    ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
    viewModel.addStudent();
    verify(apiClient).addStudent(captor.capture(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
    assertEquals(2, captor.getValue().intValue());
    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<Student>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    Mockito.verify(apiCall).enqueue(argumentCaptor.capture());

    Callback<Student> callback = argumentCaptor.getValue();
    callback.onFailure(apiCall, new Throwable());

    verify(aClass, never()).addStudent(any());
    assertEquals(ViewStates.ERROR, viewState.getState());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void AddStudentWhenNetworkRequestSucceeds(){
    when(form.isValid()).thenReturn(true);

    Response<Student> response = Response.success(student);

    viewModel.addStudent();
    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<Student>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    Mockito.verify(apiCall).enqueue(argumentCaptor.capture());

    Callback<Student> callback = argumentCaptor.getValue();
    callback.onResponse(apiCall, response);

    verify(aClass).addStudent(any());
    assertEquals(ViewStates.SUCCESS, viewState.getState());
  }

}
