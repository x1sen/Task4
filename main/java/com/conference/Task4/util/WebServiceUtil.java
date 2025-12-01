package com.conference.Task4.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebServiceUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebServiceUtil.class);

    public static void notifyStatus(String message) {
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(("{\"title\":\"" + message + "\"}").getBytes());
            int responseCode = con.getResponseCode();
            if (responseCode == 201) {
                logger.info("Notify sent");
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            in.close();
        } catch (Exception e) {
            logger.error("Web service error", e);
        }
    }
}