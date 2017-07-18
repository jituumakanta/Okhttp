package com.example.jitu.okhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private String accessToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new HomePage().execute();

    }


    public void networkcall() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://quliver.com/wrapme/homepage/get_productname.php")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("ggg" + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class HomePage extends AsyncTask<Long, Integer, Long> {

        @Override
        protected Long doInBackground(Long... params) {
            // networkcall();
            try {
                getAccessToken();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
        }
    }

    public void getAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("client_id", "5fY03oxq7hgtURDofoWChCFgaRwhEtrxZZqY2V6N")
                .add("client_secret", "UtcUdZJJF8a2kEowwK0BDt8TWkFXJNXM54rpvVQTo4tG6r6rZX6z61ptzBk3tf1rGdNNkwnLGvPGlyLSXmIDedgqJhJY00vphgkFvuQmQ3JMSW2yxJ742MKUx751pxHV")
                .add("grant_type", "client_credentials")
                .build();
        Request request = new Request.Builder()
                .url("https://test.instamojo.com/oauth2/token/")
                .post(body)
                .build();
      //  Response response = client.newCall(request).execute();
       // String jsonOutput = response.body().string();
       // System.out.println("ddd" + response.body().string());
/*
        Object obj=JSONValue.parse(jsonOutput);
        JSONObject jsonObject = (JSONObject) obj;

        String name = (String) jsonObject.get("name");*/

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString;
               // String errorMessage = null;
                String transactionID = null;
                responseString = response.body().string();
                response.body().close();
                try {
                    JSONObject responseObject = new JSONObject(responseString);
                    if (responseObject.has("error")) {
                       // errorMessage = responseObject.getString("error");
                    } else {
                        accessToken = responseObject.getString("access_token");

                    }
                } catch (JSONException e) {
                   // errorMessage = "Failed to fetch Order tokens";
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("aaa" + accessToken );
                    }
                });
            }




        });

    }


    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }
}
