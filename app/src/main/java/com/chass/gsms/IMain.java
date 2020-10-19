package com.chass.gsms;

import com.chass.gsms.models.Student;

/**
 * This interface is implemented by MainActivity.
 * We pass context from xml to methods of ViewModels used in binding.
 * By casting the context to IMain we will have access to methods implemented in MainActivity.
 */
public interface IMain {
  void selectClass(String className);
  void selectStudent(Student student);
}
