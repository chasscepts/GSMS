package com.chass.gsms;

import com.chass.gsms.viewmodels.LoginFormViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

@RunWith(JUnit4.class)
public class LoginFormViewModelTest {
  //@Rule
  //public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  LoginFormViewModel form;

  @Before
  public void setup(){
    fillAllFormFields();
  }

  @Test
  public void NewFormIsInvalid(){
    form = new LoginFormViewModel();
    assertFalse(form.isValid());
  }

  @Test
  public void FormIsValidWhenAllFieldsAreValid(){
    assertTrue(form.isValid());
  }

  @Test
  public void InvalidEmailMakesFormInvalid(){
    form.setEmail("test.com");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidSchoolIdMakesFormInvalid(){
    form.setSchoolIdText("DDD");
    assertFalse(form.isValid());
  }

  private void fillAllFormFields(){
    form = new LoginFormViewModel();
    form.setSchoolIdText("100");
    form.setEmail("magin@yahoo.com");
    form.setPassword("oooooo");
  }
}
