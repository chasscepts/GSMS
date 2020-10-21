package com.chass.gsms;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.SavedStateHandle;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.models.LoginResponse;
import com.chass.gsms.models.School;
import com.chass.gsms.models.User;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.ui.login.LoginViewModel;
import com.chass.gsms.viewmodels.LoginFormViewModel;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class LoginViewModelTest {
  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  //When register is called and
  //1. form is invalid, ViewStateViewModel will be in INFO state.
  //2. form is valid -
      //1. ViewStateViewModel will go to BUSY state
      //2. ApiClient::register will be called once
      //3. ApiClient request succeeds -
          //1. SessionManager::user will not be null
          //2. SessionManager::school will not be null
          //3. SessionManager::loggedIn will be true
          //4. ViewStateViewModel will go to NORMAL state
      //4. ApiClient request fails -
          //1. SessionManager::user will not be null
          //2. SessionManager::school will be null
          //3. SessionManager::loggedIn will be false
          //4. ViewStateViewModel will go to ERROR state

  @Mock
  ApiClient apiClient;

  @Mock
  Call<LoginResponse> apiCall;

  @Mock
  LoginResponse loginResponse;

  @Mock
  LoginFormViewModel form;

  Response<LoginResponse> response;

  SavedStateHandle savedStateHandle;
  ViewStateViewModel viewState;
  SessionManager sessionManager;
  LoginViewModel viewModel;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
    savedStateHandle = new SavedStateHandle();
    viewState = new ViewStateViewModel();
    sessionManager = new SessionManager();
    viewModel = new LoginViewModel(savedStateHandle, sessionManager, apiClient, viewState, form);
    when(apiClient.login(anyInt(), anyString(), anyString())).thenReturn(apiCall);

    when(form.getSchoolId()).thenReturn(1);
    when(form.getEmail()).thenReturn("test@test.com");
    when(form.getPassword()).thenReturn("1234567");
  }

  @Test
  public void LoginWhenFormIsInvalidSetsStateToInfo() {
    when(form.isValid()).thenReturn(false);
    viewModel.login();
    assertEquals(ViewStates.INFO, viewState.getState());
    verify(apiClient, never()).login(anyInt(), anyString(), anyString());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void LoginWhenNetworkRequestFails(){
    sessionManager.logout();
    when(form.isValid()).thenReturn(true);

    viewModel.login();
    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<LoginResponse>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    Mockito.verify(apiCall).enqueue(argumentCaptor.capture());

    Callback<LoginResponse> callback = argumentCaptor.getValue();
    callback.onFailure(apiCall, new Throwable());

    assertNull(sessionManager.getUser());
    assertNull(sessionManager.getSchool());
    assertFalse(sessionManager.isLoggedIn().getValue());
    assertEquals(ViewStates.ERROR, viewState.getState());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void LoginWhenNetworkRequestSucceeds(){
    when(form.isValid()).thenReturn(true);
    when(loginResponse.getSchool()).thenReturn(new School());
    when(loginResponse.getUser()).thenReturn(new User());

    response = Response.success(loginResponse);
    sessionManager.logout();

    viewModel.login();
    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<LoginResponse>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    Mockito.verify(apiCall, times(1)).enqueue(argumentCaptor.capture());

    Callback<LoginResponse> callback = argumentCaptor.getValue();
    callback.onResponse(apiCall, response);

    assertEquals(loginResponse.getUser(), sessionManager.getUser());
    assertEquals(loginResponse.getSchool(), sessionManager.getSchool());
    assertTrue(sessionManager.isLoggedIn().getValue());
    assertEquals(ViewStates.NORMAL, viewState.getState());
  }

}