package com.chass.gsms;

import com.chass.gsms.models.Student;
import com.chass.gsms.ui.attendance.ClassAttendanceAdapter;

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

@RunWith(JUnit4.class)
public class ClassAttendanceAdapterTest {
  @Mock
  Student student1;

  @Mock
  Student student2;

  @Mock
  Student student3;

  Student[] students = { student1, student2, student3};

  @Mock
  ClassAttendanceAdapter adapter;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void loadStudentsCorrectAddsStudentsToListAndNotifiesObservers(){
    adapter = spy(new ClassAttendanceAdapter());
    int length = students.length;
    doNothing().when(adapter).notifyStatusChanged();
    adapter.loadStudents(students);
    verify(adapter).notifyStatusChanged();
    assertEquals(length, adapter.getItemCount());
  }
}
