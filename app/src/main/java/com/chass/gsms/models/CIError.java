package com.chass.gsms.models;

import androidx.annotation.Nullable;

import org.json.JSONObject;

/**
 * Encapsulates a Code Igniter Error Message
 */
public class CIError {
  private int status, error;
  private Messages messages;

  public int getStatus() {
    return status;
  }

  public int getError() {
    return error;
  }

  public Messages getMessages() {
    return messages;
  }

  @Nullable
  public static CIError fromJson(String text){
    try {
      JSONObject jsonObject = new JSONObject(text);
      CIError ciError = new CIError();
      ciError.error = jsonObject.getInt("error");
      ciError.status = jsonObject.getInt("status");
      ciError.messages = Messages.fromJson(jsonObject.getString("messages"));
      return ciError;
    }
    catch (Exception ignore){
      ignore.printStackTrace();
    }
    return null;
  }

  public static class Messages{
    private String error;

    public String getError(){
      return error;
    }

    @Nullable
    public static Messages fromJson(String text){
      try{
        JSONObject jsonObject = new JSONObject(text);
        Messages messages = new Messages();
        messages.error = jsonObject.getString("error");
        return messages;
      }
      catch (Exception ignore){
        ignore.printStackTrace();
      }
      return null;
    }
  }
}
