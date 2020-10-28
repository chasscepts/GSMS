package com.chass.gsms.ui.classdetails;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.helpers.SharedDataStore;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.Class;
import com.chass.gsms.models.ClassSummary;
import com.chass.gsms.repositories.ClassRepository;
import com.chass.gsms.viewmodels.ViewStateViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassDetailsViewModel extends ViewModel {
  private static final String TAG = "ClassDetailsViewModel";
  private SavedStateHandle savedStateHandle;
  private final ClassRepository repository;
  private final SharedDataStore dataStore;
  private final ViewStateViewModel viewState;
  private final ILogger logger;
  private final StudentListAdapter adapter;

  public ViewStateViewModel getViewState() {
    return viewState;
  }

  public final ObservableField<Class> aClass = new ObservableField<>();

  public final ObservableBoolean hasLoadError = new ObservableBoolean();

  public final ObservableInt studentsCount = new ObservableInt();

  public StudentListAdapter getAdapter(){
    return adapter;
  }

  @ViewModelInject
  public ClassDetailsViewModel(@Assisted SavedStateHandle savedStateHandle, ClassRepository repository, SharedDataStore dataStore, ViewStateViewModel viewState, ILogger logger, StudentListAdapter adapter){
    this.savedStateHandle = savedStateHandle;
    this.repository = repository;
    this.dataStore = dataStore;
    this.viewState = viewState;
    this.logger = logger;
    this.adapter = adapter;
    //We setup class and not wait for it to be called from outside to overcome the Fragment reloading a class during configuration changes.
    this.setupClass();
  }

  public void setupClass() {
    ClassSummary classSummary = dataStore.getSelectedClassSummary();
    if(classSummary == null){
      viewState.error("Something terrible has gone wrong. We have searched yet could not find the selected class.");
      return;
    }
    viewState.busy("Loading Class Info.\nPlease wait...");
    hasLoadError.set(false);
    Call<Class> call = repository.getClass(classSummary.getId());
    call.enqueue(new Callback<Class>() {
      @Override
      public void onResponse(@NonNull Call<Class> call, @NonNull Response<Class> response) {
        if(response.isSuccessful()){
          Class bClass = response.body();
          if(bClass != null){
            aClass.set(bClass);
            dataStore.setCurrentClass(bClass);
            repository.cache(bClass);
            adapter.loadStudents(bClass.getStudents());
            viewState.restoreNormalState();
            return;
          }
        }
        else {
          logger.print(TAG, response.errorBody());
        }
        hasLoadError.set(true);
        viewState.responseError("retrieving class details");
      }

      @Override
      public void onFailure(@NonNull Call<Class> call, @NonNull Throwable t) {
        hasLoadError.set(true);
        viewState.connectionError();
        logger.print(TAG, t);
      }
    });
  }

  /**
   * Save the state of this ViewModel
   */
  @Override
  protected void onCleared() {
    super.onCleared();
  }
}