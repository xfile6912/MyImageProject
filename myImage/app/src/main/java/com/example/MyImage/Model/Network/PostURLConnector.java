package com.example.MyImage.Model.Network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostURLConnector extends Thread {

    private String result;
    private String URL;
    private String postData;
    public PostURLConnector(String url, String datastream){
        URL = "http://192.168.0.103/"+url;
        postData=datastream;
    }

    @Override
    public void run() {
        final String output = request(URL, postData);
        result = output;
    }

    public String getResult(){
        return result;
    }
    private String readStream(InputStream in) throws IOException {

        StringBuilder jsonHtml = new StringBuilder();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = null;

        while((line = reader.readLine()) != null)
            jsonHtml.append(line);

        reader.close();
        return jsonHtml.toString();
    }

    private String request(String urlStr, String postData) {
        StringBuilder output = new StringBuilder();
        try {
            java.net.URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream outputStream=conn.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int resCode = conn.getResponseCode();
                System.out.println(resCode);
                if (resCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())) ;
                    String line = null;
                    while(true) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        output.append(line + "\n");
                    }

                    reader.close();
                    conn.disconnect();
                }
            }
        } catch(Exception ex) {
            Log.e("SampleHTTP", "Exception in processing response.", ex);
            ex.printStackTrace();
        }
        return output.toString();
    }
}