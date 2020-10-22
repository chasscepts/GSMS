package android.util;

import java.util.regex.Pattern;

//https://android.googlesource.com/platform/frameworks/base/+/81aa097/core/java/android/util/Patterns.java
public class Patterns {
  public static final Pattern EMAIL_ADDRESS
      = Pattern.compile(
      "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
          "\\@" +
          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
          "(" +
          "\\." +
          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
          ")+"
  );

  /**
   * This pattern is intended for searching for things that look like they
   * might be phone numbers in arbitrary text, not for validating whether
   * something is in fact a phone number.  It will miss many things that
   * are legitimate phone numbers.
   *
   * <p> The pattern matches the following:
   * <ul>
   * <li>Optionally, a + sign followed immediately by one or more digits. Spaces, dots, or dashes
   * may follow.
   * <li>Optionally, sets of digits in parentheses, separated by spaces, dots, or dashes.
   * <li>A string starting and ending with a digit, containing digits, spaces, dots, and/or dashes.
   * </ul>
   */
  public static final Pattern PHONE
      = Pattern.compile(                                  // sdd = space, dot, or dash
      "(\\+[0-9]+[\\- \\.]*)?"                    // +<digits><sdd>*
          + "(\\([0-9]+\\)[\\- \\.]*)?"               // (<digits>)<sdd>*
          + "([0-9][0-9\\- \\.][0-9\\- \\.]+[0-9])"); // <digit><digit|sdd>+<digit>
}
