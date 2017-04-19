package com.bloodtrackerplus.bloodtracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Neelam on 2/14/2016.
 */
public class AcceptorActivity extends Activity{
    private Spinner city1, bg;
    private Button find;
    String arr1[]={"Select City", "Delhi", "Jaipur", "Mumbai"};
    String arr2[] = {"Select Blood Group", "A+ve", "A-ve", "B+ve", "B-ve", "AB+ve", "AB-ve", "O+ve", "O-ve", "A1+ve", "A1-ve", "A2+ve", "A2-ve", "A1B+ve", "A1B-ve", "A2B+ve", "A2B-ve"};
    String cy,bgrp;


    ArrayList<DonorsBean> donorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceptor_activity);
        city1=(Spinner)findViewById(R.id.spinner1);
        bg=(Spinner)findViewById(R.id.spinner1);
        find=(Button)findViewById(R.id.find);

        addListenerOnButtonClick();

        ArrayAdapter<String> a1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr1);
        city1.setAdapter(a1);
        ArrayAdapter<String> a2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr2);
        bg.setAdapter(a2);


    }
    public void addListenerOnButtonClick(){
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(AcceptorActivity.this,AcceptorBloodgroup.class);
                startActivity(a);
            }
        });
    }

    public void onclick3(View view)
    {
        cy=city1.getSelectedItem().toString().trim();
        bgrp=bg.getSelectedItem().toString().trim();

        try {
            String jsonRegistration = getJsonForMobileInfo(cy,bgrp);
            new Acceptor().execute(jsonRegistration);
        }
        catch (UnsupportedEncodingException e)
        {

        }

    }


    String getJsonForMobileInfo(String cy,String bgrp) throws UnsupportedEncodingException
    {

        JSONObject jsonObj=new JSONObject();
        JSONArray jar=new JSONArray();

        try
        {
            jar.put(jsonObj.put("city", cy).put("bldgrp", bgrp)) ;

        }
        catch (Exception e)
        {

        }

        return  jar.toString();

    }



    private class Acceptor extends AsyncTask<String,Void,String>
    {
        ProgressDialog pd;
        StringBuffer sb=new StringBuffer();
                @Override
                         protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(AcceptorActivity.this);
        pd.setMessage("Please wait...");
        pd.show();
    }



        @Override
        protected String doInBackground(String... params) {

            String response="";
            String URLString="http://bloodtrackerplus.in/bloodtrackerservices/finddonor.php";


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
           try {
               donorList=new ArrayList<DonorsBean>();
                JSONArray jsonarray = new JSONArray(result);
                JSONObject jsonobject=null;
                for(int i=0; i<jsonarray.length();i++)
                {
                    jsonobject = jsonarray.getJSONObject(i);

                    String mobno=jsonobject.getString("Mobile_Number");
                    String name=jsonobject.getString("Name");
                    DonorsBean db=new DonorsBean();
                    db.setMob(mobno);
                    db.setName(name);
                    donorList.add(db);
                }

               Intent intent=new Intent(AcceptorActivity.this,ListActivity.class);
               intent.putExtra("DONOR_LIST",donorList);
               startActivity(intent);


            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
     }
}


