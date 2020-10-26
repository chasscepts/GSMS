package com.chass.gsms.ui.classdetails;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.enums.ViewStates;
import com.chass.gsms.helpers.SharedDataStore;
import com.chass.gsms.interfaces.ILogger;
import com.chass.gsms.models.Class;
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

  private void setupClass() {
    viewState.setState(ViewStates.BUSY, "Loading Class Info. Please wait...");
    Call<Class> call = repository.getClass(dataStore.getSelectedClassName());
    call.enqueue(new Callback<Class>() {
      @Override
      public void onResponse(@NonNull Call<Class> call, @NonNull Response<Class> response) {
        if(response.isSuccessful()){
          Class bClass = response.body();
          if(bClass != null){
            aClass.set(bClass);
            adapter.loadStudents(bClass.getStudents());
            viewState.restoreNormalState();
            return;
          }
        }
        else {
          logger.print(TAG, response.errorBody());
        }
        viewState.error("Application encountered an error while retrieving class details. The response we got from the server is not what we expected response. Be assured that we are working to resolve the issue. If the problem persists, please contact us so we can resolve it.");
      }

      @Override
      public void onFailure(@NonNull Call<Class> call, @NonNull Throwable t) {
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