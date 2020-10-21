package com.chass.gsms.models;

/**
 * A container for server response after successful login or registration.
 * The purpose is for retrofit to parse the response for us.
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
