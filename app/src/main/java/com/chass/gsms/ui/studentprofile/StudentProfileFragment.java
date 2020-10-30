package com.chass.gsms.ui.studentprofile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chass.gsms.databinding.FragmentStudentProfileBinding;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.helpers.SharedDataStore;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.User;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StudentProfileFragment extends Fragment {
  private static final int CALL_PHONE = 11;
  private static final String TAG = "StudentProfileFragment";
  private FragmentStudentProfileBinding B;

  private String phone;

  @Inject
  SessionManager sessionManager;

  @Inject
  ILogger logger;

  @Inject
  SharedDataStore dataStore;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    B = FragmentStudentProfileBinding.inflate(inflater, container, false);
    StudentProfileViewModel viewModel = new ViewModelProvider(this).get(StudentProfileViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupParentsContacts();
  }

  private void setupParentsContacts() {
    User parent1 = dataStore.getCurrentStudent().getParent1();
    User parent2 = dataStore.getCurrentStudent().getParent1();
    if(parent1 != null){
      B.txtParentEmail.setOnClickListener(view -> send(parent1.getEmail()));
      B.txtParentPhoneNumber.setOnClickListener(view -> makeCallOrRequestCallPhonePermissionIfNotGranted(parent1.getPhoneNumber()));
    }
    if(parent2 != null){
      B.txtParent2Email.setOnClickListener(view -> send(parent2.getEmail()));
      B.txtParent2PhoneNumber.setOnClickListener(view -> makeCallOrRequestCallPhonePermissionIfNotGranted(parent2.getPhoneNumber()));
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(requestCode == CALL_PHONE){
      if (permissions[0].equals(Manifest.permission.CALL_PHONE)
          && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        makeCall(phone);
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
      logger.print(TAG, e);
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

  private void makeCallOrRequestCallPhonePermissionIfNotGranted(String phone){
    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED) {
      this.phone = phone;
      requestPermissions(new String[] {Manifest.permission.CALL_PHONE}, CALL_PHONE);
    }
    else {
      makeCall(phone);
    }
  }
}