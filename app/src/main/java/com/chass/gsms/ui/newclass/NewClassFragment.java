package com.chass.gsms.ui.newclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chass.gsms.databinding.FragmentNewClassBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewClassFragment extends Fragment {
  FragmentNewClassBinding B;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    B = FragmentNewClassBinding.inflate(inflater, container, false);
    NewClassViewModel viewModel = new ViewModelProvider(this).get(NewClassViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
}