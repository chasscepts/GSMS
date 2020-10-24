package com.chass.gsms;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.SavedStateHandle;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.LoginResponse;
import com.chass.gsms.models.School;
import com.chass.gsms.models.User;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.ui.login.SchoolRegistrationViewModel;
import com.chass.gsms.viewmodels.SchoolRegistrationFormViewModel;
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

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SchoolRegistrationViewModelTest {
  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @Mock
  ApiClient apiClient;

  @Mock
  Call<LoginResponse> apiCall;

  @Mock
  LoginResponse loginResponse;

  @Mock
  SchoolRegistrationFormViewModel form;

  @Mock
  File file;

  @Mock
  ILogger logger;

  Response<LoginResponse> response;


  SavedStateHandle savedStateHandle;
  ViewStateViewModel viewState;
  SessionManager sessionManager;
  SchoolRegistrationViewModel viewModel;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
    savedStateHandle = new SavedStateHandle();
    viewState = new ViewStateViewModel();
    sessionManager = new SessionManager();
    viewModel = new SchoolRegistrationViewModel(savedStateHandle, sessionManager, apiClient, viewState, form, logger);
    when(file.exists()).thenReturn(true);
    when(file.getName()).thenReturn("");
    when(form.getSchoolPicture()).thenReturn(file);
    when(apiClient.register(any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(apiCall);
  }

  @Test
  public void RegisterWhenFormIsInvalidSetsStateToInfo() {
    when(form.isValid()).thenReturn(false);
    viewModel.register();
    assertEquals(ViewStates.INFO, viewState.getState());
    verify(apiClient, never()).register(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void RegisterWhenNetworkRequestFails(){
    sessionManager.logout();
    when(form.isValid()).thenReturn(true);

    viewModel.register();
    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<LoginResponse>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    //verify(apiClient).register(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
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
  public void RegisterWhenNetworkRequestSucceeds(){
    when(form.isValid()).thenReturn(true);
    when(loginResponse.getSchool()).thenReturn(new School());
    when(loginResponse.getUser()).thenReturn(new User());

    response = Response.success(loginResponse);
    sessionManager.logout();

    viewModel.register();
    assertEquals(ViewStates.BUSY, viewState.getState());

    ArgumentCaptor<Callback<LoginResponse>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    Mockito.verify(apiCall, times(1)).enqueue(argumentCaptor.capture());

    Callback<LoginResponse> callback = argumentCaptor.getValue();
    callback.onResponse(apiCall, response);

    assertEquals(loginResponse.getUser(), sessionManager.getUser());
    assertEquals(loginResponse.getSchool(), sessionManager.getSchool());
    assertTrue(sessionManager.isLoggedIn().getValue());
    assertEquals(ViewStates.SUCCESS, viewState.getState());
  }

}
