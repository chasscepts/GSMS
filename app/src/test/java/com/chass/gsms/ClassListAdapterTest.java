package com.chass.gsms;

import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.models.ClassSummary;
import com.chass.gsms.models.School;
import com.chass.gsms.ui.classlist.ClassListAdapter;
import com.chass.gsms.ui.classlist.ClassSelectedListener;

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
public class ClassListAdapterTest {

  //String[] classes = {"S S 1 A", "S S 1 B", "S S 2 A", "S S 2 B", "S S 2 C"};
  @Mock
  ClassSummary classSummary1;

  @Mock
  ClassSummary classSummary2;

  @Mock
  ClassSummary classSummary3;

  ClassSummary[] classSummaries = { classSummary1, classSummary2 };

  @Mock
  School school;

  @Mock
  SessionManager sessionManager;

  @Mock
  ClassListAdapter adapter;

  @Mock
  ClassSelectedListener classSelectedListener;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
    when(school.getClassSummaries()).thenReturn(classSummaries);
    when(sessionManager.getSchool()).thenReturn(school);
  }

  @Test
  public void AdapterIsWellConfiguredWithClasses(){
    ClassListAdapter adapter = new ClassListAdapter(sessionManager, classSelectedListener);
    assertEquals(classSummaries.length, adapter.getItemCount());
  }

  @Test
  public void AddClassCorrectAddsItemToListAndNotifiesObservers(){
    adapter = spy(new ClassListAdapter(sessionManager, classSelectedListener));
    int length = classSummaries.length;
    doNothing().when(adapter).notifyClassAdded();
    adapter.addClass(classSummary3);
    verify(adapter).notifyClassAdded();
    assertEquals(length + 1, adapter.getItemCount());
  }
}
