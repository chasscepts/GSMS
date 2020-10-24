package com.chass.gsms.viewmodels;

import androidx.databinding.ObservableField;

public class AttendanceStatusViewModel {
  public enum statuses { PRESENT, ABSENT, NONE }

  public final ObservableField<statuses> status = new ObservableField<>(statuses.NONE);

  public void setPresent(){
    status.set(statuses.PRESENT);
  }

  public void setAbsent(){
    status.set(statuses.ABSENT);
  }

  public void clear(){
    status.set(statuses.NONE);
  }

  public static void setStatus(){

  }
}
