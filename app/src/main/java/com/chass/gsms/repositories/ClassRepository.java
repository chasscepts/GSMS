package com.chass.gsms.repositories;

import com.chass.gsms.helpers.ClassesCache;
import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.helpers.SharedDataStore;
import com.chass.gsms.hilt.RetrofitRequestDefaultTimeout;
import com.chass.gsms.models.Class;
import com.chass.gsms.networks.retrofit.ApiClient;
import com.chass.gsms.networks.retrofit.LocalGetCall;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Manages retrieving
 */
@ActivityRetainedScoped
public class ClassRepository {
  SessionManager sessionManager;
  private ClassesCache cache;
  private SharedDataStore dataStore;
  private ApiClient client;

  @Inject
  public ClassRepository(SessionManager sessionManager, ClassesCache cache, SharedDataStore dataStore, @RetrofitRequestDefaultTimeout ApiClient client){
    this.sessionManager = sessionManager;
    this.cache = cache;
    this.dataStore = dataStore;
    this.client = client;
  }

  @SuppressWarnings({"unchecked"})
  public Call<Class> getClass(int classId){
    Class aClass = cache.get(classId);
    if(aClass != null){
      return new LocalGetCall.Builder<Class>()
          .url("http://chass.me.ht/school/class/" + sessionManager.getSchool().getId() + "/" + classId)
          .response(aClass)
          .build();
    }
    return client.getClass(sessionManager.getSchool().getId(), classId);
  }

  public void cache(Class aClass){
    cache.save(aClass);
  }
}
