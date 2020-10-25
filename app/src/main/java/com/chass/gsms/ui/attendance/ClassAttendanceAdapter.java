package com.chass.gsms.ui.attendance;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chass.gsms.databinding.StudentAttendanceStatusViewBinding;
import com.chass.gsms.models.Student;
import com.chass.gsms.viewmodels.StudentAttendanceStatusViewModel;

import javax.inject.Inject;

public class ClassAttendanceAdapter extends RecyclerView.Adapter<ClassAttendanceAdapter.AttendanceStatusViewHolder> {
  private StudentAttendanceStatusViewModel[] attendanceStatuses = {};

  public StudentAttendanceStatusViewModel[] getAttendanceStatuses(){
    return attendanceStatuses;
  }

  @Inject
  public ClassAttendanceAdapter(){

  }

  @NonNull
  @Override
  public AttendanceStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new AttendanceStatusViewHolder(StudentAttendanceStatusViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull AttendanceStatusViewHolder holder, int position) {
    holder.bind(attendanceStatuses[position]);
  }

  @Override
  public int getItemCount() {
    return attendanceStatuses.length;
  }

  public void loadStudents(Student[] students){
    if(students == null) return;
    int length = students.length;
    attendanceStatuses = new StudentAttendanceStatusViewModel[length];
    for(int i = 0; i < length; i++){
      attendanceStatuses[i] = new StudentAttendanceStatusViewModel(students[i]);
    }
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
