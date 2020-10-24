package com.chass.gsms.ui.attendance;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chass.gsms.databinding.StudentAttendanceStatusViewBinding;
import com.chass.gsms.viewmodels.StudentAttendanceStatusViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class ClassAttendanceAdapter extends RecyclerView.Adapter<ClassAttendanceAdapter.AttendanceStatusViewHolder> {
  private List<StudentAttendanceStatusViewModel> attendanceStatuses;

  @Inject
  public ClassAttendanceAdapter(){
    attendanceStatuses = new ArrayList<>();   //Avoid null pointer
  }

  @NonNull
  @Override
  public AttendanceStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new AttendanceStatusViewHolder(StudentAttendanceStatusViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull AttendanceStatusViewHolder holder, int position) {
    holder.bind(attendanceStatuses.get(position));
  }

  @Override
  public int getItemCount() {
    return attendanceStatuses.size();
  }

  public void loadStatuses(StudentAttendanceStatusViewModel[] statuses){
    this.attendanceStatuses = new ArrayList<>(Arrays.asList(statuses));
    notifyStatusChanged();
  }

  //Wrapper For Testing Purposes
  public void notifyStatusChanged() {
    notifyDataSetChanged();
  }

  public static class AttendanceStatusViewHolder extends RecyclerView.ViewHolder{
    StudentAttendanceStatusViewBinding B;

    public AttendanceStatusViewHolder(StudentAttendanceStatusViewBinding binding){
      super(binding.getRoot());
      B = binding;
    }

    public void bind(StudentAttendanceStatusViewModel attendanceStatus){
      B.setViewModel(attendanceStatus);
    }
  }
}
