package com.chass.gsms.ui.classdetails;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.chass.gsms.R;
import com.chass.gsms.databinding.FragmentClassDetailsBinding;
import com.chass.gsms.models.Class;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ClassDetailsFragment extends Fragment {
  private static final int CALL_PHONE = 9;
  FragmentClassDetailsBinding B;
  private ClassDetailsViewModel viewModel;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    B = FragmentClassDetailsBinding.inflate(inflater, container, false);
    viewModel = new ViewModelProvider(this).get(ClassDetailsViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
    super.onViewCreated(view, savedInstanceState);
    B.btnAddNewStudent.setOnClickListener(view1 -> NavHostFragment.findNavController(this).navigate(R.id.NewStudentFragment));
    B.txtTeacherEmail.setOnClickListener(view1 -> send());
    B.txtTeacherPhoneNum.setOnClickListener(view1 -> makeCallOrRequestCallPhonePermissionIfNotGranted());
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    inflater.inflate(R.menu.attendance_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if(item.getItemId() == R.id.action_take_attendance){
      NavHostFragment.findNavController(this).navigate(R.id.AttendanceFragment);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    Class aClass = viewModel.aClass.get();
    if(aClass == null){
      return;
    }
    if(requestCode == CALL_PHONE){
      if (permissions[0].equals(Manifest.permission.CALL_PHONE)
          && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        makeCall(aClass.getTeacher().getPhoneNumber());
      }
    }
  }

  public void send(String email) {
    try{
      Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
      emailIntent.setData(Uri.parse("mailto:" + email));
      startActivity(emailIntent);
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

  private void makeCall(String number){
    if(TextUtils.isEmpty(number)){
      return;
    }
    if(!number.startsWith("+")){
      //We assume a local number scheme
      if(number.length() == 11){
        //remove first zero from number
        number = number.substring(1);  //This might fail for land lines
      }
      number = "+234" + number;
    }
    number = "tel:" + number;
    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(number)));
  }

  private void send(){
    Class aClass = viewModel.aClass.get();
    if(aClass == null){
      return;
    }
    send(aClass.getTeacher().getEmail());
  }

  private void makeCallOrRequestCallPhonePermissionIfNotGranted(){
    Class aClass = viewModel.aClass.get();
    if(aClass == null){
      return;
    }
    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED) {
      requestPermissions(new String[] {Manifest.permission.CALL_PHONE}, CALL_PHONE);
    }
    else {
      makeCall(aClass.getTeacher().getPhoneNumber());
    }
  }
}