package com.chass.gsms.ui.classlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.chass.gsms.R;
import com.chass.gsms.databinding.FragmentClassListBinding;
import com.chass.gsms.ui.home.HomeFragment;

public class ClassListFragment extends Fragment {
  FragmentClassListBinding B;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    B = FragmentClassListBinding.inflate(inflater, container, false);
    ClassListViewModel viewModel = new ViewModelProvider(getViewModelStore(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(ClassListViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    B.addClassBtn.setOnClickListener(view1 -> {
      NavHostFragment.findNavController(ClassListFragment.this).navigate(R.id.NewClassFragment);
    });
  }
}