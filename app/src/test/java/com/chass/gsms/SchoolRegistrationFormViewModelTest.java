package com.chass.gsms;

import android.net.Uri;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.chass.gsms.networks.clients.MultipartFormData;
import com.chass.gsms.networks.retrofit.UploadStream;
import com.chass.gsms.viewmodels.SchoolRegistrationFormViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SchoolRegistrationFormViewModelTest {
  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @Mock
  Uri uri;

  @Mock
  MultipartFormData formData;

  @Mock
  UploadStream uploadStream;

  SchoolRegistrationFormViewModel form;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
    when(uri.getPath()).thenReturn("xxx/fgd");
    fillAllFormFields();
  }

  @Test
  public void NewFormIsInvalid(){
    form = new SchoolRegistrationFormViewModel(uploadStream, formData);
    assertFalse(form.isValid());
  }

  @Test
  public void FormIsValidWhenAllFieldsAreValid(){
    assertTrue(form.isValid());
  }

  @Test
  public void InvalidSchoolNameMakesFormInvalid(){
    form.setSchoolName(" A ");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidSchoolAddressMakesFormInvalid(){
    form.setSchoolAddress(" A ");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidSchoolEmailMakesFormInvalid(){
    form.setSchoolEmail("test.com");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidSchoolPhoneNumberMakesFormInvalid(){
    form.setSchoolPhoneNumber("DDD");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidAdminEmailMakesFormInvalid(){
    form.setAdminEmail("test.com");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidPasswordMakesFormInvalid(){
    form.setPassword("test");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidRepeatPasswordMakesFormInvalid(){
    form.setRepeatPassword("aaaa");
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidProfilePictureMakesFormInvalid(){
    when(uri.getPath()).thenReturn("");
    form.setSchoolPictureUri(uri);
    assertFalse(form.isValid());
  }

  @Test
  public void InvalidAdminFirstnameDoesNotAffectFormValidity(){
    form.setAdminFirstname("A    ");
    assertTrue(form.isValid());
  }

  @Test
  public void InvalidAdminLastnameDoesNotAffectFormValidity(){
    form.setAdminLastname("B    ");
    assertTrue(form.isValid());
  }

  @Test
  public void InvalidAdminPhoneNumberDoesNotAffectFormValidity(){
    form.setAdminPhoneNumber("www");
    assertTrue(form.isValid());
  }

  private void fillAllFormFields(){
    form = new SchoolRegistrationFormViewModel(uploadStream, formData);
    form.setSchoolName("Boys Model Secondary School");
    form.setSchoolAddress("15 Drive Way");
    form.setSchoolEmail("magin@yahoo.com");
    form.setSchoolPhoneNumber("09012345678");
    form.setAdminFirstname("Brad");
    form.setAdminLastname("Igwe");
    form.setAdminEmail("admin@sch.com");
    form.setAdminPhoneNumber("07115151515");
    form.setSchoolPictureUri(uri);
    form.setPassword("oooooo");
    form.setRepeatPassword("oooooo");
  }
}
