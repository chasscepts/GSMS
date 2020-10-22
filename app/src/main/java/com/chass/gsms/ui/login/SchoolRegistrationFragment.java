package com.chass.gsms.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chass.gsms.databinding.FragmentSchoolRegistrationBinding;

public class SchoolRegistrationFragment extends Fragment {
  FragmentSchoolRegistrationBinding B;
  private SchoolRegistrationViewModel viewModel;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    B = FragmentSchoolRegistrationBinding.inflate(inflater, container, false);

    //We scope ViewModel to the Activity to retain user input after navigating to another fragment
    viewModel = new ViewModelProvider(requireActivity()).get(SchoolRegistrationViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //B.buttonSecond.setOnClickListener(view1 -> NavHostFragment.findNavController(SchoolRegistrationFragment.this).navigate(R.id.action_Second2Fragment_to_First2Fragment));
  }
}