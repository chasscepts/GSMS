package com.chass.gsms.viewmodels;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.chass.gsms.BR;

import java.io.File;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;

/**
 * Encapsulates the fields of School Registration Form and provides validation for the fields and form.
 * The Backend design is such that if the Admin Email already exists, it will attempt a login. Else it will create a new User.
 * Our approach is that if the Email and password of the Admin fields are provided we assume that the user is attempting a login. Therefore the form can still be valid even when the other Admin fields(AdminFirstname, AdminLastname and AdminPhoneNumber) are invalid.
 */
@ActivityRetainedScoped
public class SchoolRegistrationFormViewModel extends BaseObservable {
  @Inject
  public SchoolRegistrationFormViewModel(){

  }

  private String schoolName,  schoolAddress, schoolEmail, schoolPhoneNumber, schoolPicturePath, adminFirstname, adminLastname,  adminEmail,  adminPhoneNumber,  password, repeatPassword;
  private boolean schoolNameValid,  schoolAddressValid, schoolEmailValid, schoolPhoneNumberValid,schoolPictureValid, adminFirstnameValid, adminLastnameValid,  adminEmailValid,  adminPhoneNumberValid,  passwordValid, repeatPasswordValid, valid;

  //region Profile Picture
  /**
   * The Profile Picture of school
   * Because we need runtime permission to read file, we will handle opening file dialog from the Fragment and set schoolPicture from there.
   */
  private File schoolPicture;

  @Bindable
  public String getSchoolPicturePath(){
    return schoolPicturePath;
  }

  public File getSchoolPicture() {
    return schoolPicture;
  }

  public void setSchoolPicture(File file){
    schoolPicture = file;
    String path = "";
    boolean valid = false;
    if(file != null){
      path = file.getPath();
      valid = file.exists();
    }
    if(!TextUtils.equals(path, schoolPicturePath)){
      schoolPicturePath = path;
      notifyPropertyChanged(BR.schoolPicturePath);
    }
    if(valid != schoolPictureValid){
      schoolPictureValid = valid;
      notifyPropertyChanged(BR.schoolPictureValid);
      checkValidity(valid);
    }
  }

  @Bindable
  public boolean isSchoolPictureValid(){
    return schoolPictureValid;
  }
  //endregion

  //region School Name
  @Bindable
  public String getSchoolName() {
    return schoolName;
  }

  public void setSchoolName(String schoolName) {
    if(TextUtils.equals(schoolName, this.schoolName)) return;
    this.schoolName = schoolName;
    notifyPropertyChanged(BR.schoolName);
    boolean valid = hasMinLength(schoolName, 10); // TODO Arbitrary min length 10.
    if(valid != schoolNameValid){
      schoolNameValid = valid;
      notifyPropertyChanged(BR.schoolNameValid);
      checkValidity(valid);
    }
  }

  @Bindable
  public boolean isSchoolNameValid() {
    return schoolNameValid;
  }
  //endregion

  //region School Address
  @Bindable
  public String getSchoolAddress() {
    return schoolAddress;
  }

  public void setSchoolAddress(String schoolAddress) {
    if(TextUtils.equals(schoolAddress, this.schoolAddress)) return;
    this.schoolAddress = schoolAddress;
    notifyPropertyChanged(BR.schoolAddress);
    boolean valid = hasMinLength(schoolAddress, 10); //Arbitrary length 10.
    if(valid != schoolAddressValid){
      schoolAddressValid = valid;
      notifyPropertyChanged(BR.schoolAddressValid);
      checkValidity(valid);
    }
  }

  @Bindable
  public boolean isSchoolAddressValid(){
    return isSchoolAddressValid();
  }
  //endregion

  //region School Email
  @Bindable
  public String getSchoolEmail() {
    return schoolEmail;
  }

  public void setSchoolEmail(String schoolEmail) {
    if(TextUtils.equals(schoolEmail, this.schoolEmail)) return;
    this.schoolEmail = schoolEmail;
    notifyPropertyChanged(BR.schoolEmail);
    boolean valid = Patterns.EMAIL_ADDRESS.matcher(schoolEmail).matches();
    if(valid != schoolEmailValid){
      schoolEmailValid = valid;
      notifyPropertyChanged(BR.schoolEmailValid);
      checkValidity(valid);
    }
  }

  @Bindable
  public boolean isSchoolEmailValid(){
    return schoolEmailValid;
  }
  //endregion

  //region School Phone Number
  @Bindable
  public String getSchoolPhoneNumber() {
    return schoolPhoneNumber;
  }

  public void setSchoolPhoneNumber(String schoolPhoneNumber) {
    if(TextUtils.equals(schoolPhoneNumber, this.schoolPhoneNumber)) return;
    this.schoolPhoneNumber = schoolPhoneNumber;
    notifyPropertyChanged(BR.schoolPhoneNumber);
    boolean valid = Patterns.PHONE.matcher(schoolPhoneNumber).matches();
    if(valid != schoolPhoneNumberValid){
      schoolPhoneNumberValid = valid;
      notifyPropertyChanged(BR.schoolPhoneNumberValid);
      checkValidity(valid);
    }
  }

