package com.chass.gsms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chass.gsms.helpers.SessionManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
  @Inject
  SessionManager sessionManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    setupSession();
  }

  /**
   * Observe sessionManager for successful login and start MainActivity
   */
  private void setupSession() {
    sessionManager.isLoggedIn().observe(this, loggedIn -> {
      if(loggedIn){
        startActivity(new Intent(this, MainActivity.class));
        finish(); //Don't come back to this Activity on Back Press
      }
    });
  }


}