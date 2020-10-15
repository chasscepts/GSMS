package com.chass.gsms.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chass.gsms.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
  FragmentLoginBinding B;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState
  ) {
    // Inflate the layout for this fragment
    B = FragmentLoginBinding.inflate(inflater, container, false);
    //We scope ViewModel to the Activity to retain user input after navigating to another fragment
    LoginViewModel viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //B.buttonFirst.setOnClickListener(view1 -> NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_First2Fragment_to_Second2Fragment));
  }
}