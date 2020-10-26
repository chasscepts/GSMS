package com.chass.gsms.ui.newstudent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chass.gsms.databinding.FragmentNewStudentBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewStudentFragment extends Fragment {
  FragmentNewStudentBinding B;
  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState
  ) {
    // Inflate the layout for this fragment
    B = FragmentNewStudentBinding.inflate(inflater, container, false);
    NewStudentViewModel viewModel = new ViewModelProvider(getViewModelStore(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(NewStudentViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
}