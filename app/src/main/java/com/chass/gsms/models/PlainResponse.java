package com.chass.gsms.models;

/**
 * Class to hold simple text response from the server
 * Requests that do not return models from the server should return a PlainResponse object {code: code, message, message}
 */
public class PlainResponse {
  private int code;
  private String message;

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public PlainResponse(){

  }
}
