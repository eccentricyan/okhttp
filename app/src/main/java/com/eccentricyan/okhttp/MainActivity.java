package com.eccentricyan.okhttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    TextView aaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequest(button);
            }
        });


    }

    private void setResFrServer(HashMap<String, String> h) {
        aaa = (TextView) findViewById(R.id.txt1);
        aaa.setText(h.get("Host"));
    }

    public void getRequest(final Button button) {
        button.setEnabled(false);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = null;

                // リクエストオブジェクトを作って
                Request request = new Request.Builder()
                        .url("http://httpbin.org/headers")
                        .get()
                        .build();

                // クライアントオブジェクトを作って
                OkHttpClient client = new OkHttpClient();

                // リクエストして結果を受け取って
                try {
                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 返す
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
//                JSONArray arr = new JSONArray(result);
//                JSONObject jObj = result.getJSONObject(0);
//                String date = jObj.getString("NeededString");
                HashMap<String,String> map = new HashMap();
                try {
                    JSONObject json = new JSONObject(result);
                    JSONObject headers = json.getJSONObject("headers");
                    String data1 = headers.getString("Accept-Encoding");
                    String data2 = headers.getString("Host");
                    map.put("Accept-Encoding", data1);
                    map.put("Host", data2);
                    setResFrServer(map);

                } catch (JSONException e) {

                }


//                try {
//                JSONObject res = new JSONObject(result);
//
//
//                    res.getString("NeededString");
//                } catch (JSONException) {
////
////                }
                button.setEnabled(true);
            }

        }.execute();
    }
}
