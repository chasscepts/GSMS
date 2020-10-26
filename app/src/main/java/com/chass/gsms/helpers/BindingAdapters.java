package com.chass.gsms.helpers;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.chass.gsms.R;
import com.chass.gsms.models.Student;

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
  public static void name(TextView tv, String firstname, String lastname){
    tv.setText(tv.getContext().getString(R.string.full_name, lastname, firstname));
  }
}
