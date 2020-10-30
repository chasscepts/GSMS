package com.chass.gsms.viewmodels;

import android.text.TextUtils;

import androidx.databinding.Bindable;

import com.chass.gsms.BR;
import com.chass.gsms.interfaces.IMonthSelectedListener;

import java.util.Calendar;
import java.util.Date;

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

  private int month, year, tempYear, daysInMonth, startWeekday;

  private boolean open, valid;

  private String yearText;

  public int getDaysInMonth() {
    return daysInMonth;
  }

  public int getStartWeekday(){
    return startWeekday;
  }

  @Bindable
  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    if(this.month == month) return;
    this.month = month;
    notifyPropertyChanged(BR.month);
  }

  @Bindable
  public String getYearText(){
    return yearText;
  }

  public void setYearText(String year){
    if(TextUtils.equals(year, this.yearText)) return;
    yearText = year;
    try{
      tempYear = Integer.parseInt(year);
      notifyPropertyChanged(BR.yearText);
      setValid(true);
    }
    catch (Exception ex){
      setValid(false);
    }
  }

  public void incrementYear(){
    setYearText(Integer.toString(tempYear + 1));
  }

  public void decrementYear(){
    int year = tempYear - 1;
    if(year > 0){
      setYearText(Integer.toString(year));
    }
  }

  public int getYear() {
    return year;
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
    setFields();
    if(this.listener != null){
      this.listener.onMonthSelected(this);
    }
  }

  private void setThisMonth(){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    year = calendar.get(Calendar.YEAR);
    setMonth(calendar.get(Calendar.MONTH));
    setYearText(Integer.toString(year));
    setFields();
  }

  private void setFields(){
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, 1);
    daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    int day = calendar.get(Calendar.DAY_OF_WEEK);
    switch (day){
      case Calendar.MONDAY:
        startWeekday = 1;
        break;
      case Calendar.TUESDAY:
        startWeekday = 2;
        break;
      case Calendar.WEDNESDAY:
        startWeekday = 3;
        break;
      case Calendar.THURSDAY:
        startWeekday = 4;
        break;
      case Calendar.FRIDAY:
        startWeekday = 5;
        break;
      case Calendar.SATURDAY:
        startWeekday = 6;
        break;
      default:
        startWeekday = 0;
        break;
    }
  }
}
