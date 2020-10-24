package com.chass.gsms.helpers;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BindingAdapters {
  private BindingAdapters(){}

  @BindingAdapter("adapter")
  public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter){
    recyclerView.setAdapter(adapter);
  }
}
