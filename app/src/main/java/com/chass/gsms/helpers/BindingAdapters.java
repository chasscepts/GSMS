package com.chass.gsms.helpers;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.chass.gsms.R;
import com.squareup.picasso.Picasso;

public class BindingAdapters {
  private BindingAdapters(){}

  @BindingAdapter("adapter")
  public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter){
    recyclerView.setAdapter(adapter);
  }

  @BindingAdapter("checked")
  public static void setChecked(ImageView imageView, boolean checked){
    imageView.setImageResource(checked? R.drawable.ic_baseline_check_box_24 : R.drawable.ic_baseline_check_box_outline_blank_24);
  }

  @BindingAdapter({"firstname", "lastname"})
  public static void setFullname(TextView tv, String firstname, String lastname){
    if(firstname == null){
      firstname = "";
    }
    if(lastname == null){
      lastname = "";
    }
    tv.setText(tv.getContext().getString(R.string.full_name, lastname, firstname));
  }

  @BindingAdapter({"imageUrl", "imagePlaceholder"})
  public static void loadImage(ImageView imageView, String url, int placeholderId){
    Picasso.get()
        .load(url)
        .placeholder(placeholderId)
        .into(imageView);
  }

  @BindingAdapter({"imageUrl", "imagePlaceholder"})
  public static void loadImage(ImageView imageView, String url, Drawable placeholderId){
    Picasso.get()
        .load(url)
        .placeholder(placeholderId)
        .into(imageView);
  }

  @BindingAdapter("studentsCount")
  public static void setStudentsCount(TextView tv, int count){
    tv.setText(tv.getContext().getString(R.string.students_count, count));
  }

  @SuppressLint("SetTextI18n")
  @BindingAdapter("number")
  public static void setText(TextView tv, int number){
    try{
      tv.setText(Integer.toString(number));
    }
    catch (Exception ignore){}
  }

  @BindingAdapter("attendanceStatusDisplay")
  public static void setAttendanceStatus(ImageView imageView, String status){
    if("P".equals(status)){
      imageView.setImageResource(R.drawable.ic_baseline_check_box_24);
    }
    else if("A".equals(status)){
      imageView.setImageResource(R.drawable.ic_baseline_close_24);
    }
    else {
      imageView.setImageResource(android.R.color.transparent);
    }
  }
}
