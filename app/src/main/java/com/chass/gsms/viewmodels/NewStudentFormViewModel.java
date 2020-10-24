package com.chass.gsms.viewmodels;

import android.text.TextUtils;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import javax.inject.Inject;

/**
 * Encapsulates and provides validation for new Student form
 * A minimum of one parent is required for the form to be valid
 * The firstname and lastname are required
 */
public class NewStudentFormViewModel extends BaseFormViewModel {
  private final UserFormViewModel parent1Form;
  private final UserFormViewModel parent2Form;

  private String firstname, lastname;
  private boolean firstnameValid, lastnameValid, valid;

  public UserFormViewModel getParent1Form() {
    return parent1Form;
  }

  public UserFormViewModel getParent2Form() {
    return parent2Form;
  }

  @Inject
  public NewStudentFormViewModel(UserFormViewModel parent1Form, UserFormViewModel parent2Form){
    this.parent1Form =  parent1Form;
    this.parent2Form = parent2Form;
    parent1Form.setValidityChangeListener(this::validateForm);
  }

  //region Firstname
  @Bindable
  public String getFirstname() {
    return firstname;
  }

  @Bindable
  public boolean isFirstnameValid() {
    return firstnameValid;
  }

  public void setFirstname(String firstname) {
    if(TextUtils.equals(firstname, this.firstname)) return;
    this.firstname = firstname;
    notifyPropertyChanged(BR.firstname);
    boolean valid = isValidName(firstname);
    if(valid != this.firstnameValid){
      this.firstnameValid = valid;
      notifyPropertyChanged(BR.firstnameValid);
      validateForm(valid);
    }
  }
  //endregion

  //region Lastname
  @Bindable
  public String getLastname() {
    return lastname;
  }

  @Bindable
  public boolean isLastnameValid() {
    return lastnameValid;
  }

  public void setLastname(String lastname) {
    if(TextUtils.equals(lastname, this.lastname)) return;
    this.lastname = lastname;
    notifyPropertyChanged(BR.lastname);
    boolean valid = isValidName(lastname);
    if(valid != this.lastnameValid){
      this.lastnameValid = valid;
      notifyPropertyChanged(BR.lastnameValid);
      validateForm(valid);
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

  private void validateForm(boolean valid){
    if(!valid){
      setValid(false);
      return;
    }
    setValid(firstnameValid && lastnameValid && parent1Form.isValid());
  }
  //endregion
}
