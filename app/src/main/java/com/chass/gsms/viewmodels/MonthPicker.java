package com.chass.gsms.viewmodels;

import android.text.TextUtils;

import androidx.databinding.Bindable;

import com.chass.gsms.BR;
import com.chass.gsms.interfaces.IMonthSelectedListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

public class MonthPicker extends BaseFormViewModel {
  @Inject
  public MonthPicker(){
    setThisMonth();
  }

  IMonthSelectedListener listener;

  public void setOnMonthSelectedListener(IMonthSelectedListener listener){
    this.listener = listener;
  }

  private int month, year, tempYear;

  private boolean open, valid;

  private String yearText, firstDay, lastDay;

  @Bindable
  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  @Bindable
  public String getYearText(){
    return yearText;
  }

  public void setYearText(String year){
    if(TextUtils.equals(year, this.yearText)) return;
    try{
      int x = Integer.parseInt(year);
      tempYear = x;
      notifyPropertyChanged(BR.yearText);
    }
    catch (Exception ex){
      setValid(false);
    }
  }

  public int getYear() {
    return year;
  }

  public String getFirstDay(){
    return firstDay;
  }

  public String getLastDay(){
    return lastDay;
  }

  @Bindable
  public boolean isValid(){
    return valid;
  }

  private void setValid(boolean valid){
    if(valid == this.valid) return;
    this.valid = valid;
    notifyPropertyChanged(BR.valid);
  }

  @Bindable
  public boolean isOpen(){
    return open;
  }

  public void setOpen(boolean open){
    if(open == this.open) return;
    this.open = open;
    notifyPropertyChanged(BR.open);
  }

  public void revert(){
    setYearText(Integer.toString(year));
    setOpen(false);
  }

  public void select(){
    setOpen(false);
    year =  tempYear;
    setRange();
    if(this.listener != null){
      this.listener.onMonthSelected(this);
    }
  }

  private void setThisMonth(){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    year = calendar.get(Calendar.YEAR);
    setMonth(calendar.get(Calendar.MONTH) + 1);
    setYearText(Integer.toString(year));
    setRange();
  }

  private void setRange(){
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month - 1, 1);
    int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    firstDay = formatter.format(calendar.getTime());
    calendar.set(Calendar.DAY_OF_MONTH, days);
    lastDay = formatter.format(calendar.getTime());
  }
}
