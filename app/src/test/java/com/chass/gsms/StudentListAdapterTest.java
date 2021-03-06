package com.chass.gsms;

import com.chass.gsms.models.Student;
import com.chass.gsms.ui.classdetails.StudentListAdapter;
import com.chass.gsms.ui.classdetails.StudentSelectedListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class StudentListAdapterTest {
  @Mock
  Student student1;

  @Mock
  Student student2;

  @Mock
  Student student3;

  @Mock
  Student student4;

  @Mock
  StudentListAdapter adapter;

  @Mock
  StudentSelectedListener listener;



  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void loadStudentsCorrectAddsStudentsToListAndNotifiesObservers(){
    //Student[] students = {student1, student2, student3, student4};
    //adapter = spy(new StudentListAdapter());
    //int length = students.length;
    //doNothing().when(adapter).notifyStudentsChanged();
    //adapter.loadStudents(students);
    //verify(adapter).notifyStudentsChanged();
    //assertEquals(length, adapter.getItemCount());
  }
}
