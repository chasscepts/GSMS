package com.chass.gsms.ui.attendance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chass.gsms.databinding.FragmentAttendanceBinding;

import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AttendanceFragment extends Fragment {
  FragmentAttendanceBinding B;
  private AttendanceViewModel viewModel;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    B = FragmentAttendanceBinding.inflate(inflater, container, false);
    viewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);
    B.setViewModel(viewModel);
    return B.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupDatePicker();
  }

  private void setupDatePicker() {
    final DatePickerDialog.OnDateSetListener dateListener = (view, year, monthOfYear, dayOfMonth) -> viewModel.setDate(dayOfMonth, monthOfYear, year);
    B.dateWrap.setOnClickListener(view -> new DatePickerDialog(requireContext(), dateListener, viewModel.getYear(), viewModel.getMonth(), viewModel.getDay()).show());
  }
}