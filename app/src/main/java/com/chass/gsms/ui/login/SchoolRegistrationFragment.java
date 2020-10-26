package com.chass.gsms.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.chass.gsms.R;
import com.chass.gsms.databinding.FragmentSchoolRegistrationBinding;

import java.io.File;
import java.net.URI;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SchoolRegistrationFragment extends Fragment {
  private static final int CHOOSE_FILE_RESULT_CODE = 5;
  private static final int READ_FILE_PERMISSION_CODE = 7;

  FragmentSchoolRegistrationBinding B;
  private SchoolRegistrationViewModel viewModel;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    B = FragmentSchoolRegistrationBinding.inflate(inflater, container, false);

    //We scope all ViewModels to the Fragment and share data with SharedDataStore
    viewModel = new ViewModelProvider(getViewModelStore(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(SchoolRegistrationViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    B.btnLoginRegister.setOnClickListener(view1 -> NavHostFragment.findNavController(SchoolRegistrationFragment.this).navigate(R.id.LoginFragment));
    B.edtTxtSchoolProfilePicture.setOnClickListener(view1 -> {
      openFileDialog();
    });
    B.btnRegister.setOnClickListener(view1 -> {
      registerOrRequestFilePermissionIfNotGranted();
    });
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(requestCode == READ_FILE_PERMISSION_CODE){
      if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)
          && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        viewModel.register();
      }
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(requestCode == CHOOSE_FILE_RESULT_CODE){
      if (resultCode == -1) {
        Uri uri = data.getData();
        if(uri != null){
          viewModel.getFormViewModel().setSchoolPicture(new File(URI.create(uri.getPath())));
        }
      }
    }
  }

  private void registerOrRequestFilePermissionIfNotGranted(){
    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_FILE_PERMISSION_CODE);
    }
    else {
      viewModel.register();
    }
  }

  private void openFileDialog() {
    Intent chooseFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
    chooseFileIntent.setType("*/*");
    chooseFileIntent = Intent.createChooser(chooseFileIntent, "Choose Lecture File");
    startActivityForResult(chooseFileIntent, CHOOSE_FILE_RESULT_CODE);
  }
}