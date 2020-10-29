package com.chass.gsms.ui.studentprofile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chass.gsms.databinding.AttendanceStatusViewBinding;

import javax.inject.Inject;

public class AttendanceStatusAdapter extends RecyclerView.Adapter<AttendanceStatusAdapter.AttendanceStatusViewHolder> {
  private String[] statuses;

  @Inject
  public AttendanceStatusAdapter(){
    statuses = new String[]{};
  }

  public String[] getStatuses(){
    return statuses;
  }

  public void setStatuses(String[] statuses){
    this.statuses = statuses;
    notifyStatusesChanged();
  }

  /**
   * For Testing
   */
  public void notifyStatusesChanged(){
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public AttendanceStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new AttendanceStatusViewHolder(AttendanceStatusViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull AttendanceStatusViewHolder holder, int position) {
    holder.bind(statuses[position]);
  }

  @Override
  public int getItemCount() {
    return statuses.length;
  }

  public static class AttendanceStatusViewHolder extends RecyclerView.ViewHolder {
    private AttendanceStatusViewBinding B;

    public AttendanceStatusViewHolder(AttendanceStatusViewBinding binding){
      super(binding.getRoot());
      B = binding;
    }

    public void bind(String status){
      B.setStatus(status);
    }
  }
}