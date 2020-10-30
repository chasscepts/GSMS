package com.chass.gsms.networks.clients;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface IFormData {
  void use(HttpURLConnection connection) throws IOException;
}
