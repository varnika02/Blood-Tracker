package com.bloodtrackerplus.bloodtracker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neelam2015 on 7/21/2015.
 */
public class SignupActivity extends Activity {
    private EditText user;
    private EditText name, ph, pass, repass;
    private Button sign;
    String arr[] = {"Select Blood Group", "A+ve", "A-ve", "B+ve", "B-ve", "AB+ve", "AB-ve", "O+ve", "O-ve", "A1+ve", "A1-ve", "A2+ve", "A2-ve", "A1B+ve", "A1B-ve", "A2B+ve", "A2B-ve"};

    private Spinner bg;

    private  static String m,n,pa,rp,blg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_activity);
        ActionBar actionBar = this.getActionBar();
        if (actionBar == null)
            Log.d("error", "Actionbar is null");
        else {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF7519")));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }


        user = (EditText) findViewById(R.id.editText1);
        name = (EditText) findViewById(R.id.editText2);
        bg = (Spinner) findViewById(R.id.spinner1);
        pass = (EditText) findViewById(R.id.editText4);
        repass = (EditText) findViewById(R.id.editText5);
        sign = (Button) findViewById(R.id.button1);
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        bg.setAdapter(a);


        sign.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                 m = user.getText().toString().trim();
                 n = name.getText().toString();
                 pa = pass.getText().toString();
                 rp = repass.getText().toString();
                 blg = bg.getSelectedItem().toString().trim();
                Pattern pat;
                Matcher mat;
                Boolean flag = true;

                String emailpattern = "^[a-z0-9_-]{3,15}$";
                pat = Pattern.compile(emailpattern);
                mat = pat.matcher(m);
                String MobilePattern = "[0-9]{10}";


                if (m.equals("") || n.equals("") || blg.equals("Select Blood Group") || pa.equals("") || rp.equals("")) {

                    if (m.equals(""))
                        user.setError("Enter Username");
                    if (n.equals(""))
                        name.setError("Enter name");
                    if (blg.equals("Select Blood Group"))
                        Toast.makeText(getApplicationContext(), " Please select a Blood Group", Toast.LENGTH_SHORT).show();
                    if (pa.equals(""))
                        pass.setError("Enter Password");
                    if (rp.equals(""))
                        repass.setError("Re-Enter Password");
                } else if (mat.matches() == false) {
                    user.setError("Invalid Username");
                    return;
                } else if (pa.length() < 6 || pa.length() > 15) {
                    pass.setError("Enter Password of 6-15 characters");
                } else if (!pa.equals(rp)) {
                    flag = false;
                    repass.setError("Password does not match !");
                    return;

                } else if (flag == true)

                {

                    try {
                        String jsonRegistration = getJsonForMobileInfo(m,n,blg,pa);
                        new Signup().execute(jsonRegistration);
                    }
                    catch (UnsupportedEncodingException e)
                    {

                    }

                    
                }


            }
        });
    }
    String getJsonForMobileInfo(String m,String n,String blg,String pa) throws UnsupportedEncodingException
    {

        JSONObject jsonObj=new JSONObject();
        JSONArray jar=new JSONArray();

        try
        {
            jar.put(jsonObj.put("email", m).put("name", n).put("bldgrp", blg).put("pass", pa)) ;

        }
        catch (Exception e)
        {

        }

        return  jar.toString();

    }



    private class Signup extends AsyncTask<String,Void,String>
    {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(SignupActivity.this);
            pd.setMessage("please Wait...");
            pd.setCancelable(false);
            pd.show();


        }

        @Override
        protected String doInBackground(String... params) {

            String response="";
            String URLString="http://www.bloodtrackerplus.in/bloodtrackerservices/neelam.php";


            SendJsonData sendJson=new SendJsonData();
            try {
                response = sendJson.setURLandData(URLString, params[0]);
            }
            catch (Exception e)
            {

            }


            return response;
        }


        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            SharedPreferences sharedprefence=getSharedPreferences("BLOOD_APP", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit=sharedprefence.edit();
            edit.putString("USERID",result);
            edit.commit();
            pd.dismiss();
            Toast.makeText(getBaseContext(),result,Toast.LENGTH_LONG).show();
            Intent i = new Intent(SignupActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }
    }


}


