package com.chass.gsms;

import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.models.School;
import com.chass.gsms.models.Student;
import com.chass.gsms.ui.attendance.ClassAttendanceAdapter;
import com.chass.gsms.ui.classdetails.StudentListAdapter;
import com.chass.gsms.ui.classlist.ClassListAdapter;
import com.chass.gsms.viewmodels.StudentAttendanceStatusViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ClassAttendanceAdapterTest {
  @Mock
  Student student;

  @Mock
  StudentAttendanceStatusViewModel status1;

  @Mock
  StudentAttendanceStatusViewModel status2;

  @Mock
  StudentAttendanceStatusViewModel status3;

  StudentAttendanceStatusViewModel[] statuses = { status1, status2, status3};

  @Mock
  ClassAttendanceAdapter adapter;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void loadStudentsCorrectAddsStudentsToListAndNotifiesObservers(){
    adapter = spy(new ClassAttendanceAdapter());
    int length = statuses.length;
    doNothing().when(adapter).notifyStatusChanged();
    adapter.loadStatuses(statuses);
    verify(adapter).notifyStatusChanged();
    assertEquals(length, adapter.getItemCount());
  }
}
