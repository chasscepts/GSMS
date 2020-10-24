package com.chass.gsms.viewmodels;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

/**
 * A base class to provide validation and other helper methods for all ViewModels
 */
public class BaseFormViewModel extends BaseObservable {
  /**
   * Provides validation for name fields
   * Obviously null or empty text is not a valid name.
   * I have not heard a single character name.
   * 2 character names like Ed are short forms but like Tim, Tom e.t.c people are adopting them as standard.
   * So we will go with non empty texts of at least 2 characters.
   * @param name name to validate
   * @return true if @name is a valid name, false otherwise
   */
  protected boolean isValidName(String name){
    return !TextUtils.isEmpty(name) && name.trim().length() >= 2;
  }

  /**
   * Provides validation for email address.
   * We use android.util EMAIL_ADDRESS pattern to validate emails
   * @param email the text to validate
   * @return true if email matches email address pattern false otherwise
   */
  protected boolean isValidEmail(String email){
    return Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  /**
   * Provides validation for phone numbers.
   * We use android.util PHONE pattern to validate phone number
   * @param phoneNumber the text to validate
   * @return true if phoneNumber matches phone pattern false otherwise
   */
  protected boolean isValidPhoneNumber(String phoneNumber){
    return Patterns.PHONE.matcher(phoneNumber).matches();
  }

  /**
   * Validates if a text has at least given number of characters.
   * Trailing white spaces do not contribute to number of characters.
   * @param text to validate
   * @param minLength minimum number of characters @text will have to be valid
   * @return true if @text has at least @minLength number of characters
   */
  protected boolean hasMinLength(String text, int minLength){
    return !TextUtils.isEmpty(text) && text.trim().length() >= minLength;
  }

  //region Integer
  /**
   * Provides two way integer binding to EditText
   * This implementation has an issue because it resets the value to zero when an invalid number is entered.
   */
  @BindingAdapter("number")
  public static void setText(TextView view, int value) {
    String newText = Integer.toString(value);
    String oldText = view.getText().toString();
    //Handle cursor moving out of position
    if(TextUtils.equals(newText, oldText)) return;
    //Handle situation where someone is trying to enter negative number and the field is being reset to zero.
    if(value == 0 && "-".equals(oldText)) return;
    view.setText(newText);
  }

  @InverseBindingAdapter(attribute = "number")
  public static int getText(TextView view) {
    try{
      return Integer.parseInt(view.getText().toString());
    }
    catch (Exception ignored){}
    //We should return the last known valid number, but we don't know it.
    return 0;
  }
  //endregion
}