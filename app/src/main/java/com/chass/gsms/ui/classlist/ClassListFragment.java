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
import com.chass.gsms.helpers.SharedDataStore;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ClassListFragment extends Fragment {
  @Inject
  ClassSelectedListener classSelectedListener;

  @Inject
  SharedDataStore dataStore;

  FragmentClassListBinding B;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    B = FragmentClassListBinding.inflate(inflater, container, false);
    ClassListViewModel viewModel = new ViewModelProvider(this).get(ClassListViewModel.class);
    viewModel.getAdapter().reload();
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    B.addClassBtn.setOnClickListener(view1 -> NavHostFragment.findNavController(ClassListFragment.this).navigate(R.id.NewClassFragment));
    setupClassSelectedListener();
  }

  private void setupClassSelectedListener() {
    classSelectedListener.getSelectedClassSummary().observe(getViewLifecycleOwner(), classSummary -> {
      if(classSummary != null){
        classSelectedListener.onClassSelected(null);
        dataStore.setSelectedClassSummary(classSummary);
        NavHostFragment.findNavController(this).navigate(R.id.ClassDetailsFragment);
      }
    });
  }
}