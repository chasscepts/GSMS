package com.chass.gsms.models;

import com.chass.gsms.enums.Roles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parent extends User {
  private static final String CLASSES = "children_ids";
  private int[] childrenIds;

  public Parent(String firstname, String lastname, String email, String phoneNumber, int[] ids) {
    super(firstname, lastname, email, phoneNumber, Roles.PARENT);
    this.childrenIds = ids;
  }

  public boolean isChild(int id){
    for (int childrenId : childrenIds) {
      if (id == childrenId) {
        return true;
      }
    }
    return false;
  }

  public static Parent parse(String text){
    try {
      JSONObject obj = new JSONObject(text);
      int[] childrenIds;
      JSONArray classesArray = obj.getJSONArray(CLASSES);
      int length = classesArray.length();
      if(length > 0){
        childrenIds = new int[length];
        for(int i = 0; i < length; i++){
          childrenIds[i] = classesArray.getInt(i);
        }
      }
      else {
        childrenIds = new int[]{};
      }
      return new Parent(
          obj.getString(FIRSTNAME),
          obj.getString(LASTNAME),
          obj.getString(EMAIL),
          obj.getString(PHONE_NUMBER),
          childrenIds
      );
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
