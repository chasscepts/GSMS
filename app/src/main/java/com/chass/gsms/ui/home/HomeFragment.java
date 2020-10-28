package com.chass.gsms.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.chass.gsms.R;
import com.chass.gsms.databinding.FragmentHomeBinding;
import com.chass.gsms.helpers.OnSwipeTouchListener;
import com.chass.gsms.interfaces.ILogger;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
  private static final String TAG = "HomeFragment";

  @Inject
  ILogger logger;

  private static final int CALL_PHONE = 9;
  private FragmentHomeBinding B;
  private HomeViewModel viewModel;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    B = FragmentHomeBinding.inflate(inflater, container, false);
    viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    B.homeBody.setOnTouchListener(new OnSwipeTouchListener(requireContext()){
      @Override
      public void onSwipeLeft() {
        NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.ClassListFragment);
      }
    });
    B.txtEmail.setOnClickListener(view1 -> send());
    B.txtPhoneNumber.setOnClickListener(view1 -> makeCallOrRequestCallPhonePermissionIfNotGranted());
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(requestCode == CALL_PHONE){
      if (permissions[0].equals(Manifest.permission.CALL_PHONE)
          && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        makeCall(viewModel.getSessionManager().getSchool().getPhoneNumber());
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

  private void send(){
    send(viewModel.getSessionManager().getSchool().getEmail());
  }

  private void makeCallOrRequestCallPhonePermissionIfNotGranted(){
    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED) {
      requestPermissions(new String[] {Manifest.permission.CALL_PHONE}, CALL_PHONE);
    }
    else {
      makeCall(viewModel.getSessionManager().getSchool().getPhoneNumber());
    }
  }
}
