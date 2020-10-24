package com.chass.gsms.viewmodels;

import android.text.TextUtils;

import androidx.databinding.Bindable;

import com.chass.gsms.BR;
import com.chass.gsms.interfaces.IValidityChangedListener;

import javax.inject.Inject;

/**
 * Encapsulate and provides validation for fields (firstname, lastname, email, phoneNumber) required to create a user.
 * Because the user maybe referencing an already existing member which can be done through the email alone, We only requires that the email be valid to consider the form valid.
 *
 */
public class UserFormViewModel extends BaseFormViewModel {
  @Inject
  public UserFormViewModel(){

  }

  private IValidityChangedListener validityChangeListener;
  /**
   * This ViewModel is intended to be used as part of other ViewModels that are then bound to views. Those ViewModels' validity will therefore depend on the validity of this one.
   *  * The IValidityChangedListener interface provides a mechanism to inform those ViewModels that the validity of this ViewModel has changed so they take appropriate action.
   * @param listener ViewModel which validity depends on the validity of this one
   */
  public void setValidityChangeListener(IValidityChangedListener listener){
    validityChangeListener = listener;
  }

  private String firstname, lastname, email, phoneNumber;
  private boolean firstnameValid, lastnameValid, emailValid, phoneNumberValid, valid;

  //region Firstname
  @Bindable
  public String getFirstname() {
    return firstname;
  }

  @Bindable
  public boolean isFirstnameValid(){
    return firstnameValid;
  }

  public void setFirstname(String firstname) {
    if(TextUtils.equals(firstname, this.firstname)) return;
    this.firstname = firstname;
    notifyPropertyChanged(BR.firstname);
    boolean valid = isValidName(firstname);
    if(valid != this.firstnameValid){
      this.firstnameValid =  valid;
      notifyPropertyChanged(BR.firstnameValid);
    }
  }
  //endregion

  //region Lastname
  @Bindable
  public String getLastname() {
    return lastname;
  }

  @Bindable
  public boolean isLastnameValid(){
    return lastnameValid;
  }

  public void setLastname(String lastname) {
    if(TextUtils.equals(lastname, this.lastname)) return;
    this.lastname = lastname;
    notifyPropertyChanged(BR.lastname);
    boolean valid = isValidName(lastname);
    if(valid != this.lastnameValid){
      this.lastnameValid =  valid;
      notifyPropertyChanged(BR.lastnameValid);
    }
  }
  //endregion

  //region Email
  @Bindable
  public String getEmail() {
    return email;
  }

  @Bindable
  public boolean isEmailValid(){
    return emailValid;
  }

  public void setEmail(String email) {
    if(TextUtils.equals(email, this.email)) return;
    this.email = email;
    notifyPropertyChanged(BR.email);
    boolean valid = isValidEmail(email);
    if(valid != this.emailValid){
      this.emailValid =  valid;
      notifyPropertyChanged(BR.emailValid);
      setValid(valid);
    }
  }
  //endregion

  //region Phone Number
  @Bindable
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Bindable
  public boolean isPhoneNumberValid(){
    return phoneNumberValid;
  }

  public void setPhoneNumber(String phoneNumber) {
    if(TextUtils.equals(phoneNumber, this.phoneNumber)) return;
    this.phoneNumber = phoneNumber;
    notifyPropertyChanged(BR.phoneNumber);
    boolean valid = isValidPhoneNumber(phoneNumber);
    if(valid != this.phoneNumberValid){
      this.phoneNumberValid =  valid;
      notifyPropertyChanged(BR.phoneNumberValid);
    }
  }
  //endregion

  //region Form Valid
  public boolean isValid(){
    return valid;
  }

  private void setValid(boolean valid){
    if(valid == this.valid) return;
    this.valid = valid;
    if(validityChangeListener != null){
      validityChangeListener.onValidityChanged(valid);
    }
  }
  //endregion
}
