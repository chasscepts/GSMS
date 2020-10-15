package com.chass.gsms.enums;

public enum Roles {
  ADMIN("admin"),
  TEACHER("teacher"),
  PARENT("parent");

  private final String text;

  Roles(final String text) {
    this.text = text;
  }

  public static Roles fromString(String text){
    switch (text.toLowerCase()){
      case "admin": return ADMIN;
      case "teacher": return TEACHER;
      default: return PARENT;
    }
  }

  public String toString(){
    return text;
  }
}
