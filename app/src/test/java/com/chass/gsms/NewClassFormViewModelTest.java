package com.chass.gsms;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.databinding.Observable;

import com.chass.gsms.viewmodels.NewClassFormViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;

@RunWith(JUnit4.class)
public class NewClassFormViewModelTest {
  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  NewClassFormViewModel form;

  @Before
  public void setup(){

  }

  @Test
  public void NewFormIsInvalid(){
    form = new NewClassFormViewModel();
    assertFalse(form.isFormValid());
  }

  @Test
  public void FormIsValidWhenAllFieldsAreValid(){
    fillAllFormFields();
    assertTrue(form.isFormValid());
  }

  @Test
  public void InvalidClassnameMakesFormInvalid(){
    fillAllFormFields();
    assertTrue(form.isFormValid());
    form.setClassName("  A ");
    assertFalse(form.isFormValid());
  }

  @Test
  public void InvalidTeacherEmailMakesFormInvalid(){
    fillAllFormFields();
    assertTrue(form.isFormValid());
    form.setTeacherEmail("test.com");
    assertFalse(form.isFormValid());
  }

  @Test
  public void InvalidTeacherFirstnameDoesNotAffectFormValidity(){
    fillAllFormFields();
    assertTrue(form.isFormValid());
    form.setTeacherFirstname("A    ");
    assertTrue(form.isFormValid());
  }

  @Test
  public void InvalidTeacherLastnameDoesNotAffectFormValidity(){
    fillAllFormFields();
    assertTrue(form.isFormValid());
    form.setTeacherLastname("B    ");
    assertTrue(form.isFormValid());
  }

  @Test
  public void InvalidTeacherPhoneNumberDoesNotAffectFormValidity(){
    fillAllFormFields();
    assertTrue(form.isFormValid());
    form.setTeacherPhoneNumber("www");
    assertTrue(form.isFormValid());
  }

  @Test
  public void SetClassNameRaisesTheCorrectNotification(){
    form = new NewClassFormViewModel();
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setClassName("S S 3");
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.className));
  }

  @Test
  public void SetTeacherFirstnameRaisesTheCorrectNotification(){
    form = new NewClassFormViewModel();
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setTeacherFirstname("AAA");
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.teacherFirstname));
  }

  @Test
  public void SetTeacherLastnameRaisesTheCorrectNotification(){
    form = new NewClassFormViewModel();
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setTeacherLastname("AAA");
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.teacherLastname));
  }

  @Test
  public void SetTeacherEmailRaisesTheCorrectNotification(){
    form = new NewClassFormViewModel();
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setTeacherEmail("AAA");
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.teacherEmail));
  }

  @Test
  public void SetTeacherPhoneNumberRaisesTheCorrectNotification(){
    form = new NewClassFormViewModel();
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setTeacherPhoneNumber("111");
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.teacherPhoneNumber));
  }

  private void fillAllFormFields(){
    form = new NewClassFormViewModel();
    form.setClassName("S S 1 A");
    form.setTeacherFirstname("Chris");
    form.setTeacherLastname("Bruce");
    form.setTeacherEmail("test@test.com");
    form.setTeacherPhoneNumber("08012345678");
  }
}
