package com.example.jitu.okhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       new HomePage().execute();

    }


    public void networkcall(){
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
            System.out.println("ggg"+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class HomePage extends AsyncTask<Long, Integer, Long> {

        @Override
        protected Long doInBackground(Long... params) {
            networkcall();
            return null;
        }

        @Override
        protected void onPreExecute() {
        }
    }


}