  @Bindable
  public boolean isSchoolPhoneNumberValid(){
    return schoolPhoneNumberValid;
  }
  //endregion

  //region Admin Firstname
  @Bindable
  public String getAdminFirstname() {
    return adminFirstname;
  }

  public void setAdminFirstname(String adminFirstname) {
    if(TextUtils.equals(adminFirstname, this.adminFirstname)) return;
    this.adminFirstname = adminFirstname;
    notifyPropertyChanged(BR.adminFirstname);
    boolean valid = hasMinLength(adminFirstname, 3); //TODO: Arbitrary min length 3.
    if(valid != adminFirstnameValid){
      adminFirstnameValid = valid;
      notifyPropertyChanged(BR.adminFirstnameValid);
    }
  }

  @Bindable
  public boolean isAdminFirstnameValid(){
    return adminFirstnameValid;
  }
  //endregion

  //region AdminLastname
  @Bindable
  public String getAdminLastname() {
    return adminLastname;
  }

  public void setAdminLastname(String adminLastname) {
    if(TextUtils.equals(adminLastname, this.adminLastname)) return;
    this.adminLastname = adminLastname;
    notifyPropertyChanged(BR.adminLastname);
    boolean valid = hasMinLength(adminLastname, 3); //TODO: Arbitrary min length 3.
    if(valid != adminLastnameValid){
      adminLastnameValid = valid;
      notifyPropertyChanged(BR.adminLastnameValid);
    }
  }

  @Bindable
  public boolean isAdminLastnameValid(){
    return adminLastnameValid;
  }
  //endregion

  //region Admin Email
  @Bindable
  public String getAdminEmail() {
    return adminEmail;
  }

  public void setAdminEmail(String adminEmail) {
    if(TextUtils.equals(adminEmail, this.adminEmail)) return;
    this.adminEmail = adminEmail;
    notifyPropertyChanged(BR.adminEmail);
    boolean valid = Patterns.EMAIL_ADDRESS.matcher(adminEmail).matches();
    if(valid != adminEmailValid){
      adminEmailValid = valid;
      notifyPropertyChanged(BR.adminEmailValid);
      checkValidity(valid);
    }
  }

  @Bindable
  public boolean isAdminEmailValid(){
    return adminEmailValid;
  }
  //endregion

  //region Admin Phone Number
  @Bindable
  public String getAdminPhoneNumber() {
    return adminPhoneNumber;
  }

  public void setAdminPhoneNumber(String adminPhoneNumber) {
    if(TextUtils.equals(adminPhoneNumber, this.adminPhoneNumber)) return;
    this.adminPhoneNumber = adminPhoneNumber;
    notifyPropertyChanged(BR.adminPhoneNumber);
    boolean valid = Patterns.PHONE.matcher(adminPhoneNumber).matches();
    if(valid != adminPhoneNumberValid){
      adminPhoneNumberValid = valid;
      notifyPropertyChanged(BR.adminPhoneNumberValid);
    }
  }

  @Bindable
  public boolean isAdminPhoneNumberValid(){
    return adminPhoneNumberValid;
  }
  //endregion

  //region Admin Password
  @Bindable
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    if(TextUtils.equals(password, this.password)) return;
    this.password = password;
    notifyPropertyChanged(BR.password);
    boolean valid = hasMinLength(password, 6); //TODO: Arbitrary min length 6.
    if(valid != passwordValid){
      passwordValid = valid;
      notifyPropertyChanged(BR.passwordValid);
      checkValidity(valid);
    }
  }

  @Bindable
  public boolean isPasswordValid(){
    return passwordValid;
  }
  //endregion

  //region Repeat Password
  @Bindable
  public String getRepeatPassword() {
    return repeatPassword;
  }

  public void setRepeatPassword(String repeatPassword) {
    if(TextUtils.equals(repeatPassword, this.repeatPassword)) return;
    this.repeatPassword = repeatPassword;
    notifyPropertyChanged(BR.repeatPassword);
    boolean valid = TextUtils.equals(password, repeatPassword);
    if(valid != this.repeatPasswordValid){
      this.repeatPasswordValid = valid;
      notifyPropertyChanged(BR.repeatPasswordValid);
      checkValidity(valid);
    }
  }

  @Bindable
  public boolean isRepeatPasswordValid(){
    return repeatPasswordValid;
  }
  //endregion

  //region Valid
  @Bindable
  public boolean isValid(){
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
      return;
    }
    setValid(schoolNameValid &&  schoolAddressValid && schoolEmailValid && schoolPhoneNumberValid && schoolPictureValid &&
        //adminFirstnameValid && adminLastnameValid &&  adminPhoneNumberValid &&
        adminEmailValid &&  passwordValid && repeatPasswordValid);
  }
  //endregion

  /**
   * Verify that a string has given minimum length
   * @param text input text to verify
   * @param length minimum length required
   * @return false if @param text is null or it's length is less than @param length. true otherwise.
   */
  private boolean hasMinLength(String text, int length){
    if(TextUtils.isEmpty(text)) return false;
    return text.trim().length() >= length;
  }
}