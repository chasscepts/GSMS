package com.chass.gsms.ui.classlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chass.gsms.databinding.ClassItemViewBinding;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.models.ClassSummary;
import com.chass.gsms.viewmodels.ClassViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassViewHolder> {

  ClassSummary[] classSummaries;

  private final SessionManager sessionManager;

  private final ClassSelectedListener listener;

  public ClassSelectedListener getListener(){
    return listener;
  }

  @Inject
  public ClassListAdapter(SessionManager sessionManager, ClassSelectedListener listener){
    this.sessionManager = sessionManager;
    this.listener = listener;
    classSummaries = sessionManager.getSchool().getClassSummaries();
  }

  public void reload(){
    classSummaries = sessionManager.getSchool().getClassSummaries();
    notifyClassListChanged();
  }

  /**
   * This method is here just to make it possible to verify that notifyItemInserted is being called.
   */
  public void notifyClassListChanged(){
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ClassViewHolder(ClassItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), listener);
  }

  @Override
  public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
    holder.bind(classSummaries[position]);
  }

  @Override
  public int getItemCount() {
    return classSummaries.length;
  }

  public static class ClassViewHolder extends RecyclerView.ViewHolder{
    ClassItemViewBinding B;
    ClassSelectedListener listener;

    public ClassViewHolder(ClassItemViewBinding binding, ClassSelectedListener listener) {
      super(binding.getRoot());
      B = binding;
      this.listener = listener;
    }

    public void bind(ClassSummary classSummary){
      B.setViewModel(new ClassViewModel(classSummary, listener));
    }
  }
}
