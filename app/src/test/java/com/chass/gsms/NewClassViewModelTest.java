package com.chass.gsms;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.SavedStateHandle;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.ClassesCache;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.models.Class;
import com.chass.gsms.models.School;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.ui.newclass.NewClassViewModel;
import com.chass.gsms.viewmodels.NewClassFormViewModel;
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
public class NewClassViewModelTest {
  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  //When addClass is called and
    //1. form is invalid,
        //1. ViewStateViewModel will be in INFO state.
        //2. Network request is not made
    //2. form is valid -
        //1. ViewStateViewModel will go to BUSY state
        //2. ApiClient::addClass will be called once
        //3. ApiClient request succeeds -
            //1. ClassesCache::save will be called once
            //2. School::addClass will be called once
            //3. ViewStateViewModel will go to SUCCESS state
        //4. ApiClient request fails -
            //1. ClassesCache::save will NOT be called
            //2. School::addClass will NOT be called
            //3. ViewStateViewModel will go to ERROR state

  @Mock
  ApiClient apiClient;

  @Mock
  Call<com.chass.gsms.models.Class> apiCall;

  @Mock
  School school;

  @Mock
  Class aClass;

  @Mock
  NewClassFormViewModel form;

  @Mock
  ClassesCache cache;

  @Mock
  SessionManager sessionManager;

  SavedStateHandle savedStateHandle;
  ViewStateViewModel viewState;
  NewClassViewModel viewModel;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
    savedStateHandle = new SavedStateHandle();
    viewState = new ViewStateViewModel();
    when(form.getClassName()).thenReturn("S S 1");
    when(form.getTeacherFirstname()).thenReturn("AAA");
    when(form.getTeacherLastname()).thenReturn("BBB");
    when(form.getTeacherEmail()).thenReturn("test@test.com");
    when(form.getTeacherPhoneNumber()).thenReturn("08012345678");
    when(apiClient.addClass(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(apiCall);
    when(school.getId()).thenReturn(2);
    when(sessionManager.getSchool()).thenReturn(school);
    viewModel = new NewClassViewModel(savedStateHandle, sessionManager, apiClient, viewState, form, cache);
  }

  @Test
  public void RegisterWhenFormIsInvalidSetsStateToInfo() {
    when(form.isValid()).thenReturn(false);
    viewModel.addClass();
    assertEquals(ViewStates.INFO, viewState.getState());
    verify(apiClient, never()).addClass(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void RegisterWhenNetworkRequestFails(){
    when(form.isValid()).thenReturn(true);

    viewModel.addClass();
    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<com.chass.gsms.models.Class>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    Mockito.verify(apiCall).enqueue(argumentCaptor.capture());

    Callback<Class> callback = argumentCaptor.getValue();
    callback.onFailure(apiCall, new Throwable());

    verify(school, never()).addClass(any());
    verify(cache, never()).save(any());
    assertEquals(ViewStates.ERROR, viewState.getState());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void RegisterWhenNetworkRequestSucceeds(){
    when(form.isValid()).thenReturn(true);

    Response<Class> response = Response.success(aClass);
    sessionManager.logout();

    viewModel.addClass();
    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<Class>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    Mockito.verify(apiCall, times(1)).enqueue(argumentCaptor.capture());

    Callback<Class> callback = argumentCaptor.getValue();
    callback.onResponse(apiCall, response);

    verify(school).addClass(any());
    verify(cache).save(any());
    assertEquals(ViewStates.NORMAL, viewState.getState());
  }

}
