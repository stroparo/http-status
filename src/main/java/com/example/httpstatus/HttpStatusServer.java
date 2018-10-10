package com.stroparo.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import io.vertx.core.AbstractVerticle;

public class HttpStatusServer extends AbstractVerticle {

  private String[] websiteUrls = {
    "https://www.wikipedia.org/",
    "http://tldp.org/",
    "https://www.noaa.gov/"
  };

  public void start() {
    vertx.createHttpServer().requestHandler(req -> {

      String responseText = "";

      for (String url : websiteUrls) {
        responseText += url + " : " + getStatus(url) + "\n";
      }

      req.response()
        .putHeader("content-type", "text/plain")
        .end(responseText);
    }).listen(8080);
  }

  public static String getStatus(String url) {
    String result = "";
    int code = 200;
    try {
      URL siteURL = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(3000);
      connection.connect();

      result = Integer.toString(connection.getResponseCode()) + " " + connection.getResponseMessage();
    } catch (Exception e) {
      result = "Bad URL error: " + e.getMessage();
    }
    return result;
  }
}
