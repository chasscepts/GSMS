package com.chass.gsms.viewmodels;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.chass.gsms.R;

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

  @BindingAdapter("attendanceStatus")
  public static void setStatus(ImageView img, statuses status){
    if(status == statuses.NONE){
      img.setVisibility(View.GONE);
      return;
    }
    img.setVisibility(View.VISIBLE);
    img.setImageResource(status == statuses.PRESENT? R.drawable.ic_baseline_check_box_24 : R.drawable.ic_baseline_cancel_presentation_24);
  }
}
