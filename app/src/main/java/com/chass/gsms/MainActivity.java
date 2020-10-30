package com.chass.gsms;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chass.gsms.helpers.SessionManager;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
  @Inject
  SessionManager sessionManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    //getSupportActionBar().setDisplayShowHomeEnabled(true);
    toolbar.setNavigationOnClickListener(view -> {
      onBackPressed();
    });
    setupSession();
  }

  /**
   * Observe sessionManager for when user logs out and start LoginActivity
   */
  private void setupSession() {
    sessionManager.isLoggedIn().observe(this, loggedIn -> {
      if(!loggedIn){
        startActivity(new Intent(this, LoginActivity.class));
        finish(); //Don't come back to this Activity on Back Press
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.logout) {
      sessionManager.logout();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

}