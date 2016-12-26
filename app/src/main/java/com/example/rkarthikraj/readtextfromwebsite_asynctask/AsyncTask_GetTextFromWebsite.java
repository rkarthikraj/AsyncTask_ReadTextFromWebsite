package com.example.rkarthikraj.readtextfromwebsite_asynctask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTask_GetTextFromWebsite extends AppCompatActivity {

    TextView writeText;
    EditText inputText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task__get_text_from_website);
        writeText = (TextView) findViewById(R.id.tv);
        inputText = (EditText) findViewById(R.id.inputText);

    }

    public void onClickGet(View view){
        String urlText = inputText.getText().toString();
        AsyncTextReader reader = new AsyncTextReader(); // or Directly new AsyncTaskRunner().execute();
        reader.execute(urlText);
    }
    private class AsyncTextReader extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String outputresponse;
        @Override
        protected String doInBackground(String... params)
        {

            try {
                URL url = new URL("http://192.168.1.213/android_sample.php?name=" + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);//connection.setRequestMethod("POST");c
               /* //connection.setRequestProperty("Content-Type", "application/json");
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                //osw.write(String.format( String.valueOf(json)));
                osw.flush();
                osw.close();*/

                InputStream stream = connection.getInputStream();
                InputStreamReader isReader = new InputStreamReader(stream);
                BufferedReader br = new BufferedReader(isReader);
                outputresponse = br.readLine();
                Thread.sleep(3000);
            }
            catch(IOException e)
            {

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result)
        {
            progressDialog.dismiss();
            writeText.setText(outputresponse);
        }

        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(AsyncTask_GetTextFromWebsite.this,"ProgressDialog","Wait until text reads");
        }


    }




}


