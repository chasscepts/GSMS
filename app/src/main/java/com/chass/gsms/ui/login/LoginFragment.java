package com.chass.gsms.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.chass.gsms.R;
import com.chass.gsms.databinding.FragmentLoginBinding;
import com.chass.gsms.viewmodels.LoginFormViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
  FragmentLoginBinding B;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    B = FragmentLoginBinding.inflate(inflater, container, false);
    //We scope ViewModels to the Fragment and and use SharedDataStore to share data
    LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    B.btnCreateNewSchool.setOnClickListener(view1 -> NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.SchoolRegistrationFragment));
  }
}