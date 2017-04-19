package com.bloodtrackerplus.bloodtracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import android.widget.RadioGroup;
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

/**
 * Created by Neelam2015 on 7/22/2015.
 */
public class ContactActivity extends AppCompatActivity {

    Spinner s1;
    EditText mobileno,age,address,pincode;
    Button save;
    RadioButton genderRadioButton;
    RadioGroup genderRadioGroup;
    String m,c,a,p,ag,gen,uid;
    String arr1[]={"Select City", "Delhi", "Jaipur", "Mumbai"};
    SharedPreferences sharedprefence;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contact_activity);
        sharedprefence=getSharedPreferences("BLOOD_APP", Context.MODE_PRIVATE);
        edit=sharedprefence.edit();
        uid=sharedprefence.getString("USERID", null);
        s1=(Spinner)findViewById(R.id.spinner1);

        mobileno = (EditText)findViewById(R.id.editText2);
        age = (EditText)findViewById(R.id.editText3);
        address = (EditText)findViewById(R.id.editText4);
        pincode = (EditText)findViewById(R.id.editText6);
        save = (Button)findViewById(R.id.button1);
        genderRadioGroup=(RadioGroup)findViewById(R.id.gender);
        ArrayAdapter<String> a1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr1);
        s1.setAdapter(a1);

        save.setOnClickListener(new OnClickListener() {



            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                genderRadioButton=(RadioButton)findViewById(selectedId);
                 m = mobileno.getText().toString();
                 a = address.getText().toString();
                 p = pincode.getText().toString();
                 ag= age.getText().toString();
                 c=s1.getSelectedItem().toString().trim();
                gen= (String) genderRadioButton.getText();

                Boolean flag=true;

                if(m.equals("")||a.equals("")||c.equals("Select City")||p.equals("")||ag.equals("")||gen.equals(""))
                {

                    if(m.equals(""))
                        mobileno.setError("Enter Mobile number");
                    if(a.equals(""))
                        address.setError("Enter full address");
                    if(c.equals("Select City"))
                        Toast.makeText(getApplicationContext(), " Please select a City", Toast.LENGTH_SHORT).show();
                    if(p.equals(""))
                        pincode.setError("Enter pincode");
                    if(ag.equals(""))
                        age.setError("Enter your age");
                    if(gen.equals(""))
                        genderRadioButton.setError("Please select gender");

                }
                else if (m.length()!=10)
                {
                    mobileno.setError("Enter 10 digit Mobile number");
                    flag=false;
                }
                else if (ag.length()>2){
                    age.setError("enter valid age");
                }

                else if(flag==true)

                {
                   // Toast.makeText(getBaseContext(),uid,Toast.LENGTH_LONG).show();
                    try {
                        String jsonRegistration = getJsonForMobileInfo(m,ag,a,p,c,gen,uid);
                        new Contact().execute(jsonRegistration);



                    }
                    catch (UnsupportedEncodingException e)
                    {

                    }
                   // Toast.makeText(getApplicationContext(), "Saved Successfully !", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(ContactActivity.this,NavigationActivity.class));
                }
            }
        });


    }
    String getJsonForMobileInfo(String m,String ag,String a,String p, String c,String gen,String uid) throws UnsupportedEncodingException
    {

        JSONObject jsonObj=new JSONObject();
        JSONArray jar=new JSONArray();

        try
        {
            jar.put(jsonObj.put("mobile", m).put("age", ag).put("address", a).put("pincode", p).put("city",c).put("gender",gen).put("userid",uid)) ;

        }
        catch (Exception e)
        {

        }

        return  jar.toString();

    }

    private class Contact extends AsyncTask<String,Void,String>
    {
        ProgressDialog pd;
        StringBuffer sb=new StringBuffer();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(ContactActivity.this);
            pd.setMessage("Please wait...");
            pd.show();
        }



        @Override
        protected String doInBackground(String... params) {

            String response="";
            String URLString="http://www.bloodtrackerplus.in/bloodtrackerservices/contact.php";


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
            if(result.equals("Your details are saved successfully"))
            {
                edit.putString("city",c);
                edit.putString("pincode",p);
                edit.commit();


            }
            Toast.makeText(getBaseContext(),result,Toast.LENGTH_LONG).show();
            Intent i = new Intent(ContactActivity.this,OptionsActivity.class);
            startActivity(i);
            finish();

        }
    }


}
