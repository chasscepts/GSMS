package com.chass.gsms;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.models.Student;
import com.chass.gsms.ui.classdetails.ClassDetailsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements IMain {
  @Inject
  SessionManager sessionManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setupSession();

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
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
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void selectClass(String className) {
    ClassDetailsViewModel viewModel = new ViewModelProvider(this).get(ClassDetailsViewModel.class);
    viewModel.setClassName(className);
    View view = findViewById(R.id.nav_host_fragment);
    Navigation.findNavController(view).navigate(R.id.ClassDetailsFragment);
  }

  @Override
  public void selectStudent(Student student){

  }
}