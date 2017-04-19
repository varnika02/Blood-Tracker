package com.bloodtrackerplus.bloodtracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

/**
 * Created by Neelam2015 on 7/21/2015.
 */
public class LoginActivity extends Activity {

    private Button b1;
    private EditText e1;
    private EditText e2;

    private TextView tvx1;
    SharedPreferences sharedprefence;
    SharedPreferences.Editor edit;
    String user,password,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        b1 = (Button) findViewById(R.id.button);
        e1 = (EditText) findViewById(R.id.et1);
        e2 = (EditText) findViewById(R.id.et2);

        tvx1 = (TextView) findViewById(R.id.tV2);
        sharedprefence=getSharedPreferences("BLOOD_APP", Context.MODE_PRIVATE);
        edit=sharedprefence.edit();
        uid=sharedprefence.getString("user_id", null);

    }

    public void onClick(View v) {

        user = e1.getText().toString();
        password = e2.getText().toString();
        boolean flag=true;

        if (user.equals("")) {
            e1.setError("Enter Username");
        } else if (password.equals("")) {
            e2.setError("Enter Password");
            flag=false;

        }
        else if(flag==true)
        {

            edit.putString("LOGIN_STATUS", "1");

            edit.commit();

            try {
                String jsonRegistration = getJsonForMobileInfo(user,password,uid);
                new Login().execute(jsonRegistration);
            }
            catch (UnsupportedEncodingException e)
            {

            }


        }
    }
    String getJsonForMobileInfo(String user,String password,String uid) throws UnsupportedEncodingException
    {

        JSONObject jsonObj=new JSONObject();
        JSONArray jar=new JSONArray();

        try
        {
            jar.put(jsonObj.put("email", user).put("pass", password).put("userid", uid)) ;

        }
        catch (Exception e)
        {

        }

        return  jar.toString();

    }

    public void clickthis(View view)
    {
        //Toast.makeText(getApplication(),"Yooooooo",Toast.LENGTH_LONG).show();
        Intent i = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(i);
    }
    private class Login extends AsyncTask<String,Void,String>
    {
        ProgressDialog pd;
        StringBuffer sb=new StringBuffer();
            @Override
                         protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(LoginActivity.this);
        pd.setMessage("Please wait...");
        pd.show();
    }



        @Override
        protected String doInBackground(String... params) {

            String response="";
            String URLString="http://www.bloodtrackerplus.in/bloodtrackerservices/login.php";


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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.dismiss();
            JSONObject jsonobj=null;
            if(result!=null)
            {

                try {
                    jsonobj = new JSONObject(result);
                    String status=jsonobj.getString("Status");
                    if(status.equals("Success"))
                    {
                       String id =jsonobj.getString("ID");
                        String contactStatus =jsonobj.getString("ContactStatus");
                        if(contactStatus.equals("get")){
                            Intent in = new Intent(LoginActivity.this, OptionsActivity.class);
                            startActivity(in);
                        }
                        else {
                            Intent inten = new Intent(LoginActivity.this, ContactActivity.class);
                            startActivity(inten);
                        }

                    }

                    else
                    {

                        Toast.makeText(LoginActivity.this,"Invalid Login Details",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {

                }
            }

        }
    }



}