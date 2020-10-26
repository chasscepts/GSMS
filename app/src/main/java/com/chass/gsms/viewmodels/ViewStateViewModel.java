package com.chass.gsms.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;

import com.chass.gsms.databinding.BusyViewBinding;
import com.chass.gsms.databinding.ErrorStateViewBinding;
import com.chass.gsms.databinding.InfoStateViewBinding;
import com.chass.gsms.databinding.SuccessStateViewBinding;
import com.chass.gsms.enums.ViewStates;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class ViewStateViewModel {
  @Inject
  public ViewStateViewModel(){

  }

  private ViewStates state = ViewStates.NORMAL;
  private String message;

  public ViewStates getState(){
    return state;
  }

  public String getMessage(){
    return message;
  }

  /**
   * Used to inform the view that state has changed.
   * We could make state observable to achieve the same thing, but it is possible for state to be the same while the message change.
   */
  public final ObservableBoolean switched = new ObservableBoolean();

  public void setState(ViewStates state, @Nullable String message){
    this.state = state;
    this.message = message;
    switched.set(!switched.get());
  }

  /**
   * Sets the state to NORMAL (for ease of call from view)
   */
  public void restoreNormalState(){
    setState(ViewStates.NORMAL, null);
  }

  public void normal(){
    setState(ViewStates.NORMAL, null);
  }

  public void success(String msg){
    setState(ViewStates.SUCCESS, msg);
  }

  public void error(String msg){
    setState(ViewStates.ERROR, msg);
  }

  public void busy(String msg){
    setState(ViewStates.BUSY, msg);
  }

  public void info(String msg){
    setState(ViewStates.INFO, msg);
  }

  public void connectionError(){
    setState(ViewStates.ERROR, "An error occurred while trying to communicate with the server. The most observed cause of this error is unavailability of internet connection. Please ensure that you are connected to the internet then try again");
  }

  public void responseError(String action){
    StringBuilder sb = new StringBuilder();
    sb.append("Application encountered an error while ");
    sb.append(action);
    sb.append(". The response we got from the server is not what we expected response. Be assured that we are working to resolve the issue. If the problem persists, please contact us so we can resolve it.");
    setState(ViewStates.ERROR, sb.toString());
  }

  @BindingAdapter({"viewState", "switched"})
  public static void setState(FrameLayout container, ViewStateViewModel stateViewModel, boolean switched){
    container.removeAllViews();
    switch (stateViewModel.state){
      case BUSY:
        BusyViewBinding b1 = BusyViewBinding.inflate(LayoutInflater.from(container.getContext()), container, true);
        b1.setViewModel(stateViewModel);
        break;
      case ERROR:
        ErrorStateViewBinding b2 = ErrorStateViewBinding.inflate(LayoutInflater.from(container.getContext()), container, true);
        b2.setViewModel(stateViewModel);
        break;
      case INFO:
        InfoStateViewBinding b3 = InfoStateViewBinding.inflate(LayoutInflater.from(container.getContext()), container, true);
        b3.setViewModel(stateViewModel);
        break;
      case SUCCESS:
        SuccessStateViewBinding b4 = SuccessStateViewBinding.inflate(LayoutInflater.from(container.getContext()), container, true);
        b4.setViewModel(stateViewModel);
        break;
      default:
        container.setVisibility(View.GONE);
        return;
    }
    container.setVisibility(View.VISIBLE);
  }
}
