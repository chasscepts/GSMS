package com.chass.gsms;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.databinding.Observable;

import com.chass.gsms.viewmodels.NewStudentFormViewModel;
import com.chass.gsms.viewmodels.StudentViewModel;
import com.chass.gsms.viewmodels.UserFormViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class NewStudentFormViewModelTest {
  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @Mock
  UserFormViewModel parent1Form;

  @Mock
  UserFormViewModel parent2Form;

  NewStudentFormViewModel form;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void NewFormIsInvalid(){
    form = new NewStudentFormViewModel(parent1Form, parent2Form);
    assertFalse(form.isValid());
  }

  @Test
  public void FormIsValidWhenAllFieldsAreValid(){
    fillAllFormFields();
    assertTrue(form.isValid());
  }

  @Test
  public void InvalidFirstnameMakesFormInvalid(){
    fillAllFormFields();
    assertTrue(form.isValid());
    form.setFirstname(" A ");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidLastnameMakesFormInvalid(){
    fillAllFormFields();
    assertTrue(form.isValid());
    form.setLastname(" A ");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidParent1MakesFormInvalid(){
    UserFormViewModel parent1 = new UserFormViewModel();
    form = new NewStudentFormViewModel(parent1, parent2Form);
    form.setFirstname("Tim");
    form.setLastname("Bruce");
    parent1.setEmail("test@test.com");
    assertTrue(form.isValid());
    parent1.setEmail("test.com");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidParent2DoesNotAffectFormValidity(){
    UserFormViewModel parent2 = new UserFormViewModel();
    form = new NewStudentFormViewModel(parent1Form, parent2);
    when(parent1Form.isValid()).thenReturn(true);
    form.setFirstname("Tim");
    form.setLastname("Bruce");
    assertTrue(form.isValid());
    parent2.setEmail("test.com");
    assertTrue(form.isValid());
  }

  @Test
  public void SetFirstnameRaisesTheCorrectNotification(){
    form = new NewStudentFormViewModel(parent1Form, parent2Form);
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setFirstname("AAA");
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.firstname));
  }

  @Test
  public void SetLastnameRaisesTheCorrectNotification(){
    form = new NewStudentFormViewModel(parent1Form, parent2Form);
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setLastname("AAA");
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.lastname));
  }

  @Test
  public void SetValidRaisesTheCorrectNotification(){
    fillAllFormFields();
    Observable.OnPropertyChangedCallback callback = Mockito.mock(Observable.OnPropertyChangedCallback.class);
    form.addOnPropertyChangedCallback(callback);
    form.setLastname("A"); //Will make form invalid
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    Mockito.verify(callback, atLeastOnce()).onPropertyChanged(any(), argumentCaptor.capture());
    assertTrue(argumentCaptor.getAllValues().contains(BR.valid));
  }

  private void fillAllFormFields(){
    form = new NewStudentFormViewModel(parent1Form, parent2Form);
    when(parent1Form.isValid()).thenReturn(true);
    when(parent2Form.isValid()).thenReturn(true);
    form.setFirstname("Chris");
    form.setLastname("Bruce");
  }
}
