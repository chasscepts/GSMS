package com.chass.gsms;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.databinding.Observable;

import com.chass.gsms.interfaces.IValidityChangedListener;
import com.chass.gsms.viewmodels.UserFormViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class UserFormViewModelTest {
  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @Mock
  IValidityChangedListener listener;

  UserFormViewModel form;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void NewFormIsInvalid(){
    form = new UserFormViewModel();
    assertFalse(form.isValid());
  }

  @Test
  public void FormIsValidWhenAllFieldsAreValid(){
    fillAllFormFields();
    assertTrue(form.isValid());
  }

  @Test
  public void InvalidEmailMakesFormInvalid(){
    fillAllFormFields();
    assertTrue(form.isValid());
    form.setEmail("test.com");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidFirstnameDoesNotAffectFormValidity(){
    fillAllFormFields();
    assertTrue(form.isValid());
    form.setFirstname("A    ");
    assertTrue(form.isValid());
  }

  @Test
  public void InvalidLastnameDoesNotAffectFormValidity(){
    fillAllFormFields();
    assertTrue(form.isValid());
    form.setLastname("B    ");
    assertTrue(form.isValid());
  }

  @Test
  public void InvalidPhoneNumberDoesNotAffectFormValidity(){
    fillAllFormFields();
    assertTrue(form.isValid());
    form.setPhoneNumber("www");
    assertTrue(form.isValid());
  }

  @Test
  public void SetFirstnameRaisesTheCorrectNotification(){
    form = new UserFormViewModel();
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setFirstname("AAA");
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.firstname));
  }

  @Test
  public void SetLastnameRaisesTheCorrectNotification(){
    form = new UserFormViewModel();
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setLastname("AAA");
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.lastname));
  }

  @Test
  public void SetEmailRaisesTheCorrectNotification(){
    form = new UserFormViewModel();
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setEmail("AAA");
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.email));
  }

  @Test
  public void SetPhoneNumberRaisesTheCorrectNotification(){
    form = new UserFormViewModel();
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setPhoneNumber("111");
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.phoneNumber));
  }

  @Test
  public void ValidityChangesNotifiesListeners(){
    form = new UserFormViewModel();
    form.setValidityChangeListener(listener);
    form.setEmail("test@test.com");   //will make form valid
    form.setEmail("test.com");        //will make form invalid
    verify(listener, times(2)).onValidityChanged(anyBoolean());
  }

  private void fillAllFormFields(){
    form = new UserFormViewModel();
    form.setFirstname("Chris");
    form.setLastname("Bruce");
    form.setEmail("test@test.com");
    form.setPhoneNumber("08012345678");
  }
}
