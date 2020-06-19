package com.app.numad20su_siyuchen;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;



public class Service extends AppCompatActivity {
    private EditText eurInput;
    private TextView usdOutput;
    private Button btn;
    private double inputValue;

    private static final String TAG = "Service";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        eurInput = (EditText) findViewById(R.id.serviceInput);
        usdOutput = (TextView) findViewById(R.id.serviceOutput);
        btn = (Button) findViewById(R.id.buttonConvert);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (eurInput.getText().toString().trim().length()>0 && !eurInput.getText().toString().trim().equals(".")) {
                    String inputValueRaw = eurInput.getText().toString();
                    inputValue = Double.parseDouble(inputValueRaw);
                    btn.setText("Please Wait");

                    Convert task = new Convert();
                    task.execute();
                }
            }
        });
    }



    public class Convert extends AsyncTask<String, Integer, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... strings) {
            URL url;
            String[] res = new String[1];
            try {
                url = new URL("https://api.exchangeratesapi.io/latest");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream inputStream = conn.getInputStream();
                final String resp = convertStreamToString(inputStream);

                JSONObject jObject = new JSONObject(resp);
                String amount = jObject.getJSONObject("rates").getString("USD");

                res[0]=amount;
                return res;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            res[0]="Error";
            return res;
        }



        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.i(TAG, "Making progress...");
        }


        @Override
        protected void onPostExecute(String[] res) {
            super.onPostExecute(res);

            if (res[0].equalsIgnoreCase("Error")){
                usdOutput.setText("Error");
            } else {
                double output = Double.parseDouble(res[0]);
                double calculatedOutput = inputValue * output;
                usdOutput.setText(calculatedOutput + "");
                btn.setText("Convert");
            }
        }
    }
    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("editText", eurInput.getText().toString());
        outState.putString("textView", usdOutput.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        eurInput.setText(savedInstanceState.getString("editText"));
        usdOutput.setText(savedInstanceState.getString("textView"));
    }
}
