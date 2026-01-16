package com.heypixel.heypixelmod.obsoverlay.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpUtils {
    private static final Logger log = LogManager.getLogger(HttpUtils.class);
    public static final String DEFAULT_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";

    public HttpUtils() {
        HttpURLConnection.setFollowRedirects(true);
    }

    public static HttpURLConnection make(String url, String method, String agent) throws IOException {
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
        httpConnection.setRequestMethod(method);
        httpConnection.setConnectTimeout(5000);
        httpConnection.setReadTimeout(10000);
        httpConnection.setRequestProperty("User-Agent", agent);
        httpConnection.setInstanceFollowRedirects(true);
        httpConnection.setDoOutput(true);
        return httpConnection;
    }

    public static String request(String url, String method, String agent) {
        try {
            HttpURLConnection connection = make(url, method, agent);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            bufferedReader.close();
            return stringBuilder.toString();
        } catch (SocketTimeoutException var7) {
            log.error("Read timed out for URL (multi-line): {}", url);
            return null;
        } catch (IOException var8) {
            log.error("Error while making request to URL (multi-line): {}", url, var8);
            return null;
        }
    }

    public static String requestSingleLine(String url, String method, String agent) {
        try {
            HttpURLConnection connection = make(url, method, agent);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();
            return stringBuilder.toString();
        } catch (SocketTimeoutException var7) {
            log.error("Read timed out for URL (single-line): {}", url);
            return null;
        } catch (IOException var8) {
            log.error("Error while making request to URL (single-line): {}", url, var8);
            return null;
        }
    }

    public static String get(String url) throws IOException {
        return request(url, "GET", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
    }

    public static String get2(String url) throws IOException {
        return requestSingleLine(url, "GET", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
    }

    public static void download(String url, File file) throws IOException {
        FileUtils.copyInputStreamToFile(make(url, "GET", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0").getInputStream(), file);
    }

    public static String getResultFormStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        return builder.toString();
    }
}
