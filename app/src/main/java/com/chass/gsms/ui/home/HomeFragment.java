package com.chass.gsms.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.chass.gsms.R;
import com.chass.gsms.databinding.FragmentHomeBinding;
import com.chass.gsms.helpers.OnSwipeTouchListener;
import com.chass.gsms.ui.login.LoginFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

  private FragmentHomeBinding B;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    B = FragmentHomeBinding.inflate(inflater, container, false);
    HomeViewModel viewModel = new ViewModelProvider(getViewModelStore(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(HomeViewModel.class);
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
  }
}
