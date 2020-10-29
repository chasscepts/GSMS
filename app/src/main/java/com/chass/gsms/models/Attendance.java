package com.chass.gsms.models;

import com.chass.gsms.enums.AttendanceStatus;

import java.util.Date;

public class Attendance {
  private int studentId;
  private AttendanceStatus status;

  public String toJson(){
    StringBuilder sb = new StringBuilder();
    sb.append("{\"studentId\":");
    sb.append(studentId);
    sb.append(",\"status\":\"");
    sb.append(AttendanceStatus.toChar(status));
    sb.append("\"}");
    return sb.toString();
  }
}
