package com.chass.gsms;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.SavedStateHandle;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.SharedDataStore;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.Class;
import com.chass.gsms.models.Student;
import com.chass.gsms.repositories.ClassRepository;
import com.chass.gsms.ui.classdetails.ClassDetailsViewModel;
import com.chass.gsms.ui.classdetails.StudentListAdapter;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ClassDetailsViewModelTest {
  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @Mock
  ClassRepository repository;

  @Mock
  Call<Class> apiCall;

  @Mock
  Class aClass;

  @Mock
  SharedDataStore dataStore;

  @Mock
  ILogger logger;

  @Mock
  StudentListAdapter adapter;

  SavedStateHandle savedStateHandle;
  ViewStateViewModel viewState;
  ClassDetailsViewModel viewModel;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
    savedStateHandle = new SavedStateHandle();
    viewState = new ViewStateViewModel();
    when(dataStore.getSelectedClassName()).thenReturn("S S 1 A");
    when(repository.getClass(anyString())).thenReturn(apiCall);
    viewModel = new ClassDetailsViewModel(savedStateHandle, repository, dataStore, viewState, logger, adapter);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void SetupWhenNetworkRequestFails(){
    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<Class>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    verify(apiCall).enqueue(argumentCaptor.capture());

    Callback<Class> callback = argumentCaptor.getValue();
    callback.onFailure(apiCall, new Throwable());

    verify(adapter, never()).loadStudents(any());
    assertEquals(ViewStates.ERROR, viewState.getState());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void LoginWhenNetworkRequestSucceeds(){
    Student[] students = {Mockito.mock(Student.class), Mockito.mock(Student.class)};
    when(aClass.getStudents()).thenReturn(students);
    Response<Class> response = Response.success(aClass);

    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<Class>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    verify(apiCall).enqueue(argumentCaptor.capture());

    Callback<Class> callback = argumentCaptor.getValue();
    callback.onResponse(apiCall, response);

    ArgumentCaptor<Student[]> captor2 = ArgumentCaptor.forClass(Student[].class);
    verify(adapter).loadStudents(captor2.capture());
    assertArrayEquals(students, captor2.getValue());
    assertEquals(aClass, viewModel.aClass.get());

    assertEquals(ViewStates.NORMAL, viewState.getState());
  }

}