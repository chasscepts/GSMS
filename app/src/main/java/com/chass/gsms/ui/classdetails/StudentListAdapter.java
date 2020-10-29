package com.chass.gsms.ui.classdetails;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chass.gsms.databinding.StudentItemViewBinding;
import com.chass.gsms.helpers.SharedDataStore;
import com.chass.gsms.interfaces.IStudentSelectedListener;
import com.chass.gsms.models.Student;
import com.chass.gsms.viewmodels.StudentViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {
  private final SharedDataStore dataStore;
  private final IStudentSelectedListener listener;
  private Student[] students;

  @Inject
  public StudentListAdapter(SharedDataStore dataStore, IStudentSelectedListener listener){
    this.dataStore = dataStore;
    this.listener = listener;
    students = new Student[]{};
  }

  public void reload(){
    students = dataStore.getCurrentClass().getStudents();
    notifyStudentsChanged();
  }

  /**
   * For testing purposes because we can't mock notifyDataSetChanged() as it is final
   */
  public void notifyStudentsChanged(){
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new StudentViewHolder(StudentItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
    holder.bind(students[position], listener);
  }

  @Override
  public int getItemCount() {
    return students.length;
  }

  public static class StudentViewHolder extends RecyclerView.ViewHolder{
    private StudentItemViewBinding B;

    public StudentViewHolder(StudentItemViewBinding binding){
      super(binding.getRoot());
      B = binding;
    }

    public void bind(Student student, IStudentSelectedListener listener){
      B.setViewModel(new StudentViewModel(student, listener));
    }
  }
}