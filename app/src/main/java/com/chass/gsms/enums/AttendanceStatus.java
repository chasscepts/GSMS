package com.chass.gsms.enums;

public enum AttendanceStatus {
  P, A, N;

  public static AttendanceStatus fromChar(char c){
    if(c == 'P') return P;
    if(c == 'A') return A;
    return N;
  }

  public static AttendanceStatus fromString(String c){
    if("P".equals(c)) return P;
    if("A".equals(c)) return A;
    return N;
  }

  public static char toChar(AttendanceStatus status){
    if(status == P) return 'P';
    if(status == A) return 'A';
    return 'N';
  }
}
