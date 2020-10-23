package com.chass.gsms;

import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.models.School;
import com.chass.gsms.ui.classlist.ClassListAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ClassListAdapterTest {

  String[] classes = {"S S 1 A", "S S 1 B", "S S 2 A", "S S 2 B", "S S 2 C"};

  @Mock
  School school;

  @Mock
  SessionManager sessionManager;

  @Mock
  ClassListAdapter adapter;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
    when(school.getClasses()).thenReturn(classes);
    when(sessionManager.getSchool()).thenReturn(school);
  }

  @Test
  public void AdapterIsWellConfiguredWithClasses(){
    ClassListAdapter adapter = new ClassListAdapter(sessionManager);
    assertEquals(classes.length, adapter.getItemCount());
  }

  @Test
  public void AddClassCorrectAddsItemToListAndNotifiesObservers(){
    adapter = spy(new ClassListAdapter(sessionManager));
    int position = classes.length;
    doNothing().when(adapter).notifyClassAdded();
    adapter.addClass("S S 3 A");
    verify(adapter).notifyClassAdded();
    assertEquals(position + 1, adapter.getItemCount());
  }
}
