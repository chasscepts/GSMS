package com.chass.gsms.ui.studentprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chass.gsms.databinding.FragmentStudentProfileBinding;

public class StudentProfileFragment extends Fragment {
  FragmentStudentProfileBinding B;
  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState
  ) {
    // Inflate the layout for this fragment
    B = FragmentStudentProfileBinding.inflate(inflater, container, false);
    StudentProfileViewModel viewModel = new ViewModelProvider(requireActivity()).get(StudentProfileViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
}