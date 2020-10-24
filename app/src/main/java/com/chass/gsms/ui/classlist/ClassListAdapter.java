package com.chass.gsms.ui.classlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chass.gsms.databinding.ClassItemViewBinding;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.viewmodels.ClassViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassViewHolder> {

  private List<String> classNames;

  @Inject
  public ClassListAdapter(SessionManager sessionManager){
    classNames = new ArrayList<>(Arrays.asList(sessionManager.getSchool().getClasses()));
  }

  public void addClass(String className){
    classNames.add(className);
    notifyClassAdded();
  }

  /**
   * This method is here just to make it possible to verify that notifyItemInserted is being called.
   */
  public void notifyClassAdded(){
    notifyItemInserted(classNames.size() - 1);
  }

  @NonNull
  @Override
  public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ClassViewHolder(ClassItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
    holder.bind(classNames.get(position));
  }

  @Override
  public int getItemCount() {
    return classNames.size();
  }

  public static class ClassViewHolder extends RecyclerView.ViewHolder{
    ClassItemViewBinding B;

    public ClassViewHolder(ClassItemViewBinding binding) {
      super(binding.getRoot());
      B = binding;
    }

    public void bind(String className){
      B.setViewModel(new ClassViewModel(className));
    }
  }
}
