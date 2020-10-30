package com.chass.gsms.viewmodels;

import android.text.TextUtils;

import androidx.databinding.Bindable;

import com.chass.gsms.BR;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;

/**
 * Provides binding and validation for Login Form Fields.
 * schoolId must be an integer
 * email must match email pattern
 * length of password must be greater than 5.
 * All fields are required
 */
@ActivityRetainedScoped
public class LoginFormViewModel extends BaseFormViewModel {
  @Inject
  public LoginFormViewModel(){
    //test();
  }

  //Todo: remove
  private void test() {
    setSchoolIdText("100");
    setEmail("admin@ghsi.com");
    setPassword("oooooo");
  }

  private int schoolId;
  private String email, password;
  private boolean schoolIdValid, emailValid, passwordValid, valid;

  //region School Id
  /**
   * Using BindingAdapter and InverseBindingAdapter to bind int to TextView text messes up the cursor position.
   * Using a back tick crashes my app.
   * So I hack it till I find an acceptable solution.
   */
  private String schoolIdText;

  @Bindable
  public String getSchoolIdText(){
    return schoolIdText;
  }

  @Bindable
  public boolean isSchoolIdValid() {
    return schoolIdValid;
  }

  @Bindable
  public int getSchoolId() {
    return schoolId;
  }

  public void setSchoolIdText(String text){
    if(TextUtils.equals(text, schoolIdText)) return;
    schoolIdText = text;
    notifyPropertyChanged(BR.schoolIdText);
    int val = 0;
    try{
      val = Integer.parseInt(text);
    }
    catch (Exception ignored){ }
    schoolId = val;
    boolean valid = val >= 100;
    if(valid != this.schoolIdValid){
      this.schoolIdValid = valid;
      notifyPropertyChanged(BR.schoolIdValid);
      checkValidity(valid);
    }
  }
  //endregion

  //region Email
  @Bindable
  public String getEmail() {
    return email;
  }

  @Bindable
  public boolean isEmailValid() {
    return emailValid;
  }

  public void setEmail(String email) {
    if(TextUtils.equals(email, this.email)) return;
    this.email = email;
    notifyPropertyChanged(BR.email);
    boolean valid = isValidEmail(email);
    if(valid != emailValid){
      emailValid = valid;
      notifyPropertyChanged(BR.emailValid);
      checkValidity(valid);
    }
  }
  //endregion

  //region Password
  @Bindable
  public String getPassword() {
    return password;
  }

  @Bindable
  public boolean isPasswordValid() {
    return passwordValid;
  }

  public void setPassword(String password) {
    if(TextUtils.equals(password, this.password)) return;
    this.password = password;
    notifyPropertyChanged(BR.password);
    boolean valid = !TextUtils.isEmpty(password) && password.trim().length() > 6;
    if(valid != passwordValid){
      passwordValid = valid;
      notifyPropertyChanged(BR.passwordValid);
      checkValidity(valid);
    }
  }
  //endregion

  //region Valid
  @Bindable
  public boolean isValid() {
    return valid;
  }

  private void setValid(boolean valid){
    if(valid == this.valid) return;
    this.valid = valid;
    notifyPropertyChanged(BR.valid);
  }

  private void checkValidity(boolean valid){
    if(!valid){
      setValid(false);
    }
    setValid(schoolIdValid && emailValid);
  }
  //endregion
}
