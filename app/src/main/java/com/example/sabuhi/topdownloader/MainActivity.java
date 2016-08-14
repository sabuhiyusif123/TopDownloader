package com.example.sabuhi.topdownloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button btnParse;
    private ListView ListApps;
    private String mFileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnParse =(Button) findViewById(R.id.btnParse);
        ListApps = (ListView) findViewById(R.id.xmlListView);
        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add parse activation code
                ParseApplications parseApplications = new ParseApplications(mFileContents);
                parseApplications.process();

                ArrayAdapter<Applications> arrayAdapter = new ArrayAdapter<Applications>(MainActivity.this,R.layout.liste_item,parseApplications.getApplications());
                ListApps.setAdapter(arrayAdapter);
            }
        });
        ListApps = (ListView) findViewById(R.id.xmlListView);
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");

    }
    private  class DownloadData extends AsyncTask<String,Void ,String>{


        @Override
        protected String doInBackground(String... params) {
           mFileContents= downloadXMLFile(params[0]);
            if(mFileContents==null){
                Log.d("DownloadData","Error Downloading");

            }
            return mFileContents;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("DownloadData", "Result was "+ result);

        }

        private String downloadXMLFile(String urlPath){
            StringBuilder tempBuffer = new StringBuilder();
            try{

                URL url= new URL(urlPath);                                           //url i yaradir
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();    //url e qosulur
                int response = connection.getResponseCode();                // responsu alir yeni ki cavab verirmi
                Log.d("DownloadData","Response was "+ response);

                InputStream is = connection.getInputStream();          //Bu fayli oxumaq ucun inputstream e qosulur
                InputStreamReader isr = new InputStreamReader(is);     // bu ise fayli oxuyur

                int charRead;
                char[] inputBuffer = new  char[500];
                while(true){                                                          //500 xarakter oxuyacaq
                    charRead = isr.read(inputBuffer);
                    if(charRead<=0){
                        break;
                    }
                    tempBuffer.append(String.copyValueOf(inputBuffer,0,charRead));   //sora hemin 500 u tempBuffer bufferine append eliyecey
                }
                return tempBuffer.toString();                                        // burda da tempBuffer i String e cevirir ki return qiymete uygun gelsin


            }catch(IOException e){
                Log.d("DownloadData","IOException error "+ e.getMessage());
//                e.printStackTrace();
            }catch (SecurityException e){
            Log.d("DownloadData", "Security permission need "+e.getMessage());
            }
            return null;
        }
    }
}
