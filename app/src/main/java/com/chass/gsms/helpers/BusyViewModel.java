package com.chass.gsms.helpers;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.chass.gsms.BR;
import com.chass.gsms.R;

public class BusyViewModel extends BaseObservable {
  private static final String DEFAULT_BUSY_MESSAGE = "Application Busy. Please wait ...";
  private String message;

  @Bindable
  public String getMessage(){
    return message;
  }

  public void on(){
    on(DEFAULT_BUSY_MESSAGE);
  }

  public void on(String msg){
    message = message;
    notifyPropertyChanged(BR.message);
  }

  public void off(){
    message = null;
    notifyPropertyChanged(BR.message);
  }

  @BindingAdapter("busy")
  public static void setBusy(ViewGroup container, String message){
    if(message == null){
      container.setVisibility(View.GONE);
      return;
    }
    container.setVisibility(View.VISIBLE);
    TextView tv = container.findViewById(R.id.busy_text);
    if(tv != null){
      tv.setText(message);
    }
  }
}
