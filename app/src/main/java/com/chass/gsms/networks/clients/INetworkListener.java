package com.chass.gsms.networks.clients;

public interface INetworkListener {
  void onResponse(int code, String response);
  void onFailure(Throwable t);
}
