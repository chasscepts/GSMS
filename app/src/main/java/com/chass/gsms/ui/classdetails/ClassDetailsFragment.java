package com.chass.gsms.ui.classdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import com.chass.gsms.R;
import com.chass.gsms.databinding.FragmentClassDetailsBinding;

public class ClassDetailsFragment extends Fragment {
  FragmentClassDetailsBinding B;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    B = FragmentClassDetailsBinding.inflate(inflater, container, false);
    ClassDetailsViewModel viewModel = new ViewModelProvider(getViewModelStore(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(ClassDetailsViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    B.btnAddNewStudent.setOnClickListener(view1 -> NavHostFragment.findNavController(this).navigate(R.id.NewStudentFragment));
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
}