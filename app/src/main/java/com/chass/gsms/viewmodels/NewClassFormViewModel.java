package com.chass.gsms.viewmodels;

import android.text.TextUtils;

import androidx.databinding.Bindable;

import com.chass.gsms.BR;

/**
 * Encapsulates a Form for creating a new class.
 * If teacher is not already a GSMS user, all teacher fields are required for registration
 * All fields are required if teacher is not already a GSMS user.
 */
public class NewClassFormViewModel extends BaseFormViewModel{
  public NewClassFormViewModel(){}

  private int id;
  private String className, teacherFirstname, teacherLastname, teacherEmail, teacherPhoneNumber;
  private boolean idValid, classNameValid, teacherFirstnameValid, teacherLastnameValid, teacherEmailValid, teacherPhoneNumberValid, formValid;

  //region id
  @Bindable
  public int getId() {
    return id;
  }

  @Bindable
  public boolean isIdValid(){
    return idValid;
  }

  public void setId(int id) {
    if(id == this.id) return;
    this.id = id;
    notifyPropertyChanged(BR.id);
    boolean valid = id > 0;
    if(valid != idValid){
      idValid = valid;
      notifyPropertyChanged(BR.idValid);
    }
  }
  //endregion

  //region Class name
  @Bindable
  public String getClassName() {
    return className;
  }

  @Bindable
  public boolean isClassNameValid(){
    return classNameValid;
  }

  public void setClassName(String className) {
    if(TextUtils.equals(className, this.className)) return;
    this.className = className;
    notifyPropertyChanged(BR.className);
    boolean valid = hasMinLength(className, 3);
    if(valid != this.classNameValid){
      this.classNameValid =  valid;
      notifyPropertyChanged(BR.classNameValid);
      validateForm(valid);
    }
  }
  //endregion

  //region Teacher Firstname
  @Bindable
  public String getTeacherFirstname() {
    return teacherFirstname;
  }

  @Bindable
  public boolean isTeacherFirstnameValid(){
    return teacherFirstnameValid;
  }

  public void setTeacherFirstname(String teacherFirstname) {
    if(TextUtils.equals(teacherFirstname, this.teacherFirstname)) return;
    this.teacherFirstname = teacherFirstname;
    notifyPropertyChanged(BR.teacherFirstname);
    boolean valid = isValidName(teacherFirstname);
    if(valid != this.teacherFirstnameValid){
      this.teacherFirstnameValid =  valid;
      notifyPropertyChanged(BR.teacherFirstnameValid);
    }
  }
  //endregion

  //region Teacher Lastname
  @Bindable
  public String getTeacherLastname() {
    return teacherLastname;
  }

  @Bindable
  public boolean isTeacherLastnameValid(){
    return teacherLastnameValid;
  }

  public void setTeacherLastname(String teacherLastname) {
    if(TextUtils.equals(teacherLastname, this.teacherLastname)) return;
    this.teacherLastname = teacherLastname;
    notifyPropertyChanged(BR.teacherLastname);
    boolean valid = isValidName(teacherLastname);
    if(valid != this.teacherLastnameValid){
      this.teacherLastnameValid =  valid;
      notifyPropertyChanged(BR.teacherLastnameValid);
    }
  }
  //endregion

  //region Teacher Email
  @Bindable
  public String getTeacherEmail() {
    return teacherEmail;
  }

  @Bindable
  public boolean isTeacherEmailValid(){
    return teacherEmailValid;
  }

  public void setTeacherEmail(String teacherEmail) {
    if(TextUtils.equals(teacherEmail, this.teacherEmail)) return;
    this.teacherEmail = teacherEmail;
    notifyPropertyChanged(BR.teacherEmail);
    boolean valid = isValidEmail(teacherEmail);
    if(valid != this.teacherEmailValid){
      this.teacherEmailValid =  valid;
      notifyPropertyChanged(BR.teacherEmailValid);
      validateForm(valid);
    }
  }
  //endregion

  //region  Teacher Phone Number
  @Bindable
  public String getTeacherPhoneNumber() {
    return teacherPhoneNumber;
  }

  @Bindable
  public boolean isTeacherPhoneNumberValid(){
    return teacherPhoneNumberValid;
  }

  public void setTeacherPhoneNumber(String teacherPhoneNumber) {
    if(TextUtils.equals(teacherPhoneNumber, this.teacherPhoneNumber)) return;
    this.teacherPhoneNumber = teacherPhoneNumber;
    notifyPropertyChanged(BR.teacherPhoneNumber);
    boolean valid = isValidPhoneNumber(teacherPhoneNumber);
    if(valid != this.teacherPhoneNumberValid){
      this.teacherPhoneNumberValid =  valid;
      notifyPropertyChanged(BR.teacherPhoneNumberValid);
    }
  }
  //endregion

  //region Form Valid
  @Bindable
  public boolean isFormValid(){
    return formValid;
  }

  private void setFormValid(boolean valid){
    if(valid == formValid) return;
    this.formValid = valid;
    notifyPropertyChanged(BR.formValid);
  }

  private void validateForm(boolean valid){
    if(!valid){
      setFormValid(false);
    }
    setFormValid(classNameValid && teacherEmailValid);
  }
  //endregion
}