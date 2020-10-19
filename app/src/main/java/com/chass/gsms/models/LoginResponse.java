package com.chass.gsms.models;

/**
 * A class to hold server response after successful login or registration
 * retrofit will automatically parse the response for us
 */
public class LoginResponse {
  private School school;
  private User user;

  public School getSchool() {
    return school;
  }

  public User getUser() {
    return user;
  }

  public LoginResponse(){

  }
}
